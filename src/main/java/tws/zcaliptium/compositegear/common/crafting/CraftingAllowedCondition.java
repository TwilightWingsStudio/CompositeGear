/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.crafting;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;

import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.Loader;
import tws.zcaliptium.compositegear.common.ConfigurationCG;

public class CraftingAllowedCondition implements IConditionFactory
{
	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json)
	{
		String key = JsonUtils.getString(json, "key");
		
		// TODO: Add better check here for non-existing keys.

		return () -> ConfigurationCG.CRAFTING_RECIPES.getOrDefault(key, true);
	}
}
