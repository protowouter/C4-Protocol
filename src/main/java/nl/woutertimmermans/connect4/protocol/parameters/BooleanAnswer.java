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

import nl.woutertimmermans.connect4.protocol.exceptions.InvalidParameterError;

public class BooleanAnswer extends Parameter<Boolean> {
    /**
     * Return a String serialization of this Parameter.
     *
     * @return the String serialization of this Parameter.
     */
    @Override
    public String serialize() {
        String result = null;
        if (Boolean.TRUE.equals(getValue())) {
            result = "yes";
        } else if (Boolean.FALSE.equals(getValue())) {
            result = "false";
        }
        return result;
    }

    /**
     * Reads a String representation of this Parameter and tries to parse this.
     *
     * @param argString String representation for this Parameter.
     * @throws nl.woutertimmermans.connect4.protocol.exceptions.InvalidParameterError When the intended value for this parameter is not valid.
     */
    @Override
    public void read(String argString) throws InvalidParameterError {
        if ("yes".equals(argString)) {
            setValue(true);
        } else if ("no".equals(argString)) {
            setValue(false);
        } else {
            throw new InvalidParameterError("Invalid boolean answer");
        }
    }

    @Override
    public boolean testValue(Boolean val) {
        return false;
    }

}
