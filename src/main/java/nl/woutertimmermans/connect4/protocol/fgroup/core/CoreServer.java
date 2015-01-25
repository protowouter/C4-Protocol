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

package nl.woutertimmermans.connect4.protocol.fgroup.core;

import nl.woutertimmermans.connect4.protocol.base.C4Args;
import nl.woutertimmermans.connect4.protocol.base.C4Client;
import nl.woutertimmermans.connect4.protocol.base.C4ProcessFunction;
import nl.woutertimmermans.connect4.protocol.base.C4Processor;
import nl.woutertimmermans.connect4.protocol.constants.CommandString;
import nl.woutertimmermans.connect4.protocol.exceptions.C4Exception;
import nl.woutertimmermans.connect4.protocol.exceptions.InvalidParameterError;
import nl.woutertimmermans.connect4.protocol.exceptions.SyntaxError;
import nl.woutertimmermans.connect4.protocol.parameters.*;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CoreServer {

    /**
     * These are the commands that the client can send to the server.
     */

    public interface Iface {
        public void join(String pName, int gNumber, Set<Extension> exts) throws C4Exception;

        public void ready() throws C4Exception;

        public void doMove(int col) throws C4Exception;

        public void error(int eCode, String message) throws C4Exception;


    }

    public static class Client extends C4Client implements Iface {

        public Client(BufferedWriter out) {
            super(out);
        }

        public void join(String pName, int gNumber, Set<Extension> exts) throws C4Exception {
            sendJoin(pName, gNumber, exts);

        }

        public void ready() throws C4Exception {

            sendReady();

        }

        public void doMove(int col) throws C4Exception {

            sendDoMove(col);

        }

        public void error(int eCode, String message) throws C4Exception {
            sendError(eCode, message);
        }

        private void sendJoin(String pName, int gNumber, Set<Extension> exts) throws C4Exception {

            JoinArgs args = new JoinArgs(pName, gNumber, exts);

            send(CommandString.JOIN, args);

        }

        private void sendReady() throws C4Exception {

            ReadyArgs args = new ReadyArgs();
            send(CommandString.READY, args);

        }

        private void sendDoMove(int col) throws C4Exception {

            DoMoveArgs args = new DoMoveArgs(col);

            send(CommandString.DO_MOVE, args);

        }

        private void sendError(int eCode, String message) throws C4Exception {
            ErrorArgs args = new ErrorArgs(eCode, message);
            send(CommandString.ERROR, args);
        }

    }

    public static class Processor<I extends Iface> extends C4Processor<I> {
        public Processor(I interf) {
            super(interf);
        }

        protected Map<String, C4ProcessFunction<I, ? extends C4Args>> getProcessMap() {
            Map<String, C4ProcessFunction<I, ? extends C4Args>> processMap =
                    new HashMap<String, C4ProcessFunction<I, ? extends C4Args>>();
            processMap.put(CommandString.JOIN, new Join<I>());
            processMap.put(CommandString.READY, new Ready<I>());
            processMap.put(CommandString.DO_MOVE, new DoMove<I>());
            processMap.put(CommandString.ERROR, new Error<I>());
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

            iface.join(args.playerName.getValue(),
                    args.groupNumber.getValue(), args.exts.getValue());


        }
    }

    public static class Ready<I extends Iface> extends C4ProcessFunction<I, ReadyArgs> {

        public Ready() {
            super();
        }

        @Override
        public ReadyArgs getEmptyArgsInstance() {
            return new ReadyArgs();
        }

        @Override
        public void perform(ReadyArgs args, I iface) throws C4Exception {

            iface.ready();

        }

    }

    public static class DoMove<I extends Iface> extends C4ProcessFunction<I, DoMoveArgs> {

        public DoMove() {
            super();
        }

        @Override
        public DoMoveArgs getEmptyArgsInstance() {
            return new DoMoveArgs();
        }

        @Override
        public void perform(DoMoveArgs args, I iface) throws C4Exception {
            iface.doMove(args.column.getValue());
        }

    }

    public static class Error<I extends Iface> extends C4ProcessFunction<I, ErrorArgs> {

        public Error() {
            super();
        }

        @Override
        public ErrorArgs getEmptyArgsInstance() {
            return new ErrorArgs();
        }

        @Override
        public void perform(ErrorArgs args, I iface) throws C4Exception {
            iface.error(args.errorCode.getValue(), args.message.getValue());
        }
    }


    public static class JoinArgs extends C4Args {

        private PlayerName playerName;
        private GroupNumber groupNumber;
        private ExtensionList exts;

        public JoinArgs() {
            playerName = new PlayerName();
            groupNumber = new GroupNumber();
            exts = new ExtensionList();
        }

        public JoinArgs(String pName, int gNumber, Set<Extension> es) throws InvalidParameterError {
            this();
            playerName.setValue(pName);
            groupNumber.setValue(gNumber);
            exts.setValue(es);
        }

        @Override
        public String[] getArgArray() {
            String[] result;
            if (exts.getValue().size() > 0) {
                result = new String[]{playerName.serialize(),
                        groupNumber.serialize(),
                        exts.serialize()
                };
            } else {
                result = new String[]{
                        playerName.serialize(),
                        groupNumber.serialize()};
            }
            return result;
        }

        @Override
        public void read(String argString) throws C4Exception {
            if (argString == null) {
                throw new SyntaxError("Wrong amount of parameters, I need at least 2, you gave none");
            }
            String[] args = argString.split(" ", 3);
            if (args.length < 2) {
                throw new SyntaxError("Wrong amount of parameters need at least 2, you gave "
                        + args.length);
            }
            playerName.read(args[0]);
            groupNumber.read(args[1]);
            if (args.length >= 3) {
                exts.read(args[2]);
            }
        }

        @Override
        public void validate() throws SyntaxError {
            if (playerName.getValue() == null || groupNumber.getValue() == null) {
                throw new SyntaxError("Player name and group number have to be provided");
            }
        }
    }

    public static class ReadyArgs extends C4Args {

        public ReadyArgs() {

        }

        @Override
        public String[] getArgArray() {
            return new String[0];
        }

        @Override
        public void read(String argString) throws C4Exception {
        }

        @Override
        public void validate() throws SyntaxError {

        }
    }

    public static class DoMoveArgs extends C4Args {

        private Column column;

        public DoMoveArgs() {
            column = new Column();
        }

        public DoMoveArgs(int col) throws InvalidParameterError {
            this();
            column.setValue(col);
        }

        @Override
        public String[] getArgArray() {
            return new String[]{column.serialize()};
        }

        @Override
        public void read(String argString) throws C4Exception {
            column.read(argString);
        }

        @Override
        public void validate() throws SyntaxError {
            if (column.getValue() == null) {
                throw new SyntaxError("Column has to provided");
            }
        }
    }

    public static class ErrorArgs extends C4Args {
        private ErrorCode errorCode;
        private Message message;

        public ErrorArgs() {
            errorCode = new ErrorCode();
            message = new Message();
        }

        public ErrorArgs(int eCode, String mes) throws InvalidParameterError {
            this();
            errorCode.setValue(eCode);
            message.setValue(mes);
        }

        @Override
        public String[] getArgArray() {
            return new String[]{errorCode.serialize(),
                    message.serialize()};
        }

        @Override
        public void read(String argString) throws C4Exception {
            if (argString == null) {
                throw new SyntaxError("Wrong amount of parameters, I need at least 1, you gave none");
            }
            String[] args = argString.split(" ", 2);
            errorCode.read(args[0]);
            if (args.length > 1) {
                message.read(args[1]);
            }

        }

        @Override
        public void validate() throws SyntaxError {
            if (errorCode.getValue() == null) {
                throw new SyntaxError("Error code has to be provided");
            }
        }
    }

}
