/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map.Entry;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.item.Item;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

public class TagHelper
{
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	
    static void loadTag(ResourceLocation loc, JsonObject json, JsonContext context)
    {
    	CompositeGear.modLog.info("Loading tag: " + loc);
    	
    	String id = JsonUtils.getString(json, "id");
    	
        for (JsonElement entry : JsonUtils.getJsonArray(json, "values"))
        {
        	Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(entry.getAsString()));
        	
    		if (item != null) {
            	OreDictionary.registerOre(id, item);
    		}
        }
    }

	public static void loadTags(ModContainer mod)
	{
		JsonContext ctx = new JsonContext(mod.getModId());
		
		CraftingHelper.findFiles(mod, "assets/" + mod.getModId() + "/tags", null,
				(root, file) ->
				{
					String relative = root.relativize(file).toString();
					
					if (!"json".equals(FilenameUtils.getExtension(file.toString())) || relative.startsWith("_")) {
						return true;
					}

					String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
					ResourceLocation loc = new ResourceLocation(mod.getModId(), name);
		
					BufferedReader reader = null;
			        
			        try
			        {
						reader = Files.newBufferedReader(file);
						
						JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
						
						loadTag(loc, json, ctx);
			        }
			        catch (IOException e)
			        {
			            e.printStackTrace();
			        }
					catch (JsonParseException e)
					{
						CompositeGear.proxy.throwModLoadingException(new String("Malformed tag JSON!\n\nPath: " + loc).split("\n"), e);
					}
			        finally
			        {
			            IOUtils.closeQuietly(reader);
			        }
					
					return true;
				}, true, true
		);
	}
}
