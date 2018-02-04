/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
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

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tws.zcaliptium.compositegear.client.IItemModelProvider;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.EnumItemClass;
import tws.zcaliptium.compositegear.common.IClassifiedItem;
import tws.zcaliptium.compositegear.common.ModInfo;

public class ItemCGSword extends ItemSword implements IClassifiedItem, IItemModelProvider
{
	private static double SWORD_SPEED_MODIFIER = -2.4000000953674316D;
	private static double DAGGER_SPEED_MODIFIER = -0.8D;
	
	public ItemCGSword(String id, ToolMaterial material)
	{
		super(material);
		
		setUnlocalizedName(id);

		ItemsCG.registerItem(this, new ResourceLocation(ModInfo.MODID, id)); // Put into registry.
		
		if (CompositeGear.ic2Tab != null) {
			setCreativeTab(CompositeGear.ic2Tab);
		}
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack var1)
    {
    	return EnumRarity.UNCOMMON;
    }

	@Override
	public EnumItemClass getItemClass() {
		return EnumItemClass.MELEE_WEAPON;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels(String var1)
	{
	}
	
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
        	double speedModifier = SWORD_SPEED_MODIFIER;
        	
        	// TODO: Fix this stupid check in future.
        	if (stack.getItem() == ItemsCG.compositeDagger) {
        		speedModifier = DAGGER_SPEED_MODIFIER;
        	}
        	
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 3.0D + (double)this.getAttackDamage(), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", speedModifier, 0));
        }

        return multimap;
    }
}
