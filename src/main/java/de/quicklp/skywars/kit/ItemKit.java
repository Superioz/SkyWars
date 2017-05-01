package de.quicklp.skywars.kit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ItemKit {

    private String name;
    private Map<Integer, ItemStack> slotItemStackMap;

}
