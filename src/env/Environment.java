package env;

import agent.Robot;

public class Environment {
    /**
     * s1 = esquina superior izquierda, s3 = esquina superior derecha,
     * s5 = esquina inferior derecha, s7 = esquina inferior izquierda
     * S = (s1, s2, s3, s4, s5, s6, s7, s8)
     * X = (x1, x2, x3, x4)
     */
    private boolean[] inputs;
    private static final int INPUTS = 8;
    private Robot robot;

    public Environment() {
        this.inputs = new boolean[INPUTS];
    }

    public void update(boolean[] inputs) {
        if (inputs.length != INPUTS) {
            throw new IllegalArgumentException("Invalid inputs length");
        }
        this.inputs = inputs;
    }

}
