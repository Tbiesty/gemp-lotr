package com.gempukku.lotro.cards.set20.gondor;

import com.gempukku.lotro.logic.cardtype.AbstractCompanion;
import com.gempukku.lotro.logic.timing.PlayConditions;
import com.gempukku.lotro.logic.timing.TriggerConditions;
import com.gempukku.lotro.logic.effects.AddUntilStartOfPhaseModifierEffect;
import com.gempukku.lotro.logic.effects.SelfExertEffect;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Keyword;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.GameUtils;
import com.gempukku.lotro.logic.actions.OptionalTriggerAction;
import com.gempukku.lotro.logic.modifiers.KeywordModifier;
import com.gempukku.lotro.logic.modifiers.evaluator.Evaluator;
import com.gempukku.lotro.logic.timing.EffectResult;

import java.util.Collections;
import java.util.List;

/**
 * 4
 * •Aragorn, Dunedain Ranger
 * Gondor	Companion • Man
 * 8	4	8
 * Ranger.
 * At the beginning of the manuever phase, you may exert Aragorn to make him defender +1 until the regroup phase
 * for each site from your adventure deck in the current region.
 */
public class Card20_175 extends AbstractCompanion {
    public Card20_175() {
        super(4, 8, 4, 8, Culture.GONDOR, Race.MAN, null, "Aragorn", "Dunedain Ranger", true);
        addKeyword(Keyword.RANGER);
    }

    @Override
    public List<OptionalTriggerAction> getOptionalAfterTriggers(String playerId, LotroGame game, EffectResult effectResult, final PhysicalCard self) {
        if (TriggerConditions.startOfPhase(game, effectResult, Phase.MANEUVER)
                && PlayConditions.canSelfExert(self, game)) {
            OptionalTriggerAction action = new OptionalTriggerAction(self);
            action.appendCost(
                    new SelfExertEffect(action, self));
            action.appendEffect(
                    new AddUntilStartOfPhaseModifierEffect(
                            new KeywordModifier(self, self, null, Keyword.DEFENDER,
                                    new Evaluator() {
                                        @Override
                                        public int evaluateExpression(LotroGame game, PhysicalCard cardAffected) {
                                            int count = 0;
                                            for (int i=1; i<=9; i++) {
                                                if (GameUtils.getRegion(i) == GameUtils.getRegion(game)) {
                                                    final PhysicalCard site = game.getGameState().getSite(i);
                                                    if (site != null && site.getOwner().equals(self.getOwner()))
                                                        count++;
                                                }
                                            }
                                            return count;
                                        }
                                    }), Phase.REGROUP));
            return Collections.singletonList(action);
        }
        return null;
    }
}
