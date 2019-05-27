import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JPanel implements Runnable,KeyListener {

    private static final long serialVerisonUID = 1L;
    private static final int WIDTH=600 , HEIGHT=600;

    private Thread thread;
    private Snake s ;
    private ArrayList<Snake> snake;
    private Apple apple;
    private ArrayList<Apple> apples;
    private Random r ;

    private int x=10, y=10, size=5;
    private int ticks = 0;
    private int score = 0;

    private boolean running;
    //yön kavramları ilk mantık saga git oluyor
    private boolean right= true,  left=false, up =false, down =false;

    //oyun ekranımızı panel ile başlatıyoruz
    public GameScreen() {
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        addKeyListener(this);
        snake = new ArrayList<Snake>();
        apples = new ArrayList<Apple>();
        r=new Random();
        start();
    }
    //start başlamassı ve bunu thread ile derleyiciye bildirme
    public void start(){
        running=true;
        thread= new Thread(this);
        thread.start();
    }
    //start bittikten sonra başla stop diyoruz

    public void stop(){
        running= false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //tick tikleme kutu işareti gibi düşünebiliriz
    public void tick(){
         if(snake.size() == 0){
                 s = new Snake(x,y,10);
                 snake.add(s);
             }
            ticks++;
         //yılanın hızı  ve x(right-left) y(up-down) dogrultusunda yön verilmesi
         if(ticks > 240000){
             if(right)x++;
             if(left)x--;
             if(up)y--;
             if(down)y++;
             ticks=0;

             s = new Snake(x,y,10);
             snake.add(s);
             if(snake.size()>size){
                 snake.remove(0);
             }
         }
         //elmanın random x 59 y 59 alanın herhangi birinde random olarak atanması
         if(apples.size() == 00){
             int x =r.nextInt(59);
             int y= r.nextInt(59);
             apple =new Apple(x,y,10);
             apples.add(apple);
         }

         //elmanın yenmesi ve yılanın büyümesi
         for(int i = 0; i<apples.size(); i++){
             if(x==apples.get(i).getX() && y== apples.get(i).getY()){
                 size++;
                 apples.remove(i);
                 i++;
                 score++;
             }
         }
         //yılanın duvara carpınca oynu bitirme
         if(x<0 || y<0 || x>59 || y>59){
             System.out.println("Game Over");
             stop();
         }
         //yılanın kendini yemesi
         for(int i= 0; i<snake.size(); i++){
             if(x == snake.get(i).getX() && y==snake.get(i).getY()){
                 if(i != snake.size()-1){
                     System.out.println("Game Over");
                     stop();
                 }
             }
         }
    }

    public void paint(Graphics g){
        //karaleri temizleyip icine siyah renk atıyoruz
        g.clearRect(0,0,WIDTH,HEIGHT);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH,HEIGHT);

        //verdiğimiz 600 degerini 10 a bölüm 60 birim elde ediyoruz aynı işi heighte de yapıyoruz
        for(int i =0; i<WIDTH/10; i++){
            g.drawLine(i * 10,0,i*10,HEIGHT);
        }
        for(int j = 0; j<HEIGHT/10; j++){
            g.drawLine(0,j*10,HEIGHT,j * 10);
        }

        //yılan ve elma icine draw metodu ile boyuyoruz kendi sınıflarında renkleri belirttik
        for(int k = 0; k<snake.size(); k++){
            snake.get(k).draw(g);
        }
        for(int a = 0; a<apples.size(); a++){
            apples.get(a).draw(g);
        }
        //score işlemlerimiz
        if(running==true){
            g.setColor(Color.WHITE);
            g.drawString("Score : " + score,10,10);
        }
        if(running==false){
            g.setColor(Color.RED);
            g.drawString("Game Over Score = "+ score,200,200);
        }
    }


    @Override
    public void run() {
        while(running){
            tick();
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    //yön tuşlarını dinlemek için ve basılınca yapılıcaklar
    @Override
    public void keyPressed(KeyEvent e) {
       int key = e.getKeyCode();

        if(key == KeyEvent.VK_RIGHT && !left){
            right=true;
            up =false;
            down= false;
        }

        if(key == KeyEvent.VK_LEFT && !right){
            left=true;
            up =false;
            down= false;
        }

        if(key == KeyEvent.VK_UP && !down){
            up=true;
            left =false;
            right=false;
        }
        if(key == KeyEvent.VK_DOWN && !up){
            down=true;
            left =false;
            right=false;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
