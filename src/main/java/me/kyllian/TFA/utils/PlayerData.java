package me.kyllian.TFA.utils;

import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import me.kyllian.TFA.TFAPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerData {

    private TFAPlugin plugin;
    private Player player;

    private AuthenticationTask authenticationTask;

    private boolean setup = false;
    private ItemStack itemInHand;

    public PlayerData(TFAPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public boolean hasKey() {
        return plugin.getGoogleAuthenticator().getCredentialRepository().getSecretKey(player.getUniqueId().toString()) != null;
    }

    public void createKey() {
        setup = true;
        itemInHand = plugin.getPlayerHandler().getItemInHand(player);
        GoogleAuthenticatorKey key = plugin.getGoogleAuthenticator().createCredentials(player.getUniqueId().toString());
        String finalKey = key.getKey();
        plugin.getMapHandler().sendMap(player, plugin.getQrHandler().getQRCode(player.getUniqueId().toString(), plugin.getConfig().getString("Creator"), finalKey));
        return;

    }

    public void setupComplete() {
        setup = false;
        plugin.getPlayerHandler().setItemInHand(player, itemInHand);
        itemInHand = null;
        player.sendMessage(plugin.getMessageHandler().getSetupMessage());
        return;
    }

    public void removeKey() {
        plugin.getPlayerHandler().getFileConfiguration().set(player.getUniqueId().toString() + ".code", null);
        plugin.getPlayerHandler().save();
    }

    public AuthenticationTask getAuthenticationTask() {
        return authenticationTask;
    }

    public void setAuthenticationTask(AuthenticationTask authenticationTask) {
        this.authenticationTask = authenticationTask;
    }

    public boolean isSetup() {
        return setup;
    }
}
