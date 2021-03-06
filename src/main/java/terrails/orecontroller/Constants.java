package terrails.orecontroller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {

    public static final String MC_VERSION = "[1.12],[1.12.1],[1.12.2]";
    public static final String TERRACORE_VERSION = "2.1.9";

    public static final String CLIENT_PROXY = "terrails.orecontroller.proxies.ClientProxy";
    public static final String SERVER_PROXY = "terrails.orecontroller.proxies.ServerProxy";

    public static final Logger LOGGER = LogManager.getLogger(OreController.MOD_NAME);
    public static Logger getLogger(String name) {
        return LogManager.getLogger(name);
    }
}
