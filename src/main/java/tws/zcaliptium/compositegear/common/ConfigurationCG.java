/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationCG
{
	public static final String SECTION_CRAFTING = "crafting";
	public static final String SECTION_ENCHANTING = "enchanting";
	public static final String SECTION_COMPAT = "compat";
	public static final String SECTION_GAMEPLAY = "gameplay";

	public static final String SECTION_FEATURES_ARMOR = "features_armor";
	public static final String SECTION_FEATURES_MELEE = "features_melee";

	public static Configuration config;

	public static boolean allowArmorEnchanting = true;
	public static boolean allowMeleeEnchanting = true;
	public static boolean allowRangedEnchanting = true;

	// Mod compatibility.
	public static boolean acCompat = true;
	public static boolean ic2Compat = true;
	public static boolean tanCompat = true;
	public static boolean trCompat = true;
	
	// Armor Features
	public static boolean isFEAirMask = true;
	public static boolean isFEWarm = true;
	public static boolean isFECold = true;
	public static boolean isFESaveSatietyHot = true;
	public static boolean isFESaveSatietyCold = true;

	// Melee Features
	public static boolean isFEConstantGemDamage = true;
	
	public static Map<String, Boolean> CRAFTING_RECIPES = new HashMap<String, Boolean>();
	private static String DISABLEABLE_NAMES[] = new String[] {
		"composite_helmet",
		"composite_chestplate",
		"composite_leggings",
		"composite_boots",

		"composite_faceplate",

		"composite_light_helmet",
		"composite_light_vest",
		"composite_light_leggings",
		"composite_light_boots",

		"composite_sword",
		"composite_dagger",
		"composite_mace",
		"composite_club",
		"composite_bow",

		"respirator_halfmask",
		"respirator_mask",
		"respirator_mask_composite",

		"rubber_gasmask",

		"ushanka_hat",
		"balaclava_mask",
		"shemagh_mask",

		"felt_boots",
	};

	public static void init(File configFile)
	{
	    Configuration config = new Configuration(configFile);
		
	    String allowCraftingNote = "Should be TRUE to allow crafting. FALSE to remove recipe.";

	    try {
		    for (String name : DISABLEABLE_NAMES) {
		    	boolean isAllowed = config.getBoolean(name, SECTION_CRAFTING, true, allowCraftingNote);
		    	CRAFTING_RECIPES.put(name, isAllowed);
		    }

		    allowArmorEnchanting = config.getBoolean("allowArmorEnchanting", SECTION_ENCHANTING, true, "");
		    allowMeleeEnchanting = config.getBoolean("allowMeleeEnchanting", SECTION_ENCHANTING, true, "");
		    allowRangedEnchanting = config.getBoolean("allowRangedEnchanting", SECTION_ENCHANTING, true, "");

		    trCompat = config.getBoolean("techreborn", SECTION_COMPAT, true, "TechReborn integration. Enables usage of TR air cells by air masks.");
		    ic2Compat = config.getBoolean("ic2", SECTION_COMPAT, true, "Industrial Craft 2 Exp. integration. Enables usage of IC2 air cells by air masks. Also magnetizer support for armor.");
		    acCompat = config.getBoolean("applecore", SECTION_COMPAT, true, "AppleCore integration. Gives way to control hunger rate by specific equipment.");
		    tanCompat = config.getBoolean("toughasnails", SECTION_COMPAT, true, "Tough As Nails integration. Equipment will affect body temperature.");
		    
		    // Armor
		    isFEAirMask = config.getBoolean("air_mask", SECTION_FEATURES_ARMOR, true, "Air mask feature. Restores oxygen level with air cells. TR or IC2 integration required.");
		    isFEWarm = config.getBoolean("warm", SECTION_FEATURES_ARMOR, true, "Gives you warmth. TAN integration required.");
		    isFECold = config.getBoolean("cold", SECTION_FEATURES_ARMOR, true, "Chills you down. TAN integration required.");
		    isFESaveSatietyHot = config.getBoolean("save_satiety_hot", SECTION_FEATURES_ARMOR, true, "Saves your energy in hot places. AppleCore integration required.");
		    isFESaveSatietyCold = config.getBoolean("save_satiety_cold", SECTION_FEATURES_ARMOR, true, "Saves your energy in cold places. AppleCore integration required.");

		    // Melee
		    isFEConstantGemDamage = config.getBoolean("constant_gem_damage", SECTION_FEATURES_MELEE, true, "Deals constant damage to most of gem armor. Should work for many armor materials from mods.");
		    
	    } catch (Exception e) {
	      CompositeGear.modLog.error("Unable to load log file!");
	      throw new RuntimeException(e);

	    } finally {
	      config.save();
	    }
	}
}
