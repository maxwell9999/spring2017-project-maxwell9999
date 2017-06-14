package project.spring2017.maxwell9999.saco.model;

public abstract class Terrain {

   private int movementCost;
   private int defense;
   private int health;
   private boolean capturable;
   private int team;

   public Terrain(int movementCost, int defense, int health, boolean capturable, int team) {
      this.movementCost = movementCost;
      this.defense = defense;
      this.health = health;
      this.capturable = capturable;
      this.team = team;
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

   public int capture(int power) {
      this.health -= power;
      return this.health;
   }

   public void setTeam(int team) {
      this.team = team;
   }

   public boolean isCapturable() {
      return capturable;
   }

   public int getDefense() {
      return defense;
   }

   public int getTeam() {
      return team;
   }
}
