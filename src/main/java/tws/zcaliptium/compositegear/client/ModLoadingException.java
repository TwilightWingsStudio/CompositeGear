/*******************************************************************************
 * Copyright (c) 2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.client;

import javax.annotation.Nonnull;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraftforge.fml.client.CustomModLoadingErrorDisplayException;

public class ModLoadingException extends CustomModLoadingErrorDisplayException
{
	private final @Nonnull String[] lines;
	
	public ModLoadingException(@Nonnull String[] lines, Throwable t)
	{
		super(lines[0], t);
		this.lines = lines;
	}
	
	@Override
	public void initGui(GuiErrorScreen errorScreen, FontRenderer fontRenderer) {}

	@Override
	public void drawScreen(GuiErrorScreen errorScreen, FontRenderer fontRenderer, int mouseRelX, int mouseRelY,
			float tickTime)
	{
		if (errorScreen == null || fontRenderer == null) {
			return;
		}
		
		int y = errorScreen.height / 2 - lines.length * 5;
		
		for (String line : lines)
		{
			if (line != null) {
				errorScreen.drawCenteredString(fontRenderer, line, errorScreen.width / 2, y, 0xFFFFFFFF);
				y += 10;
				continue;
			}
			
			y += 5;
		}
	}
}
