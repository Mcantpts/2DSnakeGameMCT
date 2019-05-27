import javax.swing.JFrame;

public class Main {

    //constructor icine JFrame
    public Main() {
        JFrame frame = new JFrame();
        GameScreen gamescreen = new GameScreen();

        frame.add(gamescreen);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // kapanma olayı
        frame.setTitle("Snake Game || MCT");  //üst başlık

        frame.pack();
        frame.setLocationRelativeTo(null); //ortada başlaması için
        frame.setVisible(true);   //calışması için

    }

    public static void main(String[] args) {
        new Main();
    }
}
