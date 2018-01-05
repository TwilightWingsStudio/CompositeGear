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
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tws.zcaliptium.compositegear.common.EnumItemClass;
import tws.zcaliptium.compositegear.common.Compats;
import tws.zcaliptium.compositegear.common.ConfigurationCG;
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
	public static Item respiratorMask;
	public static Item respiratorMaskComposite;
	
	public static Item ushankaHat;
	public static Item balaclavaMask;
	public static Item shemaghMask;

	public static ItemStack ic2AirCell;
	public static ItemStack ic2EmptyCell;
	public static ItemStack ic2AdvancedAlloy;
	public static ItemStack ic2CarbonPlate;
	public static ItemStack ic2ReinforcedGlass;

	public static void load()
	{
		// TODO: Solve this garbage with materials.
		ItemArmor.ArmorMaterial accessoryArmorMaterial = EnumHelper.addArmorMaterial("CG_ACCESSORY", 100, new int[] { 1, 2, 2, 1 }, 15);
		ItemArmor.ArmorMaterial compositeMaskArmorMaterial = EnumHelper.addArmorMaterial("CG_MASK_COMPOSITE", 50, new int[] { 3, 9, 6, 3 }, 12);
		ItemArmor.ArmorMaterial compositeArmorMaterial = EnumHelper.addArmorMaterial("CG_COMPOSITE", 50, new int[] { 3, 9, 6, 3 }, 12);
		Item.ToolMaterial compositeToolMaterial = EnumHelper.addToolMaterial("CG_COMPOSITE", 2, 1800, 6.0F, 2.0F, 13);
		Item.ToolMaterial compositeDaggerMaterial = EnumHelper.addToolMaterial("CG_COMPOSITE_DAGGER", 2, 600, 6.0F, 0.0F, 15);

		compositeHelmet = new ItemCompositeArmor("composite_helmet", compositeArmorMaterial, COMPOSITE_NAME, 0, 0).setDefaultColor(8815987).setRarity(EnumRarity.uncommon);
		compositeChestplate = new ItemCompositeArmor("composite_chestplate", compositeArmorMaterial, COMPOSITE_NAME , 0, 1).setDefaultColor(8815987).setRarity(EnumRarity.uncommon);
		compositeLeggings = new ItemCompositeArmor("composite_leggings", compositeArmorMaterial, COMPOSITE_NAME, 0, 2).setDefaultColor(8815987).setRarity(EnumRarity.uncommon);
		compositeBoots = new ItemCompositeArmor("composite_boots", compositeArmorMaterial, COMPOSITE_NAME, 0, 3).setDefaultColor(8815987).setRarity(EnumRarity.uncommon);

		respiratorHalfMask = new ItemCGArmor("respirator_halfmask", accessoryArmorMaterial, "respirator_halfmask", 0, 0)
				.setItemClass(EnumItemClass.ACCESSORY_ARMOR).setAirMask(true).setMinAir(80);

		respiratorMask = new ItemCGArmor("respirator_mask", accessoryArmorMaterial, "respirator_mask", 0, 0)
				.setItemClass(EnumItemClass.ACCESSORY_ARMOR).setAirMask(true).setMinAir(20).setMaxDamage(150);
		
		respiratorMaskComposite = new ItemCGArmor("respirator_mask_composite", compositeMaskArmorMaterial, "respirator_mask_composite", 0, 0)
				.setItemClass(EnumItemClass.LIGHT_ARMOR).setAirMask(true).setMinAir(20).setRarity(EnumRarity.uncommon).setMaxDamage(400);
		
		ushankaHat = new ItemCompositeArmor("ushanka_hat", accessoryArmorMaterial, "ushanka_hat", 0, 0).setDefaultColor(8487297)
				.setItemClass(EnumItemClass.ACCESSORY_ARMOR).setHasDescription(true).setMaxDamage(300);
		
		balaclavaMask = new ItemCompositeArmor("balaclava_mask", accessoryArmorMaterial, "balaclava_mask", 0, 0).setDefaultColor(8487297)
				.setItemClass(EnumItemClass.ACCESSORY_ARMOR).setHasDescription(true);
		
		shemaghMask = new ItemCompositeArmor("shemagh_mask", accessoryArmorMaterial, "shemagh_mask", 0, 0).setDefaultColor(8487297)
				.setHasOverlayIcon(true).setItemClass(EnumItemClass.ACCESSORY_ARMOR).setHasDescription(true);

		// Weapons
		compositeSword = new ItemCGSword("composite_sword", compositeToolMaterial);
		compositeDagger = new ItemCGSword("composite_dagger", compositeDaggerMaterial);
		compositeBow = new ItemCGBow("composite_bow", 2000, 15);

		loadIC2Items();
	}

	public static ItemStack getStackNoMeta(Item prototype)
	{
		ItemStack result = new ItemStack(prototype);
		Items.apple.setDamage(result, 32767);
		return result;
	}

	@Optional.Method(modid = Compats.IC2)
	public static void loadIC2Items()
	{
		ic2AirCell = IC2Items.getItem("airCell");
		ic2EmptyCell = IC2Items.getItem("cell");

		ic2AdvancedAlloy = IC2Items.getItem("advancedAlloy");
		ic2CarbonPlate = IC2Items.getItem("carbonPlate");
		ic2ReinforcedGlass = IC2Items.getItem("reinforcedGlass");
	}

	// TODO: Find way to automate it and get rid of this ugly code.
	@Optional.Method(modid = Compats.IC2)
	public static void loadRecipes()
	{
    	GameRegistry.addRecipe(new RecipesDyingArmor());

    	if (ConfigurationCG.compositeHelmet) {
    		GameRegistry.addRecipe(new ItemStack(compositeHelmet, 1, 0), new Object[] { "AIA", "ALA", Character.valueOf('A'), ic2AdvancedAlloy,
    				Character.valueOf('I'), getStackNoMeta(Items.iron_helmet), Character.valueOf('L'), getStackNoMeta(Items.leather_helmet)});
    	}

		if (ConfigurationCG.compositeChestplate) {
			GameRegistry.addRecipe(new ItemStack(compositeChestplate, 1, 0), new Object[] { "AIA", "ALA", "AAA", Character.valueOf('A'), ic2AdvancedAlloy,
					Character.valueOf('I'), getStackNoMeta(Items.iron_chestplate), Character.valueOf('L'), getStackNoMeta(Items.leather_chestplate)});
		}

		if (ConfigurationCG.compositeLeggings) {
			GameRegistry.addRecipe(new ItemStack(compositeLeggings, 1, 0), new Object[] { "AAA", "ALA", "AIA", Character.valueOf('A'),
					ic2AdvancedAlloy, Character.valueOf('I'), getStackNoMeta(Items.iron_leggings), Character.valueOf('L'), getStackNoMeta(Items.leather_leggings)});
		}

		if (ConfigurationCG.compositeBoots) {
			GameRegistry.addRecipe(new ItemStack(compositeBoots, 1, 0), new Object[] { "ALA", "AIA", Character.valueOf('A'),
					ic2AdvancedAlloy, Character.valueOf('I'), getStackNoMeta(Items.iron_boots), Character.valueOf('L'), getStackNoMeta(Items.leather_boots)});
		}

		if (ConfigurationCG.respiratorHalfMask) {
			GameRegistry.addRecipe(new ShapedOreRecipe( new ItemStack(respiratorHalfMask, 1, 0), new Object[] { "RWR", "IDI", "RBR", Character.valueOf('R'), "itemRubber",
					Character.valueOf('W'), Blocks.wool, Character.valueOf('B'), Blocks.iron_bars, Character.valueOf('I'), "plateIron", Character.valueOf('D'), "dustCoal"}));
		}

		if (ConfigurationCG.respiratorMask) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(respiratorMask, 1, 0), new Object[] { "RIR", "IGI", "RMR", Character.valueOf('R'), "itemRubber",
					Character.valueOf('G'), Blocks.glass_pane, Character.valueOf('I'), "plateIron", Character.valueOf('M'), getStackNoMeta(respiratorHalfMask)}));
		}
		
		if (ConfigurationCG.respiratorMaskComposite) {
			GameRegistry.addRecipe(new ItemStack(respiratorMaskComposite, 1, 0), new Object[] { "CAC", "AGA", "CMC", Character.valueOf('A'), ic2AdvancedAlloy,
					Character.valueOf('G'), ic2ReinforcedGlass, Character.valueOf('C'), ic2CarbonPlate, Character.valueOf('M'), getStackNoMeta(respiratorMask)});

		}
		
		// DECORATIVE
		
		if (ConfigurationCG.ushankaHat) {
			GameRegistry.addRecipe(new ItemStack(ushankaHat, 1, 0), new Object[] { "LWL", "LWL", "S S", Character.valueOf('L'), Items.leather,
					Character.valueOf('W'), Blocks.wool, Character.valueOf('S'), Items.string});
		}
		
		if (ConfigurationCG.balaclavaMask) {
			GameRegistry.addRecipe(new ItemStack(balaclavaMask, 1, 0), new Object[] { "SWS", "WSW", Character.valueOf('W'), Blocks.wool,
					Character.valueOf('S'), Items.string});
		}
		
		if (ConfigurationCG.shemaghMask) {
			GameRegistry.addRecipe(new ItemStack(shemaghMask, 1, 0), new Object[] { "WWW", "S S", "SWS", Character.valueOf('W'), Blocks.wool,
					Character.valueOf('S'), Items.string});
		}

		// WEAPONS

		if (ConfigurationCG.compositeSword) {
			GameRegistry.addRecipe(new ItemStack(compositeSword, 1, 0), new Object[] { "A", "A", "I", Character.valueOf('A'), ic2AdvancedAlloy,
					Character.valueOf('I'), getStackNoMeta(Items.iron_sword)});
		}

		if (ConfigurationCG.compositeDagger) {
			GameRegistry.addRecipe(new ItemStack(compositeDagger, 1, 0), new Object[] { "A", "I", Character.valueOf('A'), ic2AdvancedAlloy,
					Character.valueOf('I'), Items.stick});
		}

		if (ConfigurationCG.compositeBow) {
			GameRegistry.addRecipe(new ItemStack(compositeBow, 1, 0), new Object[] { "CAA", "ABC", "AC ", Character.valueOf('A'), ic2AdvancedAlloy,
					Character.valueOf('C'), ic2CarbonPlate, Character.valueOf('B'), getStackNoMeta(Items.bow)});
		}
	}
}
