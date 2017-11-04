package terrails.orecontroller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {

    public static final String MOD_ID = "ore_controller";
    public static final String MOD_NAME = "Ore Controller";

    public static final String MOD_VERSION = "1.0.3";
    public static final String MC_VERSION = "[1.11],[1.11.1],[1.11.2]";
    public static final String TERRACORE_VERSION = "2.1.0";

    public static final String CLIENT_PROXY = "terrails.orecontroller.proxies.ClientProxy";
    public static final String SERVER_PROXY = "terrails.orecontroller.proxies.ServerProxy";
    public static final String GUI_FACTORY = "terrails.orecontroller.config.ConfigFactoryGUI";

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static Logger getLogger(String name) {
        return LogManager.getLogger(name);
    }
}
