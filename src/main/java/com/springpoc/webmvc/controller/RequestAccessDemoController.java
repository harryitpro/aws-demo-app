package com.springpoc.webmvc.controller;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/demo")
public class RequestAccessDemoController {

    // 🔹 1. Accessing Query Parameters: /api/demo/query?name=Alice&age=30
    @GetMapping("/query")
    public Map<String, Object> handleQueryParams(@RequestParam String name,
                                                 @RequestParam int age) {
        return Map.of("name", name, "age", age);
    }

    // 🔹 2. Accessing Path Variables: /api/demo/path/123
    @GetMapping("/path/{id}")
    public Map<String, Object> handlePathVariable(@PathVariable("id") Long id) {
        return Map.of("id", id);
    }

    // 🔹 3. Accessing Request Body: POST /api/demo/body with JSON body
    @PostMapping("/body")
    public Map<String, Object> handleRequestBody(@RequestBody PersonDto person) {
        return Map.of(
                "message", "Received person data",
                "name", person.getName(),
                "email", person.getEmail()
        );
    }

    // 🔹 4. Accessing Headers: GET /api/demo/header with custom header
    @GetMapping("/header")
    public Map<String, Object> handleHeader(@RequestHeader("X-Request-ID") String requestId) {
        return Map.of("X-Request-ID", requestId);
    }

    // 🔹 5. Accessing Cookies: GET /api/demo/cookie with cookie sessionId
    @GetMapping("/cookie")
    public Map<String, Object> handleCookie(@CookieValue(name = "sessionId", defaultValue = "N/A") String sessionId) {
        return Map.of("sessionId", sessionId);
    }

    // 🔹 6. Using HttpServletRequest for low-level access: GET /api/demo/request
    @GetMapping("/request")
    public Map<String, Object> handleRawRequest(HttpServletRequest request) {
        Map<String, Object> info = new HashMap<>();
        info.put("method", request.getMethod());
        info.put("uri", request.getRequestURI());
        info.put("queryString", request.getQueryString());
        info.put("headers", Collections.list(request.getHeaderNames())
                .stream()
                .collect(HashMap::new, (m, h) -> m.put(h, request.getHeader(h)), Map::putAll));
        info.put("cookies", Optional.ofNullable(request.getCookies())
                .map(cookies -> Arrays.stream(cookies)
                        .map(cookie -> cookie.getName() + "=" + cookie.getValue())
                        .toList())
                .orElse(List.of("No cookies")));
        return info;
    }

    // Simple DTO for request body
    @Data
    public static class PersonDto {
        private String name;
        private String email;
    }
}