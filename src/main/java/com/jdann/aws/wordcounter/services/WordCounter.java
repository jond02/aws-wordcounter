package com.jdann.aws.wordcounter.services;

import com.jdann.aws.wordcounter.dao.WordTotalRepository;
import com.jdann.aws.wordcounter.dto.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

@Component
public class WordCounter {

    private static final int LIMIT = 10;
    private WordTotalRepository wordTotalRepository;

    @Autowired
    public WordCounter(WordTotalRepository wordTotalRepository) {
        this.wordTotalRepository = wordTotalRepository;
    }

    public List<Word> findTopWords(String address) {

        List<Word> words = new ArrayList<>();
        if (address == null) {
            return words;
        }

        //check if already in database
        words = wordTotalRepository.findAllWithHashKey(address);
        if (!words.isEmpty()) {

            words.sort(Word.byTotal);
            return words;

        } else {

            //need to process content for the first time
            String content = fetchContent(address);
            words = processContent(content, address);

            //save in database
            words.forEach(wordTotalRepository::save);

            return words;
        }
    }

    private List<Word> processContent(String content, String address) {

        List<Word> words = new ArrayList<>();

        if (content == null) {
            return words;
        }

        //split the text by sequence of non-alphanumeric characters and get totals
        String[] wordSequence = content.split("[^\\w']");

        //get totals
        for (String word : wordSequence) {

            if (word.trim().length() == 0) {
                continue;
            }
            Word current = new Word(address, word);
            int i = words.indexOf(current);

            if (i > -1) {
                words.get(i).inc();
            } else {
                words.add(current);
            }
        }
        words.sort(Word.byTotal);

        //return entries up to the set limit
        return words.subList(0, words.size() > LIMIT ? LIMIT : words.size());
    }

    private String fetchContent(String address) {

        if (address == null) {
            return null;
        }

        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;

        try {
            url = new URL(address);
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();

            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ignore) {
            }
        }
        return null;
    }

}