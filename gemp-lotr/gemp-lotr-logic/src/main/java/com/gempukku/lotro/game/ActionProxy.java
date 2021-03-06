package com.gempukku.lotro.game;

import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.OptionalTriggerAction;
import com.gempukku.lotro.logic.actions.RequiredTriggerAction;
import com.gempukku.lotro.logic.timing.Action;
import com.gempukku.lotro.logic.timing.Effect;
import com.gempukku.lotro.logic.timing.EffectResult;

import java.util.List;

public interface ActionProxy {
    List<? extends Action> getPhaseActions(String playerId, LotroGame game);

    List<? extends Action> getOptionalBeforeActions(String playerId, LotroGame game, Effect effect);

    List<? extends RequiredTriggerAction> getRequiredBeforeTriggers(LotroGame game, Effect effect);

    List<? extends OptionalTriggerAction> getOptionalBeforeTriggers(String playerId, LotroGame game, Effect effect);

    List<? extends Action> getOptionalAfterActions(String playerId, LotroGame game, EffectResult effectResult);

    List<? extends RequiredTriggerAction> getRequiredAfterTriggers(LotroGame game, EffectResult effectResult);

    List<? extends OptionalTriggerAction> getOptionalAfterTriggers(String playerId, LotroGame game, EffectResult effectResult);
}
