package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.FilmRepository;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.mapper.film.FilmRowMapper;
import ru.yandex.practicum.filmorate.mapper.film.GenreRowMapper;
import ru.yandex.practicum.filmorate.mapper.film.MpaRateRowMapper;
import ru.yandex.practicum.filmorate.mapper.user.UserRowMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRate;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmDbStorage.class, FilmRepository.class, FilmRowMapper.class, MpaRateRowMapper.class, GenreRowMapper.class,
		UserDbStorage.class, UserRepository.class, UserRowMapper.class})

class FilmorateApplicationTests {

	private final UserDbStorage userStorage;
	private final FilmDbStorage filmDbStorage;

	@Test
	void testFindUserByIdInEmptyBase() {
		Optional<User> userOptional = userStorage.getUserById(1);
		assertTrue(userOptional.isEmpty());
	}

	@Test
	void testThatFilmCountIsEmptyInEmptyBase() {
		int filmsCount = filmDbStorage.getFilmsCount();
		assertEquals(0, filmsCount);
	}

	@Test
	void testFindGenreById() {
		Optional<Genre> genreOptional = filmDbStorage.getGenreById(3);
		assertThat(genreOptional)
				.isPresent()
				.hasValueSatisfying(genre ->
						assertThat(genre).hasFieldOrPropertyWithValue("id", 3)
				);
	}


	@Test
	void testFindMPARateById() {
		Optional<MpaRate> mpaRateOptional = filmDbStorage.getMpaRateById(2);
		assertThat(mpaRateOptional)
				.isPresent()
				.hasValueSatisfying(mpaRate ->
						assertThat(mpaRate).hasFieldOrPropertyWithValue("id", 2)
				);
	}

	@Test
	void testThatGenreCountIsNotEmpty() {
		int genreCount = filmDbStorage.getGenres().size();
		assertEquals(6, genreCount);
	}

	@Test
	void testThatMPARateCountIsNotEmpty() {
		int mpaRateCount = filmDbStorage.getMpaRates().size();
		assertEquals(5, mpaRateCount);
	}

}
