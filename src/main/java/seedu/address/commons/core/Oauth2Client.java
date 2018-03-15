package seedu.address.commons.core;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.InetSocketAddress;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;


public class Oauth2Client {

    private static HttpURLConnection con;
    private static final String response_type = "code";
    private static final String client_id = "code";

    public static void startServer() throws IOException {
        int port = 13370;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
