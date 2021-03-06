package com.gempukku.lotro.cards.set2.moria;

import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractEvent;
import com.gempukku.lotro.logic.effects.AddUntilEndOfPhaseModifierEffect;
import com.gempukku.lotro.logic.effects.ChooseActiveCardEffect;
import com.gempukku.lotro.logic.modifiers.StrengthModifier;

/**
 * Set: Mines of Moria
 * Side: Shadow
 * Culture: Moria
 * Twilight Cost: 0
 * Type: Event
 * Game Text: Skirmish: Make an Elf or Dwarf skirmishing a [MORIA] Orc strength -1 (or -3 if you spot an Elf and
 * a Dwarf).
 */
public class Card2_069 extends AbstractEvent {
    public Card2_069() {
        super(Side.SHADOW, 0, Culture.MORIA, "Old Differences", Phase.SKIRMISH);
    }

    @Override
    public PlayEventAction getPlayEventCardAction(String playerId, final LotroGame game, final PhysicalCard self) {
        final PlayEventAction action = new PlayEventAction(self);
        action.appendEffect(
                new ChooseActiveCardEffect(self, playerId, "Choose Elf or Dwarf",
                        Filters.or(Race.ELF, Race.DWARF),
                        Filters.inSkirmishAgainst(Culture.MORIA, Race.ORC)) {
                    @Override
                    protected void cardSelected(LotroGame game, PhysicalCard elfOrDwarf) {
                        boolean canSpotElf = Filters.canSpot(game, Race.ELF);
                        boolean canSpotDwarf = Filters.canSpot(game, Race.DWARF);
                        int penalty = (canSpotElf && canSpotDwarf) ? -3 : -1;
                        action.insertEffect(
                                new AddUntilEndOfPhaseModifierEffect(
                                        new StrengthModifier(self, Filters.sameCard(elfOrDwarf), penalty)));
                    }
                });
        return action;
    }
}
