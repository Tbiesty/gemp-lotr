package com.gempukku.lotro.logic.modifiers;

import com.gempukku.lotro.common.Filterable;
import com.gempukku.lotro.filters.Filter;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;

public class CantExertWithCardModifier extends AbstractModifier {
    private Filter _preventExertWithFilter;

    public CantExertWithCardModifier(PhysicalCard source, Filterable affectFilter, Filterable preventExertWithFilter) {
        this(source, affectFilter, null, preventExertWithFilter);
    }

    public CantExertWithCardModifier(PhysicalCard source, Filterable affectFilter, Condition condition, Filterable preventExertWithFilter) {
        super(source, "Affected by exertion preventing effect", affectFilter, condition, ModifierEffect.WOUND_MODIFIER);
        _preventExertWithFilter = Filters.and(preventExertWithFilter);
    }

    @Override
    public boolean canBeExerted(LotroGame game, PhysicalCard source, PhysicalCard card) {
        if (_preventExertWithFilter.accepts(game, source))
            return false;
        return true;
    }
}
