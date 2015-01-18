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
import nl.woutertimmermans.connect4.protocol.exceptions.InvalidParameterError;
import nl.woutertimmermans.connect4.protocol.exceptions.SyntaxError;
import nl.woutertimmermans.connect4.protocol.parameters.*;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CoreClient {

    /**
     * These are the commands that the server can send to the client.
     */

    public interface Iface {
        public void accept(int gNumber, Set<Extension> exts) throws C4Exception;

        public void startGame(String p1, String p2) throws C4Exception;

        public void requestMove(String player) throws C4Exception;

        public void doneMove(String player, int col) throws C4Exception;

        public void gameEnd(String player) throws C4Exception;

        public void error(int eCode, String message) throws C4Exception;
    }

    public static class Client extends C4Client implements Iface {

        public Client(BufferedWriter out) {
            super(out);
        }

        public void accept(int gNumber, Set<Extension> exts) throws C4Exception {

            sendAccept(gNumber, exts);

        }

        public void startGame(String p1, String p2) throws C4Exception {
            sendStartGame(p1, p2);
        }

        public void requestMove(String player) throws C4Exception {
            sendRequestMove(player);
        }

        public void doneMove(String player, int col) throws C4Exception {
            sendDoneMove(player, col);
        }

        public void gameEnd(String player) throws C4Exception {
            sendGameEnd(player);
        }

        public void error(int eCode, String message) throws C4Exception {

            sendError(eCode, message);

        }

        private void sendAccept(int gNumber, Set<Extension> exts) throws C4Exception {

            AcceptArgs args = new AcceptArgs(gNumber, exts);

            send(CommandString.ACCEPT, args);

        }

        private void sendStartGame(String p1, String p2) throws C4Exception {

            StartGameArgs args = new StartGameArgs(p1, p2);

            send(CommandString.START_GAME, args);

        }

        private void sendRequestMove(String player) throws C4Exception {

            RequestMoveArgs args = new RequestMoveArgs(player);

            send(CommandString.REQUEST_MOVE, args);

        }

        private void sendDoneMove(String player, int col) throws C4Exception {
            DoneMoveArgs args = new DoneMoveArgs(player, col);
            send(CommandString.DONE_MOVE, args);
        }

        private void sendGameEnd(String player) throws C4Exception {

            GameEndArgs args = new GameEndArgs(player);
            send(CommandString.GAME_END, args);

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

        @Override
        protected Map<String, C4ProcessFunction<I, ? extends C4Args>> getProcessMap() {
            Map<String, C4ProcessFunction<I, ? extends C4Args>> processMap =
                    new HashMap<String, C4ProcessFunction<I, ? extends C4Args>>();
            processMap.put(CommandString.ACCEPT, new Accept<I>());
            processMap.put(CommandString.START_GAME, new StartGame<I>());
            processMap.put(CommandString.REQUEST_MOVE, new RequestMove<I>());
            processMap.put(CommandString.DONE_MOVE, new DoneMove<I>());
            processMap.put(CommandString.GAME_END, new GameEnd<I>());
            processMap.put(CommandString.ERROR, new Error<I>());
            return processMap;
        }
    }

    public static class Accept<I extends Iface> extends C4ProcessFunction<I, AcceptArgs> {

        @Override
        public AcceptArgs getEmptyArgsInstance() {
            return new AcceptArgs();
        }

        @Override
        protected void perform(AcceptArgs args, I iface) throws C4Exception {

            iface.accept(args.groupNumber.getValue(), args.extensionList.getValue());

        }
    }

    public static class StartGame<I extends Iface> extends C4ProcessFunction<I, StartGameArgs> {

        @Override
        public StartGameArgs getEmptyArgsInstance() {
            return new StartGameArgs();
        }

        @Override
        protected void perform(StartGameArgs args, I iface) throws C4Exception {
            iface.startGame(args.player1.getValue(), args.player2.getValue());
        }
    }

    public static class RequestMove<I extends Iface> extends C4ProcessFunction<I, RequestMoveArgs> {

        @Override
        public RequestMoveArgs getEmptyArgsInstance() {
            return new RequestMoveArgs();
        }

        @Override
        protected void perform(RequestMoveArgs args, I iface) throws C4Exception {
            iface.requestMove(args.player.getValue());
        }

    }

    public static class DoneMove<I extends Iface> extends C4ProcessFunction<I, DoneMoveArgs> {

        @Override
        public DoneMoveArgs getEmptyArgsInstance() {
            return new DoneMoveArgs();
        }

        @Override
        protected void perform(DoneMoveArgs args, I iface) throws C4Exception {
            iface.doneMove(args.player.getValue(), args.column.getValue());
        }
    }

    public static class GameEnd<I extends Iface> extends C4ProcessFunction<I, GameEndArgs> {

        @Override
        public GameEndArgs getEmptyArgsInstance() {
            return new GameEndArgs();
        }

        @Override
        protected void perform(GameEndArgs args, I iface) throws C4Exception {
            iface.gameEnd(args.winner.getValue());
        }
    }

    public static class Error<I extends Iface> extends C4ProcessFunction<I, ErrorArgs> {

        @Override
        public ErrorArgs getEmptyArgsInstance() {
            return new ErrorArgs();
        }

        @Override
        protected void perform(ErrorArgs args, I iface) throws C4Exception {
            iface.error(args.errorCode.getValue(), args.message.getValue());
        }

    }

    public static class AcceptArgs extends C4Args {

        private GroupNumber groupNumber;
        private ExtensionList extensionList;

        public AcceptArgs() {

            groupNumber = new GroupNumber();
            extensionList = new ExtensionList();

        }

        public AcceptArgs(int gNumber, Set<Extension> exts) throws InvalidParameterError {
            this();
            groupNumber.setValue(gNumber);
            extensionList.setValue(exts);
        }

        @Override
        public String[] getArgArray() {
            String[] result;
            if (extensionList.getValue().size() > 1) {
                result = new String[]{groupNumber.serialize(), extensionList.serialize()};
            } else {
                result = new String[]{groupNumber.serialize()};
            }
            return result;
        }

        @Override
        public void read(String argString) throws C4Exception {
            String[] args = argString.split(" ", 2);
            groupNumber.read(args[0]);
            if (args.length > 1) {
                extensionList.read(args[1]);
            }
        }

        @Override
        public void validate() throws SyntaxError {
            if (groupNumber.getValue() == null) {
                throw new SyntaxError("Groupnumber not provided");
            }
        }
    }

    public static class StartGameArgs extends C4Args {

        private PlayerName player1;
        private PlayerName player2;

        public StartGameArgs() {
            player1 = new PlayerName();
            player2 = new PlayerName();
        }

        public StartGameArgs(String p1, String p2) throws InvalidParameterError {
            this();
            player1.setValue(p1);
            player2.setValue(p2);
        }

        @Override
        public String[] getArgArray() {
            return new String[]{
                    player1.serialize(),
                    player2.serialize()
            };
        }

        @Override
        public void read(String argString) throws C4Exception {
            String[] args = argString.split(" ");
            if (args.length != 2) {
                throw new SyntaxError("Wrong amount of playernames");
            } else {
                player1.read(args[0]);
                player2.read(args[1]);
            }


        }

        @Override
        public void validate() throws SyntaxError {
            if (player1.getValue() == null || player2.getValue() == null) {
                throw new SyntaxError("Less than two player names provided");
            }
        }

    }

    public static class RequestMoveArgs extends C4Args {

        private PlayerName player;

        public RequestMoveArgs() {
            player = new PlayerName();
        }

        public RequestMoveArgs(String p) throws InvalidParameterError {
            this();
            player.setValue(p);
        }

        @Override
        public String[] getArgArray() {
            return new String[]{
                    player.serialize()
            };
        }

        @Override
        public void read(String argString) throws C4Exception {
            player.read(argString);
        }

        @Override
        public void validate() throws SyntaxError {
            if (player.getValue() == null) {
                throw new SyntaxError("Playername has to provided");
            }
        }

    }

    public static class DoneMoveArgs extends C4Args {

        private PlayerName player;
        private Column column;

        public DoneMoveArgs() {
            player = new PlayerName();
            column = new Column();
        }

        public DoneMoveArgs(String pName, int col) throws InvalidParameterError {
            this();
            player.setValue(pName);
            column.setValue(col);
        }

        public String[] getArgArray() {
            return new String[]{
                    player.serialize(),
                    column.serialize()
            };
        }

        public void read(String argString) throws C4Exception {
            String[] args = argString.split(" ");
            if (args.length != 2) {
                throw new SyntaxError("Wrong amount of parameters given." +
                        " Required: 2 Received: " + args.length);
            } else {
                player.read(args[0]);
                column.read(args[1]);
            }
        }

        public void validate() throws SyntaxError {
            if (player.getValue() == null || column.getValue() == null) {
                throw new SyntaxError("Both player name and column have to be provided");
            }
        }
    }

    public static class GameEndArgs extends C4Args {

        private PlayerName winner;

        public GameEndArgs() {
            winner = new PlayerName();
        }

        public GameEndArgs(String win) throws InvalidParameterError {
            this();
            winner.setValue(win);
        }

        public String[] getArgArray() {
            return new String[]{
                    winner.serialize()
            };
        }

        public void read(String argString) throws C4Exception {
            winner.read(argString);
        }

        public void validate() throws SyntaxError {

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
            String[] args = argString.split(" ", 2);
            errorCode.read(args[0]);
            if (args.length > 1) {
                errorCode.read(args[1]);
            }
        }

        @Override
        public void validate() throws SyntaxError {
            if (errorCode.getValue() == null) {
                throw new SyntaxError("Errorcode has to be provided");
            }
        }
    }

}
