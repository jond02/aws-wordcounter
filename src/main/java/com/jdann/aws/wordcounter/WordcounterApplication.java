package com.jdann.aws.wordcounter;

import com.jdann.aws.wordcounter.dao.WordTotalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WordcounterApplication {

	private static WordTotalRepository wordTotalRepository;

	public static void main(String[] args) {
		SpringApplication.run(WordcounterApplication.class, args);
		wordTotalRepository.createTableIfNotExist();
	}

	@Autowired
	private void setRepository(WordTotalRepository wordTotalRepository) {
		WordcounterApplication.wordTotalRepository = wordTotalRepository;
	}
}
