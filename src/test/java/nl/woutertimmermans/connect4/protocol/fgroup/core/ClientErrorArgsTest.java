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

import nl.woutertimmermans.connect4.protocol.exceptions.SyntaxError;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClientErrorArgsTest {

    private CoreClient.ErrorArgs withMessage;
    private CoreClient.ErrorArgs emptyMessage;
    private CoreClient.ErrorArgs withoutMessage;
    private CoreClient.ErrorArgs empty;

    @Before
    public void setUp() throws Exception {

        withMessage = new CoreClient.ErrorArgs(203, "Error message");
        emptyMessage = new CoreClient.ErrorArgs(204, "");
        withoutMessage = new CoreClient.ErrorArgs(205, null);
        empty = new CoreClient.ErrorArgs();

    }

    @Test(expected = SyntaxError.class)
    public void testRead() throws Exception {
        empty.read(null);
    }

    @Test
    public void testSerialize() throws Exception {

        assertEquals("withMessage.serialize", "203 Error message", withMessage.serialize());
        assertEquals("emptyMessage.serialize", "204 ", emptyMessage.serialize());
        assertEquals("withoutMessage.serialize", "205 ", withoutMessage.serialize());

    }
}