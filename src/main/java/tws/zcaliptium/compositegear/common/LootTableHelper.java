package tws.zcaliptium.compositegear.common;

import org.apache.commons.io.FilenameUtils;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.ModContainer;

public class LootTableHelper
{
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
