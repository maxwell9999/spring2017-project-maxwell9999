package project.spring2017.maxwell9999.saco.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.InputAdapter;

import project.spring2017.maxwell9999.saco.controllers.Game;

public class Options extends BasicGameState {

   Input inputHandler;
   InputAdapter inputAdapter;
   TextField player1Name;
   TextField player2Name;

   Game game;

   public Options(Game game) {
      this.game = game;
   }

   @Override
   public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
      inputHandler = arg0.getInput();
      player1Name = new TextField(arg0, arg0.getDefaultFont(), 0, 0, 100, 40);
      player2Name = new TextField(arg0, arg0.getDefaultFont(), 0, 0, 100, 40);
   }

   @Override
   public void render(GameContainer arg0, StateBasedGame arg1, Graphics graphics) throws SlickException {

      //load resources
      Image startGameButton = new Image("resources/images/button_start-game.png");

      startGameButton.draw(436, 650);

      graphics.drawString("OPTIONS", 485, 200);
      graphics.drawString("Player 1 (Orange Star)", 300, 300);
      player1Name.setLocation(300, 350);
      player1Name.render(arg0, graphics);
      graphics.drawString("Player 2 (Blue Moon)", 724, 300);
      player2Name.setLocation(724, 350);
      player2Name.render(arg0, graphics);
   }
   @Override
   public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
      if (inputHandler.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
         int mouseX = inputHandler.getMouseX();
         int mouseY = inputHandler.getMouseY();

         if ((mouseX >= 436 && mouseX <= 588) && (mouseY >= 650 && mouseY <= 690)) {
            game.setPlayers(player1Name.getText(), player2Name.getText());
            arg1.enterState(Game.PLAY_STATE);
         }


      }
   }

   @Override
   public int getID() {
      return Game.OPTIONS_STATE;
   }

}