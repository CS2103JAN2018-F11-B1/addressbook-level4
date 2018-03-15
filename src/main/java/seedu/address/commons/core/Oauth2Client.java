package seedu.address.commons.core;

import seedu.address.ui.BrowserWindow;
import seedu.address.commons.core.Config;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;

import java.net.InetSocketAddress;
import java.net.HttpURLConnection;



public class Oauth2Client {

    private static HttpURLConnection con;
    private static final String response_type = "code";

    public static void authenticateWithLinkedIn() throws IOException {
        try {
            startServer();
        } catch (IOException e) { //if enters here, it is probably because the server has already started
            e.printStackTrace();
        }

        Config config = new Config();
        String client_id = config.getAppId();

        String urlString = "https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id="+
                client_id+"&redirect_uri=http://127.0.0.1:13370&state=123";

        BrowserWindow bWindow = new BrowserWindow(urlString);
        bWindow.show();


    }
/*
    public static void openBrowser(){
        URI myUri = URI.create(urlString);
        try {
            java.awt.Desktop.getDesktop().browse(myUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

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
