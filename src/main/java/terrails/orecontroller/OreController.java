package terrails.orecontroller;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import terrails.orecontroller.config.ConfigHandler;
import terrails.orecontroller.event.OreGenerationEvent;
import terrails.orecontroller.generator.OreGeneration;
import terrails.terracore.base.MainModClass;
import terrails.terracore.base.proxies.ProxyBase;

@Mod(modid = OreController.MOD_ID,
        name = OreController.MOD_NAME,
        version = OreController.VERSION,
        guiFactory = OreController.GUI_FACTORY,
        dependencies = "required-after:terracore@[0.0.0,);")
public class OreController extends MainModClass<OreController> {

    public static final String MOD_ID = "ore_controller";
    public static final String MOD_NAME = "Ore Controller";
    public static final String VERSION = "@VERSION@";
    public static final String GUI_FACTORY = "terrails.orecontroller.config.ConfigFactoryGUI";

    public static ProxyBase proxy;

    public OreController() {
        super(MOD_ID, MOD_NAME, VERSION);
        OreController.proxy = getProxy();
    }

    @Mod.EventHandler
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        ConfigHandler.init(event.getModConfigurationDirectory());
        MinecraftForge.ORE_GEN_BUS.register(new OreGenerationEvent());
    }

    @Mod.EventHandler
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Mod.EventHandler
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        GameRegistry.registerWorldGenerator(new OreGeneration(), 0);
    }

    @Override
    public OreController getInstance() {
        return this;
    }
}
