package pl.edu.pw.mini.namefactory;

class ApiTopNames {
    private boolean is_male;
    private int top50[];

    public boolean is_male() {
        return is_male;
    }

    public int[] getTop50() {
        return top50;
    }

    public ApiTopNames(boolean is_male, int[] top50) {
        this.is_male = is_male;
        this.top50 = top50;
    }
}
