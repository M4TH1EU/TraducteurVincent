package ch.m4th1eu.traducteur_vincent;

import ch.m4th1eu.traducteur_vincent.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Panel extends JPanel implements SwingerEventListener {
    private static Saver saver;
    private static File DIR = new File("C:" + "\\" + "Users" + "\\" + System.getenv("USERNAME") + "\\" + "Documents");

    static {
        Panel.saver = new Saver(new File(DIR, "\\latest.txt"));
    }

    public boolean fr_to_crypt = true;
    JEditorPane jep = new JEditorPane();
    Font sansSerif = new Font("SansSerif", 0, 15);
    Font gillSans = new Font("GillSans", 0, 15);
    private Image background = Swinger.getResource("background.png");

    private JTextArea texte_fr = new JTextArea("Texte en français");
    private JTextArea texte_crypt = new JTextArea("H1c2l2h1c2 b3g3l3g1h1é");

    private JScrollPane scroll_fr = new JScrollPane(texte_fr);
    private JScrollPane scroll_crypt = new JScrollPane(texte_crypt);

    private JButton switch_button = new JButton("FRA -> Crypté");
    private JButton convert_button = new JButton("Traduire");

    private ArrayList<String> lines = new ArrayList<>();


    //private JPasswordField passwordField = new JPasswordField(Panel.saver.get("password"));
    //private JLabel infoLabel = new JLabel("FRA -> Crypté");

    private STexturedButton closeButton = new STexturedButton(Swinger.getResource("close.png"), Swinger.getResource("close_hovered.png"));
    private STexturedButton reduceButton = new STexturedButton(Swinger.getResource("reduce.png"), Swinger.getResource("reduce_hovered.png"));


    public Panel() {

        setLayout(null);
        setBackground(Swinger.TRANSPARENT);

        /***----------------------------------------------------------***/

        texte_fr.setBounds(24, 177, 283, 399);
        texte_fr.setWrapStyleWord(true);
        texte_fr.setLineWrap(true);
        texte_fr.setOpaque(false);
        texte_fr.setFont(sansSerif);

        scroll_fr.setBounds(24, 177, 283, 399);
        scroll_fr.setOpaque(false);
        scroll_fr.setFont(sansSerif);
        add(scroll_fr);

        /***----------------------------------------------------------***/

        texte_crypt.setBounds(24, 177, 283, 399);
        texte_crypt.setWrapStyleWord(true);
        texte_crypt.setLineWrap(true);
        texte_crypt.setOpaque(false);
        texte_crypt.setFont(sansSerif);

        scroll_crypt.setBounds(493, 177, 283, 399);
        scroll_crypt.setOpaque(false);
        scroll_crypt.setFont(sansSerif);
        add(scroll_crypt);

        /***----------------------------------------------------------***/

        switch_button.setBounds(335, 142, 130, 25);
        switch_button.addActionListener(e -> {
            if (fr_to_crypt) {
                fr_to_crypt = false;
                switch_button.setText("FRA <- Crypté");
            } else {
                fr_to_crypt = true;
                switch_button.setText("FRA -> Crypté");
            }
        });
        switch_button.setContentAreaFilled(false);
        switch_button.setOpaque(true);
        switch_button.setBackground(new Color(20, 80, 121));
        switch_button.setForeground(Color.WHITE);
        switch_button.setFont(gillSans);
        add(switch_button);

        /***----------------------------------------------------------***/

        convert_button.setBounds(340, 500, 120, 25);
        convert_button.addActionListener(e -> {
            try {
                new File("C:/Users/" + System.getenv("USERNAME") + "/Documents/Traducteur/").mkdirs();
                //getCrypt();
                writeTexts();
                unCrypt();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        convert_button.setContentAreaFilled(false);
        convert_button.setOpaque(true);
        convert_button.setBackground(new Color(20, 80, 121));
        convert_button.setForeground(Color.WHITE);
        convert_button.setFont(gillSans);
        add(convert_button);

        /***----------------------------------------------------------***/

        closeButton.setBounds(750, 0, 50, 45);
        closeButton.addEventListener(this);
        add(closeButton);

        /***----------------------------------------------------------***/

        reduceButton.setBounds(700, 0, 50, 45);
        reduceButton.addEventListener(this);
        add(reduceButton);

        /***----------------------------------------------------------***/

    }

    @Override
    public void onEvent(SwingerEvent e) {
        if (e.getSource() == closeButton) {
            System.exit(0);
        } else if (e.getSource() == reduceButton) {
            FramePassword.getInstance().setState(1);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }


    public void writeTexts() throws IOException {
        String str_fr = texte_fr.getText();
        String str_crypt = texte_crypt.getText();

        BufferedWriter writer_fr = new BufferedWriter(new FileWriter("C:/Users/" + System.getenv("USERNAME") + "/Documents/Traducteur/french.txt"));
        BufferedWriter writer_crypt = new BufferedWriter(new FileWriter("C:/Users/" + System.getenv("USERNAME") + "/Documents/Traducteur/crypted.txt"));

        writer_fr.write(str_fr);
        writer_crypt.write(str_crypt);

        writer_fr.close();
        writer_crypt.close();
    }

    /*public void getCrypt() {
        try (BufferedInputStream inputStream = new BufferedInputStream(new URL("https://mathieubroillet.ch/brandons/crypt.txt").
                openStream());

             //FileOutputStream fileOS = new FileOutputStream("C:\\" + System.getenv("USERNAME") + "\\AppData\\Roaming\\crypt.txt")) {
             FileOutputStream fileOS = new FileOutputStream("C:/Users/" + System.getenv("USERNAME") + "/Documents/Traducteur/crypt.txt")) {
            byte data[] = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                fileOS.write(data, 0, byteContent);
            }
        } catch (IOException e) {
            // handles IO exceptions
        }
    }*/

    public void unCrypt() throws IOException {
        if (fr_to_crypt) {
            BufferedReader bufferedReader_fr = new BufferedReader(new FileReader("C:/Users/" + System.getenv("USERNAME") + "/Documents/Traducteur/french.txt"));
            String str1;
            while ((str1 = bufferedReader_fr.readLine()) != null) {
                /**Inversion du A et du B sinon problème de traduction.**/
                str1 = str1.replaceAll("B", "B2");
                str1 = str1.replaceAll("b", "b2");
                str1 = str1.replaceAll("A", "B1");
                str1 = str1.replaceAll("a", "b1");
                str1 = str1.replaceAll("C", "B3");
                str1 = str1.replaceAll("c", "b3");
                str1 = str1.replaceAll("D", "C1");
                str1 = str1.replaceAll("d", "c1");
                str1 = str1.replaceAll("E", "C2");
                str1 = str1.replaceAll("e", "c2");
                str1 = str1.replaceAll("F", "C3");
                str1 = str1.replaceAll("f", "c3");
                str1 = str1.replaceAll("G", "D1");
                str1 = str1.replaceAll("g", "d1");
                str1 = str1.replaceAll("H", "D2");
                str1 = str1.replaceAll("h", "d2");
                str1 = str1.replaceAll("I", "D3");
                str1 = str1.replaceAll("i", "d3");
                str1 = str1.replaceAll("J", "E1");
                str1 = str1.replaceAll("j", "e1");
                str1 = str1.replaceAll("K", "E2");
                str1 = str1.replaceAll("k", "e2");
                str1 = str1.replaceAll("L", "E3");
                str1 = str1.replaceAll("l", "e3");
                str1 = str1.replaceAll("M", "F1");
                str1 = str1.replaceAll("m", "f1");
                str1 = str1.replaceAll("N", "F2");
                str1 = str1.replaceAll("n", "f2");
                str1 = str1.replaceAll("O", "F3");
                str1 = str1.replaceAll("o", "f3");
                str1 = str1.replaceAll("P", "G1");
                str1 = str1.replaceAll("p", "g1");
                str1 = str1.replaceAll("Q", "G2");
                str1 = str1.replaceAll("q", "g2");
                str1 = str1.replaceAll("R", "G3");
                str1 = str1.replaceAll("r", "g3");
                str1 = str1.replaceAll("S", "G4");
                str1 = str1.replaceAll("s", "g4");
                str1 = str1.replaceAll("T", "H1");
                str1 = str1.replaceAll("t", "h1");
                str1 = str1.replaceAll("U", "H2");
                str1 = str1.replaceAll("u", "h2");
                str1 = str1.replaceAll("V", "H3");
                str1 = str1.replaceAll("v", "h3");
                str1 = str1.replaceAll("W", "L1");
                str1 = str1.replaceAll("w", "l1");
                str1 = str1.replaceAll("X", "L2");
                str1 = str1.replaceAll("x", "l2");
                str1 = str1.replaceAll("Y", "L3");
                str1 = str1.replaceAll("y", "l3");
                str1 = str1.replaceAll("Z", "L4");
                str1 = str1.replaceAll("z", "l4");

                lines.add(str1);
                texte_crypt.setText(str1);

            }
        } else {
            BufferedReader bufferedReader_crypt = new BufferedReader(new FileReader("C:/Users/" + System.getenv("USERNAME") + "/Documents/Traducteur/crypted.txt"));
            String str2;
            while ((str2 = bufferedReader_crypt.readLine()) != null) {

                str2 = str2.replaceAll("B1", "A");
                str2 = str2.replaceAll("b1", "a");
                str2 = str2.replaceAll("B2", "B");
                str2 = str2.replaceAll("b2", "b");
                str2 = str2.replaceAll("B3", "C");
                str2 = str2.replaceAll("b3", "c");
                str2 = str2.replaceAll("C1", "D");
                str2 = str2.replaceAll("c1", "d");
                str2 = str2.replaceAll("C2", "E");
                str2 = str2.replaceAll("c2", "e");
                str2 = str2.replaceAll("C3", "F");
                str2 = str2.replaceAll("c3", "f");
                str2 = str2.replaceAll("D1", "G");
                str2 = str2.replaceAll("d1", "g");
                str2 = str2.replaceAll("D2", "H");
                str2 = str2.replaceAll("d2", "h");
                str2 = str2.replaceAll("D3", "I");
                str2 = str2.replaceAll("d3", "i");
                str2 = str2.replaceAll("E1", "J");
                str2 = str2.replaceAll("e1", "j");
                str2 = str2.replaceAll("E2", "K");
                str2 = str2.replaceAll("e2", "k");
                str2 = str2.replaceAll("E3", "L");
                str2 = str2.replaceAll("e3", "l");
                str2 = str2.replaceAll("F1", "M");
                str2 = str2.replaceAll("f1", "m");
                str2 = str2.replaceAll("F2", "N");
                str2 = str2.replaceAll("f2", "n");
                str2 = str2.replaceAll("F3", "O");
                str2 = str2.replaceAll("f3", "o");
                str2 = str2.replaceAll("G1", "P");
                str2 = str2.replaceAll("g1", "p");
                str2 = str2.replaceAll("G2", "Q");
                str2 = str2.replaceAll("g2", "q");
                str2 = str2.replaceAll("G3", "R");
                str2 = str2.replaceAll("g3", "r");
                str2 = str2.replaceAll("G4", "S");
                str2 = str2.replaceAll("g4", "s");
                str2 = str2.replaceAll("H1", "T");
                str2 = str2.replaceAll("h1", "t");
                str2 = str2.replaceAll("H2", "U");
                str2 = str2.replaceAll("h2", "u");
                str2 = str2.replaceAll("H3", "V");
                str2 = str2.replaceAll("h3", "v");
                str2 = str2.replaceAll("L1", "W");
                str2 = str2.replaceAll("l1", "w");
                str2 = str2.replaceAll("L2", "X");
                str2 = str2.replaceAll("l2", "x");
                str2 = str2.replaceAll("L3", "Y");
                str2 = str2.replaceAll("l3", "y");
                str2 = str2.replaceAll("L4", "Z");
                str2 = str2.replaceAll("l4", "z");

                lines.add(str2);
                texte_fr.setText(str2);
            }
        }
    }
}
