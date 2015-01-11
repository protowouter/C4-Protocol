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

import nl.woutertimmermans.connect4.protocol.exceptions.ParameterFormatException;

import java.util.HashSet;
import java.util.Set;

public class ExtensionList implements Parameter {

// ------------------ Instance variables ----------------

    Set<Extension> extensions;

// --------------------- Constructors -------------------

    public ExtensionList(Set<String> exts) {
        extensions = new HashSet<>();
        exts.forEach((String s) -> extensions.add(new Extension(s)));
    }

    public ExtensionList() {

    }

// ----------------------- Queries ----------------------

    @Override
    public String serialize() {
        StringBuilder res = new StringBuilder();
        for (Extension e : extensions) {
            res.append(e.serialize());
        }
        return new String(res);
    }

    @Override
    public Set<String> getValue() {
        Set<String> result = new HashSet<>();
        extensions.forEach((Extension e) -> { result.add(e.serialize()); });
        return result;
    }

// ----------------------- Commands ---------------------

    @Override
    public void read(String argString) throws ParameterFormatException {

        extensions = new HashSet<>();

        for (String s : argString.split(" ")) {
            Extension ex = new Extension();
            ex.read(s);
            extensions.add(ex);
        }

    }

}
