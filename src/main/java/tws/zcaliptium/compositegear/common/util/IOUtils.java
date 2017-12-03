package tws.zcaliptium.compositegear.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import tws.zcaliptium.compositegear.common.CompositeGear;
import tws.zcaliptium.compositegear.common.ModInfo;

public class IOUtils
{
	public static InputStream getModConfigAsStream(String name) throws FileNotFoundException
	{
		File file = new File(CompositeGear.proxy.getGameDir(), "config/" + ModInfo.MODID + "/" + name);
		
		if ((file.canRead()) && (file.isFile())) {
			return new FileInputStream(file);
		}
		
		return IOUtils.class.getResourceAsStream("/assets/" + ModInfo.MODID + "/config/" + name + ".ini");
	}
}
