/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import ic2.api.item.IC2Items;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import techreborn.api.TechRebornAPI;
import tws.zcaliptium.compositegear.common.EnumItemClass;
import tws.zcaliptium.compositegear.common.ModInfo;
import tws.zcaliptium.compositegear.common.crafting.RecipesDyingArmor;
import tws.zcaliptium.compositegear.common.Compats;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.ConfigurationCG;

public class ItemsCG
{
	private static final String COMPOSITE_NAME = "composite";

	// Standart Armor
	public static Item compositeHelmet;
	public static Item compositeChestplate;
	public static Item compositeLeggings;
	public static Item compositeBoots;
	
	public static Item compositeFaceplate;
	
	// Light Armor
	public static Item compositeLightHelmet;
	public static Item compositeLightVest;
	public static Item compositeLightLeggings;
	public static Item compositeLightBoots;

	public static Item compositeSword;
	public static Item compositeBow;
	public static Item compositeDagger;
	public static Item compositeMace;
	
	public static Item respiratorHalfMask;
	public static Item respiratorMask;
	public static Item respiratorMaskComposite;
	public static Item rubberGasmask;
	
	public static Item ushankaHat;
	public static Item balaclavaMask;
	public static Item shemaghMask;
	
	// Materials
	public static Item rubberBall;
	public static Item woodenReel;
	public static Item stringReel;
	public static Item fabricSheet;
	public static Item rubberizedStrap;
	public static Item advAlloyPlate;
	public static Item compositePauldron;
	public static Item compositePoleyn;
	public static Item airMaskModule;
	
	public static ItemStack ic2CompressedAirCell;
	public static ItemStack ic2EmptyCell;
	
	public static ItemStack trCompressedAirCell;
	public static ItemStack trEmptyCell;
	
	public static EnumRarity CG_UNCOMMON = EnumHelper.addRarity("CG_UNCOMMON", TextFormatting.GREEN, "CgUncommon");
	public static EnumRarity CG_RARE = EnumHelper.addRarity("CG_RARE", TextFormatting.BLUE, "CgRare");

