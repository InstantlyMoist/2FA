package me.kyllian.TFA.utils;

import me.kyllian.TFA.TFAPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AuthenticationTask {

    private TFAPlugin plugin;
    private Player player;

    private PlayerData playerData;
    private ItemStack itemInHand;

    public AuthenticationTask(TFAPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        playerData = plugin.getPlayerHandler().getPlayerData(player);
        setItemInHand(plugin.getPlayerHandler().getItemInHand(player));

        plugin.getMapHandler().sendMap(player, plugin.getMessageHandler().getEnterCodeMessage());
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
}
