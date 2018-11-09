package gui;

import java.util.Random;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Baza extends Application {

	private boolean running = false;
	public static Board enemyBoard, playerBoard;
	public static int moveCounter = 0;
	private int shipsToPlace = 5;

	private boolean enemyTurn = false;

	private Random random = new Random();

	int x = random.nextInt(10);
	int y = random.nextInt(10);

	Boolean flag = false;

	private Parent createContent() {
		BorderPane root = new BorderPane();
		root.setPrefSize(600, 800);
		root.setStyle("-fx-background-color: #696969;");

		Button btnExit = new Button("Exit game");
		btnExit.setAlignment(Pos.CENTER);
		btnExit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});

		enemyBoard = new Board(true, event -> {
			if (!running)
				return;

			Cell cell = (Cell) event.getSource();
			if (cell.wasShot)
				return;

			enemyTurn = !cell.shoot();

			if (enemyBoard.ships == 0) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Battle report");
				alert.setHeaderText("You Win!");
				alert.showAndWait();
				System.exit(0);
			}
			if (enemyTurn)
				enemyMove();
		});

		playerBoard = new Board(false, event -> {
			if (running)
				return;

			Cell cell = (Cell) event.getSource();
			if (playerBoard.placeShip(new Ship(shipsToPlace, event.getButton() == MouseButton.PRIMARY), cell.x,
					cell.y)) {
				if (--shipsToPlace == 0) {
					startGame();
				}
			}
		});



		VBox vbRight = new VBox();

		vbRight.setAlignment(Pos.BASELINE_CENTER);
		vbRight.getChildren().addAll(btnExit, new Label("<- Enemy board"));
		vbRight.setSpacing(75);
		vbRight.setPadding(new Insets(45));

		VBox vbox = new VBox(50, enemyBoard, playerBoard);
		vbox.setAlignment(Pos.CENTER);

		root.setCenter(vbox);

		root.setRight(vbRight);
		return root;
	}

	public void enemyMove() {
		Bot b = new Bot();
		b.play();
		moveCounter++;

	}

	private void startGame() {
		// place enemy ships
		int type = 5;

		while (type > 0) {
			int x = random.nextInt(10);
			int y = random.nextInt(10);

			if (enemyBoard.placeShip(new Ship(type, Math.random() < 0.5), x, y)) {
				type--;
			}
		}

		running = true;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(createContent());
		primaryStage.setTitle("Battleships");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Battleships basic rules");
		alert.setHeaderText(" -ships can be placed next to each others but cant overlap \n -every player has 5 ships ");
		alert.setContentText("Place your 5 ships at the bottom board then try to find enemy's");
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
