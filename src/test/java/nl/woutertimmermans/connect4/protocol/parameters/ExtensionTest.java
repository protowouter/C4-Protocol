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

public class ExtensionTest {

    private Extension chat;
    private Extension challenge;
    private Extension challenge2;
    private Extension emptyExtension;

    @Before
    public void setUp() throws Exception {

        chat = new Extension();
        chat.setValue("Chat");
        challenge = new Extension();
        challenge.setValue("Challenge");
        challenge2 = new Extension();
        challenge2.setValue("Challenge");
        emptyExtension = new Extension();

    }

    @Test
    public void testSerialize() throws Exception {

        assertEquals("chat.serialize()", "Chat", chat.serialize());
        assertEquals("challenge2.serialize()", "Challenge", challenge2.serialize());
        assertEquals("emptyExtension.serialize()", null, emptyExtension.serialize());

    }

    @Test
    public void testEquals() throws Exception {

        assertTrue(challenge.equals(challenge2));
        assertFalse(challenge.equals(chat));
        assertFalse(emptyExtension.equals(challenge));
        assertFalse(chat.equals("Chat"));

    }

    @Test
    public void testHashCode() throws Exception {

        assertTrue(challenge.hashCode() == challenge2.hashCode());
        assertFalse(challenge.hashCode() == chat.hashCode());

    }

    @Test
    public void testTestValue() throws Exception {

        assertTrue(emptyExtension.testValue("Hallo"));
        assertTrue(emptyExtension.testValue("LaTex"));
        assertFalse(emptyExtension.testValue("Chat2"));
        assertFalse(emptyExtension.testValue("La Tex"));
        assertTrue(emptyExtension.testValue(null));

    }

    @Test
    public void testRead() throws Exception {
        final String testString = "Security";
        emptyExtension.read(testString);
        assertEquals("emptyExtension.getValue()", testString, emptyExtension.getValue());

    }
}