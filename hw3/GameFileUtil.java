package hw3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import api.Tile;

/**
 * Utility class with static methods for saving and loading game files.
 */
public class GameFileUtil {
	/**
	 * Saves the current game state to a file at the given file path.
	 * <p>
	 * The format of the file is one line of game data followed by multiple lines of
	 * game grid. The first line contains the: width, height, minimum tile level,
	 * maximum tile level, and score. The grid is represented by tile levels. The
	 * conversion to tile values is 2^level, for example, 1 is 2, 2 is 4, 3 is 8, 4
	 * is 16, etc. The following is an example:
	 * 
	 * <pre>
	 * 5 8 1 4 100
	 * 1 1 2 3 1
	 * 2 3 3 1 3
	 * 3 3 1 2 2
	 * 3 1 1 3 1
	 * 2 1 3 1 2
	 * 2 1 1 3 1
	 * 4 1 3 1 1
	 * 1 3 3 3 3
	 * </pre>
	 * 
	 * @param filePath the path of the file to save
	 * @param game     the game to save
	 */
	public static void save(String filePath, ConnectGame game) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			Grid grid = game.getGrid();
			int width = grid.getWidth();
			int height = grid.getHeight();
			int min = game.getMinTileLevel();
			int max = game.getMaxTileLevel();

			// Write the game data to the first line
			writer.write(String.format("%d %d %d %d %d\n", width, height, min, max, game.getScore()));

			// Write the grid as tile levels to subsequent lines
			for (int y = 0; y < height; y++) {
			    for (int x = 0; x < width; x++) {
			        Tile tile = grid.getTile(x, y);
			        int level = tile.getLevel();
			        writer.write(String.format("%d", level));
			        if (x < width - 1) {
			            writer.write(" ");
			        }
			    }
			    writer.newLine();
			}
			writer.close();
			

			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the file at the given file path into the given game object. When the
	 * method returns the game object has been modified to represent the loaded
	 * game.
	 * <p>
	 * See the save() method for the specification of the file format.
	 * 
	 * @param filePath the path of the file to load
	 * @param game     the game to modify
	 */
	public static void load(String filePath, ConnectGame game) {
	    BufferedReader reader = new BufferedReader(new FileReader(filePath));

	    // Read game data from first line
	    String[] gameData = reader.readLine().split(" ");
	    int width = Integer.parseInt(gameData[0]);
	    int height = Integer.parseInt(gameData[1]);
	    int min = Integer.parseInt(gameData[2]);
	    int max = Integer.parseInt(gameData[3]);
	    int score = Integer.parseInt(gameData[4]);

	    // Update game with loaded data
	    game = new ConnectGame(width, height, min, max, new Random());
	    game.setScore(score);

	    // Load tile levels into grid
	    Grid grid = game.getGrid();
	    for (int y = 0; y < height; y++) {
	        String[] row = reader.readLine().split(" ");
	        for (int x = 0; x < width; x++) {
	            int level = Integer.parseInt(row[x]);
	            Tile tile = new Tile(level);
	            grid.setTile(tile, x, y);
	        }
	    }

	    reader.close();
	}
	}
}
