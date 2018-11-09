package gui;

import java.awt.Desktop.Action;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.AlertType;

public class Bot extends Parent {

	private static Cell firstHit;

	Random r = new Random();
	public static Boolean alive = false;
	public static Boolean up = false;
	public static Boolean right=false;
	public static Boolean vertical = false;

	public void play() {

		if (alive)
			stillAlive();
		else {
			notAlive();
		}

		gameOverCheck();
	}

	private void stillAlive() {
		Boolean hitFlag = true;
		Cell randomCell = Baza.playerBoard.getCell(firstHit.getXCord(), firstHit.getYCord());
		while (hitFlag) {
			if (!firstHit.ship.isVertical()) {
				if (right)
					randomCell = goLeft(randomCell);
				if(!right)
					randomCell = goRight(randomCell);
			}

			else {
				if (up)
					randomCell = goDown(randomCell);
				if (!up) {
					randomCell = goUp(randomCell);
				}

			}
			hitFlag = randomCell.shoot();

		}

		if (!aliveCheck(firstHit)) {
			alive=false;
		}
		if (aliveCheck(firstHit)) {
			alive = true;
		}

	}

	private void notAlive() {
		Boolean hitFlag = false;
		Cell randomCell = getRandomCell();
		randomCell = Baza.playerBoard.getCell(randomCell.getXCord(), randomCell.getYCord());
		firstHit = Baza.playerBoard.getCell(randomCell.getXCord(), randomCell.getYCord());
		firstHit=randomCell;
while(randomCell.wasShot) {
	randomCell = getRandomCell();
	firstHit=randomCell;
	randomCell = Baza.playerBoard.getCell(randomCell.getXCord(), randomCell.getYCord());
	firstHit = Baza.playerBoard.getCell(randomCell.getXCord(), randomCell.getYCord());
	
}
		hitFlag = randomCell.shoot();
		
		if (!hitFlag) {
			alive = false;
			return;
		}
		if (firstHit.getYCord() < 4.5) {

			up = false;
		} else {

			up = true;
		}
		
		if (firstHit.getXCord() < 4.5) {

			right = true;
		} else {

			right = false;
		}
		
		
		while (hitFlag) {
			if (!firstHit.ship.isVertical()) {
				if (right)
					randomCell = goRight(randomCell);
				if(!right)
					randomCell = goLeft(randomCell);
			} else {
				if (up)
					randomCell = goUp(randomCell);
				if(!up)
					randomCell = goDown(randomCell);
				

			}
			hitFlag = randomCell.shoot();

		}
		if (!aliveCheck(firstHit)) {
			alive = false;
	
		}
		if (aliveCheck(firstHit)) {
			alive = true;
		}
	}

	private boolean aliveCheck(Cell c) {
		// TODO Auto-generated method stub
		if (!nullCheck(c))
			if (c.ship.isAlive())
				return true;
		return false;
	}

	private Cell getRandomCell() {
		// TODO Auto-generated method stub
		Cell c = Cell.availableCells.get(r.nextInt(Cell.availableCells.size()));
		while (c.wasShot) {
			c = Cell.availableCells.get(r.nextInt(Cell.availableCells.size()));
		}
		return c;
	}

	private void gameOverCheck() {
		// TODO Auto-generated method stub
		if (Baza.playerBoard.ships <= 0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Battle report");
			alert.setHeaderText("You just got pwned by AI in "+Baza.moveCounter+" moves!");
			// alert.setContentText("Dont worry It was developed by a genious so its okay");

			alert.setOnCloseRequest(new EventHandler<DialogEvent>() {

				@Override
				public void handle(DialogEvent event) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});

			alert.showAndWait();
		}
	}

	private Cell goUp(Cell c) {
		int x = c.getXCord();
		int y = c.getYCord();

		if (y == 0) {
			return goDown(c);

		}

		c = new Cell(x, y);
		c = Baza.playerBoard.getCell(x, --y);
		while (c.wasShot) {
			c = getRandomCell();
			c = Baza.playerBoard.getCell(c.getXCord(), c.getYCord());

		}

		return c;
	}

	private Cell goDown(Cell c) {
		int x = c.getXCord();
		int y = c.getYCord();
		if (y == 9) {

			return goUp(c);
		}
		c = new Cell(x, y);
		c = Baza.playerBoard.getCell(x, ++y);
		while (c.wasShot) {
			c = getRandomCell();
			c = Baza.playerBoard.getCell(c.getXCord(), c.getYCord());

		}

		return c;
	}

	private Cell goRight(Cell c) {
		// TODO Auto-generated method stub y
		int x = c.getXCord();
		int y = c.getYCord();
		if (x == 9) {
			return goLeft(c);

		}
		c = new Cell(x, y);
		c = Baza.playerBoard.getCell(++x, y);
		while (c.wasShot) {

			c = getRandomCell();
			c = Baza.playerBoard.getCell(c.getXCord(), c.getYCord());
		}

		return c;

	}

	private Cell goLeft(Cell c) {
		int x = c.getXCord();
		int y = c.getYCord();
		if (x == 0) {
			return goRight(c);

		}
		c = new Cell(x, y);
		c = Baza.playerBoard.getCell(--x, y);
		while (c.wasShot) {

			c = getRandomCell();
			c = Baza.playerBoard.getCell(c.getXCord(), c.getYCord());

		}

		return c;
	}

	private Boolean nullCheck(Cell c) {
		if (c == null) {
			return true;
		} else
			return false;
	}

}
