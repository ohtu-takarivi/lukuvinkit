package ohtu.takarivi.lukuvinkit.controller;

import static ohtu.takarivi.lukuvinkit.forms.FormUtils.isValidURL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

import org.json.JSONException;
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

        try {
            String responseBody = getHtmlContentsOf(url);
            String responseBodyLower = responseBody.toLowerCase();
            
            String title = "";
            String description = "";
    
            // get the text between title tags if ones exist
            if (responseBodyLower.contains("<title>")) {
                title = getTitleFromHtml(responseBody);
            }
    
            // get the text inside the "content" of a meta description if one exists
            if (responseBodyLower.contains("<meta name=\"description\" content=\"")) {
                description = getDescriptionFromHtml(responseBody);
            }
            
            JSONObject obj = new JSONObject();
            obj.put("title", title);
            obj.put("description", description);
            return obj.toString();
        } catch (JSONException | IOException e) {
            return "";
        }
    }

    /**
     * Gets the HTML page source from a given URL.
     * 
     * @param url The URL to get the HTML contents of.
     * @return The HTML contents.
     * @throws IOException
     */
    private String getHtmlContentsOf(String url) throws IOException {
        InputStream response = null;
        
        // we have to fake the user agent since some pages do not like the automatically generated one
        System.setProperty("http.agent", "");
        
        URLConnection conn = new URL(url).openConnection();
        
        conn.setRequestProperty("User-Agent", USER_AGENT);
        response = conn.getInputStream();
        
        return new BufferedReader(new InputStreamReader(response)).lines().collect(Collectors.joining("\n"));
    }

    /**
     * Gets the title between the &lt;title>...&lt;/title> tags from the HTML source. The tags must be guaranteed to exist.
     *
     * @param html The HTML source.
     * @return The title between &lt;title>...&lt;/title> tags.
     */
    private String getTitleFromHtml(String html) {
        String htmlLower = html.toLowerCase();
        int titleStart = htmlLower.indexOf("<title>") + "<title>".length();
        return html.substring(titleStart, htmlLower.indexOf("</title>", titleStart)).replace('\n', ' ');
    }

    /**
     * Gets the description from the value of "content" in the &lt;meta name="description" content="..."> tag from the HTML source. The tag must be guaranteed to exist.
     *
     * @param html The HTML source.
     * @return The "content" value of the between &lt;meta name="description" content="..."> tag.
     */
    private String getDescriptionFromHtml(String html) {
        String htmlLower = html.toLowerCase();
        int descStart = htmlLower.indexOf("<meta name=\"description\" content=\"") + "<meta name=\"description\" content=\"".length();
        return html.substring(descStart, htmlLower.indexOf("\"", descStart)).replace('\n', ' ');
    }
}
