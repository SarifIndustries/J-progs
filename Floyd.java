// Floyd-Warshall
// Find shortes distances in weighted graph
// using adjacency matrix.
public class Floyd {
    public static void main(String[] args) {
        int n = 4;
		int inf = 99;
		int[][] m;
		m = new int[n][n];
		// Matrix initialization
		for(int i=0; i<m.length; i++)
			for(int j=0; j<m.length; j++)
				if(i==j) m[i][j] = 0;
				else m[i][j] = inf;
		printMatrix(m);
		m[0][1]=20; m[0][3]=2;
		m[1][3]=10;
		m[2][0]=7;
		m[3][1]=2; m[3][2]=4;
		printMatrix(m);
		// Floyd, Warshall
		for(int k=0; k<m.length; k++) {
			for(int i=0; i<m.length; i++)
				for(int j=0; j<m.length; j++)
					if(m[i][j] > m[i][k] + m[k][j])
						m[i][j] = m[i][k] + m[k][j];
			printMatrix(m);
		}
    }
	
	static void printMatrix(int[][] m) {
		System.out.println("===============================");
		for(int i=0; i<m.length; i++) {
			for(int j=0; j<m.length; j++)
				System.out.print(String.valueOf(m[i][j]) + ' ');
			System.out.println('\n');
		}
	}
}
