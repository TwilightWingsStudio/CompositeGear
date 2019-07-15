/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.item.Item;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.crafting.JsonContext;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.init.ModItems;

public class CrossbowItemFactory extends GenericItemFactory
{
	@Override
	public Item parse(JsonContext context, JsonObject json)
	{
		String id = JsonUtils.getString(json, "id");
		JsonArray attributes = JsonUtils.getJsonArray(json, "attributes", null);

		ItemCGCrossbow item = new ItemCGCrossbow(id);

		// Attributes.
		if (attributes != null)
		{
			parseAttributes(attributes, item);
		}

		return item;
	}
	
	@Override
	protected void parseAttribute(JsonObject json, Item item, String type)
	{
		ItemCGCrossbow rangedItem = (ItemCGCrossbow)item;
		
		if (type.equals("intelligence")) {
			parseIntelligence(json, item);

		} else if (type.equals("material")) {
			parseMaterial(json, rangedItem);
			
		} else if (type.equals("durability")) {
			int durability = MathHelper.clamp(JsonUtils.getInt(json, "durability", 	1), 0, Integer.MAX_VALUE);
			if (durability > 0) {
				item.setMaxDamage(durability);			
			}

		} else if (type.equals("model")) {
			// Only client need model info.
			if (CompositeGear.proxy.isClient()) {
				parseModel(json, item); // Item model.
			}
			
		} else {
			throw new IllegalArgumentException("Invalid ranged attribute type '" + type + "'.");	
		}
	}
	
	protected void parseMaterial(JsonObject json, ItemCGCrossbow item)
	{
		String materialType = JsonUtils.getString(json, "materialType");

		if (materialType.equalsIgnoreCase("generic")) {
			item.setEnchantability(JsonUtils.getInt(json, "enchantability", 0));
			
		} else {
			throw new IllegalArgumentException("Invalid armor material type '" + materialType + "'.");
		}
	}
}
