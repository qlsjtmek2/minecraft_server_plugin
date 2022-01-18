// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.sql.ResultSet;

public class RsetDataReaderIndexed extends RsetDataReader
{
    private final int[] rsetIndexPositions;
    
    public RsetDataReaderIndexed(final ResultSet rset, final int[] rsetIndexPositions, final boolean rowNumberIncluded) {
        super(rset);
        if (!rowNumberIncluded) {
            this.rsetIndexPositions = rsetIndexPositions;
        }
        else {
            this.rsetIndexPositions = new int[rsetIndexPositions.length + 1];
            for (int i = 0; i < rsetIndexPositions.length; ++i) {
                this.rsetIndexPositions[i + 1] = rsetIndexPositions[i] + 1;
            }
        }
    }
    
    protected int pos() {
        final int i = this.pos++;
        return this.rsetIndexPositions[i];
    }
}
