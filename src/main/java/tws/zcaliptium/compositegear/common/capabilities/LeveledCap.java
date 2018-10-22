/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import tws.zcaliptium.compositegear.common.ModInfo;
import tws.zcaliptium.compositegear.lib.capabilities.ILeveled;

public final class LeveledCap
{
	public static final ResourceLocation NAME = new ResourceLocation(ModInfo.MODID, "leveled");
	
	@CapabilityInject(ILeveled.class)
	public static final Capability<ILeveled> CAPABILITY_LEVELED = null;
	
	public static void init()
	{
		CapabilityManager.INSTANCE.register(ILeveled.class, new LeveledStorage(), () -> new LeveledData());
	}
	
	/**
	 * This class is responsible for saving data to NBT.
	 */
	public static class LeveledStorage implements IStorage<ILeveled>
	{
		@Override
		public NBTBase writeNBT (Capability<ILeveled> capability, ILeveled instance, EnumFacing side)
		{
			return new NBTTagInt(instance.getLevel()); 
		}

		@Override
		public void readNBT (Capability<ILeveled> capability, ILeveled instance, EnumFacing side, NBTBase nbt)
		{
			instance.setLevel(((NBTPrimitive) nbt).getInt());
		}
	}
	
	public static class LeveledProvider implements ICapabilitySerializable<NBTBase>
	{
		private ILeveled DEFAULT_INSTANCE = CAPABILITY_LEVELED.getDefaultInstance();
		
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing)
		{
			return capability == CAPABILITY_LEVELED;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing)
		{
			if (capability == CAPABILITY_LEVELED) {
				return CAPABILITY_LEVELED.cast(DEFAULT_INSTANCE);
			}
			
			return null;
		}

		@Override
		public NBTBase serializeNBT()
		{
			return CAPABILITY_LEVELED.getStorage().writeNBT(CAPABILITY_LEVELED, DEFAULT_INSTANCE, null);
		}

		@Override
		public void deserializeNBT(NBTBase nbt)
		{
			CAPABILITY_LEVELED.getStorage().readNBT(CAPABILITY_LEVELED, DEFAULT_INSTANCE, null, nbt);
		}
	}
}