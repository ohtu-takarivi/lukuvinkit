package ohtu.takarivi.lukuvinkit.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;

import ohtu.takarivi.lukuvinkit.SpringBootTestBase;

@DirtiesContext
public class CustomUserControllerTest extends SpringBootTestBase {

    @Test
    public void indexNoRedirectIfAuthenticated() throws Exception {
        UserDetails user = userDetailsService.loadUserByUsername("nolla");
        mvc.perform(get("/").with(csrf()).with(user(user))).andExpect(status().isOk());
    }

    @Test
    public void defaultMappingIfAuthenticated() throws Exception {
        UserDetails user = userDetailsService.loadUserByUsername("nolla");
        mvc.perform(get("/test").with(csrf()).with(user(user))).andExpect(redirectedUrl("/"));
    }

    @Test
    public void defaultMappingIfNotAuthenticated() throws Exception {
        mvc.perform(get("/test")).andExpect(redirectedUrlPattern("**/login"));
    }

}
