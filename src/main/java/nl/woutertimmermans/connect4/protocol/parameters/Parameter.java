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


/**
 * This class provides a skeleton implementation of a Parameter. It provides constructors for creating both
 * an empty and a filled in Parameter and facilities for reading the value from a String and serializing
 * to a String. On all set operations it is tested whether the intended value for this Parameter is valid.
 *
 * @param <T> The type of this parameter.
 */
public abstract class Parameter<T> {

    /*@
     * invariant testValue(value);
     */
    private T value;

    /**
     * Creates an empty instance of this Parameter.
     */
    public Parameter() {

    }

    /**
     * Return a String serialization of this Parameter.
     * @return the String serialization of this Parameter.
     */
    public abstract String serialize();

    /**
     * Reads a String representation of this Parameter and tries to parse this.
     * @param argString String representation for this Parameter.
     * @throws InvalidParameterError When the intended value for this parameter is not valid.
     *
     */
    public abstract void read(String argString) throws InvalidParameterError;


    /**
     * Returns the value of this Parameter.
     * @return the value of this Parameter.
     */
    public T getValue() {
        return value;
    }


    /**
     * Sets the value of this Parameter. Checks whether this value is correct.
     * @param newVal The intended value for this Parameter.
     * @throws InvalidParameterError if the value for this parameter is invalid.
     */
    public void setValue(T newVal) throws InvalidParameterError {
        if (testValue(newVal)) {
            value = newVal;
        } else {
            throw new InvalidParameterError("Argument " + newVal + " is not valid");
        }
    }

    public abstract boolean testValue(T val);

}
