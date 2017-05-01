package de.quicklp.skywars.config;

import de.quicklp.skywars.SkyWars;
import de.quicklp.skywars.kit.ItemKit;
import de.quicklp.skywars.utils.SerializeUtil;
import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ConfigManager {

    private SkyWars mainInstance;

    /**
     * Checks if the server is available for normal use
     *
     * @return The result
     */
    public boolean isReady() {
        return mainInstance.getConfig().getBoolean("ready");
    }

    /**
     * Gets the lobby time (in sec)
     *
     * @return The seconds
     */
    public int getLobbyTime() {
        return mainInstance.getConfig().getInt("lobby.time");
    }

    /**
     * Gets the lobby spawn
     *
     * @return The location
     */
    public Location getLobbySpawn() {
        String locString = mainInstance.getConfig().getString("lobby.spawnpoint");
        return SerializeUtil.deserializeLocation(locString);
    }

    /**
     * Gets the minimum amount of players needed to started the game
     *
     * @return The amount
     */
    public int getMinPlayers() {
        return mainInstance.getConfig().getInt("game.min-players");
    }

    /**
     * Gets all kits available for this server
     *
     * @return The list of kits
     */
    public List<ItemKit> getKits() {
        List<String> kitStrings = mainInstance.getConfig().getStringList("game.kits");
        List<ItemKit> kits = new ArrayList<>();
        kitStrings.forEach(kit -> kits.add(SerializeUtil.deserializeItemKit(kit)));
        return kits;
    }

    /**
     * Gets the player spawnpoints (locations = maximum player)
     *
     * @return The list of location
     */
    public List<Location> getSpawnpoints() {
        List<String> locationStrings = mainInstance.getConfig().getStringList("game.spawnpoints");
        List<Location> locations = new ArrayList<>();
        locationStrings.forEach(loc -> locations.add(SerializeUtil.deserializeLocation(loc)));
        return null;
    }

    public List<ItemStack> getAvailableLoot() {
        List<String> lootStrings = mainInstance.getConfig().getStringList("game.available-loot");
        List<ItemStack> loot = new ArrayList<>();
        lootStrings.forEach(item -> loot.add(SerializeUtil.deserializeLoot(item)));
        return loot;
    }

    public void setLootChest(String map, int x, int y, int z) {
        mainInstance.getConfig().set("", "");
    }

}
