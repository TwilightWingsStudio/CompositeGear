/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import tws.zcaliptium.compositegear.common.config.CommonConfig;

import java.io.File;
import java.util.Iterator;

import org.apache.logging.log4j.Logger;
import tws.zcaliptium.compositegear.common.config.ConfigManager;

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
	public static File configDir;
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
		configDir = new File(event.getModConfigurationDirectory(), ModInfo.MODID);
    	container = getModContainer(ModInfo.MODID);

		ConfigManager.loadConfigs();

    	proxy.preInit();
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
    }
}
