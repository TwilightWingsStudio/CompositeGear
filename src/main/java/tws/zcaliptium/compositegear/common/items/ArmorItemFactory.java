/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.crafting.JsonContext;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.lib.IItemFactory;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public class ArmorItemFactory extends GenericItemFactory
{
	@Override
	public Item parse(JsonContext context, JsonObject json)
	{
		// Main stuff.
		String id = JsonUtils.getString(json, "id");
		String slotIn = JsonUtils.getString(json, "slot");

		EntityEquipmentSlot slot = EntityEquipmentSlot.fromString(slotIn);
		
		// Filter non-armor slots.
		if (slot.getSlotType() != EntityEquipmentSlot.Type.ARMOR) {
			throw new IllegalArgumentException("Invalid slot '" + slotIn + "'. It is not armor slot!");
		}

		ItemCGArmor item = new ItemCGArmor(id, ItemsCG.GENERIC_MATERIAL, 0, slot);

		// Durability.
		int durability = MathHelper.clamp(JsonUtils.getInt(json, "durability", 	1), 0, Integer.MAX_VALUE);
		if (durability > 0) {
			item.setMaxDamage(durability);			
		}

		// Material
		JsonObject materialObj = JsonUtils.getJsonObject(json, "material");
		String materialType = JsonUtils.getString(materialObj, "type");

		if (materialType.equalsIgnoreCase("generic")) {
			item.setProtection(JsonUtils.getInt(materialObj, "protection", 0));
			item.setToughness(JsonUtils.getInt(materialObj, "toughness", 0));
			item.setEnchantability(JsonUtils.getInt(materialObj, "enchantability", 0));
		} else {
			throw new IllegalArgumentException("Invalid armor material type '" + materialType + "'.");
		}

		// Features
		JsonArray features = JsonUtils.getJsonArray(json, "features", null);
		
		if (features != null)
		{
			parseFeatures(features, item);
		}

		JsonObject intelligenceObj = JsonUtils.getJsonObject(json, "intelligence", null);
		
		// Localized name & Description.
		if (intelligenceObj != null)
		{
			parseIntelligence(intelligenceObj, item);
		}

		// Only client need model info.
		if (CompositeGear.proxy.isClient())
		{
			parseModel(JsonUtils.getJsonObject(json, "model"), item); // Item model.
			parseArmorModel(JsonUtils.getJsonObject(json, "armorModel"), item); // Armor model.
		}

		return item;
	}

	protected void parseArmorModel(JsonObject json, ItemCGArmor item)
	{
		item.setArmorName(JsonUtils.getString(json, "armorName"));
		item.setHasOverlay(JsonUtils.getBoolean(json, "hasOverlay", false));
	}

	protected void parseFeatures(JsonArray json, ItemCGArmor item)
	{
		Iterator<JsonElement> featuresIt = json.iterator();
		while (featuresIt.hasNext())
		{
			JsonObject featureObj = featuresIt.next().getAsJsonObject();
			
			String featureType = JsonUtils.getString(featureObj, "type");
			
			if (featureType.equals("air_mask")) {
				item.setAirMask(true);
				item.setMinAir(JsonUtils.getInt(featureObj, "minAir", 0));

			} else if (featureType.equals("colorable")) {
				item.setColorable(true);
				item.setDefaultColor(JsonUtils.getInt(featureObj, "defaultColor", 16777215));
				ItemsCG.COLORABLE_REGISTRY.add(item);

			} else if (featureType.equals("warm")) {
				item.setWarm(true);
			} else if (featureType.equals("satiety_save_cold")) {
				item.setSaveSatietyCold(true);
			} else if (featureType.equals("satiety_save_hot")) {
				item.setSaveSatietyHot(true);
			} else {
				throw new IllegalArgumentException("Invalid armor feature type '" + featureType + "'.");
			}
		}
	}
}
