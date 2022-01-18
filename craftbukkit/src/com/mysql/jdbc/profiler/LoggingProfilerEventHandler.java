// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.profiler;

import java.sql.SQLException;
import java.util.Properties;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.log.Log;

public class LoggingProfilerEventHandler implements ProfilerEventHandler
{
    private Log log;
    
    public void consumeEvent(final ProfilerEvent evt) {
        if (evt.eventType == 0) {
            this.log.logWarn(evt);
        }
        else {
            this.log.logInfo(evt);
        }
    }
    
    public void destroy() {
        this.log = null;
    }
    
    public void init(final Connection conn, final Properties props) throws SQLException {
        this.log = conn.getLog();
    }
}
