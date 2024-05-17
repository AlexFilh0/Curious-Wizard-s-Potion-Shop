package com.example.wizardspotionshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.wizardspotionshop.BaseMainActivity;
import com.example.wizardspotionshop.JogoDaVelha;
import com.example.wizardspotionshop.Menu;
import com.example.wizardspotionshop.R;
import com.example.wizardspotionshop.Snake;

public class TelaPrincipal extends BaseMainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_principal);

        ImageButton btn_menu = findViewById(R.id.ic_menu);
        ImageButton btn_velha = findViewById(R.id.btn_velha);
        ImageButton btn_snake = findViewById(R.id.btn_snake);
        ImageButton btn_piano = findViewById(R.id.btn_piano);
        ImageButton btn_clicker = findViewById(R.id.btn_clicker);

        btn_clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mudar_clicker = new Intent(TelaPrincipal.this, Clicker.class);
                startActivity(mudar_clicker);
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mudar_menu = new Intent(TelaPrincipal.this, Menu.class);
                startActivity(mudar_menu);
            }
        });

        btn_velha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mudar_velha = new Intent(TelaPrincipal.this, JogoDaVelha.class);
                startActivity(mudar_velha);
            }
        });

        btn_snake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mudar_menu = new Intent(TelaPrincipal.this, Snake.class);
                startActivity(mudar_menu);
            }
        });

        btn_piano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mudar_menu = new Intent(TelaPrincipal.this, Piano.class);
                startActivity(mudar_menu);
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
