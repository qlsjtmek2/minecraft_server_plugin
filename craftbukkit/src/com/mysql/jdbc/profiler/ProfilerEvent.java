// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.profiler;

import java.util.Date;
import com.mysql.jdbc.Util;

public class ProfilerEvent
{
    public static final byte TYPE_WARN = 0;
    public static final byte TYPE_OBJECT_CREATION = 1;
    public static final byte TYPE_PREPARE = 2;
    public static final byte TYPE_QUERY = 3;
    public static final byte TYPE_EXECUTE = 4;
    public static final byte TYPE_FETCH = 5;
    public static final byte TYPE_SLOW_QUERY = 6;
    protected byte eventType;
    protected long connectionId;
    protected int statementId;
    protected int resultSetId;
    protected long eventCreationTime;
    protected long eventDuration;
    protected String durationUnits;
    protected int hostNameIndex;
    protected String hostName;
    protected int catalogIndex;
    protected String catalog;
    protected int eventCreationPointIndex;
    protected Throwable eventCreationPoint;
    protected String eventCreationPointDesc;
    protected String message;
    
    public ProfilerEvent(final byte eventType, final String hostName, final String catalog, final long connectionId, final int statementId, final int resultSetId, final long eventCreationTime, final long eventDuration, final String durationUnits, final String eventCreationPointDesc, final Throwable eventCreationPoint, final String message) {
        this.eventType = eventType;
        this.connectionId = connectionId;
        this.statementId = statementId;
        this.resultSetId = resultSetId;
        this.eventCreationTime = eventCreationTime;
        this.eventDuration = eventDuration;
        this.durationUnits = durationUnits;
        this.eventCreationPoint = eventCreationPoint;
        this.eventCreationPointDesc = eventCreationPointDesc;
        this.message = message;
    }
    
    public String getEventCreationPointAsString() {
        if (this.eventCreationPointDesc == null) {
            this.eventCreationPointDesc = Util.stackTraceToString(this.eventCreationPoint);
        }
        return this.eventCreationPointDesc;
    }
    
    public String toString() {
        final StringBuffer buf = new StringBuffer(32);
        switch (this.eventType) {
            case 4: {
                buf.append("EXECUTE");
                break;
            }
            case 5: {
                buf.append("FETCH");
                break;
            }
            case 1: {
                buf.append("CONSTRUCT");
                break;
            }
            case 2: {
                buf.append("PREPARE");
                break;
            }
            case 3: {
                buf.append("QUERY");
                break;
            }
            case 0: {
                buf.append("WARN");
                break;
            }
            case 6: {
                buf.append("SLOW QUERY");
                break;
            }
            default: {
                buf.append("UNKNOWN");
                break;
            }
        }
        buf.append(" created: ");
        buf.append(new Date(this.eventCreationTime));
        buf.append(" duration: ");
        buf.append(this.eventDuration);
        buf.append(" connection: ");
        buf.append(this.connectionId);
        buf.append(" statement: ");
        buf.append(this.statementId);
        buf.append(" resultset: ");
        buf.append(this.resultSetId);
        if (this.message != null) {
            buf.append(" message: ");
            buf.append(this.message);
        }
        if (this.eventCreationPointDesc != null) {
            buf.append("\n\nEvent Created at:\n");
            buf.append(this.eventCreationPointDesc);
        }
        return buf.toString();
    }
    
    public static ProfilerEvent unpack(final byte[] buf) throws Exception {
        int pos = 0;
        final byte eventType = buf[pos++];
        final long connectionId = readInt(buf, pos);
        pos += 8;
        final int statementId = readInt(buf, pos);
        pos += 4;
        final int resultSetId = readInt(buf, pos);
        pos += 4;
        final long eventCreationTime = readLong(buf, pos);
        pos += 8;
        final long eventDuration = readLong(buf, pos);
        pos += 4;
        final byte[] eventDurationUnits = readBytes(buf, pos);
        pos += 4;
        if (eventDurationUnits != null) {
            pos += eventDurationUnits.length;
        }
        final int eventCreationPointIndex = readInt(buf, pos);
        pos += 4;
        final byte[] eventCreationAsBytes = readBytes(buf, pos);
        pos += 4;
        if (eventCreationAsBytes != null) {
            pos += eventCreationAsBytes.length;
        }
        final byte[] message = readBytes(buf, pos);
        pos += 4;
        if (message != null) {
            pos += message.length;
        }
        return new ProfilerEvent(eventType, "", "", connectionId, statementId, resultSetId, eventCreationTime, eventDuration, new String(eventDurationUnits, "ISO8859_1"), new String(eventCreationAsBytes, "ISO8859_1"), null, new String(message, "ISO8859_1"));
    }
    
