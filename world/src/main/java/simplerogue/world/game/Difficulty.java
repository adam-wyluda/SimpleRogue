package simplerogue.world.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Adam Wyłuda
 */
@AllArgsConstructor
public enum Difficulty
{
    EASY(1, 0.2), NORMAL(2, 0.3), HARD(3, 0.4);

    @Getter
    private final int monsterMultiplier;

    @Getter
    private final double monsterChance;
}
