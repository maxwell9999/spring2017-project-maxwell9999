package project.spring2017.maxwell9999.saco.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.InputAdapter;

import project.spring2017.maxwell9999.saco.controllers.Game;

public class EndGame extends BasicGameState {

   Input inputHandler;
   InputAdapter inputAdapter;
   TextField player1Name;
   TextField player2Name;

   Game game;

   public EndGame(Game game) {
      this.game = game;
   }

   @Override
   public void init(GameContainer container, StateBasedGame arg1) throws SlickException {
      inputHandler = container.getInput();
   }

   @Override
   public void render(GameContainer container, StateBasedGame arg1, Graphics graphics) throws SlickException {

      Image splash = new Image("resources/images/splash.jpg");
      Image title = new Image("resources/images/Advance_Wars_logo.png");
      Image endGameButton = new Image("resources/images/button_end-game.png");

      splash.draw(32, 40, 4);
      title.draw(450, 50);

      endGameButton.draw(437, 650);

      String osOutcome;
      String bmOutcome;
      osOutcome = game.player1.getWinState() ? "wins!" : "loses.";
      bmOutcome = game.player2.getWinState() ? "wins!" : "loses.";

      // render player names
      graphics.drawString(game.player1.getName() + " (Orange Star) " + osOutcome, 300, 300);
      graphics.drawString(game.player2.getName() + " (Blue Moon) " + bmOutcome, 724, 300);

   }

   @Override
   public void update(GameContainer container, StateBasedGame arg1, int delta) throws SlickException {
      if (inputHandler.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
         int mouseX = inputHandler.getMouseX();
         int mouseY = inputHandler.getMouseY();

         if ((mouseX >= 437 && mouseX <= 587) && (mouseY >= 650 && mouseY <= 690)) {
            System.exit(0);
         }
      }
   }

   @Override
   public int getID() {
      return Game.END_GAME_STATE;
   }

}
