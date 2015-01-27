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

package nl.woutertimmermans.connect4.protocol.fgroup.challenge;

import nl.woutertimmermans.connect4.protocol.base.C4Args;
import nl.woutertimmermans.connect4.protocol.base.C4Client;
import nl.woutertimmermans.connect4.protocol.base.C4ProcessFunction;
import nl.woutertimmermans.connect4.protocol.base.C4Processor;
import nl.woutertimmermans.connect4.protocol.constants.CommandString;
import nl.woutertimmermans.connect4.protocol.exceptions.C4Exception;
import nl.woutertimmermans.connect4.protocol.exceptions.InvalidParameterError;
import nl.woutertimmermans.connect4.protocol.exceptions.SyntaxError;
import nl.woutertimmermans.connect4.protocol.parameters.PlayerName;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;

public class ChallengeClient {

    /**
     * These are the commands that the server can send to the client.
     */

    public static interface Iface {
        public void notifyChallenge(String playerName) throws C4Exception;
    }

    public static class Client extends C4Client implements Iface {

        public Client(BufferedWriter out) {
            super(out);
        }

        public void notifyChallenge(String playerName) throws C4Exception {
            NotifyChallengeArgs args = new NotifyChallengeArgs(playerName);
            send(CommandString.NOTIFY_CHALLENGE, args);
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
            result.put(CommandString.NOTIFY_CHALLENGE, new NotifyChallenge<I>());
            return result;
        }
    }


    public static class NotifyChallenge<I extends Iface> extends C4ProcessFunction<I, NotifyChallengeArgs> {

        @Override
        public NotifyChallengeArgs getEmptyArgsInstance() {
            return new NotifyChallengeArgs();
        }

        @Override
        protected void perform(NotifyChallengeArgs args, I iface) throws C4Exception {
            iface.notifyChallenge(args.playerName.getValue());
        }
    }


    public static class NotifyChallengeArgs extends C4Args {

        private PlayerName playerName;

        public NotifyChallengeArgs() {
            playerName = new PlayerName();
        }

        public NotifyChallengeArgs(String pName) throws InvalidParameterError {
            this();
            playerName.setValue(pName);
        }

        /**
         * Returns an array of serialized arguments.
         *
         * @return an array with serialized arguments
         */
        @Override
        public String[] getArgArray() {
            return new String[]{playerName.serialize()};
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
            playerName.read(argString);
        }

        @Override
        public void validate() throws SyntaxError {
            if (playerName.getValue() == null) {
                throw new SyntaxError("You need to supply the player name");
            }
        }
    }
}
