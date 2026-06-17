package it.unicam.cs.mpgc.rpg129078;

public class Giocatore {

    private String nome;

    private int vitaMassima;
    private int vitaCorrente;

    private int energiaMassima;
    private int energiaCorrente;

    public Giocatore(String nome, int vitaMassima, int energiaMassima) {
        this.nome = nome;

        this.vitaMassima = vitaMassima;
        this.vitaCorrente = vitaMassima;

        this.energiaMassima = energiaMassima;
        this.energiaCorrente = energiaMassima;
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


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setVitaMassima(int vitaMassima) {
        this.vitaMassima = vitaMassima;
    }

    public void setVitaCorrente(int vitaCorrente) {
        this.vitaCorrente = vitaCorrente;
    }

    public void setEnergiaMassima(int energiaMassima) {
        this.energiaMassima = energiaMassima;
    }

    public void setEnergiaCorrente(int energiaCorrente) {
        this.energiaCorrente = energiaCorrente;
    }


}


