package project.spring2017.maxwell9999.saco.controllers;

/**
 *
 * @author maxwell
 *
 * Holds the user player information
 *
 */

public class Player {

   private String name;
   private boolean winState;

   public Player(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setWinState(boolean winState) {
      this.winState = winState;
   }

   public boolean getWinState() {
      return winState;
   }

}