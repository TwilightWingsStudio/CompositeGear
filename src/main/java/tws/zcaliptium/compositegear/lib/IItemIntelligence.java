/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.lib;

import net.minecraft.item.EnumRarity;
import tws.zcaliptium.compositegear.common.items.EnumItemClass;

public interface IItemIntelligence
{
	// Getters
	public EnumItemClass getItemClass();
	public boolean hasDescription();
	public boolean hasVisualAttributes();
	
	// Setters
	public void setRarity(EnumRarity rarity);
	public void setItemClass(EnumItemClass itemClass);
	public void setHasDescription(boolean hasDescription);
	public void setHasVisualAttributes(boolean hasVisualAttributes);
}
