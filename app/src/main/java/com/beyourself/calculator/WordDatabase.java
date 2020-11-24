package com.beyourself.calculator;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.beyourself.calculator.DAO.WordDao;
import com.beyourself.calculator.DTO.Word;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase INSTANCE;
    static synchronized WordDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordDatabase.class, "Word")
                    .build();
        }
        return INSTANCE;
    }
    public abstract WordDao getWordDao();
}
