package de.quicklp.skywars.utils;

import de.quicklp.skywars.loot.LootItem;
import org.bukkit.Material;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class LootUtil {

    /**
     * rolls the item for a chest
     *
     * @param lootItem the item that can appear
     * @return
     */
    public static LootItem getItem(LootItem lootItem) {
        SecureRandom random = new SecureRandom();
        LootItem item = new LootItem(Material.AIR, 1, 1, 0);

        // Is the item in the pool?
        List<Integer> integers = LootUtil.rollNumbers(99, lootItem.getPercent());
        int i = random.nextInt(99);
        if(integers.contains(i)) {

            // Sets the amount of the Item.
            int amount = random.nextInt((lootItem.getMax() - lootItem.getMin() + 1) + lootItem.getMin());
            item.setAmount(amount);
        }
        else {
            item.setType(Material.AIR);
            item.setAmount(1);
        }

        return item;
    }

    public static List<Integer> rollNumbers(int bound, int amount) {
        SecureRandom random = new SecureRandom();
        List<Integer> numbers = new ArrayList<>();
        for(int i = 0; i < amount; i++) {
            numbers.add(random.nextInt(bound));
        }

        return numbers;
    }

}
