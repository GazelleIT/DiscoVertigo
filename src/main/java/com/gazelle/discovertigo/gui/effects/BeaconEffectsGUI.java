package com.gazelle.discovertigo.gui.effects;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.beacons.DualSwitchEffect;
import com.gazelle.discovertigo.beacons.RainbowEffect;
import com.gazelle.discovertigo.beacons.SingleBeaconEffect;
import com.gazelle.discovertigo.entities.Color;
import com.gazelle.discovertigo.entities.Stage;
import com.gazelle.discovertigo.entities.VertigoBeacon;
import com.gazelle.discovertigo.gui.GUI;
import com.gazelle.discovertigo.gui.StageGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredListener;

import java.util.*;

public class BeaconEffectsGUI extends GUI implements Listener {

    private List<VertigoBeacon> beacons;
    private Stage stage;

    public BeaconEffectsGUI(Vertigo plugin, int size, String GUIname, String stageName){
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

        this.beacons = plugin.getConfigReader().getStageBeacons(stageName);
        this.stage = plugin.getStage(stageName);

        this.setInventory();
    }

    @Override
    public void setInventory() {
        ArrayList<String> lore = new ArrayList<>();
        String name;


        // Fast DualSwitch
        name = ChatColor.GOLD + "DualSwitch Effect " + ChatColor.RED + "(Fast)";
        lore.add("Require a ThemeColor of 2 colors");
        if (stage.getColorTheme().size() != 2){
            lore.add("");
            lore.add(ChatColor.RED + "You don't have a ThemeColor of 2 colors!");
        }
        addItem(name, lore, Material.BOOK, 1, 0);
        lore.clear();
        // Normal DualSwitch
        name = ChatColor.GOLD + "DualSwitch Effect " + ChatColor.AQUA + "(Normal)";
        lore.add("Require a ThemeColor of 2 colors");
        if (stage.getColorTheme().size() != 2){
            lore.add("");
            lore.add(ChatColor.RED + "You don't have a ThemeColor of 2 colors!");
        }
        addItem(name, lore, Material.BOOK, 1, 1);
        lore.clear();
        // Slow DualSwitch
        name = ChatColor.GOLD + "DualSwitch Effect " + ChatColor.GREEN + "(Slow)";
        lore.add("Require a ThemeColor of 2 colors");
        lore.add("");
        if (stage.getColorTheme().size() != 2){
            lore.add("");
            lore.add(ChatColor.RED + "You don't have a ThemeColor of 2 colors!");
        }
        addItem(name, lore, Material.BOOK, 1, 2);
        lore.clear();

        // Slow DualSwitch
        name = ChatColor.GOLD + "DualSwitch Effect " + ChatColor.GREEN + "(Slow)"  + ChatColor.LIGHT_PURPLE + "(2 Version)";
        lore.add("Require a ThemeColor of 2 colors");
        if (stage.getColorTheme().size() != 2){
            lore.add("");
            lore.add(ChatColor.RED + "You don't have a ThemeColor of 2 colors!");
        }
        addItem(name, lore, Material.BOOK, 1, 3);
        lore.clear();
        // Normal DualSwitch
        name = ChatColor.GOLD + "DualSwitch Effect " + ChatColor.AQUA + "(Normal)"  + ChatColor.LIGHT_PURPLE + "(2 Version)";
        lore.add("Require a ThemeColor of 2 colors");
        if (stage.getColorTheme().size() != 2){
            lore.add("");
            lore.add(ChatColor.RED + "You don't have a ThemeColor of 2 colors!");
        }
        addItem(name, lore, Material.BOOK, 1, 4);
        lore.clear();
        // Fast DualSwitch
        name = ChatColor.GOLD + "DualSwitch Effect " + ChatColor.RED + "(Fast)"  + ChatColor.LIGHT_PURPLE + "(2 Version)";
        lore.add("Require a ThemeColor of 2 colors");
        lore.add("");
        if (stage.getColorTheme().size() != 2){
            lore.add("");
            lore.add(ChatColor.RED + "You don't have a ThemeColor of 2 colors!");
        }
        addItem(name, lore, Material.BOOK, 1, 5);
        lore.clear();

        // Slow SingleBeacon
        name = ChatColor.GOLD + "SingleBeacon Effect " + ChatColor.GREEN + "(Slow)";
        lore.add("Require a ThemeColor");
        lore.add("");
        lore.add(ChatColor.WHITE + "Turn on the beacons one at a time");
        lore.add(ChatColor.WHITE + "When a new one comes on");
        lore.add(ChatColor.WHITE + "the old one goes out");
        if (stage.getColorTheme().size() != 2){
            lore.add("");
            lore.add(ChatColor.RED + "You don't have a ThemeColor of 2 colors!");
        }
        addItem(name, lore, Material.BOOK, 1, 6);
        lore.clear();
        // Normal SingleBeacon
        name = ChatColor.GOLD + "SingleBeacon Effect " + ChatColor.AQUA + "(Normal)";
        lore.add("Require a ThemeColor of 2 colors");
        lore.add("");
        lore.add(ChatColor.WHITE + "Turn on the beacons one at a time");
        lore.add(ChatColor.WHITE + "When a new one comes on");
        lore.add(ChatColor.WHITE + "the old one goes out");
        if (stage.getColorTheme().size() != 2){
            lore.add("");
            lore.add(ChatColor.RED + "You don't have a ThemeColor of 2 colors!");
        }
        addItem(name, lore, Material.BOOK, 1, 7);
        lore.clear();
        // Fast SingleBeacon
        name = ChatColor.GOLD + "SingleBeacon Effect " + ChatColor.RED + "(Fast)";
        lore.add("Require a ThemeColor of 2 colors");
        lore.add("");
        lore.add(ChatColor.WHITE + "Turn on the beacons one at a time");
        lore.add(ChatColor.WHITE + "When a new one comes on");
        lore.add(ChatColor.WHITE + "the old one goes out");
        if (stage.getColorTheme().size() != 2){
            lore.add("");
            lore.add(ChatColor.RED + "You don't have a ThemeColor of 2 colors!");
        }
        addItem(name, lore, Material.BOOK, 1, 8);
        lore.clear();
        // Slow SingleBeacon delayed
        name = ChatColor.GOLD + "SingleBeacon Effect " + ChatColor.GREEN + "(Slow)" + ChatColor.LIGHT_PURPLE + "(2 Version)";
        lore.add("Require a ThemeColor of 2 colors");
        lore.add("");
        lore.add(ChatColor.WHITE + "Turn on the beacons one at a time");
        lore.add(ChatColor.WHITE + "When a new one comes on");
        lore.add(ChatColor.WHITE + "the old one goes out after some time");
        if (stage.getColorTheme().size() != 2){
            lore.add("");
            lore.add(ChatColor.RED + "You don't have a ThemeColor of 2 colors!");
        }
        addItem(name, lore, Material.BOOK, 1, 9);
        lore.clear();
        // Normal SingleBeacon delayed
        name = ChatColor.GOLD + "SingleBeacon Effect " + ChatColor.AQUA + "(Normal)" + ChatColor.LIGHT_PURPLE + "(2 Version)";
        lore.add("Require a ThemeColor of 2 colors");
        lore.add("");
        lore.add(ChatColor.WHITE + "Turn on the beacons one at a time");
        lore.add(ChatColor.WHITE + "When a new one comes on");
        lore.add(ChatColor.WHITE + "the old one goes out after some time");
        if (stage.getColorTheme().size() != 2){
            lore.add("");
            lore.add(ChatColor.RED + "You don't have a ThemeColor of 2 colors!");
        }
        addItem(name, lore, Material.BOOK, 1, 10);
        lore.clear();
        // Fast SingleBeacon delayed
        name = ChatColor.GOLD + "SingleBeacon Effect " + ChatColor.RED + "(Fast)" + ChatColor.LIGHT_PURPLE + "(2 Version)";
        lore.add("Require a ThemeColor of 2 colors");
        lore.add("");
        lore.add(ChatColor.WHITE + "Turn on the beacons one at a time");
        lore.add(ChatColor.WHITE + "When a new one comes on");
        lore.add(ChatColor.WHITE + "the old one goes out after some time");
        if (stage.getColorTheme().size() != 2){
            lore.add("");
            lore.add(ChatColor.RED + "You don't have a ThemeColor of 2 colors!");
        }
        addItem(name, lore, Material.BOOK, 1, 11);
        lore.clear();

        // Slow RainbowEffect
        name = ChatColor.GOLD + "Rainbow Effect " + ChatColor.GREEN + "(Slow)";
        lore.add("Transform your beacons into a rainbow");
        addItem(name, lore, Material.BOOK, 1, 12);
        lore.clear();
        // Normal RainbowEffect
        name = ChatColor.GOLD + "Rainbow Effect " + ChatColor.AQUA + "(Normal)";
        lore.add("Transform your beacons into a rainbow");
        addItem(name, lore, Material.BOOK, 1, 13);
        lore.clear();
        // Fast RainbowEffect
        name = ChatColor.GOLD + "Rainbow Effect " + ChatColor.RED + "(Fast)";
        lore.add("Transform your beacons into a rainbow");
        addItem(name, lore, Material.BOOK, 1, 14);
        lore.clear();

        // Reset button
        lore.clear();
        name = ChatColor.RED + "Stop Effects";
        addItem(name, lore, Material.BARRIER, 1, 52);

        // Go Back button
        lore.clear();
        name = ChatColor.RED + "Go Back";
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
                    new StageGUI(plugin, 54, stage.getName() + " GUI", stage.getName(), null).openInventory(player);
                }
                if (e.getRawSlot() == 52) {
                    stage.setNextBeaconEffect("");
                    stage.setCurrentBeaconEffect("");
                    for (VertigoBeacon beacon : beacons) {
                        beacon.setColor(Color.BLOCKED);
                    }
                }


