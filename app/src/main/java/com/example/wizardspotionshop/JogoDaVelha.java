package com.example.wizardspotionshop;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class JogoDaVelha extends BaseMainActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    private Game game;
    private Bot bot;

    public void vibra(long tempo) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(tempo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_velha);

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

        // Criar instância do jogo
        game = new Game();
        bot = new Bot();

        // Atribuir listeners de clique aos botões
        setButtonClickListeners();
    }


    @Override
    protected void onViewCreated() {

    }

    private void setButtonClickListeners() {
        Button btn_00 = findViewById(R.id.btn_00);
        Button btn_01 = findViewById(R.id.btn_01);
        Button btn_02 = findViewById(R.id.btn_02);
        Button btn_10 = findViewById(R.id.btn_10);
        Button btn_11 = findViewById(R.id.btn_11);
        Button btn_12 = findViewById(R.id.btn_12);
        Button btn_20 = findViewById(R.id.btn_20);
        Button btn_21 = findViewById(R.id.btn_21);
        Button btn_22 = findViewById(R.id.btn_22);
        TextView txt_vez = findViewById(R.id.txtVez);

        btn_00.setOnClickListener(view -> onButtonClick(0, 0, btn_00, txt_vez));
        btn_01.setOnClickListener(view -> onButtonClick(0, 1, btn_01, txt_vez));
        btn_02.setOnClickListener(view -> onButtonClick(0, 2, btn_02, txt_vez));
        btn_10.setOnClickListener(view -> onButtonClick(1, 0, btn_10, txt_vez));
        btn_11.setOnClickListener(view -> onButtonClick(1, 1, btn_11, txt_vez));
        btn_12.setOnClickListener(view -> onButtonClick(1, 2, btn_12, txt_vez));
        btn_20.setOnClickListener(view -> onButtonClick(2, 0, btn_20, txt_vez));
        btn_21.setOnClickListener(view -> onButtonClick(2, 1, btn_21, txt_vez));
        btn_22.setOnClickListener(view -> onButtonClick(2, 2, btn_22, txt_vez));
        vibra(100);

        Button btn_sair = findViewById(R.id.btn_sair_velha);
        Button btn_reiniciar = findViewById(R.id.btn_reiniciar_velha);


    }

    public void desabilitarBotao() {

        Button btn_00 = findViewById(R.id.btn_00);
        Button btn_01 = findViewById(R.id.btn_01);
        Button btn_02 = findViewById(R.id.btn_02);
        Button btn_10 = findViewById(R.id.btn_10);
        Button btn_11 = findViewById(R.id.btn_11);
        Button btn_12 = findViewById(R.id.btn_12);
        Button btn_20 = findViewById(R.id.btn_20);
        Button btn_21 = findViewById(R.id.btn_21);
        Button btn_22 = findViewById(R.id.btn_22);

        Button btn_sair = findViewById(R.id.btn_sair_velha);
        Button btn_reiniciar = findViewById(R.id.btn_reiniciar_velha);

        btn_sair.setVisibility(View.VISIBLE);
        btn_reiniciar.setVisibility(View.VISIBLE);

        btn_00.setClickable(false);
        btn_01.setClickable(false);
        btn_02.setClickable(false);
        btn_10.setClickable(false);
        btn_11.setClickable(false);
        btn_12.setClickable(false);
        btn_21.setClickable(false);
        btn_22.setClickable(false);
        btn_20.setClickable(false);

        btn_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibra(100);
                Intent i = new Intent(JogoDaVelha.this, TelaPrincipal.class);
                startActivity(i);
            }
        });

        btn_reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibra(100);
                btn_00.setText("");
                btn_01.setText("");
                btn_02.setText("");
                btn_10.setText("");
                btn_11.setText("");
                btn_12.setText("");
                btn_21.setText("");
                btn_22.setText("");
                btn_20.setText("");
                btn_sair.setVisibility(View.INVISIBLE);
                btn_reiniciar.setVisibility(View.INVISIBLE);

                game = new Game();
                bot = new Bot();

                // Atribuir listeners de clique aos botões
                setButtonClickListeners();
            }
        });
    }

    private void onButtonClick(int row, int col, Button button, TextView txt_vez) {

        if (game.makeMove(row, col)) {
            button.setText(String.valueOf(game.getCurrentPlayer()));
            vibra(100);

            // Verificar se há um vencedor após a jogada do jogador humano
            char winner = game.checkWinner();
            if (winner != '-') {
                String message;
                if (winner == 'D') {
                    message = "Empate!";
                } else {
                    if (game.getCurrentPlayer() == 'X') {
                        winner = 'X';
                        message = "Você ganhou!";
                        // Define as variáveis do banco ("tabelas")
                        DatabaseReference usuarioBD = referencia.child("usuario");

                        // Recupera o usuário logado
                        DatabaseReference usuarioLogado = usuarioBD.child(auth.getUid());



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
                        final MediaPlayer alerta = MediaPlayer.create(this, R.raw.alert);
                        alerta.start();
                        vibra(50);
                        vibra(100);

                        Toast.makeText(this, "Clicker desbloqueado!", Toast.LENGTH_SHORT).show();

                        // Desbloqueia o clicker
                        usuarioLogado.child("clicker").setValue(true);

                    }
                    else {
                        winner = 'O';
                        message = "Você perdeu!";
                    }

                }

                vibra(200);
                txt_vez.setText(message); // Exibir o vencedor no TextView
                Handler espera2 = new Handler();
                espera2.postDelayed(this::desabilitarBotao, 1000);



                //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            } else {
                // Se não houver vencedor, é a vez do bot jogar
                Handler handler = new Handler();
                handler.postDelayed(this::botMove, 1000);
            }
        } else {
            Toast.makeText(this, "Jogada inválida!", Toast.LENGTH_SHORT).show();
        }
    }

    private void botMove() {
        char[][] board = game.getBoard();
        int[] move = bot.makeMove(board);
        int row = move[0];
        int col = move[1];
        Button button = getButtonByRowCol(row, col);
        if (button != null) {
            if (game.makeMove(row, col)) {
                button.setText(String.valueOf(game.getCurrentPlayer()));

                // Verificar se há um vencedor após a jogada do bot
                char winner = game.checkWinner();
                if (winner != '-') {
                    String message;
                    if (winner == 'D') {
                        message = "Empate!";
                    } else {
                        if (game.getCurrentPlayer() == 'X') {
                            winner = 'X';
                            message = "Você ganhou!";
                        }
                        else {
                            winner = 'O';
                            message = "Você perdeu!";
                        }

                    }

                    vibra(200);
                    // Atualizar o TextView mesmo quando o bot ganha
                    TextView txt_vez = findViewById(R.id.txtVez);
                    txt_vez.setText(message);
                    Handler espera = new Handler();
                    espera.postDelayed(this::desabilitarBotao, 1000);


                }
            }
        }
    }
    private Button getButtonByRowCol(int row, int col) {
        switch (row) {
            case 0:
                switch (col) {
                    case 0: return findViewById(R.id.btn_00);
                    case 1: return findViewById(R.id.btn_01);
                    case 2: return findViewById(R.id.btn_02);
                }
                break;
            case 1:
                switch (col) {
                    case 0: return findViewById(R.id.btn_10);
                    case 1: return findViewById(R.id.btn_11);
                    case 2: return findViewById(R.id.btn_12);
                }
                break;
            case 2:
                switch (col) {
                    case 0: return findViewById(R.id.btn_20);
                    case 1: return findViewById(R.id.btn_21);
                    case 2: return findViewById(R.id.btn_22);
                }
                break;
        }
        return null; // Se a linha ou coluna estiver fora do intervalo
    }
}

