package agent;

public record Rule(boolean s1, boolean s2, boolean s3, boolean s4, boolean s5, boolean s6, boolean s7, boolean s8,
        Direction direction) {

    public boolean matches(boolean s1, boolean s2, boolean s3, boolean s4, boolean s5, boolean s6, boolean s7,
            boolean s8) {
        return this.s1 == s1 && this.s2 == s2 && this.s3 == s3 && this.s4 == s4 && this.s5 == s5 && this.s6 == s6
                && this.s7 == s7 && this.s8 == s8;
    }
}
