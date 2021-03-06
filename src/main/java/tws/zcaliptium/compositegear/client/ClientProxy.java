/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.client;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tws.zcaliptium.compositegear.common.CommonProxy;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.init.ModItems;
import tws.zcaliptium.compositegear.common.items.ItemCGMelee;
import tws.zcaliptium.compositegear.lib.IItemColorable;

public class ClientProxy extends CommonProxy
{	
	@Override
	public boolean isClient()
	{
		return true;
	}

	@Override
	public void preInit()
	{
		super.preInit();
		
		MinecraftForge.EVENT_BUS.register(new ModelBakeHandler());
	}
	
	@Override
	public void init()
	{
		super.init();
		
		MinecraftForge.EVENT_BUS.register(new ItemTooltipHandler());
	}
	
	@Override
	public void postInit()
	{
		super.postInit();
		
    	registerItemColorHandler();
	}
	
	@Override
	public File getGameDir() {
	    return new File(".");
	}
	
	@Override
	public boolean isOpenToLAN()
	{
		if (Minecraft.getMinecraft().isIntegratedServerRunning()) {
			return Minecraft.getMinecraft().getIntegratedServer().getPublic();
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isEnabledPVP()
	{
		if (isOpenToLAN()) {
			Minecraft.getMinecraft().getIntegratedServer().isPVPEnabled();
		}
		
		return true;
	}
	
    @SideOnly(Side.CLIENT)
    public void registerItemColorHandler()
    {
    	CompositeGear.modLog.info("Registering IItemColor handler for mod items.");
    	
    	Item[] items = ModItems.COLORABLE_REGISTRY.toArray(new Item[0]);
    	
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
        {
            public int colorMultiplier(ItemStack stack, int tintIndex)
            {
            	Item item = stack.getItem();

            	// If our interface...
            	if (item instanceof IItemColorable) {
            		return tintIndex > 0 ? -1 : ((IItemColorable)item).getColorData(stack, 0);
				}

                return tintIndex > 0 ? -1 : ((ItemArmor)item).getColor(stack);
            }
        }, items);
    }
    
    @Override
    public void throwModLoadingException(String[] lines, Throwable causes)
    {
    	throw new ModLoadingException(lines, causes);
    }
}
