package com.gempukku.lotro.cards.set17.rohan;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.AbstractActionProxy;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.actions.RequiredTriggerAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.AddUntilEndOfPhaseActionProxyEffect;
import com.gempukku.lotro.logic.effects.AddUntilEndOfPhaseModifierEffect;
import com.gempukku.lotro.logic.effects.ChooseActiveCardEffect;
import com.gempukku.lotro.logic.effects.DiscardCardsFromPlayEffect;
import com.gempukku.lotro.logic.modifiers.StrengthModifier;
import com.gempukku.lotro.logic.timing.EffectResult;
import com.gempukku.lotro.logic.timing.PlayConditions;
import com.gempukku.lotro.logic.timing.TriggerConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: Rise of Saruman
 * Side: Free
 * Culture: Rohan
 * Twilight Cost: 1
 * Type: Event • Skirmish
 * Game Text: Spot a [ROHAN] companion to make Frodo strength +5. If Frodo loses the skirmish, discard that
 * [ROHAN] companion.
 */
public class Card17_103 extends AbstractEvent {
    public Card17_103() {
        super(Side.FREE_PEOPLE, 1, Culture.ROHAN, "Where Now The Horse", Phase.SKIRMISH);
    }

    @Override
    public boolean checkPlayRequirements(LotroGame game, PhysicalCard self) {
        return PlayConditions.canSpot(game, Culture.ROHAN, CardType.COMPANION);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(final String playerId, LotroGame game, final PhysicalCard self) {
        final PlayEventAction action = new PlayEventAction(self);
        action.appendCost(
                new ChooseActiveCardEffect(self, playerId, "Choose a ROHAN companion", Culture.ROHAN, CardType.COMPANION) {
                    @Override
                    protected void cardSelected(LotroGame game, final PhysicalCard card) {
                        action.appendEffect(
                                new AddUntilEndOfPhaseModifierEffect(
                                        new StrengthModifier(self, Filters.frodo, 5)));
                        action.appendEffect(
                                new AddUntilEndOfPhaseActionProxyEffect(
                                        new AbstractActionProxy() {
                                            @Override
                                            public List<? extends RequiredTriggerAction> getRequiredAfterTriggers(LotroGame game, EffectResult effectResult) {
                                                if (TriggerConditions.losesSkirmish(game, effectResult, Filters.frodo)) {
                                                    RequiredTriggerAction action = new RequiredTriggerAction(self);
                                                    action.appendEffect(
                                                            new DiscardCardsFromPlayEffect(playerId, self, card));
                                                    return Collections.singletonList(action);
                                                }
                                                return null;
                                            }
                                        }));
                    }
                });
        return action;
    }
}
