package com.example.wizardspotionshop;

import static android.graphics.Color.rgb;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.wizardspotionshop.BaseMainActivity;
import com.example.wizardspotionshop.JogoDaVelha;
import com.example.wizardspotionshop.Menu;
import com.example.wizardspotionshop.R;
import com.example.wizardspotionshop.Snake;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TelaPrincipal extends BaseMainActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_principal);

        // Define as variáveis dos botões
        ImageButton btn_menu = findViewById(R.id.ic_menu);
        ImageButton btn_velha = findViewById(R.id.btn_velha);
        ImageButton btn_snake = findViewById(R.id.btn_snake);
        ImageButton btn_piano = findViewById(R.id.btn_piano);
        ImageButton btn_clicker = findViewById(R.id.btn_clicker);

        // Define as variáveis do banco ("tabelas")
        DatabaseReference usuarioBD = referencia.child("usuario");

        // Recupera o usuário logado
        DatabaseReference usuarioLogado = usuarioBD.child(auth.getUid());

        // Verifica se o jogo "Snake" está liberado
        usuarioLogado.child("snake").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean liberado = Boolean.parseBoolean(snapshot.getValue().toString());

                btn_snake.setEnabled(liberado);

                if (liberado) {
                    btn_snake.setColorFilter(null);
                } else {
                    btn_snake.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Verifica se o jogo "Velha" está liberado
        usuarioLogado.child("velha").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean liberado = Boolean.parseBoolean(snapshot.getValue().toString());

                btn_velha.setEnabled(liberado);

                if (liberado) {
                    btn_velha.setColorFilter(null);
                } else {
                    btn_velha.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Verifica se o jogo "Clicker" está liberado
        usuarioLogado.child("clicker").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean liberado = Boolean.parseBoolean(snapshot.getValue().toString());

                btn_clicker.setEnabled(liberado);

                if (liberado) {
                    btn_clicker.setColorFilter(null);
                } else {
                    btn_clicker.setColorFilter(Color.rgb(210, 105, 230), PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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
