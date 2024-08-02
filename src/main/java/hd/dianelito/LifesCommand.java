package hd.dianelito;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LifesCommand implements CommandExecutor {

    private final GameManager gameManager;
    private final JavaPlugin plugin;

    public LifesCommand(GameManager gameManager, JavaPlugin plugin) {
        this.gameManager = gameManager;
        this.plugin = plugin;
    }

    private String getConfigMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages." + path));
    }

    private String formatMessage(String message, String targetName, int amount, int lives) {
        return message.replace("{player}", targetName).replace("{amount}", String.valueOf(amount)).replace("{lives}", String.valueOf(lives));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(getConfigMessage("not_a_player"));
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(getConfigMessage("invalid_action"));
            return true;
        }

        String action = args[0].toLowerCase();
        String targetName = args.length > 1 ? args[1] : "";
        Player targetPlayer = Bukkit.getPlayer(targetName);

        if (targetName.isEmpty() && !action.equals("get")) {
            player.sendMessage(getConfigMessage("invalid_action"));
            return true;
        }

        int amount = 0;
        if (args.length == 3) {
            try {
                amount = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                player.sendMessage(getConfigMessage("invalid_quantity"));
                return true;
            }
        }

        switch (action) {
            case "add":
                if (!player.hasPermission("hardcore.lifes.add")) {
                    player.sendMessage(getConfigMessage("no_permission"));
                    return true;
                }
                if (args.length != 3) {
                    player.sendMessage(getConfigMessage("usage_add"));
                    return true;
                }
                gameManager.addLives(targetName, amount);
                player.sendMessage(formatMessage(getConfigMessage("lives_added"), targetName, amount, gameManager.getLives(targetName)));
                break;

            case "remove":
                if (!player.hasPermission("hardcore.lifes.remove")) {
                    player.sendMessage(getConfigMessage("no_permission"));
                    return true;
                }
                if (args.length != 3) {
                    player.sendMessage(getConfigMessage("usage_remove"));
                    return true;
                }
                gameManager.removeLives(targetName, amount);
                player.sendMessage(formatMessage(getConfigMessage("lives_removed"), targetName, amount, gameManager.getLives(targetName)));
                break;

            case "set":
                if (!player.hasPermission("hardcore.lifes.set")) {
                    player.sendMessage(getConfigMessage("no_permission"));
                    return true;
                }
                if (args.length != 3) {
                    player.sendMessage(getConfigMessage("usage_set"));
                    return true;
                }
                gameManager.setLives(targetName, amount);
                player.sendMessage(formatMessage(getConfigMessage("lives_set"), targetName, amount, gameManager.getLives(targetName)));
                break;

            case "get":
                if (!player.hasPermission("hardcore.lifes.get")) {
                    player.sendMessage(getConfigMessage("no_permission"));
                    return true;
                }
                if (args.length != 2) {
                    player.sendMessage(getConfigMessage("usage_get"));
                    return true;
                }
                int lives = gameManager.getLives(targetName);
                player.sendMessage(formatMessage(getConfigMessage("lives_get"), targetName, 0, lives));
                break;

            default:
                player.sendMessage(getConfigMessage("invalid_action"));
                break;
        }

        return true;
    }
}
