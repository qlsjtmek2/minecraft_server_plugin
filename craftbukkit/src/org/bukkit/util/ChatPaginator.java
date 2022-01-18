// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.util;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import org.bukkit.ChatColor;
import java.util.LinkedList;

public class ChatPaginator
{
    public static final int GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH = 55;
    public static final int AVERAGE_CHAT_PAGE_WIDTH = 65;
    public static final int UNBOUNDED_PAGE_WIDTH = Integer.MAX_VALUE;
    public static final int OPEN_CHAT_PAGE_HEIGHT = 20;
    public static final int CLOSED_CHAT_PAGE_HEIGHT = 10;
    public static final int UNBOUNDED_PAGE_HEIGHT = Integer.MAX_VALUE;
    
    public static ChatPage paginate(final String unpaginatedString, final int pageNumber) {
        return paginate(unpaginatedString, pageNumber, 55, 10);
    }
    
    public static ChatPage paginate(final String unpaginatedString, final int pageNumber, final int lineLength, final int pageHeight) {
        final String[] lines = wordWrap(unpaginatedString, lineLength);
        final int totalPages = lines.length / pageHeight + ((lines.length % pageHeight != 0) ? 1 : 0);
        final int actualPageNumber = (pageNumber <= totalPages) ? pageNumber : totalPages;
        final int from = (actualPageNumber - 1) * pageHeight;
        final int to = (from + pageHeight <= lines.length) ? (from + pageHeight) : lines.length;
        final String[] selectedLines = Java15Compat.Arrays_copyOfRange(lines, from, to);
        return new ChatPage(selectedLines, actualPageNumber, totalPages);
    }
    
    public static String[] wordWrap(final String rawString, final int lineLength) {
        if (rawString == null) {
            return new String[] { "" };
        }
        if (rawString.length() <= lineLength && !rawString.contains("\n")) {
            return new String[] { rawString };
        }
        final char[] rawChars = (rawString + ' ').toCharArray();
        StringBuilder word = new StringBuilder();
        StringBuilder line = new StringBuilder();
        final List<String> lines = new LinkedList<String>();
        int lineColorChars = 0;
        for (int i = 0; i < rawChars.length; ++i) {
            final char c = rawChars[i];
            if (c == '§') {
                word.append(ChatColor.getByChar(rawChars[i + 1]));
                lineColorChars += 2;
                ++i;
            }
            else if (c == ' ' || c == '\n') {
                if (line.length() == 0 && word.length() > lineLength) {
                    for (final String partialWord : word.toString().split("(?<=\\G.{" + lineLength + "})")) {
                        lines.add(partialWord);
                    }
                }
                else if (line.length() + word.length() - lineColorChars == lineLength) {
                    line.append((CharSequence)word);
                    lines.add(line.toString());
                    line = new StringBuilder();
                    lineColorChars = 0;
                }
                else if (line.length() + 1 + word.length() - lineColorChars > lineLength) {
                    for (final String partialWord : word.toString().split("(?<=\\G.{" + lineLength + "})")) {
                        lines.add(line.toString());
                        line = new StringBuilder(partialWord);
                    }
                    lineColorChars = 0;
                }
                else {
                    if (line.length() > 0) {
                        line.append(' ');
                    }
                    line.append((CharSequence)word);
                }
                word = new StringBuilder();
                if (c == '\n') {
                    lines.add(line.toString());
                    line = new StringBuilder();
                }
            }
            else {
                word.append(c);
            }
        }
        if (line.length() > 0) {
            lines.add(line.toString());
        }
        if (lines.get(0).length() == 0 || lines.get(0).charAt(0) != '§') {
            lines.set(0, ChatColor.WHITE + lines.get(0));
        }
        for (int i = 1; i < lines.size(); ++i) {
            final String pLine = lines.get(i - 1);
            final String subLine = lines.get(i);
            final char color = pLine.charAt(pLine.lastIndexOf(167) + 1);
            if (subLine.length() == 0 || subLine.charAt(0) != '§') {
                lines.set(i, ChatColor.getByChar(color) + subLine);
            }
        }
        return lines.toArray(new String[lines.size()]);
    }
    
    public static class ChatPage
    {
        private String[] lines;
        private int pageNumber;
        private int totalPages;
        
        public ChatPage(final String[] lines, final int pageNumber, final int totalPages) {
            this.lines = lines;
            this.pageNumber = pageNumber;
            this.totalPages = totalPages;
        }
        
        public int getPageNumber() {
            return this.pageNumber;
        }
        
        public int getTotalPages() {
            return this.totalPages;
        }
        
        public String[] getLines() {
            return this.lines;
        }
    }
}
