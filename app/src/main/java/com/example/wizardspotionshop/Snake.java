package com.example.wizardspotionshop;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Snake extends BaseMainActivity {

    private FrameLayout frameLayout;
    private ImageView snakeHead;
    private TextView scoreText;
    private Handler handler = new Handler();

    private int screenWidth;
    private int screenHeight;

    private int snakeX, snakeY;
    private int foodX, foodY;
    private int score = 0;

    private final int blockSize = 50; // tamanho do bloco da grade
    private final int numBlocksWide = 20; // número de blocos na largura da tela
    private final int numBlocksHigh = 30; // número de blocos na altura da tela

    enum Direction {UP, DOWN, LEFT, RIGHT}
    private Direction currentDirection = Direction.RIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_snake);

        frameLayout = findViewById(R.id.frame_layout);
        snakeHead = findViewById(R.id.snake_head);
        scoreText = findViewById(R.id.txtPlacar);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        startGame();
    }

    @Override
    protected void onViewCreated() {

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
    }

    private void spawnFood() {
        Random random = new Random();
        foodX = random.nextInt(numBlocksWide) * blockSize;
        foodY = random.nextInt(numBlocksHigh) * blockSize;
    }

    private void moveSnake() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
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
                if (snakeX >= screenWidth || snakeX < 0 || snakeY >= screenHeight || snakeY < 0) {
                    endGame();
                    return;
                }

                // Check for collisions with food
                if (snakeX == foodX && snakeY == foodY) {
                    score++;
                    updateScore();
                    spawnFood();
                }

                snakeHead.setX(snakeX);
                snakeHead.setY(snakeY);

                handler.postDelayed(this, 250); // speed of the snake
            }
        }, 250);
    }

    private void updateScore() {
        scoreText.setText("Score: " + score);
    }

    private void endGame() {
        // Reset game or show game over screen
        startGame();
    }

    // Handling user input for changing direction
    public void onUpButtonClick(View view) {
        if (currentDirection != Direction.DOWN)
            currentDirection = Direction.UP;
    }

    public void onDownButtonClick(View view) {
        if (currentDirection != Direction.UP)
            currentDirection = Direction.DOWN;
    }

    public void onLeftButtonClick(View view) {
        if (currentDirection != Direction.RIGHT)
            currentDirection = Direction.LEFT;
    }

    public void onRightButtonClick(View view) {
        if (currentDirection != Direction.LEFT)
            currentDirection = Direction.RIGHT;
    }
}
