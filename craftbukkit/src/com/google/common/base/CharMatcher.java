// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.base;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.CheckReturnValue;
import java.util.Arrays;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.Beta;

@Beta
@GwtCompatible
public abstract class CharMatcher implements Predicate<Character>
{
    private static final String BREAKING_WHITESPACE_CHARS = "\t\n\u000b\f\r \u0085\u1680\u2028\u2029\u205f\u3000";
    private static final String NON_BREAKING_WHITESPACE_CHARS = " \u180e\u202f";
    public static final CharMatcher WHITESPACE;
    public static final CharMatcher BREAKING_WHITESPACE;
    public static final CharMatcher ASCII;
    public static final CharMatcher DIGIT;
    public static final CharMatcher JAVA_WHITESPACE;
    public static final CharMatcher JAVA_DIGIT;
    public static final CharMatcher JAVA_LETTER;
    public static final CharMatcher JAVA_LETTER_OR_DIGIT;
    public static final CharMatcher JAVA_UPPER_CASE;
    public static final CharMatcher JAVA_LOWER_CASE;
    public static final CharMatcher JAVA_ISO_CONTROL;
    public static final CharMatcher INVISIBLE;
    public static final CharMatcher SINGLE_WIDTH;
    public static final CharMatcher ANY;
    public static final CharMatcher NONE;
    
    public static CharMatcher is(final char match) {
        return new CharMatcher() {
            public boolean matches(final char c) {
                return c == match;
            }
            
            public String replaceFrom(final CharSequence sequence, final char replacement) {
                return sequence.toString().replace(match, replacement);
            }
            
            public CharMatcher and(final CharMatcher other) {
                return other.matches(match) ? this : CharMatcher$8.NONE;
            }
            
            public CharMatcher or(final CharMatcher other) {
                return other.matches(match) ? other : super.or(other);
            }
            
            public CharMatcher negate() {
                return CharMatcher.isNot(match);
            }
            
            void setBits(final LookupTable table) {
                table.set(match);
            }
            
            public CharMatcher precomputed() {
                return this;
            }
        };
    }
    
    public static CharMatcher isNot(final char match) {
        return new CharMatcher() {
            public boolean matches(final char c) {
                return c != match;
            }
            
            public CharMatcher and(final CharMatcher other) {
                return other.matches(match) ? super.and(other) : other;
            }
            
            public CharMatcher or(final CharMatcher other) {
                return other.matches(match) ? CharMatcher$9.ANY : this;
            }
            
            public CharMatcher negate() {
                return CharMatcher.is(match);
            }
        };
    }
    
    public static CharMatcher anyOf(final CharSequence sequence) {
        switch (sequence.length()) {
            case 0: {
                return CharMatcher.NONE;
            }
            case 1: {
                return is(sequence.charAt(0));
            }
            case 2: {
                final char match1 = sequence.charAt(0);
                final char match2 = sequence.charAt(1);
                return new CharMatcher() {
                    public boolean matches(final char c) {
                        return c == match1 || c == match2;
                    }
                    
                    void setBits(final LookupTable table) {
                        table.set(match1);
                        table.set(match2);
                    }
                    
                    public CharMatcher precomputed() {
                        return this;
                    }
                };
            }
            default: {
                final char[] chars = sequence.toString().toCharArray();
                Arrays.sort(chars);
                return new CharMatcher() {
                    public boolean matches(final char c) {
                        return Arrays.binarySearch(chars, c) >= 0;
                    }
                    
                    void setBits(final LookupTable table) {
                        for (final char c : chars) {
                            table.set(c);
                        }
                    }
                };
            }
        }
    }
    
    public static CharMatcher noneOf(final CharSequence sequence) {
        return anyOf(sequence).negate();
    }
    
    public static CharMatcher inRange(final char startInclusive, final char endInclusive) {
        Preconditions.checkArgument(endInclusive >= startInclusive);
        return new CharMatcher() {
            public boolean matches(final char c) {
                return startInclusive <= c && c <= endInclusive;
            }
            
            void setBits(final LookupTable table) {
                char c = startInclusive;
                char c2;
                do {
                    table.set(c);
                    c2 = c;
                    ++c;
                } while (c2 != endInclusive);
            }
            
            public CharMatcher precomputed() {
                return this;
            }
        };
    }
    
