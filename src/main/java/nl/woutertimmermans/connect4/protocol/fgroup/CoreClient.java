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
        public void accept(int gNumber, Set<String> exts);

        public void startGame(String p1, String p2);

        public void requestMove(String player);

        public void doneMove(String player, int col);

        public void gameEnd(String player);

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
        public void startGame(String p1, String p2) {
            sendStartGame(p1, p2);
        }

        @Override
        public void requestMove(String player) {
            sendRequestMove(player);
        }

        @Override
        public void doneMove(String player, int col) {
            sendDoneMove(player, col);
        }

        @Override
        public void gameEnd(String player) {
            sendGameEnd(player);
        }

        @Override
        public void error(int eCode) {

            sendError(eCode);

        }

        private void sendAccept(int gNumber, Set<String> exts) {

            AcceptArgs args = new AcceptArgs(gNumber, exts);

            send(CommandString.ACCEPT, args);

        }

        private void sendStartGame(String p1, String p2) {

            StartGameArgs args = new StartGameArgs(p1, p2);

            send(CommandString.START_GAME, args);

        }

        private void sendRequestMove(String player) {

            RequestMoveArgs args = new RequestMoveArgs(player);

            send(CommandString.REQUEST_MOVE, args);

        }

        private void sendDoneMove(String player, int col) {
            DoneMoveArgs args = new DoneMoveArgs(player, col);
            send(CommandString.DONE_MOVE, args);
        }

        private void sendGameEnd(String player) {

            GameEndArgs args = new GameEndArgs(player);
            send(CommandString.GAME_END, args);

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
            processMap.put(CommandString.START_GAME, new StartGame<>());
            processMap.put(CommandString.REQUEST_MOVE, new RequestMove<>());
            processMap.put(CommandString.DONE_MOVE, new DoneMove<>());
            processMap.put(CommandString.GAME_END, new GameEnd<>());
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

    public static class StartGame<I extends Iface> extends C4ProcessFunction<I, StartGameArgs> {

        @Override
        public StartGameArgs getEmptyArgsInstance() {
            return new StartGameArgs();
        }

        @Override
        protected void perform(StartGameArgs args, I iface) {
            iface.startGame(args.player1.getValue(), args.player2.getValue());
        }
    }

    public static class RequestMove<I extends Iface> extends C4ProcessFunction<I, RequestMoveArgs> {

        @Override
        public RequestMoveArgs getEmptyArgsInstance() {
            return new RequestMoveArgs();
        }

        @Override
        protected void perform(RequestMoveArgs args, I iface) {
            iface.requestMove(args.player.getValue());
        }

    }

    public static class DoneMove<I extends Iface> extends C4ProcessFunction<I, DoneMoveArgs> {

        @Override
        public DoneMoveArgs getEmptyArgsInstance() {
            return new DoneMoveArgs();
        }

        @Override
        protected void perform(DoneMoveArgs args, I iface) {
            iface.doneMove(args.player.getValue(), args.column.getValue());
        }
    }

    public static class GameEnd<I extends Iface> extends C4ProcessFunction<I, GameEndArgs> {

        @Override
        public GameEndArgs getEmptyArgsInstance() {
            return new GameEndArgs();
        }

        @Override
        protected void perform(GameEndArgs args, I iface) {
            iface.gameEnd(args.winner.getValue());
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

    public static class StartGameArgs implements C4Args {

        PlayerName player1;
        PlayerName player2;

        public StartGameArgs() {
            player1 = new PlayerName();
            player2 = new PlayerName();
        }

        public StartGameArgs(String p1, String p2) {
            try {
                player1 = new PlayerName(p1);
                player2 = new PlayerName(p2);
            } catch (InvalidParameterError e) {
                e.printStackTrace();
            }

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
            String[] args = argString.split(" ", 2);
            if (args.length < 2) {
                throw new SyntaxError("Wrong amount of playernames");
            } else {
                player1.read(args[0]);
                player2.read(args[1]);
            }


        }

    }

    public static class RequestMoveArgs implements C4Args {

        PlayerName player;

        public RequestMoveArgs() {
            player = new PlayerName();
        }

        public RequestMoveArgs(String p) {
            try {
                player = new PlayerName(p);
            } catch (InvalidParameterError e) {
                e.printStackTrace();
            }
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

    }

    public static class DoneMoveArgs implements C4Args {

        PlayerName player;
        Column column;

        public DoneMoveArgs() {
            player = new PlayerName();
            column = new Column();
        }

        public DoneMoveArgs(String p, int c) {
            try {
                player = new PlayerName(p);
            } catch (InvalidParameterError e) {
                e.printStackTrace();
            }
            column = new Column(c);
        }

        public String[] getArgArray() {
            return new String[]{
                    player.serialize(),
                    column.serialize()
            };
        }

        public void read(String argString) throws C4Exception {

            String[] args = argString.split(" ");
            if (args.length < 2) {
                throw new SyntaxError("Too few parameters given");
            } else {
                player.read(args[0]);
                column.read(args[1]);
            }


        }
    }

    public static class GameEndArgs implements C4Args {

        PlayerName winner;

        public GameEndArgs() {
            winner = new PlayerName();
        }

        public GameEndArgs(String win) {
            try {
                winner = new PlayerName(win);
            } catch (InvalidParameterError e) {
                e.printStackTrace();
            }
        }

        public String[] getArgArray() {
            return new String[]{
                    winner.serialize()
            };
        }

        public void read(String argString) throws C4Exception {
            winner.read(argString);
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

}
