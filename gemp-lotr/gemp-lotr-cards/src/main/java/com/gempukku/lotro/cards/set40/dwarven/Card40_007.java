package com.gempukku.lotro.cards.set40.dwarven;

import com.gempukku.lotro.cards.AbstractEvent;
import com.gempukku.lotro.cards.PlayConditions;
import com.gempukku.lotro.cards.actions.PlayEventAction;
import com.gempukku.lotro.cards.effects.AddUntilStartOfPhaseModifierEffect;
import com.gempukku.lotro.cards.effects.choose.ChooseAndDiscardStackedCardsEffect;
import com.gempukku.lotro.cards.effects.choose.ChooseAndExertCharactersEffect;
import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.modifiers.StrengthModifier;

/**
 * Title: Brothers in Arms
 * Set: Second Edition
 * Side: Free
 * Culture: Dwarven
 * Twilight Cost: 0
 * Type: Event - Skirmish
 * Card Number: 1U7
 * Game Text: To play, exert two Dwarves and discard two cards stacked a [DWARVEN] condition.
 * Each exerted Dwarf is strength +2 until the regroup phase.
 */
public class Card40_007 extends AbstractEvent {
    public Card40_007() {
        super(Side.FREE_PEOPLE, 0, Culture.DWARVEN, "Brothers in Arms", Phase.SKIRMISH);
    }

    @Override
    public boolean checkPlayRequirements(String playerId, LotroGame game, PhysicalCard self, int withTwilightRemoved, int twilightModifier, boolean ignoreRoamingPenalty, boolean ignoreCheckingDeadPile) {
        return super.checkPlayRequirements(playerId, game, self, withTwilightRemoved, twilightModifier, ignoreRoamingPenalty, ignoreCheckingDeadPile)
                && PlayConditions.canExert(self, game, 1, 2, Race.DWARF)
                && PlayConditions.canDiscardFromStacked(self, game, self.getOwner(), 2, Filters.and(Culture.DWARVEN, CardType.CONDITION), Filters.any);
    }

    @Override
    public PlayEventAction getPlayCardAction(String playerId, LotroGame game, final PhysicalCard self, int twilightModifier, boolean ignoreRoamingPenalty) {
        final PlayEventAction action = new PlayEventAction(self);
        action.appendCost(
                new ChooseAndExertCharactersEffect(action, self.getOwner(), 2, 2, Race.DWARF) {
                    @Override
                    protected void forEachCardExertedCallback(PhysicalCard character) {
                        action.appendEffect(
                                new AddUntilStartOfPhaseModifierEffect(
                                        new StrengthModifier(self, character, 2), Phase.REGROUP));
                    }
                });
        action.appendCost(
                new ChooseAndDiscardStackedCardsEffect(action, self.getOwner(), 2, 2, Filters.and(Culture.DWARVEN, CardType.CONDITION), Filters.any));
        return action;
    }
}