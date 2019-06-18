package game;

public class Gravity {
    private double val;

    /**
     *Konstruktor
     * @param val zmienna grawitacyjna
     */
   public Gravity(double val)
    {
        this.val = val;
    }

    /**
     * metoda ktora dodaje do grawitacji 0.1
     */
    public void incGravity() {
        val = val + 0.1;
    }

    /**
     *ustawiam grawitacje
     * @param val zmienna grawitacyjna
     */
    public void setGravity(double val) {
        this.val = val;
    }

    /**
     * biorę wartosc grawitacji
     * @return val zmienna grawitacyjna
     */
    public double getGravity() {
        return val;
    }
}
