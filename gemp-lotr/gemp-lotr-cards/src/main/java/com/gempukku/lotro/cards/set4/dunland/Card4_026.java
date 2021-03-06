package com.gempukku.lotro.cards.set4.dunland;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filter;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.cardtype.AbstractAttachable;

/**
 * Set: The Two Towers
 * Side: Shadow
 * Culture: Dunland
 * Twilight Cost: 1
 * Type: Possession • Hand Weapon
 * Strength: +3
 * Game Text: Bearer must be a [DUNLAND] Man.
 */
public class Card4_026 extends AbstractAttachable {
    public Card4_026() {
        super(Side.SHADOW, CardType.POSSESSION, 1, Culture.DUNLAND, PossessionClass.HAND_WEAPON, "Iron Axe");
    }

    @Override
    public Filter getValidTargetFilter(String playerId, LotroGame game, PhysicalCard self) {
        return Filters.and(Culture.DUNLAND, Race.MAN);
    }

    @Override
    public int getStrength() {
        return 3;
    }
}
