// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3;

import org.bukkit.ChatColor;
import java.util.ArrayList;
import java.util.List;

public class TextWrapper
{
    public static List<String> wrapText(final String text) {
        final ArrayList<String> output = new ArrayList<String>();
        final String[] lines = text.split("\n");
        String lastColor = null;
        for (String line : lines) {
            if (lastColor != null) {
                line = lastColor + line;
            }
            output.add(line);
            lastColor = ChatColor.getLastColors(line);
        }
        return output;
    }
}
