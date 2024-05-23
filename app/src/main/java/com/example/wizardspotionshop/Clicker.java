package com.example.wizardspotionshop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;

public class Clicker extends BaseMainActivity {
    int cliques = 0;
    public void vibra(long tempo) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(tempo);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_clicker);

        ImageButton btn_clicker = findViewById(R.id.btn_porco_clicker);
        TextView txt_cliques = findViewById(R.id.txt_porcentagem);

        btn_clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibra(100);
                if (cliques == 99) {
                    Intent intent = new Intent(Clicker.this, TelaFinal.class);
                    startActivity(intent);
                }
                cliques++;
                txt_cliques.setText(cliques + "%");
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
