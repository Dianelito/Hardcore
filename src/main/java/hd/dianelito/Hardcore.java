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
        gameManager = new GameManager(this);
        getServer().getPluginManager().registerEvents(new DeathListener(gameManager), this);
        getCommand("lifes").setExecutor(new LifesCommand(gameManager));
        gameManager.startReviveCheck(); // Inicia el chequeo de revivir jugadores
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&aHardcore Plugin Activado &bVersión&7: " + version));
    }
    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cHardcore Plugin Desactivado"));
    }
}
