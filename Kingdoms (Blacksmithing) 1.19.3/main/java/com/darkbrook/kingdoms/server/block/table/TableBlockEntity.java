package com.darkbrook.kingdoms.server.block.table;

import java.util.Map;

import com.darkbrook.kingdoms.server.item.KingdomsItem;
import com.darkbrook.kingdoms.server.item.KingdomsStack;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.EulerAngle;

public abstract class TableBlockEntity<T extends TableBlockEntity<T>> extends BlockEntity
{
	private final Map<KingdomsItem, ? extends TableBehavior<T>> behaviorMap;
	private float height = 0.2315f;
	private ArmorStandEntity table;
	private String uuid;
	
	public TableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, 
			Map<KingdomsItem, ? extends TableBehavior<T>> behaviorMap)
	{
		super(type, pos, state);
		this.behaviorMap = behaviorMap;
	}
	
	@Override
	public void readNbt(NbtCompound nbt)
	{
		uuid = nbt.getString("Table");
	}

	@Override
	public void writeNbt(NbtCompound nbt)
	{
		nbt.putString("Table", uuid);
	}
		
	public void add()
	{
		spawnTable(TableDirection.NORTH);
		markDirty();
	}

	@Override
	public void markDirty()
	{
		if (world != null)
			world.markDirty(pos);
	}
	
	private void spawnTable(TableDirection direction)
	{
		double x = pos.getX() + (direction.getOffset(Axis.X) * 0.062d) + 0.5d;
		double y = pos.getY() + height;
		double z = pos.getZ() + (direction.getOffset(Axis.Z) * 0.062d) + 0.5d;
		table = new ArmorStandEntity(world, x, y, z);
		uuid = table.getUuidAsString();
		NbtCompound nbt = new NbtCompound();
		nbt.putBoolean("NoBasePlate", true);
		nbt.putBoolean("NoInteraction", true);
		table.readCustomDataFromNbt(nbt);
		table.setInvisible(true);
		table.setInvulnerable(true);
		table.setNoGravity(true);
		table.setSilent(true);
		table.setYaw(direction.getRotation());
		table.setHeadRotation(new EulerAngle(180.0f, 0.0f, 0.0f));
		table.setLeftLegRotation(new EulerAngle(180.0f, 0.0f, 0.0f));
		table.setRightLegRotation(new EulerAngle(180.0f, 0.0f, 0.0f));
		table.setRightArmRotation(new EulerAngle(0.0f, 270.0f, 0.0f));
		world.spawnEntity(table);
	}

	public void remove()
	{
		table.remove(RemovalReason.DISCARDED);
	}

	public void tick()
	{
		if (table == null)
		{
			table = world.getEntitiesByClass(ArmorStandEntity.class, new Box(pos), entity -> uuid.equals(entity
					.getUuidAsString())).iterator().next();
			updateStack(getTableStack());
		}
	}	
	
	@SuppressWarnings("unchecked")
	public ActionResult interact(PlayerEntity player, Hand hand)
	{		
		if (hand == Hand.OFF_HAND)
			return ActionResult.FAIL;

		ItemStack stack = player.getStackInHand(hand);
		TableBehavior<T> behavior = KingdomsStack.of(stack).getKingdomsItem().map(behaviorMap::get).orElse(null);
		return (behavior != null ? behavior : behaviorMap.get(null)).interact((T) this, player, stack);
	}
	
	public ActionResult swapItems(PlayerEntity player, ItemStack stack)
	{
		ItemStack tableStack = getTableStack();
				
		if (tableStack.isEmpty())
		{
			if (stack.isEmpty())
				return ActionResult.FAIL;

			setDirection(player.getHorizontalFacing());
			setTableStack(stack.copyWithCount(1));
			placeOnTable(player, stack);
		}
		else
		{
			setTableStack(ItemStack.EMPTY);
			pickupFromTable(player, tableStack);
		}
		
		return ActionResult.SUCCESS;
	}
	
	protected void placeOnTable(PlayerEntity player, ItemStack stack)
	{		
		stack.decrement(1);
	}
	
	protected void pickupFromTable(PlayerEntity player, ItemStack stack)
	{
		player.giveItemStack(stack);
	}
	
	protected void setDirection(Direction direction)
	{
		TableDirection converted = TableDirection.of(direction);
		double x = pos.getX() + (converted.getOffset(Axis.X) * 0.062d) + 0.5d;
		double y = pos.getY() + height;
		double z = pos.getZ() + (converted.getOffset(Axis.Z) * 0.062d) + 0.5d;
		table.refreshPositionAndAngles(x, y, z, converted.getRotation(), 0.0f);
	}
		
	protected void updateHeight()
	{
		table.setPosition(table.getPos().withAxis(Axis.Y, pos.getY() + height));
	}
	
	protected void setHeight(float height)
	{
		this.height = height;
	}
	
	protected ItemStack getTableStack()
	{
		return table.getEquippedStack(EquipmentSlot.MAINHAND);
	}
	
	protected void setTableStack(ItemStack stack)
	{
		table.equipStack(EquipmentSlot.MAINHAND, stack);
		updateStack(stack);
	}
	
	protected void playSound(SoundEvent sound, float volume, float pitch)
	{
		world.playSound(null, pos, sound, SoundCategory.BLOCKS, volume, pitch);
	}
	
	protected abstract void updateStack(ItemStack stack);
}
