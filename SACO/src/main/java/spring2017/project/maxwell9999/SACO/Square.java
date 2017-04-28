package spring2017.project.maxwell9999.SACO;

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

   public int x() {
      return SQUARE_SIZE * col;
   }

   public int y() {
      return SQUARE_SIZE * row;
   }

   public void setInMoveRange(boolean inMoveRange) {
      this.inMoveRange = inMoveRange;
   }

   public void setUnit(Unit unit) {
      this.unit = unit;
   }

   public Unit getUnit() {
      return this.unit;
   }

   public void capture() {
      if (unit == null) {
         return;
      }
      int healthRemaining = terrain.capure(this.unit.health);
      if (healthRemaining <= 0) {
         terrain.setTeam(unit.team);
      }
   }

}