package com.gempukku.lotro.cards.set4.raider;

import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Keyword;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.CostToEffectAction;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.AddBurdenEffect;
import com.gempukku.lotro.logic.effects.AssignmentEffect;
import com.gempukku.lotro.logic.effects.ChooseActiveCardEffect;
import com.gempukku.lotro.logic.effects.PreventableEffect;
import com.gempukku.lotro.logic.timing.Effect;

/**
 * Set: The Two Towers
 * Side: Shadow
 * Culture: Raider
 * Twilight Cost: 2
 * Type: Event
 * Game Text: Assignment: Assign an Easterling to the Ring-bearer. The Free Peoples player may add a burden
 * to prevent this.
 */
public class Card4_259 extends AbstractEvent {
    public Card4_259() {
        super(Side.SHADOW, 2, Culture.RAIDER, "Vision From Afar", Phase.ASSIGNMENT);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(final String playerId, final LotroGame game, final PhysicalCard self) {
        final PlayEventAction action = new PlayEventAction(self);
        final PhysicalCard ringBearer = game.getGameState().getRingBearer(game.getGameState().getCurrentPlayerId());
        action.appendEffect(
                new ChooseActiveCardEffect(self, playerId, "Choose Easterling", Keyword.EASTERLING, Filters.assignableToSkirmishAgainst(Side.SHADOW, ringBearer)) {
                    @Override
                    protected void cardSelected(final LotroGame game, PhysicalCard minion) {
                        action.insertEffect(
                                new PreventableEffect(action,
                                        new AssignmentEffect(playerId, ringBearer, minion),
                                        game.getGameState().getCurrentPlayerId(),
                                        new PreventableEffect.PreventionCost() {
                                            @Override
                                            public Effect createPreventionCostForPlayer(CostToEffectAction subAction, String playerId) {
                                                return new AddBurdenEffect(game.getGameState().getCurrentPlayerId(), self, 1);
                                            }
                                        }
                                ));
                    }
                });
        return action;
    }
}
