package com.jdann.aws.wordcounter.dto;

import java.util.List;

public class WordCountResponse {

    private String status;
    private List<Word> words;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return "WordCountResponse{" +
                "status='" + status + '\'' +
                ", words=" + words +
                '}';
    }
}
