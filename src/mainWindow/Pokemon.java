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
public final class Pokemon{
    static BufferedImage masterImg;
    private int natlDex;
    private int johtoDex;
    private int hoennDex;
    private int sinnohDex;
    private int unovaDex;
    private int kalosDex;
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
    
    private BufferedImage img;
    
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
        evoFamily = 1; 
        multiples = 1;
        //shouldn't be called
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
      /*  try{
        masterImg = ImageIO.read(new File("./src/mainWindow/pokemon_icondex.png"));
        } catch (IOException e) {
        }
        int imgX = ( getNatlDex() / 27) * 33;
        int imgY = ( (getNatlDex()-1) % 27) * 33;
        
        img = masterImg.getSubimage(imgX, imgY, 33 ,33);
  */  }
    
    boolean includeQuery(boolean[] filters) // checks pokemon against filters and returns to PokeList if it should be included or not (false = do not include)
    {   
        /* filters format:
        kanto       johto   hoenn           sinnoh      unova       kalos 
        caught      want    seen            multiples   dontHave    trade 
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
        /*
        ...  check against filters
        */
        boolean typeFlag = true;
        
        if(filters[getType1() + 11] == true)
        {
            // passes typecheck
        }
        else
        {
            if(getType2() == 0)
                return false;
            if(filters[getType2() + 11] == false)
                return false;
        }
                
        
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

        
        
        
        if(filters[6] == false && !caught)
            return false;
        if(filters[7] == false && !want)
            return false;
        if(filters[8] == false && !seen)
            return false;
        if(filters[9] == false && !(multiples > 0))
            return false;
        if(filters[11] == false && !trade)
            return false;
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
        this.evoFamily = evoFamily;
        dataSet.set(13, evoFamily);
    }
    
    /**
     * @return the img
     */
    public BufferedImage getImg() {
        return img;
    }

    /**
     * @param img the img to set
     */
    public void setImg(BufferedImage img) {
        this.img = img;
    }
}
