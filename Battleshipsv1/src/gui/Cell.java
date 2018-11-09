package gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
	public int x, y;
	public Ship ship = null;
	public boolean wasShot = false;
	public static LinkedList<Cell> availableCells = new LinkedList();

	private Board board;

	public Cell(int x, int y, Board board) {

		super(35, 35);
		this.x = x;
		this.y = y;
		this.board = board;
		availableCells.add(this);
		setFill(Color.INDIGO);
		setStroke(Color.GRAY);
	}

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;

	}

	public Ship getShip() {
		return ship;
	}

	public int getXCord() {
		return x;
	}

	public void setXCord(int x) {
		this.x = x;
	}

	public int getYCord() {
		return y;
	}

	public void setCord(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Cell [x=" + x + ", y=" + y + "]";
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public boolean isWasShot() {
		return wasShot;
	}

	public void setWasShot(boolean wasShot) {
		this.wasShot = wasShot;
	}

	public static LinkedList<Cell> getAvailableCells() {
		return availableCells;
	}

	public static void setAvailableCells(LinkedList<Cell> availableCells) {
		Cell.availableCells = availableCells;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		result = prime * result + ((ship == null) ? 0 : ship.hashCode());
		result = prime * result + (wasShot ? 1231 : 1237);
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!board.equals(other.board))
			return false;
		if (ship == null) {
			if (other.ship != null)
				return false;
		} else if (!ship.equals(other.ship))
			return false;
		if (wasShot != other.wasShot)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public boolean shoot() {
		wasShot = true;
		setFill(Color.DARKGRAY);

		if (ship != null ) {
			ship.hit();
			setFill(Color.DARKRED);
			availableCells.remove(this);
			if (!ship.isAlive()) {
				//System.out.println("POTOP");
				board.ships--;

			}

			return true;
		}

		return false;
	}
}
