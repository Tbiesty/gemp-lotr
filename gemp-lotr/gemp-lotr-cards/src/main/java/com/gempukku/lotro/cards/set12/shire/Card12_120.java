package com.gempukku.lotro.cards.set12.shire;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.AddBurdenEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndDiscardCardsFromPlayEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;

/**
 * Set: Black Rider
 * Side: Free
 * Culture: Shire
 * Twilight Cost: 1
 * Type: Event • Regroup
 * Game Text: Discard a Hobbit not assigned to a skirmish from play to discard a minion from play. If the fellowship is
 * at a battleground site, you may add 2 burdens to play this event during a skirmish involving a Hobbit.
 */
public class Card12_120 extends AbstractEvent {
    public Card12_120() {
        super(Side.FREE_PEOPLE, 1, Culture.SHIRE, "Diversion", Phase.REGROUP, Phase.SKIRMISH);
    }

    @Override
    public boolean checkPlayRequirements(LotroGame game, PhysicalCard self) {
        return PlayConditions.canDiscardFromPlay(self, game, Race.HOBBIT, Filters.notAssignedToSkirmish)
                && (
                !PlayConditions.isPhase(game, Phase.SKIRMISH)
                        || (
                        PlayConditions.location(game, Keyword.BATTLEGROUND)
                                && PlayConditions.canSpot(game, Filters.inSkirmish, Race.HOBBIT)));
    }

    @Override
    public PlayEventAction getPlayEventCardAction(String playerId, LotroGame game, PhysicalCard self) {
        PlayEventAction action = new PlayEventAction(self);
        if (PlayConditions.isPhase(game, Phase.SKIRMISH))
            action.appendCost(
                    new AddBurdenEffect(playerId, self, 2));
        action.appendCost(
                new ChooseAndDiscardCardsFromPlayEffect(action, playerId, 1, 1, Race.HOBBIT, Filters.notAssignedToSkirmish));
        action.appendEffect(
                new ChooseAndDiscardCardsFromPlayEffect(action, playerId, 1, 1, CardType.MINION));
        return action;
    }
}
