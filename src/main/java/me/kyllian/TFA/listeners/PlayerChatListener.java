package me.kyllian.TFA.listeners;

import me.kyllian.TFA.TFAPlugin;
import me.kyllian.TFA.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    private TFAPlugin plugin;

    public PlayerChatListener(TFAPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerHandler().getPlayerData(player);
        if (playerData.getAuthenticationTask() == null) {
            if (playerData.isSetup()) {
                event.setCancelled(true);
                if (plugin.getGoogleAuthenticator().authorize(plugin.getGoogleAuthenticator().getCredentialRepository().getSecretKey(player.getUniqueId().toString()), getInt(event.getMessage()))) {
                    playerData.setupComplete();
                    return;
                }
                player.sendMessage(plugin.getMessageHandler().getIncorrectMessage());
                return;
            }
        } else {
            event.setCancelled(true);
            if (plugin.getGoogleAuthenticator().authorize(plugin.getGoogleAuthenticator().getCredentialRepository().getSecretKey(player.getUniqueId().toString()), getInt(event.getMessage()))) {
                playerData.getAuthenticationTask().succes();
                return;
            } else {
                player.sendMessage(plugin.getMessageHandler().getIncorrectMessage());
                return;
            }
        }
    }


    public int getInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException exception) {
            return 0;
        }
    }
}
