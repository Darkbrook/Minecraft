package com.darkbrook.darkbrookisland;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;

public class DarkbrookRank {
	
	private static final Map<Player, DarkbrookRank> PLAYER_MAPPING = new HashMap<Player, DarkbrookRank>();
	private static final Map<Player, Boolean> OPERATOR_MAPPING = new HashMap<Player, Boolean>();

	private static DarkbrookRank operator;
	private static DarkbrookRank regular;
	
	public static void scanOperatorRanks(DarkbrookRank operator, DarkbrookRank regular) {
		
		DarkbrookRank.operator = operator;
		DarkbrookRank.regular = regular;
		
		UpdateHandler.repeat(new UpdateListener() {

			@Override
			public void onUpdate() {
		
				for(Player player : Bukkit.getServer().getOnlinePlayers()) {
					
					if(!OPERATOR_MAPPING.containsKey(player) || OPERATOR_MAPPING.get(player) != player.isOp()) {						
						updateOperatorRank(player);
						DarkbrookIsland.updateOpStatus(player);
						OPERATOR_MAPPING.put(player, player.isOp());
					}
													
				}
				
			}
			
		}, 0, 20);
		
	}
	
	public static void updateOperatorRank(Player player) {
	
		if(operator == null || regular == null) {
			return;
		}
		
		if(player.isOp()) {
			operator.addPlayer(player);
		} else {
			regular.addPlayer(player);
		}
		
	}
	
	private Team rank;
	private String prefix;
	
	public DarkbrookRank(String prefix, String suffix, String id) {
		
		this.prefix = prefix;		
		
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();		
		if(scoreboard.getTeam(id) != null) scoreboard.getTeam(id).unregister();
		
		this.rank = scoreboard.registerNewTeam(id);
		this.rank.setPrefix(prefix);
		this.rank.setSuffix(suffix);
		this.rank.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
		
	}
	
	public void addPlayer(Player player) {
		
		String name = player.getName();
		rank.addEntry(name);
		player.setDisplayName(prefix + name);
		player.setPlayerListName(prefix + name);	
		
		PLAYER_MAPPING.put(player, this);
		
	}
	
	public void removePlayer(Player player) {
		
		String name = player.getName();
		rank.removeEntry(name);
		player.setDisplayName(name);
		player.setPlayerListName(name);	
		
		PLAYER_MAPPING.get(player).removePlayer(player);
		PLAYER_MAPPING.remove(player);
		
	}

}
