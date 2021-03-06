package com.gempukku.lotro.cards.set6.isengard;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.cardtype.AbstractMinion;
import com.gempukku.lotro.logic.effects.ChooseActiveCardEffect;
import com.gempukku.lotro.logic.effects.DiscardStackedCardsEffect;
import com.gempukku.lotro.logic.effects.ExhaustCharacterEffect;
import com.gempukku.lotro.logic.effects.RemoveTwilightEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: Ents of Fangorn
 * Side: Shadow
 * Culture: Isengard
 * Twilight Cost: 1
 * Type: Minion • Orc
 * Strength: 5
 * Vitality: 2
 * Site: 4
 * Game Text: Regroup: If stacked on an [ISENGARD] card, discard this card and remove (2) to exhaust a companion.
 */
public class Card6_070 extends AbstractMinion {
    public Card6_070() {
        super(1, 5, 2, 4, Race.ORC, Culture.ISENGARD, "Isengard Tender");
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsFromStacked(final String playerId, LotroGame game, final PhysicalCard self) {
        if (PlayConditions.canUseStackedShadowCardDuringPhase(game, Phase.REGROUP, self, 2)
                && self.getStackedOn().getBlueprint().getCulture() == Culture.ISENGARD) {
            final ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new DiscardStackedCardsEffect(self, self));
            action.appendCost(
                    new RemoveTwilightEffect(2));
            action.appendEffect(
                    new ChooseActiveCardEffect(self, playerId, "Choose companion", CardType.COMPANION, Filters.canExert(self)) {
                        @Override
                        protected void cardSelected(LotroGame game, PhysicalCard card) {
                            action.insertEffect(
                                    new ExhaustCharacterEffect(self, action, card));
                        }
                    });
            return Collections.singletonList(action);
        }
        return null;
    }
}
