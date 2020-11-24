package com.beyourself.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beyourself.calculator.DAO.WordDao;
import com.beyourself.calculator.DTO.Word;

import java.util.List;

public class MainActivity_Room_Data_Base extends AppCompatActivity {

    TextView textView;
    Button insert, update, delete, clear;
    LiveData<List<Word>> allWordsLive;
    WordViewModel wordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__room__data__base);
        textView = findViewById(R.id.textView14);
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        allWordsLive = wordViewModel.allWordsLive;
        allWordsLive.observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                StringBuilder text = new StringBuilder();
                for (int i = 0; i < words.size(); i++) {
                    Word word = words.get(i);
                    text.append(word.getId()).append(":").append(word.getWord()).append("=").append(word.getChineseMeaning()).append("\n");
                }
                textView.setText(text.toString());
            }
        });
        insert = findViewById(R.id.insert);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        clear = findViewById(R.id.clear);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word1 = new Word("Hello", "你好");
                Word word2 = new Word("world", "世界");
                wordViewModel.insertWords(word1, word2);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordViewModel.clearWords();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Hi", "你好啊");
                word.setId(120);
                wordViewModel.updateWords(word);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("sd", "12");
                word.setId(120);
                wordViewModel.deleteWords(word);
            }
        });
    }

}