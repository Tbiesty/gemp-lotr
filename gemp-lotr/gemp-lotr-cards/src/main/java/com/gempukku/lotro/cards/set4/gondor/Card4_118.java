package com.gempukku.lotro.cards.set4.gondor;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filter;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.cardtype.AbstractAttachableFPPossession;
import com.gempukku.lotro.logic.effects.ChooseAndWoundCharactersEffect;
import com.gempukku.lotro.logic.effects.ExertCharactersEffect;
import com.gempukku.lotro.logic.modifiers.KeywordModifier;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Two Towers
 * Side: Free
 * Culture: Gondor
 * Twilight Cost: 1
 * Type: Possession • Ranged Weapon
 * Game Text: Bearer must be Faramir. He is an archer. Skirmish: If Faramir is skirmishing a Man or a roaming minion,
 * exert Faramir to wound that minion.
 */
public class Card4_118 extends AbstractAttachableFPPossession {
    public Card4_118() {
        super(1, 0, 0, Culture.GONDOR, PossessionClass.RANGED_WEAPON, "Faramir's Bow", null, true);
    }

    @Override
    public Filter getValidTargetFilter(String playerId, LotroGame game, PhysicalCard self) {
        return Filters.name("Faramir");
    }

    @Override
    public List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self) {
        return Collections.singletonList(
                new KeywordModifier(self, Filters.hasAttached(self), Keyword.ARCHER));
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(String playerId, LotroGame game, PhysicalCard self) {
        if (PlayConditions.canUseFPCardDuringPhase(game, Phase.SKIRMISH, self)
                && PlayConditions.canExert(self, game, Filters.hasAttached(self))
                && Filters.countActive(game, Filters.inSkirmishAgainst(Filters.hasAttached(self)), Filters.or(Race.MAN, Keyword.ROAMING)) > 0) {
            ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new ExertCharactersEffect(action, self, self.getAttachedTo()));
            action.appendEffect(
                    new ChooseAndWoundCharactersEffect(action, playerId, 1, 1, Filters.inSkirmishAgainst(Filters.hasAttached(self)), Filters.or(Race.MAN, Keyword.ROAMING)));
            return Collections.singletonList(action);
        }
        return null;
    }
}
