/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tws.zcaliptium.compositegear.client.IItemModelProvider;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.EnumItemClass;
import tws.zcaliptium.compositegear.common.IClassifiedItem;
import tws.zcaliptium.compositegear.common.ModInfo;

public class ItemCGSword extends ItemSword implements IClassifiedItem, IItemModelProvider
{
	public ItemCGSword(String id, ToolMaterial material)
	{
		super(material);
		
		setUnlocalizedName(id);

		ItemsCG.registerItem(this, new ResourceLocation(ModInfo.MODID, id)); // Put into registry.
		
		if (CompositeGear.ic2Tab != null) {
			setCreativeTab(CompositeGear.ic2Tab);
		}
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack var1)
    {
    	return EnumRarity.UNCOMMON;
    }

	@Override
	public EnumItemClass getItemClass() {
		return EnumItemClass.MELEE_WEAPON;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels(String var1)
	{
	}
}
