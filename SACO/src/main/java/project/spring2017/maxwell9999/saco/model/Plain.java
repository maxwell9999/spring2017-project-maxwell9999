package project.spring2017.maxwell9999.saco.model;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Plain extends Terrain {

   public Plain(int movementCost, int health, boolean capturable, int team) {
      super(movementCost, health, capturable, team);
      try {
         icon = new Image("resources/images/plain.gif");
      } catch (SlickException e) {
         System.err.println("Invalid terrain resource filename.");
         e.printStackTrace();
      }
   }

}
