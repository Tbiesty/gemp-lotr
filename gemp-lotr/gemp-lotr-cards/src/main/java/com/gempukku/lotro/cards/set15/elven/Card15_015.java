package com.gempukku.lotro.cards.set15.elven;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.actions.OptionalTriggerAction;
import com.gempukku.lotro.logic.cardtype.AbstractPermanent;
import com.gempukku.lotro.logic.effects.ChooseAndHealCharactersEffect;
import com.gempukku.lotro.logic.effects.SelfDiscardEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndAddUntilEOPStrengthBonusEffect;
import com.gempukku.lotro.logic.timing.EffectResult;
import com.gempukku.lotro.logic.timing.PlayConditions;
import com.gempukku.lotro.logic.timing.TriggerConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Hunters
 * Side: Free
 * Culture: Elven
 * Twilight Cost: 1
 * Type: Condition • Support Area
 * Game Text: Each time Legolas wins a skirmish, you may heal a hunter [DWARVEN] Dwarf or hunter [GONDOR] Man.
 * Skirmish: Discard this condition to make an [ELVEN] Elf strength +2.
 */
public class Card15_015 extends AbstractPermanent {
    public Card15_015() {
        super(Side.FREE_PEOPLE, 1, CardType.CONDITION, Culture.ELVEN, "Focus");
    }

    @Override
    public List<OptionalTriggerAction> getOptionalAfterTriggers(String playerId, LotroGame game, EffectResult effectResult, PhysicalCard self) {
        if (TriggerConditions.winsSkirmish(game, effectResult, Filters.legolas)) {
            OptionalTriggerAction action = new OptionalTriggerAction(self);
            action.appendEffect(
                    new ChooseAndHealCharactersEffect(action, playerId,
                            Filters.or(
                                    Filters.and(Culture.DWARVEN, Race.DWARF),
                                    Filters.and(Culture.GONDOR, Race.MAN)
                            ), Keyword.HUNTER));
            return Collections.singletonList(action);
        }
        return null;
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(String playerId, LotroGame game, PhysicalCard self) {
        if (PlayConditions.canUseFPCardDuringPhase(game, Phase.SKIRMISH, self)
                && PlayConditions.canSelfDiscard(self, game)) {
            ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new SelfDiscardEffect(self));
            action.appendEffect(
                    new ChooseAndAddUntilEOPStrengthBonusEffect(action, self, playerId, 2, Culture.ELVEN, Race.ELF));
            return Collections.singletonList(action);
        }
        return null;
    }
}
