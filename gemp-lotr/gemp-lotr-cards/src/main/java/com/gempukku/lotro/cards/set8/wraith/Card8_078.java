package com.gempukku.lotro.cards.set8.wraith;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.cardtype.AbstractPermanent;
import com.gempukku.lotro.logic.effects.SelfDiscardEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndAddUntilEOPStrengthBonusEffect;
import com.gempukku.lotro.logic.modifiers.InitiativeHandSizeModifier;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.modifiers.evaluator.CountActiveEvaluator;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: Siege of Gondor
 * Side: Shadow
 * Culture: Wraith
 * Twilight Cost: 3
 * Type: Condition • Support Area
 * Game Text: For each [WRAITH] Orc you can spot, the Free Peoples player must have an additional card in hand to have
 * initiative. Skirmish: Discard this condition to make a [WRAITH] Orc strength +2.
 */
public class Card8_078 extends AbstractPermanent {
    public Card8_078() {
        super(Side.SHADOW, 3, CardType.CONDITION, Culture.WRAITH, "Streaming to the Field", null, true);
    }

    @Override
    public List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self) {
        return Collections.singletonList(
                new InitiativeHandSizeModifier(self, null, new CountActiveEvaluator(Culture.WRAITH, Race.ORC)));
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(String playerId, LotroGame game, PhysicalCard self) {
        if (PlayConditions.canUseShadowCardDuringPhase(game, Phase.SKIRMISH, self, 0)
                && PlayConditions.canSelfDiscard(self, game)) {
            ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new SelfDiscardEffect(self));
            action.appendEffect(
                    new ChooseAndAddUntilEOPStrengthBonusEffect(action, self, playerId, 2, Culture.WRAITH, Race.ORC));
            return Collections.singletonList(action);
        }
        return null;
    }
}
