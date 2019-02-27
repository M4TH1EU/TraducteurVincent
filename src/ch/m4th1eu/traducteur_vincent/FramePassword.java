package ch.m4th1eu.traducteur_vincent;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FramePassword extends JFrame {
    private static FramePassword instance;
    private PanelPassword panel_password;

    public FramePassword() throws IOException {
        if (!isWindows()) {
            JOptionPane.showMessageDialog(null, "Ce programme est fait pour Windows, il est déconseillé de l'utiliser sous un autre système.", "INFORMATION IMPORTANTE", JOptionPane.INFORMATION_MESSAGE);
            Frame.getInstance().hide();
            getInstance().hide();

        }
        getFavicon();

        BufferedImage myPicture = ImageIO.read(new File("C:/Users/" + System.getenv("USERNAME") + "/Documents/Traducteur/" + "favicon.png"));
        ImageIcon image = new ImageIcon(myPicture);


        setTitle("Traducteur");
        setSize(800, 600);
        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setBackground(Swinger.TRANSPARENT);
        panel_password = new PanelPassword();
        setContentPane(panel_password = new PanelPassword());
        WindowMover mover = new WindowMover(this);
        addMouseListener(mover);
        addMouseMotionListener(mover);
        setIconImage(image.getImage());
        setVisible(true);
    }

    public static boolean isWindows() {
        String OS = System.getProperty("os.name");
        return OS.startsWith("Windows");
    }

    public static void main(String[] args) throws IOException {
        Swinger.setSystemLookNFeel();
        Swinger.setResourcePath("/ch/m4th1eu/traducteur_vincent/ressources/");
        FramePassword.instance = new FramePassword();
    }

    public static FramePassword getInstance() {
        return FramePassword.instance;
    }


    public PanelPassword getPanel() {
        return panel_password;
    }

    public void getFavicon() {
        if (!new File("C:/Users/" + System.getenv("USERNAME") + "/Documents/Traducteur/favicon.png").exists()) {
            JOptionPane.showMessageDialog(null, "Le premier démarrage peut prendre quelques secondes", "Information", JOptionPane.INFORMATION_MESSAGE);

            new File("C:/Users/" + System.getenv("USERNAME") + "/Documents/Traducteur/").mkdirs();

            try (BufferedInputStream inputStream = new BufferedInputStream(new URL("https://mathieubroillet.ch/Download/translator_favicon.png").
                    openStream());

                 //FileOutputStream fileOS = new FileOutputStream("C:\\" + System.getenv("USERNAME") + "\\AppData\\Roaming\\crypt.txt")) {
                 FileOutputStream fileOS = new FileOutputStream("C:/Users/" + System.getenv("USERNAME") + "/Documents/Traducteur/favicon.png")) {
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
}
