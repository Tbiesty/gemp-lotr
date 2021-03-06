package com.gempukku.lotro.cards.set11.gandalf;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.cardtype.AbstractPermanent;
import com.gempukku.lotro.logic.effects.ChooseAndDiscardCardsFromHandEffect;
import com.gempukku.lotro.logic.effects.PutCardFromStackedIntoHandEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndStackCardsFromHandEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseStackedCardsEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Set: Shadows
 * Side: Free
 * Culture: Gandalf
 * Twilight Cost: 1
 * Type: Possession • Support Area
 * Game Text: To play, spot a [GANDALF] Wizard. Regroup: Stack a spell from hand here. Fellowship: Discard a [GANDALF]
 * card from hand to take a card stacked here into hand.
 */
public class Card11_028 extends AbstractPermanent {
    public Card11_028() {
        super(Side.FREE_PEOPLE, 1, CardType.POSSESSION, Culture.GANDALF, "The Art of Gandalf");
    }

    @Override
    public boolean checkPlayRequirements(LotroGame game, PhysicalCard self) {
        return PlayConditions.canSpot(game, Culture.GANDALF, Race.WIZARD);
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(String playerId, LotroGame game, PhysicalCard self) {
        if (PlayConditions.canUseFPCardDuringPhase(game, Phase.REGROUP, self)) {
            ActivateCardAction action = new ActivateCardAction(self);
            action.appendEffect(
                    new ChooseAndStackCardsFromHandEffect(action, playerId, 1, 1, self, Keyword.SPELL));
            return Collections.singletonList(action);
        }
        if (PlayConditions.canUseFPCardDuringPhase(game, Phase.FELLOWSHIP, self)
                && PlayConditions.canDiscardFromHand(game, playerId, 1, Culture.GANDALF)) {
            final ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new ChooseAndDiscardCardsFromHandEffect(action, playerId, false, 1, Culture.GANDALF));
            action.appendEffect(
                    new ChooseStackedCardsEffect(action, playerId, 1, 1, self, Filters.any) {
                        @Override
                        protected void cardsChosen(LotroGame game, Collection<PhysicalCard> stackedCards) {
                            for (PhysicalCard stackedCard : stackedCards) {
                                action.appendEffect(
                                        new PutCardFromStackedIntoHandEffect(stackedCard));
                            }
                        }
                    });
            return Collections.singletonList(action);
        }
        return null;
    }
}
