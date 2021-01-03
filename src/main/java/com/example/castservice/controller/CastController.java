package com.example.castservice.controller;

import com.example.castservice.model.Cast;
import com.example.castservice.repository.CastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class CastController {

    @Autowired
    private CastRepository castRepository;

    @PostConstruct
    public void fillDB() {
        if(castRepository.count() == 0) {
            castRepository.save(new Cast(851, "nm0000375", "Tony Stark/Iron Man", "Robert", "Downey Jr.", 55, "Manhatten, New York City, New York, USA"));
            castRepository.save(new Cast(852, "nm0262635", "Steve Rogers/Captain America", "Chris", "Evans", 39, "Boston, Massachusetts, USA"));
            castRepository.save(new Cast(1, "nm0749263", "Bruce Banner/The Hulk", "Mark", "Ruffalo", 53, "Kenosha, Wisconsin, USA"));
            castRepository.save(new Cast(859, "nm1165110", "Thor", "Chris", "Hemsworth", 37, "Melbourne, Victoria, Australia"));
            castRepository.save(new Cast(1, "nm0424060", "Natasha Romanoff/Black Widow", "Scarlett", "Johansson", 37, "Manhatten, New York City, New York, USA"));
        }

        System.out.println("Cast test: " + castRepository.findCastByBirthPlaceContains("USA").size());
    }

    @GetMapping("/cast/imdb/{iMDB}")
    public Cast getCastByIMDB(@PathVariable String iMDB) {
        return castRepository.findCastByiMDB(iMDB);
    }

    @GetMapping("/cast/movie/{movieID}")
    public List<Cast> getCastByMovieID(@PathVariable int movieID) {
        return castRepository.findCastsByMovieId(movieID);
    }

    @GetMapping("/cast/character/{character}")
    public List<Cast> getCastsByCharacter(@PathVariable String character) {
        return castRepository.findCastByCharacterContains(character);
    }

    @GetMapping("/cast/name/{name}")
    public List<Cast> getCastsByName(@PathVariable String name) {
        return castRepository.findCastByFirstNameContainsOrLastNameContains(name, name);
    }

    @GetMapping("/cast/age/{age}")
    public List<Cast> getCastsByAge(@PathVariable Integer age) {
        return castRepository.findCastByAgeEquals(age);
    }

    @GetMapping("/cast/birthplace/{birthPlace}")
    public List<Cast> getCastsByBirthPlace(@PathVariable String birthPlace) {
        return castRepository.findCastByBirthPlaceContains(birthPlace);
    }

    @PostMapping("/cast")
    public Cast addCast(@RequestBody Cast cast) {
        castRepository.save(cast);
        return cast;
    }

    @PutMapping("/cast")
    public Cast updateCast(@RequestBody Cast updatedCast) {
        Cast retrievedCast = castRepository.findCastByiMDB(updatedCast.getiMDB());
        retrievedCast.setMovieId(updatedCast.getMovieId());
        retrievedCast.setiMDB(updatedCast.getiMDB());
        retrievedCast.setCharacter(updatedCast.getCharacter());
        retrievedCast.setFirstName(updatedCast.getFirstName());
        retrievedCast.setLastName(updatedCast.getLastName());
        retrievedCast.setAge(updatedCast.getAge());
        retrievedCast.setBirthPlace(updatedCast.getBirthPlace());
        castRepository.save(retrievedCast);
        return retrievedCast;
    }

    @DeleteMapping("/cast/imdb/{iMDB}")
    public ResponseEntity deleteCast(@PathVariable String iMDB) {
        Cast cast = castRepository.findCastByiMDB(iMDB);
        if(cast != null) {
            castRepository.delete(cast);
            return ResponseEntity.ok().build();
        } else return ResponseEntity.notFound().build();
    }
}
