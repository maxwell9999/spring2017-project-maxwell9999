package spring2017.project.maxwell9999.saco.model;

import java.util.ArrayList;

/**
 *
 * @author maxwell
 *
 * This class holds the Map information in a 2D array.
 * Includes location and terrain data as well as unit
 * positions.
 */

public class Map {

   private Square[][] grid;
   private int rows, cols;
   private ArrayList<Unit> units;

   public Map(Square[][] grid, int rows, int cols) {
      this.grid = grid;
      this.rows = rows;
      this.cols = cols;

      units = new ArrayList<Unit>();
   }

   // getters
   public Square getSquare(int row, int col) {
      return grid[row][col];
   }

   public int rows() {return rows;}
   public int cols() {return cols;}

   public Unit getUnit(int index) {
      return units.get(index);
   }

   public int getNumUnits() {
      return units.size();
   }

   //
   public void addUnit(Unit u) {
      units.add(u);
   }

}