package com.gazelle.discovertigo.gui;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.entities.VertigoBeacon;
import com.gazelle.discovertigo.entities.VertigoCrystal;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredListener;

import java.util.*;

public class StageVisualGUI extends GUI implements Listener{

    private LinkedHashMap<VertigoBeacon, Integer> beacons;
    private LinkedHashMap<VertigoCrystal, Integer> crystals;

    public StageVisualGUI(Vertigo plugin, int size, String GUIname, String stageName){
        super(plugin, size, GUIname);

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

        this.beacons = plugin.getConfigReader().getBeaconsSlotsHashMap(stageName);
        this.crystals = plugin.getConfigReader().getCrystalSlotsHashMap(stageName);

        this.setInventory();
    }

    @Override
    public void setInventory(){
        ArrayList<String> lore = new ArrayList<>();
        int i = 0;
        String name;

        if (beacons != null){
            for (VertigoBeacon beacon : beacons.keySet()){
                name = "Beacon " + i++;
                lore.add("");
                lore.add("Color: " + beacon.getLastColor().toString());

                addItem(name, lore, Material.BEACON, 1, beacons.get(beacon));
                lore.clear();
            }
        }
        i = 0;

        if (crystals != null){
            for (VertigoCrystal crystal : crystals.keySet()){
                name = "Crystal " + i++;

                super.addItem(name, lore, Material.END_CRYSTAL, 1, crystals.get(crystal));
            }
        }
    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

    }
}
