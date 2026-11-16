package com.platzi.platzi_play.domain.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(Long id) {
        super("La pelicula " + id + " no puede editarse ni eliminarse, porque no existe la pelicula");
    }
}
