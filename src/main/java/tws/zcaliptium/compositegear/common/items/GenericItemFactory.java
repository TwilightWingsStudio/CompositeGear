/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.item.Item;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.JsonContext;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.lib.IItemFactory;

public class GenericItemFactory implements IItemFactory
{
	@Override
	public Item parse(JsonContext context, JsonObject json)
	{
		String id = JsonUtils.getString(json, "id");
		String unlocalized = JsonUtils.getString(json, "unlocalizedName", null);
		
		ItemCG item = new ItemCG(id);
		
		if (unlocalized != null) {
			item.setUnlocalizedName(unlocalized);
		}
		
		// Only client need model info.
		if (CompositeGear.proxy.isClient())
		{
			JsonObject modelObj = JsonUtils.getJsonObject(json, "model");
			String type = JsonUtils.getString(modelObj, "type");

	        if (type.isEmpty())
	            throw new JsonSyntaxException("Item model type can not be an empty string");
	        
	        if (type.equalsIgnoreCase("multi")) {
	        	String name = JsonUtils.getString(modelObj, "name");
	        	String path = JsonUtils.getString(modelObj, "path");
	        	
	        	ItemsCG.registerMultiItem(item, name, path);
	        } else {
	        	throw new JsonSyntaxException("Unknown item model type.");        	
	        }
		}

		return item;
	}
}
