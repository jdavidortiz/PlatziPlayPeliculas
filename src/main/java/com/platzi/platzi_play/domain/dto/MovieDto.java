package com.platzi.platzi_play.domain.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record MovieDto (
        Long id,
        @NotBlank(message = "El titulo no es obligatorio")
        String title,
        @Min(value = 0, message = "La duración no puede ser menor que 0")
        Integer duration,
        @NotNull(message = "El genero debe ser obligatorio")
        String genre,
        @PastOrPresent(message = "La fecha de lanzamiento debe ser anterior a la fecha actual!!!")
        LocalDate releaseDate,
        @Min(value = 0, message = "El rating no puede ser menor que 0")
        @Max(value = 5, message = "El rating no puede ser mayor que 5")
        Double rating,
        String state
){

}
