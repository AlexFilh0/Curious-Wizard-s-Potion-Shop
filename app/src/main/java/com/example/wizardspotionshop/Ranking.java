package com.example.wizardspotionshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Ranking extends BaseMainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_ranking);

        Button btn_voltar = findViewById(R.id.btn_voltar);

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ranking.this, Menu.class);
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
