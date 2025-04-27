import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    Player player1, player2;
    Player currentPlayer, opponent;

    boolean waitingToAttack = false; // Important flag!

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.WHITE);

        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        currentPlayer = player1;
        opponent = player2;

        currentPlayer.leftHand.selected = true;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
    }

    private void handleClick(int x, int y) {
        // === Handle clicks ===

        // 1. Selecting own hands (normal tap)
        if (currentPlayer.leftHand.contains(x, y) && !currentPlayer.leftHand.isEliminated()) {
            currentPlayer.leftHand.selected = true;
            currentPlayer.rightHand.selected = false;
            waitingToAttack = true;  // Prepare to either split or attack
        } 
        else if (currentPlayer.rightHand.contains(x, y) && !currentPlayer.rightHand.isEliminated()) {
            currentPlayer.leftHand.selected = false;
            currentPlayer.rightHand.selected = true;
            waitingToAttack = true;  // Prepare to either split or attack
        } 
        // 2. Revive eliminated own hand
        else if (currentPlayer.leftHand.contains(x, y) && currentPlayer.leftHand.isEliminated()) {
            currentPlayer.splitFingers(currentPlayer.leftHand);
        } 
        else if (currentPlayer.rightHand.contains(x, y) && currentPlayer.rightHand.isEliminated()) {
            currentPlayer.splitFingers(currentPlayer.rightHand);
        } 
        // 3. Attack opponent's hand only when ready
        else if (waitingToAttack && opponent.leftHand.contains(x, y) && !opponent.leftHand.isEliminated()) {
            currentPlayer.attack(opponent.leftHand);
            waitingToAttack = false;
            switchTurn();
        } 
        else if (waitingToAttack && opponent.rightHand.contains(x, y) && !opponent.rightHand.isEliminated()) {
            currentPlayer.attack(opponent.rightHand);
            waitingToAttack = false;
            switchTurn();
        }

        repaint();
    }

    private void switchTurn() {
        if (opponent.isDefeated()) {
            JOptionPane.showMessageDialog(this, currentPlayer.name + " Wins!");
            System.exit(0);
        }
        Player temp = currentPlayer;
        currentPlayer = opponent;
        opponent = temp;

        if (!currentPlayer.leftHand.isEliminated()) {
            currentPlayer.leftHand.selected = true;
            currentPlayer.rightHand.selected = false;
        } else if (!currentPlayer.rightHand.isEliminated()) {
            currentPlayer.leftHand.selected = false;
            currentPlayer.rightHand.selected = true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString(currentPlayer.name + "'s Turn", WIDTH/2 - 100, 40);

        player1.draw(g, 200, 150);
        player2.draw(g, 200, 400);
    }
}
