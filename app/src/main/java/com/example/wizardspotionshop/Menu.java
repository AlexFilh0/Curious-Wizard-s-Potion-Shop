package com.example.wizardspotionshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wizardspotionshop.helper.UsuarioDAO;

public class Menu extends BaseMainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_menu);

        // Recupera o banco de dados
        UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());

        // Define as variáveis dos botões
        Button btn_ranking = findViewById(R.id.btn_ranking);
        Button btn_sair = findViewById(R.id.btn_sair);
        Button btn_voltar = findViewById(R.id.btn_voltar);

        // Define as variáveis dos campos
        TextView txt_welcome = findViewById(R.id.txt_welcome);

        // Busca usuário logado
        String usuario = usuarioDAO.retornaUsuarioLog();

        // Mostra o nome do usuário
        txt_welcome.setText("Bem Vindo, " + usuario);

        btn_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Ranking.class);
                startActivity(intent);
            }
        });

        // Sair (deslogar)
        btn_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Exclui dados da tabela de usuário logado
                usuarioDAO.sair();

                Intent intent = new Intent(Menu.this, TelaLogin.class);
                startActivity(intent);
            }
        });

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, TelaPrincipal.class);
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

    public void abreEasterEgg(View v) {
        Intent intent = new Intent(Menu.this, EasterEgg.class);
        startActivity(intent);
    }
}
