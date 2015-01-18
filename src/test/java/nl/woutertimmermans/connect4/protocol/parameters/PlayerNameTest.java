/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Wouter Timmermans
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package nl.woutertimmermans.connect4.protocol.parameters;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerNameTest {

    private PlayerName validPlayerName;
    private PlayerName testPlayer;

    @Before
    public void setUp() throws Exception {
        validPlayerName = new PlayerName();
        validPlayerName.setValue("Wouter");
        testPlayer = new PlayerName();
    }

    @Test
    public void testTestValue() throws Exception {

        assertTrue(testPlayer.testValue(null));
        assertTrue(testPlayer.testValue("W"));
        assertTrue(testPlayer.testValue("w"));
        assertFalse(testPlayer.testValue(""));
        assertFalse(testPlayer.testValue("Foute Naam"));
        assertFalse(testPlayer.testValue("Ra&reTekens"));

    }

    @Test
    public void testSerialize() throws Exception {

        assertEquals("validPlayerName.serialize()", "Wouter", validPlayerName.serialize());

    }

    @Test
    public void testRead() throws Exception {

        testPlayer.read("Frits");
        assertEquals("testPlayer.getValue()", "Frits", testPlayer.getValue());

    }
}