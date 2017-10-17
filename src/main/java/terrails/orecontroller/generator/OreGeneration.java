package terrails.orecontroller.generator;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import terrails.orecontroller.Constants;
import terrails.orecontroller.config.ConfigHandler;
import terrails.terracore.helper.StringHelper;
import terrails.terracore.world.generator.WorldGenCustomMinable;

import java.util.Random;

@Mod.EventBusSubscriber
public class OreGeneration implements IWorldGenerator {

    private static final boolean ENABLE_DEBUGGING = false;
    private static void debugMessage(String string) {
        if (ENABLE_DEBUGGING) {
            Constants.LOGGER.info(string);
        }
    }

    @SubscribeEvent
    public static void disableVanillaOreGen(OreGenEvent.GenerateMinable event) {
        if (ConfigHandler.generate) {
            for (String ore : ConfigHandler.generationArray) {
                String oreName = ore.toLowerCase();
                String nameOfOre = StringHelper.getSubstringBefore(oreName, " -");

                switch (event.getType()) {
                    case COAL:
                        if (nameOfOre.contains("minecraft:coal_ore")) {
                            event.setResult(Event.Result.DENY);
                        }
                        break;
                    case DIAMOND:
                        if (nameOfOre.contains("minecraft:diamond_ore")) {
                            event.setResult(Event.Result.DENY);
                        }
                        break;
                    case GOLD:
                        if (nameOfOre.contains("minecraft:gold_ore")) {
                            event.setResult(Event.Result.DENY);
                        }
                        break;
                    case IRON:
                        if (nameOfOre.contains("minecraft:iron_ore")) {
                            event.setResult(Event.Result.DENY);
                        }
                        break;
                    case LAPIS:
                        if (nameOfOre.contains("minecraft:lapis_ore")) {
                            event.setResult(Event.Result.DENY);
                        }
                        break;
                    case REDSTONE:
                        if (nameOfOre.contains("minecraft:redstone_ore")) {
                            event.setResult(Event.Result.DENY);
                        }
                        break;
                    case QUARTZ:
                        if (nameOfOre.contains("minecraft:quartz_ore")) {
                            event.setResult(Event.Result.DENY);
                        }
                        break;
                    case EMERALD:
                        if (nameOfOre.contains("minecraft:emerald_ore")) {
                            event.setResult(Event.Result.DENY);
                        }
                        break;
                    case DIRT:
                        if (nameOfOre.contains("minecraft:dirt")) {
                            event.setResult(Event.Result.DENY);
                        }
                        break;
                    case GRAVEL:
                        if (nameOfOre.contains("minecraft:gravel")) {
                            event.setResult(Event.Result.DENY);
                        }
                        break;
                    case DIORITE:
                        if (nameOfOre.contains("minecraft:diorite")) {
                            event.setResult(Event.Result.DENY);
                        }
                        break;
                    case GRANITE:
                        if (nameOfOre.contains("minecraft:granite")) {
                            event.setResult(Event.Result.DENY);
                        }
                        break;
                    case ANDESITE:
                        if (nameOfOre.contains("minecraft:andesite")) {
                            event.setResult(Event.Result.DENY);
                        }
                        break;
                }
            }
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (ConfigHandler.generate) {
            for (String theArray : ConfigHandler.generationArray) {
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
                    generateOre(blockOre, blockReplace, world, random, chunkX, chunkZ, minY, maxY, minVein, maxVein, perChunk, biomeID, dimensionID);
                } else {
                    if (blockOre == null)
                        Constants.LOGGER.info("Block '" + theArray + "' is invalid");
                    if (!containsRequired)
                        Constants.LOGGER.info("Config Value doesn't contains required variables: " + theArray);
                }
            }
        }
    }

    private void generateOre(IBlockState ore, Block replace, World world, Random random, int chunkX, int chunkZ, int minY, int maxY, int minVeinSize, int maxVeinSize, int chancesToSpawn, int biomeID, int dimensionID) {
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
