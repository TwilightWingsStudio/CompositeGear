/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import net.minecraft.util.text.translation.I18n;

public enum EnumItemClass
{
	NO_CLASS(""),
	ACCESSORY_ARMOR("compositegear.itemclass.accessoryarmor"),
	CLOTHING("compositegear.itemclass.clothing"),
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
