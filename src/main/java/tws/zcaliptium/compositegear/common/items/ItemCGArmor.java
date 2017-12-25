/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tws.zcaliptium.compositegear.common.Compats;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.EnumItemClass;
import tws.zcaliptium.compositegear.common.IClassifiedItem;
import tws.zcaliptium.compositegear.common.ModInfo;

public class ItemCGArmor extends ItemArmor implements IClassifiedItem
{
	protected final String armorName;
	protected EnumItemClass itemClass;

	protected boolean isAirMask;
	protected boolean hasDescription;
	protected int minAirToStartRefil;
	protected EnumRarity rarity;

	public ItemCGArmor(String id, ArmorMaterial armorMaterial, String armorName, int renderIndex, EntityEquipmentSlot armorType)
	{
		super(armorMaterial, renderIndex, armorType);

		this.armorName = armorName;
		this.itemClass = EnumItemClass.MEDIUM_ARMOR;
		this.isAirMask = false;
		this.minAirToStartRefil = 0;
		this.rarity = EnumRarity.COMMON;
		
		setUnlocalizedName(id);
		
		GameRegistry.registerItem(this, id, ModInfo.MODID);

		if (CompositeGear.ic2Tab != null) {
			setCreativeTab(CompositeGear.ic2Tab);
		}
	}

	@Override
	public EnumItemClass getItemClass()
	{
		return itemClass;
	}
	
	public ItemCGArmor setItemClass(EnumItemClass itemClass)
	{
		this.itemClass = itemClass;
		return this;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
	{
		int suffix = this.armorType == EntityEquipmentSlot.LEGS ? 2 : 1;
	
		if (type == null) {
			return ModInfo.MODID + ":textures/armor/" + this.armorName + "_" + suffix + ".png";
		}
		
		return ModInfo.MODID + ":textures/armor/" + this.armorName + "_" + suffix + "_overlay.png";
	}
	
	private static void consumeItemFromInventory(EntityPlayer player, ItemStack itemStack)
	{
		for (int i = 0; i < player.inventory.mainInventory.size(); i++)
		{
			if ((player.inventory.mainInventory.get(i) != null) && (player.inventory.mainInventory.get(i).isItemEqual(itemStack))) {
				player.inventory.decrStackSize(i, 1);
				return;
			}
		}
	}
	
	@Optional.Method(modid = Compats.IC2)
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
	{
		boolean shouldUpdate = false;
		
		// If we wear some air mask on head.
		if (this.isAirMask && this.armorType == EntityEquipmentSlot.HEAD)
		{
			// Default max value is 300
			
			if ((player.getAir() <= minAirToStartRefil) && (player.inventory.hasItemStack(ItemsCG.ic2AirCell)))
			{
				consumeItemFromInventory(player, ItemsCG.ic2AirCell);
		        player.inventory.addItemStackToInventory(new ItemStack(ItemsCG.ic2EmptyCell.getItem()));
		        player.setAir(300);
		        shouldUpdate = true;
			}
		}
		
		// If we have changed inventory contents then we should sync it on client.
		if (shouldUpdate) {
			player.inventoryContainer.detectAndSendChanges();
		}
	}

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack var1)
    {
    	return rarity;
    }

	public boolean hasColor(ItemStack stack) {
		return false;
	}
    
    public ItemCGArmor setHasDescription(boolean hasDescription)
    {
    	this.hasDescription = hasDescription;
		return this;
    }
    
    public boolean hasDescription() 
    {
    	return hasDescription;
    }
    
    public ItemCGArmor setAirMask(boolean isAirMask)
    {
    	this.isAirMask = isAirMask;
    	return this;
    }
    
    public boolean isAirMask()
    {
    	return isAirMask;
    }
    
    public ItemCGArmor setRarity(EnumRarity rarity)
    {
    	this.rarity = rarity;
		return this;
    }
    
	public ItemCGArmor setMinAir(int minAir) {
		this.minAirToStartRefil = minAir;
		
		return this;
	}
}
