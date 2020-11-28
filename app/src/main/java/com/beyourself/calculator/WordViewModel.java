package com.beyourself.calculator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.beyourself.calculator.DTO.Word;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    public LiveData<List<Word>> allWordsLive;
    private final WordRepository wordRepository;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
        allWordsLive = wordRepository.getAllWordsList();
    }

    public LiveData<List<Word>> getAllWordsLive() {
        return allWordsLive;
    }

    void insertWords(Word... word) {
        wordRepository.insertWords(word);
    }

    void updateWords(Word... word) {
        wordRepository.updateWords(word);
    }

    void deleteWords(Word... word) {
        wordRepository.deleteWords(word);
    }

    void clearWords() {
        wordRepository.clearWords();
    }

    public LiveData<List<Word>> findWordsWithPatten(String patten) {
        return wordRepository.findWordsWithPatten(patten);
    }
}
