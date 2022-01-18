// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline.console.completer;

import org.bukkit.craftbukkit.libs.jline.internal.Configuration;
import java.io.File;
import java.util.List;

public class FileNameCompleter implements Completer
{
    private static final boolean OS_IS_WINDOWS;
    
    public int complete(String buffer, final int cursor, final List<CharSequence> candidates) {
        assert candidates != null;
        if (buffer == null) {
            buffer = "";
        }
        if (FileNameCompleter.OS_IS_WINDOWS) {
            buffer = buffer.replace('/', '\\');
        }
        String translated = buffer;
        final File homeDir = this.getUserHome();
        if (translated.startsWith("~" + this.separator())) {
            translated = homeDir.getPath() + translated.substring(1);
        }
        else if (translated.startsWith("~")) {
            translated = homeDir.getParentFile().getAbsolutePath();
        }
        else if (!translated.startsWith(this.separator())) {
            final String cwd = this.getUserDir().getAbsolutePath();
            translated = cwd + this.separator() + translated;
        }
        final File file = new File(translated);
        File dir;
        if (translated.endsWith(this.separator())) {
            dir = file;
        }
        else {
            dir = file.getParentFile();
        }
        final File[] entries = (dir == null) ? new File[0] : dir.listFiles();
        return this.matchFiles(buffer, translated, entries, candidates);
    }
    
    protected String separator() {
        return File.separator;
    }
    
    protected File getUserHome() {
        return Configuration.getUserHome();
    }
    
    protected File getUserDir() {
        return new File(".");
    }
    
    protected int matchFiles(final String buffer, final String translated, final File[] files, final List<CharSequence> candidates) {
        if (files == null) {
            return -1;
        }
        int matches = 0;
        for (final File file : files) {
            if (file.getAbsolutePath().startsWith(translated)) {
                ++matches;
            }
        }
        for (final File file : files) {
            if (file.getAbsolutePath().startsWith(translated)) {
                final CharSequence name = file.getName() + ((matches == 1 && file.isDirectory()) ? this.separator() : " ");
                candidates.add(this.render(file, name).toString());
            }
        }
        final int index = buffer.lastIndexOf(this.separator());
        return index + this.separator().length();
    }
    
    protected CharSequence render(final File file, final CharSequence name) {
        assert file != null;
        assert name != null;
        return name;
    }
    
    static {
        final String os = Configuration.getOsName();
        OS_IS_WINDOWS = os.contains("windows");
    }
}
