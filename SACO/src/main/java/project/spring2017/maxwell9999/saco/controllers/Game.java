package project.spring2017.maxwell9999.saco.controllers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ThreadLocalRandom;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.opencsv.CSVReader;

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

   public static final int SCREEN_WIDTH = 1024;
   public static final int SCREEN_HEIGHT = 720;

   public static final int NORTH = 1;
   public static final int EAST = 2;
   public static final int SOUTH = 3;
   public static final int WEST = 4;

   public static final int NEUTRAL = 0;
   public static final int ORANGE_STAR = 1;
   public static final int BLUE_MOON = 2;

   //game states
   public static final int MENU_STATE = 0;
   public static final int OPTIONS_STATE = 1;
   public static final int PLAY_STATE = 2;
   public static final int END_GAME_STATE = 3;

   public Player player1;
   public Player player2;
   public Opponent computer;
   private int currentTeamTurn;
   private boolean winStatesSet;

   private Map map;
   private String mapTitle;

   public Game(String title) {
      super(title);
      try {
         this.mapRead("resources/maps/testmap2");
      } catch (Exception e) {
      }
      this.addState(new Menu());
      this.addState(new Options(this));
      this.addState(new Play(this, mapTitle));
      this.addState(new EndGame(this));
      this.currentTeamTurn = ORANGE_STAR;
      this.winStatesSet = false;
   }

   public void setPlayers(String player1Name) {
      player1 = new Player(player1Name);
      player2 = null;
      computer = new Opponent();
   }

   public void setPlayers(String player1Name, String player2Name) {
      player1 = new Player(player1Name);
      player2 = new Player(player2Name);
      computer = null;
   }

   private double calculateDamage(Square attacker, Square defender) {
      // formula from awbw.wikia.com/wiki/Damage_Formula
      // damage chart from awbw.amarriner.com/damage.php
      // Note: ideally the chart would but fully mapped
      // but for simplification purposes the infantry value
      // is hardcoded. This will be updated eventually
      // modified to remove COs

      double damagePercent;

      int randInt = ThreadLocalRandom.current().nextInt(0, 10);

      damagePercent = ((55) + randInt) *
                      (attacker.getUnit().getHealth()/10) *
                      ((200 - (100 + defender.getTerrain().getDefense() *
                               defender.getUnit().getHealth()))/100.00);

      return damagePercent;
   }

   public void battle(Square attacker, Square defender) {
      Unit attackingUnit = attacker.getUnit();
      Unit defendingUnit = defender.getUnit();
      defendingUnit.dealDamage(calculateDamage(attacker, defender)/10);
      if (defendingUnit.getHealth() <= 0) {
         map.removeUnit(defendingUnit);
         defender.setUnit(null);
         checkUnitsExist(defendingUnit.getTeam());
      } else {
         attackingUnit.dealDamage(calculateDamage(defender, attacker)/10);
      }
      if (attackingUnit.getHealth() <= 0) {
         map.removeUnit(attackingUnit);
         attacker.setUnit(null);
         checkUnitsExist(attackingUnit.getTeam());
      }
      map.clearAllMoveAttackOptions();
   }

   public void findBattleOptions(Square currentSquare) {
      int attackingTeam = currentSquare.getUnit().getTeam();
      Square nextSquare;
      //North
      nextSquare = map.getSquare(currentSquare.getRow(), currentSquare.getCol() - 1);
      if (nextSquare != null && nextSquare.getUnit() != null && nextSquare.getUnit().getTeam() != attackingTeam) {
         nextSquare.setInAttackRange(true);
      }
      //East
      nextSquare = map.getSquare(currentSquare.getRow() + 1, currentSquare.getCol());
      if (nextSquare != null && nextSquare.getUnit() != null && nextSquare.getUnit().getTeam() != attackingTeam) {
         nextSquare.setInAttackRange(true);
      }
      //South
      nextSquare = map.getSquare(currentSquare.getRow(), currentSquare.getCol() + 1);
      if (nextSquare != null && nextSquare.getUnit() != null && nextSquare.getUnit().getTeam() != attackingTeam) {
         nextSquare.setInAttackRange(true);
      }
      //West
      nextSquare = map.getSquare(currentSquare.getRow() - 1, currentSquare.getCol());
      if (nextSquare != null && nextSquare.getUnit() != null && nextSquare.getUnit().getTeam() != attackingTeam) {
         nextSquare.setInAttackRange(true);
      }
   }

   public void findMoveOptions(Square currentSquare) {

      findMoveOptionsRecurs(currentSquare.getUnit(), currentSquare, 0, 0);
   }

   private void findMoveOptionsRecurs(Unit unit, Square currentSquare, int terrainCovered, int lastDirection) {

      //is this square in the board?
      if (currentSquare == null) {
         return;
      }
      //is there a unit at this square?
      if (lastDirection != 0 && currentSquare.getUnit() != null) {
         return;
      }

      //is there a terrain obstacle at this square? (to be added)


      int newTerrainCovered = terrainCovered;

      //subtract movementCost of terrain from movement of unit
      //if non-negative
      //add to move options
      if (unit.getMovement() - terrainCovered < currentSquare.getTerrain().getMovementCost()) {
         return;
      } else if (lastDirection != 0) {
         currentSquare.setInMoveRange(true);
         newTerrainCovered += currentSquare.getTerrain().getMovementCost();
      }

      //recursively check in each cardinal directionto determine possible moves
      //(except the square we just came from)
      Square nextSquare;
      //North
      if (lastDirection != SOUTH) {
         nextSquare = map.getSquare(currentSquare.getRow(), currentSquare.getCol() - 1);
         findMoveOptionsRecurs(unit, nextSquare, newTerrainCovered, NORTH);
      }
      //East
      if (lastDirection != WEST) {
         nextSquare = map.getSquare(currentSquare.getRow() + 1, currentSquare.getCol());
         findMoveOptionsRecurs(unit, nextSquare, newTerrainCovered, EAST);
      }
      //South
      if (lastDirection != NORTH) {
         nextSquare = map.getSquare(currentSquare.getRow(), currentSquare.getCol() + 1);
         findMoveOptionsRecurs(unit, nextSquare, newTerrainCovered, SOUTH);
      }
      //West
      if (lastDirection != EAST) {
         nextSquare = map.getSquare(currentSquare.getRow() - 1, currentSquare.getCol());
         findMoveOptionsRecurs(unit, nextSquare, newTerrainCovered, WEST);
      }

   }

   public void move(Square start, Square end) {
      Unit moving = start.getUnit();
      start.setUnit(null);
      end.setUnit(moving);
      map.clearAllMoveAttackOptions();
   }

   /**
    * Read the map information in from a CSV file
    * @throws Exception
    */
   public void mapRead(String filename) throws Exception {

      CSVReader reader = null;

      try {
         reader = new CSVReader(new FileReader(filename));

         // holds map data: title, width, height
         String[] firstLine;
         // holds square data: terrain, unit
         String[] nextLine;
         // the number of lines in the file that represent squares
         int numOfSquares = 0;
         // the row and column numbers specified in the file
         int rowVal = 0;
         int colVal = 0;

         if ((firstLine = reader.readNext()) != null) {
            if (firstLine.length != 3) {
               // log that the first has a format error
               throw new Exception();
            }
            this.mapTitle = firstLine[0].trim();
            rowVal = Integer.valueOf(firstLine[1].trim());
            colVal = Integer.valueOf(firstLine[2].trim());
            this.map = new Map(rowVal, colVal);
         }


       //render 10 rows
         for (int i = 0; i < rowVal; i++) {
            for (int j = 0; j < colVal; j++) {
               //nextLine is next terrain info
               nextLine = reader.readNext();
               if (nextLine == null || nextLine.length != 2) {
                  // log that there is a blank line
                  // or a line is missing a value
                  throw new Exception();
               }

               numOfSquares++;

               Terrain terrain = null;

               switch (nextLine[0].trim()) {
                  case "plain":
                     terrain = new Plain(1, 1, 20, false, NEUTRAL);
                     break;
                  case "wood":
                     terrain = new Wood(1, 2, 20, false, NEUTRAL);
                     break;
                  case "mountain":
                     terrain = new Mountain(2, 4, 20, false, NEUTRAL);
                     break;
                  case "sea":
                     terrain = new Sea(1000, 1, 20, false, NEUTRAL);
                     break;
                  case "croad":
                     // to be added terrain = new Road(1, 0, 20, false, NEUTRAL, orientationString)
                     break;
                  case "neutralbase":
                     terrain = new Base(1, 3, 20, true, NEUTRAL);
                     break;
                  case "neutralcity":
                     terrain = new City(1, 3, 20, true, ORANGE_STAR);
                     break;
                  case "orangestarhq":
                     terrain = new HQ(1, 4, 20, true, ORANGE_STAR);
                     break;
                  case "orangestarbase":
                     terrain = new Base(1, 3, 20, true, ORANGE_STAR);
                     break;
                  case "orangestarcity":
                     terrain = new City(1, 3, 20, true, ORANGE_STAR);
                     break;
                  case "bluemoonhq":
                     terrain = new HQ(1, 4, 20, true, BLUE_MOON);
                     break;
                  case "bluemoonbase":
                     terrain = new Base(1, 3, 20, true, BLUE_MOON);
                     break;
                  case "bluemooncity":
                     terrain = new City(1, 3, 20, true, BLUE_MOON);
                     break;
               }

               UnitFactory unitFactory = new UnitFactory();
               Unit unit = null;

               switch (nextLine[1].trim()) {
                  case "osinfantry":
                     unit = unitFactory.createUnit("inf", ORANGE_STAR, true);
                     break;
                  case "bminfantry":
                     unit = unitFactory.createUnit("inf", BLUE_MOON, true);
                     break;
                  default :
                     unit = null;
               }

               this.map.setSquare(i, j, terrain, unit);
               if (unit != null) {
                  this.map.addUnit(unit);
               }
            }
         }

         if (numOfSquares != rowVal*colVal) {
            // log that there is an incorrect number of lines
            throw new Exception();
         }

      } catch (IOException e) {
         e.printStackTrace();
      } catch (Exception e) {
         // log
      } finally {
         try {
            reader.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }

   /**
    * Write out the current map to a CSV file
    */



   public Map getMap() {
      return map;
   }

   public int getCurrentTeamTurn() {
      return currentTeamTurn;
   }

   public void endCurrentTeamTurn() {
      currentTeamTurn = (currentTeamTurn == ORANGE_STAR) ? BLUE_MOON : ORANGE_STAR;
      for (Unit unit : map.getUnits()) {
         if (unit.getTeam() == currentTeamTurn) {
            unit.setActive(true);
            unit.setCanStillCapture(true);
            unit.setCanStillMove(true);
         }
      }
   }

   /**
    * checks to see if a side has any units
    * if all units are destroyed on one side
    * then other side wins
    *
    * this is only a win condition after both
    * sides have had at least one unit therefore
    * it is called upon a unit being destroyed
    */
   public void checkUnitsExist(int team) {
      for (Unit unit : map.getUnits()) {
         if (unit.getTeam() == team){
            return;
         }
      }

      setWinStates(team);
   }

   public void setWinStates(int winner) {
      winStatesSet = true;
      if (winner == ORANGE_STAR) {
         player1.setWinState(true);
         player2.setWinState(false);
      } else {
         player1.setWinState(false);
         player2.setWinState(true);
      }
   }

   public boolean getWinStatesSet() {
      return winStatesSet;
   }

   @Override
   public void initStatesList(GameContainer arg0) throws SlickException {
      enterState(MENU_STATE);
   }

   public static void main(String[] args) {
      AppGameContainer appGameContainer;

      try {
         appGameContainer = new AppGameContainer(new Game("SACO"));
         appGameContainer.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
         appGameContainer.start();
      } catch (SlickException e) {
         e.printStackTrace();
      }
   }

}