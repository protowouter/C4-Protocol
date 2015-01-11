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
import nl.woutertimmermans.connect4.protocol.parameters.ErrorCode;
import nl.woutertimmermans.connect4.protocol.parameters.ExtensionList;
import nl.woutertimmermans.connect4.protocol.parameters.GroupNumber;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CoreClient {

    /**
     * These are the commands that the server can send to the client.
     */

    public interface Iface {
        public void accept(int gNumber, Set<String> exts);

        public void error(int eCode);
    }

    public static class Client extends C4Client implements Iface {

        public Client(BufferedWriter out) {
            super(out);
        }

        @Override
        public void accept(int gNumber, Set<String> exts) {

            sendAccept(gNumber, exts);

        }

        @Override
        public void error(int eCode) {

            sendError(eCode);

        }

        private void sendAccept(int gNumber, Set<String> exts) {

            AcceptArgs args = new AcceptArgs(gNumber, exts);

            send(CommandString.ACCEPT, args);

        }

        private void sendError(int eCode) {

            ErrorArgs args = new ErrorArgs(eCode);

            send(CommandString.ERROR, args);

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
            processMap.put(CommandString.ERROR, new Error<>());
            return processMap;
        }
    }

    public static class Accept<I extends Iface> extends C4ProcessFunction<I, AcceptArgs> {

        @Override
        public AcceptArgs getEmptyArgsInstance() {
            return new AcceptArgs();
        }

        @Override
        protected void perform(AcceptArgs args, I iface) {

            iface.accept(args.groupNumber.getValue(), args.extensionList.getValue());

        }
    }

    public static class Error<I extends Iface> extends C4ProcessFunction<I, ErrorArgs> {

        @Override
        public ErrorArgs getEmptyArgsInstance() {
            return new ErrorArgs();
        }

        @Override
        protected void perform(ErrorArgs args, I iface) {
            iface.error(args.errorCode.getValue());
        }

    }

    public static class AcceptArgs implements C4Args {

        GroupNumber groupNumber;
        ExtensionList extensionList;

        public AcceptArgs() {

        }

        public AcceptArgs(int gNumber, Set<String> exts) {
            groupNumber = new GroupNumber(gNumber);
            extensionList = new ExtensionList(exts);
        }

        @Override
        public String[] getArgArray() {
            return new String[]{groupNumber.serialize(), extensionList.serialize()};
        }

        @Override
        public void read(String argString) throws C4Exception {

            String[] args = argString.split(" ", 2);
            groupNumber.read(args[0]);
            if (args.length > 1) {
                extensionList.read(args[1]);
            }



        }
    }

    public static class ErrorArgs implements C4Args {
        ErrorCode errorCode;

        public ErrorArgs() {

        }

        public ErrorArgs(int eCode) {
            errorCode = new ErrorCode(eCode);
        }

        @Override
        public String[] getArgArray() {
            return new String[]{errorCode.serialize()};
        }

        @Override
        public void read(String argString) throws C4Exception {
            errorCode = new ErrorCode();
            errorCode.read(argString);
        }
    }

// ------------------ Instance variables ----------------

// --------------------- Constructors -------------------

// ----------------------- Queries ----------------------

// ----------------------- Commands ---------------------

}
