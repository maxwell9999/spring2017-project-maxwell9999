package project.spring2017.maxwell9999.saco.view;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import project.spring2017.maxwell9999.saco.controllers.Game;
import project.spring2017.maxwell9999.saco.model.Map;
import project.spring2017.maxwell9999.saco.model.Square;
import project.spring2017.maxwell9999.saco.model.Unit;

public class Play extends BasicGameState {

   Input inputHandler;
   int leftMostX = 0;
   int upperMostY = 0;

   Image plain;
   Image wood;
   Image mountain;

   // each unit needs a separate image loaded
   ArrayList<Image> osUnits = new ArrayList<Image>();
   ArrayList<Image> bmUnits = new ArrayList<Image>();

   Image osInfantry;
   Image bmInfantry;

   private Map map;
   private String mapTitle;

   public Play(Map map, String mapTitle) {
      this.map = map;
      this.mapTitle = mapTitle;
   }

   @Override
   public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
      // load terrain resources
      plain = new Image("resources/images/plain.gif");
      wood = new Image("resources/images/wood.gif");
      mountain = new Image("resources/images/mountain.gif");

      // load unit resources
      osInfantry = new Image("resources/images/osinfantry.gif");
      bmInfantry = new Image("resources/images/bminfantry.gif");

      inputHandler = arg0.getInput();
   }

   @Override
   public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {

      // render the initial click text and title
      Graphics graphics = arg0.getGraphics();
      graphics.drawString(mapTitle.toUpperCase(), 500, 50);
      graphics.drawString("Click a square.", 25, 250);

      //render the map

      // each square of the board is 16 x 16 pixels
      // calculate the pixel value to center the board
      // after leaving 200 pixels for buttons on the left
      // this uses integer division so could be 1 pixel off center, not a big deal
      leftMostX = ((Game.SCREEN_WIDTH + 200) / 2) - ((map.rows() * 16) / 2);
      upperMostY = ((Game.SCREEN_HEIGHT )/ 2 ) - ((map.cols() * 16) / 2);

      // render rows
      for (int i = 0; i < map.rows(); i++) {
         //render columns
         for (int j = 0; j < map.cols(); j++) {
            Square currentSquare = map.getSquare(i, j);
            switch (currentSquare.getTerrain().getClass().toString()) {
               case "class project.spring2017.maxwell9999.saco.model.Plain":
                  plain.draw(leftMostX + 16 * i, upperMostY + 16 * j);
                  break;
               case "class project.spring2017.maxwell9999.saco.model.Wood":
                  wood.draw(leftMostX + 16 * i, upperMostY + 16 * j);
                  break;
               case "class project.spring2017.maxwell9999.saco.model.Mountain":
                  //accounts for extra height of mountain image
                  mountain.draw(leftMostX + 16 * i, upperMostY + 16 * (j-1) + 11);
                  break;
               default :
                  plain.draw(leftMostX + 16 * i, upperMostY + 16 * j);
            }

            Unit currentUnit = currentSquare.getUnit();

            if (currentUnit == null) {
               continue;
            }

            switch (currentUnit.getClass().toString()) {
               case "class project.spring2017.maxwell9999.saco.model.Infantry":
                  if (currentUnit.getTeam() == Game.ORANGE_STAR) {
                     osInfantry.draw(leftMostX + 16 * i, upperMostY + 16 * j);
                  } else {
                     bmInfantry.draw(leftMostX + 16 * i, upperMostY + 16 * j);
                  }
                  break;
            }
         }
      }


   }

   @Override
   public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
      if (inputHandler.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
         int mouseX = inputHandler.getMouseX();
         int mouseY = inputHandler.getMouseY();

         // find the square the mouse is by using the pixels relative to the board
         int boardX = mouseX - leftMostX;
         int boardY = mouseY - upperMostY;

         // if user clicked on board
         // X value divided by 16 is row, x val / 16 is column
         if (boardX >= 0 && boardY >= 0) {
               Square square = map.getSquare(boardX / 16, boardY / 16);
               if (square.getUnit() != null) {
                  osInfantry.destroy();
               }
         }
      }
   }

   @Override
   public int getID() {
      return Game.PLAY_STATE;
   }

}
