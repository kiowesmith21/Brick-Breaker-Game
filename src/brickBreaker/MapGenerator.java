package brickBreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
	//2d array of bricks
	public int map[][];
	public int brickWidth;
	public int brickHeight;
	
	public MapGenerator(int row, int col) {
		//create 2d array of bricks using given parameters
		map = new int[row][col];
		
		//rows
		//while i is less than rows
		for(int i = 0; i < map.length; i++) {
			
			//collumns
			//while j is less than collumns
			for (int j = 0; j < map[0].length; j++) {
				
				//1 means there is a brick
				//0 means there is not a brick
				map[i][j] = 1;
				
			}
			
		}
		
		brickWidth = 540 / col;
		brickHeight = 150 / row;
		
	}
	
	public void draw(Graphics2D g) {
	
		for(int i = 0; i < map.length; i++) {
			
			for (int j = 0; j < map[0].length; j++) {
				//if there is a brick
				if(map[i][j] > 0) {
					
					g.setColor(Color.white);
					//draw brick at 
					g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
					
					//draw brick borders
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
					
				}
				
			}
		
		}
	
	}
	
	public void setBrickValue(int value, int row, int col) {
		
		map[row][col] = value;
		
	}
	
}
