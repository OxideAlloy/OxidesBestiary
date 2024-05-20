package OxidesBestiaryMod.powers;

import OxidesBestiaryMod.BasicMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;

public class MonsterVenomPower extends AbstractPower {
    public static final String POWER_ID = BasicMod.makeID("MonsterVenomPower");
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public MonsterVenomPower(AbstractCreature owner) {
        this.name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("envenom");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

//    public void onAttack(DamageInfo info, AbstractCreature target) {
//        if (target != this.owner && info.type == DamageType.NORMAL) {
//            this.flash();
//            addToBot(new VenomAction(target, new DamageInfo(info), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
//
//
//        }
//    }

    public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && info.type == DamageType.NORMAL) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this.owner, new PlayerPoisonedPower(AbstractDungeon.player, this.owner, damageAmount)));

        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
