package com.gempukku.lotro.cards.set3.isengard;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.cardtype.AbstractPermanent;
import com.gempukku.lotro.logic.effects.choose.ChooseAndExertCharactersEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndPlayCardFromDiscardEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: Realms of Elf-lords
 * Side: Shadow
 * Culture: Isengard
 * Twilight Cost: 1
 * Type: Condition
 * Game Text: Plays to your support area. Shadow: Exert Saruman to play an [ISENGARD] weather condition from your
 * discard pile. Its twilight cost is -2.
 */
public class Card3_052 extends AbstractPermanent {
    public Card3_052() {
        super(Side.SHADOW, 1, CardType.CONDITION, Culture.ISENGARD, "A Fell Voice on the Air");
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(String playerId, LotroGame game, PhysicalCard self) {
        if (PlayConditions.canUseShadowCardDuringPhase(game, Phase.SHADOW, self, 0)
                && PlayConditions.canExert(self, game, Filters.saruman)
                && PlayConditions.canPlayFromDiscard(playerId, game, -2, Culture.ISENGARD, Keyword.WEATHER, CardType.CONDITION)) {
            ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new ChooseAndExertCharactersEffect(action, playerId, 1, 1, Filters.saruman));
            action.appendEffect(
                    new ChooseAndPlayCardFromDiscardEffect(playerId, game, -2, Filters.and(Culture.ISENGARD, Keyword.WEATHER, CardType.CONDITION)));
            return Collections.singletonList(action);
        }
        return null;
    }
}
