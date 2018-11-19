package ohtu.takarivi.lukuvinkit.controller;

import ohtu.takarivi.lukuvinkit.SpringBootTestControllerBase;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomUserControllerTest extends SpringBootTestControllerBase {

    @Test
    public void indexNoRedirectIfAuthenticated() throws Exception {
        UserDetails user = userDetailsService.loadUserByUsername("user1");
        mvc.perform(get("/").with(csrf()).with(user(user))).andExpect(status().isOk());
    }

    @Test
    public void defaultMappingIfAuthenticated() throws Exception {
        UserDetails user = userDetailsService.loadUserByUsername("user1");
        mvc.perform(get("/test").with(csrf()).with(user(user))).andExpect(status().is3xxRedirection());
    }

}
