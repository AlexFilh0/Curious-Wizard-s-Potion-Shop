package com.example.wizardspotionshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TelaRegistro extends BaseMainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_registro);

        // Define as variáveis dos botões
        Button btnRegistrar = findViewById(R.id.btn_registrar);
        Button btnVoltar = findViewById(R.id.btn_voltar);

        // Botão Registar - vai para tela de login
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Adicionar validações de registro
                Intent intent = new Intent(TelaRegistro.this, TelaLogin.class);
                startActivity(intent);
            }
        });

        // Botão Voltar - vai para tela de login
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaRegistro.this, TelaLogin.class);
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
