package com.beyourself.calculator.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.beyourself.calculator.DTO.Word;

import java.util.List;

@Dao // Database access object 数据库访问接口
public interface WordDao {
    @Insert
    void insertWord(Word... words);

    @Update
    void updateWords(Word... words);

    @Delete
    void deleteWords(Word... words);

    @Query("DELETE FROM WORD")
    void deleteAllWord();

    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    List<Word> getAllWords();

    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    LiveData<List<Word>> getAllWordsLive();

}
