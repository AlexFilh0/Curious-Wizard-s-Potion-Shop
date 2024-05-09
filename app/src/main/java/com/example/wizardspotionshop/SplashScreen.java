package com.example.wizardspotionshop;

import android.content.Intent;
import android.health.connect.datatypes.SleepSessionRecord;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.logging.Handler;

public class SplashScreen extends BaseMainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Button btnMuda = findViewById(R.id.btnMuda);

        btnMuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SplashScreen.this, TelaLogin.class);
                startActivity(intent);

            }
        });



    }

    @Override
    protected void onViewCreated() {

    }

    private int dp2px(double dpValue) {
      final float scale = getResources().getDisplayMetrics().density;
      return (int) (dpValue * scale + 0.5f);
    }
}
