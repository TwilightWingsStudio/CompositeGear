/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.loot;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.ModContainer;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.lib.IItemFactory;

public class LootTableHelper
{
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static Map<ResourceLocation, ResourceLocation> LOOT_TABLE_DEFAULTS = Maps.newHashMap();
    
    static void loadLootTableDefaults(JsonObject json, JsonContext context)
    {
        for (Entry<String, JsonElement> entry : JsonUtils.getJsonObject(json, "tables").entrySet())
        {
            ResourceLocation key = new ResourceLocation(entry.getKey());
            ResourceLocation value = new ResourceLocation(entry.getValue().getAsString());
            
            LOOT_TABLE_DEFAULTS.put(key, value);
        }
    }
	
	public static void loadLootTableDefaults(ModContainer mod)
	{
        FileSystem fs = null;
        
        Path fPath = null;
        
        try
        {
            JsonContext ctx = new JsonContext(mod.getModId());

            if (mod.getSource().isFile())
            {
                fs = FileSystems.newFileSystem(mod.getSource().toPath(), null);
                fPath = fs.getPath("/assets/" + ctx.getModId() + "/loot_tables/_defaults.json");
            }
            else if (mod.getSource().isDirectory())
            {
                fPath = mod.getSource().toPath().resolve("assets/" + ctx.getModId() + "/loot_tables/_defaults.json");
            }

            if (fPath != null && Files.exists(fPath))
            {
                try (BufferedReader reader = Files.newBufferedReader(fPath))
                {
                    JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
                    loadLootTableDefaults(json, ctx);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
		catch (JsonParseException e)
		{
			CompositeGear.proxy.throwModLoadingException(new String("Malformed loot table defaults JSON!\n\nPath: assets/" + mod.getModId() + "/loot_tables/_defaults.json").split("\n"), e);
		}
        finally
        {
            IOUtils.closeQuietly(fs);
        }
	}
	
	public static void loadTables(ModContainer mod)
	{
		CraftingHelper.findFiles(mod, "assets/" + mod.getModId() + "/loot_tables", null,
				(root, file) ->
				{
					String relative = root.relativize(file).toString();
					
					if (!"json".equals(FilenameUtils.getExtension(file.toString())) || relative.startsWith("_")) {
						return true;
					}

					String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
					ResourceLocation loc = new ResourceLocation(mod.getModId(), name);
					
					LootTableList.register(loc);
					
					return true;
				}, true, true
		);
	}
}
