package de.quicklp.skywars.loot;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
public class LootItem extends ItemStack {

    int min;
    int max;
    int percent;

    public LootItem(Material material, int min, int max, int percent) {

        this.setType(material);
        this.min = min;
        this.max = max;
        this.percent = percent;

    }

}
