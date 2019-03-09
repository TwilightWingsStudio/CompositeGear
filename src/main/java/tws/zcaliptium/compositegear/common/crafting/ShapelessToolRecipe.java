/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.crafting;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tws.zcaliptium.compositegear.common.CompositeGear;

public class ShapelessToolRecipe extends ShapelessOreRecipe
{
    public ShapelessToolRecipe(ResourceLocation group, Block result, Object... recipe)
    {
    	super(group, new ItemStack(result), recipe);
	}

    public ShapelessToolRecipe(ResourceLocation group, Item  result, Object... recipe)
    {
    	super(group, new ItemStack(result), recipe);
	}
    
    public ShapelessToolRecipe(ResourceLocation group, NonNullList<Ingredient> input, @Nonnull ItemStack result) {
    	super(group, input, result);
    }
    
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        NonNullList<ItemStack> remainingItems = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < remainingItems.size(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            
            //CompositeGear.modLog.info(i + " " + itemstack.toString());
            
            if (itemstack.getItem() instanceof ItemShears) {
            	
            	ItemStack newStack =  itemstack.copy();
            	newStack.setCount(1);
            	newStack.setItemDamage(newStack.getItemDamage() + 1);

            	if (newStack.getItemDamage() >= newStack.getMaxDamage()) {
            		newStack = ItemStack.EMPTY;
            	}
            	
            	remainingItems.set(i, newStack);
            	continue;
            }

            if (itemstack.getItem().hasContainerItem())
            {
            	remainingItems.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
            }
        }

        return remainingItems;
    }
}
