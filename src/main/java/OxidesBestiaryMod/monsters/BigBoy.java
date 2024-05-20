package OxidesBestiaryMod.monsters;

import OxidesBestiaryMod.BasicMod;
import OxidesBestiaryMod.powers.MonsterVenomPower;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;

import java.util.Iterator;

public class BigBoy extends AbstractMonster {
    public static final String ID = BasicMod.makeID("ShellBeast");
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 240;
    private static final int HP_MAX = 260;

    private static final int SLAM = 10;
    private static final byte SWING = 20;

    //BigBoy Animations
    //attack
    //fall
    //fall_idle
    //fall_slam
    //idle
    //slam

    public BigBoy(float x, float y) {
        super(NAME, "BigBoy", HP_MAX, 0.0F, 0.0F, 200.0F, 150.0F, (String)null, x, y);
        this.type = EnemyType.BOSS;
        this.setHp(HP_MIN, HP_MAX);
        this.damage.add(new DamageInfo(this, SLAM));
        this.damage.add(new DamageInfo(this, SWING));

        this.loadAnimation("OxidesBestiaryMod/images/monsters/BigBoy/skeleton.atlas", "OxidesBestiaryMod/images/monsters/BigBoy/skeleton.json", 2.0F);
        TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());

    }



    public void takeTurn() {

        label28:
        switch(this.nextMove) {
            //Damage all
            case 1:
            default:
                this.state.setAnimation(0, "slam", false);
                this.state.addAnimation(0, "idle", true, 0.0F);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_LIGHT));
                this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
                break;

            //Deal Damage
            case 2:
                this.state.setAnimation(0, "attack", false);
                this.state.addAnimation(0, "idle", true, 0.0F);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.BLUNT_HEAVY));
                this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);

        }
    }

    protected void getMove(int num) {
        this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
