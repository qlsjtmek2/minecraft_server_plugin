// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple.internal;

class ColumnWidthCalculator
{
    int calculate(final int totalWidth, final int numberOfColumns) {
        if (numberOfColumns == 1) {
            return totalWidth;
        }
        final int remainder = totalWidth % numberOfColumns;
        if (remainder == numberOfColumns - 1) {
            return totalWidth / numberOfColumns;
        }
        return totalWidth / numberOfColumns - 1;
    }
}
