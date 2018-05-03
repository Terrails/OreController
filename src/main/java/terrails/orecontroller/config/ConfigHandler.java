package terrails.orecontroller.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import terrails.orecontroller.Constants;
import terrails.orecontroller.OreController;
import terrails.orecontroller.generator.OreGeneration;

import java.io.File;

@Mod.EventBusSubscriber
public class ConfigHandler {

    public static Configuration config;
    public static final String GENERAL_SETTINGS = "General Settings";

    public static boolean generate;
    public static String[] generationArray;

    public static void init(File directory) {
        config = new Configuration(new File(directory, OreController.MOD_ID + ".cfg"));
        syncConfig();
    }

    @SubscribeEvent
    public static void configChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(OreController.MOD_ID)) {
            syncConfig();
            OreGeneration.init();
        }
    }

    public static void syncConfig(){
        generate = config.get(GENERAL_SETTINGS, "Generate", true).getBoolean();
        generationArray = config.getStringList("Ore Generation", GENERAL_SETTINGS, new String[]{}, "");

        if (config.hasChanged()) {
            config.save();
        }
    }
}
