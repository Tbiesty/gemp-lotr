package com.gempukku.lotro.cards.set11.orc;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.CostToEffectAction;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.DiscardTopCardFromDeckEffect;
import com.gempukku.lotro.logic.effects.PreventableEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndDiscardCardsFromPlayEffect;
import com.gempukku.lotro.logic.timing.Effect;
import com.gempukku.lotro.logic.timing.PlayConditions;

/**
 * Set: Shadows
 * Side: Shadow
 * Culture: Orc
 * Twilight Cost: 0
 * Type: Event • Shadow
 * Game Text: Spot 2 [ORC] minions to discard a condition from play. The Free Peoples player may discard the top 6 cards
 * of his or her draw deck to prevent this.
 */
public class Card11_118 extends AbstractEvent {
    public Card11_118() {
        super(Side.SHADOW, 0, Culture.ORC, "Dread and Despair", Phase.SHADOW);
    }

    @Override
    public boolean checkPlayRequirements(LotroGame game, PhysicalCard self) {
        return PlayConditions.canSpot(game, 2, Culture.ORC, CardType.MINION);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(String playerId, final LotroGame game, final PhysicalCard self) {
        PlayEventAction action = new PlayEventAction(self);
        action.appendEffect(
                new PreventableEffect(action,
                        new ChooseAndDiscardCardsFromPlayEffect(action, playerId, 1, 1, CardType.CONDITION) {
                            @Override
                            public String getText(LotroGame game) {
                                return "Discard a condition from play";
                            }
                        }, game.getGameState().getCurrentPlayerId(),
                        new PreventableEffect.PreventionCost() {
                            @Override
                            public Effect createPreventionCostForPlayer(CostToEffectAction subAction, String playerId) {
                                return new DiscardTopCardFromDeckEffect(self, game.getGameState().getCurrentPlayerId(), 6, false) {
                                    @Override
                                    public String getText(LotroGame game) {
                                        return "Discard top 6 cards from your deck";
                                    }
                                };
                            }
                        }));
        return action;
    }
}
