package com.darkbrook.minez.command.minez;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.library.command.CommandListener;
import com.darkbrook.library.command.events.CommandHandler;
import com.darkbrook.library.command.events.OperatorCommandEvent;
import com.darkbrook.library.item.AttributeType;
import com.darkbrook.library.item.ItemHandler;
import com.darkbrook.library.item.SlotType;
import com.darkbrook.library.message.FormatMessage;
import com.darkbrook.library.message.MessageErrorType;

public class CommandMinez extends CommandListener {

	public CommandMinez() {
		super("minez");
	}
	
	@CommandHandler
	public void onOppedPlayer(OperatorCommandEvent event) {
		
		Player player = event.getCommandSender();
		String[] args = event.getArguments();
		
		if(args.length == 1) {
			
			ItemStack stack = player.getInventory().getItemInMainHand();
			
			if(args[0].equals("fixhand")) {
				
				if(stack == null) {
					FormatMessage.error(player, MessageErrorType.HAND_ITEM);
					return;
				}
				
				double damage = 0.0D;
				
				switch(stack.getType()) {
					case DIAMOND_SWORD: damage = 6.0D; break;
					case GOLD_SWORD: damage = 3.0D; break;
					case IRON_SWORD: damage = 5.0D; break;
					case STONE_SWORD: damage = 4.0D; break;
					case WOOD_SWORD: damage = 3.0D; break;
					default: damage = 0.0D; break;
				}
				
				if(damage > 0.0D) {
					stack = ItemHandler.setAttribute(stack, AttributeType.ATTACK_SPEED, 1024.0D, SlotType.MAINHAND);
					stack = ItemHandler.setAttribute(stack, AttributeType.ATTACK_DAMAGE, damage, SlotType.MAINHAND);
					stack = ItemHandler.addFlag(stack, ItemFlag.HIDE_ATTRIBUTES);
					player.getInventory().setItemInMainHand(stack);
					FormatMessage.info(player, "The item in your hand has been fixed.");
				} else {
					FormatMessage.error(player, MessageErrorType.HAND_ITEM);
				}
				
				return;
				
			} else if(args[0].equals("idhand")) {
				
				if(stack == null) {
					FormatMessage.error(player, MessageErrorType.HAND_ITEM);
					return;
				}
				
				FormatMessage.info(player, "Hand Item Identity: " + stack.getType().toString().toLowerCase() + ":" + stack.getDurability());
				return;
				
			} 
			
		} 
		
		FormatMessage.usage(player, "minez", new String[]{"fixhand", "idhand"});
		
		
	}

}
