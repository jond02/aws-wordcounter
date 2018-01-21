package com.jdann.aws.wordcounter.model;

import java.util.Comparator;
import java.util.Objects;

public class Word {

    private final String word;
    private Long count;

    public Word(String word) {
        this.word = word;
        this.count = 1L;
    }

    public void inc() {
        this.count++;
    }

    public String getWord() {
        return word;
    }

    public Long getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", count=" + count +
                '}';
    }

    public static Comparator<Word> byCount = (Word w1, Word w2) -> w2.getCount().compareTo(w1.getCount());
}