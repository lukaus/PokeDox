/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainWindow;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Lukaus
 */
public final class Pokemon implements Comparable<Pokemon>{
    static BufferedImage masterImg;
    private int natlDex;
    private int johtoDex;
    private int hoennDex;
    private int sinnohDex;
    private int unovaDex;
    private int kalosDex;
    private int comparator; 

    private String name;
    private int type1;
    private int type2;
    ArrayList<Object> dataSet;
    private boolean caught;
    private boolean seen;
    private boolean want;
    private boolean trade;
    private int multiples;
    
    private int evoFamily;
    
    
    
    String[] types = {"", "NRM", "FGT", "FLY", "PSN", "GRD", "RCK", "BUG", "GHT", "STL", "FIR", "WTR", "GRS", "ELC", "PSY", "ICE", "DRG", "DRK", "FRY"};
    
    Pokemon()
    {
        dataSet = new ArrayList<Object>();
        natlDex = 1; 
        johtoDex = 2; 
        hoennDex = 3; 
        sinnohDex = 4; 
        unovaDex = 5; 
        kalosDex = 6;
        name = "Pikashoo"; 
        type1 = 1; 
        type2 = 2;
        caught = true; 
        want = true; 
        seen = true; 
        trade = true; 
        evoFamily = -999; 
        multiples = 1;
        System.out.println("This should NEVER be called");
        //shouldn't be called
    }
    Pokemon(Pokemon p)
    {
        dataSet = new ArrayList<Object>();
        setType1(p.getType1());
        setType2(p.getType2());
        dataSet.add(p.getNatlDex());      // add this to a ArrayList(for the JTable)
        dataSet.add(p.getName());
        dataSet.add(getType());
        dataSet.add(p.getJohtoDex());
        dataSet.add(p.getHoennDex());
        dataSet.add(p.getSinnohDex());
        dataSet.add(p.getUnovaDex());
        dataSet.add(p.getKalosDex());
 
        dataSet.add(p.isCaught());
        dataSet.add(p.isSeen());
        dataSet.add(p.isWant());
        dataSet.add(p.isTrade());
        dataSet.add(p.getMultiples());
        dataSet.add(p.getEvoFamily());
        
        
        setNatlDex(p.getNatlDex());      // Set appropriate fields
        setJohtoDex(p.getJohtoDex());
        setHoennDex(p.getHoennDex());
        setSinnohDex(p.getSinnohDex());
        setUnovaDex(p.getUnovaDex());
        setKalosDex(p.getKalosDex());
        setName(p.getName());
        

        setCaught(p.isCaught());
        setSeen(p.isSeen());
        setWant(p.isWant());
        setTrade(p.isTrade());
        setMultiples(p.getMultiples());
        setEvoFamily(p.getEvoFamily());
        
    }
    
    Pokemon(int n, int j, int h, int s, int u, int k, String nm, int t1, int t2, boolean ca, boolean se, boolean wa, boolean tr, int mu, int ef)
    {   
        dataSet = new ArrayList<Object>();
        setType1(t1);
        setType2(t2);
        dataSet.add(n);      // add this to a ArrayList(for the JTable)
        dataSet.add(nm);
        dataSet.add(getType());
        dataSet.add(j);
        dataSet.add(h);
        dataSet.add(s);
        dataSet.add(u);
        dataSet.add(k);
 
        dataSet.add(ca);
        dataSet.add(se);
        dataSet.add(wa);
        dataSet.add(tr);
        dataSet.add(mu);
        dataSet.add(ef);
        
        
        setNatlDex(n);      // Set appropriate fields
        setJohtoDex(j);
        setHoennDex(h);
        setSinnohDex(s);
        setUnovaDex(u);
        setKalosDex(k);
        setName(nm);
        

        setCaught(ca);
        setSeen(se);
        setWant(wa);
        setTrade(tr);
        setMultiples(mu);
        setEvoFamily(ef);
        
        // Calculate BufferedImage
    //  /*  
        
    //  */  
          }
    public int getComparator() {
        return comparator;
    }

