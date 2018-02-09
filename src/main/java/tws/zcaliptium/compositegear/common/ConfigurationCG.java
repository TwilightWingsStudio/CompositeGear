/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
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
	
	public static Configuration config;
	
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
		"composite_bow",

		"respirator_halfmask",
		"respirator_mask",
		"respirator_mask_composite",
		
		"rubber_gasmask",
		
		"ushanka_hat",
		"balaclava_mask",
		"shemagh_mask",
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
    	
	    } catch (Exception e) {
	      CompositeGear.modLog.error("Unable to load log file!");
	      throw new RuntimeException(e);

	    } finally {
	      config.save();
	    }
	}
}
