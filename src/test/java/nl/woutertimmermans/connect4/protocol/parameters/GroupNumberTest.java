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

public class GroupNumberTest {

    private GroupNumber group22;
    private GroupNumber group0;
    private GroupNumber testGroup;

    @Before
    public void setUp() throws Exception {

        group22 = new GroupNumber(22);
        group0 = new GroupNumber(0);
        testGroup = new GroupNumber();

    }

    @Test
    public void testSerialize() throws Exception {

        assertEquals("group22.serialize()", "22", group22.serialize());
        assertEquals("group0.serialize()", "0", group0.serialize());

    }

    @Test
    public void testTestValue() throws Exception {

        assertFalse(testGroup.testValue(-1));
        assertTrue(testGroup.testValue(null));

    }

    @Test
    public void testRead() throws Exception {

        testGroup.read("23");
        int groupValue = testGroup.getValue();
        assertEquals("testGroup.getValue()", 23, groupValue);

    }
}