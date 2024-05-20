package OxidesBestiaryMod.monsters;

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
import com.megacrit.cardcrawl.powers.PainfulStabsPower;

public class TestSphere extends AbstractMonster {
    public static final String ID = "TestSphere";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 10;
    private static final int HP_MAX = 12;

    private static final int BLOCK_AMT = 5;
    private static final byte DAMAGE_AMT = 10;

    private static final int SUMMON_AMT = 3;
    private AbstractMonster[] daggers = new AbstractMonster[4];


    //TestSphere Animations
    //animation0
    //animation1


    public TestSphere(float x, float y) {
        super(NAME, "TestSphere", HP_MAX, 0.0F, 0.0F, 200.0F, 150.0F, (String)null, x, y);
//      (String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY) {
//      this(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY, false);

        this.type = EnemyType.ELITE;

        this.setHp(HP_MIN, HP_MAX);

        this.damage.add(new DamageInfo(this, DAMAGE_AMT));



        this.loadAnimation("OxidesBestiaryMod/images/monsters/TestSphere/skeleton.atlas", "OxidesBestiaryMod/images/monsters/TestSphere/skeleton.json", 1.0F);
        TrackEntry e = this.state.setAnimation(0, "animation0", true);
        e.setTime(e.getEndTime() * MathUtils.random());

    }

    public void takeTurn() {
        switch(this.nextMove) {

            //Gain Block
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "animation1"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, BLOCK_AMT));
                this.setMove((byte)2, Intent.ATTACK);
                break;

            //Deal Damage
            case 2:
                this.state.setAnimation(0, "animation1", false);
                this.state.addAnimation(0, "animation0", true, 0.0F);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_HEAVY));
                this.setMove((byte)3, Intent.DEFEND);

        }
    }

    protected void getMove(int num) {
        //18 - Elites have more challenging movesets and abilities. (NOT IMPLEMENTED)
        this.setMove((byte)1, Intent.DEFEND);
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("OxidesBestiary:TestSphere");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
