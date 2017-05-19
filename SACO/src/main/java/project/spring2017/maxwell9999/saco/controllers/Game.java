package project.spring2017.maxwell9999.saco.controllers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

   //game states
   public static final int MENU_STATE = 0;
   public static final int PLAY_STATE = 1;

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
      this.mapRead("resources/maps/testmap2");
      this.addState(new Menu());
      this.addState(new Play(map, mapTitle));
   }

   public void battle(Unit attacker, Unit defender) {
      //
   }


   public void displayMoveOptions(Unit unit) {

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