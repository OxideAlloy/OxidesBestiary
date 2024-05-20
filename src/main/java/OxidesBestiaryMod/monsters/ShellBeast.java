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
import com.megacrit.cardcrawl.powers.FrailPower;

import java.util.Iterator;

public class ShellBeast extends AbstractMonster {
    public static final String ID = BasicMod.makeID("ShellBeast");
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 75;
    private static final int HP_MAX = 85;

    private static final int BLOCK_AMT = 10;
    private static final byte BITE_DAMAGE = 10;
    private static final int FRAIL_AMT = 1;

    public static final float[] POSX;
    public static final float[] POSY;
    private static final int SUMMON_AMT = 3;
    private AbstractMonster[] buzzers = new AbstractMonster[SUMMON_AMT];
    //public AbstractMonster[] gremlins = new AbstractMonster[3];
    //private HashMap<Integer, AbstractMonster> enemySlots = new HashMap();

    private boolean firstMove;
    private boolean justAttacked;


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

    public ShellBeast(float x, float y) {
        super(NAME, "ShellBeast", HP_MAX, 0.0F, 0.0F, 200.0F, 150.0F, (String)null, x, y);
        this.firstMove = true;
        this.justAttacked = false;
        this.type = EnemyType.ELITE;
        this.setHp(HP_MIN, HP_MAX);
        this.damage.add(new DamageInfo(this, BITE_DAMAGE));

        this.loadAnimation("OxidesBestiaryMod/images/monsters/ShellBeast/skeleton.atlas", "OxidesBestiaryMod/images/monsters/ShellBeast/skeleton.json", 2.0F);
        TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());

    }

    public void usePreBattleAction() {
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, BLOCK_AMT)));
//        Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();
//
//        while(var1.hasNext()) {
//            AbstractMonster m = (AbstractMonster)var1.next();
//            if (!m.id.equals(this.id)) {
//                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new MinionPower(this)));
//            }
//
//            if (m instanceof SnakeDagger) {
//                if (AbstractDungeon.getMonsters().monsters.indexOf(m) > AbstractDungeon.getMonsters().monsters.indexOf(this)) {
//                    this.daggers[0] = m;
//                } else {
//                    this.daggers[1] = m;
//                }
//            }
//        }

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, BLOCK_AMT));
    }

    public void takeTurn() {
//        Iterator var1;
//        AbstractMonster m;
//        int key;
//        TorchHead newMonster;
        label28:
        switch(this.nextMove) {
            //Summon
            case 1:
                //AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, BLOCK_AMT));
                //this.firstMove = false;


                Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
                int buzzersSpawned = 0;
                int i = 0;


                while(var2.hasNext()) {
                    AbstractMonster m = (AbstractMonster)var2.next();
                    if (m != this && !m.isDying) {
                        ++buzzersSpawned;
                    }
                }


                while(true) {
                    if (buzzersSpawned >= this.SUMMON_AMT || i >= this.buzzers.length) {
                        this.setMove((byte)2, Intent.ATTACK_DEFEND, ((DamageInfo)this.damage.get(0)).base);
                        break label28;
                    }

                    if (this.buzzers[i] == null || this.buzzers[i].isDeadOrEscaped()) {
                        Buzzer buzzersToSpawn = new Buzzer(POSX[i], POSY[i]);
                        this.buzzers[i] = buzzersToSpawn;
                        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(buzzersToSpawn, true));
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.buzzers[i], this, new MonsterVenomPower(this.buzzers[i])));

                        ++buzzersSpawned;
                    }

                    ++i;
                }

                //break;

            //Deal Damage + Block
            case 2:
            default:
                this.state.setAnimation(0, "attack", false);
                this.state.addAnimation(0, "idle", true, 0.0F);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, BLOCK_AMT));
                this.setMove((byte)3, Intent.DEFEND_DEBUFF);
                break;

            //Inflict Frail + Block
            case 3:
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, FRAIL_AMT, true), FRAIL_AMT));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, BLOCK_AMT));
                this.setMove((byte)2, Intent.ATTACK_DEFEND);


            //Summon 1 (Don't Block)
//            case 4:
//                //AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "SUMMON"));
//                this.justAttacked = false;
//                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));

//SUMMON CODE HERE



        }
        //AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }


    private boolean canSpawn() {
        int aliveCount = 0;
        Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();

        while(var2.hasNext()) {
            AbstractMonster m = (AbstractMonster)var2.next();
            if (m != this && !m.isDying) {
                ++aliveCount;
            }
        }

        if (aliveCount >= SUMMON_AMT) {
            return false;
        } else {
            return true;
        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "hit", false);
            this.state.addAnimation(0, "idle", true, 0.0F);
            //System.out.println("This monsters name is: " + this.id);
            //this.addToBot(new TalkAction(true, "@RRrroohrrRGHHhhh!!@", 1.5F, 1.5F));
            this.setMove((byte)1, Intent.UNKNOWN);
            this.createIntent();
        }






//        super.damage(info);
//        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
//            this.state.setAnimation(0, "hit", false);
//            this.state.addAnimation(0, "idle", true, 0.0F);
//            this.setMove((byte)4, Intent.UNKNOWN);
//            //this.justAttacked = true;
//        }
    }

    public void changeState(String stateName) {
        byte var3 = -1;
        switch(stateName.hashCode()) {
            case 1280154047:
                if (stateName.equals("ARMOR_BREAK")) {
                    var3 = 1;
                }
                break;
            case 1941037640:
                if (stateName.equals("ATTACK")) {
                    var3 = 0;
                }
        }

        switch(var3) {
            case 0:
                this.state.setAnimation(0, "Attack", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                this.setMove((byte)4, Intent.STUN);
                this.createIntent();
        }

    }

    protected void getMove(int num) {
        this.setMove((byte)1, Intent.UNKNOWN);


//        if (this.firstMove) {
//            //1st move
//            this.firstMove = false;
//        this.setMove((byte)1, Intent.UNKNOWN);
//    } else if (this.justAttacked) {
//        //Summon when attacked
//        this.setMove((byte)4, Intent.UNKNOWN);
//    } else if (num <40) {
//        //40% chance inflict frail
//        this.setMove((byte) 3, Intent.DEFEND_DEBUFF );
//    } else {
//        //60% chance attack
//        this.setMove((byte)2, Intent.ATTACK_DEFEND , ((DamageInfo)this.damage.get(0)).base);
//    }

//        System.out.println(num);
//        System.out.println(this.firstMove);
//        if (this.firstMove) {
//            //1st move
//            this.firstMove = false;
//            this.setMove((byte)1, Intent.DEFEND);
//        } else {
//            //attack
//            this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
//        }

    }


    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        POSX = new float[]{260.0F, 50.0F, -180.0F};
        POSY = new float[]{280.0F, 350.0F, 300.0F};
    }
}
