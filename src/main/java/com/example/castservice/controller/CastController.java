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
            castRepository.save(new Cast(null, 1, "hsCu1JUzQQ4pl7uFxAVFLOs9yHh", "Andy Dufresne", "Tim", "Robbins", 62, "West Covina, California, USA"));
            castRepository.save(new Cast(null, 1, "oIciQWr8VwKoR8TmAw1owaiZFyb", "Ellis Boyd Red Redding", "Morgan", "Freeman", 83, "Memphis, Tennessee, USA"));
            castRepository.save(new Cast(null, 2, "fuTEPMsBtV1zE98ujPONbKiYDc2", "Don Vito Corleone", "Marlon", "Brando", 80, "Omaha, Nebraska, USA"));
            castRepository.save(new Cast(null, 2, "fMDFeVf0pjopTJbyRSLFwNDm8Wr", "Michael Corleone", "Al", "Pacino", 80, "Manhattan, New York City, New York, USA"));
            castRepository.save(new Cast(null, 3, "b7fTC9WFkgqGOv77mLQtmD4Bwjx", "Bruce Wayne", "Christian", "Bale", 46, "Haverfordwest, Pembrokeshire, Wales, UK"));
            castRepository.save(new Cast(null, 3, "5Y9HnYYa9jF4NunY9lSgJGjSe8E", "Joker", "Heath", "Ledger", 28, "Perth, Western Australia, Australia"));
            castRepository.save(new Cast(null, 4, "fMDFeVf0pjopTJbyRSLFwNDm8Wr", "Michael", "Al", "Pacino", 80, "Manhattan, New York City, New York, USA"));
            castRepository.save(new Cast(null, 4, "ybMmK25h4IVtfE7qrnlVp47RQlh", "Tom Hagen", "Robert", "Duval", 99, "San Diego, California, USA"));
            castRepository.save(new Cast(null, 5, "2j4LJJfTPQtvnjp8LfSGOvWFATO", "Juror 1", "Martin", "Balsam", 76, "Rome, Lazio, Italy"));
            castRepository.save(new Cast(null, 5, "6vfLLGeGuO6Ko0VRnyhgE2v6RUu", "Juror 2", "John", "Fiedler", 80, "Englewood, New Jersey, USA"));
            castRepository.save(new Cast(null, 6, "j7ufzsiGkUck6HUAuQciXVnowmO", "Sam", "Sean", "Astin", 49, "Santa Monica, California, USA"));
            castRepository.save(new Cast(null, 6, "p3UamIJ6nrpafmTD8sWG2s2I4Hr", "Elanor Gamgee", "Ali", "Astin", 24, "California, USA"));
            castRepository.save(new Cast(null, 7, "qSizF2i9gz6c6DbAC5RoIq8sVqX", "Pumpkin", "Tim", "Roth", 59, "Dulwich, London, England, UK"));
            castRepository.save(new Cast(null, 7, "joHGUtEmXzJkH6ogEliiUKT9MNa", "Honey Bunny", "Arnanda", "Plummer", 63, "New York City, New York, USA"));
            castRepository.save(new Cast(null, 8, "jrf9LaTFWfLA5DBhFWENFsWh4Y9", "Oskar Schindler", "Liam", "Neeson", 68, "Ballymena, Co. Antrim, Northern Ireland, UK"));
            castRepository.save(new Cast(null, 8, "k3Dmu49B2akwDvgqy52MOxznI59", "Itzhak Stern", "Ben", "Kingsley", 77, "Scarborough, Yorkshire, England, UK"));
            castRepository.save(new Cast(null, 9, "wo2hJpn04vbtmh0B9utCFdsQhxM", "Cobb", "Leonardo", "DiCaprio", 46, "Hollywood, Los Angeles, California, USA"));
            castRepository.save(new Cast(null, 9, "4U9G4YwTlIEbAymBaseltS38eH4", "Arthur", "Joseph", "Gordon-Levitt", 39, "Los Angeles, California, USA"));
            castRepository.save(new Cast(null, 10, "5XBzD5WuTyVQZeS4VI25z2moMeY", "The Narrator", "Edward", "Norton", 51, "Boston, Massachusetts, USA"));
            castRepository.save(new Cast(null, 10, "cckcYc2v0yh1tc9QjRelptcOBko", "Tyler Durden", "Brad", "Pitt", 57, "Shawnee, Oklahoma, USA"));
            castRepository.save(new Cast(null, 11, "toK0VxhytDv4Tzswq9UzHBZdFgu", "Sauron", "Sala", "Baker", 44, "Wellington, New Zealand"));
            castRepository.save(new Cast(null, 11, "j7ufzsiGkUck6HUAuQciXVnowmO", "Sam", "Sean", "Astin", 49, "Santa Monica, California, USA"));
        }
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
        retrievedCast.setId(updatedCast.getId());
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
