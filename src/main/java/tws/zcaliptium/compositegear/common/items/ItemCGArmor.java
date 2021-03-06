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
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import ic2.api.item.IHazmatLike;
import ic2.api.item.IMetalArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tws.zcaliptium.compositegear.common.*;
import tws.zcaliptium.compositegear.common.compat.IC2Compat;
import tws.zcaliptium.compositegear.common.compat.TRCompat;
import tws.zcaliptium.compositegear.common.config.ClientConfig;
import tws.zcaliptium.compositegear.common.config.CommonConfig;
import tws.zcaliptium.compositegear.common.init.ModItems;
import tws.zcaliptium.compositegear.lib.IAttributeHolder;
import tws.zcaliptium.compositegear.lib.IItemColorable;

@Optional.Interface(iface = "ic2.api.item.IMetalArmor", modid = Compats.IC2)
@Optional.Interface(iface = "ic2.api.item.IHazmatLike", modid = Compats.IC2)
public class ItemCGArmor extends ItemArmor implements IMetalArmor, IHazmatLike, IItemColorable, IAttributeHolder
{
	public static ItemArmor.ArmorMaterial GENERIC_MATERIAL = EnumHelper.addArmorMaterial("CG_GENERIC", ModInfo.MODID + ":composite", 1, new int[] { 0, 0, 0, 0 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
	
	protected String armorName;

	// Intelligence
	protected EnumItemClass itemClass;

	// Model
	protected int defaultColor;
	protected boolean hasOverlay;

	// Material
	protected int protection;
	protected int toughness;
	protected int enchantability;
	
	protected Item repairItem;
	protected ItemStack repairStack = ItemStack.EMPTY;
	
	//
    private ResourceLocation overlayTexturePath;

	// Features
	protected boolean isAirMask;
	protected int minAirToStartRefil;
	
	protected Map<String, Object> attributes;

	public ItemCGArmor(String id, ArmorMaterial armorMaterial, String armorName, int renderIndex, EntityEquipmentSlot armorType)
	{
		super(armorMaterial, renderIndex, armorType);

		attributes = new HashMap<String, Object>();
		
		this.armorName = armorName;
		this.itemClass = EnumItemClass.NO_CLASS;
		setUnlocalizedName(id);
		
		this.setOverlayTexturePath(null);

		// Features.
		this.isAirMask = false;
		this.minAirToStartRefil = 0;

		// Material.
		this.protection = 0;
		this.toughness = 0;
		this.enchantability = 0;
		
		this.repairItem = null;

		// Model.
		this.defaultColor = 0;
		this.hasOverlay = false;

		ModItems.registerItem(this, new ResourceLocation(ModInfo.MODID, id)); // Put into registry.

		if (CompositeGear.cgTab != null) {
			setCreativeTab(CompositeGear.cgTab);
		}
	}

	public ItemCGArmor(String id, ArmorMaterial armorMaterial, int renderIndex, EntityEquipmentSlot armorType)
	{
		this(id, armorMaterial, "", renderIndex, armorType);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
	{
		int suffix = this.armorType == EntityEquipmentSlot.LEGS ? 2 : 1;

		if (type == null) {
			return ModInfo.MODID + ":textures/armor/" + this.armorName + "_" + suffix + ".png";
		}

		return ModInfo.MODID + ":textures/armor/" + this.armorName + "_" + suffix + "_overlay.png";
	}

	private static boolean consumeItemFromInventory(EntityPlayer player, ItemStack itemStack)
	{
		for (int i = 0; i < player.inventory.mainInventory.size(); i++)
		{
			if ((player.inventory.mainInventory.get(i) != null)) {
				if (!ItemStack.areItemStackTagsEqual(player.inventory.mainInventory.get(i), itemStack)) {
					continue;
				}

				player.inventory.decrStackSize(i, 1);
				return true;
			}
		}

		return false;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
	{
		if (world.isRemote) {
			return;
		}

		boolean shouldUpdate = false;

		// If we wear some air mask on head.
		if (this.isAirMask && this.armorType == EntityEquipmentSlot.HEAD)
		{
			// Default max value is 300
			if (player.getAir() <= minAirToStartRefil)
			{
				boolean refilled = false;

				// TR cells.
				if (!refilled && Loader.isModLoaded(Compats.TR) && CommonConfig.Compat.techreborn && CommonConfig.ArmorFeatures.airMask)
				{
					if (consumeItemFromInventory(player, TRCompat.trCompressedAirCell))
					{
						player.inventory.addItemStackToInventory(TRCompat.trEmptyCell.copy());
						refilled = true;
					}

				}

				// IC2 cells.
				if (!refilled && Loader.isModLoaded(Compats.IC2) && CommonConfig.Compat.ic2 && CommonConfig.ArmorFeatures.airMask)
				{
					if (consumeItemFromInventory(player, IC2Compat.ic2CompressedAirCell))
					{
						player.inventory.addItemStackToInventory(IC2Compat.ic2EmptyCell.copy());
						refilled = true;
					}
				}

				if (refilled) {
					player.setAir(300);
			        shouldUpdate = true; // Sync player inventory.
				}
			}
		}
		
		// If we have changed inventory contents then we should sync it on client.
		if (shouldUpdate) {
			player.inventoryContainer.detectAndSendChanges();
		}
	}

    public ItemCGArmor setAirMask(boolean isAirMask)
    {
    	this.isAirMask = isAirMask;
    	return this;
    }

	@Override
	public int getItemEnchantability(ItemStack stack)
	{
		if (!CommonConfig.Enchanting.allowArmorEnchanting) {
			return 0;
		}
		
		if (this.getArmorMaterial() == GENERIC_MATERIAL) {
			return this.enchantability;
		}

		return super.getItemEnchantability(stack);
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
	{
		return CommonConfig.Enchanting.allowArmorEnchanting;
	}

	public ItemCGArmor setMinAir(int minAir) {
		this.minAirToStartRefil = minAir;
		return this;
	}

    @Override
    public boolean hasOverlay(ItemStack stack)
    {
        return hasOverlay;
    }

    public ItemCGArmor setHasOverlay(boolean hasOverlay)
    {
    	this.hasOverlay = hasOverlay;
		return this;
    }

    public ItemCGArmor setDefaultColor(int color)
    {
    	this.defaultColor = color;
    	return this;
    }

    public void setArmorName(String armorName)
    {
    	this.armorName = armorName;
    }

    /**
     * Return whether the specified armor ItemStack has a color.
     */
    @Override
    public boolean hasColor(ItemStack stack)
    {
		return this.hasColorData(stack);
    }

    // IItemColorable
	@Override
    public boolean hasColorData(ItemStack stack)
	{
		NBTTagCompound nbttagcompound = stack.getTagCompound();
		return nbttagcompound != null && nbttagcompound.hasKey("display", 10) ? nbttagcompound.getCompoundTag("display").hasKey("color", 3) : false;
	}

	// Vanilla
	@Override
	public int getColor(ItemStack stack)
	{
		return this.getColorData(stack, 0);
	}

	// IItemColorable
    @Override
    public int getColorData(ItemStack stack, int colorId)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound != null)
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

            if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3))
            {
                return nbttagcompound1.getInteger("color");
            }
        }

        return defaultColor;
    }

