/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainWindow;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;  
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Lukaus
 */
class PokeList extends AbstractTableModel{
    
    private ArrayList<Pokemon> masterList;
    private ArrayList<Pokemon> data;
    private final String[] colTitles = new String[]{"#", "Nigga", "Type", "Johto#", "Hoenn#", "Sinnoh#", "Unova #", "Kalos #", "Caught", "Seen", "Want", "Trade", "Multiples"};
    String searchKey;
    
    PokeList()
    {
        getPokemon();
    } 
    
    
    void sort(boolean[] filters)
    {
        int tableSize = data.size();
        data.clear();
        for(int i = 0; i < masterList.size(); i++)
        {
            if(masterList.get(i).includeQuery(filters))
            {
                data.add(masterList.get(i));
                fireTableRowsInserted(data.size()-1,data.size()-1);
            }
            
        }
        
        searchPokemon();
        
        if(data.isEmpty())
        {
            masterList.add(new Pokemon(0, 0, 0, 0, 0, 0, "Empty Table!", 0, 0, false, false, false, false, 1337, 1));
            fireTableDataChanged();
            //fireTableRowsDeleted(0, tableSize);
        }
        else
        {
                fireTableDataChanged();
        }
    }
    
    
   ArrayList<Pokemon> getPokemon()
    {
        readData();
        data = new ArrayList<Pokemon>();
        
        int i = 0;
        while(i < masterList.size())
        {
            data.add(masterList.get(i));
            i++;
        }
        
        return data;
    }
    void readData()
    {
        masterList = new ArrayList<Pokemon>();
      //  String[] tokens = {"", ""};
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
                   //   masterList.add(new Pokemon(1, 2, 3, 4, 5, 6, "Pikashoo", 1, 2, true, true, true, true, 1, 1));
            }
            in.close();
         
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
     //       System.err.println("At: " + tokens[0]);
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
   

    void setSearchKey(String s) {
        
        searchKey = s;
        searchPokemon();
    }

    private void searchPokemon() {
       
        for(int i = 0; i < data.size(); i++)
        {
            if(!data.get(i).getName().toLowerCase().contains(searchKey.toLowerCase()))
            {
                data.remove(i);
                i--;
            }
        }
        if(data.isEmpty())
        {
            masterList.add(new Pokemon(0, 0, 0, 0, 0, 0, "Empty Table!", 0, 0, false, false, false, false, 1337, 1));
            fireTableDataChanged();
            //fireTableRowsDeleted(0, tableSize);
        }
        fireTableDataChanged();
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
}