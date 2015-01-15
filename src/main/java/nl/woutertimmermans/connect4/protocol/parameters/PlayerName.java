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

import java.util.logging.Logger;

public class PlayerName implements Parameter {

// ------------------ Instance variables ----------------

    private static final String NAME_REGEX = ParameterRegex.PLAYER_NAME;
    private String playerName;

// --------------------- Constructors -------------------

    public PlayerName(String pName) throws InvalidParameterError {
        if (!validName(pName)) {
            throw new InvalidParameterError("name does not conform to regex: " + NAME_REGEX);
        } else {
            playerName = pName;
        }

    }

    public PlayerName() {

    }

// ----------------------- Queries ----------------------

    public static boolean validName(String name) {

        return name.matches(NAME_REGEX);
    }

    public String serialize() {
        return playerName;
    }

    @Override
    public String getValue() {
        return playerName;
    }

// ----------------------- Commands ---------------------

    public void read(String name) throws InvalidParameterError {

        for (char c : name.toCharArray()) {
            Logger.getGlobal().info("char: " + c);
        }

        if (validName(name)) {
            playerName = name;
        } else {
            throw new InvalidParameterError("name does not conform to regex: " + NAME_REGEX);
        }

    }

}
