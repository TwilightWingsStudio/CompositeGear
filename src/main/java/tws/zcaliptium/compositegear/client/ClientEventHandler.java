/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.client;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import tws.zcaliptium.compositegear.common.IClassifiedItem;
import tws.zcaliptium.compositegear.common.items.ItemCGArmor;
import tws.zcaliptium.compositegear.common.items.ItemCGBow;
import tws.zcaliptium.compositegear.common.items.ItemCGSword;
import tws.zcaliptium.compositegear.common.items.ItemCompositeArmor;

@SideOnly(Side.CLIENT)
public class ClientEventHandler
{	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent ev)
	{
		ItemStack itemStack = ev.getItemStack();

		if (itemStack.getItem() instanceof IClassifiedItem)
		{
			IClassifiedItem classifiedItem = (IClassifiedItem)itemStack.getItem();
			String transItemClass = I18n.translateToLocal("compositegear.itemclass");

			ev.getToolTip().add(1, transItemClass + ": " + classifiedItem.getItemClass().getLocalized());
			
			if (itemStack.getItem() instanceof ItemCGArmor) {
				String transItemDesc = I18n.translateToLocal("compositegear.itemdesc");
				
				
				if (((ItemCGArmor)itemStack.getItem()).hasDescription()) {
					String descriptionString = I18n.translateToLocal(itemStack.getItem().getUnlocalizedName() + ".description");
					ev.getToolTip().add(2, transItemDesc + ": " + descriptionString);
				}
			}
		}
	}
}
