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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MessageTest {

    Message emptyMessage;
    Message strangeMessage;
    Message trueMessage;
    Message testMessage;

    @Before
    public void setUp() throws Exception {

        emptyMessage = new Message("");
        strangeMessage = new Message("We come in peace &483((21!?>><~><=+-_");
        trueMessage = new Message("This message is the universal truth");
        testMessage = new Message();

    }

    @Test
    public void testTestValue() throws Exception {
        assertTrue(testMessage.testValue(null));
        assertTrue(testMessage.testValue("This is a test"));
    }

    @Test
    public void testSerialize() throws Exception {
        assertEquals("strangeMessage.serialize()",
                "We come in peace &483((21!?>><~><=+-_", strangeMessage.serialize());
    }

    @Test
    public void testRead() throws Exception {

        testMessage.read("Breaky breaky");
        assertEquals("testMessage.getValue()", "Breaky breaky", testMessage.getValue());

    }
}