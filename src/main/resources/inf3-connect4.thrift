namespace java nl.woutertimmermans.inf3.c4prot

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