package ak.VillagerTweaks;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = VillagerTweaks.MOD_ID,
        name = VillagerTweaks.MOD_NAME,
        version = VillagerTweaks.MOD_VERSION,
        dependencies = VillagerTweaks.MOD_DEPENDENCIES,
        useMetadata = true,
        acceptedMinecraftVersions = VillagerTweaks.MOD_MC_VERSION)
public class VillagerTweaks {
    public static final String MOD_ID = "VillagerTweaks";
    public static final String MOD_NAME = "VillagerTweaks";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_DEPENDENCIES = "required-after:Forge@[11.14.0.1237,)";
    public static final String MOD_MC_VERSION = "[1.8,1.8.9]";

    public static String changeTradeItem;
    public static String changeProfessionItem;
    public static String changeCareerItem;
    public static String posChangeItem;
    public static String setMatingItem;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        changeTradeItem = config.get(Configuration.CATEGORY_GENERAL, "ChangeTradeItem", "minecraft:gold_ingot", "set Trade Changing Item Name").getString();
        changeProfessionItem = config.get(Configuration.CATEGORY_GENERAL, "ChangeProfessionItem", "minecraft:golden_apple", "set Profession Changing Item Name").getString();
        changeCareerItem = config.get(Configuration.CATEGORY_GENERAL, "ChangeCareerItem", "minecraft:iron_ingot", "set Career Changing Item Name").getString();
        posChangeItem = config.get(Configuration.CATEGORY_GENERAL, "HomePosChangeItemIDs", "minecraft:arrow", "set Home Position Changing Item Name").getString();
//		setMatingItem = config.get(Configuration.CATEGORY_GENERAL, "SetMateItem", Item.diamond.itemID, "set Mating Item ID").getInt();
        config.save();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new VillagerInteractHook());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}