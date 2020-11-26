package com.beyourself.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

public class MainActivity_Room_Data_Base extends AppCompatActivity {
    private NavController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__room__data__base);
        controller = Navigation.findNavController(findViewById(R.id.fragment2));
        NavigationUI.setupActionBarWithNavController(this, controller);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onSupportNavigateUp() {
        controller.navigateUp();
        return super.onSupportNavigateUp();
    }
}