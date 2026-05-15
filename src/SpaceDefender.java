import javax.swing.*;

public class SpaceDefender {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Space Defender - Итоговый проект");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);

            GameRenderer renderer = new GameRenderer(null);
            GameLogic logic = new GameLogic(renderer);
            GameRenderer finalRenderer = new GameRenderer(logic);

            frame.setContentPane(finalRenderer);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            finalRenderer.requestFocusInWindow();
            logic.startGame();
        });
    }
}