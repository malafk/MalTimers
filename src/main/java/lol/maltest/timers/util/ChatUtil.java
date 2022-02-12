package lol.maltest.timers.util;

import net.md_5.bungee.api.ChatColor;

public class ChatUtil {

    /**
     * Colors a message using {@link org.bukkit.ChatColor}
     * @param message The message you want to colorize
     * @return The message as a coloured text
     */
    public static String colour(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
