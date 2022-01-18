// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems;

import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.XMLEventReader;
import java.io.InputStream;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLInputFactory;
import java.net.URLConnection;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.net.MalformedURLException;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.net.URL;
import org.bukkit.plugin.Plugin;

public class Updater
{
    private Plugin plugin;
    private UpdateType type;
    private String versionTitle;
    private String versionLink;
    private long totalSize;
    private int sizeLine;
    private int multiplier;
    private boolean announce;
    private URL url;
    private File file;
    private Thread thread;
    private static final String DBOUrl = "http://dev.bukkit.org/server-mods/";
    private String[] noUpdateTag;
    private static final int BYTE_SIZE = 1024;
    private String updateFolder;
    private UpdateResult result;
    private static final String TITLE = "title";
    private static final String LINK = "link";
    private static final String ITEM = "item";
    
    public Updater(final Plugin plugin, final String slug, final File file, final UpdateType type, final boolean announce) {
        this.noUpdateTag = new String[] { "-DEV", "-PRE", "-SNAPSHOT" };
        this.updateFolder = YamlConfiguration.loadConfiguration(new File("bukkit.yml")).getString("settings.update-folder");
        this.result = UpdateResult.SUCCESS;
        this.plugin = plugin;
        this.type = type;
        this.announce = announce;
        this.file = file;
        try {
            this.url = new URL("http://dev.bukkit.org/server-mods/" + slug + "/files.rss");
        }
        catch (MalformedURLException ex) {
            plugin.getLogger().warning("The author of this plugin (" + plugin.getDescription().getAuthors().get(0) + ") has misconfigured their Auto Update system");
            plugin.getLogger().warning("The project slug given ('" + slug + "') is invalid. Please nag the author about this.");
            this.result = UpdateResult.FAIL_BADSLUG;
        }
        new UpdateRunnable((UpdateRunnable)null).runTaskTimerAsynchronously(plugin, 20L, 216000L);
    }
    
    public UpdateResult getResult() {
        this.waitForThread();
        return this.result;
    }
    
    public long getFileSize() {
        this.waitForThread();
        return this.totalSize;
    }
    
    public String getLatestVersionString() {
        this.waitForThread();
        return this.versionTitle;
    }
    
    public void waitForThread() {
        if (this.thread.isAlive()) {
            try {
                this.thread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void saveFile(final File folder, final String file, final String u) {
        if (!folder.exists()) {
            folder.mkdir();
        }
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            final URL url = new URL(u);
            final int fileLength = url.openConnection().getContentLength();
            in = new BufferedInputStream(url.openStream());
            fout = new FileOutputStream(String.valueOf(folder.getAbsolutePath()) + "/" + file);
            final byte[] data = new byte[1024];
            if (this.announce) {
                this.plugin.getLogger().info("About to download a new update: " + this.versionTitle);
            }
            long downloaded = 0L;
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                downloaded += count;
                fout.write(data, 0, count);
                final int percent = (int)(downloaded * 100L / fileLength);
                if (this.announce & percent % 10 == 0) {
                    this.plugin.getLogger().info("Downloading update: " + percent + "% of " + fileLength + " bytes.");
                }
            }
            File[] listFiles;
            for (int length = (listFiles = new File("plugins/" + this.updateFolder).listFiles()).length, i = 0; i < length; ++i) {
                final File xFile = listFiles[i];
                if (xFile.getName().endsWith(".zip")) {
                    xFile.delete();
                }
            }
            final File dFile = new File(String.valueOf(folder.getAbsolutePath()) + "/" + file);
            if (dFile.getName().endsWith(".zip")) {
                this.unzip(dFile.getCanonicalPath());
            }
            if (this.announce) {
                this.plugin.getLogger().info("Finished updating.");
            }
        }
        catch (Exception ex) {
            this.plugin.getLogger().warning("The auto-updater tried to download a new update, but was unsuccessful.");
            this.result = UpdateResult.FAIL_DOWNLOAD;
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fout != null) {
                    fout.close();
                }
            }
            catch (Exception ex2) {}
        }
        try {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
        catch (Exception ex3) {}
    }
    
