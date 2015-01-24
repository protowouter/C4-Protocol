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

package nl.woutertimmermans.connect4.protocol.constants;

public interface CommandString {

    // ------------ Core Client ---------------

    public static final String JOIN = "join";

    public static final String READY = "ready_for_game";

    public static final String DO_MOVE = "do_move";

    // ------------ Core Server ---------------

    public static final String ACCEPT = "accept";

    public static final String START_GAME = "start_game";

    public static final String REQUEST_MOVE = "request_move";

    public static final String DONE_MOVE = "done_move";

    public static final String GAME_END = "game_end";

    public static final String ERROR = "error";

    // ---------------- Lobby Server -------------

    public static final String STATE_CHANGE = "state_change";

    // -------------- Chat Client -----------------

    public static final String GLOBAL_CHAT = "chat_global";

    public static final String LOCAL_CHAT = "chat_local";

    // ------------- Chat Server ------------------

    public static final String MESSAGE = "message";

    // ------------- Challenge Client -------------

    public static final String ISSUE_CHALLENGE = "challenge";

    public static final String RESPOND_CHALLENGE = "challenge_response";

    // -------------- Challenge Server ------------

    public static final String NOTIFY_CHALLENGE = "challenge";

    // -------------- Security Server -------------

    public static final String STARTTLS = "STARTTLS";



}
