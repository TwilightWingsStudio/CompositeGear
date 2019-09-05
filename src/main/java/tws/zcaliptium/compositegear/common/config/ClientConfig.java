/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.config;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tws.zcaliptium.compositegear.common.ModInfo;

import java.io.File;

public class ClientConfig
{
    public static Configuration config;

    public static final String SECTION_CLIENT = "client";

    // Client.
    public static int tooltipDurabilityDisplay = 1; // 0 - Disabled, 1 - Only Our Mod, 2 - All Items
    public static boolean tooltipDurabilityDisplay_removeEnderCoreDurability = true;
    public static boolean helmetHudOverlay = true;

    public static void init(File configFile)
    {
        config = new Configuration(configFile);
        config.load();

        load();

        MinecraftForge.EVENT_BUS.register(ConfigChangeListener.class);
    }

    public static void load()
    {
        helmetHudOverlay = config.getBoolean("helmetHudOverlay", SECTION_CLIENT, true, "Enables overlay texture rendering when you wear some equipment on your head.");

        // Durability Display
        tooltipDurabilityDisplay = config.getInt("tooltipDurabilityDisplay", SECTION_CLIENT, 1, 0, 2, "Will add icon with text into tooltips of clothing/armor/tools/weapons.\nModes:\n 0 - Disable\n 1 - Only Our Mod\n 2 - All Items\n");

        String category = SECTION_CLIENT + ".tooltipDurabilityDisplay";
        tooltipDurabilityDisplay_removeEnderCoreDurability = config.getBoolean("removeEnderCoreDurability", category, true, "Will remove 'Durability: X/Y' line from all the tooltips affected by our durability display.");

        if (config.hasChanged()) config.save();
    }

    public static void save()
    {
        config.save();
    }

    public static class ConfigChangeListener
    {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
            if (eventArgs.getModID().equals(ModInfo.MODID)) {
                load();
            }
        }
    }
}
