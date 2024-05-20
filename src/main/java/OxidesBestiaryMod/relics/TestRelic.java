package OxidesBestiaryMod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static OxidesBestiaryMod.BasicMod.makeID;


public class TestRelic extends BaseRelic {
    private static final String NAME = "TestRelic"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    private static final int STRENGTH = 10; //For convenience of changing it later and clearly knowing what the number means instead of just having a 10 sitting around in the code.

    public TestRelic() {
        super(ID, NAME, RARITY, SOUND);
    }


    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, STRENGTH)));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + STRENGTH + DESCRIPTIONS[1];
    }

    // Take advantage of autocomplete!
    // If you type "public onUse" IntelliJ should already have the method in the suggestions.
    // Use the up/down arrows to select it and press enter to automatically create this whole chunk.
    // This autocomplete is also a good way to see all the hooks/look for the right hook by name, by just typing "publi"
}