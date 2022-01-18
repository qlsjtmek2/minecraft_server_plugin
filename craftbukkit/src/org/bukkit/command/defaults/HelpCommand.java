// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Collection;
import org.bukkit.help.IndexHelpTopic;
import java.util.Comparator;
import java.util.TreeSet;
import org.bukkit.help.HelpTopicComparator;
import java.util.Iterator;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpMap;
import org.bukkit.util.ChatPaginator;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.CommandSender;

public class HelpCommand extends VanillaCommand
{
    public HelpCommand() {
        super("help");
        this.description = "Shows the help menu";
        this.usageMessage = "/help <pageNumber>\n/help <topic>\n/help <topic> <pageNumber>";
        this.setPermission("bukkit.command.help");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        String command;
        int pageNumber;
        if (args.length == 0) {
            command = "";
            pageNumber = 1;
        }
        else if (NumberUtils.isDigits(args[args.length - 1])) {
            command = StringUtils.join(ArrayUtils.subarray(args, 0, args.length - 1), " ");
            try {
                pageNumber = NumberUtils.createInteger(args[args.length - 1]);
            }
            catch (NumberFormatException exception) {
                pageNumber = 1;
            }
            if (pageNumber <= 0) {
                pageNumber = 1;
            }
        }
        else {
            command = StringUtils.join(args, " ");
            pageNumber = 1;
        }
        int pageHeight;
        int pageWidth;
        if (sender instanceof ConsoleCommandSender) {
            pageHeight = Integer.MAX_VALUE;
            pageWidth = Integer.MAX_VALUE;
        }
        else {
            pageHeight = 9;
            pageWidth = 55;
        }
        final HelpMap helpMap = Bukkit.getServer().getHelpMap();
        HelpTopic topic = helpMap.getHelpTopic(command);
        if (topic == null) {
            topic = helpMap.getHelpTopic("/" + command);
        }
        if (topic == null) {
            topic = this.findPossibleMatches(command);
        }
        if (topic == null || !topic.canSee(sender)) {
            sender.sendMessage(ChatColor.RED + "No help for " + command);
            return true;
        }
        final ChatPaginator.ChatPage page = ChatPaginator.paginate(topic.getFullText(sender), pageNumber, pageWidth, pageHeight);
        final StringBuilder header = new StringBuilder();
        header.append(ChatColor.YELLOW);
        header.append("--------- ");
        header.append(ChatColor.WHITE);
        header.append("Help: ");
        header.append(topic.getName());
        header.append(" ");
        if (page.getTotalPages() > 1) {
            header.append("(");
            header.append(page.getPageNumber());
            header.append("/");
            header.append(page.getTotalPages());
            header.append(") ");
        }
        header.append(ChatColor.YELLOW);
        for (int i = header.length(); i < 55; ++i) {
            header.append("-");
        }
        sender.sendMessage(header.toString());
        sender.sendMessage(page.getLines());
        return true;
    }
    
    public boolean matches(final String input) {
        return input.equalsIgnoreCase("help") || input.equalsIgnoreCase("?");
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            final List<String> matchedTopics = new ArrayList<String>();
            final String searchString = args[0];
            for (final HelpTopic topic : Bukkit.getServer().getHelpMap().getHelpTopics()) {
                final String trimmedTopic = topic.getName().startsWith("/") ? topic.getName().substring(1) : topic.getName();
                if (trimmedTopic.startsWith(searchString)) {
                    matchedTopics.add(trimmedTopic);
                }
            }
            return matchedTopics;
        }
        return (List<String>)ImmutableList.of();
    }
    
    protected HelpTopic findPossibleMatches(String searchString) {
        final int maxDistance = searchString.length() / 5 + 3;
        final Set<HelpTopic> possibleMatches = new TreeSet<HelpTopic>(HelpTopicComparator.helpTopicComparatorInstance());
        if (searchString.startsWith("/")) {
            searchString = searchString.substring(1);
        }
        for (final HelpTopic topic : Bukkit.getServer().getHelpMap().getHelpTopics()) {
            final String trimmedTopic = topic.getName().startsWith("/") ? topic.getName().substring(1) : topic.getName();
            if (trimmedTopic.length() < searchString.length()) {
                continue;
            }
            if (Character.toLowerCase(trimmedTopic.charAt(0)) != Character.toLowerCase(searchString.charAt(0))) {
                continue;
            }
            if (damerauLevenshteinDistance(searchString, trimmedTopic.substring(0, searchString.length())) >= maxDistance) {
                continue;
            }
            possibleMatches.add(topic);
        }
        if (possibleMatches.size() > 0) {
            return new IndexHelpTopic("Search", null, null, possibleMatches, "Search for: " + searchString);
        }
        return null;
    }
    
    protected static int damerauLevenshteinDistance(final String s1, final String s2) {
        if (s1 == null && s2 == null) {
            return 0;
        }
        if (s1 != null && s2 == null) {
            return s1.length();
        }
        if (s1 == null && s2 != null) {
            return s2.length();
        }
        final int s1Len = s1.length();
        final int s2Len = s2.length();
        final int[][] H = new int[s1Len + 2][s2Len + 2];
        final int INF = s1Len + s2Len;
        H[0][0] = INF;
        for (int i = 0; i <= s1Len; ++i) {
            H[i + 1][1] = i;
            H[i + 1][0] = INF;
        }
        for (int j = 0; j <= s2Len; ++j) {
            H[1][j + 1] = j;
            H[0][j + 1] = INF;
        }
        final Map<Character, Integer> sd = new HashMap<Character, Integer>();
        for (final char Letter : (s1 + s2).toCharArray()) {
            if (!sd.containsKey(Letter)) {
                sd.put(Letter, 0);
            }
        }
        for (int k = 1; k <= s1Len; ++k) {
            int DB = 0;
            for (int l = 1; l <= s2Len; ++l) {
                final int i2 = sd.get(s2.charAt(l - 1));
                final int j2 = DB;
                if (s1.charAt(k - 1) == s2.charAt(l - 1)) {
                    H[k + 1][l + 1] = H[k][l];
                    DB = l;
                }
                else {
                    H[k + 1][l + 1] = Math.min(H[k][l], Math.min(H[k + 1][l], H[k][l + 1])) + 1;
                }
                H[k + 1][l + 1] = Math.min(H[k + 1][l + 1], H[i2][j2] + (k - i2 - 1) + 1 + (l - j2 - 1));
            }
            sd.put(s1.charAt(k - 1), k);
        }
        return H[s1Len + 1][s2Len + 1];
    }
}