    public byte[] pack() throws Exception {
        int len = 29;
        byte[] eventCreationAsBytes = null;
        this.getEventCreationPointAsString();
        if (this.eventCreationPointDesc != null) {
            eventCreationAsBytes = this.eventCreationPointDesc.getBytes("ISO8859_1");
            len += 4 + eventCreationAsBytes.length;
        }
        else {
            len += 4;
        }
        byte[] messageAsBytes = null;
        if (messageAsBytes != null) {
            messageAsBytes = this.message.getBytes("ISO8859_1");
            len += 4 + messageAsBytes.length;
        }
        else {
            len += 4;
        }
        byte[] durationUnitsAsBytes = null;
        if (this.durationUnits != null) {
            durationUnitsAsBytes = this.durationUnits.getBytes("ISO8859_1");
            len += 4 + durationUnitsAsBytes.length;
        }
        else {
            len += 4;
        }
        final byte[] buf = new byte[len];
        int pos = 0;
        buf[pos++] = this.eventType;
        pos = writeLong(this.connectionId, buf, pos);
        pos = writeInt(this.statementId, buf, pos);
        pos = writeInt(this.resultSetId, buf, pos);
        pos = writeLong(this.eventCreationTime, buf, pos);
        pos = writeLong(this.eventDuration, buf, pos);
        pos = writeBytes(durationUnitsAsBytes, buf, pos);
        pos = writeInt(this.eventCreationPointIndex, buf, pos);
        if (eventCreationAsBytes != null) {
            pos = writeBytes(eventCreationAsBytes, buf, pos);
        }
        else {
            pos = writeInt(0, buf, pos);
        }
        if (messageAsBytes != null) {
            pos = writeBytes(messageAsBytes, buf, pos);
        }
        else {
            pos = writeInt(0, buf, pos);
        }
        return buf;
    }
    
    private static int writeInt(final int i, final byte[] buf, int pos) {
        buf[pos++] = (byte)(i & 0xFF);
        buf[pos++] = (byte)(i >>> 8);
        buf[pos++] = (byte)(i >>> 16);
        buf[pos++] = (byte)(i >>> 24);
        return pos;
    }
    
    private static int writeLong(final long l, final byte[] buf, int pos) {
        buf[pos++] = (byte)(l & 0xFFL);
        buf[pos++] = (byte)(l >>> 8);
        buf[pos++] = (byte)(l >>> 16);
        buf[pos++] = (byte)(l >>> 24);
        buf[pos++] = (byte)(l >>> 32);
        buf[pos++] = (byte)(l >>> 40);
        buf[pos++] = (byte)(l >>> 48);
        buf[pos++] = (byte)(l >>> 56);
        return pos;
    }
    
    private static int writeBytes(final byte[] msg, final byte[] buf, int pos) {
        pos = writeInt(msg.length, buf, pos);
        System.arraycopy(msg, 0, buf, pos, msg.length);
        return pos + msg.length;
    }
    
    private static int readInt(final byte[] buf, int pos) {
        return (buf[pos++] & 0xFF) | (buf[pos++] & 0xFF) << 8 | (buf[pos++] & 0xFF) << 16 | (buf[pos++] & 0xFF) << 24;
    }
    
    private static long readLong(final byte[] buf, int pos) {
        return (buf[pos++] & 0xFF) | (buf[pos++] & 0xFF) << 8 | (buf[pos++] & 0xFF) << 16 | (buf[pos++] & 0xFF) << 24 | (buf[pos++] & 0xFF) << 32 | (buf[pos++] & 0xFF) << 40 | (buf[pos++] & 0xFF) << 48 | (buf[pos++] & 0xFF) << 56;
    }
    
    private static byte[] readBytes(final byte[] buf, int pos) {
        final int length = readInt(buf, pos);
        pos += 4;
        final byte[] msg = new byte[length];
        System.arraycopy(buf, pos, msg, 0, length);
        return msg;
    }
    
    public String getCatalog() {
        return this.catalog;
    }
    
    public long getConnectionId() {
        return this.connectionId;
    }
    
    public Throwable getEventCreationPoint() {
        return this.eventCreationPoint;
    }
    
    public long getEventCreationTime() {
        return this.eventCreationTime;
    }
    
    public long getEventDuration() {
        return this.eventDuration;
    }
    
    public String getDurationUnits() {
        return this.durationUnits;
    }
    
    public byte getEventType() {
        return this.eventType;
    }
    
    public int getResultSetId() {
        return this.resultSetId;
    }
    
    public int getStatementId() {
        return this.statementId;
    }
    
    public String getMessage() {
        return this.message;
    }
}
