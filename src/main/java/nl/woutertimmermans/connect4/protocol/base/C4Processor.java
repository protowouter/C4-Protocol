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

import java.util.Map;

/**
 * An processor is an object that translates an string received
 * from the network to method calls on local objects.
 *
 * @param <I>
 */

public abstract class C4Processor<I> {

// ------------------ Instance variables ----------------

    private I iface;
    private Map<String, C4ProcessFunction<I, ? extends C4Args>> functionMap;

// --------------------- Constructors -------------------

    public C4Processor(I interf) {
        iface = interf;
        functionMap = getProcessMap();
    }

// ----------------------- Queries ----------------------

    protected abstract Map<String, C4ProcessFunction<I, ? extends C4Args>> getProcessMap();

// ----------------------- Commands ---------------------

    public boolean process(String commandstring) throws C4Exception {
        String[] csArray = commandstring.split(" ", 2);
        String command = csArray[0];
        String args = csArray.length > 1 ? csArray[1] : "";
        C4ProcessFunction<I, ? extends C4Args> fn = functionMap.get(command);

        if (fn == null) {
            return false;
        } else {
            fn.process(args, iface);
            return true;
        }
    }

}
