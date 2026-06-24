package it.unicam.cs.mpgc.rpg129078;

public class Giocatore {

    private String nome;

    private int vitaMassima;
    private int vitaCorrente;

    private int energiaMassima;
    private int energiaCorrente;

    private Arma arma;
    private Abilita abilita;

    private Inventario inventario;


    public Giocatore(String nome, int vitaMassima, int energiaMassima, Arma arma, Abilita abilita) {
        this.nome = nome;

        this.vitaMassima = vitaMassima;
        this.vitaCorrente = vitaMassima;

        this.energiaMassima = energiaMassima;
        this.energiaCorrente = energiaMassima;

        this.arma = arma;
        this.abilita = abilita;

        this.inventario = new Inventario();
    }


    public String getNome() {
        return nome;
    }

    public int getVitaMassima() {
        return vitaMassima;
    }

    public int getVitaCorrente() {
        return vitaCorrente;
    }

    public int getEnergiaMassima() {
        return energiaMassima;
    }

    public int getEnergiaCorrente() {
        return energiaCorrente;
    }

    public Arma getArma() {
        return arma;
    }

    public Abilita getAbilita() {
        return abilita;
    }

    public Inventario getInventario() {
        return inventario;
    }


    public void setVitaCorrente(int vitaCorrente) {
        this.vitaCorrente = Math.max(0, Math.min(vitaCorrente, vitaMassima));
    }

    public void setEnergiaCorrente(int energiaCorrente) {
        this.energiaCorrente = Math.max(0, Math.min(energiaCorrente, energiaMassima));
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }

    public void setAbilita(Abilita abilita) {
        this.abilita = abilita;
    }


    public void setVitaMassima(int vitaMassima) {
        this.vitaMassima = vitaMassima;

        if (vitaCorrente > vitaMassima) {
            vitaCorrente = vitaMassima;
        }
    }


    public void attacca(Nemico nemico) {

        if (arma == null) {
            System.out.println("Nessuna arma equipaggiata!");
            return;
        }

        int danno = arma.calcolaDanno();

        nemico.setVitaCorrente(
                nemico.getVitaCorrente() - danno
        );

        System.out.println("Attacco inflitto: " + danno);
    }

}


