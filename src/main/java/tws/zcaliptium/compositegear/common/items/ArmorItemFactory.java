/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import com.google.gson.JsonObject;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.crafting.JsonContext;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.lib.IItemFactory;

import static com.google.common.collect.Lists.newArrayList;

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
		if (slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND) {
			throw new IllegalArgumentException("Invalid slot '" + slotIn + "'. It is not armor slot!");
		}

		ItemCGArmor item = new ItemCGArmor(id, ItemArmor.ArmorMaterial.LEATHER, 0, slot);

		// Durability.
		int durability = MathHelper.clamp(JsonUtils.getInt(json, "durability", 64), 0, Integer.MAX_VALUE);
		if (durability > 0) {
			item.setMaxDamage(durability);			
		}

		// Enchantability.
		int enchantability = JsonUtils.getInt(json, "enchantability", 0);
		if (enchantability > 0) {
			// TODO: Expand item class to apply this setting.
		}
		
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

		// Only client need model info.
		if (CompositeGear.proxy.isClient()) {
			JsonObject modelObj = JsonUtils.getJsonObject(json, "model");
			parseModel(modelObj, item);
		
			// Armor model.
			JsonObject armorModel = JsonUtils.getJsonObject(json, "armorModel");
			String armorName = JsonUtils.getString(armorModel, "armorName");
			item.setArmorName(armorName);
		}

		return item;
	}
}
