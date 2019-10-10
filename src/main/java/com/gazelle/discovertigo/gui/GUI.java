package com.gazelle.discovertigo.gui;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.entities.VertigoBeacon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GUI{

    protected final Inventory inv;
    protected final Vertigo plugin;

    public GUI(Vertigo plugin, int size, String name) {
        this.plugin =  plugin;
        inv = Bukkit.createInventory(null, size, name);
    }

    public abstract void setInventory();

    // You can call this whenever you want to put the items in
    protected void addItem(String name, ArrayList<String> lore, Material material, int amount, int slot){
        inv.setItem(slot, createGuiItem(name, lore, material, amount));
    }

    protected void addItem(String name, ArrayList<String> lore, ItemStack item, int slot){
        inv.setItem(slot, createGuiItem(name, lore, item));
    }

    private ItemStack createGuiItem(String name, ArrayList<String> desc, ItemStack i){
        ItemMeta iMeta = i.getItemMeta();
        iMeta.setDisplayName(name);
        iMeta.setLore(desc);
        i.setItemMeta(iMeta);
        return i;
    }

    // Nice little method to create a gui item with a custom name, and description
    private ItemStack createGuiItem(String name, ArrayList<String> desc, Material mat, int amount) {
        ItemStack i = new ItemStack(mat, amount);
        ItemMeta iMeta = i.getItemMeta();
        iMeta.setDisplayName(name);
        iMeta.setLore(desc);
        i.setItemMeta(iMeta);
        return i;
    }

    // You can open the inventory with this
    public void openInventory(Player p) {
        p.openInventory(inv);
    }

    public void closeInventory(Player p){ p.closeInventory(); }

    // Check for clicks on items
    @EventHandler
    public abstract void onInventoryClick(InventoryClickEvent e);
        /*String invName = e.getInventory().getName();
        if (!invName.equals(inv.getName())) {
            return;
        }
        if (e.getClick().equals(ClickType.NUMBER_KEY)){
            e.setCancelled(true);
        }
        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().equals(Material.AIR)) return;

        // Using slots click is a best option for your inventory click's
        if (e.getRawSlot() == 10) p.sendMessage("You clicked at slot " + 10);*/
}
