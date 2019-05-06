package me.kyllian.TFA;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import me.kyllian.TFA.commands.TFACommand;
import me.kyllian.TFA.handlers.*;
import me.kyllian.TFA.listeners.*;
import org.bstats.bukkit.Metrics;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TFAPlugin extends JavaPlugin {

    private KeyHandler keyHandler;
    private MapHandler mapHandler;
    private MessageHandler messageHandler;
    private PlayerHandler playerHandler;
    private QRHandler qrHandler;

    private GoogleAuthenticator googleAuthenticator;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        initializeCommands();
        initializeHandlers();
        initializeListeners();

        Metrics metrics = new Metrics( this);

        googleAuthenticator = new GoogleAuthenticator();
        googleAuthenticator.setCredentialRepository(keyHandler);
    }

    public void initializeCommands() {
        getCommand("2FA").setExecutor(new TFACommand(this));
    }

    public void initializeHandlers() {
        keyHandler = new KeyHandler(this);
        mapHandler = new MapHandler();
        messageHandler = new MessageHandler(this);
        playerHandler = new PlayerHandler(this);
        qrHandler = new QRHandler();
    }

    public void initializeListeners() {
        new PlayerChatListener(this);
        new PlayerCommandPreprocessListener(this);
        new PlayerDropItemListener(this);
        new PlayerHeldItemListener(this);
        new PlayerInteractListener(this);
        new PlayerJoinListener(this);
        new PlayerMoveListener(this);
        new PlayerQuitListener(this);
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public MapHandler getMapHandler() {
        return mapHandler;
    }

    public GoogleAuthenticator getGoogleAuthenticator() {
        return googleAuthenticator;
    }

    public PlayerHandler getPlayerHandler() {
        return playerHandler;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public QRHandler getQrHandler() {
        return qrHandler;
    }
}
