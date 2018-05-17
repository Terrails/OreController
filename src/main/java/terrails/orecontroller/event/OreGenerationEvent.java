package terrails.orecontroller.event;

import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.StringUtils;
import terrails.orecontroller.config.ConfigHandler;
import terrails.orecontroller.generator.OreGenerationString;
import terrails.orecontroller.generator.WorldGenCustomMinable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OreGenerationEvent {

    @SubscribeEvent
    public void disableVanillaOreGen(OreGenEvent.GenerateMinable event) {
        if (ConfigHandler.generate) {
            if (event.getGenerator() instanceof WorldGenCustomMinable) {
                return;
            }

            for (String ore : ConfigHandler.generationArray) {
                String oreName = ore.toLowerCase();
                String nameOfOre = StringUtils.substringBefore(oreName, " -");
                List<Integer> dim = Arrays.stream(OreGenerationString.getDimensions(oreName)).boxed().collect(Collectors.toList());
                if (!dim.contains(event.getWorld().provider.getDimension())) {
                    return;
                }
                switch (event.getType()) {
                    case COAL:
                        if (nameOfOre.contains("minecraft:coal_ore")) {
                            event.setResult(Event.Result.DENY);
                        }
                        continue;
                    case DIAMOND:
                        if (nameOfOre.contains("minecraft:diamond_ore")) {
                            event.setResult(Event.Result.DENY);
                        }
                        continue;
                    case GOLD:
                        if (nameOfOre.contains("minecraft:gold_ore")) {
                            event.setResult(Event.Result.DENY);
                        }
                        continue;
                    case IRON:
                        if (nameOfOre.contains("minecraft:iron_ore")) {
                            event.setResult(Event.Result.DENY);
                        }
                        continue;
                    case LAPIS:
                        if (nameOfOre.contains("minecraft:lapis_ore")) {
                            event.setResult(Event.Result.DENY);
                        }
                        continue;
                    case REDSTONE:
                        if (nameOfOre.contains("minecraft:redstone_ore")) {
                            event.setResult(Event.Result.DENY);
                        }
                        continue;
                    case QUARTZ:
                        if (nameOfOre.contains("minecraft:quartz_ore")) {
                            event.setResult(Event.Result.DENY);
                        }
                        continue;
                    case EMERALD:
                        if (nameOfOre.contains("minecraft:emerald_ore")) {
                            event.setResult(Event.Result.DENY);
                        }
                        continue;
                    case DIRT:
                        if (nameOfOre.contains("minecraft:dirt")) {
                            event.setResult(Event.Result.DENY);
                        }
                        continue;
                    case GRAVEL:
                        if (nameOfOre.contains("minecraft:gravel")) {
                            event.setResult(Event.Result.DENY);
                        }
                        continue;
                    case DIORITE:
                        if (nameOfOre.contains("minecraft:diorite")) {
                            event.setResult(Event.Result.DENY);
                        }
                        continue;
                    case GRANITE:
                        if (nameOfOre.contains("minecraft:granite")) {
                            event.setResult(Event.Result.DENY);
                        }
                        continue;
                    case ANDESITE:
                        if (nameOfOre.contains("minecraft:andesite")) {
                            event.setResult(Event.Result.DENY);
                        }
                        continue;
                }
            }
        }
    }
}
