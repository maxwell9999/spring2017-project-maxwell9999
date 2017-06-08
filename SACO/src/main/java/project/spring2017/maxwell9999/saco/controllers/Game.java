package project.spring2017.maxwell9999.saco.controllers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

   //game states
   public static final int MENU_STATE = 0;
   public static final int OPTIONS_STATE = 1;
   public static final int PLAY_STATE = 2;

   public static final int NEUTRAL = 0;
   public static final int ORANGE_STAR = 1;
   public static final int BLUE_MOON = 2;

   private Player player1;
   private Player player2;
   private Opponent computer;

   private Map map;
   private String mapTitle;

   public Game(String title) {
      super(title);
      this.mapRead("resources/maps/testmap");
      this.addState(new Menu());
      this.addState(new Play(map, mapTitle));
   }

   public double calculateDamage(Square attacker, Square defender) {
      // formula from awbw.wikia.com/wiki/Damage_Formula
      // damage chart from awbw.amarriner.com/damage.php
      // Note: ideally the chart would but fully mapped
      // but for simplification purposes the infantry value
      // is hardcoded. This will be updated eventually
      // modified to remove COs

      double damagePercent;

      int randInt = ThreadLocalRandom.current().nextInt(0, 10);

      damagePercent = ((55/100.00) + randInt) *
                      (attacker.getUnit().getHealth()/10) *
                      ((200 - (defender.getTerrain().getDefense() *
                               defender.getUnit().getHealth()))/100.00);

      return damagePercent;
   }

   public void battle(Square attacker, Square defender) {
      defender.getUnit().dealDamage(calculateDamage(attacker, defender));
      attacker.getUnit().dealDamage(calculateDamage(defender, attacker));
   }


   public void findMoveOptions(Square currentSquare) {

      ArrayList<Square> moveOptions = new ArrayList<Square>();
      findMoveOptionsRecurs(currentSquare.getUnit(), currentSquare, 0, moveOptions, 0);

   }

   private void findMoveOptionsRecurs(Unit unit, Square currentSquare, int distanceTraveled,
                                      ArrayList<Square> moveOptions, int lastDirection) {

      //is this square in the board?
      if (currentSquare == null) {
         return;
      }
      //is there a unit at this square?
      if (lastDirection != 0 && currentSquare.getUnit() != null) {
         return;
      }

      //is there a terrain obstacle at this square?
      //if (currentSquare.getTerrain()/*is passable*/)

      //subtract movementCost of terrain from movement of unit
      //if non-negative
      //add to move options
      if (lastDirection != 0 && unit.getMovement() - distanceTraveled >= 0) {
         moveOptions.add(currentSquare);
      }

      //recursively check in each cardinal directionto determine possible moves
      //(except the square we just came from)
      Square nextSquare;
      distanceTraveled += currentSquare.getTerrain().getMovementCost();
      //North
      if (lastDirection != SOUTH) {
         nextSquare = map.getSquare(currentSquare.getRow(), currentSquare.getCol() - 1);
         findMoveOptionsRecurs(unit, currentSquare, distanceTraveled, moveOptions, NORTH);
      }
      //East
      if (lastDirection != WEST) {
         nextSquare = map.getSquare(currentSquare.getRow() + 1, currentSquare.getCol());
         findMoveOptionsRecurs(unit, nextSquare, distanceTraveled, moveOptions, EAST);
      }
      //South
      if (lastDirection != NORTH) {
         nextSquare = map.getSquare(currentSquare.getRow(), currentSquare.getCol() + 1);
         findMoveOptionsRecurs(unit, nextSquare, distanceTraveled, moveOptions, SOUTH);
      }
      //West
      if (lastDirection != EAST) {
         nextSquare = map.getSquare(currentSquare.getRow() - 1, currentSquare.getCol());
         findMoveOptionsRecurs(unit, nextSquare, distanceTraveled, moveOptions, WEST);
      }

   }

   public void move(Unit unit, Square square) {


   }

   /**
    * Read the map information in from a CSV file
    */
   public void mapRead(String filename) {

      try {
         CSVReader reader = new CSVReader(new FileReader(filename));

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
               //throw new FileFormatException();
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
                  //throw new FileFormatException();
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
                  case "croad":
                     //terrain = new Road(1, 0, 20, false, NEUTRAL, orientationString);
                     break;
                  case "neutralbase":
                     terrain = new Base(2, 3, 20, true, NEUTRAL);
                     break;
                  case "neutral city":
                     terrain = new City(2, 3, 20, true, ORANGE_STAR);
                     break;
                  case "orangestarhq":
                     terrain = new HQ(2, 4, 20, true, ORANGE_STAR);
                     break;
                  case "orangestarbase":
                     terrain = new Base(2, 3, 20, true, ORANGE_STAR);
                     break;
                  case "orangestarcity":
                     terrain = new City(2, 3, 20, true, ORANGE_STAR);
                     break;
                  case "bluemoonhq":
                     terrain = new HQ(2, 4, 20, true, BLUE_MOON);
                     break;
                  case "bluemoonbase":
                     terrain = new Base(2, 3, 20, true, BLUE_MOON);
                     break;
                  case "bluemooncity":
                     terrain = new City(2, 3, 20, true, BLUE_MOON);
                     break;
                  default :
                     terrain = new Plain(1, 1, 20, false, NEUTRAL);

               }

               UnitFactory unitFactory = new UnitFactory();
               Unit unit = null;

               switch (nextLine[1].trim()) {
                  case "osinfantry":
                     unit = unitFactory.createUnit("inf", ORANGE_STAR);
                     break;
                  case "bminfantry":
                     unit = unitFactory.createUnit("inf", BLUE_MOON);
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
            System.out.println("Incorrect file length");
            //throw new FileFormatException();
         }

         reader.close();

      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Write out the current map to a CSV file
    */



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