// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import java.io.DataOutputStream;
import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
import java.util.Collection;
import java.io.DataInput;
import java.io.IOException;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

public final class TransactionEventTable implements Serializable
{
    private static final long serialVersionUID = 2236555729767483264L;
    private final Map<String, TableIUD> map;
    
    public TransactionEventTable() {
        this.map = new HashMap<String, TableIUD>();
    }
    
    public String toString() {
        return "TransactionEventTable " + this.map.values();
    }
    
    public void writeBinaryMessage(final BinaryMessageList msgList) throws IOException {
        for (final TableIUD tableIud : this.map.values()) {
            tableIud.writeBinaryMessage(msgList);
        }
    }
    
    public void readBinaryMessage(final DataInput dataInput) throws IOException {
        final TableIUD tableIud = TableIUD.readBinaryMessage(dataInput);
        this.map.put(tableIud.getTable(), tableIud);
    }
    
    public void add(final TransactionEventTable table) {
        for (final TableIUD iud : table.values()) {
            this.add(iud);
        }
    }
    
    public void add(String table, final boolean insert, final boolean update, final boolean delete) {
        table = table.toUpperCase();
        this.add(new TableIUD(table, insert, update, delete));
    }
    
    public void add(final TableIUD newTableIUD) {
        final TableIUD existingTableIUD = this.map.put(newTableIUD.getTable(), newTableIUD);
        if (existingTableIUD != null) {
            newTableIUD.add(existingTableIUD);
        }
    }
    
    public boolean isEmpty() {
        return this.map.isEmpty();
    }
    
    public Collection<TableIUD> values() {
        return this.map.values();
    }
    
    public static class TableIUD implements Serializable
    {
        private static final long serialVersionUID = -1958317571064162089L;
        private String table;
        private boolean insert;
        private boolean update;
        private boolean delete;
        
        private TableIUD(final String table, final boolean insert, final boolean update, final boolean delete) {
            this.table = table;
            this.insert = insert;
            this.update = update;
            this.delete = delete;
        }
        
        public static TableIUD readBinaryMessage(final DataInput dataInput) throws IOException {
            final String table = dataInput.readUTF();
            final boolean insert = dataInput.readBoolean();
            final boolean update = dataInput.readBoolean();
            final boolean delete = dataInput.readBoolean();
            return new TableIUD(table, insert, update, delete);
        }
        
        public void writeBinaryMessage(final BinaryMessageList msgList) throws IOException {
            final BinaryMessage msg = new BinaryMessage(this.table.length() + 10);
            final DataOutputStream os = msg.getOs();
            os.writeInt(2);
            os.writeUTF(this.table);
            os.writeBoolean(this.insert);
            os.writeBoolean(this.update);
            os.writeBoolean(this.delete);
            msgList.add(msg);
        }
        
        public String toString() {
            return "TableIUD " + this.table + " i:" + this.insert + " u:" + this.update + " d:" + this.delete;
        }
        
        private void add(final TableIUD other) {
            if (other.insert) {
                this.insert = true;
            }
            if (other.update) {
                this.update = true;
            }
            if (other.delete) {
                this.delete = true;
            }
        }
        
        public String getTable() {
            return this.table;
        }
        
        public boolean isInsert() {
            return this.insert;
        }
        
        public boolean isUpdate() {
            return this.update;
        }
        
        public boolean isDelete() {
            return this.delete;
        }
        
        public boolean isUpdateOrDelete() {
            return this.update || this.delete;
        }
    }
}
