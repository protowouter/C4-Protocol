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

package nl.woutertimmermans.connect4.protocol.fgroup.chat;

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

public class ChatClient {

    /**
     * These are the commands that the server can send to the client.
     */

    public interface Iface {
        /**
         * Used by the server to relay messages from a client to other clients.
         *
         * @param playerName The name of the user that sent the message.
         * @param message    The message that another client has sent.
         */
        public void message(String playerName, String message) throws C4Exception;
    }

    public static class Client extends C4Client implements Iface {

        public Client(BufferedWriter out) {
            super(out);
        }

        public void message(String playerName, String message) throws C4Exception {
            MessageArgs args = new MessageArgs(playerName, message);
            send(CommandString.MESSAGE, args);
        }
    }

    public static class Processor<I extends Iface> extends C4Processor<I> {

        public Processor(I interf) {
            super(interf);
        }


        @Override
        protected Map<String, C4ProcessFunction<I, ? extends C4Args>> getProcessMap() {
            Map<String, C4ProcessFunction<I, ? extends C4Args>> result =
                    new HashMap<String, C4ProcessFunction<I, ? extends C4Args>>();

            result.put(CommandString.MESSAGE, new Message<I>());
            return result;
        }
    }

    public static class Message<I extends Iface> extends C4ProcessFunction<I, MessageArgs> {

        @Override
        public MessageArgs getEmptyArgsInstance() {
            return new MessageArgs();
        }

        @Override
        protected void perform(MessageArgs args, I iface) throws C4Exception {
            iface.message(args.playerName.getValue(), args.message.getValue());
        }
    }

    public static class MessageArgs extends C4Args {

        private PlayerName playerName;
        private nl.woutertimmermans.connect4.protocol.parameters.Message message;

        public MessageArgs() {
            playerName = new PlayerName();
            message = new nl.woutertimmermans.connect4.protocol.parameters.Message();
        }

        public MessageArgs(String pName, String mes) throws InvalidParameterError {
            this();
            playerName.setValue(pName);
            message.setValue(mes);
        }

        /**
         * Returns an array of serialized arguments.
         *
         * @return an array with serialized arguments
         */
        @Override
        public String[] getArgArray() {
            return new String[]{playerName.serialize(), message.serialize()};
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
                throw new SyntaxError("You need to at least provide the player name");
            }
            String[] args = argString.split(" ", 2);
            playerName.read(args[0]);
            if (args.length == 2) {
                message.read(args[1]);
            }

        }

        @Override
        public void validate() throws SyntaxError {
            if (playerName.getValue() == null) {
                throw new SyntaxError("You need to at least provide the player name");
            }
        }
    }


}
