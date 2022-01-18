// 
// Decompiled by Procyon v0.5.30
// 

package com.bpo.service;

import java.util.Iterator;
import com.google.android.gcm.server.MulticastResult;
import org.json.simple.JSONArray;
import java.io.IOException;
import com.google.android.gcm.server.Result;
import java.util.List;
import com.bpo.secure.AES128Cipher;
import java.net.URLEncoder;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import java.util.ArrayList;
import com.bpo.calladmin.CallAdminEntry;

public class GCMService
{
    public static void push(final String player, final String type, final String info) {
        final JSONArray list = CallAdminEntry.io.getRegID();
        final String str = "[" + type + "]" + player + " : " + info;
        if (list == null) {
            return;
        }
        if (list.size() == 0) {
            return;
        }
        final List<String> lists = new ArrayList<String>();
        for (int i = 0; i < list.size(); ++i) {
            lists.add(list.get(i).toString());
        }
        try {
            final Sender sender = new Sender("AIzaSyCiGD6_WTC9_ksuzuS2WZk7FAbKlY3rv38");
            final Message message = new Message.Builder().addData("msg", AES128Cipher.encrypt(URLEncoder.encode(str, "EUC-KR"))).build();
            final MulticastResult multiResult = sender.send(message, (List)lists, 5);
            if (multiResult != null) {
                final List<Result> resultList = (List<Result>)multiResult.getResults();
                int j = 0;
                for (final Result result : resultList) {
                    if (result.getMessageId() == null || result.getMessageId().equals("null")) {
                        CallAdminEntry.io.removeRegID(lists.get(j));
                    }
                    ++j;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
