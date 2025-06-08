package com.darkbrook.island.library.item;

public enum SlotType {

	ALL("all"),
	HANDS("hands"),
	MAINHAND("mainhand"), 
	OFFHAND("offhand"),
	ARMOR("armor"),
	HELMET("head"),
	CHESTPLATE("chest"), 
	LEGGINGS("legs"), 
	BOOTS("feet");
	
	private String slot;
	
	private SlotType(String slot) {
		this.slot = slot;
	}
	
	public String getSlot() {
		return slot;
	}
	
	public boolean isSlot(String compare) {
		return slot.equals(compare);
	}
	
}
