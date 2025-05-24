package com.springpoc.webmvc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WelcomeController.class)
class WelcomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void greet_shouldReturnGreetingWithHostname() throws Exception {
        // Simulate the HOSTNAME env var (you could also mock System.getenv but not easily)
        String hostname = System.getenv().getOrDefault("HOSTNAME", "UNKNOWN");

        mockMvc.perform(get("/demo"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(hostname)))
                .andExpect(content().string(containsString("Welcome to Simple Demo Application!")));
    }
}
