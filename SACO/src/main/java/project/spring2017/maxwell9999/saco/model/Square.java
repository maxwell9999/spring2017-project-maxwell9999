package project.spring2017.maxwell9999.saco.model;

public class Square {

   public final static int SQUARE_SIZE = 16;

   private boolean inMoveRange = false;
   private int row;
   private int col;
   private Terrain terrain;
   private Unit unit = null;

   public Square(int row, int col, Terrain terrain) {
      this.row = row;
      this.col = col;
      this.terrain = terrain;
   }
   public Square(int row, int col, Terrain terrain, Unit unit) {
      this.row = row;
      this.col = col;
      this.terrain = terrain;
      this.unit = unit;
   }

   public int getRow() {
      return row;
   }

   public int getCol() {
      return col;
   }

   public int x() {
      return SQUARE_SIZE * col;
   }

   public int y() {
      return SQUARE_SIZE * row;
   }

   public void setInMoveRange(boolean inMoveRange) {
      this.inMoveRange = inMoveRange;
   }

   public boolean getInMoveRange() {
      return inMoveRange;
   }

   public void setUnit(Unit unit) {
      this.unit = unit;
   }

   public Unit getUnit() {
      return this.unit;
   }

   public Terrain getTerrain() {
      return this.terrain;
   }

   public void capture() {
      if (unit == null) {
         return;
      }
      int healthRemaining = terrain.capture((int) this.unit.health);
      if (healthRemaining <= 0) {
         terrain.setTeam(unit.team);
      }
   }

}