package com.gempukku.lotro.logic.modifiers.evaluator;

import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;

public interface Evaluator {
    public int evaluateExpression(LotroGame game, PhysicalCard cardAffected);
}
