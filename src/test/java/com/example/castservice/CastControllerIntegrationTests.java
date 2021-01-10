package com.example.castservice;

import com.example.castservice.model.Cast;
import com.example.castservice.repository.CastRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CastControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CastRepository castRepository;

    private Cast cast1 = new Cast(null,1, "nm0000375", "Tony Stark/Iron Man", "Robert", "Downey Jr.", 55, "Manhatten, New York City, New York, USA");
    private Cast cast2 = new Cast(null,1, "nm0262635", "Steve Rogers/Captain America", "Chris", "Evans", 39, "Boston, Massachusetts, USA");
    private Cast cast3 = new Cast(null,1, "nm0749263", "Bruce Banner/The Hulk", "Mark", "Ruffalo", 53, "Kenosha, Wisconsin, USA");
    private Cast cast4 = new Cast(null,1, "nm0424060", "Natasha Romanoff/Black Widow", "Scarlett", "Johansson", 37, "Manhatten, New York City, New York, USA");
    private Cast castToBeDeleted = new Cast(null,1, "nm1165110", "Thor", "Chris", "Hemsworth", 37, "Melbourne, Victoria, Australia");

    @BeforeEach
    public void beforeAllTests() {
        castRepository.deleteAll();
        castRepository.save(cast1);
        castRepository.save(cast2);
        castRepository.save(cast3);
        castRepository.save(cast4);
        castRepository.save(castToBeDeleted);
    }

    @AfterEach
    public void afterAllTests() {
        castRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenCast_whenGetCastByIMDB_thenReturnJsonCast() throws Exception {
        mockMvc.perform(get("/cast/imdb/{iMDB}", "nm0000375"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId", is(1)))
                .andExpect(jsonPath("$.iMDB", is("nm0000375")))
                .andExpect(jsonPath("$.character", is("Tony Stark/Iron Man")))
                .andExpect(jsonPath("$.firstName", is("Robert")))
                .andExpect(jsonPath("$.lastName", is("Downey Jr.")))
                .andExpect(jsonPath("$.age", is(55)))
                .andExpect(jsonPath("$.birthPlace", is("Manhatten, New York City, New York, USA")));
    }

    @Test
    public void givenCast_whenGetCastByMovieId_thenReturnJsonCasts() throws Exception {
        mockMvc.perform(get("/cast/movie/{movieID}", 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].iMDB", is("nm0000375")))
                .andExpect(jsonPath("$[0].character", is("Tony Stark/Iron Man")))
                .andExpect(jsonPath("$[0].firstName", is("Robert")))
                .andExpect(jsonPath("$[0].lastName", is("Downey Jr.")))
                .andExpect(jsonPath("$[0].age", is(55)))
                .andExpect(jsonPath("$[0].birthPlace", is("Manhatten, New York City, New York, USA")))
                .andExpect(jsonPath("$[2].movieId", is(1)))
                .andExpect(jsonPath("$[2].iMDB", is("nm0749263")))
                .andExpect(jsonPath("$[2].character", is("Bruce Banner/The Hulk")))
                .andExpect(jsonPath("$[2].firstName", is("Mark")))
                .andExpect(jsonPath("$[2].lastName", is("Ruffalo")))
                .andExpect(jsonPath("$[2].age", is(53)))
                .andExpect(jsonPath("$[2].birthPlace", is("Kenosha, Wisconsin, USA")));
    }

    @Test
    public void givenCast_whenGetCastsByCharacter_thenReturnJsonCasts() throws Exception {
        List<Cast> castList = new ArrayList<>();
        castList.add(cast2);
        castList.add(cast3);

        mockMvc.perform(get("/cast/character/{character}", "e"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].iMDB", is("nm0262635")))
                .andExpect(jsonPath("$[0].character", is("Steve Rogers/Captain America")))
                .andExpect(jsonPath("$[0].firstName", is("Chris")))
                .andExpect(jsonPath("$[0].lastName", is("Evans")))
                .andExpect(jsonPath("$[0].age", is(39)))
                .andExpect(jsonPath("$[0].birthPlace", is("Boston, Massachusetts, USA")))
                .andExpect(jsonPath("$[1].movieId", is(1)))
                .andExpect(jsonPath("$[1].iMDB", is("nm0749263")))
                .andExpect(jsonPath("$[1].character", is("Bruce Banner/The Hulk")))
                .andExpect(jsonPath("$[1].firstName", is("Mark")))
                .andExpect(jsonPath("$[1].lastName", is("Ruffalo")))
                .andExpect(jsonPath("$[1].age", is(53)))
                .andExpect(jsonPath("$[1].birthPlace", is("Kenosha, Wisconsin, USA")));
    }

    @Test
    public void givenCast_whenGetCastsByName_thenReturnJsonCasts() throws Exception {
        List<Cast> castList = new ArrayList<>();
        castList.add(cast2);
        castList.add(castToBeDeleted);

        mockMvc.perform(get("/cast/name/{name}", "Chris"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].iMDB", is("nm0262635")))
                .andExpect(jsonPath("$[0].character", is("Steve Rogers/Captain America")))
                .andExpect(jsonPath("$[0].firstName", is("Chris")))
                .andExpect(jsonPath("$[0].lastName", is("Evans")))
                .andExpect(jsonPath("$[0].age", is(39)))
                .andExpect(jsonPath("$[0].birthPlace", is("Boston, Massachusetts, USA")))
                .andExpect(jsonPath("$[1].movieId", is(1)))
                .andExpect(jsonPath("$[1].iMDB", is("nm1165110")))
                .andExpect(jsonPath("$[1].character", is("Thor")))
                .andExpect(jsonPath("$[1].firstName", is("Chris")))
                .andExpect(jsonPath("$[1].lastName", is("Hemsworth")))
                .andExpect(jsonPath("$[1].age", is(37)))
                .andExpect(jsonPath("$[1].birthPlace", is("Melbourne, Victoria, Australia")));
    }

    @Test
    public void givenCast_whenGetCastsByAge_thenReturnJsonCasts() throws Exception {
        List<Cast> castList = new ArrayList<>();
        castList.add(cast4);
        castList.add(castToBeDeleted);

        mockMvc.perform(get("/cast/age/{age}", 37))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].iMDB", is("nm0424060")))
                .andExpect(jsonPath("$[0].character", is("Natasha Romanoff/Black Widow")))
                .andExpect(jsonPath("$[0].firstName", is("Scarlett")))
                .andExpect(jsonPath("$[0].lastName", is("Johansson")))
                .andExpect(jsonPath("$[0].age", is(37)))
                .andExpect(jsonPath("$[0].birthPlace", is("Manhatten, New York City, New York, USA")))
                .andExpect(jsonPath("$[1].movieId", is(1)))
                .andExpect(jsonPath("$[1].iMDB", is("nm1165110")))
                .andExpect(jsonPath("$[1].character", is("Thor")))
                .andExpect(jsonPath("$[1].firstName", is("Chris")))
                .andExpect(jsonPath("$[1].lastName", is("Hemsworth")))
                .andExpect(jsonPath("$[1].age", is(37)))
                .andExpect(jsonPath("$[1].birthPlace", is("Melbourne, Victoria, Australia")));
    }

    @Test
    public void givenCast_whenGetCastsByBirthPlace_thenReturnJsonCasts() throws Exception {
        List<Cast> castList = new ArrayList<>();
        castList.add(cast1);
        castList.add(cast4);

        mockMvc.perform(get("/cast/birthplace/{birthPlace}", "Manhatten"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].iMDB", is("nm0000375")))
                .andExpect(jsonPath("$[0].character", is("Tony Stark/Iron Man")))
                .andExpect(jsonPath("$[0].firstName", is("Robert")))
                .andExpect(jsonPath("$[0].lastName", is("Downey Jr.")))
                .andExpect(jsonPath("$[0].age", is(55)))
                .andExpect(jsonPath("$[0].birthPlace", is("Manhatten, New York City, New York, USA")))
                .andExpect(jsonPath("$[1].movieId", is(1)))
                .andExpect(jsonPath("$[1].iMDB", is("nm0424060")))
                .andExpect(jsonPath("$[1].character", is("Natasha Romanoff/Black Widow")))
                .andExpect(jsonPath("$[1].firstName", is("Scarlett")))
                .andExpect(jsonPath("$[1].lastName", is("Johansson")))
                .andExpect(jsonPath("$[1].age", is(37)))
                .andExpect(jsonPath("$[1].birthPlace", is("Manhatten, New York City, New York, USA")));
    }

    @Test
    public void whenPostCast_thenReturnJsonCast() throws Exception {
        Cast cast5 = new Cast(null,1, "nm0719637", "Clint Barton/Hawkeye", "Jeremy", "Renner", 49,"Modesto, California, USA");

        mockMvc.perform(post("/cast")
                .content(mapper.writeValueAsString(cast5))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId", is(1)))
                .andExpect(jsonPath("$.iMDB", is("nm0719637")))
                .andExpect(jsonPath("$.character", is("Clint Barton/Hawkeye")))
                .andExpect(jsonPath("$.firstName", is("Jeremy")))
                .andExpect(jsonPath("$.lastName", is("Renner")))
                .andExpect(jsonPath("$.age", is(49)))
                .andExpect(jsonPath("$.birthPlace", is("Modesto, California, USA")));
    }

    @Test
    public void givenCast_whenPutCast_thenReturnJsonCast() throws Exception {
        Cast updatedCast = new Cast(null,1, "nm0000375", "Tony Stark/Iron Man", "Robert", "Downey", 55, "Manhatten, New York City, New York, USA");

        mockMvc.perform(put("/cast")
                .content(mapper.writeValueAsString(updatedCast))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId", is(1)))
                .andExpect(jsonPath("$.iMDB", is("nm0000375")))
                .andExpect(jsonPath("$.character", is("Tony Stark/Iron Man")))
                .andExpect(jsonPath("$.firstName", is("Robert")))
                .andExpect(jsonPath("$.lastName", is("Downey")))
                .andExpect(jsonPath("$.age", is(55)))
                .andExpect(jsonPath("$.birthPlace", is("Manhatten, New York City, New York, USA")));
    }

    @Test
    public void givenCast_whenDeleteCast_thenStatusOk() throws Exception {
        mockMvc.perform(delete("/cast/imdb/{iMDB}", "nm1165110")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoCast_whenDeleteCast_thenStatusNotFound() throws Exception {
        mockMvc.perform(delete("/cast/imdb/{iMDB}", "nm1165111")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
