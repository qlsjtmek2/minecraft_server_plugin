// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.text.json;

import com.avaje.ebeaninternal.server.util.ArrayStack;

public class PathStack extends ArrayStack<String>
{
    public String peekFullPath(final String key) {
        final String prefix = this.peekWithNull();
        if (prefix != null) {
            return prefix + "." + key;
        }
        return key;
    }
    
    public void pushPathKey(String key) {
        final String prefix = this.peekWithNull();
        if (prefix != null) {
            key = prefix + "." + key;
        }
        this.push(key);
    }
}
