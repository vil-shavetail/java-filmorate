package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRate;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import({FilmDbStorage.class, FilmRepository.class, FilmRowMapper.class, MpaRateRowMapper.class, GenreRowMapper.class,
		UserDbStorage.class, UserRepository.class, UserRowMapper.class})
class FilmorateApplicationTests {

	private final FilmDbStorage filmDbStorage;
	private final UserDbStorage userStorage;

	@BeforeAll
	public void setUp() {
		userStorage.createUser(buildUser("Алексей"));
		userStorage.createUser(buildUser("Сергей"));

		filmDbStorage.createFilm(buildFilm("Полицейская академия"));
		filmDbStorage.createFilm(buildFilm("Полицейская академия 2"));
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

	@Test
	void testFindUserById() {

		Optional<User> userOptional = userStorage.getUserById(1);

		assertThat(userOptional)
				.isPresent()
				.hasValueSatisfying(user ->
						assertThat(user).hasFieldOrPropertyWithValue("id", 1L)
				);
	}

	@Test
	void testGetUsers() {

		Optional<User> user1Optional = userStorage.getUserById(1);
		Optional<User> user2Optional = userStorage.getUserById(2);

		if (user1Optional.isEmpty() || user2Optional.isEmpty()) {
			return;
		}

		Collection<User> users = userStorage.getCollectionOfUsers();

		assertThat(users).contains(user1Optional.get(), user2Optional.get());
	}

	@Test
	void testUpdateUser() {

		Optional<User> user1Optional = userStorage.getUserById(1);

		if (user1Optional.isEmpty()) {
			return;
		}
		User userToUpdate = user1Optional.get();
		userToUpdate.setName("other name");

		userStorage.updateUser(userToUpdate);

		user1Optional = userStorage.getUserById(1);

		assertThat(user1Optional)
				.isPresent()
				.hasValueSatisfying(user ->
						assertThat(user).hasFieldOrPropertyWithValue("name", "other name")
				);
	}

	@Test
	void testAddFriendDeleteFriend() {

		Optional<User> user1Optional = userStorage.getUserById(1);
		Optional<User> user2Optional = userStorage.getUserById(2);

		if (user1Optional.isEmpty() || user2Optional.isEmpty()) {
			return;
		}

		Set<Long> uList1 = user1Optional.get().getFriends();
		Set<Long> uList2 = user2Optional.get().getFriends();

		assertThat(uList1).isEmpty();
		assertThat(uList2).isEmpty();

		userStorage.addFriend(1, 2);
		userStorage.addFriend(2, 1);

		user1Optional = userStorage.getUserById(1);
		user2Optional = userStorage.getUserById(2);

		if (user1Optional.isEmpty() || user2Optional.isEmpty()) {
			return;
		}

		uList1 = user1Optional.get().getFriends();
		uList2 = user2Optional.get().getFriends();

		assertThat(uList1).contains(2L);
		assertThat(uList2).contains(1L);

		Set<User> fList = userStorage.getUserFriends(1);

		assertThat(uList1).containsAll(fList.stream()
				.map(User::getId)
				.toList());

		userStorage.deleteFriend(1, 2);
		userStorage.deleteFriend(2, 1);

		user1Optional = userStorage.getUserById(1);
		user2Optional = userStorage.getUserById(2);

		if (user1Optional.isEmpty() || user2Optional.isEmpty()) {
			return;
		}

		uList1 = user1Optional.get().getFriends();
		uList2 = user2Optional.get().getFriends();

		assertThat(uList1).isEmpty();
		assertThat(uList2).isEmpty();
	}

	@Test
	void testCreateFilmGetFilm() {

		Optional<Film> optionalFilm = filmDbStorage.getFilmById(1);

		assertThat(optionalFilm)
				.isPresent()
				.hasValueSatisfying(film ->
						assertThat(film).hasFieldOrPropertyWithValue("id", 1L)
				);
	}

	@Test
	void testUpdateFilm() {

		Optional<Film> film1Optional = filmDbStorage.getFilmById(1);

		if (film1Optional.isEmpty()) {
			return;
		}

		Film filmToUpdate = film1Optional.get();
		filmToUpdate.setName("other name");
		filmDbStorage.updateFilm(filmToUpdate);
		film1Optional = filmDbStorage.getFilmById(1);

		assertThat(film1Optional)
				.isPresent()
				.hasValueSatisfying(film ->
						assertThat(film).hasFieldOrPropertyWithValue("name", "other name")
				);
	}

	@Test
	void testFilmsCount() {
		assertThat(filmDbStorage.getFilmsCount()).isEqualTo(2);
	}

	@Test
	public void testAddAndDeleteUserLikeAnd() {

		Optional<Film> film1Optional = filmDbStorage.getFilmById(1);

		if (film1Optional.isEmpty()) {
			return;
		}

		Film film = film1Optional.get();

		assertThat(film.getLikes()).isEmpty();

		filmDbStorage.addUserLike(1, 1);

		film1Optional = filmDbStorage.getFilmById(1);

		if (film1Optional.isEmpty()) {
			return;
		}

		film = film1Optional.get();

		assertThat(film.getLikes()).contains(1L);

		filmDbStorage.deleteUserLike(1, 1);

		film1Optional = filmDbStorage.getFilmById(1);

		if (film1Optional.isEmpty()) {
			return;
		}

		film = film1Optional.get();

		assertThat(film.getLikes()).isEmpty();
	}

	@Test
	void testGetMpaRateById() {
		assertThat(filmDbStorage.getMpaRateById(1)).hasValueSatisfying(
				mpaRate -> assertThat(mpaRate).hasFieldOrPropertyWithValue("name", "G"));
	}

	@Test
	void testGetGenreById() {
		assertThat(filmDbStorage.getGenreById(1)).hasValueSatisfying(
				genre -> assertThat(genre).hasFieldOrPropertyWithValue("name", "Комедия"));
	}

	@Test
	void testGetGenres() {
		Collection<Genre> gList = List.of(
				Genre.builder().id(1).name("Комедия").build(),
				Genre.builder().id(2).name("Драма").build(),
				Genre.builder().id(3).name("Мультфильм").build(),
				Genre.builder().id(4).name("Триллер").build(),
				Genre.builder().id(5).name("Документальный").build(),
				Genre.builder().id(5).name("Боевик").build()
		);

		assertThat(filmDbStorage.getGenres()).containsAnyElementsOf(gList);
	}


	private User buildUser(String name) {
		return User.builder()
				.name(name)
				.email("test@mail.ru")
				.login("Loginator")
				.birthday(LocalDate.of(1979, 8, 15))
				.build();
	}

	private Film buildFilm(String name) {
		return Film.builder()
				.name(name)
				.description("Лучший фильм в мире")
				.releaseDate(LocalDate.now())
				.duration(120)
				.mpaRate(MpaRate.builder()
						.id(1)
						.build())
				.build();
	}

}
