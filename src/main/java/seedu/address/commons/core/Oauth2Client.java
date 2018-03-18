package seedu.address.commons.core;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javafx.application.Platform;
import seedu.address.ui.BrowserWindow;
import seedu.address.commons.events.ui.HideBrowserRequestEvent;


/**
 * Class that handles the OAuth2 call with LiknedIn
 * Acts as the client in the client-server scheme
 */
public class Oauth2Client {
    static BrowserWindow bWindow;
    /**
     * Called when user types Linkedin_login
     * starts a webserver and opens a browser for Linkedin Authorization
     */
    public static void authenticateWithLinkedIn() throws IOException {
        try {
            startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Config config = new Config();
        String clientId = config.getAppId();

        String urlString = "https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id="
            + clientId + "&redirect_uri=http://127.0.0.1:13370/test&state=123";

        bWindow = new BrowserWindow(urlString);
        bWindow.show();

    }

    /**
     * Starts a webserver and allows it to expect a response at the context specified
     */
    public static void startServer() throws IOException {
        int port = 13370;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null);
        server.start();
    }

    /**
     * Closes a browser window.
     * Platform.runLater method is needed to avoid 'Not on FX application thread' error
     */
    public static void closeBrowser(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                bWindow.hide();
            }
        });


    }

    /**
     * Allows the server to handle responses made to the context created in startServer
     */
    static class MyHandler implements HttpHandler {
        private final Logger logger = LogsCenter.getLogger(Oauth2Client.class);

        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            logger.info("RECEIVED A RESPONSE FROM THE SERVER: " + t.getRequestURI().getQuery());
            //t.getRequestURI().getQuery() receives the response from the server. Need to parse it
            EventsCenter.getInstance().post(new HideBrowserRequestEvent());
            //bWindow.hide(); //this does not work becaue there is no event
        }
    }

}
