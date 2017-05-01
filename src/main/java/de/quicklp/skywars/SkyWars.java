package de.quicklp.skywars;

import de.quicklp.skywars.commands.AddItemchestCommand;
import de.quicklp.skywars.commands.AddKitCommand;
import de.quicklp.skywars.commands.AddSpawnpointCommand;
import de.quicklp.skywars.commands.SetLobbyCommand;
import de.quicklp.skywars.config.ConfigManager;
import de.quicklp.skywars.config.LanguageManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SkyWars extends JavaPlugin {

    // just the instance to access the class from outside
    private static SkyWars instance;

    public static synchronized SkyWars getInstance() {
        if(instance == null) {
            instance = new SkyWars();
        }
        return instance;
    }

    // stuff
    private ConfigManager gameManager;
    private LanguageManager languageBase;

    @Override
    public void onEnable() {

        // config
        getLogger().info("Load configuration ..");
        loadConfig();
        gameManager = new ConfigManager(this);
        languageBase = new LanguageManager(getDataFolder());
        languageBase.load("language");

        // commands
        getCommand("addspawnpoint").setExecutor(new AddSpawnpointCommand());
        getCommand("additemchest").setExecutor(new AddItemchestCommand());
        getCommand("setlobby").setExecutor(new SetLobbyCommand());
        getCommand("addkit").setExecutor(new AddKitCommand());

        // listener
    }

    @Override
    public void onDisable() {

    }

    /**
     * Example code from spigotmc.org
     */
    private void loadConfig() {
        File file = new File(getDataFolder(), "config.yml");
        if(!file.exists()) {
            getLogger().info("config.yml not found, creating!");
            saveDefaultConfig();
        }
        else {
            getLogger().info("config.yml found, loading!");
        }
    }

}
