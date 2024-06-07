package com.example.wizardspotionshop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Clicker extends BaseMainActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    int cliques = 0;
    public void vibra(long tempo) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(tempo);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_clicker);

        ImageButton btn_clicker = findViewById(R.id.btn_porco_clicker);
        TextView txt_cliques = findViewById(R.id.txt_porcentagem);

        btn_clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibra(100);
                if (cliques == 99) {
                    // Define as variáveis do banco ("tabelas")
                    DatabaseReference usuarioBD = referencia.child("usuario");

                    // Recupera o usuário logado
                    DatabaseReference usuarioLogado = usuarioBD.child(auth.getUid());

                    // Desbloqueia o jogo da velha
                    usuarioLogado.child("livre").setValue(true);

                    Intent intent = new Intent(Clicker.this, TelaFinal.class);
                    startActivity(intent);
                    Clicker.this.finish();
                    System.exit(0);
                }
                cliques++;
                txt_cliques.setText(cliques + "%");
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