                if (e.getRawSlot() == 0) {
                    if (stage.getColorTheme().size() == 2) {
                        setColorTheme(beacons, getColorTheme(stage.getLastColorThemeName()));
                        setDualSwitchEffect(stage, 0.5, true, "fastdualswitch");
                    } else
                        player.sendMessage(ChatColor.RED + "You need a ThemeColor of 2 colors for this effect!");
                }
                if (e.getRawSlot() == 1) {
                    if (stage.getColorTheme().size() == 2) {
                        setColorTheme(beacons, getColorTheme(stage.getLastColorThemeName()));
                        setDualSwitchEffect(stage, 1, true, "normaldualswitch");
                    } else
                        player.sendMessage(ChatColor.RED + "You need a ThemeColor of 2 colors for this effect!");
                }
                if (e.getRawSlot() == 2) {
                    if (stage.getColorTheme().size() == 2) {
                        setColorTheme(beacons, getColorTheme(stage.getLastColorThemeName()));
                        setDualSwitchEffect(stage, 2, true, "slowdualswitch");
                    } else
                        player.sendMessage(ChatColor.RED + "You need a ThemeColor of 2 colors for this effect!");
                }

                if (e.getRawSlot() == 3) {
                    if (stage.getColorTheme().size() == 2) {
                        setColorTheme(beacons, getColorTheme(stage.getLastColorThemeName()));
                        setDualSwitchEffect(stage, 1.7, false, "slowdualswitch2");
                    } else
                        player.sendMessage(ChatColor.RED + "You need a ThemeColor of 2 colors for this effect!");
                }
                if (e.getRawSlot() == 4) {
                    if (stage.getColorTheme().size() == 2) {
                        setColorTheme(beacons, getColorTheme(stage.getLastColorThemeName()));
                        setDualSwitchEffect(stage, 0.7, false, "normaldualswitch2");
                    } else
                        player.sendMessage(ChatColor.RED + "You need a ThemeColor of 2 colors for this effect!");
                }
                if (e.getRawSlot() == 5) {
                    if (stage.getColorTheme().size() == 2) {
                        setColorTheme(beacons, getColorTheme(stage.getLastColorThemeName()));
                        setDualSwitchEffect(stage, 0.3, false, "fastdualswitch2");
                    } else
                        player.sendMessage(ChatColor.RED + "You need a ThemeColor of 2 colors for this effect!");
                }

