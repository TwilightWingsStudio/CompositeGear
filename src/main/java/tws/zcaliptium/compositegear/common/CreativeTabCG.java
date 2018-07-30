/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CreativeTabCG extends CreativeTabs
{
	public CreativeTabCG()
	{
		super(ModInfo.MODID);
	}

	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(Items.APPLE);
	}
}
