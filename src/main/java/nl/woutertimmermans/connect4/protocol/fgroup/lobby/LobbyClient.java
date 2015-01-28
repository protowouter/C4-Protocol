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

package nl.woutertimmermans.connect4.protocol.fgroup.lobby;

import nl.woutertimmermans.connect4.protocol.base.*;
import nl.woutertimmermans.connect4.protocol.constants.CommandString;
import nl.woutertimmermans.connect4.protocol.exceptions.C4Exception;
import nl.woutertimmermans.connect4.protocol.exceptions.InvalidParameterError;
import nl.woutertimmermans.connect4.protocol.exceptions.SyntaxError;
import nl.woutertimmermans.connect4.protocol.parameters.LobbyState;
import nl.woutertimmermans.connect4.protocol.parameters.PlayerName;

import java.io.BufferedWriter;
import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Map;

public class LobbyClient {

    public static interface Iface {
        public void stateChange(String playerName, LobbyState lobbyState) throws C4Exception;
    }

    public static class Client extends C4Client implements Iface {

        public Client(BufferedWriter out) {
            super(out);
        }

        @Override
        public void stateChange(String playerName, LobbyState lobbyState) throws C4Exception {
            StateChangeArgs args = new StateChangeArgs(playerName, lobbyState);
            send(CommandString.STATE_CHANGE, args);
        }
    }

    public static class AsyncClient extends AsyncC4Client implements Iface {

        public AsyncClient(SelectionKey key) {
            super(key);
        }

        @Override
        public void stateChange(String playerName, LobbyState lobbyState) throws C4Exception {
            StateChangeArgs args = new StateChangeArgs(playerName, lobbyState);
            send(CommandString.STATE_CHANGE, args);
        }
    }


    public static class Processor<I extends Iface> extends C4Processor<I> {

        public Processor(I iface) {
            super(iface);
        }

        @Override
        protected Map<String, C4ProcessFunction<I, ? extends C4Args>> getProcessMap() {
            Map<String, C4ProcessFunction<I, ? extends C4Args>> result =
                    new HashMap<String, C4ProcessFunction<I, ? extends C4Args>>();
            result.put(CommandString.STATE_CHANGE, new StateChange<I>());
            return result;
        }
    }

    public static class StateChange<I extends Iface> extends C4ProcessFunction<I, StateChangeArgs> {

        @Override
        public StateChangeArgs getEmptyArgsInstance() {
            return new StateChangeArgs();
        }

        @Override
        protected void perform(StateChangeArgs args, I iface) throws C4Exception {
            iface.stateChange(args.playerName.getValue(), args.lobbyState);
        }
    }

    public static class StateChangeArgs extends C4Args {

        private PlayerName playerName;
        private LobbyState lobbyState;

        public StateChangeArgs() {
            playerName = new PlayerName();
        }

        public StateChangeArgs(String pName, LobbyState lState) throws InvalidParameterError {
            this();
            playerName.setValue(pName);
            lobbyState = lState;
        }

        /**
         * Returns an array of serialized arguments.
         *
         * @return an array with serialized arguments
         */
        @Override
        public String[] getArgArray() {
            return new String[]{
                    playerName.serialize(),
                    lobbyState.toString()
            };
        }

        /**
         * Reads a String representation of the protocol and initializes the value in this
         * Argument set.
         *
         * @param argString String representation to be read into this Argument set.
         * @throws nl.woutertimmermans.connect4.protocol.exceptions.C4Exception if these string cannot be parsed to a valid argument collection
         *                                                                      according to the protocol.
         */
        @Override
        public void read(String argString) throws C4Exception {
            if (argString != null) {
                String[] args = argString.split(" ", 2);
                if (args.length != 2) {
                    throw new SyntaxError("Wrong amount of arguments, I need 2 you gave " + args.length);
                } else {
                    playerName.read(args[0]);
                    lobbyState = LobbyState.parseString(args[1]);
                }
            } else {
                throw new SyntaxError("You need to give two parameters, you gave zero");
            }

        }

        @Override
        public void validate() throws SyntaxError {
            if (playerName == null || lobbyState == null) {
                throw new SyntaxError("You need to provide both a username and a lobby state");
            }
        }
    }

}
