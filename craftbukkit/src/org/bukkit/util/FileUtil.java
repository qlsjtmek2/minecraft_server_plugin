// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.util;

import java.nio.channels.FileChannel;
import java.io.IOException;
import java.nio.channels.WritableByteChannel;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.File;

public class FileUtil
{
    public static boolean copy(final File inFile, final File outFile) {
        if (!inFile.exists()) {
            return false;
        }
        FileChannel in = null;
        FileChannel out = null;
        try {
            in = new FileInputStream(inFile).getChannel();
            out = new FileOutputStream(outFile).getChannel();
            for (long pos = 0L, size = in.size(); pos < size; pos += in.transferTo(pos, 10485760L, out)) {}
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException ioe) {
                return false;
            }
        }
        catch (IOException ioe) {
            return false;
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException ioe2) {
                return false;
            }
        }
        return true;
    }
}
