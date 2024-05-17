package com.example.wizardspotionshop;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wizardspotionshop.helper.DbHelper;

import com.example.wizardspotionshop.helper.DbHelper;
import com.example.wizardspotionshop.helper.UsuarioDAO;

public class TelaLogin extends BaseMainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);

        // Recupera o banco de dados
        UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());

        // Define as variáveis dos botões
        Button btnEntrar = findViewById(R.id.btn_entrar);
        Button btnRegistrar = findViewById(R.id.btn_registrar);

        // Define as variáveis dos campos
        EditText edtLogin = findViewById(R.id.edtLogin);
        EditText edtSenha = findViewById(R.id.edtSenha);

        // Botão Entrar - vai para tela principal
        btnEntrar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 // Recupera os valores digitados
                 String login = edtLogin.getText().toString().trim();
                 String senha = edtSenha.getText().toString().trim();

                 /**** Validações de login ****/
                 // Verifica se o campo Login está preenchido
                 if (login.isEmpty()) {
                     Toast.makeText(getApplicationContext(), "Digite o Usuário", Toast.LENGTH_SHORT).show();
                     return;
                 }

                 // Verifica se o campo Senha está preenchido
                 if (senha.isEmpty()) {
                     Toast.makeText(getApplicationContext(), "Digite a Senha", Toast.LENGTH_SHORT).show();
                     return;
                 }

                 // Faz login
                 if (usuarioDAO.entrar(login, senha)) {
                     Intent intent = new Intent(TelaLogin.this, TelaPrincipal.class);
                     startActivity(intent);
                 }
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
