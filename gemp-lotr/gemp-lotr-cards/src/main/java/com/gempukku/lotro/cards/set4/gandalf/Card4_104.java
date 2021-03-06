package com.gempukku.lotro.cards.set4.gandalf;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.cardtype.AbstractAlly;
import com.gempukku.lotro.logic.effects.ChooseAndDiscardCardsFromHandEffect;
import com.gempukku.lotro.logic.effects.ChooseAndHealCharactersEffect;
import com.gempukku.lotro.logic.effects.SelfExertEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Two Towers
 * Side: Free
 * Culture: Gandalf
 * Twilight Cost: 4
 * Type: Ally • Home 2T & 8T • Ent
 * Strength: 12
 * Vitality: 4
 * Site: 2T, 8T
 * Game Text: Unhasty.
 * Fellowship: Exert Treebeard and discard an unbound companion from hand to heal an unbound companion.
 */
public class Card4_104 extends AbstractAlly {
    public Card4_104() {
        super(4, SitesBlock.TWO_TOWERS, new int[]{2, 8}, 12, 4, Race.ENT, Culture.GANDALF, "Treebeard", "Oldest Living Thing", true);
        addKeyword(Keyword.UNHASTY);
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(String playerId, LotroGame game, PhysicalCard self) {
        if (PlayConditions.canUseFPCardDuringPhase(game, Phase.FELLOWSHIP, self)
                && PlayConditions.canExert(self, game, self)
                && Filters.filter(game.getGameState().getHand(playerId), game, Filters.unboundCompanion).size() > 0) {
            ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new SelfExertEffect(action, self));
            action.appendCost(
                    new ChooseAndDiscardCardsFromHandEffect(action, playerId, false, 1, 1, Filters.unboundCompanion));
            action.appendEffect(
                    new ChooseAndHealCharactersEffect(action, playerId, 1, 1, Filters.unboundCompanion));
            return Collections.singletonList(action);
        }
        return null;
    }
}
