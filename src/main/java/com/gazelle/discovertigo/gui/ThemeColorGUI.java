package com.gazelle.discovertigo.gui;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.entities.Color;
import com.gazelle.discovertigo.entities.Stage;
import com.gazelle.discovertigo.entities.VertigoBeacon;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ThemeColorGUI extends GUI implements Listener {

    private String stageName;

    public ThemeColorGUI(Vertigo plugin, int size, String GUIname, String stageName) {
        super(plugin, size, GUIname);

        this.stageName = stageName;

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
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Click to set");
        lore.add("The ColorTheme");

        List<String> names = plugin.getConfigReader().getStageColorThemeNames(stageName);

        for (int i = 0; i < names.size(); i++){
            if (i <= 52 && checkThemeColor(names.get(i)))
                addItem(names.get(i), lore, Material.BOOK, 1, i);
        }

        lore.clear();
        String name = ChatColor.RED + "Go Back";
        addItem(name, lore, new ItemStack(Material.WOOL, 1, (byte) 14), 53);
    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        String invName = e.getInventory().getName();
        if (invName.equals(inv.getName())) {
            if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem() != null) {
                Player player = (Player) e.getWhoClicked();
                e.setCancelled(true);

                if (e.getRawSlot() == 53) {
                    closeInventory(player);
                    HandlerList.unregisterAll(this);
                    new StageGUI(plugin, 54, stageName, stageName, null).openInventory(player);
                }


                if (e.getCurrentItem().getItemMeta() != null && e.getRawSlot() != 53)
                    setColorTheme(getStageBeacons(), e.getCurrentItem().getItemMeta().getDisplayName());
            }
        }
    }

    private List<Color> getColorTheme(String name){
        return plugin.getConfigReader().getStageColorTheme(stageName, name);
    }

    private List<VertigoBeacon> getStageBeacons(){
        return plugin.getConfigReader().getStageBeacons(stageName);
    }

    private void setColorTheme(List<VertigoBeacon> beacons, String colorThemeName){
        List<Color> colorTheme = getColorTheme(colorThemeName);
        Set<Color> colors = new HashSet<>();
        for (int i = 0; i < beacons.size(); i++){
            if (colorTheme.get(i) != null){
                beacons.get(i).setColor(colorTheme.get(i));
            } else {
                beacons.get(i).setColor(Color.WHITE);
            }
            colors.add(colorTheme.get(i));
        }

        updateLastColorTheme(new ArrayList<>(colors), colorThemeName);
    }

    private void updateLastColorTheme(List<Color> colors, String colotThemeName){
        Stage stage = plugin.getStage(stageName);
        if (stage != null)
            stage.setColorTheme(colors, colotThemeName);
    }

    private boolean checkThemeColor(String themeColor){
        Stage stage = plugin.getStage(stageName);
        if (stage != null){
            return stage.isValidColorTheme(themeColor);
        }
        return false;
    }
}
