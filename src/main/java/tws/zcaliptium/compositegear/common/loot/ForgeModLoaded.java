/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.loot;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.fml.common.Loader;
import tws.zcaliptium.compositegear.common.ModInfo;

public class ForgeModLoaded implements LootCondition
{
	private final String id;
	private final boolean inverse;
	
	public ForgeModLoaded(String id, boolean inverse)
	{
		this.id = id;
		this.inverse = inverse;
	}
	
	@Override
	public boolean testCondition(Random rand, LootContext context)
	{
		return inverse ? !Loader.isModLoaded(id) : Loader.isModLoaded(id);
	}

    public static class Serializer extends LootCondition.Serializer<ForgeModLoaded>
    {
            public Serializer()
            {
                super(new ResourceLocation(ModInfo.MODID, "forge_mod_loaded"), ForgeModLoaded.class);
            }

            @Override
            public void serialize(JsonObject json, ForgeModLoaded value, JsonSerializationContext context)
            {
                json.addProperty("id", value.id);
                json.addProperty("inverse", Boolean.valueOf(value.inverse));
            }

            @Override
            public ForgeModLoaded deserialize(JsonObject json, JsonDeserializationContext context)
            {
            	String id = JsonUtils.getString(json, "id");
            	boolean isNot = JsonUtils.getBoolean(json, "inverse");

            	return new ForgeModLoaded(id, isNot);
            }
    }
}
