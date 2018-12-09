package ohtu.takarivi.lukuvinkit.controller;

import static ohtu.takarivi.lukuvinkit.forms.FormUtils.isValidURL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The Spring controller for API-related activity, such as fetching the title of a page.
 */
@Controller
public class ApiController {
    
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36";

    /**
     * The page that fetches the title and description of a remote URL.
     *
     * @param auth An Authentication object representing the currently authenticated user.
     * @param url The URL to get the information from; this is a GET parameter.
     * @return The JSON text returned by this API endpoint.
     */
    @GetMapping("/api/getLinkInfo")
    @ResponseBody
    public String getLinkInfo(Authentication auth, @RequestParam String url) {
        if (!isValidURL(url)) {
            return "";
        }
        
        InputStream response = null;
        
        try {
         // we have to fake the user agent since some pages do not like the automatically generated one
            System.setProperty("http.agent", "");
            
            URLConnection conn = new URL(url).openConnection();
            
            conn.setRequestProperty("User-Agent", USER_AGENT);
            response = conn.getInputStream();
            
            String responseBody = new BufferedReader(new InputStreamReader(response)).lines().collect(Collectors.joining("\n"));
            String responseBodyLower = responseBody.toLowerCase();
            
            String title = "";
            String description = "";

            // get the text between title tags if ones exist
            if (responseBodyLower.contains("<title>")) {
                title = setTitle(responseBodyLower, responseBody);
            }

            // get the text inside the "content" of a meta description if one exists
            if (responseBodyLower.contains("<meta name=\"description\" content=\"")) {
                description = setDescription(responseBodyLower, responseBody);
            }
            
            JSONObject obj = new JSONObject();
            obj.put("title", title);
            obj.put("description", description);
            return obj.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String setDescription(String responseBodyLower, String responseBody) {
        int descStart = responseBodyLower.indexOf("<meta name=\"description\" content=\"") + "<meta name=\"description\" content=\"".length();
        return responseBody.substring(descStart, responseBodyLower.indexOf("\"", descStart)).replace('\n', ' ');
    }

    private String setTitle(String responseBodyLower, String responseBody) {
        int titleStart = responseBodyLower.indexOf("<title>") + "<title>".length();
        return responseBody.substring(titleStart, responseBodyLower.indexOf("</title>", titleStart)).replace('\n', ' ');
    }
}
