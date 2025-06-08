package com.darkbrook.kingdoms.server.inventory;

import java.util.List;
import java.util.Set;

import com.darkbrook.kingdoms.server.item.KingdomsItems;
import com.google.common.collect.ImmutableSet;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class BestowScreenHandler extends ScreenHandler
{		
	private static final List<Inventory> PAGES = InventoryPageFactory.createPageList(54, KingdomsItems.toList());
	private static final Set<SlotActionType> CREATIVE_ACTIONS = ImmutableSet.of(SlotActionType.PICKUP,
			SlotActionType.PICKUP_ALL, SlotActionType.CLONE);
	
	private final ProxyInventory inventory;
	private int pageIndex;

	public BestowScreenHandler(int id, PlayerInventory playerInventory)
	{
		super(ScreenHandlerType.GENERIC_9X6, id);
		inventory = new ProxyInventory(PAGES.get(pageIndex));
		init(6, playerInventory);
	}
	
	protected void init(int rows, PlayerInventory playerInventory)
	{
        int i = (rows - 4) * 18;

		for (int y = 0; y < rows; y++)
			for (int x = 0; x < 9; x++)
				addSlot(new BestowSlot(inventory, x + y * 9, x * 18 + 8, y * 18 + 18));

		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlot(new Slot(playerInventory, x + y * 9 + 9, x * 18 + 8, y * 18 + i + 103));

		for (int y = 0; y < 9; y++)
			addSlot(new Slot(playerInventory, y, y * 18 + 8, i + 161));
	}
	
	@Override
	public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player)
    {
		if (slotIndex > 0 && slotIndex < inventory.size())
		{
			ItemStack stack = inventory.getStack(slotIndex);
			
			if (turnPage(stack, player) || (CREATIVE_ACTIONS.contains(actionType) && simulateCreativePickup(stack,
					button)))
				return;
		}
		
		super.onSlotClick(slotIndex, button, actionType, player);
    }
	
	private boolean turnPage(ItemStack stack, PlayerEntity player)
	{
		if (stack == InventoryPageFactory.NEXT_PAGE)
			pageIndex++;
    	else if (stack == InventoryPageFactory.PREVIOUS_PAGE)
    		pageIndex--;
    	else
    		return false;
		
    	inventory.setInventory(PAGES.get(pageIndex));
		player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.MASTER, 1.0f, 1.0f);
		return true;
	}
	
	private boolean simulateCreativePickup(ItemStack stack, int button)
	{
		ItemStack cursorStack = getCursorStack();
		
		if (cursorStack.isEmpty())
			return false;
				
		switch (button)
		{
			case 0 ->
			{
				if (!ItemStack.canCombine(stack, cursorStack))
					setCursorStack(ItemStack.EMPTY);
				else if (cursorStack.getCount() < cursorStack.getMaxCount())
					cursorStack.increment(1);
			}
			case 1 ->
			{
				if (cursorStack.getCount() > 0)
					cursorStack.decrement(1);
			}
			case 2 -> 
				cursorStack.setCount(cursorStack.getMaxCount());
		}
		
		return true;
	}
	
	@Override
	public ItemStack quickMove(PlayerEntity player, int slotIndex)
	{
        Slot slot = slots.get(slotIndex);
        
        if (slot != null && slot.hasStack())
        {
            ItemStack stack = slot.getStack().copy();

        	if (slotIndex < inventory.size())
        	{
        		insertItem(stack, inventory.size(), slots.size(), true);
            	return stack;
        	}
        	else if (!stack.isEmpty())
        		slot.setStack(ItemStack.EMPTY);
        }

		return ItemStack.EMPTY;
	}

	@Override
	public boolean canUse(PlayerEntity player)
	{
		return inventory.canPlayerUse(player);
	}
}
