package project.spring2017.maxwell9999.saco.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import project.spring2017.maxwell9999.saco.controllers.Game;
import project.spring2017.maxwell9999.saco.model.Map;
import project.spring2017.maxwell9999.saco.model.Square;
import project.spring2017.maxwell9999.saco.model.Terrain;
import project.spring2017.maxwell9999.saco.model.Unit;
import project.spring2017.maxwell9999.saco.model.UnitFactory;

public class Play extends BasicGameState {

   boolean showUnitMenu = false;
   boolean showBuyButton = false;
   boolean showTerrainInfo = false;
   boolean moveButtonSelected = false;
   boolean attackButtonSelected = false;
   boolean showUnitCreationError = false;

   Unit lastClickedUnit = null;
   Square lastClickedSquare = null;

   Graphics graphics;
   Input inputHandler;
   int leftMostX = 0;
   int upperMostY = 0;

   Image plain;
   Image wood;
   Image mountain;
   Image sea;

   Image neutralbase;
   Image neutralcity;
   Image orangestarhq;
   Image orangestarbase;
   Image orangestarcity;
   Image bluemoonhq;
   Image bluemoonbase;
   Image bluemooncity;

   Image move;
   Image attack;
   Image capture;
   Image wait;
   Image delete;

   Image selected;
   Image captureIcon;

   Image zeroIcon;
   Image oneIcon;
   Image twoIcon;
   Image threeIcon;
   Image fourIcon;
   Image fiveIcon;
   Image sixIcon;
   Image sevenIcon;
   Image eightIcon;
   Image nineIcon;

   Image buyInfantry;

   Image endTurn;

   Image osInfantry;
   Image bmInfantry;
   Image osInfantryGrey;
   Image bmInfantryGrey;

   private Game game;
   private Map map;
   private String mapTitle;
   private String helpText = "";

   public Play(Game game, String mapTitle) {
      this.game = game;
      this.map = game.getMap();
      this.mapTitle = mapTitle;
   }

   @Override
   public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {

      // setup graphics
      graphics = arg0.getGraphics();

      // load button resources
      move = new Image("resources/images/button_move.png");
      attack = new Image("resources/images/button_attack.png");
      capture = new Image("resources/images/button_capture.png");
      wait = new Image("resources/images/button_wait.png");
      delete = new Image("resources/images/button_delete.png");
      buyInfantry = new Image("resources/images/button_infantry.png");
      endTurn = new Image("resources/images/button_end-turn.png");

      // load terrain resources
      plain = new Image("resources/images/plain.gif");
      wood = new Image("resources/images/wood.gif");
      mountain = new Image("resources/images/mountain.gif");
      sea = new Image("resources/images/sea.gif");

      neutralbase = new Image("resources/images/neutralbase.gif");
      neutralcity = new Image("resources/images/neutralcity.gif");
      orangestarhq = new Image("resources/images/orangestarhq.gif");
      orangestarbase = new Image("resources/images/orangestarbase.gif");
      orangestarcity = new Image("resources/images/orangestarcity.gif");
      bluemoonhq = new Image("resources/images/bluemoonhq.gif");
      bluemoonbase = new Image("resources/images/bluemoonbase.gif");
      bluemooncity = new Image("resources/images/bluemooncity.gif");

      // load unit related resources
      osInfantry = new Image("resources/images/osinfantry.gif");
      bmInfantry = new Image("resources/images/bminfantry.gif");
      osInfantryGrey = new Image("resources/images/gs_osinfantry.gif");
      bmInfantryGrey = new Image("resources/images/gs_bminfantry.gif");

      zeroIcon = new Image("resources/images/0.gif");
      oneIcon = new Image("resources/images/1.gif");
      twoIcon = new Image("resources/images/2.gif");
      threeIcon = new Image("resources/images/3.gif");
      fourIcon = new Image("resources/images/4.gif");
      fiveIcon = new Image("resources/images/5.gif");
      sixIcon = new Image("resources/images/6.gif");
      sevenIcon = new Image("resources/images/7.gif");
      eightIcon = new Image("resources/images/8.gif");
      nineIcon = new Image("resources/images/9.gif");
      captureIcon = new Image("resources/images/capture.gif");
      selected = new Image("resources/images/selected.gif");
      selected.setAlpha((float) 0.5);

      inputHandler = arg0.getInput();
   }

