package com.gempukku.lotro.cards.set11.gollum;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.ChooseAndWoundCharactersEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;

/**
 * Set: Shadows
 * Side: Shadow
 * Culture: Gollum
 * Twilight Cost: 2
 * Type: Event • Skirmish
 * Game Text: If a minion is skirmishing a Free Peoples character who has resistance 2 or less, spot Gollum to wound
 * that character twice.
 */
public class Card11_041 extends AbstractEvent {
    public Card11_041() {
        super(Side.SHADOW, 2, Culture.GOLLUM, "Frenzied Attack", Phase.SKIRMISH);
    }

    @Override
    public boolean checkPlayRequirements(LotroGame game, PhysicalCard self) {
        return PlayConditions.canSpot(game, Filters.gollum)
                && PlayConditions.canSpot(game, CardType.MINION, Filters.inSkirmishAgainst(Side.FREE_PEOPLE, Filters.character, Filters.maxResistance(2)));
    }

    @Override
    public PlayEventAction getPlayEventCardAction(String playerId, LotroGame game, PhysicalCard self) {
        PlayEventAction action = new PlayEventAction(self);
        action.appendEffect(
                new ChooseAndWoundCharactersEffect(action, playerId, 1, 1, 2, Filters.inSkirmishAgainst(CardType.MINION), Side.FREE_PEOPLE, Filters.character, Filters.maxResistance(2)));
        return action;
    }
}
