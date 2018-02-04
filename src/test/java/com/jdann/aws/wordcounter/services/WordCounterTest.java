package com.jdann.aws.wordcounter.services;

import com.jdann.aws.wordcounter.dao.WordTotalRepositoryImpl;
import com.jdann.aws.wordcounter.dto.WordCountResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WordCounterTest {

    private WordCounter wordCounter;

    @Before
    public void setup() {
        wordCounter = new WordCounter(new WordTotalRepositoryImpl());
    }

    @Test
    public void findTopWords_ReturnWordsForDatabaseMatch() {

        WordCountResponse response = new WordCountResponse();
        wordCounter.getFromDynamoOrQueue("http://www.textfiles.com/etext/FICTION/warpeace.txt", response);
        Assert.assertTrue(response.getStatus().equals("Success"));
        Assert.assertTrue(response.getWords().size() == 10);
    }

    @Test
    public void findTopWords_ReturnsProcessingMessageForNewAddress() {

        final String testUrl = "http://test.url";
        WordCountResponse response = new WordCountResponse();
        wordCounter.getFromDynamoOrQueue(testUrl, response);
        Assert.assertTrue(response.getStatus().contains(testUrl));
        Assert.assertTrue(response.getWords().isEmpty());
    }

}
