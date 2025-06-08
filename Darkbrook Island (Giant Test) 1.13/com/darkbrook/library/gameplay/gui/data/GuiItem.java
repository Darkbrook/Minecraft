package com.darkbrook.library.gameplay.gui.data;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.library.data.object.ObjectDataParsed;
import com.darkbrook.library.data.object.ObjectDataQueue;
import com.darkbrook.library.gameplay.itemstack.DarkbrookItemStack;


public class GuiItem extends ObjectDataParsed<ItemStack>
{

	public GuiItem(String identity) 
	{
		super(identity);
	}

	@Override
	protected ItemStack onParsedValueLoad() 
	{
		ObjectDataQueue data = getData("type/size/dura/name/glow", "barrier", 1, 0, null, false);
		ObjectDataQueue lore = getDataOrArray("lore");
		
		DarkbrookItemStack stack = new DarkbrookItemStack(data.s(), data.i(), data.i()).openMeta();
		String name = data.s();
		
		if(name != null)
		{
			stack.setName(name);
		}
		
		if(lore != null)
		{
			stack.setLore(lore.queue().toList());
		}
		
		if(data.b())
		{
			stack.addEnchantment(Enchantment.DURABILITY, 1, false);
		}

		return stack.applyMeta();
	}
	
}
