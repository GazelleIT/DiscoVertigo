package com.gazelle.discovertigo.gui;

import com.gazelle.discovertigo.Vertigo;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGUI extends GUI implements Listener {

    public MainGUI(Vertigo plugin, int size, String name) {
        super(plugin, size, name);

        // Registering GUI Listener for player interactions
        boolean flag = false;
        for (RegisteredListener registeredListener : HandlerList.getRegisteredListeners(plugin)){
            if (registeredListener.getListener().toString().contains(this.getClass().getSimpleName())){
                flag = true;
            }
        }
        if (!flag){
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }

        this.setInventory();
    }

    @Override
    public void setInventory() {
        List<String> names = plugin.getConfigReader().getStagesName();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Click for open");
        lore.add("The stage's menu");

        for (int i = 0; i < names.size(); i++){
            addItem(names.get(i), lore, getRandomShulker(), 1, i);
        }
    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        String invName = e.getInventory().getName();
        if (invName.equals(inv.getName())) {
            if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem() != null) {
                Player player = (Player) e.getWhoClicked();
                e.setCancelled(true);

                String stageName = e.getCurrentItem().getItemMeta().getDisplayName();

                if (stageName != null) {
                    this.closeInventory(player);
                    HandlerList.unregisterAll(this);
                    new StageGUI(plugin, 54, stageName + " GUI", stageName, null).openInventory(player);
                }
            }
        }
    }


    private Material getRandomShulker(){
        Random rnd = new Random();
        Material[] m = {Material.BLACK_SHULKER_BOX, Material.SILVER_SHULKER_BOX, Material.BLUE_SHULKER_BOX, Material.BROWN_SHULKER_BOX,
                Material.CYAN_SHULKER_BOX, Material.GREEN_SHULKER_BOX, Material.LIGHT_BLUE_SHULKER_BOX, Material.LIME_SHULKER_BOX,
                Material.MAGENTA_SHULKER_BOX, Material.ORANGE_SHULKER_BOX, Material.RED_SHULKER_BOX, Material.WHITE_SHULKER_BOX,
                Material.YELLOW_SHULKER_BOX, Material.RED_SHULKER_BOX, Material.PINK_SHULKER_BOX};

        return m[rnd.nextInt(m.length)];
    }
}
