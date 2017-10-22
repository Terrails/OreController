package terrails.orecontroller.api;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import terrails.orecontroller.Constants;
import terrails.orecontroller.generator.OreGenerationString;
import terrails.terracore.world.generator.WorldGenCustomMinable;

import java.util.Random;

public class CustomOreGenerator {

    public void generateOre(boolean condition, String[] oreArray, Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (condition) {
            for (String theArray : oreArray) {
                String blockArray = theArray.toLowerCase();

                boolean containsMinY = blockArray.contains("-miny:");
                boolean containsMaxY = blockArray.contains("-maxy:");
                boolean containsMinVein = blockArray.contains("-minvein:");
                boolean containsMaxVein = blockArray.contains("-maxvein:");
                boolean containsPerChunk = blockArray.contains("-perchunk:");
                boolean containsRequired = containsMinY && containsMaxY && containsMinVein && containsMaxVein && containsPerChunk;

                int minY = OreGenerationString.getInteger(blockArray, "-miny:");
                int maxY = OreGenerationString.getInteger(blockArray, "-maxy:");
                int minVein = OreGenerationString.getInteger(blockArray, "-minvein:");
                int maxVein = OreGenerationString.getInteger(blockArray, "-maxvein:");
                int perChunk = OreGenerationString.getInteger(blockArray, "-perchunk:");
                int biomeID = OreGenerationString.getBiomes(blockArray);
                int dimensionID = OreGenerationString.getDimensions(blockArray);
                IBlockState blockOre = OreGenerationString.getOre(blockArray);
                Block blockReplace = OreGenerationString.getBlock(blockArray);

                if (blockOre != null && containsRequired) {
                    generator(blockOre, blockReplace, world, random, chunkX, chunkZ, minY, maxY, minVein, maxVein, perChunk, biomeID, dimensionID);
                } else {
                    if (blockOre == null)
                        Constants.LOGGER.info("Block '" + theArray + "' is invalid");
                    if (!containsRequired)
                        Constants.LOGGER.info("Config Value doesn't contains required variables: " + theArray);
                }
            }
        }
    }

    protected void generator(IBlockState ore, Block replace, World world, Random random, int chunkX, int chunkZ, int minY, int maxY, int minVeinSize, int maxVeinSize, int chancesToSpawn, int biomeID, int dimensionID) {
        int heightRange = maxY - minY;
        BlockPos pos = new BlockPos((chunkX * 16) + random.nextInt(16), minY + random.nextInt(heightRange), (chunkZ * 16) + random.nextInt(16));

        for (int i = 0; i < chancesToSpawn; i++) {
            WorldGenCustomMinable generator = null;
            if (replace != null && biomeID != Integer.MIN_VALUE && dimensionID != Integer.MIN_VALUE) {
                if (world.provider.getDimension() == dimensionID && world.getBiome(pos) == Biome.getBiome(biomeID))
                    generator = new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, replace);
            }
            else if (replace == null && biomeID == Integer.MIN_VALUE && dimensionID != Integer.MIN_VALUE) {
                if (world.provider.getDimension() == dimensionID)
                    switch (world.provider.getDimension()) {
                        case -1: generator = new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, Blocks.NETHERRACK); break;
                        case 1: generator = new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, Blocks.END_STONE); break;
                        default: generator = new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, Blocks.STONE); break;
                    }
            }
            else if (replace == null && biomeID != Integer.MIN_VALUE && dimensionID != Integer.MIN_VALUE) {
                if (world.provider.getDimension() == dimensionID && world.getBiome(pos) == Biome.getBiome(biomeID))
                    switch (world.provider.getDimension()) {
                        case -1: generator = new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, Blocks.NETHERRACK); break;
                        case 1: generator = new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, Blocks.END_STONE); break;
                        default: generator = new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, Blocks.STONE); break;
                    }
            }
            else if (replace != null && biomeID != Integer.MIN_VALUE && dimensionID == Integer.MIN_VALUE) {
                if (world.getBiome(pos) == Biome.getBiome(biomeID))
                    generator = new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, replace);
            }
            else if (replace != null && biomeID == Integer.MIN_VALUE && dimensionID != Integer.MIN_VALUE) {
                if (world.provider.getDimension() == dimensionID)
                    generator = new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, replace);
            }
            else if (replace != null && biomeID == Integer.MIN_VALUE && dimensionID == Integer.MIN_VALUE) {
                generator = new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, replace);
            }
            else if (replace == null && biomeID == Integer.MIN_VALUE && dimensionID == Integer.MIN_VALUE) {
                switch (world.provider.getDimension()) {
                    case -1: generator = new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, Blocks.NETHERRACK); break;
                    case 1: generator = new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, Blocks.END_STONE); break;
                    default: generator = new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, Blocks.STONE); break;
                }
            }

            if (generator != null)
                generator.generate(world, random, pos);
        }
    }
}
