package com.gazelle.discovertigo.gui.effects;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.entities.Stage;
import com.gazelle.discovertigo.entities.VertigoScreen;
import com.gazelle.discovertigo.gui.GUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.RegisteredListener;

import java.util.ArrayList;
import java.util.List;

public class ScreenEffectsGUI extends GUI implements Listener {

    private List<VertigoScreen> screens;
    private Stage stage;

    public ScreenEffectsGUI(Vertigo plugin, int size, String GUIname, String stageName) {
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

        screens = plugin.getConfigReader().getStageScreens(stageName);
        stage = plugin.getStage(stageName);

        this.setInventory();
    }

    @Override
    public void setInventory() {
        ArrayList<String> lore = new ArrayList<>();
        String name = "";


    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

    }
}
