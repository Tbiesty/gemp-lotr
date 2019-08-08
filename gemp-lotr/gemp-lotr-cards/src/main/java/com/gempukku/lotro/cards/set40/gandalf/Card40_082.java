package com.gempukku.lotro.cards.set40.gandalf;

import com.gempukku.lotro.cards.AbstractAttachable;
import com.gempukku.lotro.cards.PlayConditions;
import com.gempukku.lotro.cards.TriggerConditions;
import com.gempukku.lotro.cards.effects.PreventCardEffect;
import com.gempukku.lotro.cards.modifiers.VitalityModifier;
import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filter;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.effects.ChooseAndDiscardCardsFromHandEffect;
import com.gempukku.lotro.logic.effects.WoundCharactersEffect;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.timing.Action;
import com.gempukku.lotro.logic.timing.Effect;

import java.util.Collections;
import java.util.List;

/**
 * Title: *Mithrandir's Touch
 * Set: Second Edition
 * Side: Free
 * Culture: Gandalf
 * Twilight Cost: 1
 * Type: Condition - Companion
 * Vitality: +1
 * Card Number: 1R82
 * Game Text: To play, spot Gandalf. Bearer must be an unbound companion (except Gandalf).
 * Response: If bearer is about to take a wound in a skirmish, discard 2 [GANDALF] cards from hand to prevent that wound.
 */
public class Card40_082 extends AbstractAttachable {
    public Card40_082() {
        super(Side.FREE_PEOPLE, CardType.CONDITION, 1, Culture.GANDALF, null, "Mithrandir's Touch", null, true);
    }

    @Override
    public boolean checkPlayRequirements(String playerId, LotroGame game, PhysicalCard self, int withTwilightRemoved, Filter additionalAttachmentFilter, int twilightModifier) {
        return super.checkPlayRequirements(playerId, game, self, withTwilightRemoved, additionalAttachmentFilter, twilightModifier)
                && PlayConditions.canSpot(game, Filters.gandalf);
    }

    @Override
    protected Filterable getValidTargetFilter(String playerId, LotroGame game, PhysicalCard self) {
        return Filters.and(Filters.unboundCompanion, Filters.not(Filters.gandalf));
    }

    @Override
    public List<? extends Modifier> getAlwaysOnModifiers(LotroGame game, PhysicalCard self) {
        VitalityModifier vitality = new VitalityModifier(self, Filters.hasAttached(self), 1);
        return Collections.singletonList(vitality);
    }

    @Override
    public List<? extends Action> getOptionalInPlayBeforeActions(String playerId, LotroGame game, Effect effect, PhysicalCard self) {
        if (TriggerConditions.isGettingWounded(effect, game, Filters.hasAttached(self))
                && PlayConditions.isPhase(game, Phase.SKIRMISH)
                && PlayConditions.canDiscardFromHand(game, playerId, 2, Culture.GANDALF)) {
            WoundCharactersEffect woundEffect = (WoundCharactersEffect) effect;
            ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new ChooseAndDiscardCardsFromHandEffect(action, playerId, false, 2, Culture.GANDALF));
            action.appendEffect(
                    new PreventCardEffect(woundEffect, self.getAttachedTo()));
            return Collections.singletonList(action);
        }
        return null;
    }
}