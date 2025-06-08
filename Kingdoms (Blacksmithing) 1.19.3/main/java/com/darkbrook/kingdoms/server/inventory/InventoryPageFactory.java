package com.darkbrook.kingdoms.server.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.darkbrook.kingdoms.common.item.ItemStackConvertable;
import com.darkbrook.kingdoms.common.item.nbt.DisplayNameData;
import com.darkbrook.kingdoms.common.item.nbt.SkullOwnerData;
import com.darkbrook.kingdoms.common.util.Color;
import com.darkbrook.kingdoms.server.item.KingdomsItem;
import com.darkbrook.kingdoms.server.item.KingdomsItem.Data;

import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class InventoryPageFactory
{
	public static final ItemStack NEXT_PAGE = new KingdomsItem("next_page", Items.PLAYER_HEAD, Data
			.of(new DisplayNameData(Color.WHITE.on("Next Page")), new SkullOwnerData("MHF_ArrowRight")))
			.asStack();

	public static final ItemStack PREVIOUS_PAGE = new KingdomsItem("previous_page", Items.PLAYER_HEAD, Data
			.of(new DisplayNameData(Color.WHITE.on("Previous Page")), new SkullOwnerData("MHF_ArrowLeft")))
			.asStack();
    
    public static List<Inventory> createPageList(int slots, List<? extends ItemStackConvertable> stacks)
    {
    	Queue<? extends ItemStackConvertable> contents = new LinkedList<>(stacks);
    	
    	List<Inventory> pages = new ArrayList<>(); 

		while (!contents.isEmpty())
			pages.add(createPage(pages.size(), new SimpleInventory(slots), contents));
		
		return Collections.unmodifiableList(pages);
    }
    
	public static Inventory createPage(int previousPages, Inventory page,
			Queue<? extends ItemStackConvertable> contents)
    {		
		for (int i = 0, s = page.size(); i < s; i++)
		{
			if (i == (s - 1) && contents.size() > 1)
				page.setStack(i, NEXT_PAGE);
			else if (i == (s - 9) && previousPages > 0)
				page.setStack(i, PREVIOUS_PAGE);
			else if (!contents.isEmpty())
				page.setStack(i, contents.poll().asStack());
		}
		
		return page;
    }
}
