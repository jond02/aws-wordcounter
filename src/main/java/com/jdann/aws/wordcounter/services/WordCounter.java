package com.jdann.aws.wordcounter.services;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.jdann.aws.wordcounter.dao.WordTotalRepository;
import com.jdann.aws.wordcounter.dto.Word;
import com.jdann.aws.wordcounter.dto.WordCountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WordCounter {

    private WordTotalRepository wordTotalRepository;
    private static final String WORD_COUNT_QUEUE = "https://sqs.us-west-2.amazonaws.com/736338261372/word-count";
    private final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

    @Autowired
    public WordCounter(WordTotalRepository wordTotalRepository) {
        this.wordTotalRepository = wordTotalRepository;
    }

    public void findTopWords(String address, WordCountResponse response) {

        if (address == null) {
            response.setStatus("Error: Please enter a valid URL.");
            response.setWords(new ArrayList<>());
            return;
        }

        //check if already in database
        List<Word> words = wordTotalRepository.findAllWithHashKey(address);
        if (!words.isEmpty()) {

            words.sort(Word.byTotal);
            response.setStatus("Success");
            response.setWords(words);

        } else {

            //put in queue and return processing message
            SendMessageRequest send_msg_request = new SendMessageRequest()
                    .withQueueUrl(WORD_COUNT_QUEUE)
                    .withMessageBody(address);

            sqs.sendMessage(send_msg_request);

            response.setStatus("Successfully added '" + address + "' to queue, please try again later for results.");
            response.setWords(words);
        }
    }

}