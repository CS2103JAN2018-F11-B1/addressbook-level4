package seedu.address.commons.core;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;




/**
 * Created by davidten on 3/11/18.
 */
public class Oauth2Client {

    private static HttpURLConnection con;
    private static final String response_type = "code";
    private static final String client_id = "code";

    public static void requestAuthenticationCode() throws IOException {
        String url = "https://www.linkedin.com/oauth/v2/authorization";

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setRequestMethod("GET");

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            System.out.println(content.toString());

        } finally {
            con.disconnect();
        }

    }


}
