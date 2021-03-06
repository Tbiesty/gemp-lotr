package com.gempukku.lotro.cards.set18.rohan;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.choose.ChooseAndExertCharactersEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndReturnCardsToHandEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;

/**
 * Set: Treachery & Deceit
 * Side: Free
 * Culture: Rohan
 * Twilight Cost: 3
 * Type: Event • Maneuver
 * Game Text: If the fellowship is at a plains site, exert a [ROHAN] companion to return a minion to its owner's hand.
 */
public class Card18_098 extends AbstractEvent {
    public Card18_098() {
        super(Side.FREE_PEOPLE, 3, Culture.ROHAN, "Fall Back to Helm's Deep", Phase.MANEUVER);
    }

    @Override
    public boolean checkPlayRequirements(LotroGame game, PhysicalCard self) {
        return PlayConditions.location(game, Keyword.PLAINS)
                && PlayConditions.canExert(self, game, Culture.ROHAN, CardType.COMPANION);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(String playerId, LotroGame game, PhysicalCard self) {
        PlayEventAction action = new PlayEventAction(self);
        action.appendCost(
                new ChooseAndExertCharactersEffect(action, playerId, 1, 1, Culture.ROHAN, CardType.COMPANION));
        action.appendEffect(
                new ChooseAndReturnCardsToHandEffect(action, playerId, 1, 1, CardType.MINION));
        return action;
    }
}
