package com.gempukku.lotro.cards.set13.rohan;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.RemovePlayedEventFromGameEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndAddUntilEOPStrengthBonusEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndRemoveFromTheGameCardsInDiscardEffect;
import com.gempukku.lotro.logic.timing.Action;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: Bloodlines
 * Side: Free
 * Culture: Rohan
 * Twilight Cost: 2
 * Type: Event • Skirmish
 * Game Text: You may remove from the game 4 other [ROHAN] cards in your discard pile to play this event from your
 * discard pile. Then remove this event from the game. Make your mounted [ROHAN] companion strength +3.
 */
public class Card13_130 extends AbstractEvent {
    public Card13_130() {
        super(Side.FREE_PEOPLE, 2, Culture.ROHAN, "Hurried Barrows", Phase.SKIRMISH);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(String playerId, LotroGame game, PhysicalCard self) {
        PlayEventAction action = new PlayEventAction(self);
        action.appendEffect(
                new ChooseAndAddUntilEOPStrengthBonusEffect(action, self, playerId, 3, Filters.owner(playerId), Culture.ROHAN, CardType.COMPANION, Filters.mounted));
        return action;
    }

    @Override
    public List<? extends Action> getPhaseActionsFromDiscard(String playerId, LotroGame game, PhysicalCard self) {
        if (PlayConditions.isPhase(game, Phase.SKIRMISH)
                && PlayConditions.canPlayFromDiscard(playerId, game, self)
                && PlayConditions.canRemoveFromDiscardToPlay(self, game, playerId, 4, Culture.ROHAN)) {
            final PlayEventAction action = getPlayEventCardAction(playerId, game, self);
            action.appendCost(
                    new ChooseAndRemoveFromTheGameCardsInDiscardEffect(action, self, playerId, 4, 4, Culture.ROHAN));
            action.appendEffect(
                    new RemovePlayedEventFromGameEffect(action));
            return Collections.singletonList(action);
        }
        return null;
    }
}
