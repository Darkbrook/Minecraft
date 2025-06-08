package com.darkbrook.kingdoms.server.block.forge;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.darkbrook.kingdoms.KingdomsServer;
import com.darkbrook.kingdoms.common.util.Color;
import com.darkbrook.kingdoms.server.block.table.TableBlockEntity;
import com.darkbrook.kingdoms.server.item.KingdomsItem;
import com.darkbrook.kingdoms.server.item.KingdomsStack;
import com.darkbrook.kingdoms.server.item.items.MetalItem;
import com.darkbrook.kingdoms.server.item.mmo.Metal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.EulerAngle;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class ForgeBlockEntity extends TableBlockEntity<ForgeBlockEntity>
{
	private static final ParticleEffect COAL_DUST_PARTICLE = new DustParticleEffect(Vec3d.unpackRgb(0x191B20).toVector3f(), 0.5f);
	private static final float EMPTY = 0.8125f;
	private static final float FULL = 0.9375f;
	private static final float OFFSET = 0.7685f;
	private static final int MAX_FUEL = 8000;
	private static final int MAX_TEMP = 2000;
	
	private List<ArmorStandEntity> coals;
	private KingdomsItem result;
	private float height;
	private int stackMeltingPoint;
	
	private List<String> uuids;
	private float stackTemp;
	private int maxTemp;
	private int temp;
	private int fuel;

	public ForgeBlockEntity(BlockPos pos, BlockState state)
	{
		super(KingdomsServer.FORGE_BLOCK_ENTITY, pos, state, ForgeBehavior.DEFAULTS);
		setHeight(EMPTY - OFFSET);
	}

	@Override
	public void readNbt(NbtCompound nbt)
	{
		super.readNbt(nbt);
		uuids = nbt.getList("Coals", NbtElement.STRING_TYPE).stream().map(NbtElement::asString).collect(Collectors.toList());
		stackTemp = nbt.getFloat("StackTemp");
		maxTemp = nbt.getInt("MaxTemp");
		temp = nbt.getInt("Temp");
		fuel = nbt.getInt("Fuel");
	}

	@Override
	public void writeNbt(NbtCompound nbt)
	{
		super.writeNbt(nbt);
		NbtList list = new NbtList();
		uuids.stream().map(NbtString::of).forEach(list::add);
		nbt.put("Coals", list);
		nbt.putFloat("StackTemp", stackTemp);
		nbt.putInt("MaxTemp", maxTemp);
		nbt.putInt("Temp", temp);
		nbt.putInt("Fuel", fuel);
	}
	
	@Override
	public void add()
	{
		coals = new ArrayList<>();
		uuids = new ArrayList<>();
		spawnCoals(Direction.NORTH, Direction.EAST);
		spawnCoals(Direction.NORTH, Direction.WEST);
		spawnCoals(Direction.SOUTH, Direction.EAST);
		spawnCoals(Direction.SOUTH, Direction.WEST);
		super.add();
	}

	private void spawnCoals(Direction directionZ, Direction directionX)
	{
		double x = pos.getX() + (directionX.getOffsetX() * 0.2185d) + 0.5d;
		double y = pos.getY() + height;
		double z = pos.getZ() + (directionZ.getOffsetZ() * 0.2185d) + 0.5d;
		ArmorStandEntity entity = new ArmorStandEntity(world, x, y, z);
		coals.add(entity);
		uuids.add(entity.getUuidAsString());
		NbtCompound nbt = new NbtCompound();
		nbt.putBoolean("NoBasePlate", true);
		nbt.putBoolean("NoInteraction", true);
		nbt.putBoolean("Small", true);
		entity.readCustomDataFromNbt(nbt);
		entity.setInvisible(true);
		entity.setInvulnerable(true);
		entity.setNoGravity(true);
		entity.setSilent(true);
		entity.setYaw(180.0f);
		entity.setHeadRotation(new EulerAngle(180.0f, 0.0f, 0.0f));
		entity.setLeftLegRotation(new EulerAngle(180.0f, 0.0f, 0.0f));
		entity.setRightLegRotation(new EulerAngle(180.0f, 0.0f, 0.0f));
		entity.equipStack(EquipmentSlot.HEAD, new ItemStack(Blocks.DEEPSLATE_TILES));
		world.spawnEntity(entity);
	}

	@Override
	public void remove()
	{
		super.remove();

		for (ArmorStandEntity coal : coals)
			coal.remove(RemovalReason.DISCARDED);
		
		world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
	}

	@Override
	public void tick()
	{
		super.tick();
		
		if (coals == null)
		{
			coals = world.getEntitiesByClass(ArmorStandEntity.class, new Box(pos), entity -> uuids.contains(entity
					.getUuidAsString()));
		}
		
		//TODO REMOVE
		//for (int i = 0; i < 20; i++)
		//	updateSmelting();
		
		if (updateFuelAndTemp() | updateSmelting())
			markDirty();
		
		if (!getTableStack().isEmpty())
			for (PlayerEntity player : world.getPlayers())
				player.sendMessage(Color.RED.on("%d°C (%d°C)", temp, (int) stackTemp), true);
	}

	private boolean updateFuelAndTemp()
	{
		if (!getTableStack().isEmpty() && fuel > 0) //Heating
		{
			if (--fuel == 0)
				playStateChange(Blocks.DEEPSLATE_TILES, Blocks.AIR);
			else if (temp < maxTemp && temp++ == 0)
				playStateChange(Blocks.MAGMA_BLOCK, Blocks.LIGHT);
			
			updateHeight();
			randomDisplayTick(world.random);
			return true;
		}
		else if (temp > 0) //Cooling
		{
			if (fuel == 0)
				temp = 0;
			else if (--temp == 0)
				playStateChange(Blocks.BLACK_CONCRETE_POWDER, Blocks.AIR);
			
			randomDisplayTick(world.random);
			return true;
		}
		else
			return false;
	}
	
	private boolean updateSmelting()
	{
		if (getTableStack().isEmpty())
			return false;
		
		if (fuel > 0 && stackTemp <= maxTemp)
		{
			if ((stackTemp += (float) temp / stackMeltingPoint) > maxTemp)
				stackTemp = maxTemp;
			
			if (result != null && stackTemp >= stackMeltingPoint)
			{
				setTableStack(result.asStack());
				playSound(SoundEvents.ITEM_BUCKET_FILL_LAVA, 1.0f, 1.0f);
			}
			
			return true;
		}
		else if (stackTemp > 0.0f)
		{
			if (stackTemp-- < 0.0f)
				stackTemp = 0.0f;
			
			return true;
		}
		else
			return false;
	}

	@Override
	protected void placeOnTable(PlayerEntity player, ItemStack stack)
	{
		stackTemp = KingdomsStack.of(stack).getTemperature(world);
		super.placeOnTable(player, stack);
		markDirty();
		playSound(SoundEvents.BLOCK_ANCIENT_DEBRIS_PLACE, 1.0f, 1.0f);
	}
	
	@Override
	protected void pickupFromTable(PlayerEntity player, ItemStack stack)
	{
		KingdomsStack.of(stack).setTemperature(world, (int) stackTemp);
		super.pickupFromTable(player, stack);
		stackTemp = 0.0f;
		markDirty();
		playSound(SoundEvents.BLOCK_ANCIENT_DEBRIS_HIT, 1.0f, 1.0f);
	}

	private void playStateChange(Block block, Block blockUp)
	{
		world.setBlockState(pos.up(), blockUp.getDefaultState());
		showBlock(block);
		spawnParticles(ParticleTypes.SMALL_FLAME, 0.0f, 0.0f, 0.0f, 0.1f, 100);
		playSound(SoundEvents.ENTITY_ZOMBIE_INFECT, 1.0f, 0.0f);
	}
		
	private void randomDisplayTick(Random random)
	{
		if (random.nextInt(20) == 0)
			spawnParticles(ParticleTypes.SMALL_FLAME, 0.15d, 0.0d, 0.15d, 0.0d, random.nextInt(10) + 5);

		if (random.nextInt(20) == 0)
            playSound(SoundEvents.BLOCK_CAMPFIRE_CRACKLE, random.nextFloat() + 0.5f, random.nextFloat() * 0.7f + 0.6f);
	}
	
	protected ActionResult addFuel(PlayerEntity player, ItemStack stack, int amount, float potential)
	{
		int increasedFuel = fuel + amount;
		
		if (increasedFuel > MAX_FUEL)
			return ActionResult.FAIL;
		
		if (!player.getAbilities().creativeMode)
			stack.decrement(1);
		
		if (fuel == 0)
			showBlock(Blocks.BLACK_CONCRETE_POWDER);
		
		maxTemp = Math.round((float) (fuel * maxTemp + amount * potential * MAX_TEMP) / increasedFuel);
		fuel = increasedFuel;
		markDirty();
		updateHeight();
		updateStack(getTableStack());
		spawnParticles(COAL_DUST_PARTICLE, 0.15d, 0.0d, 0.15d, 0.0d, 10);
		playSound(SoundEvents.BLOCK_DEEPSLATE_BRICKS_BREAK, 1.0f, 1.0f);
		return ActionResult.SUCCESS;
	}
	
	@Override
	protected void updateStack(ItemStack stack)
	{
		Optional<KingdomsItem> optional = KingdomsStack.of(stack).getKingdomsItem();
		Optional<KingdomsItem> resultOptional = optional.map(item -> (maxTemp < Metal.STEEL.getMeltingPoint()
				? ForgeRecipe.LIGNITE
				: ForgeRecipe.BITUMINOUS_COAL).get(item));
		
		result = resultOptional.orElse(null);
		stackMeltingPoint = resultOptional.filter(MetalItem.class::isInstance).or(() -> optional
				.filter(MetalItem.class::isInstance)).map(item -> ((MetalItem) item).getMetal().getMeltingPoint())
				.orElse(0);
	}
	
	@Override
	protected void updateHeight()
	{
		setHeight(((float) fuel / MAX_FUEL) * (FULL - EMPTY) + EMPTY - OFFSET);
		
		super.updateHeight();
		
		for (ArmorStandEntity coal : coals)
			coal.setPosition(coal.getPos().withAxis(Axis.Y, pos.getY() + height));
	}
		
	@Override
	protected void setHeight(float height)
	{
		this.height = height;
		super.setHeight(height - 0.026f);
	}

	private void showBlock(Block block)
	{
		for (ArmorStandEntity coal : coals)
			coal.equipStack(EquipmentSlot.HEAD, new ItemStack(block));
	}
	
	private <T extends ParticleEffect> void spawnParticles(T particle, double deltaX, double deltaY, double deltaZ, 
			double speed, int count)
	{
		double x = pos.getX() + 0.5d;
		double y = pos.getY() + height + OFFSET;
		double z = pos.getZ() + 0.5d;
		((ServerWorld) world).spawnParticles(particle, x, y, z, count, deltaX, deltaY, deltaZ, speed);
	}
}
