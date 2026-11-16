package com.platzi.platzi_play.domain.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService//también esta dentro del controlador ioc de spring y permite utilizar esta dependencia
public interface PlatziPlayAiService {
    @UserMessage("""
            Genera un saludo de bienvenida a la plataforma de gestión de peliculas {{platform}}, usa menos de 120 caracteres y hazlo con el estilo de Platzi
            """)
    String generateGreeting(@V("platform") String platform);

    @SystemMessage("""
            Eres experto en cine que recomienda peliculas personalizadas según los gustos del usuario.
            Debes recomendar maximo 3 peliculas.
            No incluyas peliculas que estén por fuera de la plataforma PaltziPlay.
            """)
    String generateMoviesSuggestion(@UserMessage String userMessage);
}
