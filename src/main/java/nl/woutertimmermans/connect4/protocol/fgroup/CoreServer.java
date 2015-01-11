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
import nl.woutertimmermans.connect4.protocol.base.C4Client;
import nl.woutertimmermans.connect4.protocol.base.C4ProcessFunction;
import nl.woutertimmermans.connect4.protocol.base.C4Processor;
import nl.woutertimmermans.connect4.protocol.constants.CommandString;
import nl.woutertimmermans.connect4.protocol.exceptions.C4Exception;
import nl.woutertimmermans.connect4.protocol.exceptions.ParameterFormatException;
import nl.woutertimmermans.connect4.protocol.exceptions.SyntaxError;
import nl.woutertimmermans.connect4.protocol.parameters.ExtensionList;
import nl.woutertimmermans.connect4.protocol.parameters.GroupNumber;
import nl.woutertimmermans.connect4.protocol.parameters.PlayerName;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CoreServer {

    /**
     * These are the commands that the client can send to the server.
     */

// ------------------ Instance variables ----------------

// --------------------- Constructors -------------------

// ----------------------- Queries ----------------------

// ----------------------- Commands ---------------------


    public interface Iface {
        public void join(String pName, int gNumber, Set<String> exts) throws C4Exception;
    }

    public static class Client extends C4Client implements Iface {

        public Client(BufferedWriter out) {
            super(out);
        }

        public void join(String pName, int gNumber, Set<String> exts) throws C4Exception {
            boolean valid = PlayerName.validName(pName);
            sendJoin(pName, gNumber, exts);

        }

        private void sendJoin(String pName, int gNumber, Set<String> exts) throws C4Exception {

            JoinArgs args = new JoinArgs(pName, gNumber, exts);

            send(CommandString.JOIN, args);

        }

    }

    public static class Processor<I extends Iface> extends C4Processor<I> {
        public Processor(I interf) {
            super(interf);
        }

        protected Map<String, C4ProcessFunction<I, ? extends C4Args>> getProcessMap() {
            Map<String, C4ProcessFunction<I, ? extends C4Args>> processMap = new HashMap<>();
            processMap.put("join", new Join<>());
            return processMap;
        }
    }

    public static class Join<I extends Iface> extends C4ProcessFunction<I, JoinArgs> {

        public Join() {
            super();
        }

        @Override
        public JoinArgs getEmptyArgsInstance() {
            return new JoinArgs();
        }

        @Override
        public void perform(JoinArgs args, I iface) throws C4Exception {

            iface.join(args.playerName.getValue(), args.groupNumber.getValue(), args.exts.getValue());


        }
    }


    public static class JoinArgs implements C4Args {

        PlayerName playerName;
        GroupNumber groupNumber;
        ExtensionList exts;

        public JoinArgs(String pName, int gNumber, Set<String> es) throws ParameterFormatException {
            playerName = new PlayerName(pName);
            groupNumber = new GroupNumber(gNumber);
            exts = new ExtensionList(es);
        }

        private JoinArgs() {

        }


        @Override
        public String[] getArgArray() {
            return new String[]{
                    playerName.serialize(),
                    groupNumber.serialize(),
                    exts.serialize()
            };
        }

        @Override
        public void read(String argString) throws C4Exception {
            String[] args = argString.split(" ", 3);
            if (args.length < 2) {
                throw new SyntaxError("Wrong amount of parameters need at least 2, you gave "
                        + args.length);
            }
            playerName = new PlayerName();
            playerName.read(args[0]);
            groupNumber = new GroupNumber();
            groupNumber.read(args[1]);
            exts = new ExtensionList();
            exts.read(args.length >= 3 ? args[2] : "");

        }
    }

}
