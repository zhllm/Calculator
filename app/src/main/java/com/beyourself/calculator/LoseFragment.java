package com.beyourself.calculator;

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

import com.beyourself.calculator.databinding.FragmentLoseBinding;
import com.beyourself.calculator.databinding.FragmentWinBinding;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LoseFragment extends Fragment {

    public LoseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MyViewModel myViewModel = new ViewModelProvider(
                requireActivity(),
                new SavedStateViewModelFactory(requireActivity().getApplication(), this)
        ).get(MyViewModel.class);
        FragmentLoseBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_lose,
                container,
                false
        );
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        binding.button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_loseFragment_to_titleFragment);
            }
        });
        return binding.getRoot();
    }
}