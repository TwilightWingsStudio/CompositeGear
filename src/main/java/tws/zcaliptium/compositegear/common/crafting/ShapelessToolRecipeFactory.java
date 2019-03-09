/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ShapelessToolRecipeFactory implements IRecipeFactory
{
	@Override
	public IRecipe parse(JsonContext context, JsonObject json)
	{
		String group = JsonUtils.getString(json, "group", "");
		
		NonNullList<Ingredient> ings = NonNullList.create();
		
		for (JsonElement ele : JsonUtils.getJsonArray(json, "ingredients")) {
			ings.add(CraftingHelper.getIngredient(ele, context));
		}

        if (ings.isEmpty()) {
    		throw new JsonParseException("No ingredients for shapeless recipe");
        }

        ItemStack itemstack = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);
    	return new ShapelessToolRecipe(group.isEmpty() ? null : new ResourceLocation(group), ings, itemstack);
	}
}
