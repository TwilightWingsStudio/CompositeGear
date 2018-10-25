/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import ic2.api.item.IC2Items;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
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
import tws.zcaliptium.compositegear.common.compat.IC2Compat;
import tws.zcaliptium.compositegear.common.compat.TRCompat;
import tws.zcaliptium.compositegear.common.crafting.RecipesDyingEquipment;
import tws.zcaliptium.compositegear.common.Compats;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.ConfigurationCG;

public class ItemsCG
{
    public static List<Item> COLORABLE_REGISTRY = Lists.<Item>newArrayList();
    public static Map<Item, ResourceLocation> REPAIR_ITEMS_REGISTRY = new HashMap<Item, ResourceLocation>();
    public static Map<ResourceLocation, ResourceLocation> GUI_MODELS_REGISTRY = new HashMap<ResourceLocation, ResourceLocation>();

	public static EnumRarity CG_UNCOMMON = EnumHelper.addRarity("CG_UNCOMMON", TextFormatting.GREEN, "CgUncommon");
	public static EnumRarity CG_RARE = EnumHelper.addRarity("CG_RARE", TextFormatting.BLUE, "CgRare");
	public static EnumRarity CG_EPIC = EnumHelper.addRarity("CG_EPIC", TextFormatting.LIGHT_PURPLE, "CgEpic");
	public static EnumRarity CG_LEGENDARY = EnumHelper.addRarity("CG_LEGENDARY", TextFormatting.GOLD, "CgLegendary");

	public static void load()
	{
		if (Loader.isModLoaded(Compats.TR))
		{
			TRCompat.load();
		}

		if (Loader.isModLoaded(Compats.IC2))
		{
			IC2Compat.load();
		}
		
		/*
		if (CompositeGear.proxy.isClient()) {
			
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(ModInfo.MODID, "respirator_test"));
			
			ModelResourceLocation mrl2 = new ModelResourceLocation(new ResourceLocation(ModInfo.MODID, "respirator_test"), "2d");
			ModelBakery.registerItemVariants(item, mrl2);
		}
		*/
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
		registerRecipe(new RecipesDyingEquipment());
		
		/*if (!Loader.isModLoaded(Compats.IC2) && !Loader.isModLoaded(Compats.TR)) {
			GameRegistry.addSmelting(Items.SLIME_BALL, new ItemStack(rubberBall), 0);
		}*/
	}
	
	public static void defineRepairMaterials()
	{
	    for(Map.Entry<Item, ResourceLocation> entry : ItemsCG.REPAIR_ITEMS_REGISTRY.entrySet())
	    {
	    	Item keyItem = entry.getKey();

	    	if (keyItem instanceof ItemCGArmor) {
	    		Item repairItem = Item.REGISTRY.getObject(entry.getValue());
	    		
	    		if (repairItem == null) {
	    			CompositeGear.modLog.error("Unable to define repair material for '" + keyItem.getRegistryName() + "' - '" + entry.getValue() + "'");
	    		}
	    		
	    		((ItemCGArmor)keyItem).setRepairItem(repairItem);
	    	}
	    }
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
	public static void registerItemModelFull(Item item, int meta, String path, String variant) {
		ResourceLocation loc = new ResourceLocation(ModInfo.MODID, path);
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(loc, variant));
	}

	@SideOnly(Side.CLIENT)
	public static void registerMultiItem(Item item, String name, String path) {
		ResourceLocation loc = new ResourceLocation(ModInfo.MODID, path);
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(loc, "type=" + name));
	}
}
