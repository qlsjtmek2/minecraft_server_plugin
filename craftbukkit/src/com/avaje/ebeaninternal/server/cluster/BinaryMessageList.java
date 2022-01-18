// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster;

import java.util.List;
import java.util.ArrayList;

public class BinaryMessageList
{
    ArrayList<BinaryMessage> list;
    
    public BinaryMessageList() {
        this.list = new ArrayList<BinaryMessage>();
    }
    
    public void add(final BinaryMessage msg) {
        this.list.add(msg);
    }
    
    public List<BinaryMessage> getList() {
        return this.list;
    }
}
