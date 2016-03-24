import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by FitzRoi on 9/2/15.
 */
public class O1Knapsack {

    private String setStr;
    private static int M;
    private static int N;
    private ArrayList<Tuple> objects = new ArrayList<Tuple>();
    private int P[][]; //knapsack matrix
    private String tracker[][];

    public int getM() {
        return M;
    }


    public void setM(int m) {
        M = m;
    }

    public void setN(int n) {
        N = n;
    }

    public static int getN() {
        return N;
    }

    public int getW(int j){
        return this.objects.get(j).weight;
    }

    public  Tuple getObject(int j){
        return objects.get(j);
    }
    public int getP(int j){
        return this.objects.get(j).profit;
    }

    public String getSetStr() {
        return setStr;
    }

    public void setSetStr(String setStr) {
        this.setStr = setStr;
    }

    public ArrayList<Tuple> getObjects() {
        return objects;
    }


    public void init(ArrayList<Tuple> objects){
        this.objects = objects;
        this.N=objects.size();
        if(objects.size()>0) {
            P = new int[N+1][M+1];
            tracker=new String[N+1][M+1];
        }
    }

    public int[] max(int a, int b){
        if(a>b)return new int[]{0,a};
        return new int[]{1,b};
    }

    //DP algorithm to fill knapsack
    public void fillKnapsack() {
        for (int j = 1; j <= N; j++) {
            int Wj = getW(j-1); //objects array goes from 0 - N-1
            int pj = getP(j-1);
            for (int k = 1; k <= M; k++) {
                if (Wj > k) {
                    P[j][k] = P[j - 1][k];
                    tracker[j][k]="p[" + j +"-1][" +k +"]; obj=" +j;
                }
                else {
                    int maxKeyVal[]=max(P[j - 1][k - Wj] + pj, P[j - 1][k]);
                    int key=maxKeyVal[0];
                    int val=maxKeyVal[1];
                    P[j][k]=val ;
//                    if(key==0){
//                        tracker[j][k]="p[" + j +"-1][" +(k-Wj) +"] + " + pj +"; obj=" +j;
//                    }
//                    else {
//                        tracker[j][k]="p[" + j +"-1][" +k +"]" +"; obj=" +j;
//                    }

                    tracker[j][k]="max( p[" + j +"-1][" +(k-Wj) +"] + " + pj +",  p[" + j +"-1][" +k +"] )\n " +
                            "\t\t= max( " +(P[j - 1][k - Wj] + pj) +", " +(P[j - 1][k]) +" )\n" +
                            "\t\t  obj=" +j;
                }
            }
        }
    }

    public void peekInside(){
        int selectedObjs[]=new int[N+1];
        int i=N, k=M;
        while(i>0 && k > 0) {
            int Wi=getW(i-1);
            if(P[i][k]!=P[i-1][k]) {
                selectedObjs[i] = 1;
                i = i - 1;
                k = k - Wi;
            }
            else
            i = i-1;
        }

        System.out.println("Objects inside knapsack");
        for(i=0;i<=N;i++){
            if(selectedObjs[i]==1) {
                Tuple obj=getObject(i - 1);
                System.out.println(i + " (" + obj.weight + ", " + obj.profit +")");
            }
        }
    }

    public String which(int i, int j){
        if(i>-1 && i<=N && j>-1 && j<=M)
                return "\nP[" +i+"][" +j +"] = " +tracker[i][j];
        return "\nInvalid i and j";
    }
    public void printP(){
        int N=objects.size();
        String tabs = new String(new char[N]).replace('\0', '\t');
        System.out.print("w->\t" +tabs);

        for (int i = 0; i <=M;i++)
            System.out.print(i +"\t");
        System.out.println("\n");

        for (int i = 0; i <= N; i++){
            String objs="{";
            if(i==0)objs="{}" +tabs;
            else {
                String nObjs[]=new String[i];
                tabs = new String(new char[N-i]).replace('\0', '\t');
                for (int o = 0; o <i; o++) nObjs[o]="O"+(o+1);
                objs= Arrays.toString(nObjs).replace("[","{").replace("]","}") +tabs;
            }
            for (int j = 0; j <=M;j++) {
                if(j==0)
                    System.out.print(objs+"\t");

                System.out.print(P[i][j] + "\t");
            }
            System.out.println("\n");
        }
    }
    public void printObjects(){
        for(int i=0;i<this.objects.size();i++){
            System.out.print(objects.get(i).weight +",");
            System.out.print(objects.get(i).profit +"\n");
        }
    }
    public O1Knapsack(File configFile) throws IOException{

        if (configFile != null && configFile.isFile())
        {
            Properties properties = new Properties();
            FileInputStream input = null;
            try
            {
                input = new FileInputStream(configFile);
                properties.load(input);

                this.setM(Integer.parseInt(properties.getProperty("M", "0").trim()));
                this.setSetStr(properties.getProperty("weight_profit_set", "0").trim());

            }
            finally
            {
                if (input != null)
                {
                    input.close();
                }
            }
        }
        else
        {
            System.out.println("Error reading config file.");
            System.exit(-1);
        }
    }

}
