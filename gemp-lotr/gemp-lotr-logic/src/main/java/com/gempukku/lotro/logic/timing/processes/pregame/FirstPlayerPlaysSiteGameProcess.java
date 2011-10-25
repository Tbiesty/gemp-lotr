package com.gempukku.lotro.logic.timing.processes.pregame;

import com.gempukku.lotro.common.Zone;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.GameState;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.timing.processes.GameProcess;

import java.util.Collections;
import java.util.Map;

public class FirstPlayerPlaysSiteGameProcess implements GameProcess {
    private LotroGame _game;
    private Map<String, Integer> _bids;
    private String _firstPlayer;

    public FirstPlayerPlaysSiteGameProcess(LotroGame game, Map<String, Integer> bids, String firstPlayer) {
        _game = game;
        _bids = bids;
        _firstPlayer = firstPlayer;
    }

    @Override
    public void process() {
        GameState gameState = _game.getGameState();
        PhysicalCard firstSite = Filters.filter(gameState.getAdventureDeck(_firstPlayer), gameState, _game.getModifiersQuerying(), Filters.siteNumber(1)).iterator().next();
        gameState.removeCardsFromZone(null, Collections.singleton(firstSite));
        gameState.addCardToZone(_game, firstSite, Zone.ADVENTURE_PATH);

        for (String playerId : gameState.getPlayerOrder().getAllPlayers())
            gameState.setPlayerPosition(playerId, 1);
    }

    @Override
    public GameProcess getNextProcess() {
        return new PlayRingBearerRingAndAddBurdersGameProcess(_game, _bids, _firstPlayer);
    }
}
