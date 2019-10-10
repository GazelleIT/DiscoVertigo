package com.gazelle.discovertigo;

import com.gazelle.discovertigo.entities.Stage;
import com.gazelle.discovertigo.entities.VertigoBeacon;
import com.gazelle.discovertigo.entities.VertigoCrystal;
import com.gazelle.discovertigo.files.ConfigReader;
import com.gazelle.discovertigo.gui.MainGUI;
import com.gazelle.discovertigo.gui.StageGUI;
import com.gazelle.discovertigo.gui.effects.CrystalEffectsGUI;
import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.AnimatedBallEffect;
import de.slikey.effectlib.effect.CloudEffect;
import de.slikey.effectlib.effect.SmokeEffect;
import de.slikey.effectlib.effect.SphereEffect;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Commands implements CommandExecutor {

    private Vertigo plugin;

    private Stage mainStage;
    private ConfigReader reader;

    public Commands(Vertigo plugin){
        this.plugin = plugin;
        reader = new ConfigReader(this.plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;

            if (command.getName().equalsIgnoreCase("disco")){
                if (args.length == 0) {
                    player.sendMessage("/Disco direction");
                } else {

                    // Open the direction's (regia) GUI
                    if (args[0].equalsIgnoreCase("direction")){
                        if (player.hasPermission("disco.admin.direction")){
                            MainGUI gui = new MainGUI(plugin, 27, "Direction GUI");
                            gui.openInventory(player);
                        }
                    }
                }
            }

            if (command.getName().equalsIgnoreCase("mainstage")) {
                if (args.length == 0) {
                    player.sendMessage("/Disco è figo");
                } else {
                    if (args[0].equalsIgnoreCase("rainbow")){

                    }
                }
            }


        }

        if (command.getName().equalsIgnoreCase("test")){

            SphereEffect effect = new SphereEffect(plugin.getEffectManager());
            effect.setEntity((Entity) sender);
            effect.particle = Particle.REDSTONE;
            effect.color = Color.BLUE;
            effect.yOffset = 2.0;
            effect.radius = 1.0;
            effect.radiusIncrease = 0.2;
            effect.particles = 200;

            effect.callback = new BukkitRunnable() {
                @Override
                public void run() {

                }
            };

            effect.iterations = 15 * 20;
            effect.start();

        }
        return false;
    }
}
