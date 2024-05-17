package com.example.wizardspotionshop;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snake extends BaseMainActivity {

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
    private final int numBlocksWide = 20;
    private final int numBlocksHigh = 30;

    private List<ImageView> snakeBody = new ArrayList<>();

    enum Direction {UP, DOWN, LEFT, RIGHT}
    private Direction currentDirection = Direction.RIGHT;

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

        Button btn_up = findViewById(R.id.btn_up);
        Button btn_down = findViewById(R.id.btn_down);
        Button btn_right = findViewById(R.id.btn_right);
        Button btn_left = findViewById(R.id.btn_left);

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentDirection != Direction.DOWN)
                    currentDirection = Direction.UP;
            }
        });

        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentDirection != Direction.UP)
                    currentDirection = Direction.DOWN;
            }
        });

        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentDirection != Direction.LEFT)
                    currentDirection = Direction.RIGHT;
            }
        });

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentDirection != Direction.RIGHT)
                    currentDirection = Direction.LEFT;
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
                if (snakeX >= screenWidth || snakeX < 0 || snakeY >= screenHeight || snakeY < 0) {
                    endGame();
                    return;
                }

                // Check for collisions with itself
                for (int i = 1; i < snakeBody.size(); i++) {
                    ImageView part = snakeBody.get(i);
                    if (Math.abs(snakeX - part.getX()) < blockSize / 3 && Math.abs(snakeY - part.getY()) < blockSize / 3) {
                        endGame();
                        return;
                    }
                }

                // Check for collisions with food
                if (Math.abs(snakeX - foodX) < blockSize / 3 && Math.abs(snakeY - foodY) < blockSize / 3) {
                    score++;
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
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(70, 70);
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
        snakeBody.add(snakeHead);

        startGame();
    }
}
