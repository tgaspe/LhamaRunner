package application;
	
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Main extends Application {
	
	//Variables
	private static final int width = 1200;
	private static final int height = 500;
	private static final int PLAYER_HEIGHT = 100;
	private static final int PLAYER_WIDTH = 100;
	private static final int player_posX = 85;
	private double player_posY = 300;
	private static final int score_posX = 80;
	private static final int score_posY = 20;
	
	private int score = 0;
	private boolean gameStarted;
	private int speedObstacles = 50;
	private int speedClouds = 20;
	
	
	Scene scene1, scene2, scene3;
	
	
	@Override
	public void start(Stage window) {
		
		window.setTitle("Lhama Runner");
		
		
		
		//Scene1
		Label label1 = new Label("Lhama the fate of the lhamadise is in your hooves");
		Button start_btn = new Button("Start");
		start_btn.setOnAction(e -> window.setScene(scene2));
		StackPane layout1 = new StackPane();
		layout1.getChildren().addAll(label1, start_btn);
		scene1 = new Scene(layout1, width, height);
		
		
		//Scene2
		Canvas canvas = new Canvas(width, height);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
		tl.setCycleCount(Timeline.INDEFINITE);
		//Key Pressed
		//set when space key clicked jump and if scene == 1 or 3 start the game
		canvas.setOnMouseClicked(e -> gameStarted = true);
		scene2 = new Scene(new StackPane(canvas));
		
		//window.setScene(new Scene(new StackPane(canvas)));
		
		window.setScene(scene1);
		window.show();
		tl.play();
		
		//scene1.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
		
	}
	
	private void run(GraphicsContext gc) {
		//set background color 
		gc.setFill(Color.ALICEBLUE);
		gc.fillRect(0, 0, width, height);
		
		//set text color
		gc.setFill(Color.BLACK);
		gc.setFont(Font.font(25));
		
		if (gameStarted) {
			//run game
			//move obstacles and clouds 
			//take user input
		} else {
			//show start scene
			//reset the game
			//reset the score
			
		}
		
		//increase game speed every 50m
		if (score % 50 == 0) {
			//increase speed
			speedObstacles ++;
		}
		
		
		//draw score
		gc.fillText(Integer.toString(score), score_posX, score_posY);
		
		
		//draw Lhama
		gc.fillRect(player_posX, player_posY, PLAYER_WIDTH, PLAYER_HEIGHT);
		//draw obstacles
		//draw clouds
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
