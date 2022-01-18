// 
// Decompiled by Procyon v0.5.30
// 

package com.survivorserver.GlobalMarket;

import org.bukkit.inventory.Inventory;
import java.util.Map;

public class InterfaceViewer
{
    String player;
    Map<Integer, Integer> boundSlots;
    int currentPage;
    Inventory gui;
    InterfaceAction lastAction;
    int lastActionSlot;
    ViewType viewType;
    String search;
    
    public InterfaceViewer(final String player, final Inventory gui) {
        this.currentPage = 1;
        this.lastActionSlot = 0;
        this.player = player;
        this.gui = gui;
    }
    
    public void setBoundSlots(final Map<Integer, Integer> boundSlots) {
        this.boundSlots = boundSlots;
    }
    
    public Map<Integer, Integer> getBoundSlots() {
        return this.boundSlots;
    }
    
    public void setPage(final int page) {
        this.currentPage = page;
    }
    
    public int getPage() {
        return this.currentPage;
    }
    
    public String getViewer() {
        return this.player;
    }
    
    public Inventory getGui() {
        return this.gui;
    }
    
    public InterfaceAction getLastAction() {
        return this.lastAction;
    }
    
    public void setLastAction(final InterfaceAction action) {
        this.lastAction = action;
    }
    
    public int getLastActionSlot() {
        return this.lastActionSlot;
    }
    
    public void setLastActionSlot(final int slot) {
        this.lastActionSlot = slot;
    }
    
    public void setViewType(final ViewType viewType) {
        this.viewType = viewType;
    }
    
    public ViewType getViewType() {
        return this.viewType;
    }
    
    public void setSearch(final String search) {
        this.search = search;
    }
    
    public String getSearch() {
        return this.search;
    }
    
    public enum InterfaceAction
    {
        LEFTCLICK("LEFTCLICK", 0, "leftclick"), 
        RIGHTCLICK("RIGHTCLICK", 1, "rightclick"), 
        MIDDLECLICK("MIDDLECLICK", 2, "middleclick"), 
        SHIFTCLICK("SHIFTCLICK", 3, "shiftclick");
        
        private String value;
        
        private InterfaceAction(final String s, final int n, final String value) {
            this.value = value;
        }
        
        public String getValue() {
            return this.value;
        }
    }
    
    public enum ViewType
    {
        LISTINGS("LISTINGS", 0, "listings"), 
        MAIL("MAIL", 1, "mail"), 
        REQUESTS("REQUESTS", 2, "requests");
        
        private String value;
        
        private ViewType(final String s, final int n, final String value) {
            this.value = value;
        }
        
        public String getValue() {
            return this.value;
        }
    }
}
