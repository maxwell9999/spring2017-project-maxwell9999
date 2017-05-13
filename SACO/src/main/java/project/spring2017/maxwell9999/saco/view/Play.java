package project.spring2017.maxwell9999.saco.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import project.spring2017.maxwell9999.saco.controllers.Game;

public class Play extends BasicGameState {

   public Play() {
      // TODO Auto-generated constructor stub
   }

   @Override
   public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
      Image background = new Image("resources/images/background.gif");

   }

   @Override
   public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {

      //render the map

      Image plain = new Image("resources/images/plain.gif");

      //render 10 rows
      for (int i = 0; i < 10; i++) {
         //render 10 columns
         for (int j = 0; j < 10; j++) {
            plain.draw(432 + 16 * i, 280 + 16 * j);
         }
      }


   }

   @Override
   public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
      // TODO Auto-generated method stub

   }

   @Override
   public int getID() {
      return Game.PLAY_STATE;
   }

}
