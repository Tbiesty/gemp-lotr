package com.gempukku.lotro.cards.set4.gandalf;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.cardtype.AbstractCompanion;
import com.gempukku.lotro.logic.effects.AddTwilightEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndPlayCardFromDiscardEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Two Towers
 * Side: Free
 * Culture: Gandalf
 * Twilight Cost: 4
 * Type: Companion • Wizard
 * Strength: 7
 * Vitality: 4
 * Resistance: 6
 * Signet: Theoden
 * Game Text: Fellowship: If the twilight pool has fewer than 2 twilight tokens, add (2) to play a character from your
 * discard pile.
 */
public class Card4_089 extends AbstractCompanion {
    public Card4_089() {
        super(4, 7, 4, 6, Culture.GANDALF, Race.WIZARD, Signet.THEODEN, "Gandalf", "Greyhame", true);
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(String playerId, LotroGame game, PhysicalCard self) {
        if (PlayConditions.canUseFPCardDuringPhase(game, Phase.FELLOWSHIP, self)
                && game.getGameState().getTwilightPool() < 2
                && PlayConditions.canPlayFromDiscard(playerId, game, Side.FREE_PEOPLE, Filters.character)) {
            ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new AddTwilightEffect(self, 2));
            action.appendEffect(
                    new ChooseAndPlayCardFromDiscardEffect(playerId, game, Side.FREE_PEOPLE, Filters.character));
            return Collections.singletonList(action);
        }
        return null;
    }
}
