/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainWindow;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.table.AbstractTableModel;


/**
 *
 * @author Lukaus
 */
final class PokeList extends AbstractTableModel{
    
    private ArrayList<Pokemon> masterList;
    private ArrayList<Pokemon> data;
    private final String[] colTitles = new String[]{"#", "Name", "Type", "Johto#", "Hoenn#", "Sinnoh#", "Unova #", "Kalos #", "Caught", "Seen", "Want", "Trade", "Multiples"};
    String searchKey;
    
    PokeList()
    {
        getPokemon();
    } 
   

    int sort(boolean[] filters)
    {
        
        int pokeCount = 0;
        int tableSize = data.size();
        data.clear();
        for(int i = 0; i < masterList.size(); i++)
        {
            if(masterList.get(i).includeQuery(filters))
            {
                data.add(new Pokemon(masterList.get(i)));
                fireTableRowsInserted(data.size()-1,data.size()-1);
                pokeCount++;
            }
            
        }
        
        if(searchKey != null)
            pokeCount -= searchPokemon();
        
        for(int j = 0; j < data.size(); j++)
        {
            if(data.get(j).getName().contains(" family"))
                data.get(j).setName(data.get(j).getName().subSequence(0, data.get(j).getName().length() - 7).toString());
        }
        
        if(!data.isEmpty() && filters[33] == true)
            pokeCount -= evoQuell();
        
        fireTableDataChanged();
        
        return pokeCount;
    }
    
    
   ArrayList<Pokemon> getPokemon()
    {
        readData();
        data = new ArrayList<Pokemon>();
        
        int i = 0;
        while(i < masterList.size())
        {
            data.add(new Pokemon(masterList.get(i)));
            i++;
        }
        
        return data;
    }
    void readData()
    {
        masterList = new ArrayList<Pokemon>();
      
        try{
            FileInputStream fstream = new FileInputStream("./src/mainWindow/pokemon.dat");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                String[] 
                        tokens = strLine.split(" ");
                for(int i = 9; i < 13; i++)
                {
                    if(Integer.parseInt(tokens[i]) == 1 )
                        tokens[i] = "true";
                    else
                        tokens[i] = "false";
                } // add new pokemon
                masterList.add(new Pokemon(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]),Integer.parseInt(tokens[4]),Integer.parseInt(tokens[5]),
                        tokens[6],Integer.parseInt(tokens[7]),Integer.parseInt(tokens[8]),
                        Boolean.parseBoolean(tokens[9]),Boolean.parseBoolean(tokens[10]),Boolean.parseBoolean(tokens[11]),Boolean.parseBoolean(tokens[12]),
                        Integer.parseInt(tokens[13]),Integer.parseInt(tokens[14])));
                   
            }
            in.close();
         
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
     //       System.err.println("At: " + tokens[0]);
        }     
   }
    void saveData()
    {
        try {
			File file = new File("./src/mainWindow/pokemon.dat");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
                        for(int i = 0; i < masterList.size(); i++)
                        {
                            bw.write(masterList.get(i).getSaveData());
                        }
                        
                        bw.close();
			System.out.println("Done saving");

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    

    @Override
    public int getRowCount() {
       return data.size();
    }

    @Override
    public int getColumnCount() {
        return colTitles.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(getRowCount() > 0)
            return data.get(rowIndex).get(columnIndex);
        return new Pokemon(0, 0, 0, 0, 0, 0, "Empty Table!", 0, 0, false, false, false, false, 1337, 1);
    }
    @Override
     public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
     
    @Override
       public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 8) {
            return false;
        } else {
            return true;
        }
       }
       public void setValueAt(Object value, int row, int col) 
       {
           if(col < 12)
           {
               if(col == 8)
                {
                    data.get(row).setCaught((Boolean) value);
                    masterList.get(data.get(row).getNatlDex()-1).setCaught(data.get(row).isCaught());
                 //   fireTableDataChanged();
                }
                if(col == 9)
                {
                    data.get(row).setSeen((Boolean) value);
                    masterList.get(data.get(row).getNatlDex()-1).setSeen(data.get(row).isSeen());
                //    fireTableDataChanged();
                }
                if(col == 10)
                {
                    data.get(row).setWant((Boolean) value);
                    masterList.get(data.get(row).getNatlDex()-1).setWant(data.get(row).isWant());
                 //   fireTableDataChanged();
                }
                if(col == 11)
                {
                    data.get(row).setTrade((Boolean) value);
                    masterList.get(data.get(row).getNatlDex()-1).setTrade(data.get(row).isTrade());
                    //fireTableDataChanged();
                }
        //       data.get(row,col).set = value;
           }
           else
           {
               if(col == 12)
                {
                    data.get(row).setMultiples((Integer) value);
                    masterList.get(data.get(row).getNatlDex()-1).setMultiples(data.get(row).getMultiples());
                }
               
           }
        fireTableCellUpdated(row, col);
    }
     
    void setSearchKey(String s) {
        
        searchKey = s;
        searchPokemon();
    }

    private int searchPokemon() {
        int pokesRemoved = 0;
        for(int i = 0; i < data.size(); i++)
        {
            if(!(data.get(i).getName().toLowerCase().contains(searchKey.toLowerCase())) )
            {
                data.remove(i);
                i--;
                pokesRemoved++;
            }
            
        }
        if(data.isEmpty())
        {
        //    masterList.add(new Pokemon(0, 0, 0, 0, 0, 0, "Empty Table!", 0, 0, false, false, false, false, 1337, 1));
            fireTableDataChanged();
            //fireTableRowsDeleted(0, tableSize);
        }
        fireTableDataChanged();
        return pokesRemoved;
    }

    void colSort(int index) {
        for(int i = 0; i < data.size(); i++)
        {
            data.get(i).setComparator(index+1);
        }
        Collections.sort(data);
        fireTableDataChanged();
    }

    void flipValue(int row, int column) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(column == 8)
        {
            data.get(row).setCaught(!data.get(row).isCaught());
            masterList.get(data.get(row).getNatlDex()-1).setCaught(data.get(row).isCaught());
            fireTableDataChanged();
        }
        if(column == 9)
        {
            data.get(row).setSeen(!data.get(row).isSeen());
            masterList.get(data.get(row).getNatlDex()-1).setSeen(data.get(row).isSeen());
            fireTableDataChanged();
        }
        if(column == 10)
        {
            data.get(row).setWant(!data.get(row).isWant());
            masterList.get(data.get(row).getNatlDex()-1).setWant(data.get(row).isWant());
            fireTableDataChanged();
        }
        if(column == 11)
        {
            data.get(row).setTrade(!data.get(row).isTrade());
            masterList.get(data.get(row).getNatlDex()-1).setTrade(data.get(row).isTrade());
            fireTableDataChanged();
        }
    }

    void setToCaught(int i, boolean selected) 
    {
       data.get(i).setCaught(selected);
       masterList.get(i).setCaught(selected);
       fireTableDataChanged();
    }

    void setToWant(int i, boolean selected) 
    {
       data.get(i).setWant(selected);
       masterList.get(i).setWant(selected);
       fireTableDataChanged();
    }

    void setToSeen(int i, boolean selected) 
    {
       data.get(i).setSeen(selected);
       masterList.get(i).setSeen(selected);
       fireTableDataChanged();
    }

    void setToTrade(int i, boolean selected) 
    {
       data.get(i).setTrade(selected);
       masterList.get(i).setTrade(selected);
       fireTableDataChanged();
    }

    void setMultiplesNumber(int i, String text) {
       data.get(i).setMultiples(Integer.parseInt(text));
       masterList.get(i).setMultiples(Integer.parseInt(text));
       fireTableDataChanged();
    }

    private int evoQuell() 
    {
      //  System.out.println("In evoQuell()");
        int quelledPokes = 0;
        int currentDexNum = 1;
        boolean[] pokeFamilies = new boolean[364];
        for(int i = 0; i < 364; i++)
            pokeFamilies[i] = false;
        
        for(int j = 0; j < data.size(); j++)
        {
            if( pokeFamilies[data.get(j).getEvoFamily()] == true )
            {
              //  System.out.println("----------- " + data.get(j).getName() + ":" + data.get(j).getEvoFamily() + " is being quelled!");
                if(data.get(j).isCaught())
                    data.get(currentDexNum).setCaught(true);
                data.remove(j);
                quelledPokes++;
                j--;
                
            }
            else
            {
               // System.out.println(data.get(j).getName() + ":" + data.get(j).getEvoFamily() + " is being kept.");
                currentDexNum = j;//data.get(j).getNatlDex();
                pokeFamilies[data.get(j).getEvoFamily()] = true;
                if(!data.get(j).getName().contains(" family"))
                    data.get(j).setName(data.get(j).getName() + " family");
            }
        }
        
        return quelledPokes;        
    }

    int getNatlDexAt(int row) {
        return data.get(row).getNatlDex();
    }

}