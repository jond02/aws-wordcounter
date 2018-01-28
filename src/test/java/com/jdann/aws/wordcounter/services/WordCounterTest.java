package com.jdann.aws.wordcounter.services;


import com.jdann.aws.wordcounter.dao.WordTotalRepositoryImpl;
import com.jdann.aws.wordcounter.dto.Word;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class WordCounterTest {

    private WordCounter wordCounter;

    @Before
    public void setup() {
        wordCounter = new WordCounter(new WordTotalRepositoryImpl());
    }

    @Test
    public void findTopWordsTest() {

        List<Word> words = wordCounter.findTopWords("http://www.textfiles.com/etext/FICTION/warpeace.txt");
        Assert.assertNotNull(words);
    }

}
