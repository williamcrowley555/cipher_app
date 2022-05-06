package gui;

import model.CipherType;
import security.GenerateKeysRSA;
import util.CipherUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
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
    private JButton btnFileChooser;
    private JPanel pnlKey;
    private JPanel pnlFileChooser;
    private JPanel pnlElgamalKey;
    private JTextField txtSecretKey;
    private JTextField txtP;
    private JTextField txtB;

    private ButtonGroup cipherGroup;

    public MainGUI() {
        super();
        setTitle("Cipher App");
        setContentPane(pnlMain);
        setSize(800, 650);
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

    private String getExtension(File file) {
        String fileName = file.toString();
        int index = fileName.lastIndexOf('.');

        if(index > 0) {
            return fileName.substring(index + 1);
        }

        return null;
    }

    private String readFile() {
        JFileChooser fileDialog = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fileDialog.setFileFilter(filter);
        int returnVal = fileDialog.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileDialog.getSelectedFile();

            if (getExtension(file).equals("txt")) {
                BufferedReader reader;
                StringBuilder sb = new StringBuilder();

                try {
                    reader = new BufferedReader(new FileReader(file));
                    String line = reader.readLine();

                    while(line != null) {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                        line = reader.readLine();
                    }

                    return sb.toString().trim();
                }
                catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "File được chọn phải có định dạng txt!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    private void addRadioBtnListener(JRadioButton btn, int cipherType) {
        switch (cipherType) {
            case CipherUtils.AFFINE:
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pnlSingleKey.setVisible(false);
                        pnlDoubleKey.setVisible(true);
                        pnlElgamalKey.setVisible(false);
                    }
                });
                break;

            case CipherUtils.RSA:
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pnlFileChooser.setVisible(false);
                        pnlSingleKey.setVisible(false);
                        pnlDoubleKey.setVisible(false);
                        pnlElgamalKey.setVisible(false);

                        try {
                            new GenerateKeysRSA(1024).generateKeysToFile();
                        } catch (NoSuchAlgorithmException ex) {
                            ex.printStackTrace();
                        } catch (NoSuchProviderException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                break;

            case CipherUtils.ELGAMAL:
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pnlSingleKey.setVisible(false);
                        pnlDoubleKey.setVisible(false);
                        pnlElgamalKey.setVisible(true);
                    }
                });
                break;

            default:
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pnlSingleKey.setVisible(true);
                        pnlDoubleKey.setVisible(false);
                        pnlElgamalKey.setVisible(false);
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
        pnlElgamalKey.setVisible(false);
        btnFileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCipherName = getSelectedButtonText(cipherGroup);
                CipherType selectedCipher = CipherUtils.get(selectedCipherName);

                if (selectedCipher.getCode() == CipherUtils.AFFINE) {
                    String keys = readFile();

                    if (keys != null) {
                        txtKey1.setText(keys.split(";")[0]);
                        txtKey2.setText(keys.split(";")[1]);
                    }
                } else {
                    String key = readFile();

                    if (key != null) {
                        txtKey.setText(key);
                    }
                }
            }
        });

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
                } else if(selectedCipher.getCode() == CipherUtils.ELGAMAL){
                    key = txtSecretKey.getText();
                } else {
                    key = txtKey.getText();
                }

                if (selectedCipher.getCode() == CipherUtils.ELGAMAL) {
                    if (!txtSecretKey.getText().isEmpty()) {
                        key = txtSecretKey.getText();
                    }
                } else {
                    key = txtKey.getText();
                }

                if (plainText != null && !plainText.isEmpty()) {
                    if (selectedCipher.getCode() != CipherUtils.RSA) {
                        if (key == null || key.isEmpty()) {
                            return;
                        }
                    }

                    String cipherText = null;
                    try {
                        cipherText = CipherUtils.encrypt(plainText, selectedCipher.getCode(), key);
                    } catch (UnsupportedEncodingException ex) {
                        ex.printStackTrace();
                    }
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

                if (selectedCipher.getCode() == CipherUtils.ELGAMAL) {
                    if (!txtSecretKey.getText().isEmpty()) {
                        key = txtSecretKey.getText();
                    }
                } else {
                    key = txtKey.getText();
                }

                if (cipherText != null && !cipherText.isEmpty()) {
                    if (selectedCipher.getCode() != CipherUtils.RSA) {
                        if (key == null || key.isEmpty()) {
                            return;
                        }
                    }

                    String plainText = null;
                    try {
                        plainText = CipherUtils.decrypt(cipherText, selectedCipher.getCode(), key);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

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
