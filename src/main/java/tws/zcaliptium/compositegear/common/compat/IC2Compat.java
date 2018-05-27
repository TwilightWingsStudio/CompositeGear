/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.compat;

import ic2.api.item.IC2Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;
import tws.zcaliptium.compositegear.common.Compats;
import tws.zcaliptium.compositegear.common.CompositeGear;

public class IC2Compat
{
	public static ItemStack ic2CompressedAirCell;
	public static ItemStack ic2EmptyCell;

	@Optional.Method(modid = Compats.IC2)
	public static void load()
	{
		CompositeGear.modLog.info("Loading IC2 compat module.");

		ic2CompressedAirCell = IC2Items.getItem("fluid_cell", "ic2air");
		ic2EmptyCell = IC2Items.getItem("fluid_cell");
		
		if (ic2CompressedAirCell == null || ic2EmptyCell == null) {
			CompositeGear.modLog.error("One of IC2 cells is null! Respirators may not work!");
		}
	}
}
