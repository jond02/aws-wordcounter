package com.jdann.aws.wordcounter.services;


import com.jdann.aws.wordcounter.model.Word;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class WordCountTest {

    private WordCount wordCount;

    @Before
    public void setup() {
        wordCount = new WordCount();
    }

    @Test
    public void findTopWordsTest() {

        WordCount wordCount = new WordCount();
        List<Word> words = wordCount.findTopWords("http://www.textfiles.com/etext/FICTION/warpeace.txt");
        Assert.assertNotNull(words);
    }

}
