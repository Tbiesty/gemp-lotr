package com.gempukku.lotro.cards.set11.uruk_hai;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Keyword;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.cardtype.AbstractMinion;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.modifiers.SpotCondition;
import com.gempukku.lotro.logic.modifiers.StrengthModifier;

import java.util.Collections;
import java.util.List;

/**
 * Set: Shadows
 * Side: Shadow
 * Culture: Uruk-hai
 * Twilight Cost: 2
 * Type: Minion • Uruk-Hai
 * Strength: 5
 * Vitality: 2
 * Site: 5
 * Game Text: Damage +1. While you can spot an exhausted companion, this minion is strength +3.
 */
public class Card11_188 extends AbstractMinion {
    public Card11_188() {
        super(2, 5, 2, 5, Race.URUK_HAI, Culture.URUK_HAI, "Hounding Uruk");
        addKeyword(Keyword.DAMAGE, 1);
    }

    @Override
    public List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self) {
return Collections.singletonList(new StrengthModifier(self, self, new SpotCondition(CardType.COMPANION, Filters.exhausted), 3));
}
}
