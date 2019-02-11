/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
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
import net.minecraftforge.oredict.OreDictionary;
import toughasnails.api.temperature.TemperatureHelper;
import tws.zcaliptium.compositegear.client.ModelBakeHandler;
import tws.zcaliptium.compositegear.common.capabilities.LeveledCap;
import tws.zcaliptium.compositegear.common.compat.TANTemperatureModifier;
import tws.zcaliptium.compositegear.common.init.ModItems;
import tws.zcaliptium.compositegear.common.items.ArmorItemFactory;
import tws.zcaliptium.compositegear.common.items.GenericItemFactory;
import tws.zcaliptium.compositegear.common.items.ItemCGMelee;
import tws.zcaliptium.compositegear.common.items.ItemHelper;
import tws.zcaliptium.compositegear.common.items.MeleeItemFactory;
import tws.zcaliptium.compositegear.common.items.RangedItemFactory;

import java.util.Iterator;

import org.apache.logging.log4j.Logger;

import ic2.api.recipe.Recipes;

@Mod(	modid = ModInfo.MODID,
		name = ModInfo.MODNAME,
		dependencies = "after:baubles;after:ic2;after:techreborn;after:hzdslib;after:immersiveengineering;after:applecore;after:toughasnails",
		version = ModInfo.VERSION,
		guiFactory = "tws.zcaliptium.compositegear.client.gui.GuiFactoryCG")
public class CompositeGear
{
    @Instance(ModInfo.MODID)
    public static CompositeGear instance;
    
    @SidedProxy(clientSide = "tws.zcaliptium.compositegear.client.ClientProxy", serverSide = "tws.zcaliptium.compositegear.common.CommonProxy")
	public static CommonProxy proxy;
    
    public static final Side side = FMLCommonHandler.instance().getEffectiveSide();
    
    public static CreativeTabs cgTab = new CreativeTabCG();

	public static Logger modLog;
	public static ModContainer container;

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

	    try {
	    	ConfigurationCG.init(event.getSuggestedConfigurationFile());
	    } catch (Exception e) {
	      CompositeGear.modLog.error("Unable to load configuration file!");
	      throw new RuntimeException(e);
	    } finally {
	    	if (ConfigurationCG.config != null) {
	    		ConfigurationCG.save();
	    	}
	    }

    	proxy.preInit();
	    
    	ModItems.load();
    	
    	LeveledCap.init();
    	
    	ConfigurationCG.save();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	modLog.info("Initializing.");
    	
    	proxy.init();
    }

    @EventHandler
    public void afterModsLoaded(FMLPostInitializationEvent event)
    {
    	proxy.postInit();
    	
        ModItems.loadRecipes();
        ModItems.defineRepairMaterials();
    }
}
