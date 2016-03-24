import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by FitzRoi on 9/7/15.
 */
public class BSTorganizer {
    private String BSTkeyStr;
    private static int N;
    private ArrayList<Integer> freqs;
    private int C[][]; //matrix
    private int I[][];
    private  String tracker[][];




    public String getBSTkeyStr() {
        return BSTkeyStr;
    }

    public void setBSTkeyStr(String setStr) {
        this.BSTkeyStr = setStr;
    }


    public void init(ArrayList<Integer> freqs){
        this.freqs = freqs;
        this.N=freqs.size();
        if(N>0) {
            C = new int[N][N]; //matrix
            I=new int[N][N]; //indices of breakpoints
            tracker=new String[N][N]; //storage for min calculations

            for(int i=0;i<N;i++)
                C[i][i]=freqs.get(i);
        }
    }


    //DP algorithm to compute matrix multiplication order
    public void organizeBST() {

            for (int l = 2; l <= N; l++) {
                for(int i=0; i< N-l+1; i++){
                int j = i+l-1;
                C[i][j] = Integer.MAX_VALUE;    //minimizer
                String minStr[]=new String[i];
                String minValStr[]=new String[i];

                for (int r = i; r <= j; r++) {
//                    int rowl=getRowVal(l);
//                    int coli=getColVal(i);
//                    int colr=getColVal(r);
                    int x = ((r > i)? C[i][r-1]:0) +
                            ((r < j)? C[r+1][j]:0) +
                            sum(freqs, i, j);
//                    System.out.println(x);

                    if (x < C[i][j]) {
                        C[i][j] = x;
                        I[i][j] = r;
//                    minStr[i]="M(" + (l+1) + "," + (i+1) + ") + "+ "M(" + (i+2) + "," + (r+1) + ") + " + rowl +"*" +coli +"*" + colr + "";
//                    minValStr[i]=x +"";


//                    tracker[l][r]="min{" +String.join(",\n\t\t  ",minStr) +"}";
//                    tracker[l][r]+="\n\t\t= min{"+String.join(",", minValStr) +"}";
                    }
                }
            }
        }
    }

    public void computeBSTMinCost() {
        for(int size = 1; size < N; size++) {        // size of subtree
            for (int l = 0; l < N - size; l++) {
                int r = l + size;    //move along diagonal
                C[l][r] = Integer.MAX_VALUE;    //minimizer
                String minStr[]=new String[r+1];
                String minValStr[]=new String[r+1];

                for (int i = l; i <= r; i++) {
                    minStr[i]="";
                    int x=0;
                    if(i>l){
                        x+=C[l][i-1];
                        minStr[i]+=" C[" +(l+1) +"][" +(i-1+1) +"]{" +C[l][i-1] +"} + ";
                    }

                    if(i<r){
                        x+=C[i+1][r];
                        minStr[i]+=" C[" +(i+2) +"][" +(r+1) +"]{" +C[i+1][r] +"} + ";
                    }

                    x+=sum(freqs, l, r);
                    minStr[i]+=" sum(f," + (l+1) +", " + (r+1) +"){";
                    minStr[i]+=sum(freqs, l, r) +"}";
//                    int x = ((i > l)? C[l][i-1]:0) +
//                            ((i < r)? C[i+1][r]:0) +
//                            sum(freqs, l, r);
//                    minStr[i]="C(" + (l) + "," + (i+1) + ") + "+ "C(" + (i+2) + "," + (r+1) + ") + " + rowl +"*" +coli +"*" + colr + "";
                    minValStr[i]=x +"";
                    if(x < C[l][r]) {
                        C[l][r] = x;
                        I[l][r]=i;
                    }

                    tracker[l][r]="min{" +String.join(",\n\t\t  ",minStr).replace("null,\n\t\t  ","") +" }";
                    tracker[l][r]+="\n\t\t= min{"+String.join(",", minValStr).replace("null,","") +"}";
                }
            }
        }
    }

    public String which(int i, int j){
        if(i>-1 && i<=N && j>-1 && j<=N)
            return "\nC[" +i+"][" +j +"] = " +tracker[i-1][j-1];
        return "\nInvalid i and j (" +i +"," +j +")";
    }

    public int sum(ArrayList<Integer> freqs, int i, int j){
        int sum=0;
        for(int k=i; k<=j; k++)
            sum+=freqs.get(k);
        return sum;
    }
    public int f(int i){
        if(i>=0 && i<N)
            return freqs.get(i);
        return -1;
    }

    public void printP(){
        System.out.print("r=\t");

        for (int i = 0; i < N;i++)
            System.out.print((i+1) +"\t\t");
        System.out.println("\nl\n");

        for (int i = 0; i < N; i++){
            for (int j = 0; j < N;j++) {
                if(j==0)
                    System.out.print((i + 1) + "\t");
                if(j==i)
                    System.out.print(C[i][j]);
                if(j>i)
                    System.out.print(C[i][j] + "(" + (I[i][j]+1) + ")\t");

                else
                    System.out.print("\t\t");
            }
            System.out.println("\n");
        }
    }

    public void printComputationTrace(){

        for (int i = 0; i < N; i++){
            for (int j = 0; j < N;j++) {
//                if(j==0)
//                    System.out.print((i + 1) + "\t");
//                if(j==i)
//                    System.out.print(C[i][j]);
                if(j>i)
                    System.out.print(which(i+1,j+1) + "\n");

//                else
//                    System.out.print("\t\t");
            }
            System.out.println("\n");
        }
    }

//    public void printObjects(){
//        for(int i=0;i<this.objects.size();i++){
//            System.out.print(objects.get(i).row +",");
//            System.out.print(objects.get(i).col +"\n");
//        }
//    }

    public void printObjects(){System.out.println("keys="+this.freqs.toString()+"\n");}

    public void printBreakPoints(){
        System.out.print("\nr=\t");

        for (int i = 0; i < N;i++)
            System.out.print((i+1) +"\t\t");
        System.out.println("\nl\n");

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (j == 0)
                    System.out.print((i + 1) + "\t");
//                if (j == i)
//                    System.out.print(C[i][j]);
                if (j > i)
                    System.out.print("(" + (I[i][j] + 1) + ")\t\t");

                else
                    System.out.print("\t\t");
            }
            System.out.println("\n");

        }

    }

    public BSTorganizer(File configFile) throws IOException {

        if (configFile != null && configFile.isFile())
        {
            Properties properties = new Properties();
            FileInputStream input = null;
            try
            {
                input = new FileInputStream(configFile);
                properties.load(input);

                this.setBSTkeyStr(properties.getProperty("bst_keys", "0").trim());

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
