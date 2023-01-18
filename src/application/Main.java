/* Lhama Game: Created by Theodoro January 14, 2023 */

package application;
	
import javafx.animation.KeyFrame;
import java.util.Random;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class Main extends Application implements EventHandler<KeyEvent> {
	
	// --- Variables ---
	private static final int width = 1200;
	private static final int height = 500;
	private static final int PLAYER_HEIGHT = 100;
	private static final int PLAYER_WIDTH = 80;
	private static final int player_posX = 85;
	private int           player_posY = 310;
	private static final int score_posX = 80;
	private static final int score_posY = 50;
	private static final int obstacle_posY = 345;
	private int 			 obstacle_posX = 1200;
	private int 			 obstacle_posX1 = 1300;
	private int offset = 0;
	private static final int OBSTACLE_HEIGHT = 70;
	private static final int OBSTACLE_WIDTH = 10;
	private int cloud_posY = 100;
	private int cloud_posX = 1300;
	private int score = 0;
	private boolean gameStarted = false;
	private boolean gameOver = false;
	private int speedObstacles = 55;
	private int speedClouds = 5;
	double gravity = 6;
	double fall_speed = 0.0;
	double myTime = 0.0;
	boolean jump = false;
	
	Scene scene;
	Image lhama = new Image("lhama.png", 150, 150, false, false);
	Image cloud1 = new Image("cloud1.png", 100, 60, false, false);
	Image cloud2 = new Image("cloud1.png", 90, 50, false, false);
	Image cloud3 = new Image("cloud1.png", 120, 60, false, false);
	
	@Override
	public void start(Stage window) {
		
		window.setTitle("Lhama Runner");
		
		// --- Scene --- //
		Canvas canvas = new Canvas(width, height);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Timeline tl = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
		
		tl.setCycleCount(Timeline.INDEFINITE);
		StackPane layout =  new StackPane();
		layout.getChildren().addAll(canvas);
		
		scene = new Scene(layout);
		scene.setOnKeyPressed(this);
		
		window.setScene(scene);
		window.show();

		tl.play();
		
		
	}
	
	private void run(GraphicsContext gc) {
		
		// Instance of random class
	    Random rand = new Random(); 
	    int upperbound = 10;
	    int int_random = rand.nextInt(upperbound);
	    
	    // too close obstacles
	    if (int_random < 9) {
	    	int_random = 14;
	    }
		
		//draw background 
		gc.setFill(Color.rgb(152,223,230,1.0));
		gc.fillRect(0, 0, width, height);
		
		//draw grass 
		gc.setFill(Color.rgb(176, 204, 39));
		gc.fillRect(0, 400, 1200, 100);
		
		//draw text
		gc.setFill(Color.BLACK);
		gc.setFont(Font.font(25));
		
		//draw score
		gc.fillText("Score: " + Integer.toString(score), score_posX, score_posY);
					
		
		if (gameStarted) {
			
			//Increase score
			score ++;
			
			//move obstacles and clouds
			obstacle_posX = obstacle_posX - speedObstacles;
			obstacle_posX1 = obstacle_posX1 - speedObstacles;
			cloud_posX -= speedClouds;
			
			//Gravity
			fall_speed = gravity*0.5*Math.pow(myTime, 2);
			player_posY += fall_speed;
			
			//Fall time 
			if (player_posY > 0 && player_posY < 350) {
				myTime += 1;
			}
			
			//increase game speed every 50m
			if (score % 100 == 0 && score != 0) {
				//increase speed
				speedObstacles += 5;
			}
			
			//Floor boundary for gravity
			if (player_posY > 310) {
				player_posY = 310;
				jump = false;
				myTime = 0;
			}
			
			
			//Collision with obstacle
			if (obstacle_posX <= player_posX + PLAYER_WIDTH && obstacle_posX >= player_posX && obstacle_posY <= player_posY + PLAYER_HEIGHT) {			
				gameStarted = false;
				gameOver = true;
					
			} else if (obstacle_posX1 <= player_posX + PLAYER_WIDTH && obstacle_posX1 >= player_posX && obstacle_posY <= player_posY + PLAYER_HEIGHT) {
				gameStarted = false;
				gameOver = true;
			}
			
			if (obstacle_posX < 0 ) {
				obstacle_posX = 1200;
			}
			
			if (obstacle_posX1 < 0) {
				offset = int_random * 50;
				obstacle_posX1 = 1200 + offset;
			}
			
			if (cloud_posX + 1000 < 0) {
				cloud_posX = 1300;
			}
			
			//draw Lhama
			gc.drawImage(lhama, player_posX -30, player_posY -37);
			//gc.fillRect(player_posX, player_posY, PLAYER_WIDTH, PLAYER_HEIGHT);
			
			//draw Obstacles
			gc.setFill(Color.rgb(176, 148, 102));
			gc.fillRect(obstacle_posX, obstacle_posY, OBSTACLE_WIDTH, OBSTACLE_HEIGHT);
			
			//draw Obstacles
			gc.setFill(Color.rgb(176, 148, 102));
			gc.fillRect(obstacle_posX1, obstacle_posY, OBSTACLE_WIDTH, OBSTACLE_HEIGHT);
			
			//draw Clouds
			gc.drawImage(cloud1, cloud_posX, cloud_posY);
			gc.drawImage(cloud2, cloud_posX +970, cloud_posY -30);
			gc.drawImage(cloud3, cloud_posX +530, cloud_posY +50);
			
			
		} else if (gameOver) {
			
			//Reset the game
			obstacle_posX = 1200;
			speedObstacles = 55;
			speedClouds = 5;
			
			gc.setStroke(Color.BLACK);
	 		gc.setTextAlign(TextAlignment.CENTER);
			gc.setFont(Font.font(45));
			gc.fillText("Start", width/2, height/2);
			gc.fillText(Integer.toString(score), width/2, height/2 + 50);
			
			gameStarted = false;
			
		} else {
			
			//Start the game
			obstacle_posX = 1200;
			speedObstacles = 55;
			speedClouds = 5;
			
			gc.setStroke(Color.BLACK);
			gc.setFont(Font.font(45));
			gc.fillText("Start", width/2, height/2);
			gc.setTextAlign(TextAlignment.CENTER);
			
			gameStarted = false;
			
		}
		
	}

	@Override
	public void handle(KeyEvent event) {

		KeyCode keycode = event.getCode();
		
		if (keycode == keycode.SPACE) {
			player_posY = 150; 
			
			if (!gameStarted) {
				score = 0;
			}
		} 
		
		gameStarted = true;
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
		
	
}

