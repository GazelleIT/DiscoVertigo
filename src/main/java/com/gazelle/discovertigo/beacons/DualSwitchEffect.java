package com.gazelle.discovertigo.beacons;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.entities.Color;
import com.gazelle.discovertigo.entities.Stage;
import com.gazelle.discovertigo.entities.VertigoBeacon;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class DualSwitchEffect extends BukkitRunnable {

    private List<VertigoBeacon> beacons;
    private final Vertigo plugin;
    private final Stage stage;

    private List<Color> colorTheme;
    private boolean mode;
    private String name;
    private Color lastColor;
    private boolean runned;
    private int i;

    public DualSwitchEffect(Vertigo plugin, Stage stage, List<VertigoBeacon> beacons, List<Color> colorTheme, boolean mode, String name){
        this.plugin = plugin;
        this.stage = stage;
        this.beacons = beacons;
        this.colorTheme = colorTheme;
        this.mode = mode;
        this.name = name;
        lastColor = colorTheme.get(0);
        runned = false;
        i = 0;
    }

    @Override
    public void run(){

        // Check if the beacons aren't controlled by any other Thread
        if (stage.getCurrentBeaconEffect().equalsIgnoreCase("") && stage.getNextBeaconEffect().equalsIgnoreCase(name)) {

            //Setting "red" semaphore
            stage.setCurrentBeaconEffect(name);

            // Check if provided colors are two, else this will cancel
            checkColorTheme();

            // Check if beacons color is one of the two provided, else this will cancel
            checkBeaconsColor();

            // Starting effect
            switchColors();

            //Setting "green" semaphore
            stage.setCurrentBeaconEffect("");

            // Setting "true", so this effect knows it has ran before
            runned = true;

            // Next this Thread will wait x seconds
        } else {
            if (!runned) {
                // Add this Thread to the waiting list
                this.runWaitingList();
            }
            // This effect has already been used, so it get removed
            this.cancel();
        }
    }

    private void runWaitingList(){
        // Requesting effect getting on waiting list
        stage.setNextBeaconEffect(name);

        new BukkitRunnable() {

            @Override
            public void run() {
                // Checking if still on the waiting list
                if (stage.getNextBeaconEffect().equalsIgnoreCase(name)){
                    // Checking if current effect can be run
                    if (stage.getCurrentBeaconEffect().equalsIgnoreCase("")){
                        // Launching the effect
                        new DualSwitchEffect(plugin, stage, beacons, colorTheme, mode, name);
                        this.cancel();
                    }
                } else {
                    // A new effect got on the list, cancelling
                    this.cancel();
                }
            }

        }.runTaskTimer(plugin, 0, (long) (0.3 * 20));
    }

    private void checkColorTheme(){
        if (colorTheme.size() != 2)
            this.cancel();
    }

    private void checkBeaconsColor(){
        boolean flag;
        for (VertigoBeacon beacon : beacons){
            flag = false;
            Color bcColor = beacon.getColor();
            if (bcColor.equals(colorTheme.get(0)) || bcColor.equals(colorTheme.get(1))
                    || bcColor.equals(Color.BLOCKED)){
                flag = true;
            }
            if (!flag)
                this.cancel();
        }
    }

    private void switchColors(){
        Color newColor = Color.WHITE;
        // Setting correct index
        setNextColorIndex();
        for (VertigoBeacon beacon : beacons){
            Color bcColor = beacon.getColor();
            // mode = true: all colors lighted up
            // mode = false: only one color at time will light up
            if (mode) {
                if (!bcColor.equals(Color.BLOCKED)) {
                    if (bcColor.equals(colorTheme.get(0))) {
                        beacon.setColor(colorTheme.get(1));
                    } else {
                        beacon.setColor(colorTheme.get(0));
                    }
                }
            } else {
                if (bcColor.equals(lastColor)){
                    beacon.setColor(Color.BLOCKED);
                } else if (bcColor.equals(Color.BLOCKED)) {
                    beacon.setColor(colorTheme.get(i));
                    newColor = bcColor;
                } else {
                    newColor = bcColor;
                }
            }
        }
        lastColor = newColor;
    }

    private void setNextColorIndex(){
        if (colorTheme.get(i).equals(lastColor)){
           if (i == 0){
               i = 1;
           } else {
               i = 0;
           }
        }
    }
}
