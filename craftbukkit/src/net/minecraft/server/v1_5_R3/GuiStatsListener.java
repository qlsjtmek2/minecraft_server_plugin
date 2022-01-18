// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.awt.Graphics;
import java.awt.Color;
import javax.swing.Timer;
import java.awt.Dimension;
import java.text.DecimalFormat;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GuiStatsListener implements ActionListener
{
    final /* synthetic */ GuiStatsComponent a;
    
    GuiStatsListener(final GuiStatsComponent a) {
        this.a = a;
    }
    
    public void actionPerformed(final ActionEvent actionEvent) {
        this.a.a();
    }
}
