package terrails.orecontroller.api;

import com.google.common.base.CharMatcher;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import terrails.orecontroller.Constants;
import terrails.orecontroller.generator.OreGeneration;
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
                int[] dimensionID = OreGenerationString.getDimensions(blockArray);
                String[] biomes = OreGenerationString.getBiomes(blockArray);
                IBlockState blockOre = OreGenerationString.getOre(blockArray);
                Block blockReplace = OreGenerationString.getBlock(blockArray);

                if (blockOre != null && containsRequired) {
                    generator(blockOre, blockReplace, world, random, chunkX, chunkZ, minY, maxY, minVein, maxVein, perChunk, biomes, dimensionID);
                } else {
                    if (blockOre == null)
                        Constants.LOGGER.info("Block '" + OreGenerationString.getOre(blockArray) + "' is invalid");
                    if (!containsRequired)
                        Constants.LOGGER.info("Config Value doesn't contain required variables: " + theArray);
                }
            }
        }
    }

    protected void generator(IBlockState ore, Block replace, World world, Random random, int chunkX, int chunkZ, int minY, int maxY, int minVeinSize, int maxVeinSize, int chancesToSpawn, String[] biomeID, int[] dimensionID) {
        int heightRange = maxY - minY;
        BlockPos pos = new BlockPos((chunkX * 16) + random.nextInt(16), minY + random.nextInt(heightRange), (chunkZ * 16) + random.nextInt(16));

        for (int i = 0; i < chancesToSpawn; i++) {
            WorldGenCustomMinable generator = null;

            if (isNull(replace) && isNull(biomeID) && isNull(dimensionID)) {
                generator = generateDefault(world, ore, minVeinSize, maxVeinSize);
            }

            else if (isNotNull(replace) && isNull(biomeID) && isNull(dimensionID)) {
                generator = new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, replace);
            }
            else if (isNull(replace) && isNotNull(biomeID) && isNull(dimensionID)) {
                if (isBiome(world, pos, biomeID)) {
                    generator = generateDefault(world, ore, minVeinSize, maxVeinSize);
                }
            }
            else if (isNull(replace) && isNull(biomeID) && isNotNull(dimensionID)) {
                if (isDimension(world, dimensionID))
                    generator = generateDefault(world, ore, minVeinSize, maxVeinSize);
            }

            else if (isNotNull(replace) && isNotNull(biomeID) && isNull(dimensionID)) {
                if (isBiome(world, pos, biomeID))
                    generator = new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, replace);
            }
            else if (isNotNull(replace) && isNull(biomeID) && isNotNull(dimensionID)) {
                if (isDimension(world, dimensionID))
                    generator = new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, replace);
            }

            else if (isNull(replace) && isNotNull(biomeID) && isNotNull(dimensionID)) {
                if (isBiome(world, pos, biomeID) && isDimension(world, dimensionID))
                    generator = generateDefault(world, ore, minVeinSize, maxVeinSize);
            }

            else if (isNotNull(replace) && isNotNull(biomeID) && isNotNull(dimensionID)) {
                if (isBiome(world, pos, biomeID) && isDimension(world, dimensionID))
                    generator = new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, replace);
            }

            if (generator != null)
                generator.generate(world, random, pos);
        }
    }

    private WorldGenCustomMinable generateDefault(World world, IBlockState ore, int minVeinSize, int maxVeinSize) {
        switch (world.provider.getDimension()) {
            case -1: return new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, Blocks.NETHERRACK);
            case 1: return new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, Blocks.END_STONE);
            default: return new WorldGenCustomMinable(ore, minVeinSize, maxVeinSize, Blocks.STONE);
        }
    }

    private boolean isDimension(World world, int[] dimID) {
        boolean condition = false;
        for (int id : dimID) {
            if (world.provider.getDimension() == id)
                condition = true;
        }
        return condition;
    }
    private boolean isBiome(World world, BlockPos pos, String[] biomeNames) {
        boolean condition = false;
        Biome biomeAtPos = world.getBiome(pos);
        for (String string : biomeNames) {
            if (string.matches("^[0-9]+$")) {
                int biomeID = Integer.parseInt(CharMatcher.digit().retainFrom(string));
                System.out.println("Biome id is: " + biomeID);
                if (Biome.getBiome(biomeID) == biomeAtPos)
                    condition = true;
            } else {
                if (string.equals(biomeAtPos.getRegistryName().toString()))
                    condition = true;
            }
        }
        return condition;
    }
    private boolean isNull(Object object) {
        if (object instanceof Integer)
            return ((Integer) object == Integer.MIN_VALUE);
        else return (object == null);
    }
    private boolean isNotNull(Object object) {
        if (object instanceof Integer)
            return ((Integer) object != Integer.MIN_VALUE);
        else return (object != null);
    }
    private boolean isNull(int[] object) {
        boolean condition = false;
        for (int id : object)
            condition = (id == Integer.MIN_VALUE);
        return condition;
    }
    private boolean isNotNull(int[] object) {
        boolean condition = false;
        for (int id : object)
            condition = (id != Integer.MIN_VALUE);
        return condition;
    }
}
