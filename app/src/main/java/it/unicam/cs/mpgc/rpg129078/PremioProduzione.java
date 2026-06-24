package it.unicam.cs.mpgc.rpg129078;

public class PremioProduzione implements Abilita {

    @Override
    public String nome() {
        return "Premio di Produzione";
    }

    @Override
    public int effetto() {
        return 10;
    }

    @Override
    public int costoEnergia() {
        return 20;
    }

    public void usa(Giocatore giocatore, Nemico nemico) {

        int danno = effetto();

        nemico.setVitaCorrente(
                nemico.getVitaCorrente() - danno
        );

        giocatore.setVitaCorrente(
                giocatore.getVitaCorrente() + (danno / 2)
        );

    }


}