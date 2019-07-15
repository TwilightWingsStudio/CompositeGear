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

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import tws.zcaliptium.compositegear.common.ModInfo;

public class SheepColor extends LootFunction
{

	protected SheepColor(LootCondition[] conditionsIn)
	{
		super(conditionsIn);
	}

	@Override
	public ItemStack apply(ItemStack stack, Random rand, LootContext context)
	{
        if (stack.isEmpty())
        {
            return stack;
        }
        else
        {
    		if (context.getLootedEntity() == null) {
    			return stack;
    		}
    		
    		if (context.getLootedEntity() instanceof EntitySheep) {
    			EntitySheep sheep = (EntitySheep)context.getLootedEntity();
    			EnumDyeColor color = sheep.getFleeceColor();

    			stack.setItemDamage(color.getMetadata());
    		}
    		
    		return stack;
        }
	}

    public static class Serializer extends LootFunction.Serializer<SheepColor>
    {
        public Serializer()
        {
            super(new ResourceLocation(ModInfo.MODID, "sheep_color"), SheepColor.class);
        }

        public void serialize(JsonObject object, SheepColor functionClazz, JsonSerializationContext serializationContext)
        {
        }

        public SheepColor deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn)
        {
            return new SheepColor(conditionsIn);
        }
    }
}
