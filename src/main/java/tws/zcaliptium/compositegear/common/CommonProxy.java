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

public class CommonProxy
{
	public void registerEventHandlers()
	{
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
