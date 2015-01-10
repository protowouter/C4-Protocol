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

package nl.woutertimmermans.connect4.protocol.functionality;

import nl.woutertimmermans.connect4.protocol.CommandStrings;
import nl.woutertimmermans.connect4.protocol.parameters.ParameterFormatException;
import nl.woutertimmermans.connect4.protocol.parameters.PlayerName;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class CoreServer {

    /**
     * These are the commands that the client can send to the server.
     */

// ------------------ Instance variables ----------------

// --------------------- Constructors -------------------

// ----------------------- Queries ----------------------

// ----------------------- Commands ---------------------


    public interface Iface {
        public void join(String pName, int gNumber, Set<String> exts)
                throws ParameterFormatException;
    }

    public static class Client extends C4Client implements Iface {

        public Client(BufferedWriter out) {
            super(out);
        }

        public void join(String pName, int gNumber, Set<String> exts)
                throws ParameterFormatException {
            boolean valid = PlayerName.validName(pName);

        }

        private void sendJoin(String pName, int gNumber, Set<String> exts) {

            JoinArgs args = new JoinArgs(pName, gNumber, exts);

            send(CommandStrings.JOIN, args);

        }

    }

    public static class Processor<I extends Iface> extends C4Processor<I> {
        public Processor(I interf) {
            super(interf, getProcessMap());
        }

        private static <I extends Iface> Map<String, C4ProcessFunction<I, ? extends C4Args>> getProcessMap() {
            Map<String, C4ProcessFunction<I, ? extends C4Args>> processMap = new HashMap<>();
            processMap.put("join", new Join<>());
            return processMap;
        }
    }

    public static class Join<I extends Iface> extends C4ProcessFunction<I, JoinArgs> {

        public Join() {
            super("join");
        }

        @Override
        public JoinArgs getEmptyArgsInstance() {
            return new JoinArgs();
        }

        @Override
        public void perform(JoinArgs args, I iface) {

            try {
                iface.join(args.playerName, args.groupNumber, args.exts);
            } catch (ParameterFormatException e) {
                Logger.getGlobal().throwing("Join", "perform", e);
            }

        }
    }


    public static class JoinArgs implements C4Args {

        String playerName;
        int groupNumber;
        Set<String> exts;

        public JoinArgs(String pName, int gNumber, Set<String> es) {
            playerName = pName;
            groupNumber = gNumber;
            exts = es;
        }

        public JoinArgs() {

        }


        @Override
        public String[] getArgArray() {
            String[] result = new String[3];
            result[0] = playerName;
            result[1] = Integer.toString(groupNumber);
            result[2] = exts.toString();
            return result;
        }

        @Override
        public void read(String argString) {

        }
    }

}
