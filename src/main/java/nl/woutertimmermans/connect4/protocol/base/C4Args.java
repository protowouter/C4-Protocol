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

package nl.woutertimmermans.connect4.protocol.base;

import nl.woutertimmermans.connect4.protocol.exceptions.C4Exception;
import nl.woutertimmermans.connect4.protocol.exceptions.SyntaxError;

/**
 * Models a collection of arguments of arbitrary length.
 * This abstract class provides facilities for both generating
 * strings and parsing from strings.
 */

public abstract class C4Args {

// ----------------------- Queries ----------------------

    /**
     * Returns an array of serialized arguments.
     *
     * @return an array with serialized arguments
     */

    public abstract String[] getArgArray();


// ----------------------- Commands ---------------------

    /**
     * Returns a String with serialized arguments according to the protocol.
     * @return String representation of this Argument collection.
     * @throws nl.woutertimmermans.connect4.protocol.exceptions.SyntaxError when the argument collection
     * doesn't comply with the protocol specification.
     */

    public String serialize() throws SyntaxError {
        validate();
        String[] args = getArgArray();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                result.append(args[i]);
                if (i < args.length - 1) {
                    result.append(" ");
                }
            }
        }
        return new String(result);
    }


    /**
     * Reads a String representation of the protocol and initializes the value in this
     * Argument set.
     * @param argString String representation to be read into this Argument set.
     * @throws C4Exception if these string cannot be parsed to a valid argument collection
     * according to the protocol.
     */
    public abstract void read(String argString) throws C4Exception;

    public abstract void validate() throws SyntaxError;

}
