/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.item.Item;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.OreDictionary;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.lib.IItemFactory;

public class GenericItemFactory implements IItemFactory
{
	@Override
	public Item parse(JsonContext context, JsonObject json)
	{
		String id = JsonUtils.getString(json, "id");

		ItemCG item = new ItemCG(id);

		JsonObject intelligenceObj = JsonUtils.getJsonObject(json, "intelligence", null);
		
		// Localized name & Description.
		if (intelligenceObj != null)
		{
			String unlocalized = JsonUtils.getString(intelligenceObj, "unlocalizedName", null);
			
			if (unlocalized != null) {
				item.setUnlocalizedName(unlocalized);
			}
			
			boolean hasDescription = JsonUtils.getBoolean(intelligenceObj, "hasDescription", false);
			
			item.setHasDescription(hasDescription); // Description.
		}
		
		JsonArray oreDictNamesObj = JsonUtils.getJsonArray(json, "oreDictNames", null);
		
		if (oreDictNamesObj != null)
		{
			Iterator<JsonElement> it = oreDictNamesObj.iterator();
			while (it.hasNext())
			{
				String name = it.next().getAsString();
				CompositeGear.modLog.info("QQQ " + name);
				OreDictionary.registerOre(name, item);
			}
		}
		
		// Only client need model info.
		if (CompositeGear.proxy.isClient())
		{
			JsonObject modelObj = JsonUtils.getJsonObject(json, "model");
			String type = JsonUtils.getString(modelObj, "type");

	        if (type.isEmpty())
	            throw new JsonSyntaxException("Item model type can not be an empty string");
	        
	        if (type.equalsIgnoreCase("single_item")) {
	        	String path = JsonUtils.getString(modelObj, "path");
	        	
	        	ItemsCG.registerItemModel(item, path);

	        } else if (type.equalsIgnoreCase("multiple_items")) {
	        	String variant = JsonUtils.getString(modelObj, "variant");
	        	String path = JsonUtils.getString(modelObj, "path");
	        	
	        	ItemsCG.registerMultiItem(item, variant, path);
	        } else {
	        	throw new JsonSyntaxException("Unknown item model type.");        	
	        }
		}

		return item;
	}
}
