import java.awt.*;

public interface GameConstants {
    int WIDTH = 900;
    int HEIGHT = 600;
    int PADDLE_WIDTH = 120;
    int PADDLE_HEIGHT = 18;
    int PADDLE_Y = HEIGHT - 90;
    int BALL_SIZE = 16;
    int INIT_BALL_X = WIDTH / 2;
    int INIT_BALL_Y = HEIGHT / 2;
    int BALL_SPEED_X = 5;
    int BALL_SPEED_Y = -5;

    int STAR_SIZE = 25;
    int STAR_SCORE = 50;
    int STAR_SPAWN_CHANCE = 120;

    int DELAY = 16;

    int NEW_GAME_KEY = java.awt.event.KeyEvent.VK_N;
    int EXIT_KEY = java.awt.event.KeyEvent.VK_ESCAPE;
    int RESTART_KEY = java.awt.event.KeyEvent.VK_R;

    Color BACKGROUND_COLOR = new Color(10, 10, 30);
    Color PADDLE_COLOR = new Color(0, 255, 255);
    Color BALL_COLOR = new Color(255, 100, 100);
    Color STAR_COLOR = new Color(255, 215, 0);
    Color TEXT_COLOR = new Color(200, 200, 255);
    Color SHADOW_COLOR = new Color(0, 0, 0, 80);
}