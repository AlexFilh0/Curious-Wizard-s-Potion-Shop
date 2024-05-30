package com.example.wizardspotionshop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.wizardspotionshop.helper.UsuarioDAO;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends BaseMainActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_splash);

        // Recupera o banco de dados
        UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;

                // Verifica se tem usuário logado
               /* if(usuarioDAO.verificarLogado()) {
                    // Abre direto na tela principal se tiver usuário logado
                    intent = new Intent(SplashScreen.this, TelaPrincipal.class);
                } else {
                    // Abre na tela de login se não tiver usuário logado
                    intent = new Intent(SplashScreen.this, TelaLogin.class);
                } */

                if (auth.getCurrentUser() != null) {
                    // Abre direto na tela principal se tiver usuário logado
                    intent = new Intent(SplashScreen.this, TelaPrincipal.class);
                } else {
                    // Abre na tela de login se não tiver usuário logado
                    intent = new Intent(SplashScreen.this, TelaLogin.class);
                }

                startActivity(intent);

                finish();
            }
        }, 2000);

    }

    @Override
    protected void onViewCreated() {

    }

    private int dp2px(double dpValue) {
      final float scale = getResources().getDisplayMetrics().density;
      return (int) (dpValue * scale + 0.5f);
    }
}
