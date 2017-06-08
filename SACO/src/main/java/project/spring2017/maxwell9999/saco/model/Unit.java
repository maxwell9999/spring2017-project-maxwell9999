package project.spring2017.maxwell9999.saco.model;

public abstract class Unit {

   int id;
   int team;
   double health = 10;
   boolean terrainAffected;
   int movement; // maximum number of squares moved per turn
   int cost;

   public Unit(int id, int team, boolean terrainAffected, int movement, int cost) {
      this.id = id;
      this.team = team;
      this.terrainAffected = terrainAffected;
      this.movement = movement;
      this.cost = cost;
   }

   public int getID() {
      return id;
   }

   public int getTeam() {
      return team;
   }

   public double getHealth() {
      return health;
   }

   public int getMovement() {
      return movement;
   }

   public int getCost() {
      return cost;
   }

   public void dealDamage(double damage) {
      health -= damage;
   }
}