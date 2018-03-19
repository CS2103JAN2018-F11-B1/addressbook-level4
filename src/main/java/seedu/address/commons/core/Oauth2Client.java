package seedu.address.commons.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javafx.application.Platform;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import seedu.address.logic.Decrypter;
import seedu.address.ui.BrowserWindow;
import seedu.address.commons.events.ui.HideBrowserRequestEvent;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


/**
 * Class that handles the OAuth2 call with LiknedIn
 * Acts as the client in the client-server scheme
 */
public class Oauth2Client {
    static BrowserWindow bWindow;
    static String secret;
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

    public static void getLinkedInS(){
        Decrypter a = new Decrypter();
        try {
            secret = a.getLinkedInS();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        try {
            getAccessToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method exchanges the authorization token for an accessToken
     */
    public static void getAccessToken() throws IOException {
        Logger logger = LogsCenter.getLogger(Oauth2Client.class);
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("https://www.linkedin.com/oauth/v2/accessToken");

        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("param-1", "12345"));
        params.add(new BasicNameValuePair("param-2", "Hello!"));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            InputStream instream = entity.getContent();
            try {
                // do something useful
                logger.info("RESPONSE IS " + response.toString());
                logger.info("RESPONSE IS " + response.getEntity().toString());
                logger.info("RESPONSE IS " + response.getAllHeaders().toString());
            } finally {
                instream.close();
            }
        }



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

        }
    }

}
