package com.gempukku.lotro.cards.set15.gollum;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.AddUntilEndOfPhaseModifierEffect;
import com.gempukku.lotro.logic.effects.ChooseActiveCardEffect;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.modifiers.OverwhelmedByMultiplierModifier;
import com.gempukku.lotro.logic.modifiers.ResistanceModifier;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Hunters
 * Side: Free
 * Culture: Gollum
 * Twilight Cost: 2
 * Type: Event • Skirmish
 * Game Text: Spot Smeagol to prevent a Ring-bound Hobbit from being overwhelmed unless his or her strength is tripled.
 * While this card is in your discard pile, Smeagol is resistance +1.
 */
public class Card15_048 extends AbstractEvent {
    public Card15_048() {
        super(Side.FREE_PEOPLE, 2, Culture.GOLLUM, "Release Them", Phase.SKIRMISH);
    }

    @Override
    public boolean checkPlayRequirements(LotroGame game, PhysicalCard self) {
        return PlayConditions.canSpot(game, Filters.smeagol);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(String playerId, LotroGame game, final PhysicalCard self) {
        final PlayEventAction action = new PlayEventAction(self);
        action.appendEffect(
                new ChooseActiveCardEffect(self, playerId, "Choose a Ring-bound Hobbit", Race.HOBBIT, Keyword.RING_BOUND) {
                    @Override
                    protected void cardSelected(LotroGame game, PhysicalCard card) {
                        action.appendEffect(
                                new AddUntilEndOfPhaseModifierEffect(
                                        new OverwhelmedByMultiplierModifier(self, card, 3)));
                    }
                });
        return action;
    }

    @Override
    public List<? extends Modifier> getInDiscardModifiers(LotroGame game, PhysicalCard self) {
        return Collections.singletonList(
                new ResistanceModifier(self, Filters.smeagol, 1));
    }
}
