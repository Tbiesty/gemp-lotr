package com.gempukku.lotro.cards.set40.moria;

import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.cardtype.AbstractMinion;
import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;

/**
 * Title: Goblin Parol
 * Set: Second Edition
 * Side: Shadow
 * Culture: Moria
 * Twilight Cost: 5
 * Type: Minion - Goblin
 * Strength: 11
 * Vitality: 3
 * Home: 4
 * Card Number: 1C164
 * Game Text: This minion's twilight cost is -1 for each Goblin stacked on a [MORIA] condition.
 */
public class Card40_164 extends AbstractMinion {
    public Card40_164() {
        super(5, 11, 3, 4, Race.GOBLIN, Culture.MORIA, "Goblin Patrol");
    }

    @Override
    public int getTwilightCostModifier(LotroGame game, PhysicalCard self) {
        int count = 0;
        for (PhysicalCard physicalCard : Filters.filterActive(game, Culture.MORIA, CardType.CONDITION)) {
            count += Filters.filter(game.getGameState().getStackedCards(physicalCard), game, Race.GOBLIN).size();
        }

        return -count;
    }
}
