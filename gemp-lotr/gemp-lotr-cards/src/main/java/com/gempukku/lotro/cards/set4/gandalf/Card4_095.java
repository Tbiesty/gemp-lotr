package com.gempukku.lotro.cards.set4.gandalf;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Keyword;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractResponseEvent;
import com.gempukku.lotro.logic.effects.PutPlayedEventIntoHandEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndExertCharactersEffect;
import com.gempukku.lotro.logic.timing.EffectResult;
import com.gempukku.lotro.logic.timing.PlayConditions;
import com.gempukku.lotro.logic.timing.TriggerConditions;
import com.gempukku.lotro.logic.timing.results.PlayCardResult;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Two Towers
 * Side: Free
 * Culture: Gandalf
 * Twilight Cost: 3
 * Type: Event
 * Game Text: Spell.
 * Response: If you play a [GANDALF] event, exert Gandalf twice to place that event in your hand instead of your
 * discard pile.
 */
public class Card4_095 extends AbstractResponseEvent {
    public Card4_095() {
        super(Side.FREE_PEOPLE, 3, Culture.GANDALF, "Into Dark Tunnels");
        addKeyword(Keyword.SPELL);
    }

    @Override
    public List<PlayEventAction> getPlayResponseEventAfterActions(String playerId, LotroGame game, final EffectResult effectResult, PhysicalCard self) {
        if (TriggerConditions.played(game, effectResult, Culture.GANDALF, CardType.EVENT)
                && PlayConditions.canExert(self, game, 2, Filters.gandalf)) {
            PlayEventAction action = new PlayEventAction(self);
            action.appendCost(
                    new ChooseAndExertCharactersEffect(action, playerId, 1, 1, 2, Filters.gandalf));
            PhysicalCard playedCard = ((PlayCardResult) effectResult).getPlayedCard();
            action.appendEffect(
                    new PutPlayedEventIntoHandEffect(playedCard));
            return Collections.singletonList(action);
        }
        return null;
    }
}
