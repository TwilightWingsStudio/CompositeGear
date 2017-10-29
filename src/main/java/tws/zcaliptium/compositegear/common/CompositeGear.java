/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.crafting.CraftingManager;
import tws.zcaliptium.compositegear.common.item.crafting.RecipesDyingArmor;
import tws.zcaliptium.compositegear.common.items.Items;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import ic2.api.recipe.Recipes;

@Mod(modid = ModInfo.MODID, name = ModInfo.MODNAME, dependencies="required-after:IC2;", version = ModInfo.VERSION)
public class CompositeGear
{
    @Instance(ModInfo.MODID)
    public static CompositeGear instance;
    
    @SidedProxy(clientSide = "tws.zcaliptium.compositegear.client.ClientProxy", serverSide = "tws.zcaliptium.compositegear.common.CommonProxy")
	public static CommonProxy proxy;
    
    public static final Side side = FMLCommonHandler.instance().getEffectiveSide();
    
    public static CreativeTabs ic2Tab;
    
	public static void getIC2Tab()
	{
		for (int i = 0; i < CreativeTabs.creativeTabArray.length; i++) {
			if (CreativeTabs.creativeTabArray[i].getTabLabel().equalsIgnoreCase("IC2")) {
				ic2Tab = CreativeTabs.creativeTabArray[i];
			}
		}
	}
    
    @EventHandler
    public void load(FMLPreInitializationEvent event)
    {
    	getIC2Tab();

    	Items.load();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {}
    
    @EventHandler
    public void afterModsLoaded(FMLPostInitializationEvent event)
    {
    	GameRegistry.addRecipe(new RecipesDyingArmor());
    	// TODO: Add recipes registration here.
    }
}
