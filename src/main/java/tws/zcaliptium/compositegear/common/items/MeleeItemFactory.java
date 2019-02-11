/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
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

public class MeleeItemFactory extends GenericItemFactory
{
	@Override
	public Item parse(JsonContext context, JsonObject json)
	{
		String id = JsonUtils.getString(json, "id");

		ItemCGMelee item = new ItemCGMelee(id, ItemCGMelee.GENERIC_MELEE_MATERIAL);
		
		// Durability.
		int durability = MathHelper.clamp(JsonUtils.getInt(json, "durability", 	1), 0, Integer.MAX_VALUE);
		if (durability > 0) {
			item.setMaxDamage(durability);
		}

		// Material.
		parseMaterial(JsonUtils.getJsonObject(json, "material"), item);

		// Features.
		JsonArray features = JsonUtils.getJsonArray(json, "features", null);

		if (features != null)
		{
			parseFeatures(features, item);
		}

		// Intelligence.
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
		}

		return item;
	}

	@Override
	protected void parseFeature(JsonObject json, Item item, String type)
	{
		ItemCGMelee meleeItem = (ItemCGMelee)item;

		if (type.equals("shield_disabler")) {
			meleeItem.setShieldDisabler(true);
			
		} else if (type.equals("colorable")) {
			meleeItem.setColorable(true);
			meleeItem.setDefaultColor(JsonUtils.getInt(json, "defaultColor", 16777215));
			ModItems.COLORABLE_REGISTRY.add(meleeItem);

		} else if (type.equals("constant_gem_damage")) {
			meleeItem.setConstantGemDamage(JsonUtils.getInt(json, "damage"));
		} else {
			throw new IllegalArgumentException("Invalid melee feature type '" + type + "'.");	
		}
	}

	protected void parseMaterial(JsonObject json, ItemCGMelee item)
	{
		String materialType = JsonUtils.getString(json, "type");

		if (materialType.equalsIgnoreCase("generic")) {
			item.setAttackDamage(JsonUtils.getFloat(json, "attackDamage", 0.0F));
			item.setAttackSpeed(JsonUtils.getFloat(json, "attackSpeed", 0.0F));
			item.setEnchantability(JsonUtils.getInt(json, "enchantability", 0));
		} else {
			throw new IllegalArgumentException("Invalid armor material type '" + materialType + "'.");
		}
	}
}
