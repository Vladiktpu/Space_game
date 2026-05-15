import java.awt.*;

public class Star {
    private int x, y;
    private boolean active;
    private int animationFrame;

    public Star() {
        this.x = 50 + (int)(Math.random() * (GameConstants.WIDTH - 100));
        this.y = 50 + (int)(Math.random() * (GameConstants.HEIGHT - 150));
        this.active = true;
        this.animationFrame = 0;
    }

    public void updateAnimation() {
        animationFrame = (animationFrame + 1) % 60;
    }

    public void draw(Graphics g) {
        if (!active) return;

        Graphics2D g2d = (Graphics2D) g;

        int alpha = 150 + (int)(105 * Math.sin(animationFrame * 0.1));
        int glowAlpha = 80 + (int)(70 * Math.sin(animationFrame * 0.15));

        g2d.setColor(new Color(255, 255, 150, glowAlpha));
        g2d.fillOval(x - 8, y - 8, GameConstants.STAR_SIZE + 16, GameConstants.STAR_SIZE + 16);

        g2d.setColor(new Color(255, 215, 0, alpha));
        g2d.fillOval(x, y, GameConstants.STAR_SIZE, GameConstants.STAR_SIZE);

        g2d.setColor(new Color(255, 255, 200, 200));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(x + GameConstants.STAR_SIZE/2, y - 5, x + GameConstants.STAR_SIZE/2, y + GameConstants.STAR_SIZE + 5);
        g2d.drawLine(x - 5, y + GameConstants.STAR_SIZE/2, x + GameConstants.STAR_SIZE + 5, y + GameConstants.STAR_SIZE/2);
        g2d.drawLine(x + 3, y + 3, x + GameConstants.STAR_SIZE - 3, y + GameConstants.STAR_SIZE - 3);
        g2d.drawLine(x + GameConstants.STAR_SIZE - 3, y + 3, x + 3, y + GameConstants.STAR_SIZE - 3);

        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.setColor(new Color(255, 255, 100));
        g2d.drawString("⭐", x + 5, y + 18);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, GameConstants.STAR_SIZE, GameConstants.STAR_SIZE);
    }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public int getX() { return x; }
    public int getY() { return y; }
}