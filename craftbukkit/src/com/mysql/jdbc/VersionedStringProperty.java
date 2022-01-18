// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLException;
import java.util.List;

class VersionedStringProperty
{
    int majorVersion;
    int minorVersion;
    int subminorVersion;
    boolean preferredValue;
    String propertyInfo;
    
    VersionedStringProperty(String property) {
        this.preferredValue = false;
        property = property.trim();
        if (property.startsWith("*")) {
            property = property.substring(1);
            this.preferredValue = true;
        }
        if (property.startsWith(">")) {
            int charPos;
            char c;
            for (property = property.substring(1), charPos = 0, charPos = 0; charPos < property.length(); ++charPos) {
                c = property.charAt(charPos);
                if (!Character.isWhitespace(c) && !Character.isDigit(c) && c != '.') {
                    break;
                }
            }
            final String versionInfo = property.substring(0, charPos);
            final List versionParts = StringUtils.split(versionInfo, ".", true);
            this.majorVersion = Integer.parseInt(versionParts.get(0).toString());
            if (versionParts.size() > 1) {
                this.minorVersion = Integer.parseInt(versionParts.get(1).toString());
            }
            else {
                this.minorVersion = 0;
            }
            if (versionParts.size() > 2) {
                this.subminorVersion = Integer.parseInt(versionParts.get(2).toString());
            }
            else {
                this.subminorVersion = 0;
            }
            this.propertyInfo = property.substring(charPos);
        }
        else {
            final boolean majorVersion = false;
            this.subminorVersion = (majorVersion ? 1 : 0);
            this.minorVersion = (majorVersion ? 1 : 0);
            this.majorVersion = (majorVersion ? 1 : 0);
            this.propertyInfo = property;
        }
    }
    
    VersionedStringProperty(final String property, final int major, final int minor, final int subminor) {
        this.preferredValue = false;
        this.propertyInfo = property;
        this.majorVersion = major;
        this.minorVersion = minor;
        this.subminorVersion = subminor;
    }
    
    boolean isOkayForVersion(final Connection conn) throws SQLException {
        return conn.versionMeetsMinimum(this.majorVersion, this.minorVersion, this.subminorVersion);
    }
    
    public String toString() {
        return this.propertyInfo;
    }
}
