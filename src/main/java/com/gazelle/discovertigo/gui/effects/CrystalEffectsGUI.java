package com.gazelle.discovertigo.gui.effects;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.beacons.RainbowEffect;
import com.gazelle.discovertigo.crystals.CustomPathEffect;
import com.gazelle.discovertigo.crystals.RandomStrokeEffect;
import com.gazelle.discovertigo.crystals.RotateEffect;
import com.gazelle.discovertigo.entities.Stage;
import com.gazelle.discovertigo.entities.VertigoCrystal;
import com.gazelle.discovertigo.gui.GUI;
import com.gazelle.discovertigo.gui.StageGUI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class CrystalEffectsGUI extends GUI implements Listener {

    private List<VertigoCrystal> crystals;
    private Stage stage;

    public CrystalEffectsGUI(Vertigo plugin, int size, String GUIname, String stageName) {
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

        this.crystals = plugin.getConfigReader().getStageCrystals(stageName);
        this.stage = plugin.getStage(stageName);

        this.setInventory();
    }

    @Override
    public void setInventory() {
        ArrayList<String> lore = new ArrayList<>();
        String name = "";

        // Slow Rotation
        name = ChatColor.GOLD + "Rotation Effect " + ChatColor.GREEN + "(Slow)";
        lore.add(ChatColor.WHITE + "Makes your crystals rotate");
        addItem(name, lore, Material.BOOK, 1, 0);
        lore.clear();
        // Normal Rotation
        name = ChatColor.GOLD + "Rotation Effect " + ChatColor.AQUA + "(Normal)";
        lore.add(ChatColor.WHITE + "Makes your crystals rotate");
        addItem(name, lore, Material.BOOK, 1, 1);
        lore.clear();
        // Fast Rotation
        name = ChatColor.GOLD + "Rotation Effect " + ChatColor.RED + "(Fast)";
        lore.add(ChatColor.WHITE + "Makes your crystals rotate");
        addItem(name, lore, Material.BOOK, 1, 2);
        lore.clear();

        // RandomStroke down, north slow
        name = ChatColor.GOLD + "RandomStroke Effect " + ChatColor.GREEN + "(Slow)";
        lore.add(ChatColor.BLUE + "(Down) " + ChatColor.DARK_BLUE + "(North)");
        lore.add(ChatColor.WHITE + "Makes your crystals random light up");
        addItem(name, lore, Material.BOOK, 1, 3);
        lore.clear();
        // RandomStroke down, north normal
        name = ChatColor.GOLD + "RandomStroke Effect " + ChatColor.AQUA + "(Normal)";
        lore.add(ChatColor.BLUE + "(Down) " + ChatColor.DARK_BLUE + "(North)");
        lore.add(ChatColor.WHITE + "Makes your crystals random light up");
        addItem(name, lore, Material.BOOK, 1, 4);
        lore.clear();
        // RandomStroke down, north fast
        name = ChatColor.GOLD + "RandomStroke Effect " + ChatColor.RED + "(Fast)";
        lore.add(ChatColor.BLUE + "(Down) " + ChatColor.DARK_BLUE + "(North)");
        lore.add(ChatColor.WHITE + "Makes your crystals random light up");
        addItem(name, lore, Material.BOOK, 1, 5);
        lore.clear();

        // RandomStroke down, east slow
        name = ChatColor.GOLD + "RandomStroke Effect " + ChatColor.GREEN + "(Slow)";
        lore.add(ChatColor.BLUE + "(Down) " + ChatColor.DARK_GREEN + "(East)");
        lore.add(ChatColor.WHITE + "Makes your crystals random light up");
        addItem(name, lore, Material.BOOK, 1, 6);
        lore.clear();
        // RandomStroke down, east normal
        name = ChatColor.GOLD + "RandomStroke Effect " + ChatColor.AQUA + "(Normal)";
        lore.add(ChatColor.BLUE + "(Down) " + ChatColor.DARK_GREEN + "(East)");
        lore.add(ChatColor.WHITE + "Makes your crystals random light up");
        addItem(name, lore, Material.BOOK, 1, 7);
        lore.clear();
        // RandomStroke down, east fast
        name = ChatColor.GOLD + "RandomStroke Effect " + ChatColor.RED + "(Fast)";
        lore.add(ChatColor.BLUE + "(Down) " + ChatColor.DARK_GREEN + "(East)");
        lore.add(ChatColor.WHITE + "Makes your crystals random light up");
        addItem(name, lore, Material.BOOK, 1, 8);
        lore.clear();

        // Custom Crystals Path
        int c = 9;
        for (String effectName : plugin.getConfigReader().getStageCustomCrystalsNames(stage.getName())){
            name = ChatColor.GOLD + effectName + ChatColor.LIGHT_PURPLE + " (Custom)";
            lore.add(ChatColor.WHITE + "A custom effect path");
            addItem(name, lore, Material.BOOK, 1, c++);
            lore.clear();
        }


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
                // Go back button
                if (e.getRawSlot() == 53) {
                    closeInventory(player);
                    HandlerList.unregisterAll(this);
                    new StageGUI(plugin, 54, stage.getName() + " GUI", stage.getName(), null).openInventory(player);
                }
                if (e.getRawSlot() == 52) {
                    stage.setNextCrystalEffect("");
                    stage.setCurrentCrystalEffect("");
                    for (VertigoCrystal crystal : crystals) {
                        crystal.setCrystalTarget(null);
                    }
                }

                // Rotate effect
                if (e.getRawSlot() == 0) {
                    setRotateCrystalEffect(0.03, plugin.getConfigReader().getCrystalsRadius(), plugin.getConfigReader().getCrystalsBeamLenght() * -1, "slowrotate");
                }
                if (e.getRawSlot() == 1) {
                    setRotateCrystalEffect(0.01, plugin.getConfigReader().getCrystalsRadius(), plugin.getConfigReader().getCrystalsBeamLenght() * -1, "normalrotate");
                }
                if (e.getRawSlot() == 2) {
                    setRotateCrystalEffect(0.005, plugin.getConfigReader().getCrystalsRadius(), plugin.getConfigReader().getCrystalsBeamLenght() * -1, "fastrotate");
                }
                if (e.getRawSlot() == 3) {
                    setRandomStrokeEffect(1.5, plugin.getConfigReader().getCrystalsRadius(), "strokenorthdownslow", plugin.getConfigReader().getCrystalsBeamLenght() * -1,
                            plugin.getConfigReader().getCrystalsIterations(), RandomStrokeEffect.StrokeType.DOWNWARD, RandomStrokeEffect.Coordinates.NORTH);
                }
                if (e.getRawSlot() == 4) {
                    setRandomStrokeEffect(0.7, plugin.getConfigReader().getCrystalsRadius(), "strokenorthdownnormal", plugin.getConfigReader().getCrystalsBeamLenght() * -1,
                            plugin.getConfigReader().getCrystalsIterations(), RandomStrokeEffect.StrokeType.DOWNWARD, RandomStrokeEffect.Coordinates.NORTH);
                }
                if (e.getRawSlot() == 5) {
                    setRandomStrokeEffect(0.3, plugin.getConfigReader().getCrystalsRadius(), "strokenorthdownfast", plugin.getConfigReader().getCrystalsBeamLenght() * -1,
                            plugin.getConfigReader().getCrystalsIterations(), RandomStrokeEffect.StrokeType.DOWNWARD, RandomStrokeEffect.Coordinates.NORTH);
                }
                if (e.getRawSlot() == 6) {
                    setRandomStrokeEffect(1.5, plugin.getConfigReader().getCrystalsRadius(), "strokeeastdownslow", plugin.getConfigReader().getCrystalsBeamLenght() * -1,
                            plugin.getConfigReader().getCrystalsIterations(), RandomStrokeEffect.StrokeType.DOWNWARD, RandomStrokeEffect.Coordinates.EAST);
                }
                if (e.getRawSlot() == 7) {
                    setRandomStrokeEffect(0.7, plugin.getConfigReader().getCrystalsRadius(), "strokeeastdownnormal", plugin.getConfigReader().getCrystalsBeamLenght() * -1,
                            plugin.getConfigReader().getCrystalsIterations(), RandomStrokeEffect.StrokeType.DOWNWARD, RandomStrokeEffect.Coordinates.EAST);
                }
                if (e.getRawSlot() == 8) {
                    setRandomStrokeEffect(0.3, plugin.getConfigReader().getCrystalsRadius(), "strokeeastdownfast", plugin.getConfigReader().getCrystalsBeamLenght() * -1,
                            plugin.getConfigReader().getCrystalsIterations(), RandomStrokeEffect.StrokeType.DOWNWARD, RandomStrokeEffect.Coordinates.EAST);
                }
                if (StringUtils.containsIgnoreCase(e.getCurrentItem().getItemMeta().getDisplayName(), "custom")) {
                    String text = e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[0];
                    setCustomCrystalPath(ChatColor.stripColor(text));
                }
            }
        }
    }

    private void setRotateCrystalEffect(double delay, int radius, int height, String effectName){
        delay *= 20;
        new RotateEffect(this.plugin, stage, effectName, crystals, radius, height).runTaskTimer(this.plugin, 0, (long) delay);
    }

    private void setRandomStrokeEffect(double delay, int radius, String effectName, int beamLenght, int iterations, RandomStrokeEffect.StrokeType strokeType, RandomStrokeEffect.Coordinates coordinate){
        delay *= 20;
        new RandomStrokeEffect(this.plugin, stage, effectName, crystals, radius, beamLenght, iterations, strokeType, coordinate).runTaskTimer(this.plugin, 0, (long) delay);
    }

    private void setCustomCrystalPath(String effectName){
        for (CustomPathEffect effect : plugin.getConfigReader().getStageCustomCrystals(stage.getName(), effectName)){

            effect.runTask(this.plugin);
        }
    }
}
