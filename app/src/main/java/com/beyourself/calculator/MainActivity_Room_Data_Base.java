package com.beyourself.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beyourself.calculator.DAO.WordDao;
import com.beyourself.calculator.DTO.Word;

import java.util.List;

public class MainActivity_Room_Data_Base extends AppCompatActivity {

    WordDatabase wordDatabase;
    WordDao wordDao;
    TextView textView;
    Button insert, update, delete, clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__room__data__base);
        textView = findViewById(R.id.textView14);
        wordDatabase = Room.databaseBuilder(this, WordDatabase.class, "Word")
                .allowMainThreadQueries()
                .build();
        wordDao = wordDatabase.getWordDao();
        updateView();

        insert = findViewById(R.id.insert);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        clear = findViewById(R.id.clear);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word1 = new Word("Hello", "你好");
                Word word2 = new Word("world", "世界");
                wordDao.insertWord(word1, word2);
                updateView();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordDao.deleteAllWord();
                updateView();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Hi", "你好啊");
                word.setId(80);
                wordDao.updateWords(word);
                updateView();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("sd", "12");
                word.setId(80);
                wordDao.deleteWords(word);
                updateView();
            }
        });

    }

    void updateView() {
        List<Word> list = wordDao.getAllWords();
        String text = "";
        for (int i = 0; i < list.size(); i++) {
            Word word = list.get(i);
            text += word.getId() + ":" + word.getWord() + "=" + word.getChineseMeaning() + "\n";
        }
        textView.setText(text);
    }
}