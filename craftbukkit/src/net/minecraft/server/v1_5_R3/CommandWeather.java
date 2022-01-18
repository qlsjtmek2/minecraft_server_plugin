// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class CommandWeather extends CommandAbstract
{
    public String c() {
        return "weather";
    }
    
    public int a() {
        return 2;
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length < 1) {
            throw new ExceptionUsage("commands.weather.usage", new Object[0]);
        }
        int n = (300 + new Random().nextInt(600)) * 20;
        if (array.length >= 2) {
            n = CommandAbstract.a(commandListener, array[1], 1, 1000000) * 20;
        }
        final WorldData worldData = MinecraftServer.getServer().worldServer[0].getWorldData();
        worldData.setWeatherDuration(n);
        worldData.setThunderDuration(n);
        if ("clear".equalsIgnoreCase(array[0])) {
            worldData.setStorm(false);
            worldData.setThundering(false);
            CommandAbstract.a(commandListener, "commands.weather.clear", new Object[0]);
        }
        else if ("rain".equalsIgnoreCase(array[0])) {
            worldData.setStorm(true);
            worldData.setThundering(false);
            CommandAbstract.a(commandListener, "commands.weather.rain", new Object[0]);
        }
        else if ("thunder".equalsIgnoreCase(array[0])) {
            worldData.setStorm(true);
            worldData.setThundering(true);
            CommandAbstract.a(commandListener, "commands.weather.thunder", new Object[0]);
        }
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length == 1) {
            return CommandAbstract.a(array, "clear", "rain", "thunder");
        }
        return null;
    }
}
