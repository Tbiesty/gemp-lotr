package com.gempukku.lotro.cards.set17.gandalf;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.ChooseArbitraryCardsEffect;
import com.gempukku.lotro.logic.effects.DiscardCardsFromHandEffect;
import com.gempukku.lotro.logic.effects.PutCardFromDiscardIntoHandEffect;
import com.gempukku.lotro.logic.effects.RevealAndChooseCardsFromOpponentHandEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseOpponentEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Set: Rise of Saruman
 * Side: Free
 * Culture: Gandalf
 * Twilight Cost: 1
 * Type: Event • Fellowship
 * Game Text: Spell. Spot a [GANDALF] Wizard to search a Shadow player's discard pile and choose a minion, then return
 * that minion to its owner's hand. If you do, you may reveal that Shadow player’s hand and discard a minion found there.
 */
public class Card17_015 extends AbstractEvent {
    public Card17_015() {
        super(Side.FREE_PEOPLE, 1, Culture.GANDALF, "A New Light", Phase.FELLOWSHIP);
        addKeyword(Keyword.SPELL);
    }

    @Override
    public boolean checkPlayRequirements(LotroGame game, PhysicalCard self) {
        return PlayConditions.canSpot(game, Culture.GANDALF, Race.WIZARD);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(final String playerId, final LotroGame game, final PhysicalCard self) {
        final PlayEventAction action = new PlayEventAction(self);
        action.appendEffect(
                new ChooseOpponentEffect(playerId) {
                    @Override
                    protected void opponentChosen(final String opponentId) {
                        action.appendEffect(
                                new ChooseArbitraryCardsEffect(playerId, "Choose a minion to return to hand", game.getGameState().getDiscard(opponentId), CardType.MINION, 1, 1) {
                                    @Override
                                    protected void cardsSelected(LotroGame game, Collection<PhysicalCard> selectedCards) {
                                        for (PhysicalCard selectedCard : selectedCards) {
                                            action.appendEffect(
                                                    new PutCardFromDiscardIntoHandEffect(selectedCard));
                                            action.appendEffect(
                                                    new RevealAndChooseCardsFromOpponentHandEffect(action, playerId, opponentId, self, "Choose a minion to discard", CardType.MINION, 1, 1) {
                                                        @Override
                                                        protected void cardsSelected(List<PhysicalCard> selectedCards) {
                                                            for (PhysicalCard card : selectedCards) {
                                                                action.appendEffect(
                                                                        new DiscardCardsFromHandEffect(self, opponentId, Collections.singleton(card), true));
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                    }
                });
        return action;
    }
}
