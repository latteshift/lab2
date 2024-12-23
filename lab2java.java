import java.util.Random;

public class lab2java {
    public static void main(String[] args) {
        System.out.println("\nЛабораторна робота №2\nВиконала студентка групи КМ-21 Марченко Єва\n");

                Matrix matrix = Matrix.generateRandomMatrix();
        System.out.println("Матриця:");
        matrix.print();

        Matrix copyMatrix = new Matrix(matrix);
        System.out.println("\nКопія матриці:");
        copyMatrix.print();

        matrix.fillWithRandomValues();
        System.out.println("\nМатриця з випадковими величинами:");
        matrix.print();

        System.out.print("Рядок: [");
        for (int i = 0; i < 3; i++) {
            System.out.print(String.format("%.2f", matrix.getElement(1, i)));
            if (i < 2) System.out.print(", ");
        }
        System.out.println("]");
        System.out.print("Стовпчик: [");
        for (int i = 0; i < 3; i++) {
            System.out.print(String.format("%.2f", matrix.getElement(i, 2)));
            if (i < 2) System.out.print(", ");
        }
        System.out.println("]");
        System.out.println("Заданий елемент (1, 1): " + String.format("%.2f", matrix.getElement(1, 1)));

        System.out.println("\nРозмірність матриці: " + matrix.getRows() + "x" + matrix.getCols());

        System.out.println("\nМетод equals: " + matrix.equals(copyMatrix));

        System.out.println("\nМетод hashCode: " + matrix.hashCode());

        System.out.println("\nМатриця + копія матриці:");
        Matrix sumMatrix = matrix.add(copyMatrix);
        sumMatrix.print();

        System.out.print("\nВведіть скаляр для множення на матрицю: ");
                java.util.Scanner scanner = new java.util.Scanner(System.in);
                double scalar = scanner.nextDouble();
        Matrix scaledMatrix = matrix.multiplyByScalar(scalar);
        scaledMatrix.print();

        System.out.println("\nМатриця * копія матриці:");
        Matrix multipliedMatrix = matrix.multiply(copyMatrix);
        multipliedMatrix.print();

        System.out.println("\nДіагональна матриця (задана вектором):");
        System.out.print("Введіть 3 елементи вектора (через пробіл): ");
        double[] vector = new double[3];
        for (int i = 0; i < 3; i++) {
            vector[i] = scanner.nextDouble();
        }
        Matrix diagonalMatrix = Matrix.createDiagonalMatrix(vector);
        diagonalMatrix.print();

        System.out.println("\nОдинична матриця:");
        Matrix identityMatrix = Matrix.createIdentityMatrix();
        identityMatrix.print();

        System.out.println("\nОбернена матриця:");
            Matrix inverseMatrix = matrix.inverse();
            inverseMatrix.print();


        System.out.println("\nТранспонована матриця:");
        Matrix transposedMatrix = matrix.transpose();
        transposedMatrix.print();
    }
}

class Matrix {
    public int hashCode() {
        return rows * cols;
    }
    double[][] data;
    int rows;
    int cols;

    public Matrix(double[][] data) {
        this.rows = 3;
        this.cols = 3;
        this.data = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i][j] = data[i][j];
            }
        }
    }

    public Matrix(Matrix other) {
        this(other.data);
    }
    
    public static Matrix generateRandomMatrix() {
        Random random = new Random();
        double[][] data = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                data[i][j] = random.nextDouble() * 10;
            }
        }
        return new Matrix(data);
    }
    

    public void fillWithRandomValues() {
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.data[i][j] = random.nextDouble() * 10;
            }
        }
    }

    public static Matrix createDiagonalMatrix(double[] vector) {
        double[][] data = new double[3][3];
        for (int i = 0; i < 3; i++) {
            data[i][i] = vector[i];
        }
        return new Matrix(data);
    }

    public static Matrix createIdentityMatrix() {
        double[][] data = new double[3][3];
        for (int i = 0; i < 3; i++) {
            data[i][i] = 1.0;
        }
        return new Matrix(data);
    }

    public Matrix inverse() {
        double[][] augmented = new double[3][6];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                augmented[i][j] = data[i][j];
            }
            augmented[i][i + 3] = 1.0;
        }
        for (int i = 0; i < 3; i++) {
            double diagElement = augmented[i][i];
            for (int j = 0; j < 6; j++) {
                augmented[i][j] /= diagElement;
            }
            for (int k = 0; k < 3; k++) {
                if (k != i) {
                    double factor = augmented[k][i];
                    for (int j = 0; j < 6; j++) {
                        augmented[k][j] -= factor * augmented[i][j];
                    }
                }
            }
        }
        double[][] inverseData = new double[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(augmented[i], 3, inverseData[i], 0, 3);
        }
        return new Matrix(inverseData);
    }

    public double[] getRow(int index) {
        double[] row = new double[3];
        for (int i = 0; i < 3; i++) {
            row[i] = this.data[index][i];
        }
        return row;
    }

    public double[] getColumn(int index) {
        double[] column = new double[3];
        for (int i = 0; i < 3; i++) {
            column[i] = this.data[i][index];
        }
        return column;
    }

    public double getElement(int row, int col) {
        return this.data[row][col];
    }

    public int getRows() {
        return this.rows;
    }

    public int getCols() {
        return this.cols;
    }

    public Matrix add(Matrix other) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.data[i][j] + other.data[i][j];
            }
        }
        return new Matrix(result);
    }

    public Matrix multiplyByScalar(double scalar) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.data[i][j] * scalar;
            }
        }
        return new Matrix(result);
    }

    public Matrix multiply(Matrix other) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    result[i][j] += this.data[i][k] * other.data[k][j];
                }
            }
        }
        return new Matrix(result);
    }

    public Matrix transpose() {
        double[][] transposed = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                transposed[j][i] = this.data[i][j];
            }
        }
        return new Matrix(transposed);
    }

    public void print() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(String.format("%.2f ", this.data[i][j]));
            }
            System.out.println();
        }
    }
}
