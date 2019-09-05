/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.DefaultGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import tws.zcaliptium.compositegear.common.config.ClientConfig;
import tws.zcaliptium.compositegear.common.config.CommonConfig;
import tws.zcaliptium.compositegear.common.ModInfo;

public class GuiFactoryCG extends DefaultGuiFactory
{
	public GuiFactoryCG()
	{
		super(ModInfo.MODID, GuiConfig.getAbridgedConfigPath(ClientConfig.config.toString()));
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parent)
	{
		return new GuiConfig(parent, getConfigElements(), this.modid, false, false, this.title);
	}

	private static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = new ArrayList<IConfigElement>();

		list.addAll(new ConfigElement(ClientConfig.config
				.getCategory(CommonConfig.SECTION_CLIENT))
				.getChildElements());

		return list;
	}
}