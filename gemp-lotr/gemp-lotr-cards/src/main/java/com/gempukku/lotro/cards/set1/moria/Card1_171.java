package com.gempukku.lotro.cards.set1.moria;

import com.gempukku.lotro.cards.AbstractOldEvent;
import com.gempukku.lotro.cards.actions.PlayEventAction;
import com.gempukku.lotro.cards.effects.choose.ChooseAndAssignMinionToCompanionEffect;
import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.effects.ChooseActiveCardEffect;

/**
 * Set: The Fellowship of the Ring
 * Side: Shadow
 * Culture: Moria
 * Twilight Cost: 2
 * Type: Event
 * Game Text: Assignment: Assign an exhausted companion (except the Ring-bearer) to skirmish a [MORIA] Orc.
 */
public class Card1_171 extends AbstractOldEvent {
    public Card1_171() {
        super(Side.SHADOW, Culture.MORIA, "Frenzy", Phase.ASSIGNMENT);
    }

    @Override
    public PlayEventAction getPlayCardAction(final String playerId, final LotroGame game, final PhysicalCard self, int twilightModifier) {
        final PlayEventAction action = new PlayEventAction(self);
        action.appendEffect(
                new ChooseActiveCardEffect(self, playerId, "Choose exhausted companion", Filters.type(CardType.COMPANION), Filters.exhausted, Filters.canBeAssignedToSkirmishByEffect(Side.SHADOW)) {
                    @Override
                    protected void cardSelected(LotroGame game, final PhysicalCard companion) {
                        action.appendEffect(
                                new ChooseAndAssignMinionToCompanionEffect(action, playerId, companion, Culture.MORIA, Race.ORC));
                    }
                }
        );
        return action;
    }

    @Override
    public int getTwilightCost() {
        return 2;
    }
}
