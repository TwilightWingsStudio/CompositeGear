/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tws.zcaliptium.compositegear.common.Compats;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.ConfigurationCG;
import tws.zcaliptium.compositegear.common.EnumItemClass;
import tws.zcaliptium.compositegear.common.IClassifiedItem;
import tws.zcaliptium.compositegear.common.IDescriptableItem;
import tws.zcaliptium.compositegear.common.ModInfo;

public class ItemCGArmor extends ItemArmor implements IClassifiedItem, IDescriptableItem
{
	protected final String armorName;
	protected EnumItemClass itemClass;

	protected boolean isAirMask;
	protected boolean hasDescription;
	protected int minAirToStartRefil;
	protected EnumRarity rarity;
	protected boolean hasVisualAttributes;

	public ItemCGArmor(String id, ArmorMaterial armorMaterial, String armorName, int renderIndex, EntityEquipmentSlot armorType)
	{
		super(armorMaterial, renderIndex, armorType);

		this.armorName = armorName;
		this.itemClass = EnumItemClass.MEDIUM_ARMOR;
		this.isAirMask = false;
		this.minAirToStartRefil = 0;
		this.rarity = EnumRarity.COMMON;
		
		setUnlocalizedName(id);
		
		ItemsCG.registerItem(this, new ResourceLocation(ModInfo.MODID, id)); // Put into registry.

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
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
	{
		int suffix = this.armorType == EntityEquipmentSlot.LEGS ? 2 : 1;
	
		if (type == null) {
			return ModInfo.MODID + ":textures/armor/" + this.armorName + "_" + suffix + ".png";
		}
		
		return ModInfo.MODID + ":textures/armor/" + this.armorName + "_" + suffix + "_overlay.png";
	}
	
	private static boolean consumeItemFromInventory(EntityPlayer player, ItemStack itemStack)
	{
		for (int i = 0; i < player.inventory.mainInventory.size(); i++)
		{
			if ((player.inventory.mainInventory.get(i) != null)) {
				if (!ItemStack.areItemStackTagsEqual(player.inventory.mainInventory.get(i), itemStack)) {
					continue;
				}
				
				player.inventory.decrStackSize(i, 1);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
	{
		if (world.isRemote) {
			return;
		}
		
		boolean shouldUpdate = false;
		
		// If we wear some air mask on head.
		if (this.isAirMask && this.armorType == EntityEquipmentSlot.HEAD)
		{
			// Default max value is 300
			if (player.getAir() <= minAirToStartRefil)
			{
				boolean refilled = false;
				
				if (!refilled && Loader.isModLoaded(Compats.TR))
				{
					if (consumeItemFromInventory(player, ItemsCG.trCompressedAirCell))
					{
						player.inventory.addItemStackToInventory(ItemsCG.trEmptyCell.copy());
						refilled = true;
					}

				}
				
				if (!refilled && Loader.isModLoaded(Compats.IC2)) { // Use TR or IC2 cells.

					if (consumeItemFromInventory(player, ItemsCG.ic2CompressedAirCell))
					{
						player.inventory.addItemStackToInventory(ItemsCG.ic2EmptyCell.copy());
						refilled = true;
					}
				}
				
				if (refilled) {
					player.setAir(300);
			        shouldUpdate = true; // Sync player inventory.
				}
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
    
    public ItemCGArmor setHasDescription(boolean hasDescription)
    {
    	this.hasDescription = hasDescription;
		return this;
    }
    
    public ItemCGArmor setHasVisualAttributes(boolean hasVisualAttributes)
    {
    	this.hasVisualAttributes = hasVisualAttributes;
    	return this;
    }
    
    @Override
    public boolean hasDescription() 
    {
    	return hasDescription;
    }
    
    public boolean hasVisualAttributes() 
    {
    	return hasVisualAttributes;
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
    
	@Override
	public int getItemEnchantability(ItemStack stack)
	{
		if (!ConfigurationCG.allowArmorEnchanting) {
			return 0;
		}

		return super.getItemEnchantability(stack);
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
	{
		return ConfigurationCG.allowArmorEnchanting;
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
