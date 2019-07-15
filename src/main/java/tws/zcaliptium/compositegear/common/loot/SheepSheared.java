/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.loot;

import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.fml.common.Loader;
import tws.zcaliptium.compositegear.common.ModInfo;

public class SheepSheared implements LootCondition
{
	private final boolean inverse;
	
	public SheepSheared(boolean inverse)
	{
		this.inverse = inverse;
	}
	
	@Override
	public boolean testCondition(Random rand, LootContext context)
	{
		if (context.getLootedEntity() == null) {
			return false;
		}
		
		if (context.getLootedEntity() instanceof EntitySheep) {
			EntitySheep sheep = (EntitySheep)context.getLootedEntity();
			boolean isSheared = sheep.getSheared();
			
			return inverse ? !isSheared : isSheared;
		}

		return false;
	}

    public static class Serializer extends LootCondition.Serializer<SheepSheared>
    {
            public Serializer()
            {
                super(new ResourceLocation(ModInfo.MODID, "sheep_sheared"), SheepSheared.class);
            }

            @Override
            public void serialize(JsonObject json, SheepSheared value, JsonSerializationContext context)
            {
                json.addProperty("inverse", Boolean.valueOf(value.inverse));
            }

            @Override
            public SheepSheared deserialize(JsonObject json, JsonDeserializationContext context)
            {
            	boolean isNot = JsonUtils.getBoolean(json, "inverse");

            	return new SheepSheared(isNot);
            }
    }
}
