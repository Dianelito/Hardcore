package hd.dianelito;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Bukkit;

public class DeathListener implements Listener {

    private final GameManager gameManager;

    public DeathListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String playerName = player.getName();

        gameManager.playerDied(playerName);

        if (gameManager.getLives(playerName) <= 0) {
            if (!player.hasPermission("hardcore.revive.bypass")) {
                // Kick the player and send a message
                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', "¡Has sido baneado por quedarte sin vidas en el modo Hardcore!"));

                // Optionally add to the ban list
                Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(
                        player.getName(),
                        "Te has quedado sin vidas en el modo Hardcore.",
                        null,
                        null
                );
            }
        } else {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "Tienes " + gameManager.getLives(playerName) + " vidas restantes. Esperando para revivir..."));
        }
    }
}
