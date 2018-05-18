/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import toughasnails.api.temperature.TemperatureHelper;
import tws.zcaliptium.compositegear.common.compat.TANTemperatureModifier;
import tws.zcaliptium.compositegear.common.items.ArmorItemFactory;
import tws.zcaliptium.compositegear.common.items.GenericItemFactory;
import tws.zcaliptium.compositegear.common.items.ItemHelper;
import tws.zcaliptium.compositegear.common.items.ItemsCG;

import java.util.Iterator;

import org.apache.logging.log4j.Logger;

import ic2.api.recipe.Recipes;

@Mod(modid = ModInfo.MODID, name = ModInfo.MODNAME, dependencies="after:ic2;after:techreborn;after:hzdslib;after:immersiveengineering;after:applecore;after:toughasnails", version = ModInfo.VERSION)
public class CompositeGear
{
    @Instance(ModInfo.MODID)
    public static CompositeGear instance;
    
    @SidedProxy(clientSide = "tws.zcaliptium.compositegear.client.ClientProxy", serverSide = "tws.zcaliptium.compositegear.common.CommonProxy")
	public static CommonProxy proxy;
    
    public static final Side side = FMLCommonHandler.instance().getEffectiveSide();
    
    public static CreativeTabs ic2Tab;
	public static Logger modLog;
	public static ModContainer container;

	public static void getIC2Tab()
	{
		for (int i = 0; i < CreativeTabs.CREATIVE_TAB_ARRAY.length; i++) {
			if (CreativeTabs.CREATIVE_TAB_ARRAY[i].getTabLabel().equalsIgnoreCase("IC2")) {
				ic2Tab = CreativeTabs.CREATIVE_TAB_ARRAY[i];
			}
		}
	}
	
	@Optional.Method(modid = Compats.TAN)
	public static void registerTANModifier()
	{
		TemperatureHelper.registerTemperatureModifier(new TANTemperatureModifier());
	}

	public static ModContainer getModContainer(String modid)
	{
		Iterator <ModContainer> it = Loader.instance().getModList().iterator();

		while (it.hasNext()) {
			ModContainer container = it.next();
			
			if (container.getModId().equalsIgnoreCase(modid)) {
				return container;
			}
		}

		return null;
	}
    
    @EventHandler
    public void load(FMLPreInitializationEvent event)
    {
    	modLog = event.getModLog();
    	container = getModContainer(ModInfo.MODID);

    	ConfigurationCG.init(event.getSuggestedConfigurationFile());

    	if (proxy.isClient()) {
        	getIC2Tab();
    	}
    	
    	ItemHelper.factories.put(new ResourceLocation(ModInfo.MODID, "generic"), new GenericItemFactory());
    	ItemHelper.factories.put(new ResourceLocation(ModInfo.MODID, "armor"), new ArmorItemFactory());
    	ItemHelper.loadItems(container);
    	ItemsCG.load();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		proxy.registerEventHandlers();
		
		if (Loader.isModLoaded(Compats.TAN)) {
			registerTANModifier();
		}
    }
    
    @EventHandler
    public void afterModsLoaded(FMLPostInitializationEvent event)
    {
        ItemsCG.loadRecipes();
        
        // Item icon coloring won't work without it.
        if (proxy.isClient())
        {
        	registerItemColorHandler();
        }
    }
        
    @SideOnly(Side.CLIENT)
    public void registerItemColorHandler()
    {
    	modLog.info("Registering IItemColor handler for mod items.");
    	
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
        {
            public int colorMultiplier(ItemStack stack, int tintIndex)
            {
                return tintIndex > 0 ? -1 : ((ItemArmor)stack.getItem()).getColor(stack);
            }
        }, ItemsCG.compositeHelmet, ItemsCG.compositeChestplate, ItemsCG.compositeLeggings, ItemsCG.compositeBoots, ItemsCG.compositeFaceplate,
        		ItemsCG.ushankaHat, ItemsCG.balaclavaMask, ItemsCG.shemaghMask, ItemsCG.rubberGasmask, ItemsCG.respiratorMask, ItemsCG.respiratorMaskComposite,
        		ItemsCG.compositeLightHelmet, ItemsCG.compositeLightVest, ItemsCG.compositeLightLeggings, ItemsCG.compositeLightBoots, ItemsCG.feltBoots);
    }
}
