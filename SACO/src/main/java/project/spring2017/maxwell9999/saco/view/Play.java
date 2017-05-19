package project.spring2017.maxwell9999.saco.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import project.spring2017.maxwell9999.saco.controllers.Game;
import project.spring2017.maxwell9999.saco.model.Map;
import project.spring2017.maxwell9999.saco.model.Square;
import project.spring2017.maxwell9999.saco.model.Unit;

public class Play extends BasicGameState {

   private Map map;

   public Play(Map map) {
      this.map = map;
   }

   @Override
   public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
   }

   @Override
   public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {

      //render the map
      // load terrain resources
      Image plain = new Image("resources/images/plain.gif");
      Image wood = new Image("resources/images/wood.gif");
      Image mountain = new Image("resources/images/mountain.gif");

      // load unit resources
      Image osInfantry = new Image("resources/images/osinfantry.gif");
      Image bmInfantry = new Image("resources/images/bminfantry.gif");

      //render rows
      for (int i = 0; i < map.rows(); i++) {
         //render columns
         for (int j = 0; j < map.cols(); j++) {
            Square currentSquare = map.getSquare(i, j);
            switch (currentSquare.getTerrain().getClass().toString()) {
               case "class project.spring2017.maxwell9999.saco.model.Plain":
                  plain.draw(432 + 16 * i, 280 + 16 * j);
                  break;
               case "class project.spring2017.maxwell9999.saco.model.Wood":
                  wood.draw(432 + 16 * i, 280 + 16 * j);
                  break;
               case "class project.spring2017.maxwell9999.saco.model.Mountain":
                  //accounts for extra height of mountain image
                  mountain.draw(432 + 16 * i, 280 + 16 * (j-1) + 11);
                  break;
               default :
                  plain.draw(432 + 16 * i, 280 + 16 * j);
            }

            Unit currentUnit = currentSquare.getUnit();

            if (currentUnit == null) {
               continue;
            }

            switch (currentUnit.getClass().toString()) {
               case "class project.spring2017.maxwell9999.saco.model.Infantry":
                  if (currentUnit.getTeam() == Game.ORANGE_STAR) {
                     osInfantry.draw(432 + 16 * i, 280 + 16 * j);
                  } else {
                     bmInfantry.draw(432 + 16 * i, 280 + 16 * j);
                  }
                  break;
            }
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
