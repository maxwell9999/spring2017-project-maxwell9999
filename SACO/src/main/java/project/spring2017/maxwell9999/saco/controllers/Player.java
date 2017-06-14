package project.spring2017.maxwell9999.saco.controllers;

/**
 *
 * @author maxwell
 *
 * Holds the user player information
 *
 */

public class Player {


   public String name;
   public boolean winState;

   public Player(String name) {
      this.name = name;
   }

   public void setWinState(boolean winState) {
      this.winState = winState;
   }

}
