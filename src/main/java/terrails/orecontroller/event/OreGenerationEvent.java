package terrails.orecontroller.event;

import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import terrails.orecontroller.config.ConfigHandler;
import terrails.terracore.helper.StringHelper;

public class OreGenerationEvent {

    @SubscribeEvent
    public void disableVanillaOreGen(OreGenEvent.GenerateMinable event) {
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
}
