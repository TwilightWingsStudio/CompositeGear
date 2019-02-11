package tws.zcaliptium.compositegear.common.util;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tws.zcaliptium.compositegear.common.ModInfo;

public class ItemModelHelper
{
	@SideOnly(Side.CLIENT)
	public static void registerItemModel(Item item, String name)
	{
		registerItemModel(item, 0, name);
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemModel(Item item, int meta, String name)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(ModInfo.MODID + ":" + name, "inventory"));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemModelFull(Item item, int meta, String path, String variant)
	{
		ResourceLocation loc = new ResourceLocation(ModInfo.MODID, path);
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(loc, variant));
	}

	@SideOnly(Side.CLIENT)
	public static void registerMultiItem(Item item, String name, String path)
	{
		ResourceLocation loc = new ResourceLocation(ModInfo.MODID, path);
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(loc, "type=" + name));
	}
}
