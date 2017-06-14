package project.spring2017.maxwell9999.saco.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import project.spring2017.maxwell9999.saco.controllers.*;
import project.spring2017.maxwell9999.saco.model.*;

/**
 * Unit test for simple App.
 */
public class GameTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GameTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(GameTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testGame() throws Exception {
       Game testGame = new Game("SACO");

       assertTrue(testGame != null);

       // see if the map was read in
       // exact check by visual inspection
       Map testMap = testGame.getMap();
       assertTrue(testMap != null);

       // check current team
       assertTrue(testGame.getCurrentTeamTurn() == Game.ORANGE_STAR);
       testGame.endCurrentTeamTurn();
       assertTrue(testGame.getCurrentTeamTurn() == Game.BLUE_MOON);
       testGame.endCurrentTeamTurn();

       // check battling and moving
       assertTrue(testMap.getSquare(1, 1).getUnit() != null);
       assertTrue(testMap.getSquare(3, 1).getUnit() == null);
       testGame.move(testMap.getSquare(1, 1), testMap.getSquare(3, 1));
       assertTrue(testMap.getSquare(1, 1).getUnit() == null);
       assertTrue(testMap.getSquare(3, 1).getUnit() != null);
       assertTrue(testMap.getSquare(4, 1).getUnit() != null);
       testGame.battle(testMap.getSquare(3, 1), testMap.getSquare(4, 1));
       assertTrue(testMap.getSquare(4, 1).getUnit().getHealth() < 10);

       // Move options tested by visual inspection

       // set players
       testGame.setPlayers("test name 1", "test name 2");
       assertTrue(testGame.player1.getName().equals("test name 1"));
       assertTrue(testGame.player2.getName().equals("test name 2"));

       // check win states
       testGame.setWinStates(Game.ORANGE_STAR);
       assertTrue(testGame.player1.getWinState() == true &&
                  testGame.player2.getWinState() == false);
       testGame.setWinStates(Game.BLUE_MOON);
       assertTrue(testGame.player1.getWinState() == false &&
                  testGame.player2.getWinState() == true);
    }
}