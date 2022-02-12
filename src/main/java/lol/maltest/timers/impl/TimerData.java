package lol.maltest.timers.impl;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class TimerData {

    private static File file;
    private static FileConfiguration deathData;

    /**
     * Find or create the data.yml file
     */
    public void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("MalTimers").getDataFolder(), "data.yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        deathData = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Get the config
     * @return the deathData FileConfiguration
     */
    public FileConfiguration get() {
        return deathData;
    }

    /**
     * Check if config is empty
     * @return if config is empty
     */
    public boolean isEmpty() {
        return file.length() == 0;
    }

    /**
     * Save the config
     */
    public void save() {
        try {
            deathData.save(file);
        } catch (IOException e) {
            System.out.println("Failed to save data.yml!");
            e.printStackTrace();
        }
    }

    /**
     * Reload the config
     */
    public void reload() {
        deathData = YamlConfiguration.loadConfiguration(file);
    }
}
