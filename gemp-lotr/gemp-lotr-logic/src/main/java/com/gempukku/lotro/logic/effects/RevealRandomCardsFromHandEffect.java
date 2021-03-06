package com.gempukku.lotro.logic.effects;

import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.GameUtils;
import com.gempukku.lotro.logic.PlayOrder;
import com.gempukku.lotro.logic.decisions.ArbitraryCardsSelectionDecision;
import com.gempukku.lotro.logic.decisions.DecisionResultInvalidException;
import com.gempukku.lotro.logic.timing.AbstractEffect;
import com.gempukku.lotro.logic.timing.Effect;
import com.gempukku.lotro.logic.timing.results.RevealCardFromHandResult;

import java.util.Collections;
import java.util.List;

public abstract class RevealRandomCardsFromHandEffect extends AbstractEffect {
    private PhysicalCard _source;
    private int _count;
    private String _actingPlayer;
    private String _playerHand;

    protected RevealRandomCardsFromHandEffect(String actingPlayer, String handOfPlayer, PhysicalCard source, int count) {
        _actingPlayer = actingPlayer;
        _playerHand = handOfPlayer;
        _source = source;
        _count = count;
    }

    @Override
    public String getText(LotroGame game) {
        return "Reveal cards from hand";
    }

    @Override
    public Effect.Type getType() {
        return null;
    }

    @Override
    protected FullEffectResult playEffectReturningResult(LotroGame game) {
        if (_actingPlayer.equals(_playerHand) || game.getModifiersQuerying().canLookOrRevealCardsInHand(game, _playerHand, _actingPlayer)) {
            List<PhysicalCard> randomCards = GameUtils.getRandomCards(game.getGameState().getHand(_playerHand), _count);

            if (randomCards.size() > 0) {
                final PlayOrder playerOrder = game.getGameState().getPlayerOrder().getCounterClockwisePlayOrder(_source.getOwner(), false);

                String nextPlayer;
                while ((nextPlayer = playerOrder.getNextPlayer()) != null) {
                    game.getUserFeedback().sendAwaitingDecision(nextPlayer,
                            new ArbitraryCardsSelectionDecision(1, _playerHand+" revealed card(s) from hand at random", randomCards, Collections.<PhysicalCard>emptySet(), 0, 0) {
                                @Override
                                public void decisionMade(String result) throws DecisionResultInvalidException {
                                }
                            });
                }

                game.getGameState().sendMessage(GameUtils.getCardLink(_source) + " revealed cards from " + _playerHand + " hand at random - " + getAppendedNames(randomCards));
            }
            cardsRevealed(randomCards);
            for (PhysicalCard randomCard : randomCards)
                game.getActionsEnvironment().emitEffectResult(new RevealCardFromHandResult(_source, _playerHand, randomCard));

            return new FullEffectResult(randomCards.size() == _count);
        }
        return new FullEffectResult(false);
    }

    @Override
    public boolean isPlayableInFull(LotroGame game) {
        if (game.getGameState().getHand(_playerHand).size() < _count)
            return false;
        return _actingPlayer.equals(_playerHand) || game.getModifiersQuerying().canLookOrRevealCardsInHand(game, _playerHand, _actingPlayer);
    }

    protected abstract void cardsRevealed(List<PhysicalCard> revealedCards);
}
