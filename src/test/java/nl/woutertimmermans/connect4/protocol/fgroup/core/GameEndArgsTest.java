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

package nl.woutertimmermans.connect4.protocol.fgroup.core;

import nl.woutertimmermans.connect4.protocol.base.C4Args;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameEndArgsTest {

    C4Args tie;
    C4Args winner;
    C4Args empty;

    @Before
    public void setUp() throws Exception {

        tie = new CoreClient.GameEndArgs(null);
        winner = new CoreClient.GameEndArgs("Wouter");
        empty = new CoreClient.GameEndArgs();

    }

    @Test
    public void testReadNull() throws Exception {

        empty.read(null);
        assertEquals("empty.serialize()", "", empty.serialize());

    }

    @Test
    public void testReadNotNull() throws Exception {
        empty.read("FritsWester");
        assertEquals("emtpy.serialize()", "FritsWester", empty.serialize());
    }

    @Test
    public void testSerialize() throws Exception {
        assertEquals("winner.serialize()", "Wouter", winner.serialize());
        assertEquals("tie.serialize()", "", tie.serialize());
    }
}