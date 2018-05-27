/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.compat;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;
import techreborn.api.TechRebornAPI;
import tws.zcaliptium.compositegear.common.Compats;
import tws.zcaliptium.compositegear.common.CompositeGear;

public class TRCompat
{
	public static ItemStack trCompressedAirCell;
	public static ItemStack trEmptyCell;

	@Optional.Method(modid = Compats.TR)
	public static void load()
	{
		CompositeGear.modLog.info("Loading TechReborn compat module.");

		trCompressedAirCell = TechRebornAPI.subItemRetriever.getCellByName("compressedair", 1);
		trEmptyCell = TechRebornAPI.subItemRetriever.getCellByName("cell");
		
		if (trCompressedAirCell == null || trEmptyCell == null) {
			CompositeGear.modLog.error("One of TechReborn cells is null! Respirators may not work!");
		}
	}
}
