/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
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
import net.minecraft.init.SoundEvents;
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

	public static Item respiratorHalfMask;
	public static Item respiratorMask;
	public static Item respiratorMaskComposite;
	public static Item rubberGasmask;

	// Clothing
	public static Item ushankaHat;
	public static Item balaclavaMask;
	public static Item shemaghMask;

	public static Item feltBoots;

	// Weapons
	public static Item compositeSword;
	public static Item compositeBow;
	public static Item compositeDagger;
	public static Item compositeMace;
	public static Item compositeClub;

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

		ItemArmor.ArmorMaterial accessoryArmorMaterial = EnumHelper.addArmorMaterial("CG_ACCESSORY", ModInfo.MODID + ":composite", 100, new int[] { 1, 2, 2, 1 }, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
		ItemArmor.ArmorMaterial compositeArmorMaterial = EnumHelper.addArmorMaterial("CG_COMPOSITE", ModInfo.MODID + ":composite", 50, new int[] { 3, 6, 9, 3 }, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
		ItemArmor.ArmorMaterial compositeLightArmorMaterial = EnumHelper.addArmorMaterial("CG_COMPOSITE_LIGHT", ModInfo.MODID + ":composite", 50, new int[] { 2, 4, 6, 2 }, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F);

		// Standart Armor.
		compositeHelmet = new ItemCGArmor("composite_helmet", compositeArmorMaterial, COMPOSITE_NAME, 0, EntityEquipmentSlot.HEAD).setDefaultColor(8815987)
				.setRarity(CG_RARE).setItemClass(EnumItemClass.MEDIUM_ARMOR).setHasOverlay(true);
		compositeChestplate = new ItemCGArmor("composite_chestplate", compositeArmorMaterial, COMPOSITE_NAME , 0, EntityEquipmentSlot.CHEST).setDefaultColor(8815987)
				.setRarity(CG_RARE).setItemClass(EnumItemClass.MEDIUM_ARMOR).setHasOverlay(true);
		compositeLeggings = new ItemCGArmor("composite_leggings", compositeArmorMaterial, COMPOSITE_NAME, 0, EntityEquipmentSlot.LEGS).setDefaultColor(8815987)
				.setRarity(CG_RARE).setItemClass(EnumItemClass.MEDIUM_ARMOR).setHasOverlay(true);
		compositeBoots = new ItemCGArmor("composite_boots", compositeArmorMaterial, COMPOSITE_NAME, 0, EntityEquipmentSlot.FEET).setDefaultColor(8815987)
				.setRarity(CG_RARE).setItemClass(EnumItemClass.MEDIUM_ARMOR).setHasOverlay(true);

		compositeFaceplate = new ItemCGArmor("composite_faceplate", compositeLightArmorMaterial, "composite_faceplate", 0, EntityEquipmentSlot.HEAD).setDefaultColor(8815987).setRarity(CG_UNCOMMON)
				.setItemClass(EnumItemClass.LIGHT_ARMOR).setHasOverlay(true);

		// Light Armor.
		compositeLightHelmet = new ItemCGArmor("composite_light_helmet", compositeLightArmorMaterial, "composite_light" , 0, EntityEquipmentSlot.HEAD)
				.setDefaultColor(8815987).setRarity(CG_UNCOMMON).setItemClass(EnumItemClass.LIGHT_ARMOR).setHasOverlay(true);
		compositeLightVest = new ItemCGArmor("composite_light_vest", compositeLightArmorMaterial, "composite_light" , 0, EntityEquipmentSlot.CHEST)
				.setDefaultColor(8815987).setRarity(CG_UNCOMMON).setItemClass(EnumItemClass.LIGHT_ARMOR).setHasOverlay(true);
		compositeLightLeggings = new ItemCGArmor("composite_light_leggings", compositeLightArmorMaterial, "composite_light" , 0, EntityEquipmentSlot.LEGS)
				.setDefaultColor(8815987).setRarity(CG_UNCOMMON).setItemClass(EnumItemClass.LIGHT_ARMOR).setHasOverlay(true);
		compositeLightBoots = new ItemCGArmor("composite_light_boots", compositeLightArmorMaterial, "composite_light" , 0, EntityEquipmentSlot.FEET)
				.setDefaultColor(8815987).setRarity(CG_UNCOMMON).setItemClass(EnumItemClass.LIGHT_ARMOR).setHasOverlay(true);

		// Respirators
		respiratorHalfMask = new ItemCGArmor("respirator_halfmask", accessoryArmorMaterial, "respirator_halfmask", 0, EntityEquipmentSlot.HEAD)
				.setItemClass(EnumItemClass.ACCESSORY_ARMOR).setAirMask(true).setMinAir(80);

		respiratorMask = new ItemCGArmor("respirator_mask", accessoryArmorMaterial, "respirator_mask", 0, EntityEquipmentSlot.HEAD).setDefaultColor(8487297)
				.setItemClass(EnumItemClass.ACCESSORY_ARMOR).setAirMask(true).setMinAir(20).setHasOverlay(true).setMaxDamage(150);
		
		respiratorMaskComposite = new ItemCGArmor("respirator_mask_composite", compositeArmorMaterial, "respirator_mask_composite", 0, EntityEquipmentSlot.HEAD).setDefaultColor(8815987)
				.setItemClass(EnumItemClass.LIGHT_ARMOR).setAirMask(true).setMinAir(20).setRarity(CG_RARE).setHasOverlay(true).setMaxDamage(800);
		
		rubberGasmask = new ItemCGArmor("rubber_gasmask", accessoryArmorMaterial, "rubber_gasmask", 0, EntityEquipmentSlot.HEAD).setDefaultColor(8487297)
				.setItemClass(EnumItemClass.ACCESSORY_ARMOR).setAirMask(true).setMinAir(20).setHasOverlay(true).setMaxDamage(150);

		// Clothing
		ushankaHat = new ItemCGArmor("ushanka_hat", accessoryArmorMaterial, "ushanka_hat", 0, EntityEquipmentSlot.HEAD).setDefaultColor(8487297)
				.setItemClass(EnumItemClass.CLOTHING).setHasDescription(true).setHasVisualAttributes(true).setRarity(CG_UNCOMMON).setHasOverlay(true).setMaxDamage(300);
		
		balaclavaMask = new ItemCGArmor("balaclava_mask", accessoryArmorMaterial, "balaclava_mask", 0, EntityEquipmentSlot.HEAD).setDefaultColor(8487297)
				.setItemClass(EnumItemClass.CLOTHING).setHasVisualAttributes(true).setHasDescription(true).setRarity(CG_UNCOMMON).setHasOverlay(true);

		shemaghMask = new ItemCGArmor("shemagh_mask", accessoryArmorMaterial, "shemagh_mask", 0, EntityEquipmentSlot.HEAD).setDefaultColor(8487297)
				.setHasOverlayIcon(true).setItemClass(EnumItemClass.CLOTHING).setHasVisualAttributes(true).setHasDescription(true).setRarity(CG_UNCOMMON).setHasOverlay(true);
		
		feltBoots = new ItemCGArmor("felt_boots", accessoryArmorMaterial, "felt_boots", 0, EntityEquipmentSlot.FEET).setDefaultColor(8487297)
				.setItemClass(EnumItemClass.CLOTHING).setHasDescription(true).setMaxDamage(64);

		// Weapons
		compositeSword = new ItemCGMelee("composite_sword", compositeToolMaterial).setRarity(CG_RARE);
		compositeMace = new ItemCGMelee("composite_mace", compositeToolMaterial).setRarity(CG_RARE).setHasDescription(true);
		compositeClub = new ItemCGMelee("composite_club", compositeToolMaterial).setRarity(CG_RARE).setHasDescription(true);
		compositeDagger = new ItemCGMelee("composite_dagger", compositeToolMaterial).setRarity(CG_RARE).setMaxDamage(600);
		compositeBow = new ItemCGBow("composite_bow", 2000, 15).setHasDescription(true);

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
			
			if (ic2CompressedAirCell == null || ic2EmptyCell == null) {
				CompositeGear.modLog.error("One of IC2 cells is null! Respirators may not work!");
			}
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

		registerMultiItem(feltBoots, "felt_boots", "items/tool/armor");
		
		registerMultiItem(respiratorHalfMask, "respirator_halfmask", "items/tool/respirators");
		registerMultiItem(respiratorMask, "respirator_mask", "items/tool/respirators");
		registerMultiItem(respiratorMaskComposite, "respirator_mask_composite", "items/tool/respirators");
		
		registerMultiItem(rubberGasmask, "rubber_gasmask", "items/tool/hats");
		
		registerMultiItem(ushankaHat, "ushanka_hat", "items/tool/hats");
		registerMultiItem(balaclavaMask, "balaclava_mask", "items/tool/hats");
		registerMultiItem(shemaghMask, "shemagh_mask", "items/tool/hats");
		
		// Weapons
		registerMultiItem(compositeSword, "composite_sword", "items/tool/melee");
		registerMultiItem(compositeDagger, "composite_dagger", "items/tool/melee");
		registerMultiItem(compositeMace, "composite_mace", "items/tool/melee");
		registerMultiItem(compositeClub, "composite_club", "items/tool/melee");
		registerItemModel(compositeBow, "tool/composite_bow");
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

		ForgeRegistries.RECIPES.register(recipe);
	}

	public static void loadRecipes()
	{
		registerRecipe(new RecipesDyingArmor());
		
		/*if (!Loader.isModLoaded(Compats.IC2) && !Loader.isModLoaded(Compats.TR)) {
			GameRegistry.addSmelting(Items.SLIME_BALL, new ItemStack(rubberBall), 0);
		}*/
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
