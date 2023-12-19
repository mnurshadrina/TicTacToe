import javax.swing.*;

public class AboutFrame extends JFrame {
    public AboutFrame() {
        setSize(400, 600);
        setLocationRelativeTo(null);

        ImageIcon image = new ImageIcon("aboutFPASD (1).jpg");

        JLabel label = new JLabel("<html><br><center>Sudoku Game</center><br><center>Developed by Group 2:</center><br>"
                + "<center>5026221124 - Gita Elizza Larasati</center>"
                + "<center>5026221185 - Mutiara Nurshadrina Rafifah</center>"
                + "<center>5026221208 - Shine Quinn Firdaus</center><br>"
                + "<center>© Final Project ASD A 2023/2024</center></html>");
        label.setIcon(image);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setIconTextGap(10);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        //label.setBounds(100, 100, 400, 600);

        add(label);
        setTitle("About");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
