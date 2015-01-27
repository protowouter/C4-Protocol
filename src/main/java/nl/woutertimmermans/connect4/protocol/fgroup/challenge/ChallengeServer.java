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
import nl.woutertimmermans.connect4.protocol.parameters.BooleanAnswer;
import nl.woutertimmermans.connect4.protocol.parameters.PlayerName;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;

public class ChallengeServer {

    /**
     * These are the commands that the client can send to the server.
     */

    public interface Iface {
        public void issueChallenge(String playerName) throws C4Exception;

        public void respondChallenge(String playerName, boolean answer) throws C4Exception;
    }

    public static class Client extends C4Client implements Iface {

        public Client(BufferedWriter out) {
            super(out);
        }

        @Override
        public void issueChallenge(String playerName) throws C4Exception {
            IssueChallengeArgs args = new IssueChallengeArgs(playerName);
            send(CommandString.ISSUE_CHALLENGE, args);
        }

        @Override
        public void respondChallenge(String playerName, boolean answer) throws C4Exception {
            RespondChallengeArgs args = new RespondChallengeArgs(playerName, answer);
            send(CommandString.RESPOND_CHALLENGE, args);
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
            result.put(CommandString.ISSUE_CHALLENGE, new IssueChallenge<I>());
            result.put(CommandString.RESPOND_CHALLENGE, new RespondChallenge<I>());

            return result;
        }
    }


    public static class IssueChallenge<I extends Iface> extends C4ProcessFunction<I, IssueChallengeArgs> {

        @Override
        public IssueChallengeArgs getEmptyArgsInstance() {
            return new IssueChallengeArgs();
        }

        @Override
        protected void perform(IssueChallengeArgs args, I iface) throws C4Exception {
            iface.issueChallenge(args.playerName.getValue());
        }
    }

    public static class RespondChallenge<I extends Iface> extends C4ProcessFunction<I, RespondChallengeArgs> {

        @Override
        public RespondChallengeArgs getEmptyArgsInstance() {
            return new RespondChallengeArgs();
        }

        @Override
        protected void perform(RespondChallengeArgs args, I iface) throws C4Exception {
            iface.respondChallenge(args.playerName.getValue(), args.booleanAnswer.getValue());
        }
    }


    public static class RespondChallengeArgs extends C4Args {

        private PlayerName playerName;
        private BooleanAnswer booleanAnswer;

        private RespondChallengeArgs() {
            playerName = new PlayerName();
            booleanAnswer = new BooleanAnswer();
        }

        private RespondChallengeArgs(String pName, boolean answer) throws InvalidParameterError {
            this();
            playerName.setValue(pName);
            booleanAnswer.setValue(answer);
        }

        /**
         * Returns an array of serialized arguments.
         *
         * @return an array with serialized arguments
         */
        @Override
        public String[] getArgArray() {
            return new String[]{playerName.serialize(), booleanAnswer.serialize()};
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
            if (argString == null) {
                throw new SyntaxError("You need to provide 2 arguments to this command");
            } else {
                String[] args = argString.split(" ");
                if (args.length == 2) {
                    playerName.read(args[0]);
                    booleanAnswer.read(args[1]);
                } else {
                    throw new SyntaxError("You need to provide 2 commands, you gave " + args.length);
                }
            }
        }

        @Override
        public void validate() throws SyntaxError {
            if (playerName.getValue() == null || booleanAnswer.getValue() == null) {
                throw new SyntaxError("You need to provide both player name and answer");
            }
        }
    }


    public static class IssueChallengeArgs extends C4Args {

        private PlayerName playerName;

        public IssueChallengeArgs() {
            playerName = new PlayerName();
        }

        public IssueChallengeArgs(String pName) throws InvalidParameterError {
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
            if (playerName == null) {
                throw new SyntaxError("Player name needs to be provided");
            }
        }
    }

}
