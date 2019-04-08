/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common;

import java.util.List;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tws.zcaliptium.compositegear.common.items.ItemCGMelee;
import tws.zcaliptium.compositegear.common.loot.LootTableHelper;

public class CommonEventHandler
{
	private static String GEMS[] = new String[] {
		"diamond",
		"amethyst",
		"emerald",
		"ruby",
		"sapphire",
		"topaz",
		"peridot"
	};

	@SubscribeEvent
	public boolean onPlayerAttackTarget(AttackEntityEvent event)
	{
		if (event.getEntityPlayer().getEntityWorld().isRemote) {
			return true;
		}

		// If we don't hate gem armor then don't execute following code.
		if (!ConfigurationCG.isFEConstantGemDamage) {
			return true;
		}

		// Don't process if PVP disabled.
		if (!CompositeGear.proxy.isEnabledPVP()) {
			return true;
		}

		Item itemHeld = event.getEntityPlayer().getHeldItemMainhand().getItem();

		if (!(itemHeld instanceof ItemCGMelee)) {
			return true;
		}

		int constantGemDmg = ((ItemCGMelee)itemHeld).getConstantGemDamage();

		if (constantGemDmg <= 0) {
			return true;
		}

		if (event.getTarget() instanceof EntityPlayer)
		{
			EntityPlayer targetPlayer = (EntityPlayer)event.getTarget();

	        for (ItemStack itemstack : targetPlayer.getArmorInventoryList())
	        {
	            if (itemstack != null && itemstack.getItem() instanceof ItemArmor)
	            {
	            	ItemArmor armorPiece = (ItemArmor)itemstack.getItem();
	            	ItemArmor.ArmorMaterial material = armorPiece.getArmorMaterial();

	            	if (material == ArmorMaterial.DIAMOND) {
	            		itemstack.damageItem(constantGemDmg, targetPlayer);	            		
	            	} else {
	            		String name = material.toString().toLowerCase();

	            		for (String gem : GEMS)
	            		{
	            			if (name.contains(gem)) {
	            				itemstack.damageItem(constantGemDmg, targetPlayer);
	            				break;
	            			}
	            		}
	            	}
	            }
	        }
		}

		return true;
	}
	
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event)
	{	
		if (event.getEntityLiving().world.isRemote) {
			return;
		}
		
		// Player/Armor Stand should be filtered.
		if (!(event.getEntityLiving() instanceof EntityLiving)) {
			return;
		}
		
		if (ConfigurationCG.customLootTables) {
			EntityLiving entity = (EntityLiving)event.getEntityLiving();

	        ResourceLocation resourcelocation = EntityList.getKey(entity);
			
			if (resourcelocation != null && entity.deathLootTable == null) {
				
				ResourceLocation loc = LootTableHelper.LOOT_TABLE_DEFAULTS.get(resourcelocation);
				
				entity.deathLootTable = loc;
			}
		}
	}
}
