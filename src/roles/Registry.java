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
    
    Role alphaWolf = new AlphaWolf();
    Role apprenticeSeer = new ApprenticeSeer();
    Role bodyguard = new Bodyguard();
    Role curator = new Curator();
    Role cursed = new Cursed();
    Role doppelganger = new Doppelganger();
    Role dreamWolf = new DreamWolf();
    Role drunk = new Drunk();
    Role hunter = new Hunter();
    Role insomniac = new Insomniac();
    Role mason = new Mason();
    Role minion = new Minion();
    Role mysticWolf = new MysticWolf();
    Role paranormalInvestigator = new ParanormalInvestigator();
    Role prince = new Prince();
    Role revealer = new Revealer();
    Role robber = new Robber();
    Role seer = new Seer();
    Role sentinel = new Sentinel();
    Role tanner = new Tanner();
    Role troublemaker = new Troublemaker();
    Role villageIdiot = new VillageIdiot();
    Role villager = new Villager();
    Role werewolf = new Werewolf();
    Role witch = new Witch();
    
}
