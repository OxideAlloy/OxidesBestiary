package OxidesBestiaryMod.powers;

import OxidesBestiaryMod.BasicMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;


public class PlayerPoisonedPower extends AbstractPower {
    public static final String POWER_ID = BasicMod.makeID("PlayerPoisonedPower");
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCreature source;

    public PlayerPoisonedPower(AbstractCreature owner, AbstractCreature source, int poisonAmt) {
        this.name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.amount = poisonAmt;
        this.updateDescription();
        this.loadRegion("poison");
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_POISON", 0.05F);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }


    public void atEndOfTurn(boolean isPlayer) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flashWithoutSound();
            this.playApplyPowerSfx();
            this.addToBot(new LoseHPAction(this.owner, this.owner, this.amount, AbstractGameAction.AttackEffect.POISON));
            if (this.amount == 0) {
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            } else {
                this.addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
            }

            }
        }


    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
