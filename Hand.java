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

    public void draw(Graphics g, int animationFrame) {
        Graphics2D graphics = (Graphics2D) g.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int pulse = selected && !isEliminated() ? (int) (3 * Math.sin(animationFrame * 0.12)) : 0;
        graphics.setColor(isEliminated() ? new Color(214, 222, 231) : new Color(255, 255, 255));
        graphics.fillRoundRect(x - pulse, y - pulse, width + pulse * 2, height + pulse * 2, 24, 24);
        graphics.setColor(selected ? new Color(31, 111, 235) : new Color(205, 214, 224));
        graphics.setStroke(new BasicStroke(selected ? 4 : 2));
        graphics.drawRoundRect(x - pulse, y - pulse, width + pulse * 2, height + pulse * 2, 24, 24);

        if (isEliminated()) {
            graphics.setColor(new Color(135, 148, 163));
            graphics.setStroke(new BasicStroke(4));
            graphics.drawLine(x + 33, y + 35, x + 67, y + 65);
            graphics.drawLine(x + 67, y + 35, x + 33, y + 65);
        } else {
            drawHandShape(graphics);
        }
        graphics.dispose();
    }

    private void drawHandShape(Graphics2D graphics) {
        Color skin = selected ? new Color(255, 202, 146) : new Color(248, 190, 132);
        Color shadow = selected ? new Color(230, 145, 83) : new Color(219, 132, 73);
        int palmX = x + 29;
        int palmY = y + 47;

        graphics.setColor(shadow);
        graphics.fillRoundRect(palmX, palmY + 3, 43, 37, 18, 18);
        graphics.setColor(skin);
        graphics.fillRoundRect(palmX, palmY, 43, 37, 18, 18);

        int visibleFingers = Math.min(fingers, 4);
        for (int index = 0; index < 4; index++) {
            int fingerX = x + 19 + index * 16;
            int fingerHeight = index < visibleFingers ? 34 : 17;
            graphics.setColor(index < visibleFingers ? skin : new Color(238, 226, 215));
            graphics.fillRoundRect(fingerX, y + 18 + (34 - fingerHeight), 13, fingerHeight, 8, 8);
            if (index < visibleFingers) {
                graphics.setColor(shadow);
                graphics.drawLine(fingerX + 4, y + 24 + (34 - fingerHeight), fingerX + 9, y + 24 + (34 - fingerHeight));
            }
        }

        graphics.setColor(skin);
        graphics.fillRoundRect(x + 63, y + 50, 25, 13, 9, 9);
        graphics.setColor(shadow);
        graphics.drawLine(x + 69, y + 53, x + 82, y + 57);
    }
}
