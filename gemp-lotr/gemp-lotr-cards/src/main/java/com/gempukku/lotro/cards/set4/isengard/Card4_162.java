package com.gempukku.lotro.cards.set4.isengard;

import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.DiscardCardsFromHandEffect;
import com.gempukku.lotro.logic.effects.PutCardFromDiscardIntoHandEffect;
import com.gempukku.lotro.logic.effects.RevealHandEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseCardsFromDiscardEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collection;

/**
 * Set: The Two Towers
 * Side: Shadow
 * Culture: Isengard
 * Twilight Cost: 2
 * Type: Event
 * Game Text: Regroup: Spot Saruman or an [ISENGARD] Man, reveal your hand, and discard all Free Peoples cards revealed
 * to take an [ISENGARD] card into hand from your discard pile.
 */
public class Card4_162 extends AbstractEvent {
    public Card4_162() {
        super(Side.SHADOW, 2, Culture.ISENGARD, "New Power Rising", Phase.REGROUP);
    }

    @Override
    public boolean checkPlayRequirements(LotroGame game, PhysicalCard self) {
        return PlayConditions.canSpot(game, Filters.or(Filters.saruman, Filters.and(Culture.ISENGARD, Race.MAN)));
    }

    @Override
    public PlayEventAction getPlayEventCardAction(final String playerId, final LotroGame game, final PhysicalCard self) {
        final PlayEventAction action = new PlayEventAction(self);
        action.appendCost(
                new RevealHandEffect(self, playerId, playerId) {
                    @Override
                    protected void cardsRevealed(Collection<? extends PhysicalCard> cards) {
                        action.appendCost(
                                new DiscardCardsFromHandEffect(self, playerId, Filters.filter(cards, game, Side.FREE_PEOPLE), false));
                    }
                });
        action.appendEffect(
                new ChooseCardsFromDiscardEffect(playerId, 1, 1, Culture.ISENGARD) {
                    @Override
                    protected void cardsSelected(LotroGame game, Collection<PhysicalCard> cards) {
                        for (PhysicalCard card : cards) {
                            action.appendEffect(
                                    new PutCardFromDiscardIntoHandEffect(card));
                        }
                    }
                });
        return action;
    }
}
