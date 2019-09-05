/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.Loader;
import tws.zcaliptium.compositegear.common.ModInfo;
import tws.zcaliptium.compositegear.common.compat.IC2Compat;
import tws.zcaliptium.compositegear.common.compat.TRCompat;
import tws.zcaliptium.compositegear.common.crafting.FurnaceRecipeHelper;
import tws.zcaliptium.compositegear.common.crafting.RecipesDyingEquipment;
import tws.zcaliptium.compositegear.common.items.ArmorItemFactory;
import tws.zcaliptium.compositegear.common.items.CrossbowItemFactory;
import tws.zcaliptium.compositegear.common.items.GenericItemFactory;
import tws.zcaliptium.compositegear.common.items.ItemCGArmor;
import tws.zcaliptium.compositegear.common.items.ItemHelper;
import tws.zcaliptium.compositegear.common.items.MeleeItemFactory;
import tws.zcaliptium.compositegear.common.items.RangedItemFactory;
import tws.zcaliptium.compositegear.common.Compats;
import tws.zcaliptium.compositegear.common.CompositeGear;

public class ModItems
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
    	// Generic factories.
    	ItemHelper.factories.put(new ResourceLocation(ModInfo.MODID, "generic"), new GenericItemFactory());
    	ItemHelper.factories.put(new ResourceLocation(ModInfo.MODID, "armor"), new ArmorItemFactory());
    	ItemHelper.factories.put(new ResourceLocation(ModInfo.MODID, "melee_weapon"), new MeleeItemFactory());
    	ItemHelper.factories.put(new ResourceLocation(ModInfo.MODID, "ranged_weapon"), new RangedItemFactory());
    	ItemHelper.factories.put(new ResourceLocation(ModInfo.MODID, "crossbow"), new CrossbowItemFactory());

    	ItemHelper.loadItems(CompositeGear.container);
		
		if (Loader.isModLoaded(Compats.TR))
		{
			TRCompat.load();
		}

		if (Loader.isModLoaded(Compats.IC2))
		{
			IC2Compat.load();
		}
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
		
		FurnaceRecipeHelper.loadItems(CompositeGear.container);
	}
	
	public static void defineRepairMaterials()
	{
	    for(Map.Entry<Item, ResourceLocation> entry : ModItems.REPAIR_ITEMS_REGISTRY.entrySet())
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

	public static Item registerItem(Item item, ResourceLocation rl)
	{
		item.setRegistryName(rl);
		return registerItem(item);
	}

	public static Item registerItem(Item item)
	{
		ForgeRegistries.ITEMS.register(item);

		return item;
	}
}
