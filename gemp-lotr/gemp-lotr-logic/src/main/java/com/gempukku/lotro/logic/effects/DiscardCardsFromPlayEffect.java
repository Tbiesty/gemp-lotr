package com.gempukku.lotro.logic.effects;

import com.gempukku.lotro.common.Filterable;
import com.gempukku.lotro.common.Zone;
import com.gempukku.lotro.filters.Filter;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.GameState;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.GameUtils;
import com.gempukku.lotro.logic.timing.Effect;
import com.gempukku.lotro.logic.timing.results.DiscardCardsFromPlayResult;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DiscardCardsFromPlayEffect extends AbstractPreventableCardEffect {
    private PhysicalCard _source;
    private String _performingPlayer;

    public DiscardCardsFromPlayEffect(String performingPlayer, PhysicalCard source, Filterable... filters) {
        super(filters);
        _performingPlayer = performingPlayer;
        _source = source;
    }

    public PhysicalCard getSource() {
        return _source;
    }

    @Override
    protected Filter getExtraAffectableFilter() {
        if (_source == null)
            return Filters.any;
        return Filters.canBeDiscarded(_source);
    }

    public String getPerformingPlayer() {
        return _performingPlayer;
    }

    @Override
    public Effect.Type getType() {
        return Type.BEFORE_DISCARD_FROM_PLAY;
    }


    @Override
    public String getText(LotroGame game) {
        Collection<PhysicalCard> cards = getAffectedCardsMinusPrevented(game);
        return "Discard " + getAppendedTextNames(cards);
    }

    protected void forEachDiscardedByEffectCallback(Collection<PhysicalCard> discardedCards) {

    }

    @Override
    protected void playoutEffectOn(LotroGame game, Collection<PhysicalCard> cards) {
        Set<PhysicalCard> discardedCards = new HashSet<PhysicalCard>();

        Set<PhysicalCard> toMoveFromZoneToDiscard = new HashSet<PhysicalCard>();

        GameState gameState = game.getGameState();

        DiscardUtils.cardsToChangeZones(game, cards, discardedCards, toMoveFromZoneToDiscard);

        discardedCards.addAll(cards);
        toMoveFromZoneToDiscard.addAll(cards);

        String sourcePlayer = null;
        if (_source != null)
            sourcePlayer = _source.getOwner();

        gameState.removeCardsFromZone(sourcePlayer, toMoveFromZoneToDiscard);

        for (PhysicalCard card : toMoveFromZoneToDiscard)
            gameState.addCardToZone(game, card, Zone.DISCARD);

        if (_source != null && discardedCards.size() > 0)
            game.getGameState().sendMessage(_source.getOwner() + " discards " + getAppendedNames(discardedCards) + " from play using " + GameUtils.getCardLink(_source));

        for (PhysicalCard discardedCard : discardedCards)
            game.getActionsEnvironment().emitEffectResult(new DiscardCardsFromPlayResult(_source, getPerformingPlayer(), discardedCard));

        forEachDiscardedByEffectCallback(cards);
    }
}
