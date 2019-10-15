package de.strifel.deathchest;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class DeathChest extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void deathEvent(PlayerDeathEvent event) {
        if (!event.getKeepInventory()) {
            if (!(event.getEntity().getGameMode() == GameMode.CREATIVE || removeChest(event.getDrops()))) return;
            Location loc = event.getEntity().getLocation();
            loc.getBlock().setType(Material.CHEST);
            Chest chest = (Chest) loc.getBlock().getState();
            chest.getBlockInventory().addItem(Arrays.copyOf(event.getDrops().toArray(), event.getDrops().size(), ItemStack[].class));
            event.getDrops().clear();
        }
    }


    private boolean removeChest(List<ItemStack> items) {
        for (ItemStack item : items) {
            if (item.getType() == Material.CHEST) {
                if (item.getAmount() == 1) {
                    item.setType(Material.AIR);
                } else {
                    item.setAmount(item.getAmount() - 1);
                }
                return true;
            }
        }
        return false;
    }

}
