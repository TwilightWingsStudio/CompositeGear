/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationCG
{
	public static final String SECTION_CRAFTING = "crafting";
	
	public static Configuration config;
	
	public static boolean compositeHelmet;
	public static boolean compositeChestplate;
	public static boolean compositeLeggings;
	public static boolean compositeBoots;
	
	public static boolean compositeSword;
	public static boolean compositeDagger;
	public static boolean compositeBow;
	
	public static boolean respiratorHalfMask;
	public static boolean respiratorMask;
	public static boolean respiratorMaskComposite;
	
	public static boolean ushankaHat;
	public static boolean balaclavaMask;
	public static boolean shemaghMask;
	
	public static void init(File configFile)
	{
	    Configuration config = new Configuration(configFile);
		
	    String allowCraftingNote = "Should be TRUE to allow crafting. FALSE to remove recipe.";
	    
	    // TODO: Rewrite this code.
	    try {
	    	compositeHelmet = config.getBoolean("composite_helmet", SECTION_CRAFTING, true, allowCraftingNote);
	    	compositeChestplate = config.getBoolean("composite_chestplate", SECTION_CRAFTING, true, allowCraftingNote);
	    	compositeLeggings = config.getBoolean("composite_leggings", SECTION_CRAFTING, true, allowCraftingNote);
	    	compositeBoots = config.getBoolean("composite_boots", SECTION_CRAFTING, true, allowCraftingNote);

	    	compositeSword = config.getBoolean("composite_sword", SECTION_CRAFTING, true, allowCraftingNote);
	    	compositeDagger = config.getBoolean("composite_dagger", SECTION_CRAFTING, true, allowCraftingNote);
	    	compositeBow = config.getBoolean("composite_bow", SECTION_CRAFTING, true, allowCraftingNote);

	    	respiratorHalfMask = config.getBoolean("respirator_halfmask", SECTION_CRAFTING, true, allowCraftingNote);
	    	respiratorMask = config.getBoolean("respirator_mask", SECTION_CRAFTING, true, allowCraftingNote);
	    	respiratorMaskComposite = config.getBoolean("respirator_mask_composite", SECTION_CRAFTING, true, allowCraftingNote);

	    	ushankaHat = config.getBoolean("ushanka_hat", SECTION_CRAFTING, true, allowCraftingNote);
	    	balaclavaMask = config.getBoolean("balaclava_mask", SECTION_CRAFTING, true, allowCraftingNote);
	    	shemaghMask = config.getBoolean("shemagh_mask", SECTION_CRAFTING, true, allowCraftingNote);
	    	
	    } catch (Exception e) {
	      CompositeGear.modLog.error("Unable to load log file!");
	      throw new RuntimeException(e);

	    } finally {
	      config.save();
	    }
	}
}
