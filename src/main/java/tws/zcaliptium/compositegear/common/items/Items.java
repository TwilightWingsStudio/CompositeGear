/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class Items
{
	private static final String COMPOSITE_NAME = "composite";
	
	public static Item compositeHelmet;
	public static Item compositeChestplate;
	public static Item compositeLeggins;
	public static Item compositeBoots;
	
	public static void load()
	{
		ItemArmor.ArmorMaterial material = EnumHelper.addArmorMaterial("BETTER_COMPOSITE", 50, new int[] { 3, 9, 6, 3 }, 12);
		
		compositeHelmet = new ItemCompositeArmor("composite_helmet", material, COMPOSITE_NAME, 0, 0);
		compositeChestplate = new ItemCompositeArmor("composite_chestplate", material, COMPOSITE_NAME , 0, 1);
		compositeLeggins = new ItemCompositeArmor("composite_leggins", material, COMPOSITE_NAME, 0, 2);
		compositeBoots = new ItemCompositeArmor("composite_boots", material, COMPOSITE_NAME, 0, 3);
	}
}
