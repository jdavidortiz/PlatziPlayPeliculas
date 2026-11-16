package com.platzi.platzi_play.web.controller;

import com.platzi.platzi_play.domain.service.PlatziPlayAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//tambien esta dentro del controlador ioc de spring y permite utilizar esta dependencia
public class HelloController {
    private final String platform;
    private final PlatziPlayAiService aiService;

    public HelloController(@Value("${spring.application.name}") String platform, PlatziPlayAiService aiService){//el @Value tambien esta dentro del contenedor ioc de spring y tambien esta el aiService
        this.platform = platform;
        this.aiService = aiService;

    }
    @GetMapping("/hello")
    public String hello(){
        return this.aiService.generateGreeting(platform);
    }
}
