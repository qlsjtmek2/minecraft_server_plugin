// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
enum BstSide
{
    LEFT {
        public BstSide other() {
            return BstSide$1.RIGHT;
        }
    }, 
    RIGHT {
        public BstSide other() {
            return BstSide$2.LEFT;
        }
    };
    
    abstract BstSide other();
}
