// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.awt.event.FocusListener;
import java.util.logging.Handler;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JPanel;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowListener;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.ActionListener;

class ServerGuiCommandListener implements ActionListener
{
    final /* synthetic */ JTextField a;
    final /* synthetic */ ServerGUI b;
    
    ServerGuiCommandListener(final ServerGUI b, final JTextField a) {
        this.b = b;
        this.a = a;
    }
    
    public void actionPerformed(final ActionEvent actionEvent) {
        final String trim = this.a.getText().trim();
        if (trim.length() > 0) {
            this.b.b.issueCommand(trim, MinecraftServer.getServer());
        }
        this.a.setText("");
    }
}
