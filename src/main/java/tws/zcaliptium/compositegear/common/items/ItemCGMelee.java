/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tws.zcaliptium.compositegear.client.IItemModelProvider;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.ConfigurationCG;
import tws.zcaliptium.compositegear.common.ModInfo;
import tws.zcaliptium.compositegear.lib.IItemIntelligence;

public class ItemCGMelee extends ItemSword implements IItemIntelligence, IItemModelProvider
{
	// Intelligence
    protected boolean hasDescription;
	protected EnumRarity rarity;

	// Material
	protected int enchantability;
	protected float attackDamage;
	protected float attackSpeed;
	
	public ItemCGMelee(String id, ToolMaterial material)
	{
		super(material);
		
		// Intelligence.
		setUnlocalizedName(id);
		hasDescription = false;
		this.rarity = EnumRarity.COMMON;
		
		// Material
		this.attackDamage = 0.0F;
		this.attackSpeed = 0.0F;

		ItemsCG.registerItem(this, new ResourceLocation(ModInfo.MODID, id)); // Put into registry.
		
		if (CompositeGear.ic2Tab != null) {
			setCreativeTab(CompositeGear.ic2Tab);
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
		return false;
        //return this == ItemsCG.compositeMace; // TODO: Fix this stupid check in future.
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
		if (!ConfigurationCG.allowMeleeEnchanting) {
			return 0;
		}
		
		if (this.getToolMaterialName().equals("CG_GENERIC")) {
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
		return ConfigurationCG.allowMeleeEnchanting;
	}

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack var1)
    {
    	return rarity;
    }

	@Override
	public EnumItemClass getItemClass()
	{
		return EnumItemClass.MELEE_WEAPON;
	}

	@Override
	public boolean hasDescription()
	{
		return this.hasDescription;
	}

	@Override
	public boolean hasVisualAttributes()
	{
		return false;
	}

	@Override
	public void setRarity(EnumRarity rarity)
	{
		this.rarity = rarity;
	}

	@Override
	public void setItemClass(EnumItemClass itemClass) {}

	@Override
	public void setHasDescription(boolean hasDescription)
	{
		this.hasDescription = hasDescription;
	}

	@Override
	public void setHasVisualAttributes(boolean hasVisualAttributes) {}

	public void setEnchantability(int enchantability)
	{
		this.enchantability = enchantability;
	}
}
