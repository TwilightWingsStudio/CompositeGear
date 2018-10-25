/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.client.model;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class BakedWrappedWithGuiModel implements IBakedModel
{
	protected IBakedModel model1; // Main model.
	protected IBakedModel model2; // GUI model.
	
	public BakedWrappedWithGuiModel(IBakedModel model1, IBakedModel model2)
	{
		this.model1 = model1;
		this.model2 = model2;
	}
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand)
	{
		return null;
	}

	@Override
	public boolean isAmbientOcclusion()
	{
		return true;
	}

	@Override
	public boolean isGui3d()
	{
		return false;
	}

	@Override
	public boolean isBuiltInRenderer()
	{
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture()
	{
		return null;
	}

	@Override
	public ItemOverrideList getOverrides()
	{
		return ItemOverrideList.NONE;
	}
	
	@Override
	public org.apache.commons.lang3.tuple.Pair<? extends IBakedModel, javax.vecmath.Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType)
	{
		if (cameraTransformType == ItemCameraTransforms.TransformType.GUI) {
			return net.minecraftforge.client.ForgeHooksClient.handlePerspective(model2, cameraTransformType);
		}
		
        return net.minecraftforge.client.ForgeHooksClient.handlePerspective(model1, cameraTransformType);
    }

}
