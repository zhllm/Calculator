package com.beyourself.calculator;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.beyourself.calculator.DTO.Word;
import com.google.android.material.datepicker.MaterialDatePicker;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    private Button button;
    private EditText english, chinese;
    private WordViewModel wordViewModel;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onStop() {
        super.onStop();
        InputMethodManager manager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(
                requireActivity().findViewById(R.id.fragment2).getWindowToken(),
                0);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button = requireActivity().findViewById(R.id.button17);
        FragmentActivity activity = requireActivity();
        wordViewModel = new ViewModelProvider(requireActivity()).get(WordViewModel.class);
        english = activity.findViewById(R.id.editTextTextPersonName);
        chinese = activity.findViewById(R.id.editTextTextPersonName2);
        button.setEnabled(false);
        english.requestFocus();
        InputMethodManager manager = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(english, 0);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String english_word = english.getText().toString().trim();
                String chinese_word = chinese.getText().toString().trim();
                button.setEnabled(!english_word.isEmpty() && !chinese_word.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        chinese.addTextChangedListener(watcher);
        english.addTextChangedListener(watcher);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String english_word = english.getText().toString().trim();
                String chinese_word = chinese.getText().toString().trim();
                Word word = new Word(english_word, chinese_word);
                wordViewModel.insertWords(word);
                NavController controller = Navigation.findNavController(v);
                controller.navigateUp();
            }
        });
    }
}