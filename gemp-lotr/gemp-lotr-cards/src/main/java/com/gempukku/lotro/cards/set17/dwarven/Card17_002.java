package com.gempukku.lotro.cards.set17.dwarven;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.actions.CostToEffectAction;
import com.gempukku.lotro.logic.actions.OptionalTriggerAction;
import com.gempukku.lotro.logic.cardtype.AbstractPermanent;
import com.gempukku.lotro.logic.effects.AddTokenEffect;
import com.gempukku.lotro.logic.effects.SelfDiscardEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndExertCharactersEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndRemoveFromTheGameCardsInDiscardEffect;
import com.gempukku.lotro.logic.modifiers.AbstractExtraPlayCostModifier;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.modifiers.SpotCondition;
import com.gempukku.lotro.logic.modifiers.condition.AndCondition;
import com.gempukku.lotro.logic.modifiers.condition.CanSpotCultureTokensCondition;
import com.gempukku.lotro.logic.timing.EffectResult;
import com.gempukku.lotro.logic.timing.PlayConditions;
import com.gempukku.lotro.logic.timing.TriggerConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: Rise of Saruman
 * Side: Free
 * Culture: Dwarven
 * Twilight Cost: 4
 * Type: Condition • Support Area
 * Game Text: When you play this condition, you may add a [DWARVEN] token here. While you can spot 4 [DWARVEN] tokens
 * and 2 Dwarves, each [ORC] Orc gains this text 'To play, remove an [ORC] card from your discard pile from the game.'
 * Skirmish: Discard this condition to exert a minion for each Dwarf you can spot.
 */
public class Card17_002 extends AbstractPermanent {
    public Card17_002() {
        super(Side.FREE_PEOPLE, 4, CardType.CONDITION, Culture.DWARVEN, "Balin Avenged", null, true);
    }

    @Override
    public List<OptionalTriggerAction> getOptionalAfterTriggers(String playerId, LotroGame game, EffectResult effectResult, PhysicalCard self) {
        if (TriggerConditions.played(game, effectResult, self)) {
            OptionalTriggerAction action = new OptionalTriggerAction(self);
            action.appendEffect(
                    new AddTokenEffect(self, self, Token.DWARVEN));
            return Collections.singletonList(action);
        }
        return null;
    }

    @Override
    public List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self) {
        return Collections.singletonList(new AbstractExtraPlayCostModifier(self, "To play, remove an ORC card from your discard pile from the game.",
                Filters.and(Culture.ORC, Race.ORC),
                new AndCondition(
                        new CanSpotCultureTokensCondition(4, Token.DWARVEN),
                        new SpotCondition(2, Race.DWARF))) {
            @Override
            public boolean canPayExtraCostsToPlay(LotroGame game, PhysicalCard card) {
                return Filters.filter(game.getGameState().getDiscard(card.getOwner()), game, Culture.ORC).size() > 0;
            }

            @Override
            public void appendExtraCosts(LotroGame game, CostToEffectAction action, PhysicalCard card) {
                action.appendCost(
                        new ChooseAndRemoveFromTheGameCardsInDiscardEffect(action, card, card.getOwner(), 1, 1, Culture.ORC));
            }
        });
    }
    
    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(String playerId, LotroGame game, PhysicalCard self) {
        if (PlayConditions.canUseFPCardDuringPhase(game, Phase.SKIRMISH, self)
                && PlayConditions.canSelfDiscard(self, game)) {
            ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new SelfDiscardEffect(self));
            int countActive = Filters.countActive(game, Race.DWARF);
            for (int i = 0; i < countActive; i++) {
                action.appendEffect(
                        new ChooseAndExertCharactersEffect(action, playerId, 1, 1, CardType.MINION));
            }
            return Collections.singletonList(action);
        }
        return null;
    }
}