    private void unzip(final String file) {
        try {
            final File fSourceZip = new File(file);
            final String zipPath = file.substring(0, file.length() - 4);
            ZipFile zipFile = new ZipFile(fSourceZip);
            Enumeration<? extends ZipEntry> e = zipFile.entries();
            while (e.hasMoreElements()) {
                ZipEntry entry = (ZipEntry)e.nextElement();
                File destinationFilePath = new File(zipPath, entry.getName());
                destinationFilePath.getParentFile().mkdirs();
                if (entry.isDirectory()) {
                    continue;
                }
                final BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                final byte[] buffer = new byte[1024];
                final FileOutputStream fos = new FileOutputStream(destinationFilePath);
                final BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
                int b;
                while ((b = bis.read(buffer, 0, 1024)) != -1) {
                    bos.write(buffer, 0, b);
                }
                bos.flush();
                bos.close();
                bis.close();
                final String name = destinationFilePath.getName();
                if (name.endsWith(".jar") && this.pluginFile(name)) {
                    destinationFilePath.renameTo(new File("plugins/" + this.updateFolder + "/" + name));
                }
                entry = null;
                destinationFilePath = null;
            }
            e = null;
            zipFile.close();
            zipFile = null;
            File[] listFiles;
            for (int length = (listFiles = new File(zipPath).listFiles()).length, i = 0; i < length; ++i) {
                final File dFile = listFiles[i];
                if (dFile.isDirectory() && this.pluginFile(dFile.getName())) {
                    final File oFile = new File("plugins/" + dFile.getName());
                    final File[] contents = oFile.listFiles();
                    File[] listFiles2;
                    for (int length2 = (listFiles2 = dFile.listFiles()).length, j = 0; j < length2; ++j) {
                        final File cFile = listFiles2[j];
                        boolean found = false;
                        File[] array;
                        for (int length3 = (array = contents).length, k = 0; k < length3; ++k) {
                            final File xFile = array[k];
                            if (xFile.getName().equals(cFile.getName())) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            cFile.renameTo(new File(oFile.getCanonicalFile() + "/" + cFile.getName()));
                        }
                        else {
                            cFile.delete();
                        }
                    }
                }
                dFile.delete();
            }
            new File(zipPath).delete();
            fSourceZip.delete();
        }
        catch (IOException ex) {
            ex.printStackTrace();
            this.plugin.getLogger().warning("The auto-updater tried to unzip a new update file, but was unsuccessful.");
            this.result = UpdateResult.FAIL_DOWNLOAD;
        }
        new File(file).delete();
    }
    
