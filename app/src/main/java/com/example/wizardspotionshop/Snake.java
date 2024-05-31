package com.example.wizardspotionshop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snake extends BaseMainActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();


    private FrameLayout frameLayout;
    private ImageView snakeHead;
    private ImageView food;
    private TextView scoreText;
    private Handler handler = new Handler();

    private int screenWidth;
    private int screenHeight;

    private int snakeX, snakeY;
    private int foodX, foodY;
    private int score = 0;

    private final int blockSize = 50;
    private final int numBlocksWide = 14;
    private final int numBlocksHigh = 30;

    private List<ImageView> snakeBody = new ArrayList<>();

    enum Direction {UP, DOWN, LEFT, RIGHT}
    private Direction currentDirection = Direction.RIGHT;


    public void vibra(long tempo) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(tempo);
    }

    public void morreu() {
        Button btn_reiniciar = findViewById(R.id.btn_reiniciar);
        Button btn_sair = findViewById(R.id.btn_sair_snake);

        btn_sair.setVisibility(View.VISIBLE);
        btn_reiniciar.setVisibility(View.VISIBLE);

        // Define as variáveis do banco ("tabelas")
        DatabaseReference usuarioBD = referencia.child("usuario");

        // Recupera o usuário logado
        DatabaseReference usuarioLogado = usuarioBD.child(auth.getUid());

        if (score >= 2) { // MUDAR SCORE
            // Desbloqueia o jogo da velha
            usuarioLogado.child("velha").setValue(true);
        }

        /* PONTUACAO */
        // Define as variáveis dos campos
        TextView txtPontuacao = findViewById(R.id.txtPontuacao);

        // Define variáveis de controle
        int pontuacao;

        // Atualiza a pontuação
        pontuacao = Integer.parseInt(txtPontuacao.getText().toString());
        pontuacao += score;

        //txtPontuacao.setText(Integer.toString(pontuacao));
        usuarioLogado.child("pontuacao").setValue(pontuacao);

        /* FIM PONTUACAO */

        btn_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Snake.this, TelaPrincipal.class);
                startActivity(i);
            }
        });
        btn_reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_reiniciar.setVisibility(View.INVISIBLE);
                btn_sair.setVisibility(View.INVISIBLE);
                endGame();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_snake);


        frameLayout = findViewById(R.id.frame_layout);
        snakeHead = findViewById(R.id.snake_head);
        food = findViewById(R.id.snake_food);
        scoreText = findViewById(R.id.txtPlacar);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        ImageButton btn_up = findViewById(R.id.btn_up);
        ImageButton btn_down = findViewById(R.id.btn_down);
        ImageButton btn_right = findViewById(R.id.btn_right);
        ImageButton btn_left = findViewById(R.id.btn_left);

        Button btn_reiniciar = findViewById(R.id.btn_reiniciar);

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


        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentDirection != Direction.DOWN)
                    currentDirection = Direction.UP;
                vibra(80);
            }

        });

        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentDirection != Direction.UP)
                    currentDirection = Direction.DOWN;
                vibra(80);
            }
        });

        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO", String.valueOf(snakeY));
                if (currentDirection != Direction.LEFT)
                    currentDirection = Direction.RIGHT;
                vibra(80);
            }
        });

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentDirection != Direction.RIGHT)
                    currentDirection = Direction.LEFT;
                vibra(80);
            }
        });

        startGame();
    }


    @Override
    protected void onViewCreated() {
        // Ensure you implement any additional setup after view creation if needed
    }

    private void startGame() {
        spawnSnake();
        spawnFood();
        score = 0;
        updateScore();
        moveSnake();
    }

    private void spawnSnake() {
        snakeX = numBlocksWide / 2 * blockSize;
        snakeY = numBlocksHigh / 2 * blockSize;
        snakeHead.setX(snakeX);
        snakeHead.setY(snakeY);


        // Initialize snake body
        snakeBody.clear();
        snakeBody.add(snakeHead);
    }

    private void spawnFood() {
        Random random = new Random();
        foodX = random.nextInt(numBlocksWide) * blockSize;
        foodY = random.nextInt(numBlocksHigh) * blockSize;
        food.setX(foodX);
        food.setY(foodY);
    }

    private void moveSnake() {
        handler.postDelayed(new Runnable() {
            @Override

            public void run() {
                for (int i = snakeBody.size() - 1; i > 0; i--) {
                    ImageView currentPart = snakeBody.get(i);
                    ImageView previousPart = snakeBody.get(i - 1);
                    currentPart.setX(previousPart.getX());
                    currentPart.setY(previousPart.getY());
                }

                switch (currentDirection) {
                    case UP:
                        snakeY -= blockSize;
                        break;
                    case DOWN:
                        snakeY += blockSize;
                        break;
                    case LEFT:
                        snakeX -= blockSize;
                        break;
                    case RIGHT:
                        snakeX += blockSize;
                        break;
                }

                // Check for collisions with walls
                if (snakeX >= frameLayout.getWidth()-17 || snakeX < 0 || snakeY >= frameLayout.getHeight()-snakeHead.getHeight() || snakeY < 0) {
                    vibra(100);
                    morreu();
                    return;
                }

                // Check for collisions with itself
                for (int i = 1; i < snakeBody.size(); i++) {
                    ImageView part = snakeBody.get(i);
                    if (Math.abs(snakeX - part.getX()) < blockSize / 3 && Math.abs(snakeY - part.getY()) < blockSize / 3) {
                        vibra(100);
                        morreu();
                        return;
                    }
                }

                // Check for collisions with food
                if (Math.abs(snakeX - foodX) < blockSize / 3 && Math.abs(snakeY - foodY) < blockSize / 3) {
                    score++;
                    vibra(100);
                    updateScore();
                    spawnFood();
                    growSnake();
                }

                snakeHead.setX(snakeX);
                snakeHead.setY(snakeY);

                handler.postDelayed(this, 250); // speed of the snake
            }
        }, 250);
    }

    private void growSnake() {
        ImageView newPart = new ImageView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(63, 63);
        newPart.setLayoutParams(params);
        newPart.setImageResource(R.drawable.container_frame1); // This should be your 50x50 image
        frameLayout.addView(newPart);
        snakeBody.add(newPart);
    }

    private void updateScore() {
        scoreText.setText("Score: " + score);
    }


    private void endGame() {
        // Remove all body parts except the head
        for (int i = 1; i < snakeBody.size(); i++) {
            frameLayout.removeView(snakeBody.get(i));
        }
        snakeBody.clear();

        scoreText.setText("VOCÊ MORREU!");

        snakeBody.add(snakeHead);
        startGame();
    }
}
