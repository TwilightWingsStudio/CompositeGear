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

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.lib.IItemFactory;
import tws.zcaliptium.compositegear.lib.IItemIntelligence;

public class GenericItemFactory implements IItemFactory
{	
	@Override
	public Item parse(JsonContext context, JsonObject json)
	{
		// Main stuff.
		String id = JsonUtils.getString(json, "id");
		int maxStackSize = MathHelper.clamp(JsonUtils.getInt(json, "maxStackSize", 64), 1, Integer.MAX_VALUE);

		ItemCG item = new ItemCG(id);
		item.setMaxStackSize(maxStackSize);

		JsonObject intelligenceObj = JsonUtils.getJsonObject(json, "intelligence", null);

		// Localized name & Description.
		if (intelligenceObj != null) {
			parseIntelligence(intelligenceObj, item);
		}
		
		JsonArray oreDictNamesObj = JsonUtils.getJsonArray(json, "oreDictNames", null);
		
		if (oreDictNamesObj != null)
		{
			Iterator<JsonElement> it = oreDictNamesObj.iterator();
			while (it.hasNext())
			{
				String name = it.next().getAsString();
				OreDictionary.registerOre(name, item);
			}
		}

		// Only client need model info.
		if (CompositeGear.proxy.isClient()) {
			JsonObject modelObj = JsonUtils.getJsonObject(json, "model");
			parseModel(modelObj, item);
		}

		return item;
	}
	
	@SideOnly(Side.CLIENT)
	protected void parseModel(JsonObject json, Item item)
	{
		String type = JsonUtils.getString(json, "type");

        if (type.isEmpty())
            throw new JsonSyntaxException("Item model type can not be an empty string");
        
        if (type.equalsIgnoreCase("single_item")) {
        	String path = JsonUtils.getString(json, "path");
        	
        	ItemsCG.registerItemModel(item, path);

        } else if (type.equalsIgnoreCase("multiple_items")) {
        	String variant = JsonUtils.getString(json, "variant");
        	String path = JsonUtils.getString(json, "path");
        	
        	ItemsCG.registerMultiItem(item, variant, path);
        } else {
        	throw new JsonSyntaxException("Unknown item model type.");        	
        }
	}
	
	protected void parseIntelligence(JsonObject json, Item item)
	{
		IItemIntelligence intelligence = (IItemIntelligence)item;
		
		String unlocalized = JsonUtils.getString(json, "unlocalizedName", null);
		
		if (unlocalized != null) {
			item.setUnlocalizedName(unlocalized);
		}

		String rarityName = JsonUtils.getString(json, "rarity", null);
		if (rarityName != null)
		{
			for (EnumRarity rarity : EnumRarity.values()) {
				if (rarity.rarityName.equals(rarityName)) {
					intelligence.setRarity(rarity);
					break;
				}
			}
		}

		String className = JsonUtils.getString(json, "class", null);
		if (className != null)
		{
			intelligence.setItemClass(EnumItemClass.valueOf(className));
		}

		boolean hasDescription = JsonUtils.getBoolean(json, "hasDescription", false);
		boolean hasVisualAttributes = JsonUtils.getBoolean(json, "hasVisualAttributes", false);

		intelligence.setHasDescription(hasDescription); // Description.
		intelligence.setHasVisualAttributes(hasVisualAttributes);
	}
}
