// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import org.bukkit.plugin.Plugin;
import org.bukkit.command.CommandSender;
import java.util.Random;
import org.bukkit.generator.ChunkGenerator;

public class WorldCreator
{
    private final String name;
    private long seed;
    private World.Environment environment;
    private ChunkGenerator generator;
    private WorldType type;
    private boolean generateStructures;
    
    public WorldCreator(final String name) {
        this.environment = World.Environment.NORMAL;
        this.generator = null;
        this.type = WorldType.NORMAL;
        this.generateStructures = true;
        if (name == null) {
            throw new IllegalArgumentException("World name cannot be null");
        }
        this.name = name;
        this.seed = new Random().nextLong();
    }
    
    public WorldCreator copy(final World world) {
        if (world == null) {
            throw new IllegalArgumentException("World cannot be null");
        }
        this.seed = world.getSeed();
        this.environment = world.getEnvironment();
        this.generator = world.getGenerator();
        return this;
    }
    
    public WorldCreator copy(final WorldCreator creator) {
        if (creator == null) {
            throw new IllegalArgumentException("Creator cannot be null");
        }
        this.seed = creator.seed();
        this.environment = creator.environment();
        this.generator = creator.generator();
        return this;
    }
    
    public String name() {
        return this.name;
    }
    
    public long seed() {
        return this.seed;
    }
    
    public WorldCreator seed(final long seed) {
        this.seed = seed;
        return this;
    }
    
    public World.Environment environment() {
        return this.environment;
    }
    
    public WorldCreator environment(final World.Environment env) {
        this.environment = env;
        return this;
    }
    
    public WorldType type() {
        return this.type;
    }
    
    public WorldCreator type(final WorldType type) {
        this.type = type;
        return this;
    }
    
    public ChunkGenerator generator() {
        return this.generator;
    }
    
    public WorldCreator generator(final ChunkGenerator generator) {
        this.generator = generator;
        return this;
    }
    
    public WorldCreator generator(final String generator) {
        this.generator = getGeneratorForName(this.name, generator, Bukkit.getConsoleSender());
        return this;
    }
    
    public WorldCreator generator(final String generator, final CommandSender output) {
        this.generator = getGeneratorForName(this.name, generator, output);
        return this;
    }
    
    public WorldCreator generateStructures(final boolean generate) {
        this.generateStructures = generate;
        return this;
    }
    
    public boolean generateStructures() {
        return this.generateStructures;
    }
    
    public World createWorld() {
        return Bukkit.createWorld(this);
    }
    
    public static WorldCreator name(final String name) {
        return new WorldCreator(name);
    }
    
    public static ChunkGenerator getGeneratorForName(final String world, final String name, CommandSender output) {
        ChunkGenerator result = null;
        if (world == null) {
            throw new IllegalArgumentException("World name must be specified");
        }
        if (output == null) {
            output = Bukkit.getConsoleSender();
        }
        if (name != null) {
            final String[] split = name.split(":", 2);
            final String id = (split.length > 1) ? split[1] : null;
            final Plugin plugin = Bukkit.getPluginManager().getPlugin(split[0]);
            if (plugin == null) {
                output.sendMessage("Could not set generator for world '" + world + "': Plugin '" + split[0] + "' does not exist");
            }
            else if (!plugin.isEnabled()) {
                output.sendMessage("Could not set generator for world '" + world + "': Plugin '" + plugin.getDescription().getFullName() + "' is not enabled");
            }
            else {
                result = plugin.getDefaultWorldGenerator(world, id);
            }
        }
        return result;
    }
}
