package com.darkbrook.island.library.item;

public enum AttributeType {

	ARMOR("armor", 1), 
	ATTACK_DAMAGE("attackDamage", 2), 
	ATTACK_SPEED("attackSpeed", 3), 
	FOLLOW_RANGE("followRange", 4), 
	KNOCKBACK_RESISTANCE("knockbackResistance", 5), 
	LUCK("luck", 6), 
	MAX_HEALTH("maxHealth", 7),
	MOVEMENT_SPEED("movementSpeed", 8);
	
	private String attribute;
	private int UUID;
	
	private AttributeType(String attribute, int UUID)  {
		this.attribute = "generic." + attribute;
		this.UUID = UUID;
	}

	public String getAttribute() {
		return attribute;
	}
	
	public int getUUID() {
		return UUID;
	}
	
}
