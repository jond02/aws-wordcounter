package com.jdann.aws.wordcounter.services;

import com.jdann.aws.wordcounter.model.Word;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

@Component
public class WordCount {

    private static final int LIMIT = 10;

    public List<Word> findTopWords(String url) {

        String content = fetchContent(url);
        if (content == null) {
            return null;
        }

        //split the text by sequence of non-alphanumeric characters
        String[] wordSequence = content.split("[^\\w']");
        List<Word> words = new ArrayList<>();

        //get totals
        for (String word : wordSequence) {

            if (word.trim().length() == 0) {
                continue;
            }
            Word current = new Word(word);
            int i = words.indexOf(current);

            if (i > -1) {
                words.get(i).inc();
            } else {
                words.add(current);
            }
        }
        words.sort(Word.byCount);

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