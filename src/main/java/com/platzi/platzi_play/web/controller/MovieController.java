package com.platzi.platzi_play.web.controller;

import com.platzi.platzi_play.domain.dto.MovieDto;
import com.platzi.platzi_play.domain.dto.SuggestRequestDto;
import com.platzi.platzi_play.domain.dto.UpdateMovieDto;
import com.platzi.platzi_play.domain.service.MovieService;
import com.platzi.platzi_play.domain.service.PlatziPlayAiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController//entra del contenedor ioc de spring y permite utilizar esta dependencia
@RequestMapping("/movies")
@Tag(name = "Movies", description = "Operations about movies of PlatziPlay")//clase sobre el la documentación API REST con OpenAPI
public class MovieController {
    private final MovieService movieService;
    private final PlatziPlayAiService aiService;

    public MovieController(MovieService movieService, PlatziPlayAiService aiService){
        this.movieService = movieService;
        this.aiService = aiService;
    }
    //es para que spring sepa que es una peticion get, como comente el getmapping ya que lo agregue al principio del movieController
    @GetMapping
    public ResponseEntity<List<MovieDto>> getAll() {
        return ResponseEntity.ok(this.movieService.getAll());//aca obtiene cada una de las peliculas
        /*
        (List<MovieEntity>) this.crudMovieEntity.findAll(); se escribe
        primero this.crudMovieEntity.findAll() y luego se le da clic
        para castear la información
         */
    }

    @GetMapping("/{id}")//aca se quito el /movies ya que esta al principio en RequestMapping
    @Operation(
            summary = "Get a movie  by id",
            description = "Returns a movie along with the identifier",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pelicula encontrada"),
                    @ApiResponse(responseCode = "404", description = "Pelicula no encontrada", content = @Content)
            }
    )
    public ResponseEntity<MovieDto> getById(@Parameter(description = "Identificador de la pelicula a recuperar", example = "9") @PathVariable long id){//le indica el pathVariable es que
        // lo que reciba de parametro en este caso el Long id, sera una
        // variable que viene dentro del path
        MovieDto movieDto = this.movieService.getById(id);
        //el ResponseEntity es una forma de responder a esos servicios
        if (movieDto == null){
            return ResponseEntity.notFound().build();//si respondió mal envia ese error
        }
        return ResponseEntity.ok(movieDto);
    }

    @PostMapping("/suggest")
    public ResponseEntity<String> generateMoviesSuggestion(@RequestBody SuggestRequestDto suggestRequestDto){
        return ResponseEntity.ok(this.aiService.generateMoviesSuggestion(suggestRequestDto.userPreferences()));
    }

    @PostMapping
    public ResponseEntity<MovieDto> add(@PathVariable @RequestBody @Valid MovieDto movieDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.movieService.add(movieDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> update(@PathVariable long id, @RequestBody @Valid UpdateMovieDto updateMovieDto){
        return ResponseEntity.ok(this.movieService.update(id, updateMovieDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id){
        movieService.deleteById(id);
        return ResponseEntity.ok("Movie with ID " + id + " was deleted successfully.");    }
}
