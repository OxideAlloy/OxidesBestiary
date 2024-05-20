package OxidesBestiaryMod.monsters;

import OxidesBestiaryMod.BasicMod;
import OxidesBestiaryMod.powers.MonsterVenomPower;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.SnakeDagger;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import java.util.Iterator;

public class Buzzer extends AbstractMonster {
    public static final String ID = BasicMod.makeID("Buzzer");
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 8;
    private static final int HP_MAX = 12;

    private static final int STING_1 = 3;
    private static final byte STING_2 = 5;
    private static final int KAMIKAZE = 7;



    //ShellBeast Animations
    //idle
    //attack
    //hit

    //Buzzer Animations
    //idle
    //attack
    //hit

    //JawWolf Animations
    //idle
    //attack
    //hit

    //KingInYellow Animations
    //idle
    //attack
    //hit

    //BigBoy Animations
    //attack
    //fall
    //fall_idle
    //fall_slam
    //idle
    //slam

    public Buzzer(float x, float y) {
        super(NAME, "Buzzer", HP_MAX, 0.0F, 0.0F, 200.0F, 150.0F, (String)null, x, y);
        this.type = EnemyType.NORMAL;
        this.setHp(HP_MIN, HP_MAX);
        this.damage.add(new DamageInfo(this, this.STING_1));
        this.damage.add(new DamageInfo(this, this.STING_2));
        this.damage.add(new DamageInfo(this, this.KAMIKAZE));

        this.loadAnimation("OxidesBestiaryMod/images/monsters/Buzzer/skeleton.atlas", "OxidesBestiaryMod/images/monsters/Buzzer/skeleton.json", 3.0F);
        TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MonsterVenomPower(this)));
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "hit", false);
            this.state.addAnimation(0, "idle", true, 0.0F);
        }
    }

    public void takeTurn() {

        switch(this.nextMove) {
            //Sting 1
            case 1:
            default:
                this.state.setAnimation(0, "attack", false);
                this.state.addAnimation(0, "idle", true, 0.0F);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.POISON));
                this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
                break;

            //Sting 2
            case 2:
                this.state.setAnimation(0, "attack", false);
                this.state.addAnimation(0, "idle", true, 0.0F);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.POISON));
                this.setMove((byte)3, Intent.ATTACK, ((DamageInfo)this.damage.get(2)).base);

                break;

            //Kamikaze
            case 3:
                this.state.setAnimation(0, "attack", false);
                this.state.addAnimation(0, "idle", true, 0.0F);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(2), AttackEffect.POISON));
                //AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(2), AttackEffect.POISON));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this, this, this.currentHealth));
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
