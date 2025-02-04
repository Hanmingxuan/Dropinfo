package vip.lanxing.mingxuan.dropinfo;

import org.bukkit.plugin.java.JavaPlugin;

public final class Dropinfo extends JavaPlugin {
    private Config config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = new Config(this);
        getServer().getPluginManager().registerEvents(new DropListener(this), this);

        // 加载消息
        getLogger().info("Dropinfo has been enabled!");
        getLogger().info("Version: " + getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        // 卸载消息
        getLogger().info("Dropinfo has been disabled!");
    }

    public Config getPluginConfig() {
        return config;
    }
}