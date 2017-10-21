public class Matrix {

    double[]data;
    int rows;
    int cols;
    Matrix(double[][] d)
    {
        rows = d.length;
        int maksi=0;
        for(int i=0;i<rows;i++)
        {
            if(d[i].length>maksi)
                maksi=d[i].length;
        }
        cols=maksi;
        this.data = new double[rows*cols];
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<cols;j++)
            {
                if(j<d[i].length)
                    this.data[i*cols+j]=d[i][j];
                else
                    this.data[i*cols+j]=0;
            }
        }
    }

    double[][] asArray()
    {
        double [][]tab=new double[this.rows][this.cols];
        for(int i=0;i<this.rows;i++)
        {
            for(int j=0;j<this.cols;j++)
                tab[i][j]=this.data[i*this.cols+j];
        }
        return tab;
    }

    double get(int r,int c)
    {
        return this.data[(r-1)*this.cols+c-1];
    }
    void set (int r,int c, double value)
    {
        this.data[(r-1)*this.cols+c-1]=value;
    }

    void print_matrix()
    {
        for(int i=0;i<this.rows;i++)
        {
            for(int j=0;j<this.cols;j++)
            {
                System.out.print(this.data[i*this.cols+j]);
                System.out.print("  ");
            }
            System.out.print("\n");
        }
    }

    Matrix (int rows, int cols)
    {
        this.rows = rows;
        this.cols = cols;
        data = new double[rows*cols];
    }

    public String toString(){
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for(int i=0;i<rows;i++){
            buf.append("[");
            //...
        }
        //...
        return buf.toString();
    }
    public static void main(String[] arg)
    {
        double [][] tab= {{1,2}, {3,4,8}};
        Matrix a = new Matrix(tab);
        a.print_matrix();
        double[][] t=a.asArray();
        System.out.print(a.get(2,1));

    }
}
