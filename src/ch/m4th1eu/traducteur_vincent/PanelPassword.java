package ch.m4th1eu.traducteur_vincent;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;

public class PanelPassword extends JPanel implements SwingerEventListener {
    private static String password;
    Font gillSans = new Font("GillSans", 0, 11);
    String ubuntu_font;
    private Image background = Swinger.getResource("password.png");
    private JPasswordField passwordField = new JPasswordField();
    private JLabel label = new JLabel();
    private STexturedButton connectionButton = new STexturedButton(Swinger.getResource("connection.png"), Swinger.getResource("connection_hover.png"));


    private STexturedButton closeButton = new STexturedButton(Swinger.getResource("close.png"), Swinger.getResource("close_hovered.png"));
    private STexturedButton reduceButton = new STexturedButton(Swinger.getResource("reduce.png"), Swinger.getResource("reduce_hovered.png"));

    {
        try {
            ubuntu_font = Font.createFont(0, new File(new File("C:/Users/" + System.getenv("USERNAME") + "/Documents/Traducteur/font/"), "Ubuntu-R.ttf")).getFamily();
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PanelPassword() {
        getUbuntuFont();

        setLayout(null);
        setBackground(Swinger.TRANSPARENT);

        /***----------------------------------------------------------***/

        closeButton.setBounds(750, 0, 50, 45);
        closeButton.addEventListener(this);
        add(closeButton);

        /***----------------------------------------------------------***/

        reduceButton.setBounds(700, 0, 50, 45);
        reduceButton.addEventListener(this);
        add(reduceButton);

        /***----------------------------------------------------------***/

        connectionButton.setBounds(200, 400, 400, 48);
        connectionButton.addEventListener(this);
        add(connectionButton);

        /***----------------------------------------------------------***/

        passwordField.setForeground(Color.WHITE);
        passwordField.setOpaque(false);
        passwordField.setBorder(null);
        passwordField.setBounds(210, 273, 380, 60);
        passwordField.setFont(gillSans);
        add(passwordField);

        /***----------------------------------------------------------***/

        label.setForeground(Color.white);
        label.setBounds(250, 350, 300, 30);
        label.setFont(new Font(ubuntu_font, Font.ITALIC, 14));
        label.setText("La connection peut  prendre quelques secondes");
        add(label);

        /***----------------------------------------------------------***/


    }

    @Override
    public void onEvent(SwingerEvent e) {
        if (e.getSource() == closeButton) {
            System.exit(0);
        } else if (e.getSource() == reduceButton) {
            FramePassword.getInstance().setState(1);
        } else if (e.getSource() == connectionButton) {
            try {
                URL url = new URL("https://mathieubroillet.ch/" + passwordField.getText() + "/good_password.txt");

                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

                String line;
                while ((line = in.readLine()) != null) {
                    password = line;
                }

                in.close();
            } catch (IOException e2) {
                label.setForeground(Color.red);
                label.setBounds(330, 350, 140, 30);
                label.setFont(new Font(ubuntu_font, Font.PLAIN, 14));
                label.setText("Mauvais mot de passe");
            }

            if (password != null && password.equals("good")) {
                try {
                    FramePassword.getInstance().hide();
                    Frame frame = new Frame();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public void getUbuntuFont() {
        if (!new File("C:/Users/" + System.getenv("USERNAME") + "/Documents/Traducteur/font").exists()) {

            new File("C:/Users/" + System.getenv("USERNAME") + "/Documents/Traducteur/font").mkdirs();

            try (BufferedInputStream inputStream = new BufferedInputStream(new URL("https://mathieubroillet.ch/Download/Ubuntu-R.ttf").
                    openStream());

                 //FileOutputStream fileOS = new FileOutputStream("C:\\" + System.getenv("USERNAME") + "\\AppData\\Roaming\\crypt.txt")) {
                 FileOutputStream fileOS = new FileOutputStream("C:/Users/" + System.getenv("USERNAME") + "/Documents/Traducteur/font/Ubuntu-R.ttf")) {
                byte data[] = new byte[1024];
                int byteContent;
                while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                    fileOS.write(data, 0, byteContent);
                }
            } catch (IOException e) {
                // handles IO exceptions
            }
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}
