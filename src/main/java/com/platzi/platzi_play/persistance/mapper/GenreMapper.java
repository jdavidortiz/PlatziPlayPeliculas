package com.platzi.platzi_play.persistance.mapper;

import com.platzi.platzi_play.domain.Genre;
import com.platzi.platzi_play.domain.State;
import org.mapstruct.Named;

public class GenreMapper {
    @Named("stringToGenre")//se coloca el named para renombrar ese mapeador de string a genero
    public static Genre stringToGenre(String genero){
        if (genero == null) return null;
        return switch (genero.toUpperCase()){
            case "ACCION" -> Genre.ACTION;
            case "COMEDIA" -> Genre.COMEDY;
            case "DRAMA" -> Genre.DRAMA;
            case "ANIMADA" -> Genre.ANIMATED;
            case "TERROR" -> Genre.HORROR;
            case "CIENCIA_FICCION" -> Genre.SCI_FI;
            default -> null;
            //este es string a enum
        };
    }

    @Named("genreToString")
    public static String genreToString(Genre genre){
        if (genre == null) return null;
        return switch (genre){
            case ACTION -> "ACCION";
            case COMEDY -> "COMEDIA";
            case DRAMA -> "DRAMA";
            case ANIMATED -> "ANIMADA";
            case HORROR -> "TERROR";
            case SCI_FI -> "CIENCIA_FICCION";
        };
    }

    @Named("stringToBoolean")
    public static Boolean stringToBoolean(String estado){
        if (estado == null) return null;
        return switch (estado){
            case "D" -> true;
            case "N" -> false;
            default -> null;
        };
    }

    @Named("booleanToString")
    public static String booleanToString(Boolean isAvailable){
        return isAvailable ? "D" : "N";

    }
}
