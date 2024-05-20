package OxidesBestiaryMod.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static OxidesBestiaryMod.BasicMod.makeID;


public class BrokenClockRelic extends BaseRelic {
    private static final String NAME = "BrokenClockRelic"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    private static final int DRAW_DOWN = 2;
    private static final int ENERGY_DOWN = 1;

    public BrokenClockRelic() { super(ID, NAME, RARITY, SOUND); }

    public void atBattleStart() {
        this.grayscale = true;
    }

    public void onPlayerEndTurn() {
        if (!this.grayscale) {
            this.grayscale = true;
        } else {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new SkipEnemiesTurnAction());
            this.grayscale = false;
        }
    }

    public void onVictory() {
        this.grayscale = false;
    }

    public void onEquip() {
        AbstractPlayer var10000 = AbstractDungeon.player;
        var10000.masterHandSize -= DRAW_DOWN;
        AbstractDungeon.player.energy.energyMaster -= ENERGY_DOWN;
    }

    public void onUnequip() {
        AbstractPlayer var10000 = AbstractDungeon.player;
        var10000.masterHandSize += DRAW_DOWN;
        AbstractDungeon.player.energy.energyMaster += ENERGY_DOWN;
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + ENERGY_DOWN + DESCRIPTIONS[1] + DRAW_DOWN + DESCRIPTIONS[2];
    }
}