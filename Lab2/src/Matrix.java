import static java.lang.Math.pow;
import static java.lang.StrictMath.sqrt;
import java.util.Random;


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
            System.arraycopy(this.data, i * this.cols, tab[i], 0, this.cols);
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
        System.out.print("\n");
        for(int i=0;i<this.rows;i++)
        {
            for(int j=0;j<this.cols;j++)
            {
                System.out.print(this.data[i*this.cols+j]+"  ");
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
            for (int j=0;j<cols;j++)
            {
                if(j==cols-1)
                    buf.append(data[i * cols + j]).append("]");
                else
                    buf.append(data[i * cols + j]).append(", ");
            }
            if(i!=rows-1)
                buf.append(", ");
        }
        buf.append("]");
        return buf.toString();
    }

    int[] shape()
    {
        return new int[]{rows,cols};
    }

    Matrix add(Matrix m)
    {
        Matrix a= new Matrix(m.rows,m.cols);
        /*this works for reshaped matrixes
        if(this.rows*this.cols != m.rows*m.cols)
            throw new RuntimeException(String.format("those matrix can't be added"));
        for(int i=0;i<this.rows*this.cols;i++)
            this.data[i] = this.data[i] + m.data[i];*/
        if(this.rows!=m.rows ||this.cols != m.cols)
            throw new RuntimeException(String.format("those matrix can't be added"));
        for(int i=0;i<this.rows;i++)
        {
            for (int j=0;j<this.cols;j++)
                a.data[i * this.cols + j] = this.data[i * this.cols + j] + m.data[i * this.cols + j];
        }
        return a;
    }

    Matrix sub(Matrix m)
    {
        if(this.rows!=m.rows ||this.cols != m.cols)
            throw new RuntimeException(String.format("those matrix can't be added"));
        Matrix a= new Matrix(m.rows,m.cols);
        for(int i=0;i<this.rows;i++)
        {
            for (int j=0;j<this.cols;j++)
                a.data[i * this.cols + j] = this.data[i * this.cols + j] - m.data[i * this.cols + j];
        }
        return a;
    }

    Matrix mul(Matrix m)
    {
        Matrix a= new Matrix(m.rows,m.cols);
        if(this.rows!=m.rows ||this.cols != m.cols)
            throw new RuntimeException(String.format("those matrix can't be added"));
        for(int i=0;i<this.rows;i++)
        {
            for (int j=0;j<this.cols;j++)
                a.data[i * this.cols + j] = this.data[i * this.cols + j] * m.data[i * this.cols + j];
        }
        return a;
    }

    Matrix div(Matrix m)
    {
        if(this.rows!=m.rows ||this.cols != m.cols)
            throw new RuntimeException(String.format("those matrix can't be added"));
        Matrix a= new Matrix(m.rows,m.cols);
        for(int i=0;i<this.rows;i++)
        {
            for (int j=0;j<this.cols;j++)
            {
                if(m.data[i * this.cols + j]==0)
                    throw new RuntimeException(String.format("do not div by 0!!!"));
                a.data[i * this.cols + j] = this.data[i * this.cols + j] / m.data[i * this.cols + j];
            }
        }
        return a;
    }

    Matrix add(double w)
    {
        Matrix a= new Matrix(this.rows,this.cols);
        for(int i=0;i<this.rows;i++)
        {
            for (int j=0;j<this.cols;j++)
                a.data[i * this.cols + j] =this.data[i * this.cols + j]+w;
        }
        return a;
    }
    Matrix sub(double w)
    {
        Matrix a= new Matrix(this.rows,this.cols);
        for(int i=0;i<this.rows;i++)
        {
            for (int j=0;j<this.cols;j++)
                a.data[i * this.cols + j] =this.data[i * this.cols + j]-w;
        }
        return a;
    }

    Matrix mul(double w)
    {
        Matrix a= new Matrix(this.rows,this.cols);
        for(int i=0;i<this.rows;i++)
        {
            for (int j=0;j<this.cols;j++)
                a.data[i * this.cols + j] =this.data[i * this.cols + j]*w;
        }
        return a;
    }

    Matrix div(double w)
    {
        if(w==0)
            throw new RuntimeException(String.format("do not div by 0!!!"));
        Matrix a= new Matrix(this.rows,this.cols);
        for(int i=0;i<this.rows;i++)
        {
            for (int j=0;j<this.cols;j++)
                a.data[i * this.cols + j] =this.data[i * this.cols + j]/w;
        }
        return a;
    }

    Matrix dot(Matrix m)
    {
        if(this.cols!=m.rows)
            throw new RuntimeException(String.format("can't dot those matrixes"));
        Matrix a= new Matrix(this.rows,m.cols);
        for(int i=0;i<this.rows;i++)
        {
            for(int j=0;j<m.cols;j++)
            {
                for(int k=0;k<m.rows;k++)
                {
                    a.data[i*m.cols+j]+=this.data[i*this.cols+k]*m.data[k*m.cols+j];
                }
            }
        }
        return a;
    }

    double frobenius()
    {
        double a=0;
        for(int i=0;i<this.cols*this.rows;i++)
            a+=pow(this.data[i],2);
        return sqrt(a);
    }
    void reshape(int newRows,int newCols){
        if(rows*cols != newRows*newCols)
            throw new RuntimeException(String.format("%d x %d matrix can't be reshaped to %d x %d",rows,cols,newRows,newCols));
        else
        {
            rows=newRows;
            cols=newCols;
        }
    }

    Matrix inv(Matrix a)
    {
        if(a.rows!=a.cols )
            throw new RuntimeException(String.format("can't do inv"));
        return a;
    }
    public static Matrix random(int rows, int cols){
        Matrix m = new Matrix(rows,cols);
        Random r = new Random();
        for(int i=1;i<=m.rows;i++)
        {
            for(int j=1;j<=m.cols;j++)
            {
                m.set(i,j,r.nextDouble()*100);
            }
        }
        return m;
    }

    public static Matrix eye(int n){
        Matrix m = new Matrix(n,n);
        for(int i=1;i<=m.rows;i++)
        {
                m.set(i,i,1);
        }
        return m;
    }

    public static void main(String[] arg)
    {
        double [][] tab= {{1,2}, {3,4,8}, {3,7,9},{1}};
        Matrix a = new Matrix(tab);
        //a.print_matrix();
        /*double[][] t=a.asArray();
        //System.out.print(a.get(2,1));
        System.out.print( a.toString());
        //a.reshape(3,4);
        System.out.print( "\n" + a.toString());
        a.print_matrix();
        System.out.print(a.shape()[0]);*/
        double [][] tab1={{0,4}, {3,5}, {1,0}};
        Matrix m=new Matrix(tab1);
       // a.add(m);
        a.print_matrix();
        m.print_matrix();
        //System.out.print( "\n" + a.sub(m).toString());
        //a.sub(7);
        a.dot(m).print_matrix();
        System.out.print(m.frobenius());
        eye(5).print_matrix();
        Matrix r = Matrix.random(2,3);
        r.print_matrix();

    }
}
