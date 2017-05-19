package project.spring2017.maxwell9999.saco.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import project.spring2017.maxwell9999.saco.controllers.Game;

public class Menu extends BasicGameState {

   Input inputHandler;

   public Menu() {}

   @Override
   public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
      inputHandler = arg0.getInput();

   }

   @Override
   public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {

      //load resources
      Image splash = new Image("resources/images/splash.jpg");
      Image title = new Image("resources/images/Advance_Wars_logo.png");
      Image versusAIButton = new Image("resources/images/button_versus-ai.png");
      Image versusPlayerButton = new Image("resources/images/button_versus-player.png");
      Image loadMapButton = new Image("resources/images/button_load-map.png");

      splash.draw(32, 40, 4);
      title.draw(450, 50);
      versusAIButton.draw(445, 225);
      versusPlayerButton.draw(424, 275);
      loadMapButton.draw(443, 325);
   }
   @Override
   public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
      if (inputHandler.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
         int mouseX = inputHandler.getMouseX();
         int mouseY = inputHandler.getMouseY();

         if ((mouseX >= 445 && mouseX <= 578) && (mouseY >= 225 && mouseY <= 265)) {
            arg1.enterState(Game.PLAY_STATE);
         }
      }
   }

   @Override
   public int getID() {
      return Game.MENU_STATE;
   }

}
