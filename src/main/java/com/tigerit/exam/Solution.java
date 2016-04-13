package com.tigerit.exam;


import static com.tigerit.exam.IO.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
/**
 * All of your application logic should be placed inside this class.
 * Remember we will load your application from our custom container.
 * You may add private method inside this class but, make sure your
 * application's execution points start from inside run method.
 */
public class Solution implements Runnable {
    
    
    public static Integer testcase,noftable,print_case;
    public static String tname,tsize;
    public static Vector<String> tlist = new Vector<String>();
    public static Map<String,Vector<String>> columns= new HashMap<String,Vector<String>>();
    public static Map <String, long [][]> tdata = new HashMap<String,long [][]>(); 
    
    public void run() {
        
           testcase=IO.readLineAsInteger();
        
        for(int i=1; i<=testcase; i++)
        {
            print_case=i;
            noftable=IO.readLineAsInteger();
            for(int j=0; j<noftable; j++)
            {
                tname=IO.readLine();
                tlist.add(tname);
                tsize=IO.readLine();
                String[] split=tsize.split(" ");
                int col=Integer.parseInt(split[0]);
                int row=Integer.parseInt(split[1]);
                
                split=IO.readLine().split(" ");
                Vector<String>col_names=new Vector<String>();
                for(int k=0; k<split.length; k++)
                {
                    col_names.add(split[k]);
                  
                }
                columns.put(tname, col_names);
                
                long [][]data=new long[row][col];
                for(int r=0; r<row; r++)
                {
                    split=IO.readLine().split(" ");
                    if(split.length!=col)
                    {
                        System.out.println("Invalid Input");
                        System.exit(0);
                    }
                    for(int c=0; c<col; c++)
                    { 
                      data[r][c]=Long.parseLong(split[c]);
                    }
                }
                tdata.put(tname, data);
            }
            
            int number_of_query=IO.readLineAsInteger();
            for(int nq=0; nq<number_of_query; nq++)
            {
                execute_query(nq);
            }
            
            //end of a single testcase : clear memory

            tlist.clear();
            columns.clear();
            tdata.clear();
            
        }//end of all testcases
        
    } //end of run method
    
    private  void execute_query(int nq)
    {
                String []select=null;
                String query[]=null;
                String s;
                Map<String,String>jointable_map=new HashMap<String,String>();
                Vector<String>jointable_list=new Vector<String>();
                Map<String,Integer>table_index=new HashMap<String,Integer>();
                
                s=IO.readLine(); //select part
                s=s.replaceAll(",","");
                select=s.split(" ");
                
                query=IO.readLine().split(" "); //from part
                jointable_map.put(query[1], query[1]);
                jointable_list.add(query[1]);
                if(query.length>=3)
                {
                    jointable_map.put(query[2], query[1]);
                }
                 
                query=IO.readLine().split(" "); //join part
                jointable_map.put(query[1], query[1]);
                jointable_list.add(query[1]);
                if(query.length>=3)
                {
                    jointable_map.put(query[2], query[1]);
                }
                
                s=IO.readLine(); //on part
                s=s.replaceAll("=","");
                query=s.split("\\s+");
                
                String []test=query[1].split("\\.");
                s=jointable_map.get(test[0]);
                
                table_index.put(s,columns.get(s).indexOf(test[1]));
                
                test=query[2].split("\\.");
                s=jointable_map.get(test[0]);
                table_index.put(s,columns.get(s).indexOf(test[1]));
                
                int ind1,ind2;
                s=jointable_list.get(0);
                ind1= table_index.get(s);
                long [][]a=tdata.get(s);
                s=jointable_list.get(1);
                ind2=table_index.get(s);
                long [][]aa=tdata.get(s);
                
               Vector<String>selectpart=new Vector<String>();
               boolean b=select[1].equals("*");
               if(!b)
               {
                 for(int i=1; i<select.length; i++)  
                 {
                     query=select[i].split("\\.");
                     selectpart.add(jointable_map.get(query[0]));
                     selectpart.add(query[1]);
                     
                 }
               }
              
             
               if(nq==0){System.out.println("Test: "+print_case); }
               if(b)
               {  
                    Vector<String>temp=new Vector<String>(); 
                    temp=columns.get(jointable_list.get(0));
                    for(int l=0; l<temp.size(); l++)
                    {
                        System.out.print(temp.get(l)+" ");
                    }
                    temp=columns.get(jointable_list.get(1));
                    for(int l=0; l<temp.size(); l++)
                    {
                        System.out.print(temp.get(l)+" ");
                    }
                    System.out.println();
                }
                else
               {
                    for(int m=1; m<selectpart.size(); m+=2)
                    {
                        System.out.print(selectpart.get(m)+" ");
                    }
                    System.out.println();              
               }
               
               for(int i=0; i<a.length; i++)
               {
                   for(int j=0; j<aa.length; j++)
                   {
                       if(a[i][ind1]==aa[j][ind2])
                       {
                           if(b) 
                           {
                                 
                               for(int m=0; m<a[i].length; m++)
                               {
                                   System.out.print(a[i][m]+" ");
                               }
                               
                               for(int m=0; m<aa[j].length; m++)
                               {
                                   System.out.print(aa[j][m]+" ");
                               }
                               System.out.println();
                               
                           }else 
                           {
                            
                              for(int m=0; m<selectpart.size(); m+=2)
                              {
                                  String name=selectpart.get(m);
                                  int temp=jointable_list.indexOf(name);
                                  int tempindex=columns.get(name).indexOf(selectpart.get(m+1)); 
                                  if(temp==0){
                                      System.out.print(a[i][tempindex]+" ");
                                  }
                                  else {
                                      System.out.print(aa[j][tempindex]+" ");
                                  }
                                  
                              }
                                System.out.println();
                           }
                       }
                   }
               }
               
               System.out.println();
               
    } 
    
}
