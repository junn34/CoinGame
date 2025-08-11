package coingame.player;

import java.util.Map;

public class NPCSetup {
    // NPC 3명 객체 생성
    public static NPCPlayer npc1 = new NPCPlayer(
        "이상혁",
        3000000
        //Map.of("buy", 50, "sell", 30, "wait", 20) // 매수 위주
    );

    public static NPCPlayer npc2 = new NPCPlayer(
        "배준식",
        500000
      //  Map.of("buy", 30, "sell", 50, "wait", 20) // 매도 위주
    );

    public static NPCPlayer npc3 = new NPCPlayer(
        "신금재",
        200000
       // Map.of("buy", 40, "sell", 20, "wait", 40) // 대기 자주 함
    );
}

