package com.beyourself.calculator;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.beyourself.calculator.DAO.WordDao;
import com.beyourself.calculator.DTO.Word;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    public abstract WordDao getWordDao();
}
