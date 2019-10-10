package com.gazelle.discovertigo.entities;

import com.gazelle.discovertigo.Vertigo;
import de.slikey.effectlib.effect.FountainEffect;
import org.bukkit.Location;

public class VertigoFountain {

    private Location fountain_block;

    private Vertigo plugin;

    public VertigoFountain(Vertigo plugin, Location fountain_block_location){
        fountain_block = fountain_block_location;
        this.plugin = plugin;
    }

    public void drawFountain(float height, int duration){
        FountainEffect effect = new FountainEffect(plugin.getEffectManager());
        effect.setLocation(fountain_block);

        effect.height = height;
        effect.iterations = duration * 20;
        effect.start();
    }
}
