package pokker.client.game;

import pokker.lib.game.player.Player;

public class PlayerOther extends Player {
    PlayerOther(String name) {
        super(name);
    }

    @Override
    public int act(int largestBet) {
        return 0;
    }
}
