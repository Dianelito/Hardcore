package hd.dianelito;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LifesCommand implements CommandExecutor {

    private final GameManager gameManager;
    public static String prefix = "&8[&c&lHardcore&8] ";

    public LifesCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cEste comando solo puede ser ejecutado por un jugador."));
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cUso: /lifes <add|remove|set|get> <jugador> [cantidad]"));
            return true;
        }

        String action = args[0].toLowerCase();
        String targetName = args[1];
        Player targetPlayer = Bukkit.getPlayer(targetName);

        if (targetPlayer == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cEl jugador " + targetName + " no está en línea."));
            return true;
        }

        int amount = 0;
        if (args.length == 3) {
            try {
                amount = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cCantidad no válida."));
                return true;
            }
        }

        switch (action) {
            case "add":
                if (!player.hasPermission("hardcore.lifes.add")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cNo tienes permiso para ejecutar este comando."));
                    return true;
                }
                if (args.length != 3) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cUso correcto: /lifes add <jugador> <cantidad>"));
                    return true;
                }
                gameManager.addLives(targetName, amount);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&aSe han añadido " + amount + " &avidas a " + targetName + ". Ahora tiene " + gameManager.getLives(targetName) + " vidas."));
                break;

            case "remove":
                if (!player.hasPermission("hardcore.lifes.remove")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cNo tienes permiso para ejecutar este comando."));
                    return true;
                }
                if (args.length != 3) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cUso correcto: /lifes remove <jugador> <cantidad>"));
                    return true;
                }
                gameManager.removeLives(targetName, amount);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&aSe han eliminado " + amount + " &avidas a " + targetName + ". Ahora tiene " + gameManager.getLives(targetName) + " vidas."));
                break;

            case "set":
                if (!player.hasPermission("hardcore.lifes.set")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cNo tienes permiso para ejecutar este comando."));
                    return true;
                }
                if (args.length != 3) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cUso correcto: /lifes set <jugador> <cantidad>"));
                    return true;
                }
                gameManager.setLives(targetName, amount);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&aLas vidas de " + targetName + " se han establecido a " + amount + ". Ahora tiene " + gameManager.getLives(targetName) + " vidas."));
                break;

            case "get":
                if (!player.hasPermission("hardcore.lifes.get")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cNo tienes permiso para ejecutar este comando."));
                    return true;
                }
                if (args.length != 2) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cUso correcto: /lifes get <jugador>"));
                    return true;
                }
                int lives = gameManager.getLives(targetName);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + targetName + " &atiene " + lives + " vidas."));
                break;

            default:
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cAcción no válida. Usa add, remove, set o get."));
                break;
        }

        return true;
    }
}
