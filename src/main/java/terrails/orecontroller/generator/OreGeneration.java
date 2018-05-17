package terrails.orecontroller.generator;

import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import terrails.orecontroller.Constants;
import terrails.orecontroller.config.ConfigHandler;

import java.util.*;

public class OreGeneration extends CustomOreGenerator implements IWorldGenerator {

    private static final boolean ENABLE_DEBUGGING = false;
    private static void debugMessage(String string) {
        if (ENABLE_DEBUGGING) {
            Constants.LOGGER.info(string);
        }
    }

    public static List<String> generation = Lists.newArrayList();

    public static void init() {
        generation.clear();
        for (String string : ConfigHandler.generationArray) {
            string = string.toLowerCase();
            if (string.contains("-biometype:")) {
                if (string.contains("-biome:")) {
                    System.out.println("You can't have \"type\" and \"biomeType\", in config: " + string);
                    continue;
                }

                BiomeDictionary.Type[] biomeTypes = OreGenerationString.getBiomeTypes(string);
                if (biomeTypes == null || biomeTypes.length == 0) {
                    System.out.println("BiomeTypes are null in config: " + string);
                    continue;
                }

                List<String> biomes = Lists.newArrayList();
                for (Map.Entry<ResourceLocation, Biome> entry : ForgeRegistries.BIOMES.getEntries()) {
                    ResourceLocation location = entry.getKey();
                    Biome biome = entry.getValue();

                    Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(biome);

                    if (types.containsAll(Arrays.asList(biomeTypes))) {
                        biomes.add(biome.getRegistryName().toString());
                    } else {
                        biomes.remove(biome.getRegistryName().toString());
                    }
                }

                for (String biome : biomes) {
                    String str = string.replace("biometype", "biome");
                    String str2 = str.replaceFirst(".*(?=biome)(?:biome):\\s*([^\\s]+)(.*$)", "$1");
                    String str3 = str.replaceAll(str2, biome);
                    generation.add(str3);
                }
            } else {
                generation.add(string);
            }
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        String[] generateString = generation.toArray(new String[0]);
        generateOre(ConfigHandler.generate, generateString, random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
    }
}