	public static void load()
	{
		Item.ToolMaterial compositeToolMaterial = EnumHelper.addToolMaterial("CG_COMPOSITE", 2, 1800, 6.0F, 2.0F, 13);
		
		// TODO: Solve this garbage with materials.

		ItemArmor.ArmorMaterial accessoryArmorMaterial = EnumHelper.addArmorMaterial("CG_ACCESSORY", ModInfo.MODID + ":composite", 100, new int[] { 1, 2, 2, 1 }, 15, null, 0);
		ItemArmor.ArmorMaterial compositeArmorMaterial = EnumHelper.addArmorMaterial("CG_COMPOSITE", ModInfo.MODID + ":composite", 50, new int[] { 3, 6, 9, 3 }, 12, null, 2.0F);
		ItemArmor.ArmorMaterial compositeLightArmorMaterial = EnumHelper.addArmorMaterial("CG_COMPOSITE_LIGHT", ModInfo.MODID + ":composite", 50, new int[] { 2, 4, 6, 2 }, 12, null, 1.0F);
		
		// Standart Armor.
		compositeHelmet = new ItemCompositeArmor("composite_helmet", compositeArmorMaterial, COMPOSITE_NAME, 0, EntityEquipmentSlot.HEAD).setDefaultColor(8815987).setRarity(CG_RARE);
		compositeChestplate = new ItemCompositeArmor("composite_chestplate", compositeArmorMaterial, COMPOSITE_NAME , 0, EntityEquipmentSlot.CHEST).setDefaultColor(8815987).setRarity(CG_RARE);
		compositeLeggings = new ItemCompositeArmor("composite_leggings", compositeArmorMaterial, COMPOSITE_NAME, 0, EntityEquipmentSlot.LEGS).setDefaultColor(8815987).setRarity(CG_RARE);
		compositeBoots = new ItemCompositeArmor("composite_boots", compositeArmorMaterial, COMPOSITE_NAME, 0, EntityEquipmentSlot.FEET).setDefaultColor(8815987).setRarity(CG_RARE);

		compositeFaceplate = new ItemCompositeArmor("composite_faceplate", compositeLightArmorMaterial, "composite_faceplate", 0, EntityEquipmentSlot.HEAD).setDefaultColor(8815987).setRarity(CG_UNCOMMON)
				.setItemClass(EnumItemClass.LIGHT_ARMOR);
		
		// Light Armor.
		compositeLightHelmet = new ItemCompositeArmor("composite_light_helmet", compositeLightArmorMaterial, "composite_light" , 0, EntityEquipmentSlot.HEAD)
				.setDefaultColor(8815987).setRarity(CG_UNCOMMON).setItemClass(EnumItemClass.LIGHT_ARMOR);
		compositeLightVest = new ItemCompositeArmor("composite_light_vest", compositeLightArmorMaterial, "composite_light" , 0, EntityEquipmentSlot.CHEST)
				.setDefaultColor(8815987).setRarity(CG_UNCOMMON).setItemClass(EnumItemClass.LIGHT_ARMOR);
		compositeLightLeggings = new ItemCompositeArmor("composite_light_leggings", compositeLightArmorMaterial, "composite_light" , 0, EntityEquipmentSlot.LEGS)
				.setDefaultColor(8815987).setRarity(CG_UNCOMMON).setItemClass(EnumItemClass.LIGHT_ARMOR);
		compositeLightBoots = new ItemCompositeArmor("composite_light_boots", compositeLightArmorMaterial, "composite_light" , 0, EntityEquipmentSlot.FEET)
				.setDefaultColor(8815987).setRarity(CG_UNCOMMON).setItemClass(EnumItemClass.LIGHT_ARMOR);

		// Respirators
		respiratorHalfMask = new ItemCGArmor("respirator_halfmask", accessoryArmorMaterial, "respirator_halfmask", 0, EntityEquipmentSlot.HEAD)
				.setItemClass(EnumItemClass.ACCESSORY_ARMOR).setAirMask(true).setMinAir(80);

		respiratorMask = new ItemCompositeArmor("respirator_mask", accessoryArmorMaterial, "respirator_mask", 0, EntityEquipmentSlot.HEAD).setDefaultColor(8487297)
				.setItemClass(EnumItemClass.ACCESSORY_ARMOR).setAirMask(true).setMinAir(20).setMaxDamage(150);
		
		respiratorMaskComposite = new ItemCompositeArmor("respirator_mask_composite", compositeArmorMaterial, "respirator_mask_composite", 0, EntityEquipmentSlot.HEAD).setDefaultColor(8815987)
				.setItemClass(EnumItemClass.LIGHT_ARMOR).setAirMask(true).setMinAir(20).setRarity(CG_RARE).setMaxDamage(800);
		
		// Decorative
		ushankaHat = new ItemCompositeArmor("ushanka_hat", accessoryArmorMaterial, "ushanka_hat", 0, EntityEquipmentSlot.HEAD).setDefaultColor(8487297)
				.setItemClass(EnumItemClass.ACCESSORY_ARMOR).setHasDescription(true).setHasVisualAttributes(true).setRarity(CG_UNCOMMON).setMaxDamage(300);
		
		balaclavaMask = new ItemCompositeArmor("balaclava_mask", accessoryArmorMaterial, "balaclava_mask", 0, EntityEquipmentSlot.HEAD).setDefaultColor(8487297)
				.setItemClass(EnumItemClass.ACCESSORY_ARMOR).setHasVisualAttributes(true).setHasDescription(true).setRarity(CG_UNCOMMON);
		
		shemaghMask = new ItemCompositeArmor("shemagh_mask", accessoryArmorMaterial, "shemagh_mask", 0, EntityEquipmentSlot.HEAD).setDefaultColor(8487297)
				.setHasOverlayIcon(true).setItemClass(EnumItemClass.ACCESSORY_ARMOR).setHasVisualAttributes(true).setHasDescription(true).setRarity(CG_UNCOMMON);

		rubberGasmask = new ItemCompositeArmor("rubber_gasmask", accessoryArmorMaterial, "rubber_gasmask", 0, EntityEquipmentSlot.HEAD).setDefaultColor(8487297)
				.setItemClass(EnumItemClass.ACCESSORY_ARMOR).setAirMask(true).setMinAir(20).setMaxDamage(150);
		
		// Weapons
		compositeSword = new ItemCGMelee("composite_sword", compositeToolMaterial).setRarity(CG_RARE);
		compositeMace = new ItemCGMelee("composite_mace", compositeToolMaterial).setRarity(CG_RARE).setHasDescription(true);
		compositeDagger = new ItemCGMelee("composite_dagger", compositeToolMaterial).setRarity(CG_RARE).setMaxDamage(600);
		compositeBow = new ItemCGBow("composite_bow", 2000, 15).setHasDescription(true);

		// Materials.
		rubberBall = new ItemCG("rubber_ball");
		woodenReel = new ItemCG("wooden_reel");
		stringReel = new ItemCG("string_reel");
		fabricSheet = new ItemCG("fabric_sheet");
		rubberizedStrap = new ItemCG("rubberized_strap");
		advAlloyPlate = new ItemCG("advanced_alloy_plate");
		compositePauldron = new ItemCG("composite_pauldron");
		compositePoleyn = new ItemCG("composite_poleyn");
		airMaskModule = new ItemCG("air_mask_module");

		OreDictionary.registerOre("itemRubber", rubberBall);
		OreDictionary.registerOre("plateAdvancedAlloy", advAlloyPlate);

		if (CompositeGear.proxy.isClient()) {
			registerItemModels();
		}

		if (Loader.isModLoaded(Compats.TR))
		{
			trCompressedAirCell = TechRebornAPI.subItemRetriever.getCellByName("compressedair", 1);
			trEmptyCell = TechRebornAPI.subItemRetriever.getCellByName("cell");
			
			if (trCompressedAirCell == null || trEmptyCell == null) {
				CompositeGear.modLog.error("One of TechReborn cells is null! Respirators may not work!");
			}
		}

		if (Loader.isModLoaded(Compats.IC2))
		{
			ic2CompressedAirCell = IC2Items.getItem("fluid_cell", "ic2air");
			ic2EmptyCell = IC2Items.getItem("fluid_cell");
		}
	}