    // Vanilla
    @Override
    public void removeColor(ItemStack stack)
	{
		this.removeColorData(stack);
	}

    // IItemColorable
    @Override
    public void removeColorData(ItemStack stack)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound != null)
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

            if (nbttagcompound1.hasKey("color"))
            {
                nbttagcompound1.removeTag("color");
            }
        }
    }

    // Vanilla
    @Override
    public void setColor(ItemStack stack, int color)
	{
		this.setColorData(stack, 0, color);
	}

    // IItemColorable
    @Override
    public void setColorData(ItemStack stack, int colorId, int color)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            nbttagcompound = new NBTTagCompound();
            stack.setTagCompound(nbttagcompound);
        }

        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

        if (!nbttagcompound.hasKey("display", 10))
        {
            nbttagcompound.setTag("display", nbttagcompound1);
        }

        nbttagcompound1.setInteger("color", color);
    }

    private static final UUID[] ARMOR_MODIFIERS = new UUID[] {UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
	
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
    	if (this.getArmorMaterial() == GENERIC_MATERIAL) {
    		Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

    		if (equipmentSlot == this.armorType) {
    			multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", (double)this.protection, 0));
    			multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor toughness", (double)this.toughness, 0));    			
    		}

    		return multimap;
    	}
    	
		return super.getAttributeModifiers(equipmentSlot, stack);
	}

	public void setProtection(int protection)
	{
		this.protection = protection;
	}

	public void setToughness(int toughness)
	{
		this.toughness = toughness;
	}
	
	public void setEnchantability(int enchantability)
	{
		this.enchantability = enchantability;
	}

	@Override
	public boolean isColorable()
	{
		return attributes.containsKey("colorable");
	}
	
    private ItemStack getRepairItemStack()
    {
        if (!repairStack.isEmpty()) return repairStack;
        Item ret = this.repairItem;
        if (ret != null) repairStack = new ItemStack(ret,1,net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE);
        return repairStack;
    }
	
    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        ItemStack mat = getRepairItemStack();
        if (!mat.isEmpty() && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);
    }
    
    public void setRepairItem(Item repairItem)
    {
    	this.repairItem = repairItem;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack var1)
    {
    	return (EnumRarity)attributes.getOrDefault("rarity", EnumRarity.COMMON);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, net.minecraft.client.gui.ScaledResolution resolution, float partialTicks)
	{
		if (ClientConfig.helmetHudOverlay && overlayTexturePath != null)
		{
	        GlStateManager.disableDepth();
	        GlStateManager.depthMask(false);
	        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	        GlStateManager.disableAlpha();
	        Minecraft.getMinecraft().getTextureManager().bindTexture(overlayTexturePath);
	        Tessellator tessellator = Tessellator.getInstance();
	        BufferBuilder bufferbuilder = tessellator.getBuffer();
	        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
	        bufferbuilder.pos(0.0D, (double)resolution.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
	        bufferbuilder.pos((double)resolution.getScaledWidth(), (double)resolution.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
	        bufferbuilder.pos((double)resolution.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
	        bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
	        tessellator.draw();
	        GlStateManager.depthMask(true);
	        GlStateManager.enableDepth();
	        GlStateManager.enableAlpha();
	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	public void setOverlayTexturePath(ResourceLocation overlayTexturePath)
	{
		this.overlayTexturePath = overlayTexturePath;
	}

	// IMetalArmor
	@Optional.Method(modid = Compats.IC2)
	@Override
	public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player)
	{
		if (!CommonConfig.Compat.ic2) {
			return false;
		}
		
		return attributes.containsKey("ic2_metal");
	}

	@Optional.Method(modid = Compats.IC2)
	@Override
	public boolean addsProtection(EntityLivingBase entity, EntityEquipmentSlot slot, ItemStack stack)
	{
		if (!CommonConfig.Compat.ic2) {
			return false;
		}
		
		return attributes.containsKey("ic2_hazmat");
	}

	@Override
	public Map<String, Object> getAttributes()
	{
		return this.attributes;
	}
}
