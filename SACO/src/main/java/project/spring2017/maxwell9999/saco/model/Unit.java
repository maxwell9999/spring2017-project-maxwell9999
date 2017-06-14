package project.spring2017.maxwell9999.saco.model;

public abstract class Unit {

   int id;
   int team;
   double health = 10;
   boolean terrainAffected;
   int movement; // maximum number of squares moved per turn
   int cost;
   boolean active;
   boolean canStillMove = true;
   boolean canStillCapture = true;

   public Unit(int id, int team, boolean terrainAffected, int movement, int cost, boolean active) {
      this.id = id;
      this.team = team;
      this.terrainAffected = terrainAffected;
      this.movement = movement;
      this.cost = cost;
      this.active = active;
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

   public boolean getActive() {
      return active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public boolean canStillMove() {
      return canStillMove;
   }

   public void setCanStillMove(boolean canStillMove) {
      this.canStillMove = canStillMove;
   }

   public boolean canStillCapture() {
      return canStillCapture;
   }

   public void setCanStillCapture(boolean canStillCapture) {
      this.canStillCapture = canStillCapture;
   }

   public void dealDamage(double damage) {
      health -= damage;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null || !(obj.getClass().equals(this.getClass()))) {
         return false;
      }
      Unit unit = (Unit) obj;
      return this.id == unit.id;
   }

   // overriden for sonarqube purposes
   @Override
   public int hashCode() {
      return 0;
   }
}