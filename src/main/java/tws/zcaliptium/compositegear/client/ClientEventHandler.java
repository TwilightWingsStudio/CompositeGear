/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
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
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.items.ItemCGArmor;
import tws.zcaliptium.compositegear.common.items.ItemCGBow;
import tws.zcaliptium.compositegear.common.items.ItemCGMelee;
import tws.zcaliptium.compositegear.common.items.ItemCompositeArmor;
import tws.zcaliptium.compositegear.lib.IClassifiedItem;
import tws.zcaliptium.compositegear.lib.IDescriptableItem;

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
		}

		// Text description.
		if (itemStack.getItem() instanceof IDescriptableItem)
		{
			String transItemDesc = I18n.translateToLocal("compositegear.itemdesc");

			if (((IDescriptableItem)itemStack.getItem()).hasDescription()) {
				String descriptionString = I18n.translateToLocal(itemStack.getItem().getUnlocalizedName() + ".desc");
				String descriptionLines[] = descriptionString.split("\\^");
				int line = 2;

				for (String descLine : descriptionLines) {
					
					if (line == 2) {
						ev.getToolTip().add(line, transItemDesc + ": " + descLine);
					} else {
						ev.getToolTip().add(line, descLine);
					}

					line++;
				}
			}
		}

		// Visual attributes.
		if (itemStack.getItem() instanceof ItemCGArmor)
		{
			if (((ItemCGArmor)itemStack.getItem()).hasVisualAttributes())
			{
				String visualAttributes = I18n.translateToLocal(itemStack.getItem().getUnlocalizedName() + ".va");
				String attributes[] = visualAttributes.split("\\^");

				for (String attributeString : attributes) {
					ev.getToolTip().add(attributeString);
				}
			}
		}
	}
}
