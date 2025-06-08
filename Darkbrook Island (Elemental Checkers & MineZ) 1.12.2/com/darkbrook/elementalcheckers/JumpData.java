package com.darkbrook.elementalcheckers;

import java.util.HashMap;

public class JumpData {

	private static final HashMap<ElementalCheckersPlayer, HashMap<Integer, JumpData>> DATA = new HashMap<ElementalCheckersPlayer, HashMap<Integer, JumpData>>();
	
	public int clientSlot;
	public int opponentSlot;
	public JumpReason reason;
	
	public JumpData(int clientSlot, int opponentSlot, JumpReason reason) {
		this.clientSlot = clientSlot;
		this.opponentSlot = opponentSlot;
		this.reason = reason;
	}
	
	public static void setJumpData(ElementalCheckersPlayer player, int slot, JumpData jumpData) {
		HashMap<Integer, JumpData> data = DATA.containsKey(player) ? DATA.get(player) : new HashMap<Integer, JumpData>();
		data.put(slot, jumpData);
		DATA.put(player, data);
	}
	
	public static JumpData getJumpData(ElementalCheckersPlayer player, int slot) {
		return DATA.containsKey(player) && DATA.get(player).containsKey(slot) ? DATA.get(player).get(slot) : null;
	}
	
	public static boolean hasJumpData(ElementalCheckersPlayer player, int slot) {
		return getJumpData(player, slot) != null;
	}
	
	public static void update(ElementalCheckersPlayer player, int slot) {
				
		if(hasJumpData(player, slot)) {
			
			ElementalCheckers ec = player.ec;
			JumpData jumpData = getJumpData(player, slot);
			
			switch(jumpData.reason) {
				case ATTACK:
					ElementalCheckersPiece attacker = ElementalCheckersPiece.getPieceFromMapping(player, ec.selectedSlot);
					ElementalCheckersPiece enemy = ElementalCheckersPiece.getPieceFromMapping(ec.getOpposite(player.player), jumpData.opponentSlot);
					enemy.damage(ec.getOpposite(player.player), jumpData.opponentSlot, attacker);
				break;
				case VOID:
					ElementalCheckersPiece piece = ElementalCheckersPiece.getPieceFromMapping(player, ec.selectedSlot);
					piece.voidDeath(player, slot, ec.selectedSlot, true);
				break;
			}
			
		}
		
	}
	
	public static void resetJumpData(ElementalCheckersPlayer player) {
		if(DATA.containsKey(player)) DATA.remove(player);
	}
	
}
