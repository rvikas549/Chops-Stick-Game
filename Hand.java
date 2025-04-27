import java.awt.*;

public class Hand {
    int fingers;
    int x, y, width, height;
    boolean selected;

    public Hand(int x, int y) {
        this.fingers = 1;
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 100;
        this.selected = false;
    }

    public boolean contains(int mx, int my) {
        return mx >= x && mx <= x + width && my >= y && my <= y + height;
    }

    public boolean isEliminated() {
        return fingers == 0;
    }

    public void draw(Graphics g) {
        if (isEliminated()) {
            g.setColor(new Color(200, 200, 200, 150)); // faded color if eliminated
        } else if (selected) {
            g.setColor(new Color(0, 150, 255)); // highlight selected hand
        } else {
            g.setColor(Color.BLACK);
        }
        g.fillRect(x, y, width, height);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        if (!isEliminated()) {
            g.drawString(String.valueOf(fingers), x + width/2 - 10, y + height/2 + 10);
        }
    }
}
