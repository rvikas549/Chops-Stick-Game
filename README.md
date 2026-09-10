# Chop Sticks Game

> A local two-player hand strategy game built with Java Swing.

Chop Sticks is a desktop game for two players sharing one computer. Select and split your hands, attack your opponent, and eliminate both of their hands to win. The game includes illustrated hand artwork, motion, celebration confetti, a rules dialog, and an always-visible restart control.

## Preview

The game window includes:

- Illustrated hand cards with raised fingers.
- A blue pulse around the currently selected hand.
- A live turn and action status banner.
- Animated winner confetti on the winning player's side.
- A winner dialog after two seconds.
- A visible **Restart game** button in the bottom-right corner.
- A **Rules** button available during the entire match.

## Rules

Each player starts with one finger on each hand.

1. **Select:** Click your other hand to switch the selected hand.
2. **Split:** Click the selected hand to move one finger to your other hand. You can split repeatedly before attacking when the move is valid.
3. **Attack:** Click an opponent's hand to add your selected hand's fingers to it. Totals wrap at five; a total of five eliminates that hand.
4. **Win:** Eliminate both of your opponent's hands.

When a player wins, the winner's side celebrates with animated confetti. After two seconds, a popup announces the winner. Choose **Restart game** in the popup, or choose **Cancel** and use the permanent **Restart game** button in the bottom-right corner.

## Quick Start

### Requirements

- Java Development Kit (JDK) 8 or newer
- A desktop environment that supports Java Swing
- No external libraries or package manager required

Check Java:

```bash
java -version
javac -version
```

### Compile and Run

From the project directory:

```bash
javac *.java
java Main
```

Java class names are case-sensitive. Start the game with `java Main`, using an uppercase `M`, not `java main`.

On macOS, the complete command is:

```bash
cd "/Users/your-name/path/to/Chops-Stick-Game"
javac *.java
java Main
```

The game opens in a separate desktop window. The terminal does not print gameplay messages while the Swing window is running.

## Controls

| Control | Action |
| --- | --- |
| Selected hand | Split one finger to the other hand |
| Other own hand | Select that hand |
| Opponent hand | Attack and end your turn |
| Rules | Open the rules dialog |
| New game / Restart game | Reset the match |

## Project Structure

```text
Chops-Stick-Game/
├── Main.java       # Application entry point.
├── GameFrame.java  # Main Swing window configuration.
├── GamePanel.java  # Game state, controls, turn flow, animation, and drawing.
├── Player.java     # Player attacks, splitting, selection, and defeat checks.
├── Hand.java       # Hand state, hit detection, and illustrated hand rendering.
└── README.md       # GitHub documentation.
```

## Technology

- **Language:** Java
- **Interface:** Java Swing
- **Rendering:** Java AWT and Java2D
- **Packages:** `java.awt`, `java.awt.event`, `javax.swing`
- **Dependencies:** None outside the JDK

## Clean Build Files

Compilation creates `.class` files next to the source files. Remove them on macOS or Linux with:

```bash
rm -f *.class
```

On Windows PowerShell:

```powershell
Remove-Item *.class
```

## Troubleshooting

### `javac: command not found`

Install a JDK and add its `bin` directory to your `PATH`. A JRE alone is not enough because the compiler is included with the JDK.

### `Could not find or load main class Main`

Compile first and run from the folder containing `Main.class`:

```bash
javac *.java
java Main
```

Remember that `Main` uses an uppercase `M`.

### The game window does not appear

Run the application on a local desktop session. Swing windows cannot open in a headless terminal-only environment.

## License

No software license is currently specified for this repository. Add a license before distributing the project publicly.