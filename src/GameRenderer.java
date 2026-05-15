import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameRenderer extends JPanel {
    private GameLogic logic;
    private JButton startButton;

    public GameRenderer(GameLogic logic) {
        this.logic = logic;
        setLayout(null);
        setPreferredSize(new Dimension(GameConstants.WIDTH, GameConstants.HEIGHT));
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        startButton = new JButton("🚀 СТАРТ 🚀");
        startButton.setBounds(GameConstants.WIDTH / 2 - 100, GameConstants.HEIGHT / 2 + 80, 200, 50);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setBackground(new Color(0, 100, 150));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setFocusable(false);

        startButton.addActionListener(e -> {
            logic.serve();
            startButton.setVisible(false);
            requestFocusInWindow();
        });

        add(startButton);

        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                logic.movePaddleTo(e.getX());
                requestFocusInWindow();
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                e.consume();

                if (key == GameConstants.NEW_GAME_KEY) {
                    logic.newGame();
                    startButton.setVisible(true);
                } else if (key == GameConstants.EXIT_KEY) {
                    System.exit(0);
                } else if (key == GameConstants.RESTART_KEY) {
                    logic.newGame();
                    startButton.setVisible(true);
                }
                repaint();
            }
        });

        addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                SwingUtilities.invokeLater(() -> requestFocusInWindow());
            }
        });

        requestFocusInWindow();
    }

    public void showStartButton(boolean show) {
        startButton.setVisible(show);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(GameConstants.BACKGROUND_COLOR);
        g2d.fillRect(0, 0, GameConstants.WIDTH, GameConstants.HEIGHT);

        g2d.setColor(Color.WHITE);
        for (int i = 0; i < 100; i++) {
            int x = (i * 131) % GameConstants.WIDTH;
            int y = (i * 253) % GameConstants.HEIGHT;
            g2d.fillOval(x, y, 2, 2);
        }

        if (logic.getStar() != null && logic.getStar().isActive()) {
            logic.getStar().draw(g2d);
        }

        g2d.setColor(GameConstants.PADDLE_COLOR);
        g2d.fillRoundRect(logic.getPaddleX(), GameConstants.PADDLE_Y,
                GameConstants.PADDLE_WIDTH, GameConstants.PADDLE_HEIGHT, 15, 15);

        g2d.setColor(GameConstants.BALL_COLOR);
        g2d.fillOval(logic.getBallX(), logic.getBallY(),
                GameConstants.BALL_SIZE, GameConstants.BALL_SIZE);
        g2d.setColor(Color.WHITE);
        g2d.fillOval(logic.getBallX() + 4, logic.getBallY() + 4, 4, 4);

        g2d.setColor(GameConstants.TEXT_COLOR);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.drawString("Счёт: " + logic.getScore(), 20, 45);
        g2d.drawString("Жизни: " + logic.getLives(), GameConstants.WIDTH - 120, 45);

        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.drawString("Мышь — движение ракетки", 20, GameConstants.HEIGHT - 40);
        g2d.drawString("⭐ Лови звезду шариком! +50 очков ⭐", GameConstants.WIDTH / 2 - 170, GameConstants.HEIGHT - 20);
        g2d.drawString("N или R — новая игра | ESC — выход", GameConstants.WIDTH - 350, GameConstants.HEIGHT - 40);

        if (!logic.isBallServed() && !logic.isGameOver()) {
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
            String msg = "⭐ НАЖМИТЕ СТАРТ! ⭐";
            FontMetrics fm = g2d.getFontMetrics();
            int msgWidth = fm.stringWidth(msg);
            g2d.setColor(GameConstants.BALL_COLOR);
            g2d.drawString(msg, GameConstants.WIDTH / 2 - msgWidth / 2, GameConstants.HEIGHT / 2);
        }

        if (logic.isGameOver()) {
            g2d.setFont(new Font("Arial", Font.BOLD, 48));
            String msg = "💀 GAME OVER 💀";
            FontMetrics fm = g2d.getFontMetrics();
            int msgWidth = fm.stringWidth(msg);
            g2d.setColor(Color.RED);
            g2d.drawString(msg, GameConstants.WIDTH / 2 - msgWidth / 2, GameConstants.HEIGHT / 2 - 40);

            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
            String msg2 = "Нажмите N или R для новой игры";
            fm = g2d.getFontMetrics();
            msgWidth = fm.stringWidth(msg2);
            g2d.setColor(GameConstants.TEXT_COLOR);
            g2d.drawString(msg2, GameConstants.WIDTH / 2 - msgWidth / 2, GameConstants.HEIGHT / 2 + 20);
        }
    }
}