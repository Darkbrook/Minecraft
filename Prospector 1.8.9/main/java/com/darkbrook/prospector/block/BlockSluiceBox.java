package com.darkbrook.prospector.block;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.Pair;

import com.darkbrook.prospector.init.ProspectorGuis;
import com.darkbrook.prospector.init.ProspectorGuis.EnumGui;
import com.darkbrook.prospector.tileentity.TileEntitySluiceBox;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSluiceBox extends BlockContainer
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyEnum<EnumHalf> HALF = PropertyEnum.create("half", EnumHalf.class);
	public static final PropertyBool RUNNING = PropertyBool.create("running");
	
	protected final String variant;
	
	public BlockSluiceBox(String variant) 
	{
		super(Material.wood);
		this.variant = variant;
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(HALF, EnumHalf.BOTTOM).withProperty(RUNNING, false));
		setHardness(2.0f);
		setStepSound(soundTypeWood);
		setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
	}
	
	public String getVariant()
	{
		return variant;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random random)
    {
    	if (!state.getValue(RUNNING))
    		return;
    	
    	int x = pos.getX();
    	int y = pos.getY();
    	int z = pos.getZ();

    	if (random.nextInt(8) == 0)
    		world.playSound(x + 0.5d, y + 0.5d, z + 0.5d, "liquid.water", (random.nextFloat() * 0.2f) + 0.2f, (random.nextFloat() * 0.4f) + 1.2f, false);
    	
    	//TODO Particles?
    }
    
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		ItemStack stack = player.getCurrentEquippedItem();
		
		if (stack != null && stack.getItem() == Items.water_bucket)
		{
			if (!player.capabilities.isCreativeMode)
				player.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));

			updateSluices(world, pos, state, true);
		}
		else if (!world.isRemote)
		{
			TileEntity entity = world.getTileEntity(pos);

			if (entity instanceof TileEntitySluiceBox)
				ProspectorGuis.open(EnumGui.SLUICE_BOX, world, pos, player);
		}
		
		return true;
    }
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if (!stack.hasDisplayName())
			return;
		
		TileEntity entity = world.getTileEntity(pos);
		
		if (entity instanceof TileEntitySluiceBox)
			((TileEntitySluiceBox) entity).setCustomName(stack.getDisplayName());
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileEntity entity = world.getTileEntity(pos);
		
		if (entity instanceof TileEntitySluiceBox)
			InventoryHelper.dropInventoryItems(world, pos, (TileEntitySluiceBox) entity);
		
		super.breakBlock(world, pos, state);
		
		if (state.getValue(RUNNING))
			updateLowerSluices(world, pos, state, false);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntitySluiceBox();
	}
	
	@Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
		if (shouldBeRunning(world, pos, state))
			world.scheduleUpdate(pos, this, tickRate(world));
    }

	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
    {
		updateSluices(world, pos, state, shouldBeRunning(world, pos, state));
    }
	
	public boolean updateSluices(World world, BlockPos pos, IBlockState state, boolean running)
	{		
		if (state.getValue(RUNNING) == running)
			return false;
		
		state = state.withProperty(RUNNING, running);
		world.setBlockState(pos, state);
		updateLowerSluices(world, pos, state, running);
		return true;
	}
	
	public void updateLowerSluices(World world, BlockPos pos, IBlockState state, boolean running)
	{
		Pair<BlockPos, IBlockState> lowerSluice = getLowerSluice(world, pos, state);
		
		if (lowerSluice == null)
			return;
		
		IBlockState lowerState = lowerSluice.getValue();
		
		if (lowerState.getValue(RUNNING) != running)
			world.scheduleUpdate(lowerSluice.getKey(), lowerState.getBlock(), tickRate(world));
	}
	
	public boolean shouldBeRunning(World world, BlockPos pos, IBlockState state)
	{
		Pair<BlockPos, IBlockState> upperSluice = getUpperSluice(world, pos, state);
		return upperSluice != null && upperSluice.getValue().getValue(RUNNING);
	}
	
	@Override
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
		EnumHalf half = facing != EnumFacing.DOWN && (facing == EnumFacing.UP || hitY <= 0.5d) ? EnumHalf.BOTTOM : EnumHalf.TOP;
		EnumFacing horizontal = placer.getHorizontalFacing();
		EnumFacing opposite = horizontal.getOpposite();

		IBlockState state = world.getBlockState(pos.offset(half == EnumHalf.BOTTOM ? opposite : horizontal));
        return getStateFromMeta(meta).withProperty(FACING, opposite)
        		.withProperty(HALF, !(state.getBlock() instanceof BlockSluiceBox) || state.getValue(FACING) != opposite || state.getValue(HALF) != half ? half : half.opposite());
    }
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean isFullCube()
	{
		return false;
	}
	
	@Override
	public int getRenderType()
	{
		return 3;
	}
	
	@Override
	public boolean canRenderInLayer(EnumWorldBlockLayer layer)
	{
		return layer == EnumWorldBlockLayer.SOLID || layer == EnumWorldBlockLayer.TRANSLUCENT;
	}
	
	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity entity)
	{
		setBlockBoundsBasedOnState(state);
		CollisionMatrix.addCollisionBoxesToList(state.getValue(FACING), pos.getX() + minX, pos.getY() + minY, pos.getZ() + minZ, mask, list);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) 
	{
		setBlockBoundsBasedOnState(world.getBlockState(pos));
	}
	
	public void setBlockBoundsBasedOnState(IBlockState state)
	{
		if (state.getBlock() != this)
			return;
		
		float height = state.getValue(HALF).getHeight();
		
    	if (state.getValue(FACING).getAxis() == Axis.Z)
	    	setBlockBounds(0.125f, height, 0.0f, 0.875f, height + 0.5f, 1.0f);
    	else
	    	setBlockBounds(0.0f, height, 0.125f, 1.0f, height + 0.5f, 0.875f);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(HALF, (meta & 4) == 0 ? EnumHalf.BOTTOM : EnumHalf.TOP).withProperty(RUNNING, (meta & 8) != 0);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		return state.getValue(FACING).getHorizontalIndex() | state.getValue(HALF).ordinal() << 2 | (state.getValue(RUNNING) ? 1 : 0) << 3;
	}
	
	@Override
	public BlockState createBlockState() 
	{
	    return new BlockState(this, FACING, HALF, RUNNING);
	}
	
	public static Pair<BlockPos, IBlockState> getLowerSluice(World world, BlockPos pos, IBlockState state)
	{
		EnumFacing facing = state.getValue(FACING);
		EnumHalf half = state.getValue(HALF);
		BlockPos lowerPos = pos.offset(facing);

		if (half == EnumHalf.BOTTOM)
			lowerPos = lowerPos.down();
		
		IBlockState lowerState = world.getBlockState(lowerPos);
		return lowerState.getBlock() instanceof BlockSluiceBox && lowerState.getValue(FACING) == facing && lowerState.getValue(HALF) != half ? Pair.of(lowerPos, lowerState) : null;
	}
	
	public static Pair<BlockPos, IBlockState> getUpperSluice(World world, BlockPos pos, IBlockState state)
	{
		EnumFacing facing = state.getValue(FACING);
		EnumHalf half = state.getValue(HALF);
		BlockPos upperPos = pos.offset(facing.getOpposite());

		if (half == EnumHalf.TOP)
			upperPos = upperPos.up();

		IBlockState upperState = world.getBlockState(upperPos);
		return upperState.getBlock() instanceof BlockSluiceBox && upperState.getValue(FACING) == facing && upperState.getValue(HALF) != half ? Pair.of(upperPos, upperState) : null;
	}
	
	public static boolean isRunning(IBlockState state)
	{
		return state.getBlock() instanceof BlockSluiceBox && state.getValue(RUNNING);
	}
	
	public static enum EnumHalf implements IStringSerializable
	{
		BOTTOM, TOP;		
		
		@Override
		public String toString()
		{
			return getName();
		}
		
		@Override
		public String getName() 
		{
			return name().toLowerCase();
		}
		
		public float getHeight()
		{
			return this == BOTTOM ? 0.0f : 0.5f;
		}
		
		public EnumHalf opposite()
		{
			return this == BOTTOM ? TOP : BOTTOM;
		}
	}
	
	protected static class CollisionMatrix
	{
		private static final AxisAlignedBB[][] MATRIX = new AxisAlignedBB[4][8];
		
		static
		{
			for (EnumFacing facing : EnumFacing.HORIZONTALS)
			{
				AxisAlignedBB[] boxes = MATRIX[facing.getHorizontalIndex()];
				
				for (int i = 0, l = boxes.length; i < l; i++)
					boxes[i] = getDirectionalCollisionBox(facing, i, l, 0.125d, 0.0625d);
			}
		}
		
		private static AxisAlignedBB getDirectionalCollisionBox(EnumFacing facing, int i, int l, double w, double h)
		{
			double y = (i - 1) * h;
			double z = (facing.getAxisDirection() == AxisDirection.NEGATIVE ? i : l - i - 1) * w;
			return facing.getAxis() == Axis.Z ? new AxisAlignedBB(0, y, z, 0.75d, y + h, z + w) : new AxisAlignedBB(z, y, 0, z + w, y + h, 0.75d);
		}
		
		public static void addCollisionBoxesToList(EnumFacing facing, double x, double y, double z, AxisAlignedBB mask, List<AxisAlignedBB> list)
		{
			for (AxisAlignedBB box : MATRIX[facing.getHorizontalIndex()])
			{
				box = box.offset(x, y, z);
				
				if (mask.intersectsWith(box))
					list.add(box);
			}
		}
	}
}
