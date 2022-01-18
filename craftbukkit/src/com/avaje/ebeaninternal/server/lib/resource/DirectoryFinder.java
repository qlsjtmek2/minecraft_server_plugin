// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.resource;

import java.util.logging.Level;
import java.io.File;
import java.util.logging.Logger;

public class DirectoryFinder
{
    private static final Logger logger;
    
    public static File find(final File startDir, String match, final int maxDepth) {
        String matchSub = null;
        final int slashPos = match.indexOf(47);
        if (slashPos > -1) {
            matchSub = match.substring(slashPos + 1);
            match = match.substring(0, slashPos);
        }
        final File found = find(startDir, match, matchSub, 0, maxDepth);
        if (found != null && matchSub != null) {
            return new File(found, matchSub);
        }
        return found;
    }
    
    private static File find(File dir, final String match, final String matchSub, final int depth, final int maxDepth) {
        if (dir == null) {
            final String curDir = System.getProperty("user.dir");
            dir = new File(curDir);
        }
        if (dir.exists()) {
            final File[] list = dir.listFiles();
            if (list != null) {
                for (int i = 0; i < list.length; ++i) {
                    if (isMatch(list[i], match, matchSub)) {
                        return list[i];
                    }
                }
                if (depth < maxDepth) {
                    for (int i = 0; i < list.length; ++i) {
                        if (list[i].isDirectory()) {
                            final File found = find(list[i], match, matchSub, depth + 1, maxDepth);
                            if (found != null) {
                                return found;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    
    private static boolean isMatch(final File f, final String match, final String matchSub) {
        if (f == null) {
            return false;
        }
        if (!f.isDirectory()) {
            return false;
        }
        if (!f.getName().equalsIgnoreCase(match)) {
            return false;
        }
        if (matchSub == null) {
            return true;
        }
        final File sub = new File(f, matchSub);
        if (DirectoryFinder.logger.isLoggable(Level.FINEST)) {
            DirectoryFinder.logger.finest("search; " + f.getPath());
        }
        return sub.exists();
    }
    
    static {
        logger = Logger.getLogger(DirectoryFinder.class.getName());
    }
}
