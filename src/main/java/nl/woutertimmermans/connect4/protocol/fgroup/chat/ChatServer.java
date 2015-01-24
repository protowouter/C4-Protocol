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
import nl.woutertimmermans.connect4.protocol.parameters.Message;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {

    /**
     * These are the commands that the client can send to the server.
     */

    public interface Iface {
        /**
         * Used by clients to send messages to other clients in the local environment.
         *
         * @param message The message that will be sent.
         */
        public void chatLocal(String message) throws C4Exception;

        /**
         * Used by clients to send messages that will probably to clients in any environment.
         *
         * @param message The message that will be sent.
         */
        public void chatGlobal(String message) throws C4Exception;
    }

    public static class Client extends C4Client implements Iface {

        public Client(BufferedWriter out) {
            super(out);
        }

        @Override
        public void chatLocal(String message) throws C4Exception {
            ChatArgs args = new ChatArgs(message);
            send(CommandString.LOCAL_CHAT, args);
        }

        @Override
        public void chatGlobal(String message) throws C4Exception {
            ChatArgs args = new ChatArgs(message);
            send(CommandString.GLOBAL_CHAT, args);
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

            result.put(CommandString.LOCAL_CHAT, new ChatLocal<I>());
            result.put(CommandString.GLOBAL_CHAT, new ChatGlobal<I>());

            return result;
        }
    }

    public static class ChatLocal<I extends Iface> extends C4ProcessFunction<I, ChatArgs> {

        @Override
        public ChatArgs getEmptyArgsInstance() {
            return new ChatArgs();
        }

        @Override
        protected void perform(ChatArgs args, I iface) throws C4Exception {
            iface.chatLocal(args.message.getValue());
        }
    }

    public static class ChatGlobal<I extends Iface> extends C4ProcessFunction<I, ChatArgs> {

        @Override
        public ChatArgs getEmptyArgsInstance() {
            return new ChatArgs();
        }

        @Override
        protected void perform(ChatArgs args, I iface) throws C4Exception {
            iface.chatGlobal(args.message.getValue());
        }
    }

    public static class ChatArgs extends C4Args {

        private Message message;

        public ChatArgs() {
            message = new Message();
        }

        public ChatArgs(String mes) throws InvalidParameterError {
            super();
            message.setValue(mes);
        }

        /**
         * Returns an array of serialized arguments.
         *
         * @return an array with serialized arguments
         */
        @Override
        public String[] getArgArray() {
            return new String[]{message.getValue()};
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
            message.read(argString);
        }

        @Override
        public void validate() throws SyntaxError {
            // Always valid
        }
    }


}
