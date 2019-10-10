package com.gazelle.discovertigo.crystals;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.entities.Stage;
import com.gazelle.discovertigo.entities.VertigoBeacon;
import com.gazelle.discovertigo.entities.VertigoCrystal;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.logging.Level;

public class CustomPathEffect extends BukkitRunnable {

    private final Vertigo plugin;
    private final Stage stage;
    private final String name;
    private String id;
    private boolean runned;

    private int count;

    private List<VertigoCrystal> crystals;
    private List<String> commands;

    public CustomPathEffect(Vertigo plugin, Stage stage, String name, List<VertigoCrystal> crystals, List<String> commands, int count, String id){
        this.plugin = plugin;
        this.stage = stage;
        this.name = name;
        this.crystals = crystals;
        this.commands = commands;
        this.id = id;

        //plugin.getServer().getConsoleSender().sendMessage("started - " + name + id);

        this.count = (count != (commands.size())) ? count : 0;

        runned = false;
    }

    @Override
    public void run(){

        plugin.getServer().getConsoleSender().sendMessage("runned - " + name + id);

        // Check if the beacons aren't controlled by any other Thread
        if (stage.getCurrentCrystalEffect().equalsIgnoreCase("") && stage.getNextCrystalEffect().equalsIgnoreCase(name)) {

            //plugin.getServer().getConsoleSender().sendMessage("validated - " + name + id);

            //Setting "red" semaphore
            stage.setCurrentCrystalEffect(name);

            drawEffect();

            //Setting "green" semaphore
            stage.setCurrentCrystalEffect("");

            // Setting "true", so this effect knows it has ran before
            runned = true;

            // Next this Thread will wait x seconds
        } else {
            if (!runned) {
                // Add this Thread to the waiting list
                this.runWaitingList(0);
            }
            // This effect has already been used, so it get removed
            this.cancel();
        }
    }

    private void runWaitingList(double delay){
        //plugin.getServer().getConsoleSender().sendMessage("waitingList - " + name + id);

        // Requesting effect getting on waiting list
        stage.setNextCrystalEffect(name);

        new BukkitRunnable() {

            @Override
            public void run() {
                // Checking if still on the waiting list
                if (stage.getNextCrystalEffect().equalsIgnoreCase(name)){
                    // Checking if current effect can be run
                    if (stage.getCurrentCrystalEffect().equalsIgnoreCase("")){
                        // Launching the effect
                        new CustomPathEffect(plugin, stage, name, crystals, commands, count, id).runTask(plugin);
                        this.cancel();
                    }
                } else {
                    // A new effect got on the list, cancelling
                    this.cancel();
                }
            }

        }.runTaskTimer(plugin, (long) (delay * 20), (long) (0.3 * 20));
    }

    private void drawEffect(){
        while(count < commands.size()) {
            String command = commands.get(count);
            //plugin.getServer().getConsoleSender().sendMessage("current command: " + command  + " - "  + name + id);
            if (StringUtils.containsIgnoreCase(command, "delay")) {
                String[] strings = command.split(" ");
                if (strings.length > 1) {
                    try {
                        // Trying to convert string number to int, if successful start delay
                        double delay = Double.parseDouble(strings[1]);

                        //Setting "green" semaphore
                        stage.setCurrentCrystalEffect("");

                        // Setting "true", so this effect knows it has ran before
                        runned = true;

                        count++;

                        //plugin.getServer().getConsoleSender().sendMessage("delayed - " + name + id);

                        // Running delay
                        runWaitingList(delay);

                        return;
                    } catch (NumberFormatException ex) {
                        plugin.getLogger().log(Level.SEVERE, ex.getMessage());
                        this.cancel();
                        return;
                    }
                }
            } else if (StringUtils.containsIgnoreCase(command, "blocked")) {
                // Setting off the crystal
                setBeamTarget(null);
                //plugin.getServer().getConsoleSender().sendMessage("blocked - " + name + id);
            } else {
                Location loc = plugin.getConfigReader().getLocationFromString(command);
                if (loc != null) {
                    // Setting crystal's target
                    setBeamTarget(loc);
                    //plugin.getServer().getConsoleSender().sendMessage("target - " + name + id);
                } else {
                    // Invalid command, cancelling effect
                    this.cancel();
                    return;
                }
            }

            count++;
            if (count >= commands.size()){
                runWaitingList(0);
            }
        }
    }

    private void setBeamTarget(Location loc){
        for (VertigoCrystal crystal : crystals){
            //plugin.getServer().getConsoleSender().sendMessage("Targeting with: - " + crystal.getCrystalLocation().toString() + " " + name + id);
            crystal.setCrystalTarget(loc);
        }
    }


}
