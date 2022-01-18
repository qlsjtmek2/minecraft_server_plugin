// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl;

public class Constants
{
    private static final boolean VERBOSE;
    public static final int DEFAULT_CAPACITY = 10;
    public static final float DEFAULT_LOAD_FACTOR = 0.5f;
    public static final byte DEFAULT_BYTE_NO_ENTRY_VALUE;
    public static final short DEFAULT_SHORT_NO_ENTRY_VALUE;
    public static final char DEFAULT_CHAR_NO_ENTRY_VALUE;
    public static final int DEFAULT_INT_NO_ENTRY_VALUE;
    public static final long DEFAULT_LONG_NO_ENTRY_VALUE;
    public static final float DEFAULT_FLOAT_NO_ENTRY_VALUE;
    public static final double DEFAULT_DOUBLE_NO_ENTRY_VALUE;
    
    static {
        VERBOSE = (System.getProperty("gnu.trove.verbose", null) != null);
        String property = System.getProperty("gnu.trove.no_entry.byte", "0");
        byte value;
        if ("MAX_VALUE".equalsIgnoreCase(property)) {
            value = 127;
        }
        else if ("MIN_VALUE".equalsIgnoreCase(property)) {
            value = -128;
        }
        else {
            value = Byte.valueOf(property);
        }
        if (value > 127) {
            value = 127;
        }
        else if (value < -128) {
            value = -128;
        }
        DEFAULT_BYTE_NO_ENTRY_VALUE = value;
        if (Constants.VERBOSE) {
            System.out.println("DEFAULT_BYTE_NO_ENTRY_VALUE: " + Constants.DEFAULT_BYTE_NO_ENTRY_VALUE);
        }
        property = System.getProperty("gnu.trove.no_entry.short", "0");
        short value2;
        if ("MAX_VALUE".equalsIgnoreCase(property)) {
            value2 = 32767;
        }
        else if ("MIN_VALUE".equalsIgnoreCase(property)) {
            value2 = -32768;
        }
        else {
            value2 = Short.valueOf(property);
        }
        if (value2 > 32767) {
            value2 = 32767;
        }
        else if (value2 < -32768) {
            value2 = -32768;
        }
        DEFAULT_SHORT_NO_ENTRY_VALUE = value2;
        if (Constants.VERBOSE) {
            System.out.println("DEFAULT_SHORT_NO_ENTRY_VALUE: " + Constants.DEFAULT_SHORT_NO_ENTRY_VALUE);
        }
        property = System.getProperty("gnu.trove.no_entry.char", "\u0000");
        char value3;
        if ("MAX_VALUE".equalsIgnoreCase(property)) {
            value3 = '\uffff';
        }
        else if ("MIN_VALUE".equalsIgnoreCase(property)) {
            value3 = '\0';
        }
        else {
            value3 = property.toCharArray()[0];
        }
        if (value3 > '\uffff') {
            value3 = '\uffff';
        }
        else if (value3 < '\0') {
            value3 = '\0';
        }
        DEFAULT_CHAR_NO_ENTRY_VALUE = value3;
        if (Constants.VERBOSE) {
            System.out.println("DEFAULT_CHAR_NO_ENTRY_VALUE: " + (Object)(int)value3);
        }
        property = System.getProperty("gnu.trove.no_entry.int", "0");
        int value4;
        if ("MAX_VALUE".equalsIgnoreCase(property)) {
            value4 = Integer.MAX_VALUE;
        }
        else if ("MIN_VALUE".equalsIgnoreCase(property)) {
            value4 = Integer.MIN_VALUE;
        }
        else {
            value4 = Integer.valueOf(property);
        }
        DEFAULT_INT_NO_ENTRY_VALUE = value4;
        if (Constants.VERBOSE) {
            System.out.println("DEFAULT_INT_NO_ENTRY_VALUE: " + Constants.DEFAULT_INT_NO_ENTRY_VALUE);
        }
        String property2 = System.getProperty("gnu.trove.no_entry.long", "0");
        long value5;
        if ("MAX_VALUE".equalsIgnoreCase(property2)) {
            value5 = Long.MAX_VALUE;
        }
        else if ("MIN_VALUE".equalsIgnoreCase(property2)) {
            value5 = Long.MIN_VALUE;
        }
        else {
            value5 = Long.valueOf(property2);
        }
        DEFAULT_LONG_NO_ENTRY_VALUE = value5;
        if (Constants.VERBOSE) {
            System.out.println("DEFAULT_LONG_NO_ENTRY_VALUE: " + Constants.DEFAULT_LONG_NO_ENTRY_VALUE);
        }
        property = System.getProperty("gnu.trove.no_entry.float", "0");
        float value6;
        if ("MAX_VALUE".equalsIgnoreCase(property)) {
            value6 = Float.MAX_VALUE;
        }
        else if ("MIN_VALUE".equalsIgnoreCase(property)) {
            value6 = Float.MIN_VALUE;
        }
        else if ("MIN_NORMAL".equalsIgnoreCase(property)) {
            value6 = Float.MIN_NORMAL;
        }
        else if ("NEGATIVE_INFINITY".equalsIgnoreCase(property)) {
            value6 = Float.NEGATIVE_INFINITY;
        }
        else if ("POSITIVE_INFINITY".equalsIgnoreCase(property)) {
            value6 = Float.POSITIVE_INFINITY;
        }
        else {
            value6 = Float.valueOf(property);
        }
        DEFAULT_FLOAT_NO_ENTRY_VALUE = value6;
        if (Constants.VERBOSE) {
            System.out.println("DEFAULT_FLOAT_NO_ENTRY_VALUE: " + Constants.DEFAULT_FLOAT_NO_ENTRY_VALUE);
        }
        property2 = System.getProperty("gnu.trove.no_entry.double", "0");
        double value7;
        if ("MAX_VALUE".equalsIgnoreCase(property2)) {
            value7 = Double.MAX_VALUE;
        }
        else if ("MIN_VALUE".equalsIgnoreCase(property2)) {
            value7 = Double.MIN_VALUE;
        }
        else if ("MIN_NORMAL".equalsIgnoreCase(property2)) {
            value7 = Double.MIN_NORMAL;
        }
        else if ("NEGATIVE_INFINITY".equalsIgnoreCase(property2)) {
            value7 = Double.NEGATIVE_INFINITY;
        }
        else if ("POSITIVE_INFINITY".equalsIgnoreCase(property2)) {
            value7 = Double.POSITIVE_INFINITY;
        }
        else {
            value7 = Double.valueOf(property2);
        }
        DEFAULT_DOUBLE_NO_ENTRY_VALUE = value7;
        if (Constants.VERBOSE) {
            System.out.println("DEFAULT_DOUBLE_NO_ENTRY_VALUE: " + Constants.DEFAULT_DOUBLE_NO_ENTRY_VALUE);
        }
    }
}
