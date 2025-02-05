package vip.lanxing.mingxuan.dropinfo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Dropinfo extends JavaPlugin {
    private Config config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = new Config(this);
        getServer().getPluginManager().registerEvents(new DropListener(this), this);
        getCommand("dropinfo").setExecutor(this);

        // Plain text English messages
        getLogger().info("[Dropinfo] Enabled");
        getLogger().info("[Dropinfo] Version: " + getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        // Plain text English message
        getLogger().info("[Dropinfo] Disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("dropinfo.reload")) {
                config.reload();
                sender.sendMessage("[Dropinfo] Configuration reloaded");
                return true;
            }
        }
        return false;
    }

    public Config getPluginConfig() {
        return config;
    }
}