package tws.zcaliptium.compositegear.client;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IItemModelProvider
{
	@SideOnly(Side.CLIENT)
	void registerModels(String var1);
}
