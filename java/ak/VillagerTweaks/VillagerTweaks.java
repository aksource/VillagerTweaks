package ak.VillagerTweaks;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(modid="VillagerTweaks", name="VillagerTweaks", version="@VERSION@",dependencies="required-after:FML", useMetadata = true)

public class VillagerTweaks
{
	@Mod.Instance("VillagerTweaks")
	public static VillagerTweaks instance;

	public static String changeTradeItem;
	public static String changeProfessionItem;
	public static String posChangeItem;
	public static String setMatingItem;
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		changeTradeItem = config.get(Configuration.CATEGORY_GENERAL, "ChangeTradeItem", "minecraft:gold_ingot", "set Trade Changing Item Name").getString();
		changeProfessionItem = config.get(Configuration.CATEGORY_GENERAL, "ChangfeProfesstionItem", "minecraft:golden_apple", "set Profession Changing Item Name").getString();
		posChangeItem = config.get(Configuration.CATEGORY_GENERAL, "HomePosChangeItemIDs", "minecraft:arrow").getString();
//		setMatingItem = config.get(Configuration.CATEGORY_GENERAL, "SetMateItem", Item.diamond.itemID, "set Mating Item ID").getInt();
		config.save();
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new VillagerInteractHook());
	}
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}
}