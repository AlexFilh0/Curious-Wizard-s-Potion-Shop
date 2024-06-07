package com.example.wizardspotionshop;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

public class TelaFinal extends BaseMainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_final);

        Button btn_sair = findViewById(R.id.btn_sair_final);
        Button btn_modo_livre = findViewById(R.id.btn_modo_livre);

        btn_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelaFinal.this.finish();

                System.exit(0);
            }
        });

        btn_modo_livre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TelaFinal.this, TelaPrincipal.class);
                startActivity(i);
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
