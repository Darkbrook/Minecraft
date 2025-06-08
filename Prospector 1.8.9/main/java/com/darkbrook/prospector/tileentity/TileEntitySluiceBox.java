package com.darkbrook.prospector.tileentity;

import org.apache.commons.lang3.tuple.Pair;

import com.darkbrook.common.tileentity.ITileEntityField;
import com.darkbrook.common.tileentity.TileEntityBase;
import com.darkbrook.common.tileentity.TileEntityInt;
import com.darkbrook.prospector.Prospector;
import com.darkbrook.prospector.block.BlockSluiceBox;
import com.darkbrook.prospector.init.ProspectorItems;
import com.darkbrook.prospector.inventory.ContainerSluiceBox;
import com.darkbrook.prospector.item.crafting.SluicingRecipes;
import com.darkbrook.prospector.item.crafting.SluicingRecipes.SluicingRecipe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;

public class TileEntitySluiceBox extends TileEntityBase implements ITickable
{
	private TileEntityInt sluiceTime = new TileEntityInt("SluiceTime");
	private TileEntityInt sluiceTimeTotal = new TileEntityInt("SluiceTimeTotal");
	
	public TileEntitySluiceBox()
	{
		assignFields();
	}
	
	@Override
	public void update()
	{
		if (worldObj.isRemote)
			return;
				
		if (contents[0] != null)
		{
			IBlockState state = worldObj.getBlockState(pos);
			
			if (!BlockSluiceBox.isRunning(state))
				return;
			
			SluicingRecipe recipe = SluicingRecipes.getInstance().getRecipe(contents[0]);
			
			if (recipe == null)
				return;
			
			if (hasFlakeSpace(1, recipe.getAmount().getMax()))
			{
				if (++sluiceTime.value < sluiceTimeTotal.value)
					return;
				
				sluiceTime.value = 0;
				addFlakes(1, recipe.getAmount().nextInt(random));
				
				if (--contents[0].stackSize <= 0)
					contents[0] = null;
				
				markDirty();
			}
			else
			{
				TileEntitySluiceBox entity = getLowerSluiceBoxTileEntity(state);
				
				if (entity == null || !canTransferInputTo(entity) || ++sluiceTime.value < sluiceTimeTotal.value)
					return;
				
				sluiceTime.value = 0;
				transferInputTo(entity);
				
				if (--contents[0].stackSize <= 0)
					contents[0] = null;
				
				markDirty();
			}
		}
		else
			sluiceTime.value = 0;
	}

	private boolean hasFlakeSpace(int slot, int space)
	{
		ItemStack stack = contents[slot];
		space -= (stack == null ? getInventoryStackLimit() : getInventoryStackLimit(stack) - stack.stackSize);
		return space <= 0 || (++slot < contents.length && hasFlakeSpace(slot, space));
	}

	private void addFlakes(int slot, int space)
	{
		ItemStack stack = contents[slot];
		int stored = space;
		
		if (stack == null)
		{
			space -= getInventoryStackLimit();
			stack = contents[slot] = new ItemStack(ProspectorItems.gold_flake, 0);
		}
		else
			space -= (getInventoryStackLimit(stack) - stack.stackSize);
		
		if (space <= 0)
			stack.stackSize += stored;
		else
		{
			stack.stackSize += (stored - space);
			
			if (++slot < contents.length)
				addFlakes(slot, space);
		}
	}
	
	private TileEntitySluiceBox getLowerSluiceBoxTileEntity(IBlockState state)
	{
		Pair<BlockPos, IBlockState> lowerSluice = BlockSluiceBox.getLowerSluice(worldObj, pos, state);
		
		if (lowerSluice != null)
		{
			TileEntity entity = worldObj.getTileEntity(lowerSluice.getKey());

			if (entity instanceof TileEntitySluiceBox)
				return (TileEntitySluiceBox) entity;
		}
		
		return null;
	}
	
	private boolean canTransferInputTo(TileEntitySluiceBox entity)
	{
		ItemStack input = contents[0];
		ItemStack transferInput = entity.contents[0];
		return transferInput == null || (transferInput.stackSize < getInventoryStackLimit(transferInput) && input.isItemEqual(transferInput) && ItemStack.areItemStackTagsEqual(input, transferInput));
	}
	
	private void transferInputTo(TileEntitySluiceBox entity)
	{
		ItemStack transferInput = entity.contents[0];
		
		if (transferInput == null)
		{
			ItemStack copy = contents[0].copy();
			copy.stackSize = 1;
			entity.setInventorySlotContents(0, copy);
		}
		else
		{
			transferInput.stackSize++;
			entity.markDirty();
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{		
		ItemStack reference = contents[slot];
		boolean isNewStack = stack == null || !stack.isItemEqual(reference) || !ItemStack.areItemStackTagsEqual(stack, reference);
		super.setInventorySlotContents(slot, stack);
		
		if (stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();

		if (slot == 0 && isNewStack)
		{
			sluiceTime.value = 0;
			SluicingRecipe recipe = SluicingRecipes.getInstance().getRecipe(stack);
			sluiceTimeTotal.value = recipe != null ? recipe.getSluiceTime() : Short.MAX_VALUE;
			markDirty();
		}
	}

	@Override
	public int getSizeInventory()
	{
		return 4;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player)
	{
		return new ContainerSluiceBox(this, playerInventory);
	}

	@Override
	public String getGuiID()
	{
		return Prospector.MODID + ":" + getUnlocalizedName();
	}

	@Override
	public String getUnlocalizedName()
	{
		return "sluice_box";
	}

	@Override
	protected ITileEntityField[] createFields()
	{
		return new ITileEntityField[] {sluiceTime, sluiceTimeTotal};
	}
}
