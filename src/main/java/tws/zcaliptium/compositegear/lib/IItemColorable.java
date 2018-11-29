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
	// NOTE: Can't use 'hasColor', 'getColor', 'setColor' and 'removeColor'.
	// These names intersect with vanilla ones from ItemArmor and cause bugs in release build.

	public boolean hasColorData(ItemStack stack);

	public boolean isColorable();
	public int getColorData(ItemStack stack, int colorId);

	public void removeColorData(ItemStack stack);
	public void setColorData(ItemStack stack, int colorId, int color);
}
