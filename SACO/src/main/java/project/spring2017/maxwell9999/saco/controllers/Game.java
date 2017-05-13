package project.spring2017.maxwell9999.saco.controllers;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import project.spring2017.maxwell9999.saco.model.*;
import project.spring2017.maxwell9999.saco.view.*;


/**
 * @author maxwell
 *
 * This is a the overall game logic class.
 * It handles moving the pieces around the Map
 * and oversees interactions between units and
 * buildings.
 *
 */
public class Game extends StateBasedGame {

   //game states
   public static final int MENU_STATE = 0;
   public static final int PLAY_STATE = 1;

   public static final int ORANGE_STAR = 1;
   public static final int BLUE_MOON = 2;

   private Player player1;
   private Player player2;
   private Opponent computer;

   public Game(String title) {
      super(title);
      this.addState(new Menu());
      this.addState(new Play());
   }

   public void battle(Unit attacker, Unit defender) {
      //
   }


   public void displayMoveOptions(Unit unit) {

   }

   public void move(Unit unit, Square square) {

   }

   @Override
   public void initStatesList(GameContainer arg0) throws SlickException {
      enterState(PLAY_STATE);
   }





   public static void main(String[] args) {
      AppGameContainer appGameContainer;

      try {
         appGameContainer = new AppGameContainer(new Game("SACO"));
         appGameContainer.setDisplayMode(1024, 720, false);
         appGameContainer.start();
      } catch (SlickException e) {
         e.printStackTrace();
      }
   }

}