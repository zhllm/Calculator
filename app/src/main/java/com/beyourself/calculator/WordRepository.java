package com.beyourself.calculator;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.beyourself.calculator.DAO.WordDao;
import com.beyourself.calculator.DTO.Word;

import java.util.List;

public class WordRepository  {
    private LiveData<List<Word>> allWordsList;
    private WordDao wordDao;

    public WordRepository(Context context) {
        WordDatabase database = WordDatabase.getInstance(context.getApplicationContext());
        wordDao = database.getWordDao();
        allWordsList = wordDao.getAllWordsLive();
    }

    public LiveData<List<Word>> getAllWordsList() {
        return allWordsList;
    }


    void insertWords(Word... word) {
        new InsertAsyncTask(wordDao).execute(word);
    }

    void updateWords(Word... word) {
        new UpdateAsyncTask(wordDao).execute(word);
    }

    void deleteWords(Word... word) {
        new DeleteAsyncTask(wordDao).execute(word);
    }

    void clearWords() {
        new ClearAsyncTask(wordDao).execute();
    }


    static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {
        final private WordDao wordDao;

        public InsertAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insertWord(words);
            return null;
        }
    }

    static class ClearAsyncTask extends AsyncTask<Word, Void, Void> {
        final private WordDao wordDao;

        public ClearAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deleteAllWord();
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Word, Void, Void> {
        final private WordDao wordDao;

        public UpdateAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.updateWords(words);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<Word, Void, Void> {
        final private WordDao wordDao;

        public DeleteAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deleteWords(words);
            return null;
        }
    }
}
