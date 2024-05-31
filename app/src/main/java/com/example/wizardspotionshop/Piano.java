package com.example.wizardspotionshop;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;


import java.util.*;

public class Piano extends BaseMainActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    String notes[] = new String[] {"c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "b"};
    List<String> notaTocada = new ArrayList<String>();

    String[] seq = sequencia();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.tela_piano);

        // Define as variáveis dos campos
        TextView txtPontuacao = findViewById(R.id.txtPontuacao);

        // Define as variáveis do banco ("tabelas")
        DatabaseReference usuarioBD = referencia.child("usuario");

        // Recupera o usuário logado
        DatabaseReference usuarioLogado = usuarioBD.child(auth.getUid());

        // Recupera a pontuação total do usuário
        usuarioLogado.child("pontuacao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtPontuacao.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String[] sequencia() {
        Random nota = new Random();
        String seq[] = new String[] {notes[nota.nextInt(12)], notes[nota.nextInt(12)], notes[nota.nextInt(12)]};

        return seq;
    }

    public void gameLoop() throws InterruptedException {
        TextView txtNota = findViewById(R.id.txtNotas);

        txtNota.setText("Notas: " + seq[0] + " " + seq[1] + " "+ seq[2]);
        //tocar as notas
        for (int i = 0; i <= 2; i++) {
            switch (seq[i]) {
                case "c":
                    final MediaPlayer c = MediaPlayer.create(this, R.raw.cnote);
                    c.start();
                    break;
                case "c#":
                    final MediaPlayer cc = MediaPlayer.create(this, R.raw.csustnote);
                    cc.start();
                    break;
                case "d":
                    final MediaPlayer d = MediaPlayer.create(this, R.raw.dnote);
                    d.start();
                    break;
                case "d#":
                    final MediaPlayer dd = MediaPlayer.create(this, R.raw.dsustnote);
                    dd.start();
                    break;
                case "e":
                    final MediaPlayer e = MediaPlayer.create(this, R.raw.enote);
                    e.start();
                    break;
                case "f":
                    final MediaPlayer f = MediaPlayer.create(this, R.raw.fnote);
                    f.start();
                    break;
                case "f#":
                    final MediaPlayer ff = MediaPlayer.create(this, R.raw.fsustnote);
                    ff.start();
                    break;
                case "g":
                    final MediaPlayer g = MediaPlayer.create(this, R.raw.gnote);
                    g.start();
                    break;
                case "g#":
                    final MediaPlayer gg = MediaPlayer.create(this, R.raw.gsustnote);
                    gg.start();
                    break;
                case "a":
                    final MediaPlayer a = MediaPlayer.create(this, R.raw.anote);
                    a.start();
                    break;
                case "a#":
                    final MediaPlayer aa = MediaPlayer.create(this, R.raw.asustnote);
                    aa.start();
                    break;
                case "b":
                    final MediaPlayer b = MediaPlayer.create(this, R.raw.bnote);
                    b.start();
                    break;
            }
            Thread.sleep(1000);
        }
    }

    public void isCorrect() {
        Button btnPiano = findViewById(R.id.btn_piano_start);
        //Verifica se está correto
        if (notaTocada.size() == 3) {
            String[] nTocadas = new String[notaTocada.size()];
            notaTocada.toArray(nTocadas);

            if (Arrays.equals(nTocadas, seq)) {
                btnPiano.setText("FOI");
                notaTocada.clear();
                seq = sequencia();

                // Define as variáveis do banco ("tabelas")
                DatabaseReference usuarioBD = referencia.child("usuario");

                // Recupera o usuário logado
                DatabaseReference usuarioLogado = usuarioBD.child(auth.getUid());

                // Desbloqueia o jogo da cobrinha
                usuarioLogado.child("snake").setValue(true);

                /* PONTUACAO */
                // Define as variáveis dos campos
                TextView txtPontuacao = findViewById(R.id.txtPontuacao);

                // Define variáveis de controle
                int pontuacao;

                // Atualiza a pontuação
                pontuacao = Integer.parseInt(txtPontuacao.getText().toString());
                pontuacao += 5;

                //txtPontuacao.setText(Integer.toString(pontuacao));
                usuarioLogado.child("pontuacao").setValue(pontuacao);

                /* FIM PONTUACAO */
            }
            else {
                btnPiano.setText("NÃO");
                notaTocada.clear();
                seq = sequencia();
            }
        }
    }

    public void clickStart(View v) {
        try {
            gameLoop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void onViewCreated() {
        
    }

    private int dp2px(double dpValue) {
      final float scale = getResources().getDisplayMetrics().density;
      return (int) (dpValue * scale + 0.5f);
    }


    public void clickC(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cnote);
        mp.start();
        notaTocada.add("c");
        isCorrect();
    }
    
    public void clickCSust(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.csustnote);
        mp.start();
        notaTocada.add("c#");
        isCorrect();
    }

    public void clickD(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.dnote);
        mp.start();
        notaTocada.add("d");
        isCorrect();
    }

    public void clickDSust(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.dsustnote);
        mp.start();
        notaTocada.add("d#");
        isCorrect();
    }

    public void clickE(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.enote);
        mp.start();
        notaTocada.add("e");
        isCorrect();
    }

    public void clickF(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.fnote);
        mp.start();
        notaTocada.add("f");
        isCorrect();
    }

    public void clickFSust(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.fsustnote);
        mp.start();
        notaTocada.add("f#");
        isCorrect();
    }

    public void clickG(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.gnote);
        mp.start();
        notaTocada.add("g");
        isCorrect();
    }

    public void clickGSust(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.gsustnote);
        mp.start();
        notaTocada.add("g#");
        isCorrect();
    }

    public void clickA(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.anote);
        mp.start();
        notaTocada.add("a");
        isCorrect();
    }

    public void clickASust(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.asustnote);
        mp.start();
        notaTocada.add("a#");
        isCorrect();
    }

    public void clickB(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.bnote);
        mp.start();
        notaTocada.add("b");
        isCorrect();
    }
}
