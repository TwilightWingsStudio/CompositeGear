/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.client;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import ic2.api.item.IElectricItem;
import reborncore.api.power.IEnergyItemInfo;

import tws.zcaliptium.compositegear.common.*;
import tws.zcaliptium.compositegear.common.capabilities.LeveledCap;
import tws.zcaliptium.compositegear.common.config.ClientConfig;
import tws.zcaliptium.compositegear.common.items.*;
import tws.zcaliptium.compositegear.lib.IAttributeHolder;

@SideOnly(Side.CLIENT)
public class ItemTooltipHandler
{	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onItemTooltip(ItemTooltipEvent ev)
	{
		ItemStack itemStack = ev.getItemStack();
		Item item = itemStack.getItem();

		int line = 1;
		
		if (item == null) {
			return;
		}

		if (item instanceof IAttributeHolder)
		{
			IAttributeHolder attributeHolder = (IAttributeHolder)item;
			
			if (attributeHolder.getAttributes() == null) {
				return;
			}

			EnumItemClass classIn = (EnumItemClass)attributeHolder.getAttributes().getOrDefault("class", EnumItemClass.NO_CLASS);
			boolean hasDescription = attributeHolder.getAttributes().containsKey("has_description");
			
			// If class selected then print it out.
			if (classIn != EnumItemClass.NO_CLASS)
			{
				String transItemClass = I18n.translateToLocal("compositegear.itemclass");

				ev.getToolTip().add(line, transItemClass + ": " + classIn.getLocalized());
				line++;
			}

			// Item Description.
			if (hasDescription)
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
		}

		if (ClientConfig.tooltipDurabilityDisplay > 0 && isItemAllowedForDurabilityDisplay(item))
		{
			if (Loader.isModLoaded(Compats.ENDERCORE) && ClientConfig.tooltipDurabilityDisplay_removeEnderCoreDurability)
			{
				List<String> tooltip = ev.getToolTip();
				
				String pattern = I18n.translateToLocal("endercore.tooltip.durability");

				for (int i = 1; i < tooltip.size(); i++)
				{
					// Remove durability string.
					if (tooltip.get(i).matches(".*Durability:\\s+\\d+/\\d+")) {
						tooltip.remove(i);
						break;
					}
				}
			}
			
			ev.getToolTip().add(line, TextFormatting.BLACK + "@D");
			line++;
		}

		if (item instanceof IAttributeHolder)
		{
			IAttributeHolder attributeHolder = (IAttributeHolder)itemStack.getItem();
			
			float safeFall = (float)attributeHolder.getAttributes().getOrDefault("safe_fall", 0.0F);
			if (safeFall > 0) {
				String safeFallLabel = I18n.translateToLocal("compositegear.safefall");
				ev.getToolTip().add(TextFormatting.DARK_GREEN + " +" + safeFall + " " + safeFallLabel);
			}
			
			// Visual Attributes.
			if (attributeHolder.getAttributes().containsKey("has_va"))
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
	
	public static final ResourceLocation ICONS_RESOURCE = new ResourceLocation(ModInfo.MODID, "textures/misc/icons.png");

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderTooltip(RenderTooltipEvent.PostText event)
	{
		ItemStack stack = event.getStack();

		// Handle only valied item tooltips.
		if (stack == null || stack.isEmpty()) {
			return;
		}
		
		Minecraft mc = Minecraft.getMinecraft();
		Item item = stack.getItem();
		List<String> lines = event.getLines();


		if (ClientConfig.tooltipDurabilityDisplay > 0 && isItemAllowedForDurabilityDisplay(item))
		{
			int x = event.getX();
			int y = event.getY();
			
			for (int i = 1; i < lines.size(); i++)
			{
				//CompositeGear.modLog.info(lines.get(i));
				if (lines.get(i).contains("@D")) {
					y += (i * 10) + 2;
					break;
				}
			}

			GlStateManager.pushMatrix();
			GlStateManager.color(1F, 1F, 1F);
			
			mc.getTextureManager().bindTexture(ICONS_RESOURCE);
			Gui.drawModalRectWithCustomSizedTexture(x, y - 1, 0, 0, 10, 10, 256, 256);
			String durabilityLine = (stack.getMaxDamage() - stack.getItemDamage()) + "/" + stack.getMaxDamage();
			mc.fontRenderer.drawStringWithShadow(durabilityLine, x + 12, y, 0xFFFFFF);
			
			GlStateManager.popMatrix();
		}
	}
	
	public boolean isItemAllowedForDurabilityDisplay(Item item)
	{		
		boolean b2 = (item instanceof ItemCGBow) || (item instanceof ItemCGArmor) || (item instanceof ItemCGMelee) || (item instanceof ItemCGCrossbow);
		
		// If only our items then return result of checking them.
		if (ClientConfig.tooltipDurabilityDisplay == 1) {
			return b2;
		}
		
		// IC2 items use durability for displaying charge level.
		if (Loader.isModLoaded(Compats.IC2) && (item instanceof IElectricItem)) {
			return false;
		}
		
		// Ignore electric items from TechReborn and other mods based on RebornCore.
		if (Loader.isModLoaded(Compats.REBORNCORE) && item instanceof IEnergyItemInfo) {
			return false;
		}
		
		if (item instanceof ItemTool || item instanceof ItemArmor || item instanceof ItemSword || item instanceof ItemHoe
				|| item instanceof ItemBow || item instanceof ItemShears) {
			return true;
		}
		
		return b2;
	}
}
