package tws.zcaliptium.compositegear.common.items;

import com.google.gson.JsonObject;

import net.minecraft.item.Item;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.crafting.JsonContext;
import tws.zcaliptium.compositegear.common.CompositeGear;

public class MeleeItemFactory extends GenericItemFactory
{
	@Override
	public Item parse(JsonContext context, JsonObject json)
	{
		String id = JsonUtils.getString(json, "id");

		ItemCGMelee item = new ItemCGMelee(id, ItemsCG.GENERIC_TOOL_MATERIAL);
		
		// Durability.
		int durability = MathHelper.clamp(JsonUtils.getInt(json, "durability", 	1), 0, Integer.MAX_VALUE);
		if (durability > 0) {
			item.setMaxDamage(durability);			
		}

		// Material
		parseMaterial(JsonUtils.getJsonObject(json, "material"), item);

		JsonObject intelligenceObj = JsonUtils.getJsonObject(json, "intelligence", null);

		// Localized name & Description.
		if (intelligenceObj != null)
		{
			parseIntelligence(intelligenceObj, item);
		}
		
		// Only client need model info.
		if (CompositeGear.proxy.isClient())
		{
			parseModel(JsonUtils.getJsonObject(json, "model"), item); // Item model.
		}

		return item;
	}

	protected void parseMaterial(JsonObject json, ItemCGMelee item)
	{
		String materialType = JsonUtils.getString(json, "type");

		if (materialType.equalsIgnoreCase("generic")) {
			item.setAttackDamage(JsonUtils.getFloat(json, "attackDamage", 0.0F));
			item.setAttackSpeed(JsonUtils.getFloat(json, "attackSpeed", 0.0F));
			item.setEnchantability(JsonUtils.getInt(json, "enchantability", 0));
		} else {
			throw new IllegalArgumentException("Invalid armor material type '" + materialType + "'.");
		}
	}
}