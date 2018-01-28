package com.jdann.aws.wordcounter.dao;

import com.jdann.aws.wordcounter.dto.Word;

import java.util.List;

public interface WordTotalRepository {

    void save(Word word);

    void save(List<Word> words);

    Word find(String url, String word);

    void delete(Word word);

    List<Word> findAllWithHashKey(String hashKey);

    boolean createTableIfNotExist();

    boolean deleteTable(String tableName);

}
