// 
// Decompiled by Procyon v0.5.30
// 

package org.fusesource.jansi;

public class AnsiRenderer
{
    public static final String BEGIN_TOKEN = "@|";
    private static final int BEGIN_TOKEN_LEN = 2;
    public static final String END_TOKEN = "|@";
    private static final int END_TOKEN_LEN = 2;
    public static final String CODE_TEXT_SEPARATOR = " ";
    public static final String CODE_LIST_SEPARATOR = ",";
    
    public static String render(final String input) throws IllegalArgumentException {
        final StringBuffer buff = new StringBuffer();
        int i = 0;
        while (true) {
            int j = input.indexOf("@|", i);
            if (j == -1) {
                if (i == 0) {
                    return input;
                }
                buff.append(input.substring(i, input.length()));
                return buff.toString();
            }
            else {
                buff.append(input.substring(i, j));
                final int k = input.indexOf("|@", j);
                if (k == -1) {
                    return input;
                }
                j += 2;
                final String spec = input.substring(j, k);
                final String[] items = spec.split(" ", 2);
                if (items.length == 1) {
                    return input;
                }
                final String replacement = render(items[1], items[0].split(","));
                buff.append(replacement);
                i = k + 2;
            }
        }
    }
    
    private static String render(final String text, final String... codes) {
        Ansi ansi = Ansi.ansi();
        for (final String name : codes) {
            final Code code = Code.valueOf(name.toUpperCase());
            if (code.isColor()) {
                if (code.isBackground()) {
                    ansi = ansi.bg(code.getColor());
                }
                else {
                    ansi = ansi.fg(code.getColor());
                }
            }
            else if (code.isAttribute()) {
                ansi = ansi.a(code.getAttribute());
            }
        }
        return ansi.a(text).reset().toString();
    }
    
    public static boolean test(final String text) {
        return text != null && text.contains("@|");
    }
    
    public enum Code
    {
        BLACK((Enum)Ansi.Color.BLACK), 
        RED((Enum)Ansi.Color.RED), 
        GREEN((Enum)Ansi.Color.GREEN), 
        YELLOW((Enum)Ansi.Color.YELLOW), 
        BLUE((Enum)Ansi.Color.BLUE), 
        MAGENTA((Enum)Ansi.Color.MAGENTA), 
        CYAN((Enum)Ansi.Color.CYAN), 
        WHITE((Enum)Ansi.Color.WHITE), 
        FG_BLACK((Enum)Ansi.Color.BLACK, false), 
        FG_RED((Enum)Ansi.Color.RED, false), 
        FG_GREEN((Enum)Ansi.Color.GREEN, false), 
        FG_YELLOW((Enum)Ansi.Color.YELLOW, false), 
        FG_BLUE((Enum)Ansi.Color.BLUE, false), 
        FG_MAGENTA((Enum)Ansi.Color.MAGENTA, false), 
        FG_CYAN((Enum)Ansi.Color.CYAN, false), 
        FG_WHITE((Enum)Ansi.Color.WHITE, false), 
        BG_BLACK((Enum)Ansi.Color.BLACK, true), 
        BG_RED((Enum)Ansi.Color.RED, true), 
        BG_GREEN((Enum)Ansi.Color.GREEN, true), 
        BG_YELLOW((Enum)Ansi.Color.YELLOW, true), 
        BG_BLUE((Enum)Ansi.Color.BLUE, true), 
        BG_MAGENTA((Enum)Ansi.Color.MAGENTA, true), 
        BG_CYAN((Enum)Ansi.Color.CYAN, true), 
        BG_WHITE((Enum)Ansi.Color.WHITE, true), 
        RESET((Enum)Ansi.Attribute.RESET), 
        INTENSITY_BOLD((Enum)Ansi.Attribute.INTENSITY_BOLD), 
        INTENSITY_FAINT((Enum)Ansi.Attribute.INTENSITY_FAINT), 
        ITALIC((Enum)Ansi.Attribute.ITALIC), 
        UNDERLINE((Enum)Ansi.Attribute.UNDERLINE), 
        BLINK_SLOW((Enum)Ansi.Attribute.BLINK_SLOW), 
        BLINK_FAST((Enum)Ansi.Attribute.BLINK_FAST), 
        BLINK_OFF((Enum)Ansi.Attribute.BLINK_OFF), 
        NEGATIVE_ON((Enum)Ansi.Attribute.NEGATIVE_ON), 
        NEGATIVE_OFF((Enum)Ansi.Attribute.NEGATIVE_OFF), 
        CONCEAL_ON((Enum)Ansi.Attribute.CONCEAL_ON), 
        CONCEAL_OFF((Enum)Ansi.Attribute.CONCEAL_OFF), 
        UNDERLINE_DOUBLE((Enum)Ansi.Attribute.UNDERLINE_DOUBLE), 
        UNDERLINE_OFF((Enum)Ansi.Attribute.UNDERLINE_OFF), 
        BOLD((Enum)Ansi.Attribute.INTENSITY_BOLD), 
        FAINT((Enum)Ansi.Attribute.INTENSITY_FAINT);
        
        private final Enum n;
        private final boolean background;
        
        private Code(final Enum n, final boolean background) {
            this.n = n;
            this.background = background;
        }
        
        private Code(final Enum n) {
            this(n, false);
        }
        
        public boolean isColor() {
            return this.n instanceof Ansi.Color;
        }
        
        public Ansi.Color getColor() {
            return (Ansi.Color)this.n;
        }
        
        public boolean isAttribute() {
            return this.n instanceof Ansi.Attribute;
        }
        
        public Ansi.Attribute getAttribute() {
            return (Ansi.Attribute)this.n;
        }
        
        public boolean isBackground() {
            return this.background;
        }
    }
}
