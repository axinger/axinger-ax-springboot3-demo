package com.ax.test.cllback;

public class Caller {
    public MyCallInterface mc;

    public void setCallfuc(MyCallInterface mc) {
        this.mc = mc;
    }

    public void call() {
        this.mc.method();
    }
}
