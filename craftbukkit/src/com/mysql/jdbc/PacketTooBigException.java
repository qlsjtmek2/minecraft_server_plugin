// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLException;

public class PacketTooBigException extends SQLException
{
    public PacketTooBigException(final long packetSize, final long maximumPacketSize) {
        super(Messages.getString("PacketTooBigException.0") + packetSize + Messages.getString("PacketTooBigException.1") + maximumPacketSize + Messages.getString("PacketTooBigException.2") + Messages.getString("PacketTooBigException.3") + Messages.getString("PacketTooBigException.4"), "S1000");
    }
}
