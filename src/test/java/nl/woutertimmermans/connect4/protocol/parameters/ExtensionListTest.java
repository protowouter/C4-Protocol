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

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExtensionListTest {

    ExtensionList valid;
    ExtensionList empty;

    @Before
    public void setUp() throws Exception {
        Set<Extension> exSet = new HashSet<Extension>();
        Extension ex1 = new Extension();
        ex1.setValue("Challenge");
        exSet.add(ex1);
        Extension ex2 = new Extension();
        ex2.setValue("Chat");
        exSet.add(ex2);

        valid = new ExtensionList();
        valid.setValue(exSet);
        empty = new ExtensionList();
    }

    @Test
    public void testSerialize() throws Exception {
        String posOne = "Challenge Chat";
        String posTwo = "Chat Challenge";
        assertTrue(valid.serialize().equals(posOne) || valid.serialize().equals(posTwo));
    }

    @Test
    public void testRead() throws Exception {

        empty.read("Chat Security");
        Set<Extension> test = new HashSet<Extension>();
        Extension ex1 = new Extension();
        Extension ex2 = new Extension();
        ex2.setValue("Security");
        ex1.setValue("Chat");
        test.add(ex1);
        test.add(ex2);
        ExtensionList testList = new ExtensionList();
        testList.setValue(test);
        assertEquals("empty.equals(testList)", empty, testList);

    }

    @Test
    public void testTestValue() throws Exception {

        Set<Extension> test = new HashSet<Extension>();
        Extension ex1 = new Extension();
        Extension ex2 = new Extension();
        ex1.setValue("Chat");
        ex2.setValue("Security");
        test.add(ex1);
        test.add(ex2);
        assertTrue(empty.testValue(test));
        assertTrue(empty.testValue(null));

    }
}