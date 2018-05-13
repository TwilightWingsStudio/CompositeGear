/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import squeek.applecore.api.AppleCoreAPI;
import squeek.applecore.api.hunger.ExhaustionEvent;
import tws.zcaliptium.compositegear.common.Compats;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.ConfigurationCG;
import tws.zcaliptium.compositegear.common.items.ItemsCG;

public class ACHungerHandler
{
    @SubscribeEvent
	@Optional.Method(modid = Compats.APPLECORE)
    public void getMaxExhaustion(ExhaustionEvent.GetMaxExhaustion event)
    {
    	// If bonuses disabled then do nothing!
    	if (!ConfigurationCG.hatsBonuses) {
    		return;
    	}

    	EntityPlayer player = event.player;
    	
    	if (player == null) {
    		return;
    	}

    	Biome biome = player.world.getBiome(player.getPosition());
    	ItemStack headStack = player.inventory.armorInventory.get(3);

    	if (headStack.getItem() == ItemsCG.ushankaHat || headStack.getItem() == ItemsCG.balaclavaMask) {
        	if (biome.getTempCategory() == Biome.TempCategory.COLD) {   
        		event.maxExhaustionLevel *= 1.25F;
        	}
    	} else if (headStack.getItem() == ItemsCG.shemaghMask) {
        	if (biome.getTempCategory() == Biome.TempCategory.WARM) {
        		event.maxExhaustionLevel *= 1.25F;
        	}
    	}
    }
}
