// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.data;

import java.io.InputStream;
import java.io.IOException;
import think.rpgitems.Plugin;

public class Font
{
    public static int[] widths;
    
    public static void load() {
        Font.widths = new int[65535];
        try {
            final InputStream in = Plugin.plugin.getResource("font.bin");
            for (int i = 0; i < Font.widths.length; ++i) {
                Font.widths[i] = in.read();
            }
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
