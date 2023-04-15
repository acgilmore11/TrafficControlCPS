package entity;

public class functions {
	static int getVehiclesOverInterval(double total, double a, double b) {
		double k = total / trapezoidal(1.0, 0.0, Global.DAILY_SECONDS);
		return (int) trapezoidal(k, a, b);
	}
	
	//utility function: computes integral between upper and lower limit using trapezoidal integration strategy
	private static double trapezoidal(double k, double a, double b) {
        double deltX = 1;
        double sum = 0;
        
        double currX = a;
        
        while (currX <= b) {
            if (currX == a || currX == b) {
                sum += vArrivalFunc(k, currX);
            } else {
                sum += 2 * vArrivalFunc(k, currX);
            }
            currX += deltX;
        }
        
       
        return (deltX / 2) * sum;
    }
	
	private static double vArrivalFunc(double k, double x) {
		double res = k * (3 * Math.sin((4*Math.PI/Global.DAILY_SECONDS)*x - 1) + 3.5);
        return res;
	}
	
	public static void main(String[] args) {
		System.out.println(getVehiclesOverInterval(Global.TOTAL_NUMV_LANE, 0, 86400));
		
		for (int i = 0; i < 100; i++) {
			System.out.println("Total number of vehicles arriving from start to " + i + ": " + getVehiclesOverInterval(Global.TOTAL_NUMV_LANE, 0, i));
		}
		
	}
}
