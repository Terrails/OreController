package terrails.orecontroller.generator;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class WorldGenCustomMinable extends WorldGenerator {

    private final int veinSize;
    private final IBlockState oreToGenerate;
    private final Predicate<IBlockState> predicate;

    public WorldGenCustomMinable(IBlockState oreToGenerate, int minVeinSize, int maxVeinSize, Block blockToReplace, int replaceMeta){
        this.oreToGenerate = oreToGenerate;
        this.veinSize = ThreadLocalRandom.current().nextInt(minVeinSize, maxVeinSize + 1);
        if (replaceMeta != -1) {
            this.predicate = CustomBlockMatcher.forBlock(blockToReplace, replaceMeta);
        } else {
            this.predicate = BlockMatcher.forBlock(blockToReplace);
        }
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (veinSize < 4) {
            return generateSmallVein(world, rand, x, y, z);
        } else {
            WorldGenerator generate = new WorldGenMinable(oreToGenerate, veinSize, predicate);
            return generate.generate(world, rand, pos);
        }
    }

    private boolean generateSmallVein(World world, Random random, int x, int y, int z) {
        boolean value = false;
        int newX = x + 8;
        int newZ = z + 8;
        IBlockState blockState = world.getBlockState(new BlockPos(newX, y, newZ));
        if (predicate.apply(blockState)) {
        // if (blockState.getBlock().isReplaceableOreGen(blockState, world, new BlockPos(x, y, z), predicate)) {
            for (int i = 0; i < veinSize; i++) {
                int posX = newX + random.nextInt(2) + 8;
                int posY = y + random.nextInt(2) + 8;
                int posZ = newZ + random.nextInt(2) + 8;
                world.setBlockState(new BlockPos(posX, posY, posZ), oreToGenerate, 2);
                value = true;
            }
        }
        return value;
    }

    public static class CustomBlockMatcher implements Predicate<IBlockState> {
        private final Block block;
        private final int metadata;

        private CustomBlockMatcher(Block blockType, int metadata) {
            this.block = blockType;
            this.metadata = metadata;
        }

        public static CustomBlockMatcher forBlock(Block blockType, int metadata) {
            return new CustomBlockMatcher(blockType, metadata);
        }

        @Override
        public boolean apply(@Nullable IBlockState state) {
            boolean flag1 = state != null && state.getBlock() == this.block;
            boolean flag2 = (this.metadata == -1 || (state != null && state.getBlock().getMetaFromState(state) == this.metadata));
            return flag1 && flag2;
        }
    }
}
