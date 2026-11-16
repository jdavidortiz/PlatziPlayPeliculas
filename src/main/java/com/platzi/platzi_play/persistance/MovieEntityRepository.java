package com.platzi.platzi_play.persistance;

import com.platzi.platzi_play.domain.dto.MovieDto;
import com.platzi.platzi_play.domain.dto.UpdateMovieDto;
import com.platzi.platzi_play.domain.exception.MovieAlreadyExistsException;
import com.platzi.platzi_play.domain.exception.MovieNotFoundException;
import com.platzi.platzi_play.domain.repository.MovieRepository;
import com.platzi.platzi_play.persistance.crud.CrudMovieEntity;
import com.platzi.platzi_play.persistance.entity.MovieEntity;
import com.platzi.platzi_play.persistance.mapper.MovieMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
@Repository
public class MovieEntityRepository implements MovieRepository {
    private final CrudMovieEntity crudMovieEntity;
    private final MovieMapper movieMapper;

    public MovieEntityRepository(CrudMovieEntity crudMovieEntity, MovieMapper movieMapper) {
        this.crudMovieEntity = crudMovieEntity;
        this.movieMapper = movieMapper;
    }

    @Override
    public List<MovieDto> getAll() {
        return this.movieMapper.toDto(this.crudMovieEntity.findAll());
    }

    @Override
    public MovieDto getById(long id) {
        MovieEntity movieEntity = this.crudMovieEntity.findById(id).orElse(null);//valida que si encuentra el id o no de la pelicula
        return this.movieMapper.toDto(movieEntity);//como ya se tiene el Entity y se necesita un dto usando el toDTO
    }

    @Override
    public MovieDto save(MovieDto movieDto) {
        if (this.crudMovieEntity.findFirstByTitulo(movieDto.title()) != null){
            throw new MovieAlreadyExistsException(movieDto.title());
        }
        MovieEntity movieEntity = this.movieMapper.toEntity(movieDto);
        movieEntity.setEstado("D");
        return this.movieMapper.toDto(this.crudMovieEntity.save(movieEntity));
    }

    @Override
    public MovieDto update(long id, UpdateMovieDto updateMovieDto) {
        MovieEntity movieEntity = this.crudMovieEntity.findById(id)
                .orElseThrow(()-> new MovieNotFoundException(id));

        this.movieMapper.updateEntityFromDto(updateMovieDto, movieEntity);
        return this.movieMapper.toDto(crudMovieEntity.save(movieEntity));
    }

    @Override
    public void deleteById(long id) {
        MovieEntity movieEntity = this.crudMovieEntity.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
        this.crudMovieEntity.delete(movieEntity);

    }
}
