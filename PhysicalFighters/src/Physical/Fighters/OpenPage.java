// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URI;
import java.awt.Desktop;

public class OpenPage
{
    public static void main(final String string) {
        if (Desktop.isDesktopSupported()) {
            final Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URI(string));
                }
                catch (URISyntaxException ex) {
                    ex.printStackTrace();
                }
                catch (IOException ex2) {
                    ex2.printStackTrace();
                }
            }
        }
    }
}
