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

package nl.woutertimmermans.connect4.protocol.fgroup;

import nl.woutertimmermans.connect4.protocol.base.C4Args;
import nl.woutertimmermans.connect4.protocol.base.C4ProcessFunction;
import nl.woutertimmermans.connect4.protocol.base.C4Processor;
import nl.woutertimmermans.connect4.protocol.constants.CommandString;
import nl.woutertimmermans.connect4.protocol.exceptions.ParameterFormatException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CoreClient {

    /**
     * These are the commands that the server can send to the client.
     */

    public interface Iface {
        public void accept(int gNumber, Set<String> exts);
    }

    public static class Client implements Iface {

        @Override
        public void accept(int gNumber, Set<String> exts) {

        }
    }

    public static class Processor<I extends Iface> extends C4Processor<I> {

        public Processor(I interf, Map<String, C4ProcessFunction<I, ? extends C4Args>> fMap) {
            super(interf);
        }

        @Override
        protected Map<String, C4ProcessFunction<I, ? extends C4Args>> getProcessMap() {
            Map<String, C4ProcessFunction<I, ? extends C4Args>> processMap = new HashMap<>();
            processMap.put(CommandString.ACCEPT, new Accept<>());
            return processMap;
        }
    }

    public static class Accept<I extends Iface> extends C4ProcessFunction<I, AcceptArgs> {

        @Override
        public AcceptArgs getEmptyArgsInstance() {
            return null;
        }

        @Override
        protected void perform(AcceptArgs args, I iface) {



        }
    }

    public static class AcceptArgs implements C4Args {

        int groupNumber;
        Set<String> extensionList;

        @Override
        public String[] getArgArray() {
            return new String[] {Integer.toString(groupNumber), extensionList.toString()};
        }

        @Override
        public void read(String argString) throws ParameterFormatException {

            try {
                String[] args = argString.split(" ");
                groupNumber = Integer.parseInt(args[0]);
                extensionList = new HashSet<>();
                for (int i = 1; i < args.length; i++) {
                    extensionList.add(args[i]);
                }
            } catch (NumberFormatException e) {

                throw new ParameterFormatException(e.getMessage());

            }



        }
    }

// ------------------ Instance variables ----------------

// --------------------- Constructors -------------------

// ----------------------- Queries ----------------------

// ----------------------- Commands ---------------------

}
