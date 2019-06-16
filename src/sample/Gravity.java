package sample;

public class Gravity
{
    private double val;
    Gravity(double val)
    {
        this.val = val;
    }

    public void incGravity() { val = val + 0.1; }
    public void setGravity(double val) { this.val = val;}
    public double getGravity() { return val; }
}
