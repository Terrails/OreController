package terrails.orecontroller.generator;

import com.google.common.base.CharMatcher;
import com.google.common.primitives.Ints;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import terrails.orecontroller.Constants;
import terrails.terracore.helper.StringHelper;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class OreGenerationString {

    private static final boolean ENABLE_DEBUGGING = false;
    private static void debugMessage(String string) {
        if (ENABLE_DEBUGGING) {
            Constants.LOGGER.info(string);
        }
    }

    public static IBlockState getOre(String string) {
        if (!string.isEmpty()) {
            String blockString = StringHelper.getSubstringBefore(string, " -").contains("|") ? StringHelper.getSubstringBefore(string, "|") : StringHelper.getSubstringBefore(string, " -");
            debugMessage("Ore String is: " + blockString);

            String meta1 = StringHelper.getSubstringBefore(string , " -");
            String meta2 = meta1.contains("|") ? StringHelper.getSubstringAfter(meta1, "|").replace("|", "") : "0";
            int metadata = Integer.parseInt(meta2);
            debugMessage("Ore Metadata is: " + metadata);
            
            Block theBlock = Block.getBlockFromName(blockString);

            IBlockState blockState = theBlock != null ? theBlock.getStateFromMeta(metadata) : null;
            debugMessage("The Block is: " + blockState);
            return blockState;
        }
        return null;
    }
    public static Block getBlock(String string) {
        if (string.contains("-replace:")) {
            String replace1 = StringHelper.getSubstringAfter(string, "-replace:").replace("-replace:", "");
            debugMessage("Block Replace1 is: " + replace1);
            String replace2 = replace1.contains(" -") ? replace1.replaceAll("([\\s]).*", "$1").replace(" ", "") : replace1;
            debugMessage("Block String is: " + replace2);

            Block theBlock = Block.getBlockFromName(replace2);
            debugMessage("Block is: " + theBlock);
            return theBlock;
        }
        return null;
    }

    public static String[] getBiomes(String string) {
        if (string.contains("-biome:")) {
            String biome1 = StringHelper.getSubstringAfter(string, "-biome:").replace("-biome:", "");
            debugMessage("Biome1 String is: " + biome1);
            String biome2 = biome1.contains(" -") ? StringHelper.getSubstringBefore(biome1, " -") : biome1;
            debugMessage("Biome2 String is: " + biome2);

            if (!biome2.contains("|")) {
                if (!biome2.matches(".*\\d+.*")) {
                    return new String[] {biome2.trim()};
                } else {
                    return new String[] {CharMatcher.digit().retainFrom(biome2)};
                }
            } else {
                List<String> biomeName = new ArrayList<>();
                String[] biome3 = biome2.split("\\|");
                boolean ran = false;
                for (String biome : biome3) {
                    biomeName.add(biome.trim());
                    ran = true;
                }
                if (!ran) return null;
                return biomeName.toArray(new String[biomeName.size()]);
            }
        }
        return null;
    }

    public static int[] getDimensions(String string) {
        if (string.contains("-dimension:")) {
            String dim1 = StringHelper.getSubstringAfter(string, "-dimension:").replace("-dimension:", "");
            debugMessage("Dim1 String is: " + dim1);
            String dim2 = dim1.contains(" -") ? StringHelper.getSubstringBefore(dim1, " -") : dim1;
            debugMessage("Dim2 String is: " + dim2);
            if (!dim2.contains("|")) {
                return new int[] {Integer.parseInt(dim2)};
            } else {
                List<Integer> dimID = new ArrayList<>();
                String[] dim3 = dim2.split("\\|");
                boolean ran = false;
                for (String dim : dim3) {
                    dimID.add(Integer.parseInt(dim));
                    ran = true;
                }
                if (!ran) dimID.add(Integer.MIN_VALUE);
                return Ints.toArray(dimID);
            }
        }
        return new int[]{Integer.MIN_VALUE};
    }

    public static double getInteger(String string, String index) {
        if (string.contains(index)) {
            String metaDataString = string.replaceAll("^.*(" + index + "\\d+\\.?\\d*).*$", "$1").replace(index, "");
            if (metaDataString.matches(".*\\d.*")) {
                return Double.parseDouble(metaDataString);
            } else return 0;
        }
        else return 0;
    }
}
