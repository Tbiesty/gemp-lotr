package com.gempukku.lotro.cards.set3.site;

import com.gempukku.lotro.logic.cardtype.AbstractSite;
import com.gempukku.lotro.logic.timing.PlayConditions;
import com.gempukku.lotro.logic.timing.TriggerConditions;
import com.gempukku.lotro.logic.effects.RemoveBurdenEffect;
import com.gempukku.lotro.common.SitesBlock;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.OptionalTriggerAction;
import com.gempukku.lotro.logic.timing.EffectResult;

import java.util.Collections;
import java.util.List;

/**
 * Set: Realms of Elf-lords
 * Twilight Cost: 0
 * Type: Site
 * Site: 3
 * Game Text: Sanctuary. When the fellowship moves to House of Elrond, the Free Peoples player may spot 2 Elves
 * to remove a burden.
 */
public class Card3_119 extends AbstractSite {
    public Card3_119() {
        super("House of Elrond", SitesBlock.FELLOWSHIP, 3, 0, Direction.RIGHT);

    }

    @Override
    public List<OptionalTriggerAction> getOptionalAfterTriggers(String playerId, LotroGame game, EffectResult effectResult, PhysicalCard self) {
        if (TriggerConditions.movesTo(game, effectResult, self)
                && playerId.equals(game.getGameState().getCurrentPlayerId())
                && PlayConditions.canSpot(game, 2, Race.ELF)) {
            OptionalTriggerAction action = new OptionalTriggerAction(self);
            action.appendEffect(
                    new RemoveBurdenEffect(playerId, self));
            return Collections.singletonList(action);
        }
        return null;
    }
}
