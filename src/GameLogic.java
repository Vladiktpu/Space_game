import java.awt.Rectangle;
import java.util.Random;

public class GameLogic implements Runnable, GameConstants {
    private int ballX, ballY;
    private int ballDX, ballDY;
    private int paddleX;
    private int score;
    private int lives;
    private boolean running;
    private boolean gameOver;
    private boolean ballServed;
    private Thread gameThread;
    private GameRenderer renderer;
    private Random random;

    private Star star;
    private int frameCounter;

    public GameLogic(GameRenderer renderer) {
        this.renderer = renderer;
        this.random = new Random();
        this.star = null;
        this.frameCounter = 0;
        initGame();
    }

    private void initGame() {
        ballX = INIT_BALL_X;
        ballY = INIT_BALL_Y;
        ballDX = BALL_SPEED_X * (random.nextBoolean() ? 1 : -1);
        ballDY = BALL_SPEED_Y;
        paddleX = (WIDTH - PADDLE_WIDTH) / 2;
        score = 0;
        lives = 3;
        gameOver = false;
        ballServed = false;
        star = null;
        frameCounter = 0;
    }

    public void startGame() {
        if (gameThread == null || !running) {
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public void stopGame() {
        running = false;
        if (gameThread != null) {
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        while (running) {
            if (!gameOver && ballServed) {
                updatePhysics();
                checkCollisions();
                spawnStar();

                if (renderer != null) {
                    renderer.repaint();
                }
            }

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updatePhysics() {
        ballX += ballDX;
        ballY += ballDY;

        if (ballX <= 0 || ballX >= WIDTH - BALL_SIZE) {
            ballDX = -ballDX;
        }
        if (ballY <= 0) {
            ballDY = -ballDY;
        }

        if (ballY >= HEIGHT - BALL_SIZE) {
            lives--;
            if (lives <= 0) {
                gameOver = true;
            } else {
                ballX = INIT_BALL_X;
                ballY = INIT_BALL_Y;
                ballServed = false;
                if (renderer != null) {
                    renderer.showStartButton(true);
                }
            }
        }
    }

    private void checkCollisions() {
        Rectangle ballRect = new Rectangle(ballX, ballY, BALL_SIZE, BALL_SIZE);
        Rectangle paddleRect = new Rectangle(paddleX, PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT);

        if (ballRect.intersects(paddleRect) && ballDY > 0) {
            ballDY = -ballDY;
            int hitPos = ballX + BALL_SIZE/2 - (paddleX + PADDLE_WIDTH/2);
            ballDX += hitPos / 10;

            if (ballDX > 7) ballDX = 7;
            if (ballDX < -7) ballDX = -7;

            score += 10;
        }

        if (star != null && star.isActive()) {
            Rectangle starRect = star.getBounds();
            if (ballRect.intersects(starRect)) {
                score += STAR_SCORE;
                star = null;
            }
        }
    }

    private void spawnStar() {
        frameCounter++;

        if (star != null) {
            star.updateAnimation();
        }

        if (star == null && frameCounter > STAR_SPAWN_CHANCE) {
            star = new Star();
            frameCounter = 0;
        }
    }

    public void movePaddleTo(int mouseX) {
        paddleX = mouseX - PADDLE_WIDTH / 2;
        if (paddleX < 0) paddleX = 0;
        if (paddleX > WIDTH - PADDLE_WIDTH) {
            paddleX = WIDTH - PADDLE_WIDTH;
        }
    }

    public void serve() {
        if (!gameOver) {
            ballServed = true;
            if (renderer != null) {
                renderer.showStartButton(false);
            }
        }
    }

    public void newGame() {
        stopGame();
        initGame();
        startGame();
        if (renderer != null) {
            renderer.showStartButton(true);
        }
    }

    public boolean isGameOver() { return gameOver; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public int getBallX() { return ballX; }
    public int getBallY() { return ballY; }
    public int getPaddleX() { return paddleX; }
    public Star getStar() { return star; }
    public boolean isBallServed() { return ballServed; }
}