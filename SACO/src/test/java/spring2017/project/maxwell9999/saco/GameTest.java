package spring2017.project.maxwell9999.saco;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import spring2017.project.maxwell9999.saco.controllers.*;
import spring2017.project.maxwell9999.saco.model.*;

/**
 * Unit test for simple App.
 */
public class GameTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GameTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( GameTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testGame()
    {
        assertTrue(new Game("SACO") != null);
    }

    /**
     * Rigourous Test :-)
     */
    public void testPlain()
    {
       // can't test ui with slick2D
       //assertTrue(new Plain(1, 20, false, 1) != null);
    }

}