import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;
public class SpeedClicker extends JFrame {

    //data
    private long count;
    private Timer gameTimer;
    private Timer moveTimer;
    private int difficulty;
    private int clicks;
    private final Color[] colors = {Color.BLACK, Color.MAGENTA, Color.GREEN, Color.BLUE, Color.WHITE, Color.RED, Color.PINK};
    private int currentColor;
    Random generate = new Random();
    JLabel gameTimerDisplay;
    JLabel scoreLabel;
    JLabel diffLabel;
    
    JButton startButton, playButton;
    JTextField input;

    

    public SpeedClicker() {

        count = 30;
        difficulty = 0;
        currentColor = 0;
        clicks = 0;

        generate = new Random();
        
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Speed Clicker");
        setLayout(null);
        setResizable(false);

        //game timer
        gameTimerDisplay = new JLabel("Timer:30");
        gameTimerDisplay.setBounds(50, 30, 500, 50);
        Font largeFont = new Font("serif", Font.BOLD, 48);
        gameTimerDisplay.setFont(largeFont);
        add(gameTimerDisplay);

        //score
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(350, 30, 300, 50);
        scoreLabel.setFont(new Font("serif", Font.BOLD, 32));
        add(scoreLabel);

        //play button
        playButton = new JButton("Click Me");
        playButton.setBounds(250, 250, 100, 100);
        playButton.addActionListener(e -> onButtonPressed());
        playButton.setFocusable(false);
        add(playButton);
        playButton.setVisible(false);

        //difficulty
        diffLabel = new JLabel("Difficulty: 1.Normal 2.Challenge 3.Master");
        diffLabel.setBounds(10, 460, 350, 25);
        diffLabel.setFont(new Font("serif", Font.PLAIN, 18));
        add(diffLabel);
        //take input
        input = new JTextField();
        input.setBounds(10, 500, 100, 50);
        add(input);

        //start button
        startButton = new JButton("Start");
        startButton.setBounds(250, 500, 100, 50);
        startButton.addActionListener(e -> startGame());
        add(startButton);
    }

    private void startGame() {

        //read difficulty
        String t = input.getText();
        if (t.equals("")) t = "1";

        difficulty = Integer.parseInt(t);
        
        //make play button visible
        playButton.setVisible(true);

        //hide input & start
        input.setVisible(false);
        startButton.setVisible(false);
        diffLabel.setVisible(false);
        
        //reset stats
        clicks = 0;
        scoreLabel.setText("Score: 0");
        count = 30;
        gameTimerDisplay.setText("Timer: 30");

        //countdown timer
        gameTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                count--;
                gameTimerDisplay.setText("Timer: " + count);
                if(count <= 10)
                	gameTimerDisplay.setForeground(Color.RED);
                if (count <= 0) {
                    gameTimer.stop();
                    moveTimer.stop();
                    endGame();
                }
            }
        });
        gameTimer.start();

        //move timer
        int speed = 800;
        if (difficulty == 2) speed = 500;
        if (difficulty == 3) speed = 250;

        moveTimer = new Timer(speed, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moveButtonRandom();
            }
        });
        moveTimer.start();
    }

    private void moveButtonRandom() {
        int maxX = getWidth() - playButton.getWidth() - 20;
        int maxY = getHeight() - playButton.getHeight() - 60;

        int x = generate.nextInt(maxX);
        int y = generate.nextInt(maxY);

        playButton.setLocation(x, y);
    }

    private void onButtonPressed() {
        clicks++;
        scoreLabel.setText("Score: " + clicks);

        currentColor++;
        if (currentColor >= colors.length) currentColor = 0;
        playButton.setBackground(colors[currentColor]);
    }

    private void endGame() {
        // show UI back
        input.setVisible(true);
        startButton.setVisible(true);
        diffLabel.setVisible(true);
        playButton.setVisible(false);
    }
}
