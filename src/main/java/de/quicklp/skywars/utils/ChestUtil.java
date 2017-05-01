package de.quicklp.skywars.utils;

import de.quicklp.skywars.loot.LootItem;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;

import java.security.SecureRandom;
import java.util.List;

public class ChestUtil {

    public static void setContent(Chest chest, List<LootItem> items) {
        items.forEach(lootItem -> {

            SecureRandom random = new SecureRandom();
            int i = random.nextInt(chest.getInventory().getSize());
            Inventory chestInventory = chest.getInventory();

            if((chestInventory.getItem(i) != null || chestInventory.getItem(i).getType().equals(Material.AIR))) {
                chestInventory.setItem(i, lootItem);
            }
            else if(!(chestInventory.firstEmpty() == -1)) {
                chest.getInventory().addItem(lootItem);
            }
        });
    }
}
