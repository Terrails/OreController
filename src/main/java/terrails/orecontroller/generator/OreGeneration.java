package terrails.orecontroller.generator;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import terrails.orecontroller.Constants;
import terrails.orecontroller.api.CustomOreGenerator;
import terrails.orecontroller.config.ConfigHandler;

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
