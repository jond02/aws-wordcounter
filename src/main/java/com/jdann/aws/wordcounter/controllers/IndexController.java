package com.jdann.aws.wordcounter.controllers;

import com.jdann.aws.wordcounter.model.Word;
import com.jdann.aws.wordcounter.services.WordCount;
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
public class IndexController {

    private WordCount wordCount;

    @Autowired
    public IndexController(WordCount wordCount) {
        this.wordCount = wordCount;
    }

    @GetMapping(value = "wordcount", produces = "application/json")
    public ResponseEntity<List<Word>> countWords(@PathParam("url") String url) {

        List<Word> words = wordCount.findTopWords(url);

        if (words == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(words, HttpStatus.OK);
        }
    }

}