    public void setComparator(int c) {
        /* comparator int will determine which to sort by, 0 will indicate not to sort. 
         * If already set to sort by given comparator, will set comparator negative
         * If negative, will sort decending. if already set to negative comparator, will set 0 and sort ascending national dex
        */
        if (this.comparator < 0)
        {
            this.comparator = 0;
        }
        if (this.comparator > 0 && this.comparator == c)
        {
            this.comparator = (c * -1);
        }
        if (this.comparator > 0 && this.comparator != c)
        {
            this.comparator = c;
        }
        if (this.comparator == 0)
        {
            this.comparator = c;
        }
        
    }
    boolean includeQuery(boolean[] filters) // checks pokemon against filters and returns to PokeList if it should be included or not (false = do not include)
    {   
        /* filters format:
        kanto       johto   hoenn           sinnoh      unova       kalos 
        caught(6)      want(7)    seen(8)            multiples(9)   dontHave(10)    trade(11) 
        
        normal(1)   fire(10)    fighting(2)     water(11)        
        flying(3)   grass(12)   poison(4)       electric(13)
        steel(9)    fairy(18)   ground(5)       psychic(14) 
        rock(6)     ice(15)     bug(7)          dragon(16)
        ghost(8)    dark(17)
        types:
        "NRM", "FGT", "FLY", "PSN",
        "GRD", "RCK", "BUG", "GHT",
        "STL", "FIR", "WTR", "GRS",
        "ELC", "PSY", "ICE", "DRG",
        "DRK", "FRY"
        strictMatch, singleOnly, dualOnly, evoFamilyOnly
        /*
        ...  check against filters
        */
        if(filters[31] == true)
        {
            if(this.getType2() != 0)
                return false;
        }
        
        if(filters[32] == true)
        {
            if(this.getType2() == 0)
                return false;
        }

        
        if(filters[getType1() + 11] == true)    // check if type is permitted
        {
            if(filters[30] == true)     // check if we are using strict type matching
            {
                if((getType2() != 0) && filters[getType2() + 11] == false)   // if so, check if type2 is permitted
                    return false;   // if not, FAIL
            }
            // passes typecheck
        }
        else        // if type1 doesn't pass
        {
            if(filters[30] == true)
                return false;
            if(getType2() == 0) // is there a type 2?
                return false;   // no? then FAIL
            if(filters[getType2() + 11] == false) // yes? does it match true?
                return false;   // no? FAIL   otherwise, continue
        }
        
        if(filters[6] == false )
        {
            if(caught)
                return false;
        }
        if(filters[7] == true && !want)
        {
             if(!want)
                return false;
        }
        if(filters[8] == true )
        {
            if(!seen)
                return false;
        }
        if(filters[9] == true)
        {
             if( !(multiples > 0))
                return false;
        }
        if(filters[11] == true )
        {
            if(!trade)
                return false;
        }
        /**/
        
        if(filters[0] == true)   // kanto check  (if Kanto is to be included and in Kanto dex)
        {
            if(natlDex <= 151)
                return true;
        }
        else
        {
            if(natlDex <= 151)
                return false;
        }
        
        if(filters[1] == true)    // johto check
        {
            if(johtoDex > 0)
            {
                return true;
            }
        }
        else
        {
            if(johtoDex > 0)
            {
                return false;
            }
        }
            
        if(filters[2] == true)    // hoenn check
        {
            if((hoennDex > 0))
                return true;
        }       
        else
        {
            if((hoennDex > 0))
                return false;
        }
        
        if(filters[3] == true)    // sinnoh check
        {
            if((sinnohDex > 0))
                return true;
        }       
        else
        {
            if((sinnohDex > 0))
                return false;
        }
        
        if(filters[4] == true)    // unova check, 0 included for Victini
        {
            if((unovaDex >= 0))
                return true;
        }       
        else
        {
            if((unovaDex >= 0))
                return false;
        }
        
        if(filters[5] == true)    // kalos check
        {
            if((kalosDex > 0))
                return true;
        }       
        else
        {
            if((kalosDex > 0))
                return false;
        }
      /*  

        
        
        

        */
        
        
        return true;
    }
    
    String getType()    // returns properly formatted 
    {
        return getTypeStr(getType1()) + "/" + getTypeStr(getType2());        
    }
    
    String getTypeStr(int n)        // takes an int
    {                        // returns string associated typewise with that int
        return types[n];  
    }
    
    ArrayList<Object> getData()
    {
        return dataSet;
    }
    String getSaveData()
    {
        String data = getNatlDex() + " " + getJohtoDex() + " " + getHoennDex() + " " + getSinnohDex() +
                " " + getUnovaDex() + " " + getKalosDex() + " " + getName() + " " + getType1() + " " + getType2() + " ";
        
          
        if(isCaught())
        {
            data += "1 ";
        }
        else
        {
            data += "0 ";
        }
        
        if(isSeen())
        {
            data += "1 ";
        }
        else
        {
            data += "0 ";
        }
        
        if(isWant())
        {
            data += "1 ";
        }
        else
        {
            data += "0 ";
        }
        
        if(isTrade())
        {
            data += "1 ";
        }
        else
        {
            data += "0 ";
        }
                        
        data += getMultiples() + " " + getEvoFamily() + "\n";
        return data;
    }
    
    Object get(int index)
    {
        return dataSet.get(index);
    }
 
    /**
     * @return the natlDex
     */
    public int getNatlDex() {
        return natlDex;
    }

    /**
     * @param natlDex the natlDex to set
     */
    public void setNatlDex(int natlDex) {
        this.natlDex = natlDex;
        dataSet.set(0, natlDex);
    }

