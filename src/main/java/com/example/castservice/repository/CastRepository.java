package com.example.castservice.repository;

import com.example.castservice.model.Cast;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CastRepository extends MongoRepository<Cast, String> {
    Cast findCastByiMDB(String iMDB);
    List<Cast> findCastByFirstNameContainsOrLastNameContains(String firstName, String lastName);
    List<Cast> findCastByCharacterContains(String character);
    List<Cast> findCastByAgeEquals(Integer age);
    List<Cast> findCastByBirthPlaceContains(String birthPlace);
    List<Cast> findCastsByMovieId(int movieID);
}
