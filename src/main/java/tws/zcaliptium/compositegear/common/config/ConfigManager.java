package tws.zcaliptium.compositegear.common.config;

import tws.zcaliptium.compositegear.common.CompositeGear;

import java.io.File;

public class ConfigManager
{
    public static void loadConfigs()
    {
        try {
            if (!CompositeGear.configDir.exists()) {
                CompositeGear.configDir.mkdir();
            }

            CommonConfig.init(new File(CompositeGear.configDir, "common.cfg"));

            if (CompositeGear.proxy.isClient()) {
                ClientConfig.init(new File(CompositeGear.configDir, "client.cfg"));
            }

        } catch (Exception e) {
            CompositeGear.modLog.error("Unable to load configuration file!");
            throw new RuntimeException(e);
        } finally {

            if (CommonConfig.config != null) {
                CommonConfig.save();
            }

            if (CompositeGear.proxy.isClient()) {
                if (ClientConfig.config != null) {
                    ClientConfig.save();
                }
            }
        }
    }
}
