import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TTTGraphics extends JFrame {
    private static final long serialVersionUID = 1L;

    public static final int ROWS = 3;
    public static final int COLS = 3;

    public static final int CELL_SIZE = 120;
    public static final int BOARD_WIDTH = CELL_SIZE * COLS;
    public static final int BOARD_HEIGHT = CELL_SIZE * ROWS;
    public static final int GRID_WIDTH = 5;
    public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2;

    public static final int CELL_PADDING = CELL_SIZE / 5;
    public static final int SYMBOL_STROKE_WIDTH = 10;
    public static final Color COLOR_GRID = new Color(255, 247, 212);
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);
    public static final Image CROSS_ICON = new ImageIcon("src/cat.png").getImage();
    public static final Image NOUGHT_ICON = new ImageIcon("src/panda.png").getImage();

    public enum State {
        PLAYING, DRAW, CROSS_WON, NOUGHT_WON
    }

    private State currentState;

    public enum Seed {
        CROSS, NOUGHT, NO_SEED
    }

    private Seed currentPlayer;
    private Seed[][] board;

    // UI Components
    private GamePanel gamePanel;
    private JLabel statusBar;
    private String player1Name;
    private String player2Name;
    private JButton resetButton;
    private Image backgroundImage;
    private JButton aboutButton;

    public TTTGraphics() {
        initGame();

        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                int row = mouseY / CELL_SIZE;
                int col = mouseX / CELL_SIZE;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < ROWS && col >= 0
                            && col < COLS && board[row][col] == Seed.NO_SEED) {
                        currentState = stepGame(currentPlayer, row, col);
                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                    }
                } else {
                    newGame();
                }
                repaint();
            }
        });

        getPlayerNames();

        statusBar = new JLabel("       ");
        statusBar.setFont(FONT_STATUS);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));
        statusBar.setOpaque(true);

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(gamePanel, BorderLayout.CENTER);
        cp.add(statusBar, BorderLayout.PAGE_END);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
                repaint();
            }
        });

        aboutButton = new JButton("About");
        aboutButton.addActionListener(e -> {
            SwingUtilities.invokeLater(AboutFrame::new);
        });
        cp.add(aboutButton, BorderLayout.LINE_END);

        // Add the "New Game" button to the UI
        cp.add(resetButton, BorderLayout.LINE_START);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setTitle("Tic Tac Toe");
        setVisible(true);

        loadImage();
        newGame();
    }

    private void getPlayerNames() {
        player1Name = JOptionPane.showInputDialog(this, "Enter name for Player 1:", "Player 1", JOptionPane.PLAIN_MESSAGE);
        player2Name = JOptionPane.showInputDialog(this, "Enter name for Player 2:", "Player 2", JOptionPane.PLAIN_MESSAGE);

        // Ensure that names are not empty or canceled
        if (player1Name == null || player1Name.trim().isEmpty()) {
            player1Name = "Player 1";
        }

        if (player2Name == null || player2Name.trim().isEmpty()) {
            player2Name = "Player O";
        }
    }

    public void initGame() {
        board = new Seed[ROWS][COLS];
    }
    public void newGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col] = Seed.NO_SEED;
            }
        }
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
    }

    public State stepGame(Seed player, int selectedRow, int selectedCol) {
        // Update game board
        board[selectedRow][selectedCol] = player;
        if (board[selectedRow][0] == player
                && board[selectedRow][1] == player
                && board[selectedRow][2] == player
                || board[0][selectedCol] == player
                && board[1][selectedCol] == player
                && board[2][selectedCol] == player
                || selectedRow == selectedCol
                && board[0][0] == player
                && board[1][1] == player
                && board[2][2] == player
                || selectedRow + selectedCol == 2
                && board[0][2] == player
                && board[1][1] == player
                && board[2][0] == player) {
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        } else {
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    if (board[row][col] == Seed.NO_SEED) {
                        return State.PLAYING;
                    }
                }
            }
            return State.DRAW;
        }
    }

    class GamePanel extends JPanel {
        private static final long serialVersionUID = 1L;

        @Override
        public void paintComponent(Graphics g) {

            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, 360, 360, null);
            g.setColor(COLOR_GRID);

            for (int row = 1; row < ROWS; ++row) {
                g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDHT_HALF,
                        BOARD_WIDTH - 1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
            }
            for (int col = 1; col < COLS; ++col) {
                g.fillRoundRect(CELL_SIZE * col - GRID_WIDHT_HALF, 0,
                        GRID_WIDTH, BOARD_HEIGHT - 1, GRID_WIDTH, GRID_WIDTH);
            }

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    int x1 = col * CELL_SIZE + CELL_PADDING;
                    int y1 = row * CELL_SIZE + CELL_PADDING;
                    if (board[row][col] == Seed.CROSS) {
                        g.drawImage(CROSS_ICON, x1, y1, CELL_SIZE - 2 * CELL_PADDING, CELL_SIZE - 2 * CELL_PADDING, null);
                    } else if (board[row][col] == Seed.NOUGHT) {
                        g.drawImage(NOUGHT_ICON, x1, y1, CELL_SIZE - 2 * CELL_PADDING, CELL_SIZE - 2 * CELL_PADDING, null);
                    }
                }
            }
            if (currentState == State.PLAYING) {
                statusBar.setForeground(Color.BLACK);
                statusBar.setText((currentPlayer == Seed.CROSS) ? player1Name + "'s Turn" : player2Name + "'s Turn");
            } else if (currentState == State.DRAW) {
                statusBar.setForeground(Color.BLUE);
                statusBar.setText("It's a Draw! Click to play again");
            } else if (currentState == State.CROSS_WON) {
                statusBar.setForeground(Color.BLUE);
                statusBar.setText(player1Name + " Won! Click to play again");
            } else if (currentState == State.NOUGHT_WON) {
                statusBar.setForeground(Color.BLUE);
                statusBar.setText(player2Name + " Won! Click to play again");
            }

        }

    }
    public void loadImage(){
        ImageIcon icon = new ImageIcon("src/tes.jpeg");
        backgroundImage = icon.getImage();
    }
}
