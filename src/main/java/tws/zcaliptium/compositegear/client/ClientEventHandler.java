/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.client;

import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import tws.zcaliptium.compositegear.common.IClassifiedItem;
import tws.zcaliptium.compositegear.common.items.ItemCGArmor;
import tws.zcaliptium.compositegear.common.items.ItemCGBow;
import tws.zcaliptium.compositegear.common.items.ItemCGSword;
import tws.zcaliptium.compositegear.common.items.ItemCompositeArmor;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class ClientEventHandler
{	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent ev)
	{
		ItemStack itemStack = ev.itemStack;

		if (itemStack.getItem() instanceof IClassifiedItem)
		{
			IClassifiedItem classifiedItem = (IClassifiedItem)itemStack.getItem();
			String transItemClass = StatCollector.translateToLocal("compositegear.itemclass");

			ev.toolTip.add(1, transItemClass + ": " + classifiedItem.getItemClass().getLocalized());
			
			if (itemStack.getItem() instanceof ItemCGArmor) {
				String transItemDesc = StatCollector.translateToLocal("compositegear.itemdesc");
				
				
				if (((ItemCGArmor)itemStack.getItem()).hasDescription()) {
					String descriptionString = StatCollector.translateToLocal(itemStack.getItem().getUnlocalizedName() + ".description");
					ev.toolTip.add(2, transItemDesc + ": " + descriptionString);
				}
			}
		}
	}
}
