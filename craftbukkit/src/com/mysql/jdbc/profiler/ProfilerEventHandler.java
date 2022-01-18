// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.profiler;

import com.mysql.jdbc.Extension;

public interface ProfilerEventHandler extends Extension
{
    void consumeEvent(final ProfilerEvent p0);
}
