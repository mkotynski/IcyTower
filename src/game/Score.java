package game;

public class Score {
    private String who;
    private int fullScore;

    public Score(String who, int fullScore)
    {
        this.who = who;
        this.fullScore = fullScore;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public int getFullScore() {
        return fullScore;
    }

    public void setFullScore(int fullScore) {
        this.fullScore = fullScore;
    }

    public static void b_sort(Score tab[]){
        int temp;
        String tempName;
        int zmiana = 1;
        while(zmiana > 0){
            zmiana = 0;
            for(int i=0; i<tab.length-1; i++){
                if(tab[i].getFullScore()>tab[i+1].getFullScore()){
                    temp = tab[i+1].getFullScore();
                    tempName = tab[i+1].getWho();
                    tab[i+1].setFullScore(tab[i].getFullScore());
                    tab[i+1].setWho(tab[i].getWho());
                    tab[i].setWho(tempName);
                    tab[i].setFullScore(temp);
                    zmiana++;
                }
            }
        }

    }

    public static void odwroc(Score[] wejscie) {

        // indeks pierwszego elementu
        int poczatek  = 0;
        // indeks ostatniego elementu
        int koniec = wejscie.length-1;

        // dopóki indeks początkowy jest mniejszy od indeksu końcowego
        while (poczatek < koniec) {
            // zamieniamy elementy
            int pomoc = wejscie[poczatek].getFullScore();
            String name = wejscie[poczatek].getWho();
            wejscie[poczatek].setFullScore(wejscie[koniec].getFullScore());
            wejscie[poczatek].setWho(wejscie[koniec].getWho());
            wejscie[koniec].setFullScore(pomoc);
            wejscie[koniec].setWho(name);
            // przesuwamy się w kierunku środka wektora zwiększając i zmniejszając odpowiednio indeksy
            poczatek++;
            koniec--;
        }
    }
}
