package com.gempukku.lotro.cards.set4.site;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Keyword;
import com.gempukku.lotro.common.SitesBlock;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.cardtype.AbstractSite;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.modifiers.TwilightCostModifier;
import com.gempukku.lotro.logic.modifiers.evaluator.Evaluator;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Two Towers
 * Twilight Cost: 9
 * Type: Site
 * Site: 9T
 * Game Text: Battleground. The Shadow number of Fortress of Orthanc is +2 for each companion over 4.
 */
public class Card4_360 extends AbstractSite {
    public Card4_360() {
        super("Fortress of Orthanc", SitesBlock.TWO_TOWERS, 9, 9, Direction.LEFT);
        addKeyword(Keyword.BATTLEGROUND);
    }

    @Override
    public List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self) {
        return Collections.singletonList(
                new TwilightCostModifier(self, self, null,
                        new Evaluator() {
                            @Override
                            public int evaluateExpression(LotroGame game, PhysicalCard self) {
                                return Math.max(0, 2 * (Filters.countActive(game, CardType.COMPANION) - 4));
                            }
                        }));
    }
}
