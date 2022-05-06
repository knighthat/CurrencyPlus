package me.knighthat.plugin;

import lombok.NonNull;
import org.bukkit.ChatColor;

public class Utils {

    public static @NonNull String color(@NonNull String a) {
        return ChatColor.translateAlternateColorCodes('&', a);
    }

    public static @NonNull String strip(@NonNull String a) {
        return ChatColor.stripColor(color(a));
    }
}
