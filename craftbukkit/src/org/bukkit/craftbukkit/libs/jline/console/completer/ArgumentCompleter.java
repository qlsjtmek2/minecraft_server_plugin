// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline.console.completer;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArgumentCompleter implements Completer
{
    private final ArgumentDelimiter delimiter;
    private final List<Completer> completers;
    private boolean strict;
    
    public ArgumentCompleter(final ArgumentDelimiter delimiter, final Collection<Completer> completers) {
        this.completers = new ArrayList<Completer>();
        this.strict = true;
        assert delimiter != null;
        this.delimiter = delimiter;
        assert completers != null;
        this.completers.addAll(completers);
    }
    
    public ArgumentCompleter(final ArgumentDelimiter delimiter, final Completer... completers) {
        this(delimiter, Arrays.asList(completers));
    }
    
    public ArgumentCompleter(final Completer... completers) {
        this(new WhitespaceArgumentDelimiter(), completers);
    }
    
    public ArgumentCompleter(final List<Completer> completers) {
        this(new WhitespaceArgumentDelimiter(), completers);
    }
    
    public void setStrict(final boolean strict) {
        this.strict = strict;
    }
    
    public boolean isStrict() {
        return this.strict;
    }
    
    public ArgumentDelimiter getDelimiter() {
        return this.delimiter;
    }
    
    public List<Completer> getCompleters() {
        return this.completers;
    }
    
    public int complete(final String buffer, final int cursor, final List<CharSequence> candidates) {
        assert candidates != null;
        final ArgumentDelimiter delim = this.getDelimiter();
        final ArgumentList list = delim.delimit(buffer, cursor);
        final int argpos = list.getArgumentPosition();
        final int argIndex = list.getCursorArgumentIndex();
        if (argIndex < 0) {
            return -1;
        }
        final List<Completer> completers = this.getCompleters();
        Completer completer;
        if (argIndex >= completers.size()) {
            completer = completers.get(completers.size() - 1);
        }
        else {
            completer = completers.get(argIndex);
        }
        for (int i = 0; this.isStrict() && i < argIndex; ++i) {
            final Completer sub = completers.get((i >= completers.size()) ? (completers.size() - 1) : i);
            final String[] args = list.getArguments();
            final String arg = (args == null || i >= args.length) ? "" : args[i];
            final List<CharSequence> subCandidates = new LinkedList<CharSequence>();
            if (sub.complete(arg, arg.length(), subCandidates) == -1) {
                return -1;
            }
            if (subCandidates.size() == 0) {
                return -1;
            }
        }
        final int ret = completer.complete(list.getCursorArgument(), argpos, candidates);
        if (ret == -1) {
            return -1;
        }
        final int pos = ret + list.getBufferPosition() - argpos;
        if (cursor != buffer.length() && delim.isDelimiter(buffer, cursor)) {
            for (int j = 0; j < candidates.size(); ++j) {
                CharSequence val;
                for (val = candidates.get(j); val.length() > 0 && delim.isDelimiter(val, val.length() - 1); val = val.subSequence(0, val.length() - 1)) {}
                candidates.set(j, val);
            }
        }
        Log.trace("Completing ", buffer, " (pos=", cursor, ") with: ", candidates, ": offset=", pos);
        return pos;
    }
    
    public abstract static class AbstractArgumentDelimiter implements ArgumentDelimiter
    {
        private char[] quoteChars;
        private char[] escapeChars;
        
        public AbstractArgumentDelimiter() {
            this.quoteChars = new char[] { '\'', '\"' };
            this.escapeChars = new char[] { '\\' };
        }
        
        public void setQuoteChars(final char[] chars) {
            this.quoteChars = chars;
        }
        
        public char[] getQuoteChars() {
            return this.quoteChars;
        }
        
        public void setEscapeChars(final char[] chars) {
            this.escapeChars = chars;
        }
        
        public char[] getEscapeChars() {
            return this.escapeChars;
        }
        
        public ArgumentList delimit(final CharSequence buffer, final int cursor) {
            final List<String> args = new LinkedList<String>();
            final StringBuilder arg = new StringBuilder();
            int argpos = -1;
            int bindex = -1;
            for (int i = 0; buffer != null && i <= buffer.length(); ++i) {
                if (i == cursor) {
                    bindex = args.size();
                    argpos = arg.length();
                }
                if (i == buffer.length() || this.isDelimiter(buffer, i)) {
                    if (arg.length() > 0) {
                        args.add(arg.toString());
                        arg.setLength(0);
                    }
                }
                else {
                    arg.append(buffer.charAt(i));
                }
            }
            return new ArgumentList(args.toArray(new String[args.size()]), bindex, argpos, cursor);
        }
        
        public boolean isDelimiter(final CharSequence buffer, final int pos) {
            return !this.isQuoted(buffer, pos) && !this.isEscaped(buffer, pos) && this.isDelimiterChar(buffer, pos);
        }
        
        public boolean isQuoted(final CharSequence buffer, final int pos) {
            return false;
        }
        
        public boolean isEscaped(final CharSequence buffer, final int pos) {
            if (pos <= 0) {
                return false;
            }
            for (int i = 0; this.escapeChars != null && i < this.escapeChars.length; ++i) {
                if (buffer.charAt(pos) == this.escapeChars[i]) {
                    return !this.isEscaped(buffer, pos - 1);
                }
            }
            return false;
        }
        
        public abstract boolean isDelimiterChar(final CharSequence p0, final int p1);
    }
    
    public static class WhitespaceArgumentDelimiter extends AbstractArgumentDelimiter
    {
        public boolean isDelimiterChar(final CharSequence buffer, final int pos) {
            return Character.isWhitespace(buffer.charAt(pos));
        }
    }
    
    public static class ArgumentList
    {
        private String[] arguments;
        private int cursorArgumentIndex;
        private int argumentPosition;
        private int bufferPosition;
        
        public ArgumentList(final String[] arguments, final int cursorArgumentIndex, final int argumentPosition, final int bufferPosition) {
            assert arguments != null;
            this.arguments = arguments;
            this.cursorArgumentIndex = cursorArgumentIndex;
            this.argumentPosition = argumentPosition;
            this.bufferPosition = bufferPosition;
        }
        
        public void setCursorArgumentIndex(final int i) {
            this.cursorArgumentIndex = i;
        }
        
        public int getCursorArgumentIndex() {
            return this.cursorArgumentIndex;
        }
        
        public String getCursorArgument() {
            if (this.cursorArgumentIndex < 0 || this.cursorArgumentIndex >= this.arguments.length) {
                return null;
            }
            return this.arguments[this.cursorArgumentIndex];
        }
        
        public void setArgumentPosition(final int pos) {
            this.argumentPosition = pos;
        }
        
        public int getArgumentPosition() {
            return this.argumentPosition;
        }
        
        public void setArguments(final String[] arguments) {
            this.arguments = arguments;
        }
        
        public String[] getArguments() {
            return this.arguments;
        }
        
        public void setBufferPosition(final int pos) {
            this.bufferPosition = pos;
        }
        
        public int getBufferPosition() {
            return this.bufferPosition;
        }
    }
    
    public interface ArgumentDelimiter
    {
        ArgumentList delimit(final CharSequence p0, final int p1);
        
        boolean isDelimiter(final CharSequence p0, final int p1);
    }
}
