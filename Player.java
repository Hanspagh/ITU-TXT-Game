
/**
 * An instance of the player class controls the players status
 * 
 * @author (Phillip P.) 
 * @version (1.0.2)
 */
public class Player
{
    // instance variables - replace the example below with your own
    private String name;    //Name
    private int hp;         //HP
    private int hp_max;    //Max HP
    private int level;      //Level
    private int level_pnt;  //Level points
    private int xp;         //Xp
    private int att;        //Attack points
    private double att_mod;    //Attack modifier. The value witch the attack will go up with each level
    private double att_crit;   //Attack crit chance. The chance to get a critical hit. Is described in percent %.
    private boolean isFighting;  //Bool showing if the player is already against a mob
    private Monster monster;    //The monster the player is fighting

    /**
     * Constructor for objects of class Player
     */
    public Player(String _Name)
    {
        // initialise instance variables
        name = _Name;
        hp = 10;
        hp_max = 10;
        level = 1;
        level_pnt = 5;
        xp = 0;
        att = 1;
        att_mod = 1;
        att_crit = 0.00;
        isFighting = false;
        
        System.out.println("");
        System.out.println("##GAME RULES##");
        System.out.println("The goal of the game is to defeat monsters and progress in level.");
        System.out.println("When you want to encounter a monster, use the encMonster() method.");
        System.out.println("Then attack the monster with the attMonster() method until the monster (or the player) dies.");
        System.out.println("When a monster dies, you will recieve XP");
        System.out.println("When the player has 5 XP they level up and gain some level points.");
        System.out.println("These can be used to improve the players HP, attack points and critical hit chance.");
        System.out.println("Enjoy!");
        
        System.out.println("");
        System.out.println("##GAME START##");
        System.out.println("Welcome " + name + "!");
        System.out.println("Your stats are:");
        System.out.println("HP: " + hp);
        System.out.println("Max HP: " + hp_max);
        System.out.println("Level: " + level);
        System.out.println("Level points: " + level_pnt);
        System.out.println("XP: " + xp);
        System.out.println("Attack points: " + att);
        System.out.println("Attack modifier: " + att_mod);
        System.out.println("Chance of critical hit: " + (att_crit * 100) + " %");
        System.out.println("");
    }
    
    /**
     * Spend level points
     * You can spend them on 3 types of advantages.
     * Type 0 to gain extra HP
     * Type 1 to gain extra attack modifier, which enlarges your attack
     * Type 2 to enlarge you chances of getting critical hit
     */
    public void levelPointSpend(int type)
    {
        if (level_pnt > 0)
        {
            switch(type)
            {
                case 0:
                    hp_max += 3;
                    hp = hp_max;
                    System.out.println("Max HP: " + hp_max);
                    System.out.println("HP restored. HP: " + hp);
                    printSkillIncrease();
                    break;
                case 1:
                    att_mod += 0.25;
                    System.out.println("Attack modification is now: " + att_mod);
                    printSkillIncrease();
                    break;
                    case 2:
                        if (att_crit < 1)
                        {
                            att_crit += 0.05;
                            System.out.println("Your critical hit chance has been improved!");
                            System.out.println("Critical hit chance is now: " + (att_crit * 100) + " %");
                            printSkillIncrease();
                        }
                        else
                        {
                            att_crit = 1;
                            System.out.println("The critical hit chance has been maxed out. 100 %.");
                        }
                        break;
                default:
                    System.out.println("Unable to increase skill. Use numbers 0, 1 or 2.");
                    System.out.println("");
                    break;
            }
        }
        else
        {
            System.out.println("");
            System.out.println("You do not have any level points!");
            System.out.println("");
        }
    }
    
    /**
     * Method called when a skill is increased
     */
    private void printSkillIncrease()
    {
        level_pnt -= 1;
        //System.out.println("");
        System.out.println("You have spend 1 level point.");
        System.out.println("You now have " + level_pnt + " level points remaining!");
        System.out.println("");
    }
            
