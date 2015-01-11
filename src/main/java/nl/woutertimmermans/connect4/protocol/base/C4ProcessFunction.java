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

/**
 * Models an function that can be performed on an interface.
 *
 * @param <I> The interface that should be implemented by the
 *            object on which the function method will be called.
 * @param <A> The class of arguments that this function can process.
 */

public abstract class C4ProcessFunction<I, A extends C4Args> {

// ------------------ Instance variables ----------------

    private String functionName;

// --------------------- Constructors -------------------

    public C4ProcessFunction(String fName) {

    }

// ----------------------- Queries ----------------------

// ----------------------- Commands ---------------------

    public final void process(String argString, I iface) {

        A args = getEmptyArgsInstance();
        args.read(argString);
        perform(args, iface);

    }

    public abstract A getEmptyArgsInstance();

    public abstract void perform(A args, I iface);

}
