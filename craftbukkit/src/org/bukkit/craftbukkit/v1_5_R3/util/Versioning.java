// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.util;

import java.io.InputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;
import org.bukkit.Bukkit;

public final class Versioning
{
    public static String getBukkitVersion() {
        String result = "Unknown-Version";
        final InputStream stream = Bukkit.class.getClassLoader().getResourceAsStream("META-INF/maven/org.spigotmc/spigot-api/pom.properties");
        final Properties properties = new Properties();
        if (stream != null) {
            try {
                properties.load(stream);
                result = properties.getProperty("version");
            }
            catch (IOException ex) {
                Logger.getLogger(Versioning.class.getName()).log(Level.SEVERE, "Could not get Bukkit version!", ex);
            }
        }
        return result;
    }
}
