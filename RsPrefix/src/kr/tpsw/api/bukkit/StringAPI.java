// 
// Decompiled by Procyon v0.5.30
// 

package kr.tpsw.api.bukkit;

import java.io.File;

public class StringAPI
{
    public static String mergeArgs(final String[] args, final int start, final int end, final char c) {
        final StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; ++i) {
            sb.append(args[i]).append(c);
        }
        return sb.toString().trim();
    }
    
    public static String mergeArgs(final String[] args, final int start) {
        return mergeArgs(args, start, args.length, ' ');
    }
    
    public static String mergeArgsUnder(final String[] args, final int start) {
        return mergeArgs(args, start, args.length, '_');
    }
    
    public static boolean contains(final String s, final char c) {
        return s.indexOf(c) > -1;
    }
    
    public static int getCharCount(final String s, final char c) {
        int count = 0;
        char[] charArray;
        for (int length = (charArray = s.toCharArray()).length, i = 0; i < length; ++i) {
            final char cc = charArray[i];
            if (cc == c) {
                ++count;
            }
        }
        return count;
    }
    
    public static String getFileNameWithoutExtension(final File file) {
        if (!file.isDirectory()) {
            final String name = file.getName();
            final int index = name.lastIndexOf(46) + 1;
            return name.substring(0, index);
        }
        System.out.println(file.getAbsolutePath());
        return null;
    }
}
