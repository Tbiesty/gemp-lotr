package com.gempukku.lotro.cards.set3.sauron;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.cardtype.AbstractMinion;
import com.gempukku.lotro.logic.effects.DiscardTopCardFromDeckEffect;
import com.gempukku.lotro.logic.effects.SelfExertEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndDiscardCardsFromPlayEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: Realms of Elf-lords
 * Side: Shadow
 * Culture: Sauron
 * Twilight Cost: 3
 * Type: Minion • Orc
 * Strength: 9
 * Vitality: 3
 * Site: 6
 * Game Text: Maneuver: Exert this minion and discard your [SAURON] condition to make the Free Peoples player discard
 * the top 2 cards from his or her draw deck.
 */
public class Card3_101 extends AbstractMinion {
    public Card3_101() {
        super(3, 9, 3, 6, Race.ORC, Culture.SAURON, "Orc Warrior");
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(String playerId, LotroGame game, PhysicalCard self) {
        if (PlayConditions.canUseShadowCardDuringPhase(game, Phase.MANEUVER, self, 0)
                && PlayConditions.canExert(self, game, self)
                && Filters.countActive(game, Filters.owner(playerId), Culture.SAURON, CardType.CONDITION) > 0) {
            ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new SelfExertEffect(action, self));
            action.appendCost(
                    new ChooseAndDiscardCardsFromPlayEffect(action, playerId, 1, 1, Filters.owner(playerId), Culture.SAURON, CardType.CONDITION));
            action.appendEffect(
                    new DiscardTopCardFromDeckEffect(self, game.getGameState().getCurrentPlayerId(), 2, true));
            return Collections.singletonList(action);
        }
        return null;
    }
}
