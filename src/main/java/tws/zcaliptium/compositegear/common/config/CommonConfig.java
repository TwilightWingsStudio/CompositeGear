/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.config;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import net.minecraftforge.common.config.Configuration;

public class CommonConfig
{
	public static Configuration config;

	public static final String SECTION_COMMON = "common";

	public static final String SECTION_CRAFTING = "common.crafting";
	public static final String SECTION_ENCHANTING = "common.enchanting";
	public static final String SECTION_COMPAT = "common.compat";
	public static final String SECTION_GAMEPLAY = "common.gameplay";

	public static final String SECTION_FEATURES_ARMOR = "common.features_armor";
	public static final String SECTION_FEATURES_MELEE = "common.features_melee";

	private static final String[] DEFAULT_CRAFTING_BLACKLIST = new String[]{};

	// Enchanting.
	public static class Enchanting
	{
		public static boolean allowRangedEnchanting = true;
		public static boolean allowArmorEnchanting = true;
		public static boolean allowMeleeEnchanting = true;
	}

	// Gameplay.
	public static class Gameplay
	{
		public static boolean customLootTables = true;
	}

	// Mod compatibility.
	public static class Compat
	{
		public static boolean applecore = true;
		public static boolean ic2 = true;
		public static boolean tan = true;
		public static boolean techreborn = true;
	}

	// Armor Features
	public static class ArmorFeatures
	{
		public static boolean airMask = true;
		public static boolean warm = true;
		public static boolean cold = true;
		public static boolean saveSatietyHot = true;
		public static boolean saveSatietyCold = true;
		public static boolean safeFall = true;
	}

	// Melee Features
	public static class MeleeFeatures
	{
		public static boolean constantGemDamage = true;
	}

	public static class Crafting
	{
		public static Set<String> ITEM_BLACKLIST = new HashSet();
	}

	public static void init(File configFile)
	{
	    config = new Configuration(configFile);
	    config.load();

	    load();
	}
	
	public static void load()
	{
    	String[] itemBlacklist = config.getStringList("craftingBlacklist", SECTION_CRAFTING, DEFAULT_CRAFTING_BLACKLIST, "List of mod items that should be uncraftable. Put item names without mod namespace.\nThe option works for only items from this mod. Example: composite_sword\n");

    	if (itemBlacklist.length > 0) {
    		for (String name : itemBlacklist) {	    			
    			Crafting.ITEM_BLACKLIST.add(name);
    		}
    	}

		// Enchanting.
	    Enchanting.allowArmorEnchanting = config.getBoolean("allowArmorEnchanting", SECTION_ENCHANTING, true, "Armor and clothing.");
	    Enchanting.allowMeleeEnchanting = config.getBoolean("allowMeleeEnchanting", SECTION_ENCHANTING, true, "Melee weapons.");
	    Enchanting.allowRangedEnchanting = config.getBoolean("allowRangedEnchanting", SECTION_ENCHANTING, true, "Bows / Crossbows.");
	    
	    Gameplay.customLootTables = config.getBoolean("customLootTables", SECTION_GAMEPLAY, true, "Enables custom loot tables. Currently affects only vanilla animals to drop animal hides instead of leather.");

	    // Compat
	    Compat.techreborn = config.getBoolean("techreborn", SECTION_COMPAT, true, "TechReborn integration. Enables usage of TR air cells by air masks.");
	    Compat.ic2 = config.getBoolean("ic2", SECTION_COMPAT, true, "Industrial Craft 2 Exp. integration. Enables usage of IC2 air cells by air masks. Also magnetizer support for armor.");
	    Compat.applecore = config.getBoolean("applecore", SECTION_COMPAT, true, "AppleCore integration. Gives way to control hunger rate by specific equipment.");
	    Compat.tan = config.getBoolean("toughasnails", SECTION_COMPAT, true, "Tough As Nails integration. Equipment will affect body temperature.");

	    // Armor
	    ArmorFeatures.airMask = config.getBoolean("air_mask", SECTION_FEATURES_ARMOR, true, "Air mask feature. Restores oxygen level with air cells.\nTR or IC2 should be installed. Also integration should be enabled for these mods.");
	    ArmorFeatures.warm = config.getBoolean("warm", SECTION_FEATURES_ARMOR, true, "Gives you warmth. TAN integration required.");
	    ArmorFeatures.cold = config.getBoolean("cold", SECTION_FEATURES_ARMOR, true, "Chills you down. TAN integration required.");
	    ArmorFeatures.saveSatietyHot = config.getBoolean("save_satiety_hot", SECTION_FEATURES_ARMOR, true, "Saves your energy in hot places. AppleCore integration required.");
	    ArmorFeatures.saveSatietyCold = config.getBoolean("save_satiety_cold", SECTION_FEATURES_ARMOR, true, "Saves your energy in cold places. AppleCore integration required.");
	    ArmorFeatures.safeFall = config.getBoolean("safe_fall", SECTION_FEATURES_ARMOR, true, "Increases safe fall distance. Some boots added by mod give such bonus.");
	    
	    // Melee
	    MeleeFeatures.constantGemDamage = config.getBoolean("constant_gem_damage", SECTION_FEATURES_MELEE, true, "Deals constant damage to most of gem armor. Should work for many armor materials from mods.");
	
	    if (config.hasChanged()) config.save();
	}
	
	public static void save()
	{
		config.save();
	}
}
