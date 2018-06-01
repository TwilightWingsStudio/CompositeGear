/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.ModInfo;
import tws.zcaliptium.compositegear.lib.IItemIntelligence;

public class ItemCG extends Item implements IItemIntelligence
{
	protected boolean hasDescription;

    public ItemCG(String id)
    {
		setUnlocalizedName(id);
		
		hasDescription = false;
    	
		ItemsCG.registerItem(this, new ResourceLocation(ModInfo.MODID, id)); // Put into registry.

		if (CompositeGear.ic2Tab != null) {
			setCreativeTab(CompositeGear.ic2Tab);
		}
    }

	@Override
	public EnumItemClass getItemClass()
	{
		return EnumItemClass.NO_CLASS;
	}

	@Override
	public boolean hasDescription()
	{
		return this.hasDescription;
	}

	@Override
	public boolean hasVisualAttributes()
	{
		return false;
	}
	
	@Override
	public void setRarity(EnumRarity rarity) {}
	
	@Override
	public void setItemClass(EnumItemClass itemClass) {}

	@Override
	public void setHasDescription(boolean hasDescription)
	{
		this.hasDescription = hasDescription;
	}

	@Override
	public void setHasVisualAttributes(boolean hasVisualAttributes) {}
}
