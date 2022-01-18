// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.updater;

import java.text.ParseException;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonParseException;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonDeserializationContext;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
import java.text.SimpleDateFormat;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonDeserializer;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import java.net.URLConnection;
import java.io.Reader;
import org.bukkit.craftbukkit.libs.com.google.gson.FieldNamingPolicy;
import java.lang.reflect.Type;
import java.util.Date;
import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BukkitDLUpdaterService
{
    private static final String API_PREFIX_ARTIFACT = "/api/1.0/downloads/projects/craftbukkit/view/";
    private static final String API_PREFIX_CHANNEL = "/api/1.0/downloads/channels/";
    private static final DateDeserializer dateDeserializer;
    private final String host;
    
    public BukkitDLUpdaterService(final String host) {
        this.host = host;
    }
    
    public ArtifactDetails getArtifact(final String slug, final String name) {
        try {
            return this.fetchArtifact(slug);
        }
        catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BukkitDLUpdaterService.class.getName()).log(Level.WARNING, "Could not get " + name + ": " + ex.getClass().getSimpleName());
        }
        catch (IOException ex2) {
            Logger.getLogger(BukkitDLUpdaterService.class.getName()).log(Level.WARNING, "Could not get " + name + ": " + ex2.getClass().getSimpleName());
        }
        return null;
    }
    
    private String getUserAgent() {
        return "CraftBukkit/" + BukkitDLUpdaterService.class.getPackage().getImplementationVersion() + "/" + System.getProperty("java.version");
    }
    
    public ArtifactDetails fetchArtifact(final String slug) throws IOException {
        final URL url = new URL("http", this.host, "/api/1.0/downloads/projects/craftbukkit/view/" + slug + "/");
        InputStreamReader reader = null;
        try {
            final URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", this.getUserAgent());
            reader = new InputStreamReader(connection.getInputStream());
            final Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, BukkitDLUpdaterService.dateDeserializer).setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            return gson.fromJson(reader, ArtifactDetails.class);
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
    
    public ArtifactDetails.ChannelDetails getChannel(final String slug, final String name) {
        try {
            return this.fetchChannel(slug);
        }
        catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BukkitDLUpdaterService.class.getName()).log(Level.WARNING, "Could not get " + name + ": " + ex.getClass().getSimpleName());
        }
        catch (IOException ex2) {
            Logger.getLogger(BukkitDLUpdaterService.class.getName()).log(Level.WARNING, "Could not get " + name + ": " + ex2.getClass().getSimpleName());
        }
        return null;
    }
    
    public ArtifactDetails.ChannelDetails fetchChannel(final String slug) throws IOException {
        final URL url = new URL("http", this.host, "/api/1.0/downloads/channels/" + slug + "/");
        InputStreamReader reader = null;
        try {
            final URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", this.getUserAgent());
            reader = new InputStreamReader(connection.getInputStream());
            final Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, BukkitDLUpdaterService.dateDeserializer).setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            return gson.fromJson(reader, ArtifactDetails.ChannelDetails.class);
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
    
    static {
        dateDeserializer = new DateDeserializer();
    }
    
    static class DateDeserializer implements JsonDeserializer<Date>
    {
        private static final SimpleDateFormat format;
        
        public Date deserialize(final JsonElement je, final Type type, final JsonDeserializationContext jdc) throws JsonParseException {
            try {
                return DateDeserializer.format.parse(je.getAsString());
            }
            catch (ParseException ex) {
                throw new JsonParseException("Date is not formatted correctly", ex);
            }
        }
        
        static {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    }
}
