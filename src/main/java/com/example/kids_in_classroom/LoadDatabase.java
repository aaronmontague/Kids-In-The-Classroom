package com.example.kids_in_classroom;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(StudentRepository repository) {
		return args -> {
			repository.save(new Student("Bilbo", "Baggins", 32, 1301));
			repository.save(new Student("Frodo", "Baggins", 682, 1382));
			repository.save(new Student("Gimli", "Gloinson", 4, 1289));
			repository.save(new Student("Dego", "Gamge", 1104, 1397));
			repository.save(new Student("Pieli", "Gamge", 1144, 1399));
			repository.save(new Student("Bard", "The Bowman", 1106, 1397));
			repository.save(new Student("Sauron", "NotNice", 2, -1699));
			repository.save(new Student("Gandalf", "Tharkun", 1, -1699));
			repository.save(new Student("Gimli", "Gimlison", 1145, 1397));
		};
	}
}
