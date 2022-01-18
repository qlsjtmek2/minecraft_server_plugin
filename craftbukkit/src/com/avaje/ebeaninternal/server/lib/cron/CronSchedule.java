// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.cron;

import java.util.Calendar;
import java.util.StringTokenizer;

public class CronSchedule
{
    private String schedule;
    private boolean[] bHours;
    private boolean[] bMinutes;
    private boolean[] bMonths;
    private boolean[] bDaysOfWeek;
    private boolean[] bDaysOfMonth;
    
    public CronSchedule(final String scheduleLine) {
        this.setSchedule(scheduleLine);
    }
    
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof CronSchedule) {
            final CronSchedule cs = (CronSchedule)obj;
            if (this.schedule.equals(cs.getSchedule())) {
                return true;
            }
        }
        return false;
    }
    
    public int hashCode() {
        int hc = CronSchedule.class.getName().hashCode();
        hc = hc * 31 + this.schedule.hashCode();
        return hc;
    }
    
    private void initBooleanArrays() {
        this.bHours = new boolean[24];
        this.bMinutes = new boolean[60];
        this.bMonths = new boolean[12];
        this.bDaysOfWeek = new boolean[7];
        this.bDaysOfMonth = new boolean[31];
        for (int i = 0; i < 60; ++i) {
            if (i < 24) {
                this.bHours[i] = false;
            }
            if (i < 60) {
                this.bMinutes[i] = false;
            }
            if (i < 12) {
                this.bMonths[i] = false;
            }
            if (i < 7) {
                this.bDaysOfWeek[i] = false;
            }
            if (i < 31) {
                this.bDaysOfMonth[i] = false;
            }
        }
    }
    
    public void setSchedule(final String schedule) {
        this.schedule = schedule;
        this.initBooleanArrays();
        final StringTokenizer tokenizer = new StringTokenizer(schedule);
        final int numTokens = tokenizer.countTokens();
        int i = 0;
        while (tokenizer.hasMoreElements()) {
            final String token = tokenizer.nextToken();
            switch (i) {
                case 0: {
                    this.parseToken(token, this.bMinutes, false);
                    break;
                }
                case 1: {
                    this.parseToken(token, this.bHours, false);
                    break;
                }
                case 2: {
                    this.parseToken(token, this.bDaysOfMonth, true);
                    break;
                }
                case 3: {
                    this.parseToken(token, this.bMonths, true);
                    break;
                }
                case 4: {
                    this.parseToken(token, this.bDaysOfWeek, false);
                }
                case 5: {}
            }
            ++i;
        }
        if (numTokens < 5) {
            final String msg = "The schedule[" + schedule + "] did not contain enough tokens (5 required) [" + numTokens + "].";
            throw new RuntimeException(msg);
        }
    }
    
    private void parseToken(final String token, final boolean[] arrayBool, final boolean bBeginInOne) {
        try {
            if (token.equals("*")) {
                for (int i = 0; i < arrayBool.length; ++i) {
                    arrayBool[i] = true;
                }
                return;
            }
            int index = token.indexOf(",");
            if (index > 0) {
                final StringTokenizer tokenizer = new StringTokenizer(token, ",");
                while (tokenizer.hasMoreTokens()) {
                    this.parseToken(tokenizer.nextToken(), arrayBool, bBeginInOne);
                }
                return;
            }
            index = token.indexOf("-");
            if (index > 0) {
                int start = Integer.parseInt(token.substring(0, index));
                int end = Integer.parseInt(token.substring(index + 1));
                if (bBeginInOne) {
                    --start;
                    --end;
                }
                for (int j = start; j <= end; ++j) {
                    arrayBool[j] = true;
                }
                return;
            }
            index = token.indexOf("/");
            if (index > 0) {
                for (int each = Integer.parseInt(token.substring(index + 1)), k = 0; k < arrayBool.length; k += each) {
                    arrayBool[k] = true;
                }
                return;
            }
            int iValue = Integer.parseInt(token);
            if (bBeginInOne) {
                --iValue;
            }
            arrayBool[iValue] = true;
        }
        catch (Exception e) {
            final String msg = "The schedule[" + this.schedule + "] had a problem parsing a token [" + token + "].";
            throw new RuntimeException(msg, e);
        }
    }
    
    public boolean isScheduledToRunNow(final Calendar thisMinute) {
        return this.bHours[thisMinute.get(11)] && this.bMinutes[thisMinute.get(12)] && this.bMonths[thisMinute.get(2)] && this.bDaysOfWeek[thisMinute.get(7) - 1] && this.bDaysOfMonth[thisMinute.get(5) - 1];
    }
    
    public String getSchedule() {
        return this.schedule;
    }
}
