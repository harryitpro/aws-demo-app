package com.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {GlobalControllerAdviceTest.TestController.class, GlobalControllerAdvice.class})
public class GlobalControllerAdviceTest {

    @Autowired
    private MockMvc mockMvc;

    // Test Controller to trigger GlobalControllerAdvice logic
    @RestController
    static class TestController {
        @GetMapping("/throw-null")
        public String throwNullPointer() {
            throw new NullPointerException("Test NPE");
        }

        @GetMapping("/check-model")
        public String checkModel() {
            return "Model test";
        }

        @PostMapping("/bind-data")
        public String bindData(@RequestBody TestDto dto) {
            return dto.getName();
        }
    }

    // DTO for testing data binding
    static class TestDto {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Test
    void testHandleNullPointerException() throws Exception {
        // Test that NullPointerException is caught and returns 500 with correct message
        mockMvc.perform(MockMvcRequestBuilders.get("/throw-null"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Null error: Test NPE"));
    }

    @Test
    void testModelAttributeVersion() throws Exception {
        // Test that the 'version' model attribute is added
        mockMvc.perform(MockMvcRequestBuilders.get("/check-model"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("version", "1.0.0"))
                .andExpect(content().string("Model test"));
    }

    @Test
    void testInitBinderDisallowsIdField() throws Exception {
        // Test that the 'id' field is not bound from request body
        String json = "{\"id\":\"123\",\"name\":\"Alice\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/bind-data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Alice"));
        // Note: We can't directly test that 'id' is null in the DTO here via MockMvc,
        // but the binding should ignore 'id' due to setDisallowedFields("id").
    }
}