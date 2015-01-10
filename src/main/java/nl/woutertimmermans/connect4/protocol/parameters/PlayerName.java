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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerName implements Parameter {

// ------------------ Instance variables ----------------

    private static final String NAME_REGEX = "^([a-zA-Z]|[0-9])*$";
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);
    private String playerName;

// --------------------- Constructors -------------------

    private PlayerName(String name) {

        playerName = name;

    }

// ----------------------- Queries ----------------------

    public static boolean validName(String name) {
        Matcher m = NAME_PATTERN.matcher(name);
        return m.find() && m.group().equals(name);
    }

    public static PlayerName createPlayerName(String name) throws ParameterFormatException {

        if (validName(name)) {
            return new PlayerName(name);
        } else {
            throw new ParameterFormatException("name does not conform to regex: " + NAME_REGEX);
        }

    }

// ----------------------- Commands ---------------------

    public String toString() {
        return playerName;
    }

}
