/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import net.minecraft.item.Item;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.lib.IItemFactory;

public class ItemHelper
{
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static Map<ResourceLocation, IItemFactory> factories = Maps.newHashMap();
    
    public static Item getItem(JsonObject json, JsonContext context)
    {
        if (json == null || json.isJsonNull())
            throw new JsonSyntaxException("Json cannot be null");

        if (context == null)
            throw new IllegalArgumentException("getItem Context cannot be null");

        String type = context.appendModId(JsonUtils.getString(json, "type"));
        if (type.isEmpty())
            throw new JsonSyntaxException("Item type can not be an empty string");

        
        IItemFactory factory = factories.get(new ResourceLocation(type));
        if (factory == null)
            throw new JsonSyntaxException("Unknown item type: " + type);

        return factory.parse(context, json);
    }

	public static void loadItems(ModContainer mod)
	{
        JsonContext ctx = new JsonContext(mod.getModId());

		CraftingHelper.findFiles(CompositeGear.container, "assets/" + mod.getModId() + "/items", null,
				(root, file) ->
				{
	                String relative = root.relativize(file).toString();
	                if (!"json".equals(FilenameUtils.getExtension(file.toString())) || relative.startsWith("_"))
	                    return true;

	                String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
	                ResourceLocation key = new ResourceLocation(ctx.getModId(), name);
	                
	                BufferedReader reader = null;

	                try
	                {
	                    reader = Files.newBufferedReader(file);

	                    JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
	                    
	                    Item item = ItemHelper.getItem(json, ctx);
	                }
	                catch (JsonParseException e)
	                {
	                    FMLLog.log.error("Parsing error loading item {}", key, e);
	                    System.exit(1);
	                    return false;
	                }
	                catch (IOException e)
	                {
	                    FMLLog.log.error("Couldn't read item {} from {}", key, file, e);
	                    System.exit(1);
	                    return false;
	                }
	                finally
	                {
	                	org.apache.commons.io.IOUtils.closeQuietly(reader);
	                }

					return true;
				}, true, true
		);
	}
}
