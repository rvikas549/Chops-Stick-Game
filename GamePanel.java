import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    static final int WIDTH = 860;
    static final int HEIGHT = 650;

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Player opponent;
    private String status = "Select a hand to begin.";
    private JButton newGameButton;
    private JButton rulesButton;
    private final Timer animationTimer;
    private Timer winnerDialogTimer;
    private Player winner;
    private boolean gameOver;
    private int animationFrame;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(245, 248, 252));
        setLayout(null);

        rulesButton = new JButton("Rules");
        rulesButton.setBounds(WIDTH - 255, HEIGHT - 58, 95, 38);
        rulesButton.setFocusPainted(false);
        rulesButton.setBackground(new Color(227, 234, 242));
        rulesButton.setForeground(new Color(24, 38, 56));
        rulesButton.setBorder(BorderFactory.createEmptyBorder());
        rulesButton.addActionListener(event -> showRules());
        add(rulesButton);

        newGameButton = new JButton("New game");
        newGameButton.setBounds(WIDTH - 150, HEIGHT - 58, 120, 38);
        newGameButton.setFocusPainted(false);
        newGameButton.setBackground(new Color(31, 111, 235));
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        newGameButton.setBorder(BorderFactory.createLineBorder(new Color(20, 84, 185), 1));
        newGameButton.setOpaque(true);
        newGameButton.setVisible(true);
        newGameButton.addActionListener(event -> resetGame());
        add(newGameButton);

        animationTimer = new Timer(40, event -> {
            animationFrame++;
            repaint();
        });
        animationTimer.start();

        resetGame();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                handleClick(event.getX(), event.getY());
            }
        });
    }

    private void handleClick(int x, int y) {
        if (gameOver || currentPlayer.isDefeated()) return;

        if (currentPlayer.leftHand.contains(x, y) && !currentPlayer.leftHand.isEliminated()) {
            handleOwnHand(currentPlayer.leftHand);
        } else if (currentPlayer.rightHand.contains(x, y) && !currentPlayer.rightHand.isEliminated()) {
            handleOwnHand(currentPlayer.rightHand);
        } else if (opponent.leftHand.contains(x, y) && !opponent.leftHand.isEliminated()) {
            attack(opponent.leftHand);
        } else if (opponent.rightHand.contains(x, y) && !opponent.rightHand.isEliminated()) {
            attack(opponent.rightHand);
        }
        repaint();
    }

    private void handleOwnHand(Hand hand) {
        if (hand.selected) {
            if (currentPlayer.splitSelectedHand()) {
                status = currentPlayer.name + " split one finger. Tap again or attack.";
            } else {
                status = "That hand cannot split yet. Attack or select the other hand.";
            }
        } else {
            currentPlayer.leftHand.selected = hand == currentPlayer.leftHand;
            currentPlayer.rightHand.selected = hand == currentPlayer.rightHand;
            status = currentPlayer.name + " selected a hand. Tap it to split or attack.";
        }
    }

    private void attack(Hand target) {
        int result = currentPlayer.attack(target);
        if (result == 0) {
            status = currentPlayer.name + " eliminated a hand!";
        } else {
            status = currentPlayer.name + " attacked and left the target with " + result + " finger" + (result == 1 ? "" : "s") + ".";
        }
        switchTurn();
    }

    private void switchTurn() {
        if (opponent.isDefeated()) {
            finishGame();
            return;
        }

        Player temp = currentPlayer;
        currentPlayer = opponent;
        opponent = temp;
        currentPlayer.leftHand.selected = !currentPlayer.leftHand.isEliminated();
        currentPlayer.rightHand.selected = false;
        if (currentPlayer.leftHand.isEliminated()) currentPlayer.rightHand.selected = true;
        status = currentPlayer.name + "'s turn. Choose a hand, then tap the opponent to attack.";
    }

    private void finishGame() {
        winner = currentPlayer;
        gameOver = true;
        status = winner.name + " wins! Celebrate!";
        newGameButton.setText("Restart game");
        winnerDialogTimer = new Timer(2000, event -> showWinnerDialog());
        winnerDialogTimer.setRepeats(false);
        winnerDialogTimer.start();
    }

    private void showWinnerDialog() {
        String[] options = {"Restart game", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this,
                winner.name + " won the game!", "Game complete",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);
        if (choice == 0) {
            resetGame();
        } else {
            status = "Game over. Click Restart game in the bottom-right corner to play again.";
            newGameButton.setText("Restart game");
            newGameButton.requestFocusInWindow();
            repaint();
        }
    }

    private void showRules() {
        String rules = "<html><div style='width: 360px'>"
                + "<h2>How to play Chop Sticks</h2>"
                + "<p><b>1. Select:</b> Tap your other hand to switch the selected hand.</p>"
                + "<p><b>2. Split:</b> Tap the selected hand to move one finger to your other hand. "
                + "Split as many times as you like before attacking when the move is valid.</p>"
                + "<p><b>3. Attack:</b> Tap an opponent's hand to add your selected hand's fingers to it. "
                + "A total of five eliminates that hand.</p>"
                + "<p><b>4. Win:</b> Eliminate both of your opponent's hands.</p>"
                + "</div></html>";
        JOptionPane.showMessageDialog(this, rules, "Rules", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetGame() {
        if (winnerDialogTimer != null) winnerDialogTimer.stop();
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        currentPlayer = player1;
        opponent = player2;
        winner = null;
        gameOver = false;
        newGameButton.setText("New game");
        currentPlayer.leftHand.selected = true;
        status = "Player 1's turn. Tap the selected hand to split, or attack.";
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setColor(new Color(24, 38, 56));
        graphics.setFont(new Font("SansSerif", Font.BOLD, 30));
        graphics.drawString("CHOP STICKS", 30, 48);
        graphics.setColor(new Color(96, 112, 130));
        graphics.setFont(new Font("SansSerif", Font.PLAIN, 14));
        graphics.drawString("A quick two-player hand strategy game", 32, 70);

        graphics.setColor(new Color(227, 234, 242));
        graphics.fillRoundRect(30, 95, WIDTH - 60, 54, 14, 14);
        graphics.setColor(new Color(31, 111, 235));
        graphics.fillRoundRect(30, 95, 8, 54, 8, 8);
        graphics.setColor(new Color(24, 38, 56));
        graphics.setFont(new Font("SansSerif", Font.BOLD, 16));
        graphics.drawString(status, 55, 128);

        player1.draw(graphics, 260, 205, animationFrame);
        player2.draw(graphics, 260, 405, animationFrame);

        if (gameOver) {
            drawCelebration(graphics, winner == player1 ? 205 : 405);
        }

        graphics.setColor(new Color(96, 112, 130));
        graphics.setFont(new Font("SansSerif", Font.PLAIN, 13));
        graphics.drawString("Tap selected hand = split  |  Tap other hand = select  |  Tap opponent = attack", 30, HEIGHT - 24);
        graphics.dispose();
    }

    private void drawCelebration(Graphics2D graphics, int playerY) {
        int centerX = WIDTH / 2;
        int centerY = playerY + 55;
        String message = "WINNER!";
        graphics.setColor(new Color(255, 193, 7, 235));
        graphics.fillRoundRect(centerX - 74, centerY - 91, 148, 32, 16, 16);
        graphics.setColor(new Color(24, 38, 56));
        graphics.setFont(new Font("SansSerif", Font.BOLD, 16));
        graphics.drawString(message, centerX - 35, centerY - 70);

        Color[] partyColors = {new Color(31, 111, 235), new Color(239, 91, 91), new Color(43, 170, 120), new Color(245, 166, 35)};
        for (int index = 0; index < 18; index++) {
            double angle = index * 0.75;
            int distance = 68 + (int) ((animationFrame * 2 + index * 13) % 70);
            int x = centerX + (int) (Math.cos(angle) * distance);
            int y = centerY - 32 + (int) (Math.sin(angle) * distance * 0.55);
            int size = 6 + index % 5;
            graphics.setColor(partyColors[index % partyColors.length]);
            if (index % 2 == 0) {
                graphics.fillOval(x, y, size, size);
            } else {
                graphics.fillRect(x, y, size, size);
            }
        }
    }
}