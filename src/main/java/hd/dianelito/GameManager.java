package hd.dianelito;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Map;

public class GameManager {
    private final JavaPlugin plugin;
    private final Map<String, Integer> playerLives = new HashMap<>();
    private final String prefix = "&8[&c&lHardcore&8] ";

    public GameManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void startReviveCheck() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getGameMode() == GameMode.SPECTATOR && !player.hasPermission("hardcore.revive.bypass")) {
                        if (getLives(player.getName()) > 0) {
                            player.setGameMode(GameMode.SURVIVAL);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&aHas sido revivido y cambiado a modo supervivencia."));
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 20L, 20L); // Ejecutar cada segundo (20 ticks)
    }

    public int getLives(String playerName) {
        return playerLives.getOrDefault(playerName, 0);
    }

    public void setLives(String playerName, int lives) {
        playerLives.put(playerName, lives);
    }

    public void addLives(String playerName, int lives) {
        playerLives.put(playerName, getLives(playerName) + lives);
    }

    public void removeLives(String playerName, int lives) {
        playerLives.put(playerName, Math.max(getLives(playerName) - lives, 0));
    }

    public void playerDied(String playerName) {
        removeLives(playerName, 1);
    }
}