    /**
     * @return the johtoDex
     */
    public int getJohtoDex() {
        return johtoDex;
    }

    /**
     * @param johtoDex the johtoDex to set
     */
    public void setJohtoDex(int johtoDex) {
        this.johtoDex = johtoDex;
        dataSet.set(3, johtoDex);
    }

    /**
     * @return the hoennDex
     */
    public int getHoennDex() {
        return hoennDex;
    }

    /**
     * @param hoennDex the hoennDex to set
     */
    public void setHoennDex(int hoennDex) {
        this.hoennDex = hoennDex;
        dataSet.set(4, hoennDex);
    }

    /**
     * @return the sinnohDex
     */
    public int getSinnohDex() {
        return sinnohDex;
    }

    /**
     * @param sinnohDex the sinnohDex to set
     */
    public void setSinnohDex(int sinnohDex) {
        this.sinnohDex = sinnohDex;
        dataSet.set(5, sinnohDex);
    }

    /**
     * @return the unovaDex
     */
    public int getUnovaDex() {
        return unovaDex;
    }

    /**
     * @param unovaDex the unovaDex to set
     */
    public void setUnovaDex(int unovaDex) {
        this.unovaDex = unovaDex;
        dataSet.set(6, unovaDex);
    }

    /**
     * @return the kalosDex
     */
    public int getKalosDex() {
        return kalosDex;
    }

    /**
     * @param kalosDex the kalosDex to set
     */
    public void setKalosDex(int kalosDex) {
        this.kalosDex = kalosDex;
        dataSet.set(7, kalosDex);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
        dataSet.set(1, name);
    }

    /**
     * @return the type1
     */
    public int getType1() {
        return type1;
    }

    /**
     * @param type1 the type1 to set
     */
    public void setType1(int type1) {
        this.type1 = type1;
      
    }

    /**
     * @return the type2
     */
    public int getType2() {
        return type2;
    }

    /**
     * @param type2 the type2 to set
     */
    public void setType2(int type2) {
        this.type2 = type2;
       
    }

    /**
     * @return the caught
     */
    public boolean isCaught() {
        return caught;
    }

    /**
     * @param caught the caught to set
     */
    public void setCaught(boolean caught) {
        this.caught = caught;
        dataSet.set(8, caught);
    }

    /**
     * @return the seen
     */
    public boolean isSeen() {
        return seen;
    }

    /**
     * @param seen the seen to set
     */
    public void setSeen(boolean seen) {
        this.seen = seen;
        dataSet.set(9, seen);
    }

    /**
     * @return the want
     */
    public boolean isWant() {
        return want;
    }

    /**
     * @param want the want to set
     */
    public void setWant(boolean want) {
        this.want = want;
        dataSet.set(10, want);
    }

    /**
     * @return the trade
     */
    public boolean isTrade() {
        return trade;
    }

    /**
     * @param trade the trade to set
     */
    public void setTrade(boolean trade) {
        this.trade = trade;
        dataSet.set(11, trade);
    }

    /**
     * @return the multiples
     */
    public int getMultiples() {
        return multiples;
    }

    /**
     * @param multiples the multiples to set
     */
    public void setMultiples(int multiples) {
        this.multiples = multiples;
        dataSet.set(12, multiples);
    }
/**
     * @return the evoFamily
     */
    public int getEvoFamily() {
        return evoFamily;
    }

    /**
     * @param evoFamily the evoFamily to set
     */
    public void setEvoFamily(int evoFamily) {
     //   System.out.println("Why is this being called?");
        this.evoFamily = evoFamily;
        dataSet.set(13, evoFamily);
    }
    
    /**
     * @return the img
     */

