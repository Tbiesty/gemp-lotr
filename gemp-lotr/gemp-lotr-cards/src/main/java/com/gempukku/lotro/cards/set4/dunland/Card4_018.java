package com.gempukku.lotro.cards.set4.dunland;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.cardtype.AbstractMinion;
import com.gempukku.lotro.logic.effects.AddUntilStartOfPhaseModifierEffect;
import com.gempukku.lotro.logic.effects.ChooseActiveCardEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndAssignCharacterToMinionEffect;
import com.gempukku.lotro.logic.modifiers.AllyParticipatesInArcheryFireAndSkirmishesModifier;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Two Towers
 * Side: Shadow
 * Culture: Dunland
 * Twilight Cost: 3
 * Type: Minion • Man
 * Strength: 9
 * Vitality: 1
 * Site: 3
 * Game Text: Assignment: Spot an ally to make that ally participate in skirmishes and assign this minion to skirmish
 * that ally.
 */
public class Card4_018 extends AbstractMinion {
    public Card4_018() {
        super(3, 9, 1, 3, Race.MAN, Culture.DUNLAND, "Dunlending Warrior");
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(final String playerId, LotroGame game, final PhysicalCard self) {
        if (PlayConditions.canUseShadowCardDuringPhase(game, Phase.ASSIGNMENT, self, 0)
                && PlayConditions.canSpot(game, CardType.ALLY)) {
            final ActivateCardAction action = new ActivateCardAction(self);
            action.appendEffect(
                    new ChooseActiveCardEffect(self, playerId, "Choose an Ally", CardType.ALLY) {
                        @Override
                        protected void cardSelected(LotroGame game, PhysicalCard card) {
                            action.appendEffect(
                                    new AddUntilStartOfPhaseModifierEffect(
                                        new AllyParticipatesInArcheryFireAndSkirmishesModifier(self, Filters.sameCard(card)), Phase.SKIRMISH));
                            action.appendEffect(
                                    new ChooseAndAssignCharacterToMinionEffect(action, playerId, self, true, Filters.sameCard(card)));
                        }
                    });
            return Collections.singletonList(action);
        }
        return null;
    }
}