    public static CharMatcher forPredicate(final Predicate<? super Character> predicate) {
        Preconditions.checkNotNull(predicate);
        if (predicate instanceof CharMatcher) {
            return (CharMatcher)predicate;
        }
        return new CharMatcher() {
            public boolean matches(final char c) {
                return predicate.apply(c);
            }
            
            public boolean apply(final Character character) {
                return predicate.apply(Preconditions.checkNotNull(character));
            }
        };
    }
    
    public abstract boolean matches(final char p0);
    
    public CharMatcher negate() {
        return new CharMatcher() {
            public boolean matches(final char c) {
                return !CharMatcher.this.matches(c);
            }
            
            public boolean matchesAllOf(final CharSequence sequence) {
                return CharMatcher.this.matchesNoneOf(sequence);
            }
            
            public boolean matchesNoneOf(final CharSequence sequence) {
                return CharMatcher.this.matchesAllOf(sequence);
            }
            
            public int countIn(final CharSequence sequence) {
                return sequence.length() - CharMatcher.this.countIn(sequence);
            }
            
            public CharMatcher negate() {
                return CharMatcher.this;
            }
        };
    }
    
    public CharMatcher and(final CharMatcher other) {
        return new And(Arrays.asList(this, Preconditions.checkNotNull(other)));
    }
    
    public CharMatcher or(final CharMatcher other) {
        return new Or(Arrays.asList(this, Preconditions.checkNotNull(other)));
    }
    
    public CharMatcher precomputed() {
        return Platform.precomputeCharMatcher(this);
    }
    
    CharMatcher precomputedInternal() {
        final LookupTable table = new LookupTable();
        this.setBits(table);
        return new CharMatcher() {
            public boolean matches(final char c) {
                return table.get(c);
            }
            
            public CharMatcher precomputed() {
                return this;
            }
        };
    }
    
    void setBits(final LookupTable table) {
        char c = '\0';
        char c2;
        do {
            if (this.matches(c)) {
                table.set(c);
            }
            c2 = c;
            ++c;
        } while (c2 != '\uffff');
    }
    
    public boolean matchesAnyOf(final CharSequence sequence) {
        return !this.matchesNoneOf(sequence);
    }
    
