package com.gempukku.lotro.cards.set12.uruk_hai;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.GameUtils;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.OptionalEffect;
import com.gempukku.lotro.logic.effects.PutPlayedEventIntoHandEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndAddUntilEOPStrengthBonusEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;

/**
 * Set: Black Rider
 * Side: Shadow
 * Culture: Uruk-hai
 * Twilight Cost: 1
 * Type: Event • Skirmish
 * Game Text: Make an [URUK-HAI] minion strength +2. Then you may spot 6 companions to return this event to your hand.
 */
public class Card12_139 extends AbstractEvent {
    public Card12_139() {
        super(Side.SHADOW, 1, Culture.URUK_HAI, "Broken in Defeat", Phase.SKIRMISH);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(String playerId, LotroGame game, final PhysicalCard self) {
        PlayEventAction action = new PlayEventAction(self);
        action.appendEffect(
                new ChooseAndAddUntilEOPStrengthBonusEffect(action, self, playerId, 2, Culture.URUK_HAI, CardType.MINION));
        if (PlayConditions.canSpot(game, 6, CardType.COMPANION))
            action.appendEffect(
                    new OptionalEffect(action, playerId,
                            new PutPlayedEventIntoHandEffect(self) {
                                @Override
                                public String getText(LotroGame game) {
                                    return "Return " + GameUtils.getFullName(self) + " to your hand";
                                }
                            }));
        return action;
    }
}
