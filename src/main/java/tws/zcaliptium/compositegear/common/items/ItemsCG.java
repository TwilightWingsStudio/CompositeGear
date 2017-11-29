/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.IC2Items;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import tws.zcaliptium.compositegear.common.ConfigurationCG;
import tws.zcaliptium.compositegear.common.EnumItemClass;
import tws.zcaliptium.compositegear.common.item.crafting.RecipesDyingArmor;

public class ItemsCG
{
	private static final String COMPOSITE_NAME = "composite";

	public static Item compositeHelmet;
	public static Item compositeChestplate;
	public static Item compositeLeggings;
	public static Item compositeBoots;
	
	public static Item compositeSword;
	public static Item compositeBow;
	public static Item compositeDagger;
	
	public static Item respiratorHalfMask;

	public static void load()
	{
		ItemArmor.ArmorMaterial accessoryArmorMaterial = EnumHelper.addArmorMaterial("CG_ACCESSORY", 100, new int[] { 1, 2, 2, 1 }, 15);
		ItemArmor.ArmorMaterial compositeArmorMaterial = EnumHelper.addArmorMaterial("CG_COMPOSITE", 50, new int[] { 3, 9, 6, 3 }, 12);
		Item.ToolMaterial compositeToolMaterial = EnumHelper.addToolMaterial("CG_COMPOSITE", 2, 1800, 6.0F, 2.0F, 13);
		Item.ToolMaterial compositeDaggerMaterial = EnumHelper.addToolMaterial("CG_COMPOSITE_DAGGER", 2, 600, 6.0F, 0.0F, 15);
		
		compositeHelmet = new ItemCompositeArmor("composite_helmet", compositeArmorMaterial, COMPOSITE_NAME, 0, 0);
		compositeChestplate = new ItemCompositeArmor("composite_chestplate", compositeArmorMaterial, COMPOSITE_NAME , 0, 1);
		compositeLeggings = new ItemCompositeArmor("composite_leggings", compositeArmorMaterial, COMPOSITE_NAME, 0, 2);
		compositeBoots = new ItemCompositeArmor("composite_boots", compositeArmorMaterial, COMPOSITE_NAME, 0, 3);
		
		respiratorHalfMask = new ItemCGArmor("respirator_halfmask", accessoryArmorMaterial, "respirator_halfmask", 0, 0).setItemClass(EnumItemClass.ACCESSORY_ARMOR);
		
		// Weapons
		compositeSword = new ItemCGSword("composite_sword", compositeToolMaterial);
		compositeDagger = new ItemCGSword("composite_dagger", compositeDaggerMaterial);
		compositeBow = new ItemCGBow("composite_bow", 2000, 15);
	}

	public static ItemStack getStackNoMeta(Item prototype)
	{
		ItemStack result = new ItemStack(prototype);
		Items.apple.setDamage(result, 32767);
		return result;
	}

	// TODO: Find way to automate it and get rid of this ugly code.
	@Optional.Method(modid = "IC2")
	public static void loadRecipes()
	{
    	GameRegistry.addRecipe(new RecipesDyingArmor());
    	
    	ItemStack advancedAlloy = IC2Items.getItem("advancedAlloy");

    	if (ConfigurationCG.compositeHelmet) {
    		GameRegistry.addRecipe(new ItemStack(compositeHelmet, 1, 0), new Object[] { "AIA", "ALA", Character.valueOf('A'), advancedAlloy, Character.valueOf('I'), getStackNoMeta(Items.iron_helmet), Character.valueOf('L'), getStackNoMeta(Items.leather_helmet)});
    	}
		
		if (ConfigurationCG.compositeChestplate) {
			GameRegistry.addRecipe(new ItemStack(compositeChestplate, 1, 0), new Object[] { "AIA", "ALA", "AAA", Character.valueOf('A'), advancedAlloy, Character.valueOf('I'), getStackNoMeta(Items.iron_chestplate), Character.valueOf('L'), getStackNoMeta(Items.leather_chestplate)});
		}

		if (ConfigurationCG.compositeLeggings) {
			GameRegistry.addRecipe(new ItemStack(compositeLeggings, 1, 0), new Object[] { "AAA", "ALA", "AIA", Character.valueOf('A'), advancedAlloy, Character.valueOf('I'), getStackNoMeta(Items.iron_leggings), Character.valueOf('L'), getStackNoMeta(Items.leather_leggings)});
		}
		
		if (ConfigurationCG.compositeBoots) {
			GameRegistry.addRecipe(new ItemStack(compositeBoots, 1, 0), new Object[] { "ALA", "AIA", Character.valueOf('A'), advancedAlloy, Character.valueOf('I'), getStackNoMeta(Items.iron_boots), Character.valueOf('L'), getStackNoMeta(Items.leather_boots)});
		}
		
		GameRegistry.addRecipe(new ItemStack(respiratorHalfMask, 1, 0), new Object[] { "RWR", "IDI", "RBR", Character.valueOf('R'), IC2Items.getItem("rubber"), Character.valueOf('W'), Blocks.wool, Character.valueOf('B'), Blocks.iron_bars, Character.valueOf('I'), IC2Items.getItem("plateiron"), Character.valueOf('D'), IC2Items.getItem("coalDust")});
		
		// WEAPONS

		if (ConfigurationCG.compositeSword) {
			GameRegistry.addRecipe(new ItemStack(compositeSword, 1, 0), new Object[] { "A", "A", "I", Character.valueOf('A'), advancedAlloy, Character.valueOf('I'), getStackNoMeta(Items.iron_sword)});
		}

		if (ConfigurationCG.compositeDagger) {
			GameRegistry.addRecipe(new ItemStack(compositeDagger, 1, 0), new Object[] { "A", "I", Character.valueOf('A'), advancedAlloy, Character.valueOf('I'), Items.stick});
		}
		
		if (ConfigurationCG.compositeBow) {
			GameRegistry.addRecipe(new ItemStack(compositeBow, 1, 0), new Object[] { "CAA", "ABC", "AC ", Character.valueOf('A'), advancedAlloy, Character.valueOf('C'), IC2Items.getItem("carbonPlate"), Character.valueOf('B'), getStackNoMeta(Items.bow)});
		}
	}
}
