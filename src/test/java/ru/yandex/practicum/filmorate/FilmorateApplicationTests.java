package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;

@SpringBootTest
class FilmorateApplicationTests {

	@Autowired
	UserController userController;

	@Autowired
	FilmController filmController;

	@Test
	void contextLoads() {
		Assertions.assertTrue(userController.findAll().isEmpty());
		Assertions.assertTrue(filmController.findAll().isEmpty());
	}

}
