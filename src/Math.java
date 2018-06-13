public class Math {
    public static int abs(int a) {
        return (a < 0) ? -a : a;
    }
    
    public static int compare(int a, int b) {
        if (a < b) {
            return -1;
        } else if (a == b) {
            return 0;
        } else {
            return 1;
        }
    }
    
    public static int factorial(int a) {
        if (a < 0) {
            throw new IllegalArgumentException("Negative factorial");
        }
        if (a == 0 || a == 1) {
            return 1;
        }
        return a * Math.factorial(a - 1);
    }
    
    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return Math.gcd(b, a % b);
        }
    }
    
    public static int lcm(int a, int b) {
        return a / Math.gcd(a, b) * b;
    }
    
    public static int max(int a, int b) {
        return (a < b) ? b : a;
    }
    
    public static int min(int a, int b) {
        return (a < b) ? a : b;
    }
    
    public static int mod(int a, int b) {
        return a % b;
    }
    
    public static int negate(int a) {
        return -a;
    }
    
    public static int pow(int a, int b) {
        if (b < 0) {
            throw new IllegalArgumentException("Unsupported Operation");
        } else if (b == 0) {
            return 1;
        } else {
            int result = 1;
            for (int i = 0; i < b; i++) {
                result *= a;
            }
            return result;
        }
    }
    
    public static int signum(int a) {
        return Math.compare(a, 0);
    }
}
