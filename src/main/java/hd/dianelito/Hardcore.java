package hd.dianelito;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Hardcore extends JavaPlugin {

    public static String prefix = "&8[&c&lHardcore&8] ";
    private static String version;
    private GameManager gameManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.gameManager = new GameManager(this);
        getCommand("lifes").setExecutor(new LifesCommand(gameManager, this));
        getServer().getPluginManager().registerEvents(new DeathListener(gameManager), this);

        // Auto-updater
        if (getConfig().getBoolean("auto_update.enabled")) {
            new Updater(this).checkForUpdates();
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cHardcore Plugin Desactivado"));
    }
}
