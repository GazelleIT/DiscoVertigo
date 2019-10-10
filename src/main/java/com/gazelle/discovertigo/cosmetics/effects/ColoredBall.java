package com.gazelle.discovertigo.cosmetics.effects;

import com.gazelle.discovertigo.Vertigo;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.CloudEffect;
import de.slikey.effectlib.effect.SmokeEffect;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ColoredBall {

    private Vertigo plugin;
    private Player player;

    private int duration;
    private int particles;

    public ColoredBall(Vertigo plugin, Player targetPlayer, int duration, int nParticles){
        this.plugin = plugin;

        player = targetPlayer;
        this.duration = duration;
        particles = nParticles;
    }

    public void playEffect(Color ballColor){
        plugin.getServer().getConsoleSender().sendMessage("ciao");
        SmokeEffect effect = new SmokeEffect(plugin.getEffectManager());
        effect.duration = duration;
        effect.particleCount = particles;
        effect.particle = Particle.REDSTONE;
        effect.color = ballColor;
        effect.type = EffectType.INSTANT;
        effect.asynchronous = true;
        effect.iterations = 20;
        effect.duration = duration;
        effect.targetPlayer = player;
        //effect.targetOffset = new Vector(0, 2, 0);
        effect.start();
        plugin.getServer().getConsoleSender().sendMessage("started");
    }
}
