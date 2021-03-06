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

import nl.woutertimmermans.connect4.protocol.constants.ParameterRegex;
import nl.woutertimmermans.connect4.protocol.exceptions.InvalidParameterError;

public class Extension extends Parameter<String> {

    static final String EXTENSION_REGEX = ParameterRegex.EXTENSION;

// --------------------- Constructors -------------------

    public Extension() {
        super();
    }

// ----------------------- Queries ----------------------

    @Override
    public String serialize() {
        return getValue();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Extension &&
                ((getValue() == null && ((Extension) o).getValue() == null)
                        || getValue() != null && getValue().equals(((Extension) o).getValue()));
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }

    @Override
    public boolean testValue(String val) {
        return val == null || val.matches(EXTENSION_REGEX);
    }

// ----------------------- Commands ---------------------

    @Override
    public void read(String argString) throws InvalidParameterError {

        setValue(argString);
    }

}
