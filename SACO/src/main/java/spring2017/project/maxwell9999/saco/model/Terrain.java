package spring2017.project.maxwell9999.saco.model;

public abstract class Terrain {

   private int movementCost;
   private int health = 20;
   private boolean capturable;
   private int team;
   //Image icon; slick2d image

   public Terrain(int movementCost, int health, boolean capturable, int team/*, String filename*/) {
      this.movementCost = movementCost;
      this.capturable = capturable;
      this.team = team;
      //icon = new Image(filename);
   }

   public int getMovementCost() {
      return this.movementCost;
   }

   public int getHealth() {
      return this.health;
   }

   public void setHealth(int health) {
      this.health = health;
   }

   public int capure(int power) {
      this.health -= power;
      return this.health;
   }

   public void setTeam(int team) {
      this.team = team;
   }

   public boolean isCapturable() {
      return capturable;
   }
}
