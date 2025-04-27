import java.awt.*;

public class Player {
    Hand leftHand, rightHand;
    String name;

    public Player(String name) {
        this.name = name;
        leftHand = new Hand(0,0);
        rightHand = new Hand(0,0);
    }

    public void attack(Hand opponentHand) {
        Hand attackingHand = getSelectedHand();
        if (attackingHand == null || attackingHand.isEliminated()) return;

        int sum = attackingHand.fingers + opponentHand.fingers;

        if (sum == 5) {
            opponentHand.fingers = 0; // eliminated
        } else if (sum > 5) {
            opponentHand.fingers = sum - 5; // recycle
        } else {
            opponentHand.fingers = sum;
        }
    }

    public Hand getSelectedHand() {
        if (leftHand.selected) return leftHand;
        if (rightHand.selected) return rightHand;
        return null;
    }

    public void splitFingers(Hand deadHand) {
        if (!deadHand.isEliminated()) return;
        Hand aliveHand = (deadHand == leftHand) ? rightHand : leftHand;
        if (!aliveHand.isEliminated() && aliveHand.fingers > 1) {
            deadHand.fingers++;
            aliveHand.fingers--;
        }
    }
    

    public boolean isDefeated() {
        return leftHand.isEliminated() && rightHand.isEliminated();
    }

    public void draw(Graphics g, int x, int y) {
        leftHand.x = x;
        leftHand.y = y;
        rightHand.x = x + 150;
        rightHand.y = y;

        leftHand.draw(g);
        rightHand.draw(g);
    }
}
