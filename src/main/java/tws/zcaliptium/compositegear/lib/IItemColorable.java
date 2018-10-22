/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.lib;

import net.minecraft.item.ItemStack;

public interface IItemColorable
{
	public boolean isColorable();
	public boolean hasColor(ItemStack stack);
	public int getColor(ItemStack stack);
	
	public void removeColor(ItemStack stack);
	public void setColor(ItemStack stack, int color);
}
