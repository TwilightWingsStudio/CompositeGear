/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
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

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.ModInfo;
import tws.zcaliptium.compositegear.common.init.ModItems;
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
		ModelResourceLocation model = parseModelEntry(json);
		ModelLoader.setCustomModelResourceLocation(item, 0, model);
		
		// GUI-only model.
		JsonObject modelObj = JsonUtils.getJsonObject(json, "gui", null);

		if (modelObj != null)
		{
			ModelResourceLocation model2 = parseModelEntry(modelObj);
			ModelBakery.registerItemVariants(item, model2);
			
			ModItems.GUI_MODELS_REGISTRY.put(model, model2);
		}
	}
	
	protected ModelResourceLocation parseModelEntry(JsonObject json)
	{
		// TODO: Here is fallback. Remove it in future.
		String type = JsonUtils.getString(json, "modelType", JsonUtils.getString(json, "type"));

        if (type.isEmpty())
            throw new JsonSyntaxException("Item model type can not be an empty string");
        
        // It is really simple. :)
        if (type.equalsIgnoreCase("simple")) {
        	String path = JsonUtils.getString(json, "path");

        	return new ModelResourceLocation(ModInfo.MODID + ":" + path, "inventory");

        // A little bit harder...
        } else if (type.equalsIgnoreCase("variant")) {
        	String path = JsonUtils.getString(json, "path");
        	String variant = JsonUtils.getString(json, "variant");

        	return new ModelResourceLocation(ModInfo.MODID + ":" + path, variant);

        // For lazy people.
        } else if (type.equalsIgnoreCase("variant_type")) {
        	String path = JsonUtils.getString(json, "path");
        	String variant = JsonUtils.getString(json, "variant");

        	return new ModelResourceLocation(ModInfo.MODID + ":" + path, "type=" + variant);        	

        } else {
        	throw new JsonSyntaxException("Unknown item model type '" + type + "'.");        	
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

	protected void parseAttribute(JsonObject json, Item item, String type)
	{
		throw new IllegalArgumentException("Invalid item feature type '" + type + "'.");
	}

	protected void parseAttributes(JsonArray json, Item item)
	{
		Iterator<JsonElement> featuresIt = json.iterator();
		while (featuresIt.hasNext())
		{
			JsonObject featureObj = featuresIt.next().getAsJsonObject();
			
			String featureType = JsonUtils.getString(featureObj, "type");
			
			parseAttribute(featureObj, item, featureType);
		}
	}
}
