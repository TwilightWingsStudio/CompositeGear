/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.lib;

import com.google.gson.JsonObject;

import net.minecraft.item.Item;
import net.minecraftforge.common.crafting.JsonContext;

public interface IItemFactory
{
	public Item parse(JsonContext context, JsonObject json);
}
