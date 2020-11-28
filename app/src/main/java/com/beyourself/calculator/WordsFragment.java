package com.beyourself.calculator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.beyourself.calculator.DTO.Word;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class WordsFragment extends Fragment {
    private MyAdapter myAdapter1, myAdapter2;
    private RecyclerView recyclerView;
    private FloatingActionButton button;
    private boolean fistOpen = true;
    private WordViewModel wordViewModel;
    private LiveData<List<Word>> filterWords;
    private final static String VIEW_TYPE_SHP = "view_type_shp";
    private static final String IS_USING_CARD_TYPE = "is_using_card_type";

    public WordsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.switch_view:
                SharedPreferences shp = requireActivity()
                        .getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE);
                boolean viewType = shp.getBoolean(IS_USING_CARD_TYPE, false);
                SharedPreferences.Editor editor = shp.edit();
                if (viewType) {
                    recyclerView.setAdapter(myAdapter1);
                    editor.putBoolean(IS_USING_CARD_TYPE, false);
                } else {
                    recyclerView.setAdapter(myAdapter2);
                    editor.putBoolean(IS_USING_CARD_TYPE, true);
                }
                editor.apply();
                break;
            case R.id.clear_data:
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                wordViewModel.clearWords();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("MyTag", "操作取消");
                            }
                        }).setTitle("清除数据").create().show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_words, container, false);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        SearchView bar = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        bar.setMaxWidth(400);
        bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String patten = newText.trim();
                filterWords.removeObservers(requireActivity());
                filterWords = wordViewModel.findWordsWithPatten(patten);
                filterWords.observe(requireActivity(), new Observer<List<Word>>() {
                    @Override
                    public void onChanged(List<Word> words) {
                        int temp = myAdapter1.getItemCount();
                        myAdapter1.setAllWords(words);
                        myAdapter2.setAllWords(words);
                        if (fistOpen || temp != myAdapter1.getItemCount()) {
                            myAdapter2.notifyDataSetChanged();
                            myAdapter1.notifyDataSetChanged();
                            fistOpen = false;
                        }
                    }
                });
                return true;
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wordViewModel = new ViewModelProvider(requireActivity()).get(WordViewModel.class);
        recyclerView = requireActivity().findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        myAdapter1 = new MyAdapter(false, wordViewModel);
        myAdapter2 = new MyAdapter(true, wordViewModel);

        SharedPreferences shp = requireActivity()
                .getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE);
        boolean viewType = shp.getBoolean(IS_USING_CARD_TYPE, false);
        if (viewType) {
            recyclerView.setAdapter(myAdapter2);
        } else {
            recyclerView.setAdapter(myAdapter1);
        }
        filterWords = wordViewModel.getAllWordsLive();
        filterWords.observe(requireActivity(), new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                int temp = myAdapter1.getItemCount();
                myAdapter1.setAllWords(words);
                myAdapter2.setAllWords(words);
                if (fistOpen || temp != myAdapter1.getItemCount()) {
                    myAdapter2.notifyDataSetChanged();
                    myAdapter1.notifyDataSetChanged();
                    fistOpen = false;
                }
            }
        });
        button = requireActivity().findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_wordsFragment_to_addFragment);
            }
        });
    }
}