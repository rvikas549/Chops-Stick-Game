import java.awt.*;

public class Player {
    final Hand leftHand;
    final Hand rightHand;
    final String name;

    public Player(String name) {
        this.name = name;
        leftHand = new Hand(0,0);
        rightHand = new Hand(0,0);
    }

    public int attack(Hand opponentHand) {
        Hand attackingHand = getSelectedHand();
        if (attackingHand == null || attackingHand.isEliminated() || opponentHand.isEliminated()) return 0;

        int sum = attackingHand.fingers + opponentHand.fingers;
        opponentHand.fingers = sum % 5;
        return opponentHand.fingers;
    }

    public Hand getSelectedHand() {
        if (leftHand.selected) return leftHand;
        if (rightHand.selected) return rightHand;
        return null;
    }

    public boolean splitSelectedHand() {
        Hand source = getSelectedHand();
        if (source == null || source.fingers < 2) return false;

        Hand target = source == leftHand ? rightHand : leftHand;
        if (target.fingers >= 4) return false;
        source.fingers--;
        target.fingers++;
        return true;
    }
    

    public boolean isDefeated() {
        return leftHand.isEliminated() && rightHand.isEliminated();
    }

    public void draw(Graphics g, int x, int y, int animationFrame) {
        leftHand.x = x;
        leftHand.y = y;
        rightHand.x = x + 150;
        rightHand.y = y;

        g.setColor(new Color(24, 38, 56));
        g.setFont(new Font("SansSerif", Font.BOLD, 18));
        g.drawString(name, x, y - 22);
        leftHand.draw(g, animationFrame);
        rightHand.draw(g, animationFrame);
    }
}
