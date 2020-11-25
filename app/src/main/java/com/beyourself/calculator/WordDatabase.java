package com.beyourself.calculator;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.beyourself.calculator.DAO.WordDao;
import com.beyourself.calculator.DTO.Word;

@Database(entities = {Word.class}, version = 4, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase INSTANCE;

    static synchronized WordDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordDatabase.class, "Word")
                    // .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION3_4)
                    .build();
        }
        return INSTANCE;
    }

    public abstract WordDao getWordDao();

    static final Migration MIGRATION2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word ADD COLUMN test_bar INTEGER NOT NULL DEFAULT 1");
        }
    };

    static final Migration MIGRATION3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE word_temp (id INTEGER PRIMARY KEY NOT NULL, english_word TEXT," +
                    "chinese_meaning TEXT)");
            database.execSQL("INSERT INTO word_temp(id, chinese_meaning, english_word) " +
                    "SELECT id, chinese_meaning, english_word FROM Word");
            database.execSQL("DROP TABLE Word");
            database.execSQL("ALTER TABLE word_temp RENAME TO Word");
        }
    };
}
