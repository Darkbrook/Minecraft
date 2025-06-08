package com.darkbrook.kingdoms.server.block.anvil;

import java.util.Optional;

import com.darkbrook.kingdoms.KingdomsServer;
import com.darkbrook.kingdoms.common.util.Color;
import com.darkbrook.kingdoms.server.block.table.TableBlockEntity;
import com.darkbrook.kingdoms.server.item.KingdomsItem;
import com.darkbrook.kingdoms.server.item.KingdomsStack;
import com.darkbrook.kingdoms.server.item.items.MetalItem;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public class AnvilBlockEntity extends TableBlockEntity<AnvilBlockEntity>
{
	private AnvilRecipe anvilRecipe;
	private float stackMeltingPoint;
	private float stackTemp;
	private float progress;
	
	public AnvilBlockEntity(BlockPos pos, BlockState state)
	{
		super(KingdomsServer.ANVIL_BLOCK_ENTITY, pos, state, AnvilBehavior.DEFAULTS);
	}
	
	@Override
	public void readNbt(NbtCompound nbt)
	{
		super.readNbt(nbt);
		stackTemp = nbt.getFloat("StackTemp");
		progress = nbt.getFloat("Progress");
	}

	@Override
	public void writeNbt(NbtCompound nbt)
	{
		super.writeNbt(nbt);
		nbt.putFloat("StackTemp", stackTemp);
		nbt.putFloat("Progress", progress);
	}
	
	@Override
	public void tick()
	{
		super.tick();
		
		if (stackTemp > 0.0f)
		{
			if (stackTemp-- < 0.0f)
				stackTemp = 0.0f;
			
			markDirty();
		}
		
		if (!getTableStack().isEmpty())
			for (PlayerEntity player : world.getPlayers())
				player.sendMessage(Color.RED.on("%d°C (%.2f%%)", (int) stackTemp,progress * 100.0f), true);
	}
	
	public ActionResult hit(PlayerEntity player, Hand hand, ItemStack stack)
	{
		if (anvilRecipe == null || KingdomsStack.of(stack).isBroken())
			return ActionResult.PASS;

		float cooldown = player.getAttackCooldownProgress(0.5f);

		if (cooldown < 0.1f)
			return ActionResult.PASS;
		
		Optional<Integer> optional = KingdomsStack.of(stack).getKingdomsItem().map(AnvilRecipe.TOOL_FORCE::get);

		if (optional.isPresent())
		{
			float toolForce = Math.min((float) optional.get() / anvilRecipe.getToolForce(), 1.0f);
			float malleability = stackTemp / stackMeltingPoint;
			float hitForce = toolForce * malleability * cooldown * cooldown / anvilRecipe.getForce();
			
			if (hitForce == 0.0f)
				return ActionResult.PASS;
			
			if ((progress += hitForce) >= 1.0f)
			{
				progress = 0.0f;
				setTableStack(anvilRecipe.getResultStack(this));
				playSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0f, 0.0f);
			}
			
			stack.damage(1, player.getRandom(), (ServerPlayerEntity) player);
			markDirty();
			playSound(SoundEvents.BLOCK_ANVIL_PLACE, 1.0f, (1.0f - malleability / 2.0f) + 1.0f);
		}

		return ActionResult.PASS;
	}

	@Override
	protected void updateStack(ItemStack stack)
	{		
		Optional<KingdomsItem> optional = KingdomsStack.of(stack).getKingdomsItem();
		anvilRecipe = optional.map(AnvilRecipe.DEFAULTS::get).orElse(null);
		stackMeltingPoint = optional.filter(MetalItem.class::isInstance).map(metal -> ((MetalItem) metal).getMetal()
				.getMeltingPoint()).orElse(0);
	}
	
	@Override
	protected void placeOnTable(PlayerEntity player, ItemStack stack)
	{
		KingdomsStack kingdomsStack = KingdomsStack.of(stack);
		stackTemp = kingdomsStack.getTemperature(world);
		progress = kingdomsStack.getSmithingProgress();
		super.placeOnTable(player, stack);
		markDirty();
		playSound(SoundEvents.BLOCK_NETHERITE_BLOCK_PLACE, 1.0f, 1.0f);
	}
	
	@Override
	protected void pickupFromTable(PlayerEntity player, ItemStack stack)
	{
		KingdomsStack kingdomsStack = KingdomsStack.of(stack);
		kingdomsStack.setTemperature(world, (int) stackTemp);
		kingdomsStack.setSmithingProgress(progress);
		super.pickupFromTable(player, stack);
		stackTemp = 0.0f;
		progress = 0.0f;
		markDirty();
		playSound(SoundEvents.BLOCK_NETHERITE_BLOCK_HIT, 1.0f, 1.0f);
	}
	
	public static void registerEvents()
	{
		AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) ->
		{
	    	BlockEntity entity = world.getBlockEntity(pos);
	    	
	    	if (!(entity instanceof AnvilBlockEntity))
	    		return ActionResult.PASS;
	    	
	    	return ((AnvilBlockEntity) entity).hit(player, hand, player.getStackInHand(hand));
		});
	}
}
