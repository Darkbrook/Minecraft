package com.darkbrook.elementalcheckers.replay;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.elementalcheckers.guis.GuiTypeElementalCheckersLog;
import com.darkbrook.library.compressed.CompressedSound;
import com.darkbrook.library.entity.PacketHandler;
import com.darkbrook.library.gui.Gui;
import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;

public class ElementalCheckersLog {
	
	private List<ElementalCheckersFrame> frames = new ArrayList<ElementalCheckersFrame>();
	private String endMessage;
	private String endSubMessage;
	
	public void addLog(ItemStack[] contents, CompressedSound sound, String message) {
		frames.add(new ElementalCheckersFrame(contents, sound, message));
	}
	
	private void playFrame(Player player, Gui gui, ElementalCheckersFrame frame, int delay) {
		
		UpdateHandler.delay(new UpdateListener() {

			@Override
			public void onUpdate() {
				gui.setContents(frame.contents);
				if(!frame.message.isEmpty()) player.sendMessage(frame.message);
				frame.sound.play(player, true);
				player.updateInventory();
			}
			
		}, delay);
		
	}
	
	private void endFrames(Player player, int delay) {
		
		UpdateHandler.delay(new UpdateListener() {

			@Override
			public void onUpdate() {
				player.closeInventory();
				PacketHandler.sendTitleMessage(player, endMessage);
				PacketHandler.sendSubTitleMessage(player, endSubMessage);
				player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, 1.0F, 2.0F);
			}
			
		}, delay);
		
	}
	
	public void play(Player player) {
		Gui gui = new Gui(new GuiTypeElementalCheckersLog(), ChatColor.DARK_GRAY + "Elemental Checkers Playback", 6);
		gui.open(player);
		for(int i = 0; i < frames.size(); i++) playFrame(player, gui, frames.get(i), i * 16);	
		endFrames(player, (frames.size() * 16) + 16);
	}
	
	public void end(String endMessage, String endSubMessage) {
		this.endMessage = endMessage;
		this.endSubMessage = endSubMessage;
	}

}
