package com.example.wizardspotionshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wizardspotionshop.helper.UsuarioDAO;

public class TelaRegistro extends BaseMainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_registro);

        // Recupera o banco de dados
        UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());

        // Define as variáveis dos botões
        Button btnRegistrar = findViewById(R.id.btn_registrar);
        Button btnVoltar = findViewById(R.id.btn_voltar);

        // Define as variáveis dos campos
        EditText edtLogin = findViewById(R.id.edtLogin);
        EditText edtSenha = findViewById(R.id.edtSenha);
        EditText edtSenha2 = findViewById(R.id.edtSenha2);

        // Botão Registar - vai para tela de login
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Recupera os valores digitados
                String login  = edtLogin.getText().toString().trim();
                String senha  = edtSenha.getText().toString().trim();
                String senha2 = edtSenha2.getText().toString().trim();

                /**** Validações de registro ****/
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

                // Verifica se o campo Repita a Senha está preenchido
                if (senha2.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Repita a Senha", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verifica se os campos de senha estão iguais
                if (!senha.equals(senha2)) {
                    Toast.makeText(getApplicationContext(), "As senhas não correspondem", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verifica se o usuário já está cadastrado


                usuarioDAO.registrar(login, senha);

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
