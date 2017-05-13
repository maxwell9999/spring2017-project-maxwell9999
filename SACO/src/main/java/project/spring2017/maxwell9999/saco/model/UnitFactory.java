package project.spring2017.maxwell9999.saco.model;

public class UnitFactory {

   private int id;

   public UnitFactory() {
      this.id = 0;
   }

   public Unit createUnit(String type, int team) {
      id++;
      if (type == "inf") {
         return new Infantry(team, this.id);
      } else {
         return null;
      }
   }

}