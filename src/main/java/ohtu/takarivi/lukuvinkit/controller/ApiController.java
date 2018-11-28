package ohtu.takarivi.lukuvinkit.controller;

import ohtu.takarivi.lukuvinkit.forms.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
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
     * The page that fetches the title of a remote URL.
     *
     * @param auth         An Authentication object representing the currently authenticated user.
     * @return The action to be taken by this controller.
     */
    @GetMapping("/api/getTitle")
    @ResponseBody
    public String getTitle(Authentication auth, @RequestParam String url) {
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
            
            if (responseBodyLower.contains("<title>")) {
                int titleStart = responseBodyLower.indexOf("<title>") + "<title>".length();
                title = responseBody.substring(titleStart, responseBodyLower.indexOf("</title>", titleStart)).replace('\n', ' ');
            }
            
            if (responseBodyLower.contains("<meta name=\"description\" content=\"")) {
                int descStart = responseBodyLower.indexOf("<meta name=\"description\" content=\"") + "<meta name=\"description\" content=\"".length();
                description = responseBody.substring(descStart, responseBodyLower.indexOf("\">", descStart)).replace('\n', ' ');
            } else if (responseBodyLower.contains("<meta name=description content=\"")) {
                int descStart = responseBodyLower.indexOf("<meta name=description content=\"") + "<meta name=description content=\"".length();
                description = responseBody.substring(descStart, responseBodyLower.indexOf("\">", descStart)).replace('\n', ' ');
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
