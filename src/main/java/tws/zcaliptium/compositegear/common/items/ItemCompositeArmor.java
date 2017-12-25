/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import ic2.api.item.IMetalArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tws.zcaliptium.compositegear.common.Compats;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.EnumItemClass;
import tws.zcaliptium.compositegear.common.ModInfo;

@Optional.Interface(iface = "ic2.api.item.IMetalArmor", modid = Compats.IC2)
public class ItemCompositeArmor extends ItemCGArmor implements IMetalArmor
{
	private int defaultColor;
	private boolean hasOverlayIcon;

	public ItemCompositeArmor(String id, ArmorMaterial armorMaterial, String armorName, int renderIndex, EntityEquipmentSlot armorType)
	{
		super(id, armorMaterial, armorName, renderIndex, armorType);
	
		defaultColor = 0;
	}

	// IMetalArmor
	@Optional.Method(modid = Compats.IC2)
	@Override
	public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player) {
		return true;
	}

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack var1)
    {
    	return this.rarity;
    }

    @Override
    public boolean hasColor(ItemStack stack)
    {
        return !stack.hasTagCompound() ? false : (!stack.getTagCompound().hasKey("display", 10) ? false : stack.getTagCompound().getCompoundTag("display").hasKey("color", 3));
    }
    
    @Override
    public int getColor(ItemStack stack)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();
        
        if (nbttagcompound == null) {
            return defaultColor;
        } else {
        	//System.out.println("DA KURWA!");
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
            return nbttagcompound1 == null ? defaultColor : (nbttagcompound1.hasKey("color", 3) ? nbttagcompound1.getInteger("color") : defaultColor);
        }
    }

    // setColor
    @Override
    public void setColor(ItemStack stack, int newColor)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null){
            nbttagcompound = new NBTTagCompound();
            stack.setTagCompound(nbttagcompound);
        }

        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

        if (!nbttagcompound.hasKey("display", 10)) {
            nbttagcompound.setTag("display", nbttagcompound1);
        }

        nbttagcompound1.setInteger("color", newColor);
    }

    @Override
    public void removeColor(ItemStack stack)
    {
    	NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound != null)
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

            if (nbttagcompound1.hasKey("color"))
            {
                nbttagcompound1.removeTag("color");
            }
        }
    }

    public ItemCompositeArmor setDefaultColor(int color)
    {
    	this.defaultColor = color;
    	return this;
    }

    public ItemCompositeArmor setHasOverlayIcon(boolean hasOverlayIcon)
    {
    	this.hasOverlayIcon = hasOverlayIcon;
		return this;
    }
}
