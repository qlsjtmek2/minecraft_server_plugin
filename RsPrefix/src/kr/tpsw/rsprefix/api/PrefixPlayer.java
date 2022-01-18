// 
// Decompiled by Procyon v0.5.30
// 

package kr.tpsw.rsprefix.api;

import kr.tpsw.api.bukkit.DataAPI;
import java.util.LinkedList;
import java.util.ArrayList;
import org.bukkit.inventory.Inventory;
import java.util.List;

public class PrefixPlayer
{
    private String name;
    private List<String> list;
    private String mainPrefix;
    private List<Inventory> invList;
    public boolean needUpdateInv;
    
    public PrefixPlayer(final String name) {
        this(name, new ArrayList<String>(), null);
    }
    
    public PrefixPlayer(final String name, final List<String> list, final String mainPrefix) {
        this.invList = new LinkedList<Inventory>();
        this.needUpdateInv = false;
        this.name = name;
        this.list = list;
        this.mainPrefix = mainPrefix;
    }
    
    public List<Inventory> getInvList() {
        return this.invList;
    }
    
    public void updateInvList() {
        this.invList.clear();
        for (int i = this.list.size() / 45 + 1, j = 1; j <= i; ++j) {
            this.invList.add(InvAPI.createInv(this, j));
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public List<String> getList() {
        return this.list;
    }
    
    public void setList(final List<String> list) {
        this.list = list;
    }
    
    public String getMainPrefix() {
        if (this.mainPrefix == null) {
            return "";
        }
        return this.mainPrefix;
    }
    
    public void setMainPrefix(final int index) {
        if (DataAPI.isListhasIndex(this.list, index)) {
            this.mainPrefix = this.list.get(index);
        }
        else {
            this.mainPrefix = null;
        }
    }
}
