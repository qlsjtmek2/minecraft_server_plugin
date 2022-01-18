// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline.console.completer;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.IOException;
import org.bukkit.craftbukkit.libs.jline.console.CursorBuffer;
import java.util.Collection;
import java.util.List;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;

public class CandidateListCompletionHandler implements CompletionHandler
{
    public boolean complete(final ConsoleReader reader, final List<CharSequence> candidates, final int pos) throws IOException {
        final CursorBuffer buf = reader.getCursorBuffer();
        if (candidates.size() != 1) {
            if (candidates.size() > 1) {
                final String value = this.getUnambiguousCompletions(candidates);
                setBuffer(reader, value, pos);
            }
            printCandidates(reader, candidates);
            reader.drawLine();
            return true;
        }
        final CharSequence value2 = candidates.get(0);
        if (value2.equals(buf.toString())) {
            return false;
        }
        setBuffer(reader, value2, pos);
        return true;
    }
    
    public static void setBuffer(final ConsoleReader reader, final CharSequence value, final int offset) throws IOException {
        while (reader.getCursorBuffer().cursor > offset && reader.backspace()) {}
        reader.putString(value);
        reader.setCursorPosition(offset + value.length());
    }
    
    public static void printCandidates(final ConsoleReader reader, Collection<CharSequence> candidates) throws IOException {
        final Set<CharSequence> distinct = new HashSet<CharSequence>(candidates);
        if (distinct.size() > reader.getAutoprintThreshold()) {
            reader.print(Messages.DISPLAY_CANDIDATES.format(candidates.size()));
            reader.flush();
            final String noOpt = Messages.DISPLAY_CANDIDATES_NO.format(new Object[0]);
            final String yesOpt = Messages.DISPLAY_CANDIDATES_YES.format(new Object[0]);
            final char[] allowed = { yesOpt.charAt(0), noOpt.charAt(0) };
            int c;
            while ((c = reader.readCharacter(allowed)) != -1) {
                final String tmp = new String(new char[] { (char)c });
                if (noOpt.startsWith(tmp)) {
                    reader.println();
                    return;
                }
                if (yesOpt.startsWith(tmp)) {
                    break;
                }
                reader.beep();
            }
        }
        if (distinct.size() != candidates.size()) {
            final Collection<CharSequence> copy = new ArrayList<CharSequence>();
            for (final CharSequence next : candidates) {
                if (!copy.contains(next)) {
                    copy.add(next);
                }
            }
            candidates = copy;
        }
        reader.println();
        reader.printColumns(candidates);
    }
    
    private String getUnambiguousCompletions(final List<CharSequence> candidates) {
        if (candidates == null || candidates.isEmpty()) {
            return null;
        }
        final String[] strings = candidates.toArray(new String[candidates.size()]);
        final String first = strings[0];
        final StringBuilder candidate = new StringBuilder();
        for (int i = 0; i < first.length() && this.startsWith(first.substring(0, i + 1), strings); ++i) {
            candidate.append(first.charAt(i));
        }
        return candidate.toString();
    }
    
    private boolean startsWith(final String starts, final String[] candidates) {
        for (final String candidate : candidates) {
            if (!candidate.startsWith(starts)) {
                return false;
            }
        }
        return true;
    }
    
    private enum Messages
    {
        DISPLAY_CANDIDATES, 
        DISPLAY_CANDIDATES_YES, 
        DISPLAY_CANDIDATES_NO;
        
        private static final ResourceBundle bundle;
        
        public String format(final Object... args) {
            if (Messages.bundle == null) {
                return "";
            }
            return String.format(Messages.bundle.getString(this.name()), args);
        }
        
        static {
            bundle = ResourceBundle.getBundle(CandidateListCompletionHandler.class.getName(), Locale.getDefault());
        }
    }
}
