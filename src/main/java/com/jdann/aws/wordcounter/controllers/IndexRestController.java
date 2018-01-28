package com.jdann.aws.wordcounter.controllers;

import com.jdann.aws.wordcounter.dao.WordTotalRepository;
import com.jdann.aws.wordcounter.dto.Word;
import com.jdann.aws.wordcounter.services.WordCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class IndexRestController {

    private WordCounter wordCounter;
    private WordTotalRepository wordTotalRepository;

    @Autowired
    public IndexRestController(WordCounter wordCounter, WordTotalRepository wordTotalRepository) {
        this.wordCounter = wordCounter;
        this.wordTotalRepository = wordTotalRepository;
    }

    @GetMapping(value = "wordcount", produces = "application/json")
    public ResponseEntity<List<Word>> countWords(@PathParam("address") String address) {

        List<Word> words = wordCounter.findTopWords(address);

        if (words.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(words, HttpStatus.OK);
        }
    }

    @GetMapping(value = "rebuildDatabase")
    public void rebuildDatabase() {
        wordTotalRepository.deleteTable(Word.TABLE_NAME);
        wordTotalRepository.createTableIfNotExist();
    }

}
