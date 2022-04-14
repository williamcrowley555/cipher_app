package gui;

import model.CipherType;
import util.CipherUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;

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
    private JTextField txtKey1;
    private JTextField txtKey2;
    private JPanel pnlSingleKey;
    private JPanel pnlDoubleKey;

    private ButtonGroup cipherGroup;

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

    private void addCipherTypes(JPanel panel, ArrayList<CipherType> cipherTypes) {
        cipherGroup = new ButtonGroup();

        for (int i = 0; i < cipherTypes.size(); ++i) {
            JRadioButton radioBtn = new JRadioButton(cipherTypes.get(i).getName());
            addRadioBtnListener(radioBtn, cipherTypes.get(i).getCode());
            cipherGroup.add(radioBtn);
            panel.add(radioBtn);

            if (i == 0) {
                radioBtn.setSelected(true);
                pnlSingleKey.setVisible(true);
                pnlDoubleKey.setVisible(false);
            }
        }
    }

    private void addRadioBtnListener(JRadioButton btn, int cipherType) {
        switch (cipherType) {
            case CipherUtils.AFFINE:
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pnlSingleKey.setVisible(false);
                        pnlDoubleKey.setVisible(true);
                    }
                });
                break;

            default:
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pnlSingleKey.setVisible(true);
                        pnlDoubleKey.setVisible(false);
                    }
                });
                break;
        }
    }

    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }

    public void initComponents() {
        addCipherTypes(pnlCipherTypes, CipherUtils.get());

        btnEncryption.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String plainText = txtInput.getText();
                String selectedCipherName = getSelectedButtonText(cipherGroup);
                CipherType selectedCipher = CipherUtils.get(selectedCipherName);
                String key = null;

                if (selectedCipher.getCode() == CipherUtils.AFFINE) {
                    if (!txtKey1.getText().isEmpty() && !txtKey2.getText().isEmpty()) {
                        key = txtKey1.getText() + ";" + txtKey2.getText();
                    }
                } else {
                    key = txtKey.getText();
                }

                if (plainText != null && !plainText.isEmpty() &&
                        key != null && !key.isEmpty()) {

                    String cipherText = CipherUtils.encrypt(plainText, selectedCipher.getCode(), key);

                    txtOutput.setText(cipherText);
                }
            }
        });

        btnDecryption.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String cipherText = txtInput.getText();
                String selectedCipherName = getSelectedButtonText(cipherGroup);
                CipherType selectedCipher = CipherUtils.get(selectedCipherName);
                String key = null;

                if (selectedCipher.getCode() == CipherUtils.AFFINE) {
                    if (!txtKey1.getText().isEmpty() && !txtKey2.getText().isEmpty()) {
                        key = txtKey1.getText() + ";" + txtKey2.getText();
                    }
                } else {
                    key = txtKey.getText();
                }

                if (cipherText != null && !cipherText.isEmpty() &&
                        key != null && !key.isEmpty()) {

                    String plainText = CipherUtils.decrypt(cipherText, selectedCipher.getCode(), key);

                    txtOutput.setText(plainText);
                }

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
