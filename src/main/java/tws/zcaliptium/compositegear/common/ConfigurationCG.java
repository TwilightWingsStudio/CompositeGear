package tws.zcaliptium.compositegear.common;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationCG
{
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
	
	public static void init(File configFile)
	{
	    Configuration config = new Configuration(configFile);
		
	    String allowCraftingNote = "Should be TRUE to allow crafting. FALSE to remove recipe.";
	    
	    // TODO: Rewrite this code.
	    try {
	    	compositeHelmet = config.getBoolean("composite_helmet", "crafting", true, allowCraftingNote);
	    	compositeChestplate = config.getBoolean("composite_chestplate", "crafting", true, allowCraftingNote);
	    	compositeLeggings = config.getBoolean("composite_leggings", "crafting", true, allowCraftingNote);
	    	compositeBoots = config.getBoolean("composite_boots", "crafting", true, allowCraftingNote);
	    	
	    	compositeSword = config.getBoolean("composite_sword", "crafting", true, allowCraftingNote);
	    	compositeDagger = config.getBoolean("composite_dagger", "crafting", true, allowCraftingNote);
	    	compositeBow = config.getBoolean("composite_bow", "crafting", true, allowCraftingNote);
	    	
	    	respiratorHalfMask = config.getBoolean("respirator_halfmask", "crafting", true, allowCraftingNote);
	    	respiratorMask = config.getBoolean("respirator_mask", "crafting", true, allowCraftingNote);
	    	respiratorMaskComposite = config.getBoolean("respirator_mask_composite", "crafting", true, allowCraftingNote);
	    	
	    } catch (Exception e) {
	      CompositeGear.modLog.error("Unable to load log file!");
	      throw new RuntimeException(e);

	    } finally {
	      config.save();
	    }
	}
}
