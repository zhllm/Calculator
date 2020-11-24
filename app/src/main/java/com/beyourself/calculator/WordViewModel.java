package com.beyourself.calculator;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
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

}
