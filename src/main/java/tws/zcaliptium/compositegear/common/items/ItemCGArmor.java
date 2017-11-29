package tws.zcaliptium.compositegear.common.items;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.EnumItemClass;
import tws.zcaliptium.compositegear.common.IClassifiedItem;
import tws.zcaliptium.compositegear.common.ModInfo;

public class ItemCGArmor extends ItemArmor implements IClassifiedItem
{
	protected final String armorName;
	protected EnumItemClass itemClass;
	protected boolean isAirMask;

	public ItemCGArmor(String id, ArmorMaterial armorMaterial, String armorName, int renderIndex, int armorType)
	{
		super(armorMaterial, renderIndex, armorType);

		this.armorName = armorName;
		this.itemClass = EnumItemClass.MEDIUM_ARMOR;
		this.isAirMask = false;
		
		GameRegistry.registerItem(this, id, ModInfo.MODID);
		setUnlocalizedName(id);
		setTextureName(ModInfo.MODID + ":" + id);
		
		if (CompositeGear.ic2Tab != null) {
			setCreativeTab(CompositeGear.ic2Tab);
		}
	}

	@Override
	public EnumItemClass getItemClass()
	{
		return itemClass;
	}
	
	public ItemCGArmor setItemClass(EnumItemClass itemClass)
	{
		this.itemClass = itemClass;
		return this;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		int suffix = this.armorType == 2 ? 2 : 1;
	
		if (type == null) {
			return ModInfo.MODID + ":textures/armor/" + this.armorName + "_" + suffix + ".png";
		}
		
		return ModInfo.MODID + ":textures/armor/" + this.armorName + "_" + suffix + "_overlay.png";
	}
	
	private static void consumeItemFromInventory(EntityPlayer player, ItemStack itemStack)
	{
		for (int i = 0; i < player.inventory.mainInventory.length; i++)
		{
			if ((player.inventory.mainInventory[i] != null) && (player.inventory.mainInventory[i].isItemEqual(itemStack))) {
				player.inventory.decrStackSize(i, 1);
				return;
			}
		}
	}
	
	@Optional.Method(modid = "IC2")
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
	{
		boolean shouldUpdate = false;
		
		// If we wear some air mask on head.
		if (this.isAirMask && this.armorType == 0)
		{
			if ((player.getAir() <= 100) && (player.inventory.hasItemStack(ItemsCG.ic2AirCell)))
			{
				consumeItemFromInventory(player, ItemsCG.ic2AirCell);
		        player.inventory.addItemStackToInventory(new ItemStack(ItemsCG.ic2EmptyCell.getItem()));
		        player.setAir(player.getAir() + 150);
		        shouldUpdate = true;
			}
		}
		
		// If we have changed inventory contents then we should sync it on client.
		if (shouldUpdate) {
			player.inventoryContainer.detectAndSendChanges();
		}
	}

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack var1)
    {
    	return EnumRarity.common;
    }

	public boolean hasColor(ItemStack stack) {
		return false;
	}

    @SideOnly(Side.CLIENT)
    @Override
	public boolean requiresMultipleRenderPasses() {
		return false;
	}

    @SideOnly(Side.CLIENT)
    @Override
	public void registerIcons(IIconRegister iconRegister) {
        super.registerIcons(iconRegister);
	}

    @SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamageForRenderPass(int damage, int renderPass) {
		return super.getIconFromDamageForRenderPass(damage, renderPass);
	}
    
    public ItemCGArmor setAirMask(boolean isAirMask)
    {
    	this.isAirMask = isAirMask;
    	return this;
    }
    
    public boolean isAirMask()
    {
    	return isAirMask;
    }
}
