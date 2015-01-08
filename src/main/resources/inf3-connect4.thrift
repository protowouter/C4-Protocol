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
namespace java nl.woutertimmermans.connect4.protocol

struct C4Player {
    1: string name,
    2: C4Color color,
}

enum C4Color {

    BLACK = 1,
    WHITE = 2,

}

struct C4Session {

    1: i32 session_id,
    2: C4Player player,

}

exception C4InvalidMoveException {
    1: string message,
}

service C4Lobby {

    C4Session connect(1: string name),

    list<C4Player> getPlayerList(),

}

service C4GameParticipant {

    void doMove(),

    void receiveChatMessage(1: string message),


}



service C4Game {

    void makeMove(1: C4Session session, 2: i32 column) throws(1: C4InvalidMoveException err1),

    list<C4Player> getPlayers(),

    C4Player getCurrentPlayer(),




}