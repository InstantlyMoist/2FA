package me.kyllian.TFA.commands;

import me.kyllian.TFA.TFAPlugin;
import me.kyllian.TFA.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TFACommand implements CommandExecutor {

    private TFAPlugin plugin;

    public TFACommand(TFAPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("2fa.reload")) {
                    sender.sendMessage(plugin.getMessageHandler().getNoPermissionMessage("2fa.reload"));
                    return true;
                }
                plugin.getMessageHandler().reload();
                plugin.getPlayerHandler().reload();
                plugin.reloadConfig();
                sender.sendMessage(plugin.getMessageHandler().getReloadedMessage());
                return true;
            }
            if (args[0].equalsIgnoreCase("setup")) {
                if (!sender.hasPermission("2fa.setup")) {
                    sender.sendMessage(plugin.getMessageHandler().getNoPermissionMessage("2fa.setup"));
                    return true;
                }
                if (!(sender instanceof Player)) {
                    sender.sendMessage(plugin.getMessageHandler().getNotAPlayerMessage());
                    return true;
                }
                Player player = (Player) sender;
                if (!player.hasPermission("2fa.setup")) {
                    player.sendMessage(plugin.getMessageHandler().getNoPermissionMessage("2fa.setup"));
                    return true;
                }
                PlayerData playerData = plugin.getPlayerHandler().getPlayerData(player);
                if (playerData.hasKey()) {
                    player.sendMessage(plugin.getMessageHandler().getAlreadySetupMessage());
                    return true;
                }
                playerData.createKey();
                return true;
            }
            if (args[0].equalsIgnoreCase("cancel")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(plugin.getMessageHandler().getNotAPlayerMessage());
                    return true;
                }
                Player player = (Player) sender;
                PlayerData playerData = plugin.getPlayerHandler().getPlayerData(player);
                if (playerData.isSetup()) {
                    playerData.setupComplete();
                    playerData.removeKey();
                    player.sendMessage(plugin.getMessageHandler().getCanceledMessage());
                    return true;
                } else {
                    player.sendMessage(plugin.getMessageHandler().getNotSettingUpMessage());
                    return true;
                }
            }
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("remove")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(plugin.getMessageHandler().getNotAPlayerMessage());
                    return true;
                }
                Player player = (Player) sender;
                PlayerData playerData = plugin.getPlayerHandler().getPlayerData(player);
                if (!playerData.hasKey()) {
                    player.sendMessage(plugin.getMessageHandler().getNoCodeMessage());
                    return true;
                }
                String code = args[1];
                if (plugin.getGoogleAuthenticator().authorize(plugin.getGoogleAuthenticator().getCredentialRepository().getSecretKey(player.getUniqueId().toString()), Integer.parseInt(code))) {
                    player.sendMessage(plugin.getMessageHandler().getCodeRemovedMessage());
                    playerData.removeKey();
                    return true;
                }
                player.sendMessage(plugin.getMessageHandler().getIncorrectMessage());
                return true;

            }
            if (args[0].equalsIgnoreCase("reset")) {
                if (!sender.hasPermission("2fa.reset")) {
                    sender.sendMessage(plugin.getMessageHandler().getNoPermissionMessage("2fa.reset"));
                    return true;
                }
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
                    sender.sendMessage(plugin.getMessageHandler().getNotAPlayerMessage());
                    return true;
                }
                PlayerData playerData = plugin.getPlayerHandler().getPlayerData(target);
                playerData.removeKey();
                sender.sendMessage(plugin.getMessageHandler().getCodeRemovedMessage());
                return true;

            }
        }
        plugin.getMessageHandler().getHelpMessages().forEach(string -> {
            sender.sendMessage(plugin.getMessageHandler().colorTranslate(string));
        });
        return true;
    }
}
