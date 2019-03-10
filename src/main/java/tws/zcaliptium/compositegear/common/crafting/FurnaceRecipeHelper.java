package tws.zcaliptium.compositegear.common.crafting;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FilenameUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.items.ItemHelper;

public class FurnaceRecipeHelper
{
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    
	public static void getRecipe(JsonObject json, JsonContext context)
	{
		ItemStack sourceStack = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "ingredient"), context);
		ItemStack itemstack = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);
		int xp = JsonUtils.getInt(json, "experience", 0);
		
		GameRegistry.addSmelting(sourceStack, itemstack, xp);
	}
	
	public static void loadItems(ModContainer mod)
	{
		JsonContext ctx = new JsonContext(mod.getModId());

		CraftingHelper.findFiles(CompositeGear.container, "assets/" + mod.getModId() + "/furnace_recipes", null,
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
	                    if (json.has("conditions") && !CraftingHelper.processConditions(JsonUtils.getJsonArray(json, "conditions"), ctx)) {
	                        return true;
	                    }
						    
	                    FurnaceRecipeHelper.getRecipe(json, ctx);
					}
					catch (JsonParseException e)
					{
						CompositeGear.proxy.throwModLoadingException(new String("Malformed furnace recipe JSON!\n\nPath: " + key).split("\n"), e);
					}
					catch (IOException e)
					{
						CompositeGear.proxy.throwModLoadingException(new String("IOException while reading furnace recipe JSON!\n\nPath: " + key).split("\n"), e);
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