class Game {
        private char[][] board;
        private char currentPlayer;

        public Game() {
            board = new char[3][3];
            currentPlayer = 'O';
            initializeBoard();
        }

        private void initializeBoard() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = '-';
                }
            }
        }

        public boolean makeMove(int row, int col) {
            if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != '-') {
                return false;
            }

            board[row][col] = currentPlayer;
            currentPlayer = (currentPlayer == 'O') ? 'X' : 'O'; // Troca de jogador após a jogada
            return true;
            }
        public char getCurrentPlayer() {
            return currentPlayer;
        }
        public char checkWinner() {
            // Check rows
            for (int i = 0; i < 3; i++) {
                if (board[i][0] != '-' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                    return board[i][0];
                }
            }
            // Check columns
            for (int j = 0; j < 3; j++) {
                if (board[0][j] != '-' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                    return board[0][j];
                }
            }
            // Check diagonals
            if (board[0][0] != '-' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
                return board[0][0];
            }
            if (board[0][2] != '-' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
                return board[0][2];
            }
            // Check for draw
            boolean isDraw = true;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        isDraw = false;
                        break;
                    }
                }
            }
            if (isDraw) {
                return 'D';
            }
            return '-';
        }



    public char[][] getBoard() {
        return board;
    }

}

class Bot {
    private Random random;

    public Bot() {
        random = new Random();
    }

    public int[] makeMove(char[][] board) {
        int[] move = new int[2];
        int row, col;

        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != '-');

        move[0] = row;
        move[1] = col;

        return move;
    }
}





