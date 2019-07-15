/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.ModInfo;
import tws.zcaliptium.compositegear.common.init.ModItems;
import tws.zcaliptium.compositegear.lib.IAttributeHolder;

public class ItemCGCrossbow extends ItemBow implements IAttributeHolder
{
    private int enchantability;
	
	protected Map<String, Object> attributes;
	
	public static final String LOAD_STATE = "Charged";
	
    public ItemCGCrossbow(String id)
    {
		attributes = new HashMap<String, Object>();
    	
		setUnlocalizedName(id);
        this.maxStackSize = 1; // Because item have durability.
        this.setMaxDamage(1);
        this.enchantability = 0;
        
        // If charged then show suitable sprite for that state.
        this.addPropertyOverride(new ResourceLocation("charged"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && getLoadState(stack) ? 1.0F : 0.0F;
            }
        });
        
		ModItems.registerItem(this, new ResourceLocation(ModInfo.MODID, id)); // Put into registry.

		if (CompositeGear.cgTab != null) {
			setCreativeTab(CompositeGear.cgTab);
		}
    }
    
    public static NBTTagCompound getTagCompound(ItemStack stack)
    {
    	NBTTagCompound nbt = stack.getTagCompound();

    	if (nbt == null) {
    		nbt = new NBTTagCompound();
    		stack.setTagCompound(nbt);
    	}

    	if (!nbt.hasKey(LOAD_STATE)) {
    		nbt.setBoolean(LOAD_STATE, false);
    	}

    	return nbt;
    }
    
    public static boolean getLoadState(ItemStack stack)
    {
    	return getTagCompound(stack).getBoolean(LOAD_STATE);
    }
    
    public static void setLoadState(ItemStack stack, boolean state)
    {
    	getTagCompound(stack).setBoolean(LOAD_STATE, state);
    }
    
    public EnumAction getItemUseAction(ItemStack stack)
    {
    	return EnumAction.NONE;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
    	// Only when holding at main hand it possible to shoot.
		if (hand == EnumHand.MAIN_HAND)
		{
	        ItemStack stack = playerIn.getHeldItemMainhand();
	        NBTTagCompound nbt = getTagCompound(stack);
	        
	        // If loaded then shoot.
	        if (getLoadState(stack)) {
	        	// TODO: Shoot.
	        	shoot(worldIn, playerIn);
	        	setLoadState(stack, false);
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	        } else {
	            playerIn.setActiveHand(hand);
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	        }

			//return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		
		// Otherwise do nothing.
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItemOffhand());
    }
    
    public void shoot(World worldIn, EntityPlayer entityplayer)
    {
    	float f = 0.5F;
        worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
    }
    
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		return EnumActionResult.PASS;
	}
	
	@Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
	{
		int i = getMaxItemUseDuration(stack) - timeLeft;

		// If not loaded then we can load it.
		if (i >= 20 && !getLoadState(stack) && consumeAmmo(stack)) {
			setLoadState(stack, true);
		}
	}
	
    @Override
    public int getMaxItemUseDuration(ItemStack itemstack)
    {
        return 72000;
    }
	
    public static boolean consumeAmmo(ItemStack stack)
	{
		// TODO: Consume stack.
		return true;
	}

	public void setEnchantability(int enchantability)
	{
		this.enchantability = enchantability;
	}

	@Override
	public Map<String, Object> getAttributes()
	{
		return this.attributes;
	}
}
