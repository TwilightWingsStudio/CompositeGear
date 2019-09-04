/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.ModInfo;
import tws.zcaliptium.compositegear.common.init.ModItems;
import tws.zcaliptium.compositegear.lib.IAttributeHolder;

public class ItemCG extends Item implements IAttributeHolder
{
	protected Map<String, Object> attributes;

    public ItemCG(String id)
    {
		attributes = new HashMap<String, Object>();
    	
		setUnlocalizedName(id);
    	
		ModItems.registerItem(this, new ResourceLocation(ModInfo.MODID, id)); // Put into registry.

		if (CompositeGear.cgTab != null) {
			setCreativeTab(CompositeGear.cgTab);
		}
    }

	@Override
	public Map<String, Object> getAttributes()
	{
		return this.attributes;
	}
}
