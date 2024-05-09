package com.example.wizardspotionshop;

import android.os.Bundle;

public class JogoDaVelha extends BaseMainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_jogo_da_velha);
    }

    @Override
    protected void onViewCreated() {
        
    }

    private int dp2px(double dpValue) {
      final float scale = getResources().getDisplayMetrics().density;
      return (int) (dpValue * scale + 0.5f);
    }
}
