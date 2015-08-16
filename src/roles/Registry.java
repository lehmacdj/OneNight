package roles;

/**
 * @author Devin Lehmacher <lehmacdj@gmail.com>
 */
public class Registry {
    
    //A singleton structure
    private static Registry instance = null;
    public static Registry getInstance() {
        if (instance ==null) {
            return instance = new Registry();
        } else {
            return instance;
        }
    }
    
    public final Role alphaWolf = new AlphaWolf();
    public final Role apprenticeSeer = new ApprenticeSeer();
    public final Role auraSeer = new AuraSeer();
    public final Role bodyguard = new Bodyguard();
    public final Role curator = new Curator();
    public final Role cursed = new Cursed();
    public final Role doppelganger = new Doppelganger();
    public final Role dreamWolf = new DreamWolf();
    public final Role drunk = new Drunk();
    public final Role hunter = new Hunter();
    public final Role insomniac = new Insomniac();
    public final Role mason = new Mason();
    public final Role minion = new Minion();
    public final Role mysticWolf = new MysticWolf();
    public final Role paranormalInvestigator = new ParanormalInvestigator();
    public final Role prince = new Prince();
    public final Role revealer = new Revealer();
    public final Role robber = new Robber();
    public final Role seer = new Seer();
    public final Role sentinel = new Sentinel();
    public final Role tanner = new Tanner();
    public final Role troublemaker = new Troublemaker();
    public final Role villageIdiot = new VillageIdiot();
    public final Role villager = new Villager();
    public final Role werewolf = new Werewolf();
    public final Role witch = new Witch();
    
}
