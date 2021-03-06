package com.gempukku.lotro.cards.set7.site;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Keyword;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.common.SitesBlock;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.cardtype.AbstractSite;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.modifiers.RemoveKeywordModifier;
import com.gempukku.lotro.logic.modifiers.condition.InitiativeCondition;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Return of the King
 * Twilight Cost: 2
 * Type: Site
 * Site: 2K
 * Game Text: Plains. If the Shadow has initiative, minions are not roaming.
 */
public class Card7_336 extends AbstractSite {
    public Card7_336() {
        super("Rohirrim Camp", SitesBlock.KING, 2, 2, Direction.LEFT);
        addKeyword(Keyword.PLAINS);
    }

    @Override
    public List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self) {
        return Collections.singletonList(
                new RemoveKeywordModifier(self, CardType.MINION, new InitiativeCondition(Side.SHADOW), Keyword.ROAMING));
    }
}