    public boolean matchesAllOf(final CharSequence sequence) {
        for (int i = sequence.length() - 1; i >= 0; --i) {
            if (!this.matches(sequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public boolean matchesNoneOf(final CharSequence sequence) {
        return this.indexIn(sequence) == -1;
    }
    
    public int indexIn(final CharSequence sequence) {
        for (int length = sequence.length(), i = 0; i < length; ++i) {
            if (this.matches(sequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }
    
    public int indexIn(final CharSequence sequence, final int start) {
        final int length = sequence.length();
        Preconditions.checkPositionIndex(start, length);
        for (int i = start; i < length; ++i) {
            if (this.matches(sequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }
    
    public int lastIndexIn(final CharSequence sequence) {
        for (int i = sequence.length() - 1; i >= 0; --i) {
            if (this.matches(sequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }
    
    public int countIn(final CharSequence sequence) {
        int count = 0;
        for (int i = 0; i < sequence.length(); ++i) {
            if (this.matches(sequence.charAt(i))) {
                ++count;
            }
        }
        return count;
    }
    
    @CheckReturnValue
    public String removeFrom(final CharSequence sequence) {
        final String string = sequence.toString();
        int pos = this.indexIn(string);
        if (pos == -1) {
            return string;
        }
        final char[] chars = string.toCharArray();
        int spread = 1;
    Label_0027:
        while (true) {
            ++pos;
            while (pos != chars.length) {
                if (this.matches(chars[pos])) {
                    ++spread;
                    continue Label_0027;
                }
                chars[pos - spread] = chars[pos];
                ++pos;
            }
            break;
        }
        return new String(chars, 0, pos - spread);
    }
    
    @CheckReturnValue
    public String retainFrom(final CharSequence sequence) {
        return this.negate().removeFrom(sequence);
    }
    
    @CheckReturnValue
    public String replaceFrom(final CharSequence sequence, final char replacement) {
        final String string = sequence.toString();
        final int pos = this.indexIn(string);
        if (pos == -1) {
            return string;
        }
        final char[] chars = string.toCharArray();
        chars[pos] = replacement;
        for (int i = pos + 1; i < chars.length; ++i) {
            if (this.matches(chars[i])) {
                chars[i] = replacement;
            }
        }
        return new String(chars);
    }
    
    @CheckReturnValue
    public String replaceFrom(final CharSequence sequence, final CharSequence replacement) {
        final int replacementLen = replacement.length();
        if (replacementLen == 0) {
            return this.removeFrom(sequence);
        }
        if (replacementLen == 1) {
            return this.replaceFrom(sequence, replacement.charAt(0));
        }
        final String string = sequence.toString();
        int pos = this.indexIn(string);
        if (pos == -1) {
            return string;
        }
        final int len = string.length();
        final StringBuilder buf = new StringBuilder(len * 3 / 2 + 16);
        int oldpos = 0;
        do {
            buf.append(string, oldpos, pos);
            buf.append(replacement);
            oldpos = pos + 1;
            pos = this.indexIn(string, oldpos);
        } while (pos != -1);
        buf.append(string, oldpos, len);
        return buf.toString();
    }
    
    @CheckReturnValue
    public String trimFrom(final CharSequence sequence) {
        int len;
        int first;
        for (len = sequence.length(), first = 0; first < len && this.matches(sequence.charAt(first)); ++first) {}
        int last;
        for (last = len - 1; last > first && this.matches(sequence.charAt(last)); --last) {}
        return sequence.subSequence(first, last + 1).toString();
    }
    
    @CheckReturnValue
    public String trimLeadingFrom(final CharSequence sequence) {
        int len;
        int first;
        for (len = sequence.length(), first = 0; first < len && this.matches(sequence.charAt(first)); ++first) {}
        return sequence.subSequence(first, len).toString();
    }
    
    @CheckReturnValue
    public String trimTrailingFrom(final CharSequence sequence) {
        final int len = sequence.length();
        int last;
        for (last = len - 1; last >= 0 && this.matches(sequence.charAt(last)); --last) {}
        return sequence.subSequence(0, last + 1).toString();
    }
    
    @CheckReturnValue
    public String collapseFrom(final CharSequence sequence, final char replacement) {
        final int first = this.indexIn(sequence);
        if (first == -1) {
            return sequence.toString();
        }
        final StringBuilder builder = new StringBuilder(sequence.length()).append(sequence.subSequence(0, first)).append(replacement);
        boolean in = true;
        for (int i = first + 1; i < sequence.length(); ++i) {
            final char c = sequence.charAt(i);
            if (this.apply(Character.valueOf(c))) {
                if (!in) {
                    builder.append(replacement);
                    in = true;
                }
            }
            else {
                builder.append(c);
                in = false;
            }
        }
        return builder.toString();
    }
    
    @CheckReturnValue
    public String trimAndCollapseFrom(final CharSequence sequence, final char replacement) {
        final int first = this.negate().indexIn(sequence);
        if (first == -1) {
            return "";
        }
        final StringBuilder builder = new StringBuilder(sequence.length());
        boolean inMatchingGroup = false;
        for (int i = first; i < sequence.length(); ++i) {
            final char c = sequence.charAt(i);
            if (this.apply(Character.valueOf(c))) {
                inMatchingGroup = true;
            }
            else {
                if (inMatchingGroup) {
                    builder.append(replacement);
                    inMatchingGroup = false;
                }
                builder.append(c);
            }
        }
        return builder.toString();
    }
    
    public boolean apply(final Character character) {
        return this.matches(character);
    }
    
    static {
        WHITESPACE = anyOf("\t\n\u000b\f\r \u0085\u1680\u2028\u2029\u205f\u3000 \u180e\u202f").or(inRange('\u2000', '\u200a')).precomputed();
        BREAKING_WHITESPACE = anyOf("\t\n\u000b\f\r \u0085\u1680\u2028\u2029\u205f\u3000").or(inRange('\u2000', '\u2006')).or(inRange('\u2008', '\u200a')).precomputed();
        ASCII = inRange('\0', '\u007f');
        CharMatcher digit = inRange('0', '9');
        final String zeroes = "\u0660\u06f0\u07c0\u0966\u09e6\u0a66\u0ae6\u0b66\u0be6\u0c66\u0ce6\u0d66\u0e50\u0ed0\u0f20\u1040\u1090\u17e0\u1810\u1946\u19d0\u1b50\u1bb0\u1c40\u1c50\ua620\ua8d0\ua900\uaa50\uff10";
        for (final char base : zeroes.toCharArray()) {
            digit = digit.or(inRange(base, (char)(base + '\t')));
        }
        DIGIT = digit.precomputed();
        JAVA_WHITESPACE = inRange('\t', '\r').or(inRange('\u001c', ' ')).or(is('\u1680')).or(is('\u180e')).or(inRange('\u2000', '\u2006')).or(inRange('\u2008', '\u200b')).or(inRange('\u2028', '\u2029')).or(is('\u205f')).or(is('\u3000')).precomputed();
        JAVA_DIGIT = new CharMatcher() {
            public boolean matches(final char c) {
                return Character.isDigit(c);
            }
        };
        JAVA_LETTER = new CharMatcher() {
            public boolean matches(final char c) {
                return Character.isLetter(c);
            }
        };
        JAVA_LETTER_OR_DIGIT = new CharMatcher() {
            public boolean matches(final char c) {
                return Character.isLetterOrDigit(c);
            }
        };
        JAVA_UPPER_CASE = new CharMatcher() {
            public boolean matches(final char c) {
                return Character.isUpperCase(c);
            }
        };
        JAVA_LOWER_CASE = new CharMatcher() {
            public boolean matches(final char c) {
                return Character.isLowerCase(c);
            }
        };
        JAVA_ISO_CONTROL = inRange('\0', '\u001f').or(inRange('\u007f', '\u009f'));
        INVISIBLE = inRange('\0', ' ').or(inRange('\u007f', ' ')).or(is('\u00ad')).or(inRange('\u0600', '\u0603')).or(anyOf("\u06dd\u070f\u1680\u17b4\u17b5\u180e")).or(inRange('\u2000', '\u200f')).or(inRange('\u2028', '\u202f')).or(inRange('\u205f', '\u2064')).or(inRange('\u206a', '\u206f')).or(is('\u3000')).or(inRange('\ud800', '\uf8ff')).or(anyOf("\ufeff\ufff9\ufffa\ufffb")).precomputed();
        SINGLE_WIDTH = inRange('\0', '\u04f9').or(is('\u05be')).or(inRange('\u05d0', '\u05ea')).or(is('\u05f3')).or(is('\u05f4')).or(inRange('\u0600', '\u06ff')).or(inRange('\u0750', '\u077f')).or(inRange('\u0e00', '\u0e7f')).or(inRange('\u1e00', '\u20af')).or(inRange('\u2100', '\u213a')).or(inRange('\ufb50', '\ufdff')).or(inRange('\ufe70', '\ufeff')).or(inRange('\uff61', '\uffdc')).precomputed();
        ANY = new CharMatcher() {
            public boolean matches(final char c) {
                return true;
            }
            
            public int indexIn(final CharSequence sequence) {
                return (sequence.length() == 0) ? -1 : 0;
            }
            
            public int indexIn(final CharSequence sequence, final int start) {
                final int length = sequence.length();
                Preconditions.checkPositionIndex(start, length);
                return (start == length) ? -1 : start;
            }
            
            public int lastIndexIn(final CharSequence sequence) {
                return sequence.length() - 1;
            }
            
            public boolean matchesAllOf(final CharSequence sequence) {
                Preconditions.checkNotNull(sequence);
                return true;
            }
            
            public boolean matchesNoneOf(final CharSequence sequence) {
                return sequence.length() == 0;
            }
            
            public String removeFrom(final CharSequence sequence) {
                Preconditions.checkNotNull(sequence);
                return "";
            }
            
            public String replaceFrom(final CharSequence sequence, final char replacement) {
                final char[] array = new char[sequence.length()];
                Arrays.fill(array, replacement);
                return new String(array);
            }
            
            public String replaceFrom(final CharSequence sequence, final CharSequence replacement) {
                final StringBuilder retval = new StringBuilder(sequence.length() * replacement.length());
                for (int i = 0; i < sequence.length(); ++i) {
                    retval.append(replacement);
                }
                return retval.toString();
            }
            
            public String collapseFrom(final CharSequence sequence, final char replacement) {
                return (sequence.length() == 0) ? "" : String.valueOf(replacement);
            }
            
            public String trimFrom(final CharSequence sequence) {
                Preconditions.checkNotNull(sequence);
                return "";
            }
            
            public int countIn(final CharSequence sequence) {
                return sequence.length();
            }
            
            public CharMatcher and(final CharMatcher other) {
                return Preconditions.checkNotNull(other);
            }
            
            public CharMatcher or(final CharMatcher other) {
                Preconditions.checkNotNull(other);
                return this;
            }
            
            public CharMatcher negate() {
                return CharMatcher$6.NONE;
            }
            
            public CharMatcher precomputed() {
                return this;
            }
        };
        NONE = new CharMatcher() {
            public boolean matches(final char c) {
                return false;
            }
            
            public int indexIn(final CharSequence sequence) {
                Preconditions.checkNotNull(sequence);
                return -1;
            }
            
            public int indexIn(final CharSequence sequence, final int start) {
                final int length = sequence.length();
                Preconditions.checkPositionIndex(start, length);
                return -1;
            }
            
            public int lastIndexIn(final CharSequence sequence) {
                Preconditions.checkNotNull(sequence);
                return -1;
            }
            
            public boolean matchesAllOf(final CharSequence sequence) {
                return sequence.length() == 0;
            }
            
            public boolean matchesNoneOf(final CharSequence sequence) {
                Preconditions.checkNotNull(sequence);
                return true;
            }
            
            public String removeFrom(final CharSequence sequence) {
                return sequence.toString();
            }
            
            public String replaceFrom(final CharSequence sequence, final char replacement) {
                return sequence.toString();
            }
            
            public String replaceFrom(final CharSequence sequence, final CharSequence replacement) {
                Preconditions.checkNotNull(replacement);
                return sequence.toString();
            }
            
            public String collapseFrom(final CharSequence sequence, final char replacement) {
                return sequence.toString();
            }
            
            public String trimFrom(final CharSequence sequence) {
                return sequence.toString();
            }
            
            public int countIn(final CharSequence sequence) {
                Preconditions.checkNotNull(sequence);
                return 0;
            }
            
            public CharMatcher and(final CharMatcher other) {
                Preconditions.checkNotNull(other);
                return this;
            }
            
            public CharMatcher or(final CharMatcher other) {
                return Preconditions.checkNotNull(other);
            }
            
            public CharMatcher negate() {
                return CharMatcher$7.ANY;
            }
            
            void setBits(final LookupTable table) {
            }
            
            public CharMatcher precomputed() {
                return this;
            }
        };
    }
    
    private static class And extends CharMatcher
    {
        List<CharMatcher> components;
        
        And(final List<CharMatcher> components) {
            this.components = components;
        }
        
        public boolean matches(final char c) {
            for (final CharMatcher matcher : this.components) {
                if (!matcher.matches(c)) {
                    return false;
                }
            }
            return true;
        }
        
        public CharMatcher and(final CharMatcher other) {
            final List<CharMatcher> newComponents = new ArrayList<CharMatcher>(this.components);
            newComponents.add(Preconditions.checkNotNull(other));
            return new And(newComponents);
        }
    }
    
    private static class Or extends CharMatcher
    {
        List<CharMatcher> components;
        
        Or(final List<CharMatcher> components) {
            this.components = components;
        }
        
        public boolean matches(final char c) {
            for (final CharMatcher matcher : this.components) {
                if (matcher.matches(c)) {
                    return true;
                }
            }
            return false;
        }
        
        public CharMatcher or(final CharMatcher other) {
            final List<CharMatcher> newComponents = new ArrayList<CharMatcher>(this.components);
            newComponents.add(Preconditions.checkNotNull(other));
            return new Or(newComponents);
        }
        
        void setBits(final LookupTable table) {
            for (final CharMatcher matcher : this.components) {
                matcher.setBits(table);
            }
        }
    }
    
    private static final class LookupTable
    {
        int[] data;
        
        private LookupTable() {
            this.data = new int[2048];
        }
        
        void set(final char index) {
            final int[] data = this.data;
            final char c = (char)(index >> 5);
            data[c] |= 1 << index;
        }
        
        boolean get(final char index) {
            return (this.data[index >> 5] & 1 << index) != 0x0;
        }
    }
}
