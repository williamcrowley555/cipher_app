package gui;

import model.CipherType;
import util.CipherUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainGUI extends JFrame {

    private JPanel pnlMain;
    private JTextArea txtInput;
    private JTextArea txtOutput;
    private JButton btnEncryption;
    private JButton btnDecryption;
    private JLabel lblCypherType;
    private JPanel pnlCipherTypes;
    private JLabel lblKey;
    private JTextArea txtKey;

    public MainGUI() {
        super();
        setTitle("Cipher App");
        setContentPane(pnlMain);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        initComponents();
    }

    public void addCipherTypes(JPanel panel, ArrayList<CipherType> cipherTypes) {
        ButtonGroup cipherGroup = new ButtonGroup();

        for (int i = 0; i < cipherTypes.size(); ++i) {
            JRadioButton radioBtn = new JRadioButton(cipherTypes.get(i).getName());
            cipherGroup.add(radioBtn);
            panel.add(radioBtn);

            if (i == 0)
                radioBtn.setSelected(true);
        }
    }

    public void initComponents() {
        addCipherTypes(pnlCipherTypes, CipherUtils.get());

        btnEncryption.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });

        btnDecryption.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        // Customize LookAndFeel UI
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception ignored){}

        new MainGUI().setVisible(true);
    }
}
