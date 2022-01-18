// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.commands;

import java.util.Date;
import java.util.TimeZone;
import java.util.SimpleTimeZone;
import java.text.SimpleDateFormat;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Calendar;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.bukkit.potion.PotionEffectType;
import java.util.Map;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;
import think.rpgitems.Plugin;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.ChatColor;
import think.rpgitems.data.Locale;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import think.rpgitems.lib.gnu.trove.map.hash.TCharObjectHashMap;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Commands
{
    private static HashMap<String, ArrayList<CommandDef>> commands;
    private static TCharObjectHashMap<Class<? extends CommandArgument>> argTypes;
    
    static {
        Commands.commands = new HashMap<String, ArrayList<CommandDef>>();
        (Commands.argTypes = new TCharObjectHashMap<Class<? extends CommandArgument>>()).put('s', ArgumentString.class);
        Commands.argTypes.put('i', ArgumentInteger.class);
        Commands.argTypes.put('f', ArgumentDouble.class);
        Commands.argTypes.put('p', ArgumentPlayer.class);
        Commands.argTypes.put('o', ArgumentOption.class);
        Commands.argTypes.put('n', ArgumentItem.class);
        Commands.argTypes.put('m', ArgumentMaterial.class);
        Commands.argTypes.put('e', ArgumentEnum.class);
        register(new CommandHandler() {
            @CommandString("rpgitem help $terms:s[]")
            @CommandDocumentation("$command.rpgitem.help")
            @CommandGroup("help")
            public void help(final CommandSender sender, final String query) {
                Commands.searchHelp(sender, query);
            }
        });
    }
    
    public static void exec(final CommandSender sender, String com) {
        com = com.trim();
        if (com.length() == 0) {
            return;
        }
        final int pos = com.indexOf(32);
        String comName;
        if (pos == -1) {
            comName = com;
        }
        else {
            comName = com.substring(0, pos);
        }
        com = com.substring(pos + 1);
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final ArrayList<CommandDef> command = Commands.commands.get(comName);
        if (command == null) {
            sender.sendMessage(ChatColor.RED + String.format(Locale.get("message.error.unknown.command", locale), comName));
            return;
        }
        if (pos == -1) {
            for (final CommandDef c : command) {
                if (c.arguments.length == 0) {
                    try {
                        if (c.handlePermissions || sender.hasPermission("rpgitem")) {
                            c.method.invoke(c.handler, sender);
                        }
                        else {
                            sender.sendMessage(ChatColor.RED + Locale.get("message.error.permission", locale));
                        }
                    }
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    catch (IllegalArgumentException e2) {
                        e2.printStackTrace();
                    }
                    catch (InvocationTargetException e3) {
                        e3.printStackTrace();
                    }
                    return;
                }
            }
            if (sender.hasPermission("rpgitem")) {
                sender.sendMessage(String.format(ChatColor.GREEN + Locale.get("message.command.usage", locale), comName, Plugin.plugin.getDescription().getVersion()));
                for (final CommandDef c : command) {
                    final StringBuilder buf = new StringBuilder();
                    buf.append(ChatColor.GREEN).append('/').append(comName);
                    CommandArgument[] arguments;
                    for (int length = (arguments = c.arguments).length, k = 0; k < length; ++k) {
                        final CommandArgument a = arguments[k];
                        buf.append(' ');
                        if (a.name.length() != 0) {
                            buf.append(ChatColor.RED);
                            buf.append(Locale.get("command.info." + a.name, locale));
                        }
                        buf.append(a.isConst() ? ChatColor.GREEN : ChatColor.GOLD);
                        buf.append(a.printable(locale));
                    }
                    sender.sendMessage(buf.toString());
                }
                sender.sendMessage(ChatColor.GREEN + Locale.get("message.command.info", locale));
            }
            else {
                sender.sendMessage(ChatColor.RED + Locale.get("message.error.permission", locale));
            }
            return;
        }
        final ArrayList<String> args = new ArrayList<String>();
        while (true) {
            while (com.length() != 0) {
                boolean quote = false;
                int end;
                if (com.charAt(0) == '`') {
                    com = com.substring(1);
                    end = com.indexOf(96);
                    quote = true;
                }
                else {
                    end = com.indexOf(32);
                }
                if (end == -1) {
                    args.add(com);
                }
                else {
                    args.add(com.substring(0, end));
                }
                if (quote) {
                    com = com.substring(end + 1);
                    end = com.indexOf(32);
                }
                if (end == -1) {
                    CommandError lastError = null;
                Label_1148:
                    for (final CommandDef c2 : command) {
                        if (c2.arguments.length != args.size()) {
                            if (c2.arguments.length == 0 || !(c2.arguments[c2.arguments.length - 1] instanceof ArgumentString)) {
                                continue;
                            }
                            if (args.size() < c2.arguments.length) {
                                continue;
                            }
                        }
                        final ArrayList<Object> outArgs = new ArrayList<Object>();
                        outArgs.add(sender);
                        for (int i = 0; i < c2.arguments.length; ++i) {
                            final CommandArgument a2 = c2.arguments[i];
                            if (!a2.isConst()) {
                                if (i == c2.arguments.length - 1 && a2 instanceof ArgumentString) {
                                    final StringBuilder joined = new StringBuilder();
                                    for (int j = i; j < args.size(); ++j) {
                                        joined.append(args.get(j)).append(' ');
                                    }
                                    args.set(i, joined.toString().trim());
                                }
                                final Object res = a2.parse(args.get(i), locale);
                                if (res instanceof CommandError) {
                                    lastError = (CommandError)res;
                                    continue Label_1148;
                                }
                                outArgs.add(res);
                            }
                            else {
                                final ArgumentConst cst = (ArgumentConst)a2;
                                if (!cst.value.equals(args.get(i))) {
                                    continue Label_1148;
                                }
                            }
                        }
                        try {
                            if (c2.handlePermissions || sender.hasPermission("rpgitem")) {
                                c2.method.invoke(c2.handler, outArgs.toArray());
                            }
                            else {
                                sender.sendMessage(ChatColor.RED + Locale.get("message.error.permission", locale));
                            }
                        }
                        catch (IllegalAccessException e4) {
                            e4.printStackTrace();
                        }
                        catch (IllegalArgumentException e5) {
                            e5.printStackTrace();
                        }
                        catch (InvocationTargetException e6) {
                            e6.printStackTrace();
                        }
                        return;
                    }
                    if (sender.hasPermission("rpgitem")) {
                        if (lastError != null) {
                            sender.sendMessage(ChatColor.RED + String.format(Locale.get("message.error.command", locale), lastError.error));
                        }
                        else {
                            final ArrayList<String> consts = new ArrayList<String>();
                            for (final CommandDef c3 : command) {
                                for (int i = 0; i < c3.arguments.length; ++i) {
                                    if (i >= args.size()) {
                                        break;
                                    }
                                    final CommandArgument a2 = c3.arguments[i];
                                    if (!a2.isConst()) {
                                        if (i == c3.arguments.length - 1 && a2 instanceof ArgumentString) {
                                            final StringBuilder joined = new StringBuilder();
                                            for (int j = i; j < args.size(); ++j) {
                                                joined.append(args.get(j)).append(' ');
                                            }
                                            args.set(i, joined.toString().trim());
                                        }
                                        final Object res = a2.parse(args.get(i), locale);
                                        if (res instanceof CommandError) {
                                            lastError = (CommandError)res;
                                            break;
                                        }
                                    }
                                    else {
                                        final ArgumentConst cst = (ArgumentConst)a2;
                                        if (!cst.value.equals(args.get(i))) {
                                            break;
                                        }
                                        consts.add(cst.value);
                                    }
                                }
                            }
                            final StringBuilder search = new StringBuilder();
                            for (final String term : consts) {
                                search.append(term).append(' ');
                            }
                            searchHelp(sender, search.toString());
                        }
                    }
                    else {
                        sender.sendMessage(ChatColor.RED + Locale.get("message.error.permission", locale));
                    }
                    return;
                }
                com = com.substring(end + 1);
            }
            continue;
        }
    }
    
    public static List<String> complete(final CommandSender sender, String com) {
        com = com.trim();
        if (com.length() == 0) {
            return new ArrayList<String>();
        }
        final int pos = com.indexOf(32);
        String comName;
        if (pos == -1) {
            comName = com;
        }
        else {
            comName = com.substring(0, pos);
        }
        com = com.substring(pos + 1);
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final ArrayList<CommandDef> command = Commands.commands.get(comName);
        if (command != null) {
            final ArrayList<String> args = new ArrayList<String>();
            while (true) {
                while (com.length() != 0) {
                    boolean quote = false;
                    int end;
                    if (com.charAt(0) == '`') {
                        com = com.substring(1);
                        end = com.indexOf(96);
                        quote = true;
                    }
                    else {
                        end = com.indexOf(32);
                    }
                    if (end == -1) {
                        args.add(com);
                    }
                    else {
                        args.add(com.substring(0, end));
                    }
                    if (quote) {
                        com = com.substring(end + 1);
                        end = com.indexOf(32);
                    }
                    if (end == -1) {
                        final HashMap<String, Boolean> out = new HashMap<String, Boolean>();
                        for (final CommandDef c : command) {
                            for (int i = 0; i < c.arguments.length; ++i) {
                                final CommandArgument a = c.arguments[i];
                                if (i == args.size() - 1) {
                                    final List<String> res = a.tabComplete(args.get(i));
                                    if (res != null) {
                                        for (final String s : res) {
                                            out.put(s, true);
                                        }
                                        break;
                                    }
                                }
                                else if (!a.isConst()) {
                                    final Object res2 = a.parse(args.get(i), locale);
                                    if (res2 instanceof CommandError) {
                                        break;
                                    }
                                }
                                else {
                                    final ArgumentConst cst = (ArgumentConst)a;
                                    if (!cst.value.equals(args.get(i))) {
                                        break;
                                    }
                                }
                            }
                        }
                        final ArrayList<String> outList = new ArrayList<String>();
                        for (final String s2 : out.keySet()) {
                            outList.add(s2);
                        }
                        return outList;
                    }
                    com = com.substring(end + 1);
                }
                continue;
            }
        }
        if (pos == -1) {
            final ArrayList<String> out2 = new ArrayList<String>();
            for (final String n : Commands.commands.keySet()) {
                if (n.startsWith(comName)) {
                    out2.add("/" + n);
                }
            }
            return out2;
        }
        return new ArrayList<String>();
    }
    
    public static void register(final CommandHandler handler) {
        final Method[] methods = handler.getClass().getMethods();
        Method[] array;
        for (int length = (array = methods).length, i = 0; i < length; ++i) {
            final Method method = array[i];
            final Class[] params = method.getParameterTypes();
            final CommandString comString = method.getAnnotation(CommandString.class);
            if (comString != null) {
                if (params.length == 0 || !params[0].isAssignableFrom(CommandSender.class)) {
                    throw new RuntimeException("First argument must be CommandSender @ " + method.getName());
                }
                add(comString.value(), method, handler);
            }
        }
        final Collection<ArrayList<CommandDef>> coms = Commands.commands.values();
        for (final ArrayList<CommandDef> c : coms) {
            Collections.sort(c);
        }
    }
    
    private static void add(String com, final Method method, final CommandHandler handler) {
        com = com.trim();
        int pos = com.indexOf(32);
        String comName;
        if (pos == -1) {
            comName = com;
        }
        else {
            comName = com.substring(0, pos);
        }
        final CommandDef def = new CommandDef();
        def.commandString = com;
        def.method = method;
        def.handler = handler;
        final Class[] params = method.getParameterTypes();
        if (method.isAnnotationPresent(CommandDocumentation.class)) {
            def.documentation = method.getAnnotation(CommandDocumentation.class).value();
        }
        else {
            def.documentation = "";
        }
        if (method.isAnnotationPresent(CommandGroup.class)) {
            def.sortKey = method.getAnnotation(CommandGroup.class).value();
        }
        else {
            def.sortKey = "";
        }
        final CommandString comString = method.getAnnotation(CommandString.class);
        def.handlePermissions = comString.handlePermissions();
        if (!Commands.commands.containsKey(comName)) {
            Commands.commands.put(comName, new ArrayList<CommandDef>());
        }
        Commands.commands.get(comName).add(def);
        if (pos == -1) {
            def.arguments = new CommandArgument[0];
            return;
        }
        com = com.substring(pos + 1);
        final ArrayList<CommandArgument> arguments = new ArrayList<CommandArgument>();
        int realArgumentsCount = 0;
        do {
            pos = com.indexOf(32);
            String a;
            if (pos == -1) {
                a = com;
            }
            else {
                a = com.substring(0, pos);
                com = com.substring(pos + 1);
            }
            if (a.charAt(0) == '$') {
                String name = "";
                if (a.contains(":")) {
                    final String[] as = a.split(":");
                    name = as[0].substring(1);
                    a = "$" + as[1];
                }
                final char t = a.charAt(1);
                final Class<? extends CommandArgument> cAT = Commands.argTypes.get(t);
                if (cAT == null) {
                    throw new RuntimeException("Invalid command argument type " + t);
                }
                try {
                    final CommandArgument arg = (CommandArgument)cAT.newInstance();
                    arg.init(a.substring(3, a.length() - 1));
                    if (!params[realArgumentsCount + 1].isAssignableFrom(arg.getType())) {
                        throw new RuntimeException("Type mismatch for " + method.getName());
                    }
                    arg.name = name;
                    arguments.add(arg);
                    ++realArgumentsCount;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                arguments.add(new ArgumentConst(a));
            }
        } while (pos != -1);
        if (params.length != realArgumentsCount + 1) {
            throw new RuntimeException("Argument count mis-match for " + method.getName());
        }
        arguments.toArray(def.arguments = new CommandArgument[arguments.size()]);
    }
    
    public static void searchHelp(final CommandSender sender, final String terms) {
        if (terms.equalsIgnoreCase("_genhelp")) {
            for (final String locale : Locale.getLocales()) {
                generateHelp(locale);
            }
            return;
        }
        String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        sender.sendMessage(ChatColor.GREEN + String.format(Locale.get("message.help.for", locale), terms));
        final String[] term = terms.toLowerCase().split(" ");
        for (final Map.Entry<String, ArrayList<CommandDef>> command : Commands.commands.entrySet()) {
            for (final CommandDef c : command.getValue()) {
                int count = 0;
                String[] array;
                for (int length = (array = term).length, j = 0; j < length; ++j) {
                    final String t = array[j];
                    if (c.commandString.toLowerCase().contains(t)) {
                        ++count;
                    }
                }
                if (count == term.length) {
                    final StringBuilder buf = new StringBuilder();
                    buf.append(ChatColor.GREEN).append(ChatColor.BOLD).append('/').append(command.getKey());
                    CommandArgument[] arguments;
                    for (int length2 = (arguments = c.arguments).length, k = 0; k < length2; ++k) {
                        final CommandArgument a = arguments[k];
                        buf.append(' ');
                        if (a.name.length() != 0) {
                            buf.append(ChatColor.RED).append(ChatColor.BOLD);
                            buf.append(Locale.get("command.info." + a.name, locale));
                        }
                        buf.append(a.isConst() ? ChatColor.GREEN : ChatColor.GOLD).append(ChatColor.BOLD);
                        buf.append(a.printable(locale));
                    }
                    sender.sendMessage(buf.toString());
                    String docStr = c.documentation;
                    if (docStr.charAt(0) == '$') {
                        if (docStr.contains("+")) {
                            final String[] dArgs = docStr.split("\\+");
                            docStr = Locale.get(dArgs[0].substring(1), locale);
                            if (dArgs[1].equalsIgnoreCase("PotionEffectType")) {
                                final StringBuilder out = new StringBuilder();
                                PotionEffectType[] values;
                                for (int length3 = (values = PotionEffectType.values()).length, n = 0; n < length3; ++n) {
                                    final PotionEffectType type = values[n];
                                    if (type != null) {
                                        out.append(type.getName().toLowerCase()).append(", ");
                                    }
                                }
                                docStr = String.valueOf(docStr) + out.toString();
                            }
                        }
                        else {
                            docStr = Locale.get(docStr.substring(1), locale);
                        }
                    }
                    docStr = docStr.replaceAll("@", new StringBuilder().append(ChatColor.BLUE).toString()).replaceAll("#", new StringBuilder().append(ChatColor.WHITE).toString());
                    final StringBuilder docBuf = new StringBuilder();
                    final char[] chars = docStr.toCharArray();
                    docBuf.append(ChatColor.WHITE);
                    for (int i = 0; i < chars.length; ++i) {
                        char l = chars[i];
                        if (l == '&') {
                            ++i;
                            l = chars[i];
                            switch (l) {
                                case '0': {
                                    docBuf.append(ChatColor.BLACK);
                                    break;
                                }
                                case '1': {
                                    docBuf.append(ChatColor.DARK_BLUE);
                                    break;
                                }
                                case '2': {
                                    docBuf.append(ChatColor.DARK_GREEN);
                                    break;
                                }
                                case '3': {
                                    docBuf.append(ChatColor.DARK_AQUA);
                                    break;
                                }
                                case '4': {
                                    docBuf.append(ChatColor.DARK_RED);
                                    break;
                                }
                                case '5': {
                                    docBuf.append(ChatColor.DARK_PURPLE);
                                    break;
                                }
                                case '6': {
                                    docBuf.append(ChatColor.GOLD);
                                    break;
                                }
                                case '7': {
                                    docBuf.append(ChatColor.GRAY);
                                    break;
                                }
                                case '8': {
                                    docBuf.append(ChatColor.DARK_GRAY);
                                    break;
                                }
                                case '9': {
                                    docBuf.append(ChatColor.BLUE);
                                    break;
                                }
                                case 'a': {
                                    docBuf.append(ChatColor.GREEN);
                                    break;
                                }
                                case 'b': {
                                    docBuf.append(ChatColor.AQUA);
                                    break;
                                }
                                case 'c': {
                                    docBuf.append(ChatColor.RED);
                                    break;
                                }
                                case 'd': {
                                    docBuf.append(ChatColor.LIGHT_PURPLE);
                                    break;
                                }
                                case 'e': {
                                    docBuf.append(ChatColor.YELLOW);
                                    break;
                                }
                                case 'f': {
                                    docBuf.append(ChatColor.WHITE);
                                    break;
                                }
                                case 'r': {
                                    docBuf.append(ChatColor.WHITE);
                                    break;
                                }
                            }
                        }
                        else {
                            docBuf.append(l);
                        }
                    }
                    sender.sendMessage(docBuf.toString());
                }
            }
        }
    }
    
    private static HashMap<String, String> getMap() {
        final HashMap<String, String> langMap = new HashMap<String, String>();
        langMap.put("en_US", "English (US)");
        BufferedReader r = null;
        try {
            r = new BufferedReader(new InputStreamReader(Plugin.plugin.getResource("languages.txt"), "UTF-8"));
            String line = null;
            while ((line = r.readLine()) != null) {
                final String[] args = line.split("=");
                langMap.put(args[0], args[1]);
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        finally {
            try {
                r.close();
            }
            catch (IOException e3) {
                e3.printStackTrace();
            }
        }
        try {
            r.close();
        }
        catch (IOException e3) {
            e3.printStackTrace();
        }
        return langMap;
    }
    
    public static void generateHelp(final String locale) {
        BufferedWriter w = null;
        final HashMap<String, String> langMap = getMap();
        try {
            final File out = new File(Plugin.plugin.getDataFolder(), String.valueOf(Calendar.getInstance().get(1)) + "-" + Calendar.getInstance().get(2) + "-" + Calendar.getInstance().get(5) + "-" + locale + ".md");
            if (out.exists()) {
                out.delete();
            }
            w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out), "UTF-8"));
            w.write("---\n");
            w.write("layout: locale\n");
            w.write("title: " + langMap.get(locale) + "\n");
            w.write("permalink: " + locale + ".html\n");
            w.write("---\n");
            for (final Map.Entry<String, ArrayList<CommandDef>> command : Commands.commands.entrySet()) {
                w.write(String.format("## Commands /%s ", command.getKey()));
                w.write("\n\n");
                for (final CommandDef c : command.getValue()) {
                    final StringBuilder buf = new StringBuilder();
                    buf.append("### /");
                    buf.append(command.getKey()).append(" ");
                    CommandArgument[] arguments;
                    for (int length = (arguments = c.arguments).length, j = 0; j < length; ++j) {
                        final CommandArgument a = arguments[j];
                        if (a.name.length() != 0) {
                            buf.append("<span style='color:#006EFF'>");
                            buf.append(Locale.get("command.info." + a.name, locale));
                            buf.append("</span>");
                        }
                        if (a.isConst()) {
                            buf.append("<span style='color:#b5e853'>");
                        }
                        else {
                            buf.append("<span style='color:#1BE0BF'>");
                        }
                        buf.append(a.printable(locale));
                        buf.append("</span> ");
                    }
                    buf.append("\n");
                    String docStr = c.documentation;
                    if (docStr.charAt(0) == '$') {
                        if (docStr.contains("+")) {
                            final String[] dArgs = docStr.split("\\+");
                            docStr = Locale.get(dArgs[0].substring(1), locale);
                            if (dArgs[1].equalsIgnoreCase("PotionEffectType")) {
                                final StringBuilder out2 = new StringBuilder();
                                PotionEffectType[] values;
                                for (int length2 = (values = PotionEffectType.values()).length, k = 0; k < length2; ++k) {
                                    final PotionEffectType type = values[k];
                                    if (type != null) {
                                        out2.append(type.getName().toLowerCase()).append(", ");
                                    }
                                }
                                docStr = String.valueOf(docStr) + out2.toString();
                            }
                        }
                        else {
                            docStr = Locale.get(docStr.substring(1), locale);
                        }
                    }
                    docStr = docStr.replaceAll("#", "</span>").replaceAll("@", "<span style='color:#0000ff'>").replaceAll("`", "`` ` ``");
                    final StringBuilder docBuf = new StringBuilder();
                    final char[] chars = docStr.toCharArray();
                    for (int i = 0; i < chars.length; ++i) {
                        char l = chars[i];
                        if (l == '&') {
                            ++i;
                            l = chars[i];
                            if (l != 'r') {
                                docBuf.append("<span style='color:#");
                            }
                            switch (l) {
                                case '0': {
                                    docBuf.append("000000");
                                    break;
                                }
                                case '1': {
                                    docBuf.append("0000aa");
                                    break;
                                }
                                case '2': {
                                    docBuf.append("00aa00");
                                    break;
                                }
                                case '3': {
                                    docBuf.append("00aaaa");
                                    break;
                                }
                                case '4': {
                                    docBuf.append("aa0000");
                                    break;
                                }
                                case '5': {
                                    docBuf.append("aa00aa");
                                    break;
                                }
                                case '6': {
                                    docBuf.append("ffaa00");
                                    break;
                                }
                                case '7': {
                                    docBuf.append("aaaaaa");
                                    break;
                                }
                                case '8': {
                                    docBuf.append("555555");
                                    break;
                                }
                                case '9': {
                                    docBuf.append("5555ff");
                                    break;
                                }
                                case 'a': {
                                    docBuf.append("55ff55");
                                    break;
                                }
                                case 'b': {
                                    docBuf.append("55ffff");
                                    break;
                                }
                                case 'c': {
                                    docBuf.append("ff5555");
                                    break;
                                }
                                case 'd': {
                                    docBuf.append("ff55ff");
                                    break;
                                }
                                case 'e': {
                                    docBuf.append("ffff55");
                                    break;
                                }
                                case 'f': {
                                    docBuf.append("ffffff");
                                    break;
                                }
                                case 'r': {
                                    docBuf.append("'></span'");
                                    break;
                                }
                            }
                            docBuf.append("'>");
                        }
                        else {
                            docBuf.append(l);
                        }
                    }
                    buf.append(docBuf.toString());
                    buf.append("\n\n");
                    w.write(buf.toString());
                }
            }
            w.write("\n\n");
            w.write("Generated at: ");
            final SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.setTimeZone(new SimpleTimeZone(0, "GMT"));
            sdf.applyPattern("dd MMM yyyy HH:mm:ss z");
            w.write(sdf.format(new Date()));
        }
        catch (IOException e) {
            e.printStackTrace();
            try {
                w.close();
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
            return;
        }
        finally {
            try {
                w.close();
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        try {
            w.close();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
