package terrails.orecontroller.generator;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import terrails.orecontroller.Constants;
import terrails.orecontroller.api.CustomOreGenerator;
import terrails.orecontroller.config.ConfigHandler;
import terrails.terracore.helper.StringHelper;

import java.util.Random;

public class OreGeneration extends CustomOreGenerator implements IWorldGenerator {

    private static final boolean ENABLE_DEBUGGING = false;
    private static void debugMessage(String string) {
        if (ENABLE_DEBUGGING) {
            Constants.LOGGER.info(string);
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        generateOre(ConfigHandler.generate, ConfigHandler.generationArray, random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
    }
}
