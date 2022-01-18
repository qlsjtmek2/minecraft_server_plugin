// 
// Decompiled by Procyon v0.5.30
// 

package org.fusesource.jansi;

import java.util.Collection;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Ansi
{
    private static final char FIRST_ESC_CHAR = '\u001b';
    private static final char SECOND_ESC_CHAR = '[';
    public static final String DISABLE;
    private static Callable<Boolean> detector;
    private static final InheritableThreadLocal<Boolean> holder;
    private final StringBuilder builder;
    private final ArrayList<Integer> attributeOptions;
    
    public static void setDetector(final Callable<Boolean> detector) {
        if (detector == null) {
            throw new IllegalArgumentException();
        }
        Ansi.detector = detector;
    }
    
    public static boolean isDetected() {
        try {
            return Ansi.detector.call();
        }
        catch (Exception e) {
            return true;
        }
    }
    
    public static void setEnabled(final boolean flag) {
        Ansi.holder.set(flag);
    }
    
    public static boolean isEnabled() {
        return Ansi.holder.get();
    }
    
    public static Ansi ansi() {
        if (isEnabled()) {
            return new Ansi();
        }
        return new NoAnsi();
    }
    
    public Ansi() {
        this(new StringBuilder());
    }
    
    public Ansi(final Ansi parent) {
        this(new StringBuilder(parent.builder));
        this.attributeOptions.addAll(parent.attributeOptions);
    }
    
    public Ansi(final int size) {
        this(new StringBuilder(size));
    }
    
    public Ansi(final StringBuilder builder) {
        this.attributeOptions = new ArrayList<Integer>(5);
        this.builder = builder;
    }
    
    public static Ansi ansi(final StringBuilder builder) {
        return new Ansi(builder);
    }
    
    public static Ansi ansi(final int size) {
        return new Ansi(size);
    }
    
    public Ansi fg(final Color color) {
        this.attributeOptions.add(color.fg());
        return this;
    }
    
    public Ansi bg(final Color color) {
        this.attributeOptions.add(color.bg());
        return this;
    }
    
    public Ansi a(final Attribute attribute) {
        this.attributeOptions.add(attribute.value());
        return this;
    }
    
    public Ansi cursor(final int x, final int y) {
        return this.appendEscapeSequence('H', x, y);
    }
    
    public Ansi cursorUp(final int y) {
        return this.appendEscapeSequence('A', y);
    }
    
    public Ansi cursorDown(final int y) {
        return this.appendEscapeSequence('B', y);
    }
    
    public Ansi cursorRight(final int x) {
        return this.appendEscapeSequence('C', x);
    }
    
    public Ansi cursorLeft(final int x) {
        return this.appendEscapeSequence('D', x);
    }
    
    public Ansi eraseScreen() {
        return this.appendEscapeSequence('J', Erase.ALL.value());
    }
    
    public Ansi eraseScreen(final Erase kind) {
        return this.appendEscapeSequence('J', kind.value());
    }
    
    public Ansi eraseLine() {
        return this.appendEscapeSequence('K');
    }
    
    public Ansi eraseLine(final Erase kind) {
        return this.appendEscapeSequence('K', kind.value());
    }
    
    public Ansi scrollUp(final int rows) {
        return this.appendEscapeSequence('S', rows);
    }
    
    public Ansi scrollDown(final int rows) {
        return this.appendEscapeSequence('T', rows);
    }
    
    public Ansi saveCursorPosition() {
        return this.appendEscapeSequence('s');
    }
    
    public Ansi restorCursorPosition() {
        return this.appendEscapeSequence('u');
    }
    
    public Ansi reset() {
        return this.a(Attribute.RESET);
    }
    
    public Ansi bold() {
        return this.a(Attribute.INTENSITY_BOLD);
    }
    
    public Ansi boldOff() {
        return this.a(Attribute.INTENSITY_BOLD_OFF);
    }
    
    public Ansi a(final String value) {
        this.flushAtttributes();
        this.builder.append(value);
        return this;
    }
    
    public Ansi a(final boolean value) {
        this.flushAtttributes();
        this.builder.append(value);
        return this;
    }
    
    public Ansi a(final char value) {
        this.flushAtttributes();
        this.builder.append(value);
        return this;
    }
    
    public Ansi a(final char[] value, final int offset, final int len) {
        this.flushAtttributes();
        this.builder.append(value, offset, len);
        return this;
    }
    
    public Ansi a(final char[] value) {
        this.flushAtttributes();
        this.builder.append(value);
        return this;
    }
    
    public Ansi a(final CharSequence value, final int start, final int end) {
        this.flushAtttributes();
        this.builder.append(value, start, end);
        return this;
    }
    
    public Ansi a(final CharSequence value) {
        this.flushAtttributes();
        this.builder.append(value);
        return this;
    }
    
    public Ansi a(final double value) {
        this.flushAtttributes();
        this.builder.append(value);
        return this;
    }
    
    public Ansi a(final float value) {
        this.flushAtttributes();
        this.builder.append(value);
        return this;
    }
    
    public Ansi a(final int value) {
        this.flushAtttributes();
        this.builder.append(value);
        return this;
    }
    
    public Ansi a(final long value) {
        this.flushAtttributes();
        this.builder.append(value);
        return this;
    }
    
    public Ansi a(final Object value) {
        this.flushAtttributes();
        this.builder.append(value);
        return this;
    }
    
    public Ansi a(final StringBuffer value) {
        this.flushAtttributes();
        this.builder.append(value);
        return this;
    }
    
    public Ansi newline() {
        this.flushAtttributes();
        this.builder.append(System.getProperty("line.separator"));
        return this;
    }
    
    public Ansi format(final String pattern, final Object... args) {
        this.flushAtttributes();
        this.builder.append(String.format(pattern, args));
        return this;
    }
    
    public Ansi render(final String text) {
        this.a(AnsiRenderer.render(text));
        return this;
    }
    
    public Ansi render(final String text, final Object... args) {
        this.a(String.format(AnsiRenderer.render(text), args));
        return this;
    }
    
    public String toString() {
        this.flushAtttributes();
        return this.builder.toString();
    }
    
    private Ansi appendEscapeSequence(final char command) {
        this.flushAtttributes();
        this.builder.append('\u001b');
        this.builder.append('[');
        this.builder.append(command);
        return this;
    }
    
    private Ansi appendEscapeSequence(final char command, final int option) {
        this.flushAtttributes();
        this.builder.append('\u001b');
        this.builder.append('[');
        this.builder.append(option);
        this.builder.append(command);
        return this;
    }
    
    private Ansi appendEscapeSequence(final char command, final Object... options) {
        this.flushAtttributes();
        return this._appendEscapeSequence(command, options);
    }
    
    private void flushAtttributes() {
        if (this.attributeOptions.isEmpty()) {
            return;
        }
        if (this.attributeOptions.size() == 1 && this.attributeOptions.get(0) == 0) {
            this.builder.append('\u001b');
            this.builder.append('[');
            this.builder.append('m');
        }
        else {
            this._appendEscapeSequence('m', this.attributeOptions.toArray());
        }
        this.attributeOptions.clear();
    }
    
    private Ansi _appendEscapeSequence(final char command, final Object... options) {
        this.builder.append('\u001b');
        this.builder.append('[');
        for (int size = options.length, i = 0; i < size; ++i) {
            if (i != 0) {
                this.builder.append(';');
            }
            if (options[i] != null) {
                this.builder.append(options[i]);
            }
        }
        this.builder.append(command);
        return this;
    }
    
    static {
        DISABLE = Ansi.class.getName() + ".disable";
        Ansi.detector = new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return !Boolean.getBoolean(Ansi.DISABLE);
            }
        };
        holder = new InheritableThreadLocal<Boolean>() {
            protected Boolean initialValue() {
                return Ansi.isDetected();
            }
        };
    }
    
    public enum Color
    {
        BLACK(0, "BLACK"), 
        RED(1, "RED"), 
        GREEN(2, "GREEN"), 
        YELLOW(3, "YELLOW"), 
        BLUE(4, "BLUE"), 
        MAGENTA(5, "MAGENTA"), 
        CYAN(6, "CYAN"), 
        WHITE(7, "WHITE"), 
        DEFAULT(9, "DEFAULT");
        
        private final int value;
        private final String name;
        
        private Color(final int index, final String name) {
            this.value = index;
            this.name = name;
        }
        
        public String toString() {
            return this.name;
        }
        
        public int value() {
            return this.value;
        }
        
        public int fg() {
            return this.value + 30;
        }
        
        public int bg() {
            return this.value + 40;
        }
        
        public int fgBright() {
            return this.value + 90;
        }
        
        public int bgBright() {
            return this.value + 100;
        }
    }
    
    public enum Attribute
    {
        RESET(0, "RESET"), 
        INTENSITY_BOLD(1, "INTENSITY_BOLD"), 
        INTENSITY_FAINT(2, "INTENSITY_FAINT"), 
        ITALIC(3, "ITALIC_ON"), 
        UNDERLINE(4, "UNDERLINE_ON"), 
        BLINK_SLOW(5, "BLINK_SLOW"), 
        BLINK_FAST(6, "BLINK_FAST"), 
        NEGATIVE_ON(7, "NEGATIVE_ON"), 
        CONCEAL_ON(8, "CONCEAL_ON"), 
        STRIKETHROUGH_ON(9, "STRIKETHROUGH_ON"), 
        UNDERLINE_DOUBLE(21, "UNDERLINE_DOUBLE"), 
        INTENSITY_BOLD_OFF(22, "INTENSITY_BOLD_OFF"), 
        ITALIC_OFF(23, "ITALIC_OFF"), 
        UNDERLINE_OFF(24, "UNDERLINE_OFF"), 
        BLINK_OFF(25, "BLINK_OFF"), 
        NEGATIVE_OFF(27, "NEGATIVE_OFF"), 
        CONCEAL_OFF(28, "CONCEAL_OFF"), 
        STRIKETHROUGH_OFF(29, "STRIKETHROUGH_OFF");
        
        private final int value;
        private final String name;
        
        private Attribute(final int index, final String name) {
            this.value = index;
            this.name = name;
        }
        
        public String toString() {
            return this.name;
        }
        
        public int value() {
            return this.value;
        }
    }
    
    public enum Erase
    {
        FORWARD(0, "FORWARD"), 
        BACKWARD(1, "BACKWARD"), 
        ALL(2, "ALL");
        
        private final int value;
        private final String name;
        
        private Erase(final int index, final String name) {
            this.value = index;
            this.name = name;
        }
        
        public String toString() {
            return this.name;
        }
        
        public int value() {
            return this.value;
        }
    }
    
    private static class NoAnsi extends Ansi
    {
        public Ansi fg(final Color color) {
            return this;
        }
        
        public Ansi bg(final Color color) {
            return this;
        }
        
        public Ansi a(final Attribute attribute) {
            return this;
        }
        
        public Ansi cursor(final int x, final int y) {
            return this;
        }
        
        public Ansi cursorUp(final int y) {
            return this;
        }
        
        public Ansi cursorRight(final int x) {
            return this;
        }
        
        public Ansi cursorDown(final int y) {
            return this;
        }
        
        public Ansi cursorLeft(final int x) {
            return this;
        }
        
        public Ansi eraseScreen() {
            return this;
        }
        
        public Ansi eraseScreen(final Erase kind) {
            return this;
        }
        
        public Ansi eraseLine() {
            return this;
        }
        
        public Ansi eraseLine(final Erase kind) {
            return this;
        }
        
        public Ansi scrollUp(final int rows) {
            return this;
        }
        
        public Ansi scrollDown(final int rows) {
            return this;
        }
        
        public Ansi saveCursorPosition() {
            return this;
        }
        
        public Ansi restorCursorPosition() {
            return this;
        }
        
        public Ansi reset() {
            return this;
        }
    }
}
