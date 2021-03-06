package com.gempukku.lotro.cards.set6.elven;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.actions.SubAction;
import com.gempukku.lotro.logic.cardtype.AbstractPermanent;
import com.gempukku.lotro.logic.decisions.ArbitraryCardsSelectionDecision;
import com.gempukku.lotro.logic.decisions.DecisionResultInvalidException;
import com.gempukku.lotro.logic.effects.AddTwilightEffect;
import com.gempukku.lotro.logic.effects.DiscardCardFromDeckEffect;
import com.gempukku.lotro.logic.effects.PlayoutDecisionEffect;
import com.gempukku.lotro.logic.effects.SelfDiscardEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: Ents of Fangorn
 * Side: Free
 * Culture: Elven
 * Twilight Cost: 0
 * Type: Condition
 * Game Text: To play, spot 2 Elves. Plays to your support area. Fellowship: Add (1) to look at the top card of your
 * draw deck. You may discard this condition to discard that card.
 */
public class Card6_017 extends AbstractPermanent {
    public Card6_017() {
        super(Side.FREE_PEOPLE, 0, CardType.CONDITION, Culture.ELVEN, "Forewarned");
    }

    @Override
    public boolean checkPlayRequirements(LotroGame game, PhysicalCard self) {
        return PlayConditions.canSpot(game, 2, Race.ELF);
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(String playerId, final LotroGame game, final PhysicalCard self) {
        if (PlayConditions.canUseFPCardDuringPhase(game, Phase.FELLOWSHIP, self)) {
            final ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new AddTwilightEffect(self, 1));
            if (game.getGameState().getDeck(playerId).size() > 0)
                action.appendEffect(
                        new PlayoutDecisionEffect(playerId,
                                new ArbitraryCardsSelectionDecision(1, "Choose a card to discard from deck", Collections.singleton(game.getGameState().getDeck(playerId).get(0)), 0, 1) {
                                    @Override
                                    public void decisionMade(String result) throws DecisionResultInvalidException {
                                        final List<PhysicalCard> selectedCards = getSelectedCardsByResponse(result);
                                        if (selectedCards.size() > 0) {
                                            PhysicalCard selectedCard = selectedCards.get(0);
                                            SubAction subAction = new SubAction(action);
                                            subAction.appendCost(
                                                    new SelfDiscardEffect(self));
                                            subAction.appendEffect(
                                                    new DiscardCardFromDeckEffect(selectedCard));
                                            game.getActionsEnvironment().addActionToStack(subAction);
                                        }
                                    }
                                }));
            return Collections.singletonList(action);
        }
        return null;
    }
}