                if (e.getRawSlot() == 6) {
                    if (stage.getColorTheme().size() > 0) {
                        setSingleBeaconEffect(stage, "slowsinglebeacon", 2, 0, false);
                    } else
                        player.sendMessage(ChatColor.RED + "You need a ThemeColor for this effect!");
                }
                if (e.getRawSlot() == 7) {
                    if (stage.getColorTheme().size() > 0) {
                        setSingleBeaconEffect(stage, "normalsinglebeacon", 1, 0, false);
                    } else
                        player.sendMessage(ChatColor.RED + "You need a ThemeColor for this effect!");
                }
                if (e.getRawSlot() == 8) {
                    if (stage.getColorTheme().size() > 0) {
                        setSingleBeaconEffect(stage, "fastsinglebeacon", 0.5, 0, false);
                    } else
                        player.sendMessage(ChatColor.RED + "You need a ThemeColor for this effect!");
                }
                if (e.getRawSlot() == 9) {
                    if (stage.getColorTheme().size() > 0) {
                        setSingleBeaconEffect(stage, "slowsinglebeacon2", 2, 1, true);
                    } else
                        player.sendMessage(ChatColor.RED + "You need a ThemeColor for this effect!");
                }
                if (e.getRawSlot() == 10) {
                    if (stage.getColorTheme().size() > 0) {
                        setSingleBeaconEffect(stage, "normalsinglebeacon2", 1, 0.5, true);
                    } else
                        player.sendMessage(ChatColor.RED + "You need a ThemeColor for this effect!");
                }
                if (e.getRawSlot() == 11) {
                    if (stage.getColorTheme().size() > 0) {
                        setSingleBeaconEffect(stage, "fastsinglebeacon2", 0.5, 0.3, true);
                    } else
                        player.sendMessage(ChatColor.RED + "You need a ThemeColor for this effect!");
                }
                if (e.getRawSlot() == 12) {
                    setRainbowEffect(stage, 1.5, "slowrainboweffect");
                }
                if (e.getRawSlot() == 13) {
                    setRainbowEffect(stage, 0.7, "normalrainboweffect");
                }
                if (e.getRawSlot() == 14) {
                    setRainbowEffect(stage, 0.3, "fastrainboweffect");
                }
            }

        }
    }

    private void setRandomColorAll(){
        Random rand = new Random();
        for (VertigoBeacon beacon : beacons){
            beacon.setColor(Color.getColor(rand.nextInt(15)));
        }
    }

    private List<Color> getColorTheme(String name){
        return plugin.getConfigReader().getStageColorTheme(stage.getName(), name);
    }

    private void setColorTheme(List<VertigoBeacon> beacons, List<Color> colorTheme){
        for (int i = 0; i < beacons.size(); i++){
            if (colorTheme.get(i) != null){
                beacons.get(i).setColor(colorTheme.get(i));
            } else {
                beacons.get(i).setColor(Color.WHITE);
            }
        }
    }

    /**
     * @param delay: delay between beacons switches
     */
    private void setDualSwitchEffect(Stage stage, double delay, boolean mode, String effectName){
        delay *= 20;
        new DualSwitchEffect(super.plugin, stage, this.beacons, stage.getColorTheme(), mode, effectName).runTaskTimer(super.plugin, 0, (long) delay);
    }

    /**
     * @param delay: set delay between color changes
     */
    private void setRainbowEffect(Stage stage, double delay, String effectName){
        delay *= 20;
        new RainbowEffect(super.plugin, stage, this.beacons, effectName).runTaskTimer(super.plugin, 0, (long) delay);
    }

    /**
     * @param delay: delay between beacons power off and on
     * @param lastBeaconDelay: time after that last turned on beacon gets turned off, if mode set to "true"
     * @param mode: if "true" last beacon will turn off after "lastBeaconDelay", else it will turn off when
     *            the next beacon will turn on
     */
    private void setSingleBeaconEffect(Stage stage, String effectName, double delay, double lastBeaconDelay, boolean mode){
        delay *= 20;
        lastBeaconDelay *= 20;
        new SingleBeaconEffect(super.plugin, stage, this.beacons, stage.getColorTheme(), effectName, (long) lastBeaconDelay, mode).runTaskTimer(super.plugin, 0, (long) delay);
    }
}
