package com.example.wizardspotionshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.wizardspotionshop.helper.UsuarioDAO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Menu extends BaseMainActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

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

        // Define as variáveis do banco ("tabelas")
        DatabaseReference usuarioBD = referencia.child("usuario");

        // Busca usuário logado (SQLite)
        // String usuario = usuarioDAO.retornaUsuarioLog();

        // Recupera o usuário logado
        DatabaseReference usuarioLogado = usuarioBD.child(auth.getUid());
        usuarioLogado.child("usuario").addValueEventListener(new ValueEventListener() {
            String usuario = "";
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue().toString();
                // Mostra o nome do usuário
                txt_welcome.setText("Bem-vindo, " + usuario.toUpperCase() +"!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                // Desloga o usuário
                // usuarioDAO.sair();
                auth.signOut();

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
