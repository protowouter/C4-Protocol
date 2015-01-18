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

public class ErrorCodeTest {

    private ErrorCode zero;
    private ErrorCode nineNineNine;
    private ErrorCode random;
    private ErrorCode empty;

    @Before
    public void setUp() throws Exception {
        zero = new ErrorCode(0);
        nineNineNine = new ErrorCode(999);
        random = new ErrorCode(358);
        empty = new ErrorCode();
    }

    @Test
    public void testTestValue() throws Exception {

        assertTrue(empty.testValue(0));
        assertTrue(empty.testValue(300));
        assertTrue(empty.testValue(123));
        assertTrue(empty.testValue(999));

        assertFalse(empty.testValue(-1));
        assertFalse(empty.testValue(-1343));
        assertFalse(empty.testValue(12043));
        assertFalse(empty.testValue(1000));
        assertTrue(empty.testValue(null));


    }

    @Test
    public void testSerialize() throws Exception {

        assertEquals("empty.serialize()", null, empty.serialize());
        assertEquals("random.serialize()", "358", random.serialize());
        assertEquals("zero.serialize()", "0", zero.serialize());
        assertEquals("nineNineNine.serialize()", "999", nineNineNine.serialize());

    }

    @Test
    public void testRead() throws Exception {
        final int testCode = 457;
        empty.read("457");
        int readVal = empty.getValue();
        assertEquals("empty.getValue()", testCode, readVal);

    }
}