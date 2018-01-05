/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.crafting;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import ic2.api.item.IC2Items;
import net.minecraft.util.JsonUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class IC2IngredientFactory implements IIngredientFactory
{
	@Override
	public Ingredient parse(JsonContext context, JsonObject json)
	{
		String name = JsonUtils.getString(json, "name");
		
		ItemStack stack = null;
		
		// If item with variant.
		if (name.contains("#")) {
			String[] parts = name.split("#");
			stack = IC2Items.getItem(parts[0], parts[1]);
		} else {
			stack = IC2Items.getItem(name);
		}
		
		if (stack == null)
			throw new JsonSyntaxException("IC2 item with name " + name + " could not be found");
		
		return Ingredient.fromStacks(stack);
	}
}
