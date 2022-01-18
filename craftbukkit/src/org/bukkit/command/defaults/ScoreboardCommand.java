// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import com.google.common.collect.ImmutableMap;
import java.util.Comparator;
import java.util.Collections;
import com.google.common.collect.ImmutableList;
import org.bukkit.util.StringUtil;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import java.util.Iterator;
import java.util.Set;
import org.bukkit.scoreboard.Scoreboard;
import java.util.HashSet;
import org.bukkit.entity.Player;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Score;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import java.util.Collection;
import org.bukkit.scoreboard.Objective;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import java.util.Map;
import java.util.List;

public class ScoreboardCommand extends VanillaCommand
{
    private static final List<String> MAIN_CHOICES;
    private static final List<String> OBJECTIVES_CHOICES;
    private static final List<String> OBJECTIVES_CRITERIA;
    private static final List<String> PLAYERS_CHOICES;
    private static final List<String> TEAMS_CHOICES;
    private static final List<String> TEAMS_OPTION_CHOICES;
    private static final Map<String, DisplaySlot> OBJECTIVES_DISPLAYSLOT;
    private static final Map<String, ChatColor> TEAMS_OPTION_COLOR;
    private static final List<String> BOOLEAN;
    
    public ScoreboardCommand() {
        super("scoreboard");
        this.description = "Scoreboard control";
        this.usageMessage = "/scoreboard";
        this.setPermission("bukkit.command.scoreboard");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length < 1 || args[0].length() == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /scoreboard <objectives|players|teams>");
            return false;
        }
        final Scoreboard mainScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        if (args[0].equalsIgnoreCase("objectives")) {
            if (args.length == 1) {
                sender.sendMessage(ChatColor.RED + "Usage: /scoreboard objectives <list|add|remove|setdisplay>");
                return false;
            }
            if (args[1].equalsIgnoreCase("list")) {
                final Set<Objective> objectives = mainScoreboard.getObjectives();
                if (objectives.isEmpty()) {
                    sender.sendMessage(ChatColor.RED + "There are no objectives on the scoreboard");
                    return false;
                }
                sender.sendMessage(ChatColor.DARK_GREEN + "Showing " + objectives.size() + " objective(s) on scoreboard");
                for (final Objective objective : objectives) {
                    sender.sendMessage("- " + objective.getName() + ": displays as '" + objective.getDisplayName() + "' and is type '" + objective.getCriteria() + "'");
                }
            }
            else if (args[1].equalsIgnoreCase("add")) {
                if (args.length < 4) {
                    sender.sendMessage(ChatColor.RED + "/scoreboard objectives add <name> <criteriaType> [display name ...]");
                    return false;
                }
                final String name = args[2];
                final String criteria = args[3];
                if (criteria == null) {
                    sender.sendMessage(ChatColor.RED + "Invalid objective criteria type. Valid types are: " + stringCollectionToString(ScoreboardCommand.OBJECTIVES_CRITERIA));
                }
                else if (name.length() > 16) {
                    sender.sendMessage(ChatColor.RED + "The name '" + name + "' is too long for an objective, it can be at most 16 characters long");
                }
                else if (mainScoreboard.getObjective(name) != null) {
                    sender.sendMessage(ChatColor.RED + "An objective with the name '" + name + "' already exists");
                }
                else {
                    String displayName = null;
                    if (args.length > 4) {
                        displayName = StringUtils.join(ArrayUtils.subarray(args, 4, args.length), ' ');
                        if (displayName.length() > 32) {
                            sender.sendMessage(ChatColor.RED + "The name '" + displayName + "' is too long for an objective, it can be at most 32 characters long");
                            return false;
                        }
                    }
                    final Objective objective2 = mainScoreboard.registerNewObjective(name, criteria);
                    if (displayName != null && displayName.length() > 0) {
                        objective2.setDisplayName(displayName);
                    }
                    sender.sendMessage("Added new objective '" + name + "' successfully");
                }
            }
            else if (args[1].equalsIgnoreCase("remove")) {
                if (args.length != 3) {
                    sender.sendMessage(ChatColor.RED + "/scoreboard objectives remove <name>");
                    return false;
                }
                final String name = args[2];
                final Objective objective3 = mainScoreboard.getObjective(name);
                if (objective3 == null) {
                    sender.sendMessage(ChatColor.RED + "No objective was found by the name '" + name + "'");
                }
                else {
                    objective3.unregister();
                    sender.sendMessage("Removed objective '" + name + "' successfully");
                }
            }
            else if (args[1].equalsIgnoreCase("setdisplay")) {
                if (args.length != 3 && args.length != 4) {
                    sender.sendMessage(ChatColor.RED + "/scoreboard objectives setdisplay <slot> [objective]");
                    return false;
                }
                final String slotName = args[2];
                final DisplaySlot slot = ScoreboardCommand.OBJECTIVES_DISPLAYSLOT.get(slotName);
                if (slot == null) {
                    sender.sendMessage(ChatColor.RED + "No such display slot '" + slotName + "'");
                }
                else if (args.length == 4) {
                    final String objectiveName = args[3];
                    final Objective objective2 = mainScoreboard.getObjective(objectiveName);
                    if (objective2 == null) {
                        sender.sendMessage(ChatColor.RED + "No objective was found by the name '" + objectiveName + "'");
                        return false;
                    }
                    objective2.setDisplaySlot(slot);
                    sender.sendMessage("Set the display objective in slot '" + slotName + "' to '" + objective2.getName() + "'");
                }
                else {
                    final Objective objective = mainScoreboard.getObjective(slot);
                    if (objective != null) {
                        objective.setDisplaySlot(null);
                    }
                    sender.sendMessage("Cleared objective display slot '" + slotName + "'");
                }
            }
        }
        else if (args[0].equalsIgnoreCase("players")) {
            if (args.length == 1) {
                sender.sendMessage(ChatColor.RED + "/scoreboard players <set|add|remove|reset|list>");
                return false;
            }
            if (args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove")) {
                if (args.length != 5) {
                    if (args[1].equalsIgnoreCase("set")) {
                        sender.sendMessage(ChatColor.RED + "/scoreboard players set <player> <objective> <score>");
                    }
                    else if (args[1].equalsIgnoreCase("add")) {
                        sender.sendMessage(ChatColor.RED + "/scoreboard players add <player> <objective> <count>");
                    }
                    else {
                        sender.sendMessage(ChatColor.RED + "/scoreboard players remove <player> <objective> <count>");
                    }
                    return false;
                }
                final String objectiveName2 = args[3];
                final Objective objective3 = mainScoreboard.getObjective(objectiveName2);
                if (objective3 == null) {
                    sender.sendMessage(ChatColor.RED + "No objective was found by the name '" + objectiveName2 + "'");
                    return false;
                }
                if (!objective3.isModifiable()) {
                    sender.sendMessage(ChatColor.RED + "The objective '" + objectiveName2 + "' is read-only and cannot be set");
                    return false;
                }
                final String valueString = args[4];
                int value;
                try {
                    value = Integer.parseInt(valueString);
                }
                catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "'" + valueString + "' is not a valid number");
                    return false;
                }
                if (value < 1 && !args[1].equalsIgnoreCase("set")) {
                    sender.sendMessage(ChatColor.RED + "The number you have entered (" + value + ") is too small, it must be at least 1");
                    return false;
                }
                final String playerName = args[2];
                if (playerName.length() > 16) {
                    sender.sendMessage(ChatColor.RED + "'" + playerName + "' is too long for a player name");
                    return false;
                }
                final Score score = objective3.getScore(Bukkit.getOfflinePlayer(playerName));
                int newScore;
                if (args[1].equalsIgnoreCase("set")) {
                    newScore = value;
                }
                else if (args[1].equalsIgnoreCase("add")) {
                    newScore = score.getScore() + value;
                }
                else {
                    newScore = score.getScore() - value;
                }
                score.setScore(newScore);
                sender.sendMessage("Set score of " + objectiveName2 + " for player " + playerName + " to " + newScore);
            }
            else if (args[1].equalsIgnoreCase("reset")) {
                if (args.length != 3) {
                    sender.sendMessage(ChatColor.RED + "/scoreboard players reset <player>");
                    return false;
                }
                final String playerName2 = args[2];
                if (playerName2.length() > 16) {
                    sender.sendMessage(ChatColor.RED + "'" + playerName2 + "' is too long for a player name");
                    return false;
                }
                mainScoreboard.resetScores(Bukkit.getOfflinePlayer(playerName2));
                sender.sendMessage("Reset all scores of player " + playerName2);
            }
            else if (args[1].equalsIgnoreCase("list")) {
                if (args.length > 3) {
                    sender.sendMessage(ChatColor.RED + "/scoreboard players list <player>");
                    return false;
                }
                if (args.length == 2) {
                    final Set<OfflinePlayer> players = mainScoreboard.getPlayers();
                    if (players.isEmpty()) {
                        sender.sendMessage(ChatColor.RED + "There are no tracked players on the scoreboard");
                    }
                    else {
                        sender.sendMessage(ChatColor.DARK_GREEN + "Showing " + players.size() + " tracked players on the scoreboard");
                        sender.sendMessage(offlinePlayerSetToString(players));
                    }
                }
                else {
                    final String playerName2 = args[2];
                    if (playerName2.length() > 16) {
                        sender.sendMessage(ChatColor.RED + "'" + playerName2 + "' is too long for a player name");
                        return false;
                    }
                    final Set<Score> scores = mainScoreboard.getScores(Bukkit.getOfflinePlayer(playerName2));
                    if (scores.isEmpty()) {
                        sender.sendMessage(ChatColor.RED + "Player " + playerName2 + " has no scores recorded");
                    }
                    else {
                        sender.sendMessage(ChatColor.DARK_GREEN + "Showing " + scores.size() + " tracked objective(s) for " + playerName2);
                        for (final Score score2 : scores) {
                            sender.sendMessage("- " + score2.getObjective().getDisplayName() + ": " + score2.getScore() + " (" + score2.getObjective().getName() + ")");
                        }
                    }
                }
            }
        }
        else {
            if (!args[0].equalsIgnoreCase("teams")) {
                sender.sendMessage(ChatColor.RED + "Usage: /scoreboard <objectives|players|teams>");
                return false;
            }
            if (args.length == 1) {
                sender.sendMessage(ChatColor.RED + "/scoreboard teams <list|add|remove|empty|join|leave|option>");
                return false;
            }
            if (args[1].equalsIgnoreCase("list")) {
                if (args.length == 2) {
                    final Set<Team> teams = mainScoreboard.getTeams();
                    if (teams.isEmpty()) {
                        sender.sendMessage(ChatColor.RED + "There are no teams registered on the scoreboard");
                    }
                    else {
                        sender.sendMessage(ChatColor.DARK_GREEN + "Showing " + teams.size() + " teams on the scoreboard");
                        for (final Team team : teams) {
                            sender.sendMessage("- " + team.getName() + ": '" + team.getDisplayName() + "' has " + team.getSize() + " players");
                        }
                    }
                }
                else {
                    if (args.length != 3) {
                        sender.sendMessage(ChatColor.RED + "/scoreboard teams list [name]");
                        return false;
                    }
                    final String teamName = args[2];
                    final Team team2 = mainScoreboard.getTeam(teamName);
                    if (team2 == null) {
                        sender.sendMessage(ChatColor.RED + "No team was found by the name '" + teamName + "'");
                    }
                    else {
                        final Set<OfflinePlayer> players2 = team2.getPlayers();
                        if (players2.isEmpty()) {
                            sender.sendMessage(ChatColor.RED + "Team " + team2.getName() + " has no players");
                        }
                        else {
                            sender.sendMessage(ChatColor.DARK_GREEN + "Showing " + players2.size() + " player(s) in team " + team2.getName());
                            sender.sendMessage(offlinePlayerSetToString(players2));
                        }
                    }
                }
            }
            else if (args[1].equalsIgnoreCase("add")) {
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "/scoreboard teams add <name> [display name ...]");
                    return false;
                }
                final String name = args[2];
                if (name.length() > 16) {
                    sender.sendMessage(ChatColor.RED + "The name '" + name + "' is too long for a team, it can be at most 16 characters long");
                }
                else if (mainScoreboard.getTeam(name) != null) {
                    sender.sendMessage(ChatColor.RED + "A team with the name '" + name + "' already exists");
                }
                else {
                    String displayName2 = null;
                    if (args.length > 3) {
                        displayName2 = StringUtils.join(ArrayUtils.subarray(args, 3, args.length), ' ');
                        if (displayName2.length() > 32) {
                            sender.sendMessage(ChatColor.RED + "The display name '" + displayName2 + "' is too long for a team, it can be at most 32 characters long");
                            return false;
                        }
                    }
                    final Team team = mainScoreboard.registerNewTeam(name);
                    if (displayName2 != null && displayName2.length() > 0) {
                        team.setDisplayName(displayName2);
                    }
                    sender.sendMessage("Added new team '" + team.getName() + "' successfully");
                }
            }
            else if (args[1].equalsIgnoreCase("remove")) {
                if (args.length != 3) {
                    sender.sendMessage(ChatColor.RED + "/scoreboard teams remove <name>");
                    return false;
                }
                final String name = args[2];
                final Team team2 = mainScoreboard.getTeam(name);
                if (team2 == null) {
                    sender.sendMessage(ChatColor.RED + "No team was found by the name '" + name + "'");
                }
                else {
                    team2.unregister();
                    sender.sendMessage("Removed team " + name);
                }
            }
            else if (args[1].equalsIgnoreCase("empty")) {
                if (args.length != 3) {
                    sender.sendMessage(ChatColor.RED + "/scoreboard teams clear <name>");
                    return false;
                }
                final String name = args[2];
                final Team team2 = mainScoreboard.getTeam(name);
                if (team2 == null) {
                    sender.sendMessage(ChatColor.RED + "No team was found by the name '" + name + "'");
                }
                else {
                    final Set<OfflinePlayer> players2 = team2.getPlayers();
                    if (players2.isEmpty()) {
                        sender.sendMessage(ChatColor.RED + "Team " + team2.getName() + " is already empty, cannot remove nonexistant players");
                    }
                    else {
                        for (final OfflinePlayer player : players2) {
                            team2.removePlayer(player);
                        }
                        sender.sendMessage("Removed all " + players2.size() + " player(s) from team " + team2.getName());
                    }
                }
            }
            else if (args[1].equalsIgnoreCase("join")) {
                Label_3592: {
                    if (sender instanceof Player) {
                        if (args.length >= 3) {
                            break Label_3592;
                        }
                    }
                    else if (args.length >= 4) {
                        break Label_3592;
                    }
                    sender.sendMessage(ChatColor.RED + "/scoreboard teams join <team> [player...]");
                    return false;
                }
                final String teamName = args[2];
                final Team team2 = mainScoreboard.getTeam(teamName);
                if (team2 == null) {
                    sender.sendMessage(ChatColor.RED + "No team was found by the name '" + teamName + "'");
                }
                else {
                    final Set<String> addedPlayers = new HashSet<String>();
                    if (sender instanceof Player && args.length == 3) {
                        team2.addPlayer((OfflinePlayer)sender);
                        addedPlayers.add(sender.getName());
                    }
                    else {
                        for (int i = 3; i < args.length; ++i) {
                            final String playerName = args[i];
                            final Player player2 = Bukkit.getPlayerExact(playerName);
                            OfflinePlayer offlinePlayer;
                            if (player2 != null) {
                                offlinePlayer = player2;
                            }
                            else {
                                offlinePlayer = Bukkit.getOfflinePlayer(playerName);
                            }
                            team2.addPlayer(offlinePlayer);
                            addedPlayers.add(offlinePlayer.getName());
                        }
                    }
                    sender.sendMessage("Added " + addedPlayers.size() + " player(s) to team " + team2.getName() + ": " + stringCollectionToString(addedPlayers));
                }
            }
            else if (args[1].equalsIgnoreCase("leave")) {
                if (!(sender instanceof Player) && args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "/scoreboard teams leave [player...]");
                    return false;
                }
                final Set<String> left = new HashSet<String>();
                final Set<String> noTeam = new HashSet<String>();
                if (sender instanceof Player && args.length == 2) {
                    final Team team = mainScoreboard.getPlayerTeam((OfflinePlayer)sender);
                    if (team != null) {
                        team.removePlayer((OfflinePlayer)sender);
                        left.add(sender.getName());
                    }
                    else {
                        noTeam.add(sender.getName());
                    }
                }
                else {
                    for (int j = 2; j < args.length; ++j) {
                        final String playerName3 = args[j];
                        final Player player3 = Bukkit.getPlayerExact(playerName3);
                        OfflinePlayer offlinePlayer2;
                        if (player3 != null) {
                            offlinePlayer2 = player3;
                        }
                        else {
                            offlinePlayer2 = Bukkit.getOfflinePlayer(playerName3);
                        }
                        final Team team3 = mainScoreboard.getPlayerTeam(offlinePlayer2);
                        if (team3 != null) {
                            team3.removePlayer(offlinePlayer2);
                            left.add(offlinePlayer2.getName());
                        }
                        else {
                            noTeam.add(offlinePlayer2.getName());
                        }
                    }
                }
                if (!left.isEmpty()) {
                    sender.sendMessage("Removed " + left.size() + " player(s) from their teams: " + stringCollectionToString(left));
                }
                if (!noTeam.isEmpty()) {
                    sender.sendMessage("Could not remove " + noTeam.size() + " player(s) from their teams: " + stringCollectionToString(noTeam));
                }
            }
            else if (args[1].equalsIgnoreCase("option")) {
                if (args.length != 4 && args.length != 5) {
                    sender.sendMessage(ChatColor.RED + "/scoreboard teams option <team> <friendlyfire|color|seefriendlyinvisibles> <value>");
                    return false;
                }
                final String teamName = args[2];
                final Team team2 = mainScoreboard.getTeam(teamName);
                if (team2 == null) {
                    sender.sendMessage(ChatColor.RED + "No team was found by the name '" + teamName + "'");
                    return false;
                }
                final String option = args[3].toLowerCase();
                if (!option.equals("friendlyfire") && !option.equals("color") && !option.equals("seefriendlyinvisibles")) {
                    sender.sendMessage(ChatColor.RED + "/scoreboard teams option <team> <friendlyfire|color|seefriendlyinvisibles> <value>");
                    return false;
                }
                if (args.length == 4) {
                    if (option.equals("color")) {
                        sender.sendMessage(ChatColor.RED + "Valid values for option color are: " + stringCollectionToString(ScoreboardCommand.TEAMS_OPTION_COLOR.keySet()));
                    }
                    else {
                        sender.sendMessage(ChatColor.RED + "Valid values for option " + option + " are: true and false");
                    }
                }
                else {
                    final String value2 = args[4].toLowerCase();
                    if (option.equals("color")) {
                        final ChatColor color = ScoreboardCommand.TEAMS_OPTION_COLOR.get(value2);
                        if (color == null) {
                            sender.sendMessage(ChatColor.RED + "Valid values for option color are: " + stringCollectionToString(ScoreboardCommand.TEAMS_OPTION_COLOR.keySet()));
                            return false;
                        }
                        team2.setPrefix(color.toString());
                        team2.setSuffix(ChatColor.RESET.toString());
                    }
                    else {
                        if (!value2.equals("true") && !value2.equals("false")) {
                            sender.sendMessage(ChatColor.RED + "Valid values for option " + option + " are: true and false");
                            return false;
                        }
                        if (option.equals("friendlyfire")) {
                            team2.setAllowFriendlyFire(value2.equals("true"));
                        }
                        else {
                            team2.setCanSeeFriendlyInvisibles(value2.equals("true"));
                        }
                    }
                    sender.sendMessage("Set option " + option + " for team " + team2.getName() + " to " + value2);
                }
            }
        }
        return true;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], ScoreboardCommand.MAIN_CHOICES, new ArrayList<String>());
        }
        if (args.length > 1) {
            if (args[0].equalsIgnoreCase("objectives")) {
                if (args.length == 2) {
                    return StringUtil.copyPartialMatches(args[1], ScoreboardCommand.OBJECTIVES_CHOICES, new ArrayList<String>());
                }
                if (args[1].equalsIgnoreCase("add")) {
                    if (args.length == 4) {
                        return StringUtil.copyPartialMatches(args[3], ScoreboardCommand.OBJECTIVES_CRITERIA, new ArrayList<String>());
                    }
                }
                else if (args[1].equalsIgnoreCase("remove")) {
                    if (args.length == 3) {
                        return StringUtil.copyPartialMatches(args[2], this.getCurrentObjectives(), new ArrayList<String>());
                    }
                }
                else if (args[1].equalsIgnoreCase("setdisplay")) {
                    if (args.length == 3) {
                        return StringUtil.copyPartialMatches(args[2], ScoreboardCommand.OBJECTIVES_DISPLAYSLOT.keySet(), new ArrayList<String>());
                    }
                    if (args.length == 4) {
                        return StringUtil.copyPartialMatches(args[3], this.getCurrentObjectives(), new ArrayList<String>());
                    }
                }
            }
            else if (args[0].equalsIgnoreCase("players")) {
                if (args.length == 2) {
                    return StringUtil.copyPartialMatches(args[1], ScoreboardCommand.PLAYERS_CHOICES, new ArrayList<String>());
                }
                if (args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove")) {
                    if (args.length == 3) {
                        return super.tabComplete(sender, alias, args);
                    }
                    if (args.length == 4) {
                        return StringUtil.copyPartialMatches(args[3], this.getCurrentObjectives(), new ArrayList<String>());
                    }
                }
                else if (args.length == 3) {
                    return StringUtil.copyPartialMatches(args[2], this.getCurrentPlayers(), new ArrayList<String>());
                }
            }
            else if (args[0].equalsIgnoreCase("teams")) {
                if (args.length == 2) {
                    return StringUtil.copyPartialMatches(args[1], ScoreboardCommand.TEAMS_CHOICES, new ArrayList<String>());
                }
                if (args[1].equalsIgnoreCase("join")) {
                    if (args.length == 3) {
                        return StringUtil.copyPartialMatches(args[2], this.getCurrentTeams(), new ArrayList<String>());
                    }
                    if (args.length >= 4) {
                        return super.tabComplete(sender, alias, args);
                    }
                }
                else {
                    if (args[1].equalsIgnoreCase("leave")) {
                        return super.tabComplete(sender, alias, args);
                    }
                    if (args[1].equalsIgnoreCase("option")) {
                        if (args.length == 3) {
                            return StringUtil.copyPartialMatches(args[2], this.getCurrentTeams(), new ArrayList<String>());
                        }
                        if (args.length == 4) {
                            return StringUtil.copyPartialMatches(args[3], ScoreboardCommand.TEAMS_OPTION_CHOICES, new ArrayList<String>());
                        }
                        if (args.length == 5) {
                            if (args[3].equalsIgnoreCase("color")) {
                                return StringUtil.copyPartialMatches(args[4], ScoreboardCommand.TEAMS_OPTION_COLOR.keySet(), new ArrayList<String>());
                            }
                            return StringUtil.copyPartialMatches(args[4], ScoreboardCommand.BOOLEAN, new ArrayList<String>());
                        }
                    }
                    else if (args.length == 3) {
                        return StringUtil.copyPartialMatches(args[2], this.getCurrentTeams(), new ArrayList<String>());
                    }
                }
            }
        }
        return (List<String>)ImmutableList.of();
    }
    
    private static String offlinePlayerSetToString(final Set<OfflinePlayer> set) {
        final StringBuilder string = new StringBuilder();
        String lastValue = null;
        for (final OfflinePlayer value : set) {
            string.append(lastValue = value.getName()).append(", ");
        }
        string.delete(string.length() - 2, Integer.MAX_VALUE);
        if (string.length() != lastValue.length()) {
            string.insert(string.length() - lastValue.length(), "and ");
        }
        return string.toString();
    }
    
    private static String stringCollectionToString(final Collection<String> set) {
        final StringBuilder string = new StringBuilder();
        String lastValue = null;
        for (final String value : set) {
            string.append(lastValue = value).append(", ");
        }
        string.delete(string.length() - 2, Integer.MAX_VALUE);
        if (string.length() != lastValue.length()) {
            string.insert(string.length() - lastValue.length(), "and ");
        }
        return string.toString();
    }
    
    private List<String> getCurrentObjectives() {
        final List<String> list = new ArrayList<String>();
        for (final Objective objective : Bukkit.getScoreboardManager().getMainScoreboard().getObjectives()) {
            list.add(objective.getName());
        }
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        return list;
    }
    
    private List<String> getCurrentPlayers() {
        final List<String> list = new ArrayList<String>();
        for (final OfflinePlayer player : Bukkit.getScoreboardManager().getMainScoreboard().getPlayers()) {
            list.add(player.getName());
        }
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        return list;
    }
    
    private List<String> getCurrentTeams() {
        final List<String> list = new ArrayList<String>();
        for (final Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()) {
            list.add(team.getName());
        }
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        return list;
    }
    
    static {
        MAIN_CHOICES = ImmutableList.of("objectives", "players", "teams");
        OBJECTIVES_CHOICES = ImmutableList.of("list", "add", "remove", "setdisplay");
        OBJECTIVES_CRITERIA = ImmutableList.of("health", "playerKillCount", "totalKillCount", "deathCount", "dummy");
        PLAYERS_CHOICES = ImmutableList.of("set", "add", "remove", "reset", "list");
        TEAMS_CHOICES = ImmutableList.of("add", "remove", "join", "leave", "empty", "list", "option");
        TEAMS_OPTION_CHOICES = ImmutableList.of("color", "friendlyfire", "seeFriendlyInvisibles");
        OBJECTIVES_DISPLAYSLOT = ImmutableMap.of("belowName", DisplaySlot.BELOW_NAME, "list", DisplaySlot.PLAYER_LIST, "sidebar", DisplaySlot.SIDEBAR);
        TEAMS_OPTION_COLOR = ImmutableMap.builder().put("aqua", ChatColor.AQUA).put("black", ChatColor.BLACK).put("blue", ChatColor.BLUE).put("bold", ChatColor.BOLD).put("dark_aqua", ChatColor.DARK_AQUA).put("dark_blue", ChatColor.DARK_BLUE).put("dark_gray", ChatColor.DARK_GRAY).put("dark_green", ChatColor.DARK_GREEN).put("dark_purple", ChatColor.DARK_PURPLE).put("dark_red", ChatColor.DARK_RED).put("gold", ChatColor.GOLD).put("gray", ChatColor.GRAY).put("green", ChatColor.GREEN).put("italic", ChatColor.ITALIC).put("light_purple", ChatColor.LIGHT_PURPLE).put("obfuscated", ChatColor.MAGIC).put("red", ChatColor.RED).put("reset", ChatColor.RESET).put("strikethrough", ChatColor.STRIKETHROUGH).put("underline", ChatColor.UNDERLINE).put("white", ChatColor.WHITE).put("yellow", ChatColor.YELLOW).build();
        BOOLEAN = ImmutableList.of("true", "false");
    }
}
