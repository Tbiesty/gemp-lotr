package com.gempukku.lotro.cards.set6.elven;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.RevealTopCardsOfDrawDeckEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndAddUntilEOPStrengthBonusEffect;

import java.util.List;

/**
 * Set: Ents of Fangorn
 * Side: Free
 * Culture: Elven
 * Twilight Cost: 1
 * Type: Event
 * Game Text: Skirmish: Reveal the top card of your draw deck to make a minion skirmishing an Elf strength -X, where X
 * is the twilight cost of the revealed card.
 */
public class Card6_016 extends AbstractEvent {
    public Card6_016() {
        super(Side.FREE_PEOPLE, 1, Culture.ELVEN, "Forearmed", Phase.SKIRMISH);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(final String playerId, LotroGame game, final PhysicalCard self) {
        final PlayEventAction action = new PlayEventAction(self);
        action.appendCost(
                new RevealTopCardsOfDrawDeckEffect(self, playerId, 1) {
                    @Override
                    protected void cardsRevealed(List<PhysicalCard> revealedCards) {
                        for (PhysicalCard card : revealedCards) {
                            int twilightCost = card.getBlueprint().getTwilightCost();
                            action.appendEffect(
                                    new ChooseAndAddUntilEOPStrengthBonusEffect(action, self, playerId, -twilightCost, CardType.MINION, Filters.inSkirmishAgainst(Race.ELF)));
                        }
                    }
                });
        return action;
    }
}
