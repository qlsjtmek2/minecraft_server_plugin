/*    */ package com.marquez.marsk.Events;
/*    */ 
import ch.njol.skript.lang.SkriptEvent;

import ch.njol.skript.lang.Literal;

import org.bukkit.event.Event;

import javax.annotation.Nullable;

import ch.njol.util.Checker;

import org.bukkit.event.player.PlayerMoveEvent;

import ch.njol.skript.lang.SkriptParser;

import com.marquez.marsk.AreaFile;

import ch.njol.skript.Skript;

import org.bukkit.entity.Player;

import com.marquez.marsk.Locations;

import java.util.ArrayList;

import java.util.List;
/*    */ 
public class EvtEnterArea extends SkriptEvent

{

    private Literal<String> area;

    

    public String toString(@Nullable final Event arg0, final boolean arg1) {

        return new StringBuilder("on enter area at ").append(this.area).toString();

    }

    

    public boolean check(final Event arg0) {

        return this.area == null || this.area.check(arg0, new Checker<String>() {

            public boolean check(final String area) {

                return EvtEnterArea.this.LocContains(area, ((PlayerMoveEvent)arg0).getPlayer());

            }

        });

    }

    

    public boolean init(final Literal[] arg0, final int arg1, final SkriptParser.ParseResult arg2) {

        final String area = (String)arg0[0].getSingle();

        if (AreaFile.findArea(area) == -1) {

            Skript.error("'" + arg0[0] + "' \uc740(\ub294) \uc874\uc7ac\ud558\uc9c0 \uc54a\ub294 \uc774\ub984\uc785\ub2c8\ub2e4.");

            return false;

        }

        this.area = arg0[0];

        return true;

    }

    

    public boolean LocContains(final String area, final Player p) {

        if (Locations.playerArea.get(p) == null) {

            Locations.playerArea.put(p, new ArrayList());

        }

        if (AreaFile.findArea(area) == -1 || ((List<E>)Locations.playerArea.get(p)).contains(area)) {

            return false;

        }

        if (Locations.isInPosition(new Locations(AreaFile.foundArea(area)), p.getLocation(), p.getWorld())) {

            final List<String> arealist = (List<E>)Locations.playerArea.get(p);

            arealist.add(area);

            Locations.playerArea.put(p, arealist);

            return true;

        }

        return false;

    }

}