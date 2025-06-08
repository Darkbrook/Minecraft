package com.darkbrook.library.blueprint;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.darkbrook.library.block.MemoryBlock;
import com.darkbrook.library.blueprint.base.BlueprintFile;
import com.darkbrook.library.blueprint.selection.BlockSelectionHandler;
import com.darkbrook.library.compressed.CompressedParticle;
import com.darkbrook.library.compressed.CompressedSound;
import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;
import com.google.common.collect.Lists;

public class Blueprint extends BlueprintFile {
	
	public static final CompressedSound BLUEPRINT_SOUND = new CompressedSound(Sound.ENTITY_ENDERMEN_TELEPORT, 0.5F, 0.0F);
	public static final CompressedParticle BLUEPRINT_PARTICLE = new CompressedParticle(Particle.SPELL_MOB_AMBIENT, 0.2F, 0.3F, 0.2F, 10);
	
	public static void playBlueprintEffects(Location location) {		
		Blueprint.BLUEPRINT_SOUND.play(location);
		Blueprint.BLUEPRINT_PARTICLE.play(location);
	}
	
	private Location location;
	
	public Blueprint(String name) {
		super(name);
	}
	
	public boolean create(Player player) {
		if(this.exists() || !BlockSelectionHandler.hasSelection(player)) return false;
		this.create(BlockSelectionHandler.getSelection(player), player.getLocation());
		return true;
	}
	
	public boolean createFromClipboard(Player player) {
		Blueprint clipboard = BlockSelectionHandler.getClipboard(player);
		if(this.exists() || clipboard == null) return false;
		this.create(clipboard.getRawBlocks());
		return true;
	}
	
	public void load() {
		this.internalLoad(this);
	}
	
	public void loadFromSelection(Player player) {
		this.internalMemoryLoad(player);
	}

	private void asyncSyncListPlace(List<MemoryBlock> blocks, World world, int delay) {
		
		UpdateHandler.delay(new UpdateListener() {

			@Override
			public void onUpdate() {
				for(MemoryBlock block : blocks) block.setBlockAt(new Location(world, block.getBlockX(), block.getBlockY(), block.getBlockZ()));
			}
			
		}, delay);
		
	}

	private void asyncSyncListPlace(List<MemoryBlock> blocks, World world, Material material, int data, int delay) {
		
		UpdateHandler.delay(new UpdateListener() {

			@Override
			public void onUpdate() {
				
				for(MemoryBlock block : blocks) {
					block.setType(material);
					block.setData(data);
					block.setBlockAt(new Location(world, block.getBlockX(), block.getBlockY(), block.getBlockZ()));
				}
				
			}
			
		}, delay);
		
	}
	
	public boolean paste(Location location, boolean quickPaste) {
		
		if(!this.isLoaded()) return false;
		
		if(quickPaste) {
			for(MemoryBlock block : this.getBlocksWithOffset(location)) block.setBlockAt(new Location(location.getWorld(), block.getBlockX(), block.getBlockY(), block.getBlockZ()));
		} else {
		
			List<List<MemoryBlock>> blocks = Lists.partition(this.getBlocksWithOffset(location), 4096);
			int delay = 1;

			for(List<MemoryBlock> block : blocks) {
				asyncSyncListPlace(block, location.getWorld(), delay);
				delay++;
			}
		
		}
		
		return true;
		
	}
	
	public boolean paste(Location location, Material material, int data, boolean quickPaste) {
		
		if(!this.isLoaded()) return false;
		
		if(quickPaste) {
			
			for(MemoryBlock block : this.getBlocksWithOffset(location)) {
				block.setType(material);
				block.setData(data);
				block.setBlockAt(new Location(location.getWorld(), block.getBlockX(), block.getBlockY(), block.getBlockZ()));
			}
			
		} else {
		
			List<List<MemoryBlock>> blocks = Lists.partition(this.getBlocksWithOffset(location), 4096);
			int delay = 1;

			for(List<MemoryBlock> block : blocks) {
				asyncSyncListPlace(block, location.getWorld(), material, data, delay);
				delay++;
			}
		
		}
		
		return true;
		
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}
	
}