    public boolean pluginFile(final String name) {
        File[] listFiles;
        for (int length = (listFiles = new File("plugins").listFiles()).length, i = 0; i < length; ++i) {
            final File file = listFiles[i];
            if (file.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    private String getFile(final String link) {
        String download = null;
        try {
            final URL url = new URL(link);
            URLConnection urlConn = url.openConnection();
            InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
            BufferedReader buff = new BufferedReader(inStream);
            int counter = 0;
            String line;
            while ((line = buff.readLine()) != null) {
                ++counter;
                if (line.contains("<li class=\"user-action user-action-download\">")) {
                    download = line.split("<a href=\"")[1].split("\">Download</a>")[0];
                }
                else if (line.contains("<dt>Size</dt>")) {
                    this.sizeLine = counter + 1;
                }
                else {
                    if (counter != this.sizeLine) {
                        continue;
                    }
                    String size = line.replaceAll("<dd>", "").replaceAll("</dd>", "");
                    this.multiplier = (size.contains("MiB") ? 1048576 : 1024);
                    size = size.replace(" KiB", "").replace(" MiB", "");
                    this.totalSize = (long)(Double.parseDouble(size) * this.multiplier);
                }
            }
            urlConn = null;
            inStream = null;
            buff.close();
            buff = null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.plugin.getLogger().warning("The auto-updater tried to contact dev.bukkit.org, but was unsuccessful.");
            this.result = UpdateResult.FAIL_DBO;
            return null;
        }
        return download;
    }
    
    private boolean versionCheck(final String title) {
        if (this.type != UpdateType.NO_VERSION_CHECK) {
            final String version = this.plugin.getDescription().getVersion();
            if (title.split(" v").length != 2) {
                this.plugin.getLogger().warning("The author of this plugin (" + this.plugin.getDescription().getAuthors().get(0) + ") has misconfigured their Auto Update system");
                this.plugin.getLogger().warning("Files uploaded to BukkitDev should contain the version number, seperated from the name by a 'v', such as PluginName v1.0");
                this.plugin.getLogger().warning("Please notify the author of this error.");
                this.result = UpdateResult.FAIL_NOVERSION;
                return false;
            }
            final String remoteVersion = title.split(" v")[1].split(" ")[0];
            int remVer = -1;
            int curVer = 0;
            try {
                remVer = this.calVer(remoteVersion);
                curVer = this.calVer(version);
            }
            catch (NumberFormatException nfe) {
                remVer = -1;
            }
            if (this.hasTag(version) || version.equalsIgnoreCase(remoteVersion) || curVer >= remVer) {
                this.result = UpdateResult.NO_UPDATE;
                return false;
            }
        }
        return true;
    }
    
    private Integer calVer(final String s) throws NumberFormatException {
        if (s.contains(".")) {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); ++i) {
                final Character c = s.charAt(i);
                if (Character.isLetterOrDigit(c)) {
                    sb.append(c);
                }
            }
            return Integer.parseInt(sb.toString());
        }
        return Integer.parseInt(s);
    }
    
    private boolean hasTag(final String version) {
        String[] noUpdateTag;
        for (int length = (noUpdateTag = this.noUpdateTag).length, i = 0; i < length; ++i) {
            final String string = noUpdateTag[i];
            if (version.contains(string)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean readFeed() {
        try {
            String title = "";
            String link = "";
            final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            final InputStream in = this.read();
            if (in != null) {
                final XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
                while (eventReader.hasNext()) {
                    XMLEvent event = eventReader.nextEvent();
                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart().equals("title")) {
                            event = eventReader.nextEvent();
                            title = event.asCharacters().getData();
                        }
                        else {
                            if (!event.asStartElement().getName().getLocalPart().equals("link")) {
                                continue;
                            }
                            event = eventReader.nextEvent();
                            link = event.asCharacters().getData();
                        }
                    }
                    else {
                        if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("item")) {
                            this.versionTitle = title;
                            this.versionLink = link;
                            break;
                        }
                        continue;
                    }
                }
                return true;
            }
            return false;
        }
        catch (XMLStreamException e) {
            this.plugin.getLogger().warning("Could not reach dev.bukkit.org for update checking. Is it offline?");
            return false;
        }
    }
    
    private InputStream read() {
        try {
            return this.url.openStream();
        }
        catch (IOException e) {
            this.plugin.getLogger().warning("Could not reach BukkitDev file stream for update checking. Is dev.bukkit.org offline?");
            return null;
        }
    }
    
    static /* synthetic */ void access$11(final Updater updater, final UpdateResult result) {
        updater.result = result;
    }
    
    public enum UpdateResult
    {
        SUCCESS("SUCCESS", 0), 
        NO_UPDATE("NO_UPDATE", 1), 
        FAIL_DOWNLOAD("FAIL_DOWNLOAD", 2), 
        FAIL_DBO("FAIL_DBO", 3), 
        FAIL_NOVERSION("FAIL_NOVERSION", 4), 
        FAIL_BADSLUG("FAIL_BADSLUG", 5), 
        UPDATE_AVAILABLE("UPDATE_AVAILABLE", 6);
        
        private UpdateResult(final String s, final int n) {
        }
    }
    
    public enum UpdateType
    {
        DEFAULT("DEFAULT", 0), 
        NO_VERSION_CHECK("NO_VERSION_CHECK", 1), 
        NO_DOWNLOAD("NO_DOWNLOAD", 2);
        
        private UpdateType(final String s, final int n) {
        }
    }
    
    private class UpdateRunnable extends BukkitRunnable
    {
        public void run() {
            if (Updater.this.url != null && Updater.this.readFeed() && Updater.this.versionCheck(Updater.this.versionTitle)) {
                final String fileLink = Updater.this.getFile(Updater.this.versionLink);
                if (fileLink != null && Updater.this.type != UpdateType.NO_DOWNLOAD) {
                    String name = Updater.this.file.getName();
                    if (fileLink.endsWith(".zip")) {
                        final String[] split = fileLink.split("/");
                        name = split[split.length - 1];
                    }
                    Updater.this.saveFile(new File("plugins/" + Updater.this.updateFolder), name, fileLink);
                    final BukkitRunnable reloader = new BukkitRunnable() {
                        public void run() {
                            Player[] onlinePlayers;
                            for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, i = 0; i < length; ++i) {
                                final Player player = onlinePlayers[i];
                                if (player.isOp()) {
                                    player.sendMessage(ChatColor.AQUA + "RPG Items update downloaded. Please reload to complete the update.");
                                }
                            }
                        }
                    };
                    reloader.runTask(Updater.this.plugin);
                    this.cancel();
                }
                else {
                    Updater.access$11(Updater.this, UpdateResult.UPDATE_AVAILABLE);
                }
            }
        }
    }
}
