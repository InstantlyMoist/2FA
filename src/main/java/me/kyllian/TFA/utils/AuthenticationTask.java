package me.kyllian.TFA.utils;

import me.kyllian.TFA.TFAPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class AuthenticationTask extends BukkitRunnable {

    private TFAPlugin plugin;
    private Player player;

    private PlayerData playerData;
    private ItemStack itemInHand;

    private boolean cancelled;

    public AuthenticationTask(TFAPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        playerData = plugin.getPlayerHandler().getPlayerData(player);
        setItemInHand(plugin.getPlayerHandler().getItemInHand(player));

        plugin.getMapHandler().sendMap(player, plugin.getMessageHandler().getEnterCodeMessage());

        player.sendMessage(plugin.getMessageHandler().getEnterCodeChatMessage());

        runTaskLater(plugin, plugin.getConfig().getInt("TimeForAuthentication"));
    }

    public void succes() {
        player.sendMessage(plugin.getMessageHandler().getCorrectMessage());
        plugin.getPlayerHandler().setItemInHand(player, getItemInHand());
        playerData.setAuthenticationTask(null);
    }

    public ItemStack getItemInHand() {
        return itemInHand;
    }

    public void setItemInHand(ItemStack itemInHand) {
        this.itemInHand = itemInHand;
    }

    @Override
    public void run() {
        if (isCancelled()) return;
        player.kickPlayer(plugin.getMessageHandler().getKickMessage());
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