    @Override
    public int compareTo(Pokemon o) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        /** this will sort by one of 13 comparators.
         * comparator int will determine which to sort by, 0 will indicate not to sort. 
         * If already set to sort by given comparator, will set comparator negative
         * If negative, will sort decending. if already set to negative comparator, will set 0 and sort ascending national dex
        * 1  = national dex
        * 2  = name 
        * 3  = type 
        * 4  = johto dex    -1 goes to end no matter what!
        * 5  = hoenn dex    ..
        * 6  = sinnoh dex   ..
        * 7  = unova dex    ..
        * 8  = kalos dex    -1 goes to end no matter what!
        * 9  = caught       
        * 10 = seen
        * 11 = want
        * 12 = trade
        * 13 = number of multiples
        */
        if(comparator == 0 || comparator == 1)
        {
            return this.getNatlDex() - o.getNatlDex();
        }
        if(comparator > 1)
        {
            if(comparator == 2)
            {
                return this.getName().toLowerCase().compareTo(o.getName().toLowerCase());
            }
            if(comparator == 3)
            {
                return this.getType().toLowerCase().compareTo(o.getType().toLowerCase());
            }
            if(comparator == 4)
            {
                if(this.getJohtoDex() == -1 && o.getJohtoDex() != -1)
                {
                    return 1;
                }
                if(o.getJohtoDex() == -1 && this.getJohtoDex() != -1)
                {
                    return -1;
                }
                
                return this.getJohtoDex() - o.getJohtoDex();
            }
            if(comparator == 5)
            {
                if(this.getHoennDex() == -1 && o.getHoennDex() != -1)
                {
                    return 1;
                }
                if(o.getHoennDex() == -1 && this.getHoennDex() != -1)
                {
                    return -1;
                }
                return this.getHoennDex() - o.getHoennDex();
            }
            if(comparator == 6)
            {
                  if(this.getSinnohDex() == -1 && o.getSinnohDex() != -1)
                {
                    return 1;
                }
                if(o.getSinnohDex() == -1 && this.getSinnohDex() != -1)
                {
                    return -1;
                }
                return this.getSinnohDex() - o.getSinnohDex();
            }
            if(comparator == 7)
            {
                if(this.getUnovaDex() == -1 && o.getUnovaDex() != -1)
                {
                    return 1;
                }
                if(o.getUnovaDex() == -1 && this.getUnovaDex() != -1)
                {
                    return -1;
                }
                return this.getUnovaDex() - o.getUnovaDex();
            }
            if(comparator == 8)
            {
                if(this.getKalosDex() == -1 && o.getKalosDex() != -1)
                {
                    return 1;
                }
                if(o.getKalosDex() == -1 && this.getKalosDex() != -1)
                {
                    return -1;
                }
                return this.getKalosDex() - o.getKalosDex();
            }
            if(comparator == 9)
            {
                return Boolean.compare(this.isCaught(), o.isCaught());
            }
            if(comparator == 10)
            {
                return Boolean.compare(this.isSeen(), o.isSeen());
            }
            if(comparator == 11)
            {
                return Boolean.compare(this.isWant(), o.isWant());
            }
            if(comparator == 12)
            {
                 return Boolean.compare(this.isTrade(), o.isTrade());
            }
            if(comparator == 13)
            {
                return this.getMultiples() - o.getMultiples();
            }
        }
        if(comparator < 0)
        {
            if(comparator == -1)
            {
                return o.getNatlDex() - this.getNatlDex();
            }
            if(comparator == -2)
            {
                return o.getName().toLowerCase().compareTo(this.getName().toLowerCase());
            }
            if(comparator == -3)
            {
                return o.getType().toLowerCase().compareTo(this.getType().toLowerCase());
            }
            if(comparator == -4)
            {
                if(this.getJohtoDex() == -1 && o.getJohtoDex() != -1)
                {
                    return 1;
                }
                if(o.getJohtoDex() == -1 && this.getJohtoDex() != -1)
                {
                    return -1;
                }
                return o.getJohtoDex() - this.getJohtoDex();
            }
            if(comparator == -5)
            {
                if(this.getHoennDex() == -1 && o.getHoennDex() != -1)
                {
                    return 1;
                }
                if(o.getHoennDex() == -1 && this.getHoennDex() != -1)
                {
                    return -1;
                }
                return o.getHoennDex() - this.getHoennDex();
            }
            if(comparator == -6)
            {
                if(this.getSinnohDex() == -1 && o.getSinnohDex() != -1)
                {
                    return 1;
                }
                if(o.getSinnohDex() == -1 && this.getSinnohDex() != -1)
                {
                    return -1;
                }
                return o.getSinnohDex() - this.getSinnohDex();
            }
            if(comparator == -7)
            {
                if(this.getUnovaDex() == -1 && o.getUnovaDex() != -1)
                {
                    return 1;
                }
                if(o.getUnovaDex() == -1 && this.getUnovaDex() != -1)
                {
                    return -1;
                }
                return o.getUnovaDex() - this.getUnovaDex();
            }
            if(comparator == -8)
            {
                if(this.getKalosDex() == -1 && o.getKalosDex() != -1)
                {
                    return 1;
                }
                if(o.getKalosDex() == -1 && this.getKalosDex() != -1)
                {
                    return -1;
                }
                return o.getKalosDex() - this.getKalosDex();
            }
            if(comparator == -9)
            {
                return Boolean.compare(o.isCaught(), this.isCaught());
            }
            if(comparator == -10)
            {
                return Boolean.compare(o.isSeen(), this.isSeen());
            }
            if(comparator == -11)
            {
                return Boolean.compare(o.isWant(), this.isWant());
            }
            if(comparator == -12)
            {
                return Boolean.compare(o.isTrade(), this.isTrade());
            }
            if(comparator == -13)
            {
               return o.getMultiples() - this.getMultiples();
            }
        }
        return 0;
    }
}
