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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import tws.zcaliptium.compositegear.client.ClientEventHandler;
import tws.zcaliptium.compositegear.common.compat.ACHungerHandler;

public class CommonProxy
{
	public void registerEventHandlers()
	{
		CommonEventHandler eventhandler = new CommonEventHandler();
		FMLCommonHandler.instance().bus().register(eventhandler);
		MinecraftForge.EVENT_BUS.register(eventhandler);
		
		if (ConfigurationCG.acCompat && Loader.isModLoaded(Compats.APPLECORE)) {		
			MinecraftForge.EVENT_BUS.register(new ACHungerHandler());
		}
	}
	
	public File getGameDir() {
	    return new File(".");
	}
	
	public boolean isClient() {
		return false;
	}
	
	public boolean isOpenToLAN() {
		return false;
	}
}