	private static void registerItemModels()
	{
		registerMultiItem(compositeHelmet, "composite_helmet", "items/tool/armor");
		registerMultiItem(compositeChestplate, "composite_chestplate", "items/tool/armor");
		registerMultiItem(compositeLeggings, "composite_leggings", "items/tool/armor");
		registerMultiItem(compositeBoots, "composite_boots", "items/tool/armor");
		
		registerMultiItem(compositeFaceplate, "composite_faceplate", "items/tool/armor");
		
		registerMultiItem(compositeLightHelmet, "composite_light_helmet", "items/tool/armor");
		registerMultiItem(compositeLightVest, "composite_light_vest", "items/tool/armor");
		registerMultiItem(compositeLightLeggings, "composite_light_leggings", "items/tool/armor");
		registerMultiItem(compositeLightBoots, "composite_light_boots", "items/tool/armor");
		
		registerMultiItem(compositeSword, "composite_sword", "items/tool/generic");
		registerMultiItem(compositeDagger, "composite_dagger", "items/tool/generic");
		registerMultiItem(compositeMace, "composite_mace", "items/tool/generic");
		registerItemModel(compositeBow, "tool/composite_bow");
		
		registerMultiItem(respiratorHalfMask, "respirator_halfmask", "items/tool/respirators");
		registerMultiItem(respiratorMask, "respirator_mask", "items/tool/respirators");
		registerMultiItem(respiratorMaskComposite, "respirator_mask_composite", "items/tool/respirators");
		
		registerMultiItem(rubberGasmask, "rubber_gasmask", "items/tool/hats");
		
		registerMultiItem(ushankaHat, "ushanka_hat", "items/tool/hats");
		registerMultiItem(balaclavaMask, "balaclava_mask", "items/tool/hats");
		registerMultiItem(shemaghMask, "shemagh_mask", "items/tool/hats");
		
		// Materials.
		registerMultiItem(rubberBall, "rubber_ball", "items/materials");
		registerMultiItem(woodenReel, "wooden_reel", "items/materials");
		registerMultiItem(stringReel, "string_reel", "items/materials");
		registerMultiItem(fabricSheet, "fabric_sheet", "items/materials");
		registerMultiItem(rubberizedStrap, "rubberized_strap", "items/materials");
		registerMultiItem(advAlloyPlate, "advanced_alloy_plate", "items/materials");
		registerMultiItem(compositePauldron, "composite_pauldron", "items/materials");
		registerMultiItem(compositePoleyn, "composite_poleyn", "items/materials");
		registerMultiItem(airMaskModule, "air_mask_module", "items/materials");
	}

	public static ItemStack getStackNoMeta(Item prototype)
	{
		ItemStack result = new ItemStack(prototype);
		Items.APPLE.setDamage(result, 32767);
		return result;
	}

	private static void registerRecipe(IRecipe recipe)
	{
		recipe.setRegistryName(new ResourceLocation(ModInfo.MODID, "100"));

		if (!Loader.isModLoaded(Compats.IC2) && !Loader.isModLoaded(Compats.TR)) {
			GameRegistry.addSmelting(Items.SLIME_BALL, new ItemStack(rubberBall), 0);
		}

		ForgeRegistries.RECIPES.register(recipe);
	}

	public static void loadRecipes()
	{
		registerRecipe(new RecipesDyingArmor());
	}

	public static Item registerItem(Item item, ResourceLocation rl) {
		item.setRegistryName(rl);
		return registerItem(item);
	}

	public static Item registerItem(Item item) {
		ForgeRegistries.ITEMS.register(item);
		return item;
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemModel(Item item, String name) {
		registerItemModel(item, 0, name);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemModel(Item item, int meta, String name) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(ModInfo.MODID + ":" + name, "inventory"));
	}

	@SideOnly(Side.CLIENT)
	public static void registerMultiItem(Item item, String name, String path) {
		ResourceLocation loc = new ResourceLocation(ModInfo.MODID, path);
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(loc, "type=" + name));
	}
}
