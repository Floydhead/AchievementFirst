import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CallGithubAPI {

    /**
     * Public method to get response from the github API for given username and type.
     *
     * @param username User input username.
     * @param type     User decided parameter.
     * @return String representation of JSON URL response.
     */
    public String getResponse(String username, String type) {
        if (type == null) {
            type = "owner";
        }
        return getResponseHelper(username, type);
    }

    /**
     * A private helper method to get a string representation of github URL response for given username
     * and type of access.
     *
     * @param username The username of individual who is querying, ie, Floydhead in my case
     * @param type     A choice between all or owner or member repositories, default being owner.
     * @return
     */
    private String getResponseHelper(String username, String type) {
        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();
        int status = 0;

        try {
            /**
             * Establishing Connection to the github URL.
             */
            if (username != null) {
                URL url = new URL("https://api.github.com/users/" + username + "/repos?type=" + type);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                status = connection.getResponseCode();
            }
            if (status == 200) {
                /**
                 * Getting and storing response from github URL response.
                 */
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String URLResponse;
                while ((URLResponse = bufferedReader.readLine()) != null) {
                    response.append(URLResponse);
                }
            }
            connection.disconnect();

        } catch (MalformedURLException e) {
            System.out.println("Incorrect URL");
        } catch (IOException e) {
            System.out.println("URL Return Unexpected");
        }
        return response.toString();
    }

    /**
     * Public response function which uses the URL response to create a JSON array representation.
     *
     * @param URLResponse JSON array in string format.
     * @return JSONArray format of URL response.
     */
    public JSONArray getJSONResponse(String URLResponse) {
        JSONArray repos = null;
        if (URLResponse != null) {
            repos = getJSONResponseHelper(URLResponse);
        }
        return repos;
    }

    /**
     * Private helper method to get a JSON response from the URL response.
     *
     * @param URLResponse JSON array in string format.
     * @return JSON array format of URL response.
     */
    private JSONArray getJSONResponseHelper(String URLResponse) {
        try {
            JSONArray jsonArray = new JSONArray(new JSONTokener(URLResponse));
            return jsonArray;
        } catch (JSONException je) {
            System.out.println("No such user found");
            return null;
        }
    }

    /**
     * Public display method to output the projects belonging to user's repository.
     *
     * @param jsonArray input JSON array for the printing of user repos.
     */
    public void display(JSONArray jsonArray) {
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                System.out.println(jsonArray.getJSONObject(i).get("name"));
            }
        }
    }
}
