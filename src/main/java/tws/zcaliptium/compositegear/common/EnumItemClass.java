package tws.zcaliptium.compositegear.common;

import net.minecraft.util.text.translation.I18n;

public enum EnumItemClass
{
	ACCESSORY_ARMOR("compositegear.itemclass.accessoryarmor"),
	LIGHT_ARMOR("compositegear.itemclass.lightarmor"),
	MEDIUM_ARMOR("compositegear.itemclass.mediumarmor"),
	HEAVY_ARMOR("compositegear.itemclass.heavyarmor"),
	MELEE_WEAPON("compositegear.itemclass.meleeweapon"),
	RANGED_WEAPON("compositegear.itemclass.rangedweapon");
	
	private String unlocalisedName;

	EnumItemClass(String unlocalisedName)
	{
		this.unlocalisedName = unlocalisedName;
	}
	
	public String getUnlocalized()
	{
		return this.unlocalisedName;
	}
	
	public String getLocalized()
	{
		return I18n.translateToLocal(this.unlocalisedName);
	}
}
