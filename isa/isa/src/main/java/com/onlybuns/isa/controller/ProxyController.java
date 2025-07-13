package com.onlybuns.isa.controller;

import com.onlybuns.isa.service.LoadBalancerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
public class ProxyController {

    @Autowired
    LoadBalancerService loadBalancerService;

    private final RestTemplate restTemplate;

    public ProxyController() {
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/proxy/**")
    public ResponseEntity<String> proxy(HttpServletRequest request, HttpMethod method,
                                        @RequestBody(required = false) String body) {
        String path = request.getRequestURI().substring("/proxy/".length());
        String query = request.getQueryString();
        String fullPath = path + (query != null ? "?" + query : "");

        String baseUrl = loadBalancerService.getNextInstance();

        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        String targetUrl = baseUrl + "/" + fullPath;

        System.out.println("Zahtev je prosledjen na: " + targetUrl);

        HttpHeaders headers = new HttpHeaders();
        Collections.list(request.getHeaderNames()).forEach(
                name -> headers.set(name, request.getHeader(name)));
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try{
            return restTemplate.exchange(targetUrl, method, entity, String.class);
        } catch(ResourceAccessException ex){
            String fallbackUrl = loadBalancerService.getNextInstance();
            String fallbackTarget = fallbackUrl + fullPath;
            return restTemplate.exchange(fallbackTarget, method, entity, String.class);
        }
    }
}
