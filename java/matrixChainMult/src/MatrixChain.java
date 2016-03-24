import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by FitzRoi on 9/5/15.
 */
public class MatrixChain {
    private String matrixInputStr;
    private static int N;
    private ArrayList<Tuple> objects = new ArrayList<Tuple>();
    private int M[][]; //matrix
    private int I[][];
    private  String tracker[][];




    public void setN(int n) {
        N = n;
    }

    public static int getN() {
        return N;
    }

    public int getRowVal(int j){
        return this.objects.get(j).row;
    }

    public int getColVal(int j){
        return this.objects.get(j).col;
    }

    public String getMatrixInputStr() {
        return matrixInputStr;
    }

    public void setMatrixInputStr(String setStr) {
        this.matrixInputStr = setStr;
    }

    public ArrayList<Tuple> getObjects() {
        return objects;
    }


    public void init(ArrayList<Tuple> objects){
        this.objects = objects;
        this.N=objects.size();
        if(objects.size()>0) {
            M = new int[N][N]; //matrix
            I=new int[N][N]; //indices of breakpoints
            tracker=new String[N][N]; //storage for min calculations

            for(int i=1;i<N;i++)
                M[i][i]=0;
        }
    }


    //DP algorithm to compute matrix multiplication order
    public void computeMatrixChain() {
        for(int size = 1; size < N; size++) {        // size of subsequence
            for (int l = 0; l < N - size; l++) {
                int r = l + size;    //move along diagonal
                M[l][r] = Integer.MAX_VALUE;    //minimizer
                String minStr[]=new String[r];
                String minValStr[]=new String[r];

                for (int i = l; i < r; i++) {
                    int rowl=getRowVal(l);
                    int coli=getColVal(i);
                    int colr=getColVal(r);
                    int x = M[l][i] + M[i + 1][r] + (rowl*coli*colr);
                    minStr[i]="M(" + (l+1) + "," + (i+1) + ") + "+ "M(" + (i+2) + "," + (r+1) + ") + " + rowl +"*" +coli +"*" + colr + "";
                    minValStr[i]=x +"";
                    if(x < M[l][r]) {
                        M[l][r] = x;
                        I[l][r]=i;
                    }

                    tracker[l][r]="min{" +String.join(",\n\t\t  ",minStr) +"}";
                    tracker[l][r]+="\n\t\t= min{"+String.join(",", minValStr) +"}";
                }
            }
        }
    }

    public String which(int i, int j){
        if(i>-1 && i<=N && j>-1 && j<=N)
            return "\nM[" +i+"][" +j +"] = " +tracker[i-1][j-1];
        return "\nInvalid i and j";
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
                    System.out.print(M[i][j]);
                if(j>i)
                    System.out.print(M[i][j] + "(" + (I[i][j]+1) + ")\t");

                else
                    System.out.print("\t\t");
            }
            System.out.println("\n");
        }
    }

    public void printObjects(){
        for(int i=0;i<this.objects.size();i++){
            System.out.print(objects.get(i).row +",");
            System.out.print(objects.get(i).col +"\n");
        }
    }

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
//                    System.out.print(M[i][j]);
                if (j > i)
                    System.out.print("(" + (I[i][j] + 1) + ")\t\t");

                else
                    System.out.print("\t\t");
            }
            System.out.println("\n");

        }

    }

    public MatrixChain(File configFile) throws IOException {

        if (configFile != null && configFile.isFile())
        {
            Properties properties = new Properties();
            FileInputStream input = null;
            try
            {
                input = new FileInputStream(configFile);
                properties.load(input);

                this.setMatrixInputStr(properties.getProperty("matrix_chain", "0").trim());

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



    public void printOptimalParenthesizations() {
        boolean[] inAResult = new boolean[I.length];//I contains the indices of breakpoints
        System.out.println("Matrix Multiplication Order");
        printOptimalParenthesizations(I, 0, I.length - 1, inAResult);
    }

    void printOptimalParenthesizations(int[][]s, int i, int j,  /* for pretty printing: */ boolean[] inAResult) {
        if (i != j) {
            printOptimalParenthesizations(s, i, s[i][j], inAResult);
            printOptimalParenthesizations(s, s[i][j] + 1, j, inAResult);
            String istr = inAResult[i] ? "_result " : " ";
            String jstr = inAResult[j] ? "_result " : " ";
            System.out.println(" A_" + (i+1) + istr + "* A_" + (j+1) + jstr);

            //label matrices with letters
//            System.out.println(" " + ((char) (65+i)) + istr + "* " + ((char) (65 + j)) + jstr);
            inAResult[i] = true;
            inAResult[j] = true;
        }
    }
}
