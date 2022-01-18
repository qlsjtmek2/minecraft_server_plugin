// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.awt.event.FocusListener;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
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

public class ServerGUI extends JComponent
{
    private static boolean a;
    private DedicatedServer b;
    
    public static void a(final DedicatedServer dedicatedServer) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex) {}
        final ServerGUI serverGUI = new ServerGUI(dedicatedServer);
        ServerGUI.a = true;
        final JFrame frame = new JFrame("Minecraft server");
        frame.add(serverGUI);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new ServerWindowAdapter(dedicatedServer));
    }
    
    public ServerGUI(final DedicatedServer b) {
        this.b = b;
        this.setPreferredSize(new Dimension(854, 480));
        this.setLayout(new BorderLayout());
        try {
            this.add(this.d(), "Center");
            this.add(this.b(), "West");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private JComponent b() {
        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(new GuiStatsComponent(this.b), "North");
        panel.add(this.c(), "Center");
        panel.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
        return panel;
    }
    
    private JComponent c() {
        final JScrollPane scrollPane = new JScrollPane(new PlayerListBox(this.b), 22, 30);
        scrollPane.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
        return scrollPane;
    }
    
    private JComponent d() {
        final JPanel panel = new JPanel(new BorderLayout());
        final JTextArea textArea = new JTextArea();
        this.b.getLogger().getLogger().addHandler(new GuiLogOutputHandler(textArea));
        final JScrollPane scrollPane = new JScrollPane(textArea, 22, 30);
        textArea.setEditable(false);
        final JTextField textField = new JTextField();
        textField.addActionListener(new ServerGuiCommandListener(this, textField));
        textArea.addFocusListener(new ServerGuiFocusAdapter(this));
        panel.add(scrollPane, "Center");
        panel.add(textField, "South");
        panel.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
        return panel;
    }
    
    static {
        ServerGUI.a = false;
    }
}
