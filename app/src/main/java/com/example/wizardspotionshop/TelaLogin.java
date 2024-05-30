package com.example.wizardspotionshop;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.wizardspotionshop.helper.DbHelper;

import com.example.wizardspotionshop.helper.DbHelper;
import com.example.wizardspotionshop.helper.UsuarioDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TelaLogin extends BaseMainActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();

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
                     Toast.makeText(getApplicationContext(), "Digite o E-mail", Toast.LENGTH_SHORT).show();
                     return;
                 }

                 // Verifica se o campo Senha está preenchido
                 if (senha.isEmpty()) {
                     Toast.makeText(getApplicationContext(), "Digite a Senha", Toast.LENGTH_SHORT).show();
                     return;
                 }

                 // Faz login
                 /*if (usuarioDAO.entrar(login, senha)) {
                     Intent intent = new Intent(TelaLogin.this, TelaPrincipal.class);
                     startActivity(intent);
                 } */

                 // Login (Firebase)
                 auth.signInWithEmailAndPassword(login, senha).addOnCompleteListener(TelaLogin.this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             Toast.makeText(getApplicationContext(), "Bem-vindo!", Toast.LENGTH_SHORT).show();

                             // Vai para tela principal
                             Intent intent = new Intent(TelaLogin.this, TelaPrincipal.class);
                             startActivity(intent);
                         } else {
                             Toast.makeText(getApplicationContext(), "Usuário/Senha incorretos", Toast.LENGTH_SHORT).show();
                             Log.i("Login", "Ocorreram os seguintes problemas: " + task.getException());
                         }
                     }
                 });
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
