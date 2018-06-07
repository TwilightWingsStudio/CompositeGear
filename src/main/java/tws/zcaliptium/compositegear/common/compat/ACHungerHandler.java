/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
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
import tws.zcaliptium.compositegear.lib.ISpecialArmor;

public class ACHungerHandler
{
    @SubscribeEvent
	@Optional.Method(modid = Compats.APPLECORE)
    public void getMaxExhaustion(ExhaustionEvent.GetMaxExhaustion event)
    {
    	EntityPlayer player = event.player;
    	
    	if (player == null) {
    		return;
    	}

    	Biome biome = player.world.getBiome(player.getPosition());
		InventoryPlayer inventory = player.inventory;
    	float multiplier = 1.0F;

		for (int i = 0; i < 4; i++)
		{
			ItemStack stack = inventory.armorInventory.get(i);

			Item item = stack.getItem();

			if (item instanceof ISpecialArmor) {
				ISpecialArmor tempItem = (ISpecialArmor)item;
				
				if (ConfigurationCG.isFESaveSatietyCold && tempItem.isSaveSatietyCold() && biome.getTempCategory() == Biome.TempCategory.COLD) {
					multiplier += 0.15F;
				}
				
				if (ConfigurationCG.isFESaveSatietyHot && tempItem.isSaveSatietyHot() && biome.getTempCategory() == Biome.TempCategory.WARM) {					
					multiplier += 0.15F;
				}
			}
		}
		
		//CompositeGear.modLog.info("Mul " + multiplier);

		event.maxExhaustionLevel *= multiplier;
    }
}
