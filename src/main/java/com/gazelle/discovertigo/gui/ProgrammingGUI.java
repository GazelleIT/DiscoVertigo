package com.gazelle.discovertigo.gui;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.entities.Color;
import com.gazelle.discovertigo.entities.VertigoBeacon;
import com.gazelle.discovertigo.entities.VertigoCrystal;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ProgrammingGUI extends GUI implements Listener {

    private List<String> p_effects;

    private LinkedHashMap<VertigoBeacon, Integer> beacons;
    private LinkedHashMap<VertigoCrystal, Integer> crystals;


    public ProgrammingGUI(Vertigo plugin, int size, String GUIname, String stageName){
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

        p_effects = new ArrayList<>();

        this.setInventory();
    }

    @Override
    public void setInventory() {
        ArrayList<String> lore = new ArrayList<>();
        String name;

        // On-Off Button
        if (areBeaconsOff()){
            name = ChatColor.GRAY + "Switch " + ChatColor.GREEN + "ON" + ChatColor.GRAY + " lights";
            addItem(name, lore, new ItemStack(Material.WOOL, 1, (byte)0), 4);
        } else {
            name = ChatColor.GRAY + "Switch " + ChatColor.RED + "OFF" + ChatColor.GRAY + " lights";
            addItem(name, lore, new ItemStack(Material.WOOL, 1, (byte)7), 4);
        }

        // Enter programming mode button
        name = "Enter programming mode";
        addItem(name, lore, new ItemStack(Material.WOOL, 1, (byte)8), 27);

        // Activate programmed effects
        if (p_effects.isEmpty()){
            name = "No programmed effects";
            addItem(name, lore, new ItemStack(Material.WOOL, 1, (byte)14), 37);
        } else {
            name = "Activate programmed effects";
            addItem(name, lore, new ItemStack(Material.WOOL, 1, (byte)4), 37);
        }

        // ThemeColors button
        name = "ThemeColors settings";
        addItem(name, lore, Material.BOOK_AND_QUILL, 1, 19);

        // Beacons Effects
        name = "Beacons Effects";
        addItem(name, lore, Material.BEACON, 1, 24);

        // Crystals Effects
        name = "Crystals Effects";
        addItem(name, lore, Material.END_CRYSTAL, 1, 26);

        // Glowstone Effects
        name = "Glowstone Effects";
        addItem(name, lore, Material.GLOWSTONE, 1, 33);

        // Fountain Effects
        name = "Fountain Effects";
        addItem(name, lore, Material.WATER_BUCKET, 1, 35);
    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        String invName = e.getInventory().getName();
        if (invName.equals(inv.getName())) {
            if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem() != null) {
                Player player = (Player) e.getWhoClicked();
                e.setCancelled(true);

                // switch on-off button
                if (e.getRawSlot() == 4) {
                    ItemStack clickedItem = e.getCurrentItem();
                    if (clickedItem != null) {
                        if (clickedItem.getItemMeta().getDisplayName().contains("ON")) {
                            this.showBeacons(true);
                        } else {
                            this.showBeacons(false);
                        }
                        setInventory();
                        return;
                    }
                }

                // ThemeColors button
                if (e.getRawSlot() == 10) {
                    closeInventory(player);

                    return;
                }
            }
        }
    }

    private boolean areBeaconsOff(){
        boolean flag = true;
        for (VertigoBeacon beacon : beacons.keySet()){
            if (!beacon.getColor().equals(Color.BLOCKED))
                flag = false;
        }
        return flag;
    }

    private void showBeacons(boolean flag){
        for (VertigoBeacon beacon : beacons.keySet()){
            beacon.setVisibleBeam(flag);
        }
    }
}
