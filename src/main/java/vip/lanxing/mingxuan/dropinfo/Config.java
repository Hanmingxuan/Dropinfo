package vip.lanxing.mingxuan.dropinfo;

public class Config {
    private final Dropinfo plugin;
    private int checkInterval;
    private double radius;
    private int maxItems;

    public Config(Dropinfo plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        plugin.reloadConfig();
        checkInterval = plugin.getConfig().getInt("check-interval", 100);
        radius = plugin.getConfig().getDouble("radius", 3.0);
        maxItems = plugin.getConfig().getInt("max-items", 10);
    }

    // Getters
    public int getCheckInterval() { return checkInterval; }
    public double getRadius() { return radius; }
    public int getMaxItems() { return maxItems; }
}