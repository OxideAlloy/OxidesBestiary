package OxidesBestiaryMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class VenomAction extends AbstractGameAction {
    private DamageInfo info;

    public VenomAction(AbstractCreature target, DamageInfo info, AttackEffect effect) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
    }

    public void update() {
        if (this.duration == 0.5F) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        }

        this.tickDuration();
        if (this.isDone) {
            this.target.damage(this.info);
            if (this.target.lastDamageTaken > 0) {
                this.addToTop(new ApplyPowerAction(target, this.target, new PoisonPower(target, this.target, this.target.lastDamageTaken), this.target.lastDamageTaken, true));
                this.addToTop(new WaitAction(0.1F));
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

    }
}