    /**
     * Encounter a monster
     */
    public void encMonster()
    {
        if(!isFighting)
        {
        monster = new Monster();
        monster.level = (level + (int)(Math.random() * 3));
        monster.hp = (5 + monster.level);
        monster.att = (level + (int)(Math.random() * 3));
        monster.att_mod = 1;
        monster.att_crit = 0.02;
        isFighting = true;
        
        System.out.println("##MONSTER##");
        System.out.println("You have encountered a monster!");
        System.out.println("The monsters level: " + monster.level);
        System.out.println("The monsters HP: " + monster.hp);
        System.out.println("The monsters attack points: " + monster.att);
        System.out.println("");
    }
    else
    {
        System.out.println("You are already in a fight!");
        
        System.out.println("##MONSTER##");
        System.out.println("You have encountered a monster!");
        System.out.println("The monsters level: " + monster.level);
        System.out.println("The monsters HP: " + monster.hp);
        System.out.println("The monsters attack points: " + monster.att);
        System.out.println("");
    }
    }
    
    /**
     * Attack monster
     */
    public void attMonster()
    {
        if (isFighting)
        {
            //Calculate attack damage from attack points plus some random
            int playerAttack; int monsterAttack;
            playerAttack = (int)(att * att_mod + (int)(Math.random() * 3));
            monsterAttack = (int)(monster.att * monster.att_mod + (int)(Math.random() * 3));
            //Add critical strike chance and extra damage if it is succesfull
            if (Math.random() < att_crit)
            {
                playerAttack += (int)(att/2);
                System.out.println("The monster got hit by a Critical Hit!");
            }
            
            if (monster.hp > playerAttack)
            {
                //Let the monster loose life equal to the attack damage
                monster.hp -= playerAttack;
                System.out.println("You damaged the monster for " + playerAttack + " points");
                System.out.println("The monster now has " + monster.hp + " HP");
                
                //Let the player loose life, when the monster attacks back
                if (hp > monsterAttack)
                {
                    hp -= monsterAttack;
                    System.out.println("The monster damaged you for " + monsterAttack + " points");
                    System.out.println("You now have " + hp + " HP");
                }
                else
                {
                    System.out.println("The monster damaged you for " + monsterAttack + " points");
                    System.out.println("You now have less than 1 HP. The game is over!");
                    System.out.println("");
                    System.out.println("###############");
                    System.out.println("###GAME OVER###");
                    System.out.println("###############");
                    System.out.println("");
                    System.out.println("##THE GAME HAS RESTARTED##");
                    System.out.println("");
                    
                    // Restart the game by reseting all values
                    hp = 10;
                    level = 1;
                    level_pnt = 5;
                    xp = 0;
                    att = 1;
                    att_mod = 1;
                    att_crit = 0.01;
                    
                    isFighting = false;
                    
                    System.out.println("##GAME START##");
                    System.out.println("Player start values");
                    System.out.println("HP: " + hp);
                    System.out.println("Level: " + level);
                    System.out.println("XP: " + xp);
                    System.out.println("Attack points: " + att);
                    System.out.println("Attack modifier: " + att_mod);
                    System.out.println("Chance of critical hit: " + (att_crit * 100) + " %");
                }
            }
            else
            {
                //Gain one xp
                xp += 1;
                hp += 2;
                //Kill mob
                isFighting = false;
                System.out.println("");
                System.out.println("Monster defeated! Gained 1 XP");
                System.out.println("You also gained 2 HP");
                //If player has enough XP to level up
                if (xp == 5)
                {
                    //Level up if player has enough XP
                    //When leveling up the player gains 1 level, 1 level point and one attack point. The xp is set to 0.
                    level += 1;
                    xp = 0;
                    level_pnt += 3;
                    att += 1;
                    
                    System.out.println("You leveled up and gained a level point to spend");
                    System.out.println("Current level: " + level);
                    System.out.println("Current attack: " + att);
                    System.out.println("Current number of level points: " + level_pnt);
                }
                System.out.println("Current XP: " + xp);
                System.out.println("");
            }
        }
        else
        {
            System.out.println("No monster encountered yet");
            System.out.println("");
        }
    }
}