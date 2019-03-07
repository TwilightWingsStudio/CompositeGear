/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.items;

import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import ic2.api.item.IMetalArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tws.zcaliptium.compositegear.common.Compats;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.ConfigurationCG;
import tws.zcaliptium.compositegear.common.ModInfo;
import tws.zcaliptium.compositegear.common.compat.IC2Compat;
import tws.zcaliptium.compositegear.common.compat.TRCompat;
import tws.zcaliptium.compositegear.common.init.ModItems;
import tws.zcaliptium.compositegear.lib.IItemColorable;
import tws.zcaliptium.compositegear.lib.IItemIntelligence;
import tws.zcaliptium.compositegear.lib.ISpecialArmor;

@Optional.Interface(iface = "ic2.api.item.IMetalArmor", modid = Compats.IC2)
public class ItemCGArmor extends ItemArmor implements IItemIntelligence, IMetalArmor, ISpecialArmor, IItemColorable
{
	public static ItemArmor.ArmorMaterial GENERIC_MATERIAL = EnumHelper.addArmorMaterial("CG_GENERIC", ModInfo.MODID + ":composite", 1, new int[] { 0, 0, 0, 0 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
	
	protected String armorName;

	// Intelligence
	protected boolean hasDescription;
	protected boolean hasVisualAttributes;
	protected EnumItemClass itemClass;
	protected EnumRarity rarity;

	// Model
	protected boolean isColorable;
	protected int defaultColor;
	protected boolean hasOverlay;

	// Material
	protected int protection;
	protected int toughness;
	protected int enchantability;
	
	protected Item repairItem;
	protected ItemStack repairStack = ItemStack.EMPTY;

	// Features
	protected boolean isAirMask;
	protected int minAirToStartRefil;
	
	// TAN
	protected boolean isWarm;
	protected boolean isCold;
	
	// AppleCore
	protected boolean isSaveSatietyHot;
	protected boolean isSaveSatietyCold;
	protected boolean isMetal; // IC2

	public ItemCGArmor(String id, ArmorMaterial armorMaterial, String armorName, int renderIndex, EntityEquipmentSlot armorType)
	{
		super(armorMaterial, renderIndex, armorType);

		this.armorName = armorName;
		this.itemClass = EnumItemClass.NO_CLASS;
		this.rarity = EnumRarity.COMMON;
		setUnlocalizedName(id);

		// Features.
		this.isAirMask = false;
		this.minAirToStartRefil = 0;
		this.isMetal = false;

		// Material.
		this.protection = 0;
		this.toughness = 0;
		this.enchantability = 0;
		
		this.repairItem = null;

		// Model.
		this.defaultColor = 0;
		this.hasOverlay = false;
		this.isColorable = false;

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
				if (!refilled && Loader.isModLoaded(Compats.TR) && ConfigurationCG.trCompat && ConfigurationCG.isFEAirMask)
				{
					if (consumeItemFromInventory(player, TRCompat.trCompressedAirCell))
					{
						player.inventory.addItemStackToInventory(TRCompat.trEmptyCell.copy());
						refilled = true;
					}

				}

				// IC2 cells.
				if (!refilled && Loader.isModLoaded(Compats.IC2) && ConfigurationCG.ic2Compat && ConfigurationCG.isFEAirMask)
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
    public boolean isAirMask()
    {
    	return isAirMask;
    }

	@Override
	public int getItemEnchantability(ItemStack stack)
	{
		if (!ConfigurationCG.allowArmorEnchanting) {
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
		return ConfigurationCG.allowArmorEnchanting;
	}

	public ItemCGArmor setMinAir(int minAir) {
		this.minAirToStartRefil = minAir;
		return this;
	}

	// IMetalArmor
	@Optional.Method(modid = Compats.IC2)
	@Override
	public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player)
	{
		if (!ConfigurationCG.ic2Compat) {
			return false;
		}
		
		return isMetal;
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
	public boolean isWarm()
	{
		return isWarm;
	}

	@Override
	public boolean isCold()
	{
		return isCold;
	}

	@Override
	public boolean isSaveSatietyHot()
	{
		return isSaveSatietyHot;
	}

	@Override
	public boolean isSaveSatietyCold()
	{
		return isSaveSatietyCold;
	}

	public void setWarm(boolean isWarm)
	{
		this.isWarm = isWarm;
	}

	public void setCold(boolean isCold)
	{
		this.isCold = isCold;
	}

	public void setSaveSatietyHot(boolean isSaveSatietyHot)
	{
		this.isSaveSatietyHot = isSaveSatietyHot;
	}

	public void setSaveSatietyCold(boolean isSaveSatietyCold)
	{
		this.isSaveSatietyCold = isSaveSatietyCold;
	}
	
	public void setMetal(boolean isMetal)
	{
		this.isMetal = isMetal;
	}

	@Override
	public boolean isColorable()
	{
		return isColorable;
	}

	public void setColorable(boolean isColorable)
	{
		this.isColorable = isColorable;
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
    	return rarity;
    }

	@Override
	public EnumItemClass getItemClass()
	{
		return itemClass;
	}

    @Override
    public boolean hasDescription() 
    {
    	return hasDescription;
    }

    @Override
    public boolean hasVisualAttributes() 
    {
    	return hasVisualAttributes;
    }

	@Override
	public void setRarity(EnumRarity rarity)
	{
		this.rarity = rarity;
	}

	@Override
	public void setItemClass(EnumItemClass itemClass)
	{
		this.itemClass = itemClass;
	}

	@Override
	public void setHasDescription(boolean hasDescription)
	{
		this.hasDescription = hasDescription;
	}

	@Override
	public void setHasVisualAttributes(boolean hasVisualAttributes)
	{
		this.hasVisualAttributes = hasVisualAttributes;
	}
}
