// 
// Decompiled by Procyon v0.5.30
// 

package com.survivorserver.GlobalMarket;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonFactory;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerHandler extends Thread
{
    Market market;
    private Socket socket;
    MarketServer server;
    
    public ServerHandler(final MarketServer server, final Socket socket, final Market market) {
        this.server = server;
        this.market = market;
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try {
            this.handleClient();
            this.socket.close();
        }
        catch (Exception e) {
            this.market.log.warning("Could not handle client: " + e.getMessage());
        }
    }
    
    public void handleClient() throws Exception {
        final BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        final String recieved = in.readLine();
        final Map<String, Object> reply = new HashMap<String, Object>();
        final ObjectMapper mapper = new ObjectMapper();
        final JsonFactory factory = mapper.getFactory();
        JsonParser parser = null;
        try {
            parser = factory.createJsonParser(recieved);
        }
        catch (Exception e) {
            reply.put("failure", "JSON error");
        }
        if (parser != null) {
            JsonNode node = null;
            try {
                node = (JsonNode)mapper.readTree(parser);
            }
            catch (Exception e2) {
                reply.put("failure", "JSON error");
            }
            if (node != null) {
                final String function = node.get("function").asText();
                final List<String> args = (List<String>)node.findValuesAsText("args");
                if (function.equalsIgnoreCase("getAllListings")) {
                    reply.put("success", this.server.storage.getAllListings());
                }
                else if (function.equalsIgnoreCase("getSessionId")) {
                    reply.put("success", this.server.getSessionId(args.get(0)));
                }
                else if (function.equalsIgnoreCase("generateSessionId")) {
                    reply.put("success", this.server.generateSessionId(args.get(0)));
                }
                else if (function.equalsIgnoreCase("getAllMail")) {
                    reply.put("success", this.server.storage.getAllMailFor(args.get(0)));
                }
                else if (function.equalsIgnoreCase("getBalance")) {
                    reply.put("success", this.market.getEcon().getBalance((String)args.get(0)));
                }
                else if (function.equalsIgnoreCase("format")) {
                    reply.put("success", this.market.getEcon().format(Double.parseDouble(args.get(0))));
                }
                else if (function.equalsIgnoreCase("doPoll")) {
                    final WebViewer viewer = this.server.addViewer(args.get(0));
                    final long started = System.currentTimeMillis();
                    while (System.currentTimeMillis() - started <= 29500L) {
                        if (!viewer.getVersionId().toString().equalsIgnoreCase(this.server.currentVersion().toString())) {
                            viewer.setVersionId(this.server.currentVersion());
                            reply.put("success", "doRefresh");
                            break;
                        }
                    }
                    if (reply.isEmpty()) {
                        reply.put("failure", "No changes");
                    }
                    viewer.updateLastSeen();
                }
                else {
                    reply.put("failure", "Function " + function + " not found");
                }
            }
        }
        this.write(reply);
    }
    
    public void write(final Map<String, Object> reply) throws IOException {
        final PrintWriter writer = new PrintWriter(this.socket.getOutputStream());
        writer.println(new ObjectMapper().writeValueAsString((Object)reply));
        writer.flush();
    }
}
