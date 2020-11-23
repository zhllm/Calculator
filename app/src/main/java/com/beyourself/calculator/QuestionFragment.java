package com.beyourself.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beyourself.calculator.databinding.FragmentQuestionBinding;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MyViewModel myViewModel = new ViewModelProvider(
                requireActivity(),
                new SavedStateViewModelFactory(requireActivity().getApplication(), this)
        ).get(MyViewModel.class);
        myViewModel.generator();
        FragmentQuestionBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_question,
                container,
                false
        );
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        StringBuilder builder = new StringBuilder();
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button3:
                        builder.append("1");
                        break;
                    case R.id.button4:
                        builder.append("2");
                        break;
                    case R.id.button5:
                        builder.append("3");
                        break;
                    case R.id.button6:
                        builder.append("4");
                        break;
                    case R.id.button7:
                        builder.append("5");
                        break;
                    case R.id.button8:
                        builder.append("6");
                        break;
                    case R.id.button9:
                        builder.append("7");
                        break;
                    case R.id.button10:
                        builder.append("8");
                        break;
                    case R.id.button11:
                        builder.append("9");
                        break;
                    case R.id.button12:
                        builder.setLength(0);
                        break;
                    case R.id.button13:
                        if (builder.length() > 0) {
                            builder.append("0");
                        }
                        break;
//                    case R.id.button14:
//                        builder.append("0");
//                        break;
                    default:
                        break;
                }
                if (builder.length() == 0) {
                    binding.textView8.setText(R.string.input_indicator);
                } else {
                    binding.textView8.setText(builder.toString());
                }
            }
        };

        binding.button3.setOnClickListener(listener); // 1
        binding.button4.setOnClickListener(listener); // 2
        binding.button5.setOnClickListener(listener); // 3
        binding.button6.setOnClickListener(listener); // 4
        binding.button7.setOnClickListener(listener); // 5
        binding.button8.setOnClickListener(listener); // 6
        binding.button9.setOnClickListener(listener); // 7
        binding.button10.setOnClickListener(listener); // 8
        binding.button11.setOnClickListener(listener); // 9
        binding.button12.setOnClickListener(listener); // c
        binding.button13.setOnClickListener(listener); // 0
        // binding.button14.setOnClickListener(listener); // ok
        binding.button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        builder.length() > 0 && Integer.valueOf(builder.toString()).intValue() == myViewModel.getAnswer().getValue()
                ) {
                    myViewModel.answerCorrect();
                    builder.setLength(0);
                    binding.textView8.setText(R.string.answer_correct);
                } else {
                    NavController controller = Navigation.findNavController(v);
                    if (myViewModel.win_flag) {
                        controller.navigate(R.id.action_questionFragment_to_winFragment);
                        myViewModel.win_flag = false;
                        myViewModel.save();
                    } else {
                        controller.navigate(R.id.action_questionFragment_to_loseFragment);
                    }
                }
            }
        });
        return binding.getRoot();
    }
}