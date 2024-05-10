package com.example.wizardspotionshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TelaLogin extends BaseMainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);

        // Define as variáveis dos botões
        Button btnEntrar = findViewById(R.id.btn_entrar);
        Button btnRegistrar = findViewById(R.id.btn_registrar);

        // Botão Entrar - vai para tela principal
        btnEntrar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 // Adicionar validações de login
                 Intent intent = new Intent(TelaLogin.this, TelaPrincipal.class);
                 startActivity(intent);
             }
         });

        // Botão Registar - vai para tela principal
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaLogin.this, TelaRegistro.class);
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
