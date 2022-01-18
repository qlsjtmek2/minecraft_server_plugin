// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.Properties;
import java.io.IOException;
import java.net.SocketException;
import java.net.Socket;

public interface SocketFactory
{
    Socket afterHandshake() throws SocketException, IOException;
    
    Socket beforeHandshake() throws SocketException, IOException;
    
    Socket connect(final String p0, final int p1, final Properties p2) throws SocketException, IOException;
}
