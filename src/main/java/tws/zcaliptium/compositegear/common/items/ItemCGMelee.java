/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tws.zcaliptium.compositegear.client.IItemModelProvider;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.config.CommonConfig;
import tws.zcaliptium.compositegear.common.ModInfo;
import tws.zcaliptium.compositegear.common.init.ModItems;
import tws.zcaliptium.compositegear.lib.IAttributeHolder;
import tws.zcaliptium.compositegear.lib.IItemColorable;

public class ItemCGMelee extends ItemSword implements IItemModelProvider, IItemColorable, IAttributeHolder
{
	public static Item.ToolMaterial GENERIC_MELEE_MATERIAL = EnumHelper.addToolMaterial("CG_MELEE_GENERIC", 0, 1, 0.0F, 0.0F, 0);
	
	// Model.
	protected int defaultColor;
	protected boolean hasOverlay;

	// Material.
	protected int enchantability;
	protected float attackDamage;
	protected float attackSpeed;

	// Features.
	protected boolean isShieldDisabler;
	protected int constantGemDamage;
	
	protected Map<String, Object> attributes;
	
	public ItemCGMelee(String id, ToolMaterial material)
	{
		super(material);
		
		attributes = new HashMap<String, Object>();

		setUnlocalizedName(id);
		
		// Material.
		this.attackDamage = 0.0F;
		this.attackSpeed = 0.0F;
		
		// Features.
		this.isShieldDisabler = false;
		this.constantGemDamage = 0;

		ModItems.registerItem(this, new ResourceLocation(ModInfo.MODID, id)); // Put into registry.
		
		if (CompositeGear.cgTab != null) {
			setCreativeTab(CompositeGear.cgTab);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels(String var1)
	{
	}
	
	@Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity, EntityLivingBase attacker)
    {
		return this.isShieldDisabler;
    }
	
	@Override
    public float getAttackDamage()
    {
        return this.attackDamage;
    }
	
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)(this.getAttackDamage() - 1.0), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)(attackSpeed - 4.0), 0));
        }

        return multimap;
    }

	@Override
	public int getItemEnchantability(ItemStack stack)
	{
		if (!CommonConfig.Enchanting.allowMeleeEnchanting) {
			return 0;
		}
		
		if (this.getToolMaterialName().equals("CG_MELEE_GENERIC")) {
			return this.enchantability;
		}

		return super.getItemEnchantability(stack);
	}
	
	public void setAttackDamage(float attackDamage)
	{
		this.attackDamage = attackDamage;
	}

	public void setAttackSpeed(float attackSpeed)
	{
		this.attackSpeed = attackSpeed;
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
	{
		return CommonConfig.Enchanting.allowMeleeEnchanting;
	}
	
	@Override
	public boolean isColorable()
	{
		return attributes.containsKey("colorable");
	}

    public boolean hasOverlay(ItemStack stack)
    {
        return hasOverlay;
    }

    public ItemCGMelee setHasOverlay(boolean hasOverlay)
    {
    	this.hasOverlay = hasOverlay;
		return this;
    }

    public ItemCGMelee setDefaultColor(int color)
    {
    	this.defaultColor = color;
    	return this;
    }

    // IItemColorable
    @Override
	public boolean hasColorData(ItemStack stack)
	{
		NBTTagCompound nbttagcompound = stack.getTagCompound();
		return nbttagcompound != null && nbttagcompound.hasKey("display", 10) ? nbttagcompound.getCompoundTag("display").hasKey("color", 3) : false;
	}

	@Override
	public int getColorData(ItemStack stack, int colorId)
	{
		NBTTagCompound nbttagcompound = stack.getTagCompound();

		if (nbttagcompound != null)
		{
			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

			if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3))
			{
				return nbttagcompound1.getInteger("color");
			}
		}

		return defaultColor;
	}

    /**
     * Remove the color from the specified melee ItemStack.
     */
    @Override
    public void removeColorData(ItemStack stack)
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

    /**
     * Sets the color of the specified melee ItemStack
     */
    @Override
    public void setColorData(ItemStack stack, int colorId, int color)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            nbttagcompound = new NBTTagCompound();
            stack.setTagCompound(nbttagcompound);
        }

        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

        if (!nbttagcompound.hasKey("display", 10))
        {
            nbttagcompound.setTag("display", nbttagcompound1);
        }

        nbttagcompound1.setInteger("color", color);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack var1)
    {
    	return (EnumRarity)attributes.getOrDefault("rarity", EnumRarity.COMMON);
    }

	public void setEnchantability(int enchantability)
	{
		this.enchantability = enchantability;
	}

	public int getConstantGemDamage()
	{
		return constantGemDamage;
	}

	public void setConstantGemDamage(int constantGemDamage)
	{
		this.constantGemDamage = constantGemDamage;
	}

	protected void setShieldDisabler(boolean isShieldDisabler)
	{
		this.isShieldDisabler = isShieldDisabler;
	}
	
	@Override
	public Map<String, Object> getAttributes()
	{
		return this.attributes;
	}
}
