/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import toughasnails.api.temperature.IModifierMonitor;
import toughasnails.api.temperature.ITemperatureModifier;
import toughasnails.api.temperature.Temperature;
import tws.zcaliptium.compositegear.common.Compats;
import tws.zcaliptium.compositegear.common.config.CommonConfig;
import tws.zcaliptium.compositegear.common.items.ItemCGArmor;

@Optional.Interface(iface = "toughasnails.api.temperature.ITemperatureModifier", modid = Compats.TAN)
public class TANTemperatureModifier implements ITemperatureModifier
{
	@Optional.Method(modid = Compats.TAN)
	@Override
	public Temperature applyEnvironmentModifiers(World world, BlockPos pos, Temperature initialTemperature,
			IModifierMonitor monitor)
	{
		return null;
	}

	// ITemperatureModifier
	@Optional.Method(modid = Compats.TAN)
	@Override
	public Temperature applyPlayerModifiers(EntityPlayer player, Temperature initialTemperature,
			IModifierMonitor monitor)
	{
		int newTemperatureLevel = initialTemperature.getRawValue();

		InventoryPlayer inventory = player.inventory;

		ItemStack headStack = inventory.armorInventory.get(3);

		int modifier = 0;

		for (int i = 0; i < 4; i++)
		{
			ItemStack stack = inventory.armorInventory.get(i);

			Item item = stack.getItem();
			
			if (item instanceof ItemCGArmor) {
				ItemCGArmor tempItem = (ItemCGArmor)item;
				
				if (CommonConfig.isFEWarm && tempItem.getAttributes().containsKey("tan_warm")) {
					modifier += 1;
				}
			}
		}

		newTemperatureLevel += modifier;
		monitor.addEntry(new IModifierMonitor.Context(this.getId(), "CG_Armor", initialTemperature, new Temperature(newTemperatureLevel)));

		return new Temperature(newTemperatureLevel);
	}

	@Optional.Method(modid = Compats.TAN)
	@Override
	public boolean isPlayerSpecific()
	{
		return true;
	}

	@Optional.Method(modid = Compats.TAN)
	@Override
	public String getId()
	{
		return "cg_armor";
	}
}
