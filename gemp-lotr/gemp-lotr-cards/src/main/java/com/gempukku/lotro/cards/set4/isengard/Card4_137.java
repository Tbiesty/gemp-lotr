package com.gempukku.lotro.cards.set4.isengard;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.cardtype.AbstractPermanent;
import com.gempukku.lotro.logic.effects.AddTokenEffect;
import com.gempukku.lotro.logic.effects.RemoveTokenEffect;
import com.gempukku.lotro.logic.effects.TakeControlOfASiteEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndDiscardCardsFromPlayEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndPlayCardFromHandEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Two Towers
 * Side: Shadow
 * Culture: Isengard
 * Twilight Cost: 0
 * Type: Condition
 * Game Text: Plays to your support area. Shadow: Play an Uruk-hai to place an [ISENGARD] token on this card.
 * Regroup: Remove 3 [ISENGARD] tokens from this card and discard an Uruk-hai to take control of a site.
 */
public class Card4_137 extends AbstractPermanent {
    public Card4_137() {
        super(Side.SHADOW, 0, CardType.CONDITION, Culture.ISENGARD, "Attack on Helm's Deep");
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(String playerId, LotroGame game, PhysicalCard self) {
        if (PlayConditions.canUseShadowCardDuringPhase(game, Phase.SHADOW, self, 0)
                && Filters.filter(game.getGameState().getHand(playerId), game, Race.URUK_HAI, Filters.playable(game)).size() > 0) {
            ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new ChooseAndPlayCardFromHandEffect(playerId, game, Race.URUK_HAI));
            action.appendEffect(
                    new AddTokenEffect(self, self, Token.ISENGARD));
            return Collections.singletonList(action);
        }
        if (PlayConditions.canUseShadowCardDuringPhase(game, Phase.REGROUP, self, 0)
                && game.getGameState().getTokenCount(self, Token.ISENGARD) >= 3
                && Filters.countActive(game, Race.URUK_HAI) > 0) {
            ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new RemoveTokenEffect(self, self, Token.ISENGARD, 3));
            action.appendCost(
                    new ChooseAndDiscardCardsFromPlayEffect(action, playerId, 1, 1, Race.URUK_HAI));
            action.appendEffect(
                    new TakeControlOfASiteEffect(self, playerId));
            return Collections.singletonList(action);
        }
        return null;
    }
}
