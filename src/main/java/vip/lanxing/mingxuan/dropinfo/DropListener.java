package vip.lanxing.mingxuan.dropinfo;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

public class DropListener implements Listener {
    private final Dropinfo plugin;

    public DropListener(Dropinfo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof Item item)) return;

        ItemStack stack = item.getItemStack();
        String translateKey = getTranslateKey(stack.getType());
        Component name = Component.translatable(translateKey);

        // 仅在物品可堆叠时显示数量
        if (stack.getMaxStackSize() > 1) {
            name = name.append(Component.text(" [x" + stack.getAmount() + "]"));
        }

        name = name.decoration(TextDecoration.ITALIC, false);

        item.customName(name);
        item.setCustomNameVisible(true);

        // Folia 兼容的定时任务
        item.getScheduler().runAtFixedRate(plugin, task -> {
            if (item.isDead()) {
                task.cancel();
                return;
            }

            boolean shouldShow = checkDensity(item);
            if (item.isCustomNameVisible() != shouldShow) {
                item.setCustomNameVisible(shouldShow);
            }
        }, null, 20L, plugin.getPluginConfig().getCheckInterval());
    }

    private String getTranslateKey(Material material) {
        return (material.isBlock() ? "block" : "item") + ".minecraft." + material.getKey().getKey();
    }

    private boolean checkDensity(Item item) {
        double radius = plugin.getPluginConfig().getRadius();
        return item.getLocation().getNearbyEntities(radius, radius, radius)
                .stream()
                .filter(e -> e instanceof Item && e != item)
                .count() <= plugin.getPluginConfig().getMaxItems();
    }
}