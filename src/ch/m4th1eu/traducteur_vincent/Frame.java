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

public class Frame extends JFrame {
    private static Frame instance;
    private Panel panel;


    public Frame() throws IOException {
        getFavicon();

        BufferedImage myPicture = ImageIO.read(new File("C:/Users/" + System.getenv("USERNAME") + "/Documents/Traducteur/" + "favicon.png"));
        ImageIcon image = new ImageIcon(myPicture);
        setTitle("Traducteur");
        setSize(800, 600);
        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setBackground(Swinger.TRANSPARENT);
        panel = new Panel();
        setContentPane(panel = new Panel());
        WindowMover mover = new WindowMover(this);
        addMouseListener(mover);
        addMouseMotionListener(mover);
        setIconImage(image.getImage());
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        Swinger.setSystemLookNFeel();
        Swinger.setResourcePath("/ch/m4th1eu/traducteur_vincent/ressources/");
        Frame.instance = new Frame();
    }

    public static Frame getInstance() {
        return Frame.instance;
    }

    public Panel getPanel() {
        return panel;
    }

    public void getFavicon() {
        if (!new File("C:/Users/" + System.getenv("USERNAME") + "/Documents/Traducteur/favicon.png").exists()) {
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
