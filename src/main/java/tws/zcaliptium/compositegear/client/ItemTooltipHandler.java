/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.client;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.relauncher.SideOnly;
import squeek.applecore.ModInfo;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import tws.zcaliptium.compositegear.client.model.BakedWrappedWithGuiModel;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.capabilities.LeveledCap;
import tws.zcaliptium.compositegear.common.items.EnumItemClass;
import tws.zcaliptium.compositegear.common.items.ItemCGArmor;
import tws.zcaliptium.compositegear.common.items.ItemCGBow;
import tws.zcaliptium.compositegear.common.items.ItemCGMelee;
import tws.zcaliptium.compositegear.lib.IItemIntelligence;

@SideOnly(Side.CLIENT)
public class ItemTooltipHandler
{	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent ev)
	{
		ItemStack itemStack = ev.getItemStack();

		int line = 1;

		if (itemStack.getItem() instanceof IItemIntelligence)
		{
			IItemIntelligence itemIntelligence = (IItemIntelligence)itemStack.getItem();
			
			// If class selected then print it out.
			if (itemIntelligence.getItemClass() != EnumItemClass.NO_CLASS)
			{
				String transItemClass = I18n.translateToLocal("compositegear.itemclass");

				ev.getToolTip().add(line, transItemClass + ": " + itemIntelligence.getItemClass().getLocalized());
				line++;
			}

			// Item Description.
			if (itemIntelligence.hasDescription())
			{
				String transItemDesc = I18n.translateToLocal("compositegear.itemdesc");

				String descriptionString = I18n.translateToLocal(itemStack.getItem().getUnlocalizedName() + ".desc");
				String descriptionLines[] = descriptionString.split("\\^");
				
				boolean firstDescriptionLine = true;

				for (String descLine : descriptionLines) {
					if (firstDescriptionLine) {
						ev.getToolTip().add(line, transItemDesc + ": " + descLine);
						firstDescriptionLine = false;
					} else {
						ev.getToolTip().add(line, descLine);
					}

					line++;
				}
			}

			// Visual Attributes.
			if (itemIntelligence.hasVisualAttributes())
			{
				String visualAttributes = I18n.translateToLocal(itemStack.getItem().getUnlocalizedName() + ".va");
				String attributes[] = visualAttributes.split("\\^");

				for (String attributeString : attributes) {
					ev.getToolTip().add(attributeString);
				}
			}

			if (itemStack.hasCapability(LeveledCap.CAPABILITY_LEVELED, EnumFacing.DOWN)) {
				ev.getToolTip().add("@Leveled (" + itemStack.getCapability(LeveledCap.CAPABILITY_LEVELED, null).getLevel() + ")");
			}
		}
	}
}
