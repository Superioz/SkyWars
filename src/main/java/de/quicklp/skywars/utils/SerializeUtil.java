package de.quicklp.skywars.utils;

import de.quicklp.skywars.kit.ItemKit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class SerializeUtil {

    public static final String SEPERATOR = "¶";

    private static final Pattern SERIALIZED_LOCATION = Pattern.compile("\\w*;-?\\d*(:-?\\d*){4}");

    /**
     * Serializes an itemkit
     *
     * @param kit The kit
     * @return The string
     */
    public static String serializeItemKit(ItemKit kit) {
        List<String> serializedParts = new ArrayList<>();
        serializedParts.add(kit.getName());
        kit.getSlotItemStackMap().forEach((integer, stack) -> serializedParts.add(integer + ":" + serializeItemStack(stack)));

        return String.join(SEPERATOR, serializedParts);
    }

    /**
     * Deserializes a string to itemkit
     *
     * @param string The serialized item kit
     * @return The itemkit
     */
    public static ItemKit deserializeItemKit(String string) {
        String[] spl = string.split(SEPERATOR);
        String name = spl[0];
        Map<Integer, ItemStack> slotItemStackMap = new HashMap<>();
        for(int i = 1; i < spl.length; i++) {
            String[] serializedSpl = spl[i].split(":", 2);
            slotItemStackMap.put(Integer.parseInt(serializedSpl[0]), deserializeItemStack(serializedSpl[1]));
        }
        return new ItemKit(name, slotItemStackMap);
    }

    /**
     * Serializes an itemstack
     *
     * @param stack The itemstack
     * @return The json string
     */
    public static String serializeItemStack(ItemStack stack) {
        return new JSONObject(stack.serialize()).toJSONString();
    }

    /**
     * Deserializes a string to an itemstack
     *
     * @param json The json string
     * @return The itemstack
     */
    public static ItemStack deserializeItemStack(String json) {
        try {
            return ItemStack.deserialize(((JSONObject) new JSONParser().parse(json)));
        }
        catch(ParseException e) {
            return null;
        }
    }

    /**
     * Serializes a location
     *
     * @param loc Location
     * @return The string
     * @see #SERIALIZED_LOCATION
     */
    public static String serializeLocation(Location loc) {
        return loc.getWorld().getName() + ";"
                + (StringUtil.join(":", loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()));
    }

    /**
     * Deserializes a location
     *
     * @param string The string
     * @return The location
     * @see #SERIALIZED_LOCATION
     */
    public static Location deserializeLocation(String string) {
        if(!SERIALIZED_LOCATION.matcher(string).matches()) return null;
        try {
            String[] spl = string.split(";");
            World world = Bukkit.getWorld(spl[0]);
            String[] numberSpl = spl[1].split(":");
            double x = Double.parseDouble(numberSpl[0]);
            double y = Double.parseDouble(numberSpl[1]);
            double z = Double.parseDouble(numberSpl[2]);
            float yaw = Float.parseFloat(numberSpl[3]);
            float pitch = Float.parseFloat(numberSpl[4]);
            return new Location(world, x, y, z, yaw, pitch);
        }
        catch(NullPointerException | NumberFormatException ex) {
            return null;
        }
    }

}
