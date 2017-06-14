package project.spring2017.maxwell9999.saco.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import project.spring2017.maxwell9999.saco.controllers.Player;

/**
 * Unit test for SACO Game controller.
 */
public class PlayerTest extends TestCase {

   /**
    * Create the test case
    *
    * @param testName name of the test case
    */
   public PlayerTest(String testName) {
      super(testName);
   }

   /**
    * @return the suite of tests being tested
    */
   public static Test suite() {
      return new TestSuite(PlayerTest.class);
   }

   /**
    * Rigourous Test :-)
    */
   public void testPlayer() {

      Player testPlayer = new Player("test name");

      assertTrue(testPlayer != null);

      testPlayer.setWinState(true);
      assertTrue(testPlayer.winState);
      testPlayer.setWinState(false);
      assertTrue(!testPlayer.winState);

   }

}