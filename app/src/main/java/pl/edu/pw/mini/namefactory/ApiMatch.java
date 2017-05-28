package pl.edu.pw.mini.namefactory;

class ApiMatch {
    private int name_id1;
    private int name_id2;

    public int getName_id1() {
        return name_id1;
    }

    public int getName_id2() {
        return name_id2;
    }

    public ApiMatch(int name_id1, int name_id2) {
        this.name_id1 = name_id1;
        this.name_id2 = name_id2;
    }
}
