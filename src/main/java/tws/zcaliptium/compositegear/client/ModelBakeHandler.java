/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.client;

import java.util.Map;
import java.util.Set;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tws.zcaliptium.compositegear.client.model.BakedWrappedWithGuiModel;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.ModInfo;
import tws.zcaliptium.compositegear.common.init.ModItems;

@SideOnly(Side.CLIENT)
public class ModelBakeHandler
{
	@SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onModelBake(ModelBakeEvent e)
    {
	    for(Map.Entry<ResourceLocation, ResourceLocation> entry : ModItems.GUI_MODELS_REGISTRY.entrySet())
	    {
	    	ModelResourceLocation key = (ModelResourceLocation)entry.getKey();
	    	ModelResourceLocation value = (ModelResourceLocation)entry.getValue();
	    	
	        IBakedModel mainModel = e.getModelRegistry().getObject(key);
	        IBakedModel guiModel = e.getModelRegistry().getObject(value);
	        
	        // Replace main model with wrapper.
	        e.getModelRegistry().putObject(key, new BakedWrappedWithGuiModel(mainModel, guiModel));
	    }
		
		/*
        ModelResourceLocation mrl = new ModelResourceLocation(new ResourceLocation(ModInfo.MODID, "respirator_test"), "");
        ModelResourceLocation mrl2 = new ModelResourceLocation(new ResourceLocation(ModInfo.MODID, "respirator_test"), "2d");
        
        IBakedModel mainModel = e.getModelRegistry().getObject(mrl);
        IBakedModel invModel = e.getModelRegistry().getObject(mrl2);
        
        e.getModelRegistry().putObject(mrl, new MyBakedModel(mainModel, invModel));

        //for (Object set : e.getModelRegistry().getKeys().toArray())
        {
        //	CompositeGear.modLog.info(set.toString());
        //}
        
        if (mainModel == null) {
        	CompositeGear.modLog.info("Pidoras 1!");        	
        }

        if (invModel == null) {
        	CompositeGear.modLog.info("Pidoras 2!");        	
        }
        
        CompositeGear.modLog.info("Sobaka!");*/
    }
}
