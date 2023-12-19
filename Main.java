/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2023/2024
 * Group Capstone Project
 * Group #2
 * 1 - 5026221124 - Gita Elizza Larasati
 * 2 - 5026221185 - Mutiara Nurshadrina Rafifah
 * 3 - 5026221208 - Shine Quinn Firdaus
 */
import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TTTGraphics(); // Let the constructor do the job
            }
        });
    }
}