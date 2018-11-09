package gui;

import javafx.scene.Parent;

	public class Ship extends Parent {
	    public int type;
	    public boolean vertical = true;

	    private int health;

	    public Ship(int type, boolean vertical) {
	        this.type = type;
	        this.vertical = vertical;
	        health = type;
	    }

	    public void hit() {
	        health--;
	    }

	    public boolean isAlive() {
	        //return health > 0;
	    	if(health>0) return true;
	    	else {
	    		return false;
	    	}
	    }

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public boolean isVertical() {
			return vertical;
		}

		public void setVertical(boolean vertical) {
			this.vertical = vertical;
		}

		public int getHealth() {
			return health;
		}

		public void setHealth(int health) {
			this.health = health;
		}
	    
	    
	    
	    
	}

