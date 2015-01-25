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
import nl.woutertimmermans.connect4.protocol.parameters.Extension;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AcceptArgsTest {

    private CoreClient.AcceptArgs validArgs;
    private CoreClient.AcceptArgs emptyArgs;


    @Before
    public void setUp() throws Exception {
        Extension ext1 = new Extension();
        ext1.setValue("Chat");
        Extension ext2 = new Extension();
        ext2.setValue("Challenge");
        Set<Extension> validSet = new HashSet<Extension>();
        validSet.add(ext1);
        validSet.add(ext2);

        validArgs = new CoreClient.AcceptArgs(23, validSet);
        emptyArgs = new CoreClient.AcceptArgs();
    }

    @Test(expected = SyntaxError.class)
    public void testReadNull() throws Exception {
        emptyArgs.read(null);
    }

    public void testSerialize() throws Exception {
        assertTrue("23 Chat Challenge".equals(validArgs.serialize()) || "23 Challenge Chat".equals(validArgs.serialize()));
    }

    @Test
    public void testReadValid() throws Exception {
        emptyArgs.read("23");
        assertEquals("serialize should output same result", "23", emptyArgs.serialize());
    }

    @Test
    public void testValidate() throws Exception {
        validArgs.validate();
    }
}