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
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
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
import tws.zcaliptium.compositegear.common.EnumItemClass;
import tws.zcaliptium.compositegear.common.ModInfo;
import tws.zcaliptium.compositegear.common.crafting.RecipesDyingArmor;
import tws.zcaliptium.compositegear.common.Compats;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.ConfigurationCG;

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
	
	public static Item rubberGasmask;
	
	public static Item ushankaHat;
	public static Item balaclavaMask;
	public static Item shemaghMask;
	
	public static Item rubberBall;

	public static ItemStack ic2AirCell;
	public static ItemStack ic2EmptyCell;

	public static void load()
	{
		Item.ToolMaterial compositeToolMaterial = EnumHelper.addToolMaterial("CG_COMPOSITE", 2, 1800, 6.0F, 2.0F, 13);
		
		// TODO: Solve this garbage with materials.

		ItemArmor.ArmorMaterial accessoryArmorMaterial = EnumHelper.addArmorMaterial("CG_ACCESSORY", ModInfo.MODID + ":composite", 100, new int[] { 1, 2, 2, 1 }, 15, null, 0);
		ItemArmor.ArmorMaterial compositeMaskArmorMaterial = EnumHelper.addArmorMaterial("CG_MASK_COMPOSITE", ModInfo.MODID + ":composite", 50, new int[] { 3, 9, 6, 3 }, 12, null, 0);
		ItemArmor.ArmorMaterial compositeArmorMaterial = EnumHelper.addArmorMaterial("CG_COMPOSITE", ModInfo.MODID + ":composite", 50, new int[] { 3, 6, 9, 3 }, 12, null, 0);
		
		// Armor
		compositeHelmet = new ItemCompositeArmor("composite_helmet", compositeArmorMaterial, COMPOSITE_NAME, 0, EntityEquipmentSlot.HEAD).setDefaultColor(8815987).setRarity(EnumRarity.UNCOMMON);
		compositeChestplate = new ItemCompositeArmor("composite_chestplate", compositeArmorMaterial, COMPOSITE_NAME , 0, EntityEquipmentSlot.CHEST).setDefaultColor(8815987).setRarity(EnumRarity.UNCOMMON);
		compositeLeggings = new ItemCompositeArmor("composite_leggings", compositeArmorMaterial, COMPOSITE_NAME, 0, EntityEquipmentSlot.LEGS).setDefaultColor(8815987).setRarity(EnumRarity.UNCOMMON);
		compositeBoots = new ItemCompositeArmor("composite_boots", compositeArmorMaterial, COMPOSITE_NAME, 0, EntityEquipmentSlot.FEET).setDefaultColor(8815987).setRarity(EnumRarity.UNCOMMON);

		// Respirators
		respiratorHalfMask = new ItemCGArmor("respirator_halfmask", accessoryArmorMaterial, "respirator_halfmask", 0, EntityEquipmentSlot.HEAD)
				.setItemClass(EnumItemClass.ACCESSORY_ARMOR).setAirMask(true).setMinAir(80);

		respiratorMask = new ItemCGArmor("respirator_mask", accessoryArmorMaterial, "respirator_mask", 0, EntityEquipmentSlot.HEAD)
				.setItemClass(EnumItemClass.ACCESSORY_ARMOR).setAirMask(true).setMinAir(20).setMaxDamage(150);
		
		respiratorMaskComposite = new ItemCGArmor("respirator_mask_composite", compositeMaskArmorMaterial, "respirator_mask_composite", 0, EntityEquipmentSlot.HEAD)
				.setItemClass(EnumItemClass.LIGHT_ARMOR).setAirMask(true).setMinAir(20).setRarity(EnumRarity.UNCOMMON).setMaxDamage(200);
		
		// Decorative
		ushankaHat = new ItemCompositeArmor("ushanka_hat", accessoryArmorMaterial, "ushanka_hat", 0, EntityEquipmentSlot.HEAD).setDefaultColor(8487297)
				.setItemClass(EnumItemClass.ACCESSORY_ARMOR).setHasDescription(true).setHasVisualAttributes(true).setMaxDamage(300);
		
		balaclavaMask = new ItemCompositeArmor("balaclava_mask", accessoryArmorMaterial, "balaclava_mask", 0, EntityEquipmentSlot.HEAD).setDefaultColor(8487297)
				.setItemClass(EnumItemClass.ACCESSORY_ARMOR).setHasVisualAttributes(true).setHasDescription(true);
		
		shemaghMask = new ItemCompositeArmor("shemagh_mask", accessoryArmorMaterial, "shemagh_mask", 0, EntityEquipmentSlot.HEAD).setDefaultColor(8487297)
				.setHasOverlayIcon(true).setItemClass(EnumItemClass.ACCESSORY_ARMOR).setHasVisualAttributes(true).setHasDescription(true);

		rubberGasmask = new ItemCompositeArmor("rubber_gasmask", accessoryArmorMaterial, "rubber_gasmask", 0, EntityEquipmentSlot.HEAD).setDefaultColor(8487297)
				.setItemClass(EnumItemClass.ACCESSORY_ARMOR).setMaxDamage(150);
		
		// Weapons
		compositeSword = new ItemCGSword("composite_sword", compositeToolMaterial);
		compositeDagger = new ItemCGSword("composite_dagger", compositeToolMaterial).setMaxDamage(600);
		compositeBow = new ItemCGBow("composite_bow", 2000, 15).setHasDescription(true);
		
		//
		rubberBall = new ItemCG("rubber_ball");
		OreDictionary.registerOre("itemRubber", rubberBall);

		if (CompositeGear.proxy.isClient()) {
			registerItemModels();
		}
		
		/*if (Loader.isModLoaded(Compats.IC2))
		{
			ic2AirCell = IC2Items.getItem("fluid_cell", "ic2air");
			ic2EmptyCell = IC2Items.getItem("fluid_cell");
		}*/
	}
	
	private static void registerItemModels()
	{
		registerMultiItem(compositeHelmet, "composite_helmet", "items/tool/armor");
		registerMultiItem(compositeChestplate, "composite_chestplate", "items/tool/armor");
		registerMultiItem(compositeLeggings, "composite_leggings", "items/tool/armor");
		registerMultiItem(compositeBoots, "composite_boots", "items/tool/armor");
		
		registerMultiItem(compositeSword, "composite_sword", "items/tool/generic");
		registerMultiItem(compositeDagger, "composite_dagger", "items/tool/generic");
		registerItemModel(compositeBow, "tool/composite_bow");
		
		registerMultiItem(respiratorHalfMask, "respirator_halfmask", "items/tool/respirators");
		registerMultiItem(respiratorMask, "respirator_mask", "items/tool/respirators");
		registerMultiItem(respiratorMaskComposite, "respirator_mask_composite", "items/tool/respirators");
		
		registerMultiItem(rubberGasmask, "rubber_gasmask", "items/tool/hats");
		
		registerMultiItem(ushankaHat, "ushanka_hat", "items/tool/hats");
		registerMultiItem(balaclavaMask, "balaclava_mask", "items/tool/hats");
		registerMultiItem(shemaghMask, "shemagh_mask", "items/tool/hats");
		
		registerMultiItem(rubberBall, "rubber_ball", "items/materials");
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
