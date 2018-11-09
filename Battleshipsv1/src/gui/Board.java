package gui;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board extends Parent {
	private VBox rows = new VBox();
	private boolean enemy = false;
	public int ships = 5;

	public Board(boolean enemy, EventHandler<? super MouseEvent> handler) {
		this.enemy = enemy;
		for (int y = 0; y < 10; y++) {
			HBox row = new HBox();
			for (int x = 0; x < 10; x++) {
				Cell c = new Cell(x, y, this);
				c.setOnMouseClicked(handler);
				row.getChildren().add(c);
			}

			rows.getChildren().add(row);
		}

		getChildren().add(rows);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enemy ? 1231 : 1237);
		result = prime * result + ((rows == null) ? 0 : rows.hashCode());
		result = prime * result + ships;
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
		Board other = (Board) obj;
		if (enemy != other.enemy)
			return false;
		if (rows == null) {
			if (other.rows != null)
				return false;
		} else if (!rows.equals(other.rows))
			return false;
		if (ships != other.ships)
			return false;
		return true;
	}

	public boolean placeShip(Ship ship, int x, int y) {
		if (canPlaceShip(ship, x, y)) {
			int length = ship.type;

			if (ship.vertical) {
				for (int i = y; i < y + length; i++) {
					Cell cell = getCell(x, i);
					cell.ship = ship;

					if (!enemy) {
						cell.setFill(Color.DARKOLIVEGREEN);
						cell.setStroke(Color.BLACK);
					}
				}
			} else {
				for (int i = x; i < x + length; i++) {
					Cell cell = getCell(i, y);
					cell.ship = ship;
					if (!enemy) {
						cell.setFill(Color.DARKOLIVEGREEN);
						cell.setStroke(Color.BLACK);
					}
				}
			}

			return true;
		}

		return false;
	}

	public Cell getCell(int x, int y) {
		return (Cell) ((HBox) rows.getChildren().get(y)).getChildren().get(x);
	}

	private Cell[] getNeighbors(int x, int y) {
		Point2D[] points = new Point2D[] { new Point2D(x, y), new Point2D(x, y), new Point2D(x, y), new Point2D(x, y) };

		List<Cell> neighbors = new ArrayList<Cell>();

		for (Point2D p : points) {
			if (isValidPoint(p)) {
				neighbors.add(getCell((int) p.getX(), (int) p.getY()));
			}
		}

		return neighbors.toArray(new Cell[0]);
	}

	private boolean canPlaceShip(Ship ship, int x, int y) {
		int length = ship.type;

		if (ship.vertical) {
			for (int i = y; i < y + length; i++) {
				if (!isValidPoint(x, i))
					return false;

				Cell cell = getCell(x, i);
				if (cell.ship != null)
					return false;

				for (Cell neighbor : getNeighbors(x, i)) {
					if (!isValidPoint(x, i))
						return false;

					if (neighbor.ship != null)
						return false;
				}
			}
		} else {
			for (int i = x; i < x + length; i++) {
				if (!isValidPoint(i, y))
					return false;

				Cell cell = getCell(i, y);
				if (cell.ship != null)
					return false;

				for (Cell neighbor : getNeighbors(i, y)) {
					if (!isValidPoint(i, y))
						return false;

					if (neighbor.ship != null)
						return false;
				}
			}
		}

		return true;
	}

	private boolean isValidPoint(Point2D point) {
		return isValidPoint(point.getX(), point.getY());
	}

	public boolean isValidPoint(double x, double y) {
		return x >= 0 && x <= 9 && y >= 0 && y <= 9;
	}

}