package de.quicklp.skywars.utils;

import org.bukkit.ChatColor;

public class ChatUtil {

    public static String translateColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }


}