   @Override
   public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {

      if (inputHandler.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
         int mouseX = inputHandler.getMouseX();
         int mouseY = inputHandler.getMouseY();

         clickHandler(mouseX, mouseY);
      }

      if (showBuyButton) {
         displayBuyButton();
      }

      if (showUnitMenu) {
         displayUnitMenu(lastClickedUnit);
      }

      if (moveButtonSelected) {
         helpText += "Click a highlighted square\nto move to it.";
      }
      if (attackButtonSelected) {
         helpText += "Click a highlighted enemy\nto attack to it.";
      }
      if (showUnitCreationError) {
         helpText += "There is already a unit on this factory.";
      }
      if (helpText == "") {
         helpText = "Click a square for unit, building\nand terrain information and options.";
      }

      boardRenderer();

      staticsRenderer();
      helpTextRenderer();
      displayEndTurnButton();

      helpText = "";
   }

   @Override
   public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
      if (game.getWinStatesSet()) {
         arg1.enterState(Game.END_GAME_STATE);
      }
   }

   @Override
   public int getID() {
      return Game.PLAY_STATE;
   }

   private void clickHandler(int mouseX, int mouseY) {

      // handle click on a button
      // if a button is pressed then
      // a unit is not pressed so return
      if (buttonClicked(mouseX, mouseY)) {
         return;
      }

      // check if the user clicked on the board
      // find the square the mouse is by using the pixels relative to the board
      int boardX = mouseX - leftMostX;
      int boardY = mouseY - upperMostY;

      // if user clicked on board
      // X value divided by 16 is row, x val / 16 is column
      if (boardX >= 0 && boardY >= 0) {
         // save the current value in lastClickedSquare to move a unit
         Square previousClickedSquare = lastClickedSquare;
         lastClickedSquare = map.getSquare(boardX / 16, boardY / 16);
         Unit unit = lastClickedSquare.getUnit();
         if (lastClickedSquare.getInMoveRange()) {
            game.move(previousClickedSquare, lastClickedSquare);
         }
         if (lastClickedSquare.getInAttackRange()) {
            game.battle(previousClickedSquare, lastClickedSquare);
            lastClickedUnit.setActive(false);
         }
         if (unit != null) {
            map.clearAllMoveAttackOptions();
            lastClickedUnit = unit;
            showUnitMenu = true;
         } else {
            showUnitMenu = false;
         }
         if (lastClickedSquare.getTerrain().getClass().toString().equals(
               "class project.spring2017.maxwell9999.saco.model.Base") &&
               lastClickedSquare.getTerrain().getTeam() == game.getCurrentTeamTurn()) {
            showBuyButton = true;
         } else {
            showBuyButton = false;
         }
         showTerrainInfo = true;
      }

   }

   private boolean buttonClicked(int mouseX, int mouseY) {
      // buy infantry button
      if (mouseX >= 25 && mouseX <= 197 && mouseY >= 300 && mouseY <= 340) {
         if (lastClickedSquare.getUnit() == null) {
            UnitFactory unitFactory = new UnitFactory();
            Unit newUnit = unitFactory.createUnit("inf", game.getCurrentTeamTurn(), false);
            lastClickedSquare.setUnit(newUnit);
            map.addUnit(newUnit);
         } else {
            showUnitCreationError = true;
         }
         return true;
      }
      // move button
      if (mouseX >= 25 && mouseX <= 121 && mouseY >= 360 && mouseY <= 400) {
         map.clearAllMoveAttackOptions();
         if (lastClickedUnit.canStillMove()) {
            game.findMoveOptions(lastClickedSquare);
            moveButtonSelected = true;
         }
         lastClickedUnit.setCanStillMove(false);
         return true;
      }
      // attack button
      if (mouseX >= 25 && mouseX <= 130 && mouseY >= 420 && mouseY <= 460) {
         map.clearAllMoveAttackOptions();
         game.findBattleOptions(lastClickedSquare);
         attackButtonSelected = true;
         return true;
      }
      // capture button
      if (mouseX >= 150 && mouseX <= 270 && mouseY >= 420 && mouseY <= 460) {
         Terrain terrain = lastClickedSquare.getTerrain();
         if (lastClickedUnit.canStillCapture() && terrain.isCapturable()) {
            int beforeTeam = terrain.getTeam();
            lastClickedSquare.capture();
            int afterTeam = terrain.getTeam();
            if ( afterTeam != beforeTeam && terrain.getClass().toString().equals(
                  "class project.spring2017.maxwell9999.saco.model.HQ")) {
               // an hq was just capture, end the game
               game.setWinStates(afterTeam);
            }
         }
         lastClickedUnit.setCanStillCapture(false);
         return true;
      }
      // wait button
      if (mouseX >= 25 && mouseX <= 113 && mouseY >= 480 && mouseY <= 520) {
         lastClickedUnit.setActive(false);
         return true;
      }
      // delete button
      if (mouseX >= 150 && mouseX <= 254 && mouseY >= 480 && mouseY <= 520) {
         map.removeUnit(lastClickedUnit);
         lastClickedSquare.setUnit(null);
         return true;
      }
      // end turn button
      if (mouseX >= 25 && mouseX <= 153 && mouseY >= 540 && mouseY <= 580) {
         game.endCurrentTeamTurn();
         return true;
      }

      return false;
   }

   private void displayUnitMenu(Unit lastClickedUnit) {
      if (lastClickedUnit == null) {
         return;
      }

      helpText += "Choose an option for this unit. \n";

      if (lastClickedUnit.getTeam() == game.getCurrentTeamTurn()) {
         //display unit option buttons (move, attack, capture, etc.)
         move.draw(25, 360);
         attack.draw(25, 420);
         capture.draw(150, 420);
         wait.draw(25, 480);
         delete.draw(150, 480);
      }
      //display unit statistics (to be added)

   }

   private void displayBuyButton() {
      helpText += "Choose a unit to build. \n";
      buyInfantry.draw(25, 300);
   }

   private void displayEndTurnButton() {
      endTurn.draw(25, 540);
   }

   // display terrain info to be added

   private void staticsRenderer() {
      // render title
      graphics.drawString(mapTitle.toUpperCase(), leftMostX, 50);
      // render player names
      graphics.drawString(game.player1.getName(), leftMostX, upperMostY - 50);
      graphics.drawString(game.player2.getName(), leftMostX + map.rows() * 16, upperMostY - 50);
      // render money (to be added)

   }

   private void helpTextRenderer() {
      // render the help text
      graphics.drawString(helpText, 25, 200);
   }

   private void boardRenderer() {
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
               case "class project.spring2017.maxwell9999.saco.model.Sea":
                  sea.draw(leftMostX + 16 * i, upperMostY + 16 * j);
                  break;
               case "class project.spring2017.maxwell9999.saco.model.Wood":
                  wood.draw(leftMostX + 16 * i, upperMostY + 16 * j);
                  break;
               case "class project.spring2017.maxwell9999.saco.model.Mountain":
                  //accounts for extra height of mountain image
                  mountain.draw(leftMostX + 16 * i, upperMostY + 16 * (j-1) + 11);
                  break;
               case "class project.spring2017.maxwell9999.saco.model.HQ":
                  switch (currentSquare.getTerrain().getTeam()) {
                     case Game.ORANGE_STAR:
                        orangestarhq.draw(leftMostX + 16 * i, upperMostY + 16 * (j-1) + 1);
                        break;
                     case Game.BLUE_MOON:
                        bluemoonhq.draw(leftMostX + 16 * i, upperMostY + 16 * (j-1) + 1);
                        break;
                  }
                  break;
               case "class project.spring2017.maxwell9999.saco.model.Base":
                  switch (currentSquare.getTerrain().getTeam()) {
                     case Game.NEUTRAL:
                        neutralbase.draw(leftMostX + 16 * i, upperMostY + 16 * (j-1) + 7);
                        break;
                     case Game.ORANGE_STAR:
                        orangestarbase.draw(leftMostX + 16 * i, upperMostY + 16 * (j-1) + 7);
                        break;
                     case Game.BLUE_MOON:
                        bluemoonbase.draw(leftMostX + 16 * i, upperMostY + 16 * (j-1) + 7);
                        break;
                  }
                  break;
               case "class project.spring2017.maxwell9999.saco.model.City":
                  switch (currentSquare.getTerrain().getTeam()) {
                     case Game.NEUTRAL:
                        neutralcity.draw(leftMostX + 16 * i, upperMostY + 16 * (j-1) + 10);
                        break;
                     case Game.ORANGE_STAR:
                        orangestarcity.draw(leftMostX + 16 * i, upperMostY + 16 * (j-1) + 10);
                        break;
                     case Game.BLUE_MOON:
                        bluemooncity.draw(leftMostX + 16 * i, upperMostY + 16 * (j-1) + 10);
                        break;
                  }
                  break;
               default :
                  plain.draw(leftMostX + 16 * i, upperMostY + 16 * j);
            }

            if (currentSquare.getInMoveRange() || currentSquare.getInAttackRange()) {
               selected.draw(leftMostX + 16 * i, upperMostY + 16 * j);
            }

            Unit currentUnit = currentSquare.getUnit();

            if (currentUnit == null) {
               continue;
            }

            // RE: sonarqube smell: this is a switch statement
            // to increase extension when more unit types are added
            switch (currentUnit.getClass().toString()) {
               case "class project.spring2017.maxwell9999.saco.model.Infantry":
                  if (currentUnit.getTeam() == Game.ORANGE_STAR) {
                     if (currentUnit.getActive()) {
                        osInfantry.draw(leftMostX + 16 * i, upperMostY + 16 * j);
                     } else {
                        osInfantryGrey.draw(leftMostX + 16 * i, upperMostY + 16 * j);
                     }
                  } else {
                     if (currentUnit.getActive()) {
                        bmInfantry.draw(leftMostX + 16 * i, upperMostY + 16 * j);
                     } else {
                        bmInfantryGrey.draw(leftMostX + 16 * i, upperMostY + 16 * j);
                     }
                  }
                  break;
            }

            if (currentUnit.canStillCapture() == false) {
               captureIcon.draw(leftMostX + 16 * i + 8, upperMostY + 16 * j + 8);
            }

            switch ((int) currentUnit.getHealth()) {
               case 0:
                  zeroIcon.draw(leftMostX + 16 * i + 8, upperMostY + 16 * j + 9);
                  break;
               case 1:
                  oneIcon.draw(leftMostX + 16 * i + 8, upperMostY + 16 * j + 9);
                  break;
               case 2:
                  twoIcon.draw(leftMostX + 16 * i + 8, upperMostY + 16 * j + 9);
                  break;
               case 3:
                  threeIcon.draw(leftMostX + 16 * i + 8, upperMostY + 16 * j + 9);
                  break;
               case 4:
                  fourIcon.draw(leftMostX + 16 * i + 8, upperMostY + 16 * j + 9);
                  break;
               case 5:
                  fiveIcon.draw(leftMostX + 16 * i + 8, upperMostY + 16 * j + 9);
                  break;
               case 6:
                  sixIcon.draw(leftMostX + 16 * i + 8, upperMostY + 16 * j + 9);
                  break;
               case 7:
                  sevenIcon.draw(leftMostX + 16 * i + 8, upperMostY + 16 * j + 9);
                  break;
               case 8:
                  eightIcon.draw(leftMostX + 16 * i + 8, upperMostY + 16 * j + 9);
                  break;
               case 9:
                  nineIcon.draw(leftMostX + 16 * i + 8, upperMostY + 16 * j + 9);
                  break;
            }
         }
      }
   }

}
