import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        this.setTitle("Chops Stick Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.add(new GamePanel());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
