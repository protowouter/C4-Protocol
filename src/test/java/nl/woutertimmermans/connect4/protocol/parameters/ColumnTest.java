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

public class ColumnTest {

    Column col0;
    Column col3;
    Column col6;
    Column emptyCol;

    @Before
    public void setUp() throws Exception {

        col0 = new Column(0);
        col3 = new Column(3);
        col6 = new Column(6);
        emptyCol = new Column();

    }

    @Test
    public void testTestValue() throws Exception {

        assertFalse(emptyCol.testValue(-100));
        assertFalse(emptyCol.testValue(-1));
        assertTrue(emptyCol.testValue(0));
        assertTrue(emptyCol.testValue(1));
        assertTrue(emptyCol.testValue(2));
        assertTrue(emptyCol.testValue(3));
        assertTrue(emptyCol.testValue(4));
        assertTrue(emptyCol.testValue(5));
        assertTrue(emptyCol.testValue(6));
        assertFalse(emptyCol.testValue(7));
        assertFalse(emptyCol.testValue(10));
        assertTrue(emptyCol.testValue(null));

    }

    @Test
    public void testSerialize() throws Exception {
        assertEquals("col0.serialize()", "0", col0.serialize());
        assertEquals("col3.serialize()", "3", col3.serialize());
        assertEquals("col6.serialize()", "6", col6.serialize());
        assertEquals("emptyCol.serialize()", null, emptyCol.serialize());
    }

    @Test
    public void testRead() throws Exception {

        emptyCol.read("5");
        int five = 5;
        int colValue = emptyCol.getValue();
        assertEquals("emptyCol.getValue()", five, colValue);

    }
}