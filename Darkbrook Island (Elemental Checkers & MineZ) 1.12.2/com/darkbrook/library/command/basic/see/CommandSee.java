package com.darkbrook.library.command.basic.see;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.darkbrook.library.command.CommandListener;
import com.darkbrook.library.command.events.CommandHandler;
import com.darkbrook.library.command.events.OperatorCommandEvent;
import com.darkbrook.library.entity.PlayerHandler;
import com.darkbrook.library.message.FormatMessage;

public class CommandSee extends CommandListener {

	public CommandSee() {
		super("see");
	}

	@CommandHandler
	public void onOppedCommand(OperatorCommandEvent event) {
		
		Player player = event.getCommandSender();
		
		if(event.getLength() == 0) {
			
			if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
				player.removePotionEffect(PotionEffectType.NIGHT_VISION);
				FormatMessage.info(player, "See disabled.");
			} else {
				PlayerHandler.addPotionEffect(player, new PotionEffect(PotionEffectType.NIGHT_VISION, 999999999, 0, false, false));
				FormatMessage.info(player, "See enabled.");
			}
		
		} else {
			FormatMessage.usage(player, "see", "");
		}
		
	}
	
}
