/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import toughasnails.api.temperature.TemperatureHelper;
import tws.zcaliptium.compositegear.client.ItemTooltipHandler;
import tws.zcaliptium.compositegear.common.capabilities.LeveledCap;
import tws.zcaliptium.compositegear.common.compat.ACHungerHandler;
import tws.zcaliptium.compositegear.common.compat.TANTemperatureModifier;
import tws.zcaliptium.compositegear.common.init.ModItems;

public class CommonProxy
{
	public boolean isClient()
	{
		return false;
	}
	
	public void preInit()
	{
    	ModItems.load();
    	
    	LeveledCap.init();
	}
	
	public void init()
	{
		MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
		
		if (ConfigurationCG.acCompat && Loader.isModLoaded(Compats.APPLECORE)) {		
			MinecraftForge.EVENT_BUS.register(new ACHungerHandler());
		}
		
		if (ConfigurationCG.tanCompat && Loader.isModLoaded(Compats.TAN)) {
			registerTANModifier();
		}
	}
	
	public void postInit()
	{
        ModItems.loadRecipes();
        ModItems.defineRepairMaterials();
	}
	
	public File getGameDir() {
	    return new File(".");
	}

	public boolean isOpenToLAN() {
		return false;
	}

	public boolean isEnabledPVP()
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance().isPVPEnabled();
	}
	
	@Optional.Method(modid = Compats.TAN)
	public static void registerTANModifier()
	{
		TemperatureHelper.registerTemperatureModifier(new TANTemperatureModifier());
	}
	
	public void throwModLoadingException(String[] lines, Throwable causes)
	{
		if (causes != null) {
			FMLCommonHandler.instance().raiseException(causes, "", true);
		}
	}
}
