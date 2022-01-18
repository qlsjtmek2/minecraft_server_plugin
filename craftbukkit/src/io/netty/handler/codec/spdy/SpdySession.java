// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Iterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.Set;
import io.netty.util.internal.PlatformDependent;
import java.util.Map;

final class SpdySession
{
    private final Map<Integer, StreamState> activeStreams;
    
    SpdySession() {
        this.activeStreams = (Map<Integer, StreamState>)PlatformDependent.newConcurrentHashMap();
    }
    
    int numActiveStreams() {
        return this.activeStreams.size();
    }
    
    boolean noActiveStreams() {
        return this.activeStreams.isEmpty();
    }
    
    boolean isActiveStream(final int streamID) {
        return this.activeStreams.containsKey(streamID);
    }
    
    Set<Integer> getActiveStreams() {
        final TreeSet<Integer> StreamIDs = new TreeSet<Integer>(new PriorityComparator());
        StreamIDs.addAll(this.activeStreams.keySet());
        return StreamIDs;
    }
    
    void acceptStream(final int streamID, final byte priority, final boolean remoteSideClosed, final boolean localSideClosed, final int sendWindowSize, final int receiveWindowSize) {
        if (!remoteSideClosed || !localSideClosed) {
            this.activeStreams.put(streamID, new StreamState(priority, remoteSideClosed, localSideClosed, sendWindowSize, receiveWindowSize));
        }
    }
    
    boolean removeStream(final int streamID) {
        final Integer StreamID = streamID;
        final StreamState state = this.activeStreams.get(StreamID);
        this.activeStreams.remove(StreamID);
        return state != null && state.clearPendingWrites();
    }
    
    boolean isRemoteSideClosed(final int streamID) {
        final StreamState state = this.activeStreams.get(streamID);
        return state == null || state.isRemoteSideClosed();
    }
    
    void closeRemoteSide(final int streamID) {
        final Integer StreamID = streamID;
        final StreamState state = this.activeStreams.get(StreamID);
        if (state != null) {
            state.closeRemoteSide();
            if (state.isLocalSideClosed()) {
                this.activeStreams.remove(StreamID);
            }
        }
    }
    
    boolean isLocalSideClosed(final int streamID) {
        final StreamState state = this.activeStreams.get(streamID);
        return state == null || state.isLocalSideClosed();
    }
    
    void closeLocalSide(final int streamID) {
        final Integer StreamID = streamID;
        final StreamState state = this.activeStreams.get(StreamID);
        if (state != null) {
            state.closeLocalSide();
            if (state.isRemoteSideClosed()) {
                this.activeStreams.remove(StreamID);
            }
        }
    }
    
    boolean hasReceivedReply(final int streamID) {
        final StreamState state = this.activeStreams.get(streamID);
        return state != null && state.hasReceivedReply();
    }
    
    void receivedReply(final int streamID) {
        final StreamState state = this.activeStreams.get(streamID);
        if (state != null) {
            state.receivedReply();
        }
    }
    
    int getSendWindowSize(final int streamID) {
        final StreamState state = this.activeStreams.get(streamID);
        return (state != null) ? state.getSendWindowSize() : -1;
    }
    
    int updateSendWindowSize(final int streamID, final int deltaWindowSize) {
        final StreamState state = this.activeStreams.get(streamID);
        return (state != null) ? state.updateSendWindowSize(deltaWindowSize) : -1;
    }
    
    int updateReceiveWindowSize(final int streamID, final int deltaWindowSize) {
        final StreamState state = this.activeStreams.get(streamID);
        if (deltaWindowSize > 0) {
            state.setReceiveWindowSizeLowerBound(0);
        }
        return (state != null) ? state.updateReceiveWindowSize(deltaWindowSize) : -1;
    }
    
    int getReceiveWindowSizeLowerBound(final int streamID) {
        final StreamState state = this.activeStreams.get(streamID);
        return (state != null) ? state.getReceiveWindowSizeLowerBound() : 0;
    }
    
