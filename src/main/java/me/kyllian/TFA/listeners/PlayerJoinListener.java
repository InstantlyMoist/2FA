package me.kyllian.TFA.listeners;

import me.kyllian.TFA.TFAPlugin;
import me.kyllian.TFA.utils.AuthenticationTask;
import me.kyllian.TFA.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private TFAPlugin plugin;

    public PlayerJoinListener(TFAPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerHandler().getPlayerData(player);
        if (!playerData.hasKey()) {
            player.sendMessage(plugin.getMessageHandler().getInfoMessage());
            return;
        }
        playerData.setAuthenticationTask(new AuthenticationTask(plugin, player));
        return;
    }
}
