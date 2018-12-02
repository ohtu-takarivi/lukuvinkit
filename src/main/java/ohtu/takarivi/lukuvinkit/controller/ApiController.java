package ohtu.takarivi.lukuvinkit.controller;

import ohtu.takarivi.lukuvinkit.forms.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * The Spring controller for API-related activity, such as fetching the title of a page.
 */
@Controller
public class ApiController {

    /**
     * The page that fetches the title and description of a remote URL.
     *
     * @param auth An Authentication object representing the currently authenticated user.
     * @param url The URL to get the information from; this is a GET parameter.
     * @return The text returned by this API endpoint.
     */
    @GetMapping("/api/getLinkInfo")
    @ResponseBody
    public String getLinkInfo(Authentication auth, @RequestParam String url) {
        if (!LinkAddForm.isValidURL(url)) {
            return "";
        }
        
        InputStream response = null;
        
        try {
            System.setProperty("http.agent", "");
            URLConnection conn = new URL(url).openConnection();
            
            // we have to fake the user agent since some pages do not like the automatically generated one
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
            response = conn.getInputStream();
            
            String responseBody = new BufferedReader(new InputStreamReader(response)).lines().collect(Collectors.joining("\n"));
            String responseBodyLower = responseBody.toLowerCase();
            
            String title = "";
            String description = "";

            // get the text between title tags if ones exist
            if (responseBodyLower.contains("<title>")) {
                int titleStart = responseBodyLower.indexOf("<title>") + "<title>".length();
                title = responseBody.substring(titleStart, responseBodyLower.indexOf("</title>", titleStart)).replace('\n', ' ');
            }

            System.out.println("desc check");
            // get the text inside the "content" of a meta description if one exists
            if (responseBodyLower.contains("<meta name=\"description\" content=\"")) {
                System.out.println("meta with name quotes");
                int descStart = responseBodyLower.indexOf("<meta name=\"description\" content=\"") + "<meta name=\"description\" content=\"".length();
                description = responseBody.substring(descStart, responseBodyLower.indexOf("\">", descStart)).replace('\n', ' ');
                System.out.println("found desc : " + description);
            } else if (responseBodyLower.contains("<meta name=description content=\"")) {
                System.out.println("meta without name quotes");
                int descStart = responseBodyLower.indexOf("<meta name=description content=\"") + "<meta name=description content=\"".length();
                description = responseBody.substring(descStart, responseBodyLower.indexOf("\">", descStart)).replace('\n', ' ');
                System.out.println("found desc : " + description);
            }
            
            return title + "\n" + description;
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
}