    void updateAllReceiveWindowSizes(final int deltaWindowSize) {
        for (final StreamState state : this.activeStreams.values()) {
            state.updateReceiveWindowSize(deltaWindowSize);
            if (deltaWindowSize < 0) {
                state.setReceiveWindowSizeLowerBound(deltaWindowSize);
            }
        }
    }
    
    boolean putPendingWrite(final int streamID, final Object msg) {
        final StreamState state = this.activeStreams.get(streamID);
        return state != null && state.putPendingWrite(msg);
    }
    
    Object getPendingWrite(final int streamID) {
        final StreamState state = this.activeStreams.get(streamID);
        return (state != null) ? state.getPendingWrite() : null;
    }
    
    Object removePendingWrite(final int streamID) {
        final StreamState state = this.activeStreams.get(streamID);
        return (state != null) ? state.removePendingWrite() : null;
    }
    
    private static final class StreamState
    {
        private final byte priority;
        private volatile boolean remoteSideClosed;
        private volatile boolean localSideClosed;
        private boolean receivedReply;
        private final AtomicInteger sendWindowSize;
        private final AtomicInteger receiveWindowSize;
        private volatile int receiveWindowSizeLowerBound;
        private final Queue<Object> pendingWriteQueue;
        
        StreamState(final byte priority, final boolean remoteSideClosed, final boolean localSideClosed, final int sendWindowSize, final int receiveWindowSize) {
            this.pendingWriteQueue = new ConcurrentLinkedQueue<Object>();
            this.priority = priority;
            this.remoteSideClosed = remoteSideClosed;
            this.localSideClosed = localSideClosed;
            this.sendWindowSize = new AtomicInteger(sendWindowSize);
            this.receiveWindowSize = new AtomicInteger(receiveWindowSize);
        }
        
        byte getPriority() {
            return this.priority;
        }
        
        boolean isRemoteSideClosed() {
            return this.remoteSideClosed;
        }
        
        void closeRemoteSide() {
            this.remoteSideClosed = true;
        }
        
        boolean isLocalSideClosed() {
            return this.localSideClosed;
        }
        
        void closeLocalSide() {
            this.localSideClosed = true;
        }
        
        boolean hasReceivedReply() {
            return this.receivedReply;
        }
        
        void receivedReply() {
            this.receivedReply = true;
        }
        
        int getSendWindowSize() {
            return this.sendWindowSize.get();
        }
        
        int updateSendWindowSize(final int deltaWindowSize) {
            return this.sendWindowSize.addAndGet(deltaWindowSize);
        }
        
        int updateReceiveWindowSize(final int deltaWindowSize) {
            return this.receiveWindowSize.addAndGet(deltaWindowSize);
        }
        
        int getReceiveWindowSizeLowerBound() {
            return this.receiveWindowSizeLowerBound;
        }
        
        void setReceiveWindowSizeLowerBound(final int receiveWindowSizeLowerBound) {
            this.receiveWindowSizeLowerBound = receiveWindowSizeLowerBound;
        }
        
        boolean putPendingWrite(final Object msg) {
            return this.pendingWriteQueue.offer(msg);
        }
        
        Object getPendingWrite() {
            return this.pendingWriteQueue.peek();
        }
        
        Object removePendingWrite() {
            return this.pendingWriteQueue.poll();
        }
        
        boolean clearPendingWrites() {
            if (this.pendingWriteQueue.isEmpty()) {
                return false;
            }
            this.pendingWriteQueue.clear();
            return true;
        }
    }
    
    private final class PriorityComparator implements Comparator<Integer>
    {
        @Override
        public int compare(final Integer id1, final Integer id2) {
            final StreamState state1 = SpdySession.this.activeStreams.get(id1);
            final StreamState state2 = SpdySession.this.activeStreams.get(id2);
            return state1.getPriority() - state2.getPriority();
        }
    }
}
