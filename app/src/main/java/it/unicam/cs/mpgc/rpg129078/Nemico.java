package it.unicam.cs.mpgc.rpg129078;

public class Nemico {

    private String nome;

    private int vitaMassima;
    private int vitaCorrente;

    private int attacco;

    private TipoNemico tipo;

    public Nemico(String nome, int vitaMassima, int attacco, TipoNemico tipo) {
        this.nome = nome;

        this.vitaMassima = Math.max(0, vitaMassima);
        this.vitaCorrente = this.vitaMassima;

        this.attacco = Math.max(0, attacco);

        this.tipo = tipo;
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

    public int getAttacco() {
        return attacco;
    }

    public TipoNemico getTipo() {
        return tipo;
    }

    // ===== SETTER =====

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setVitaMassima(int vitaMassima) {
        this.vitaMassima = Math.max(0, vitaMassima);
    }

    public void setVitaCorrente(int vitaCorrente) {
        if (vitaCorrente < 0) {
            this.vitaCorrente = 0;
        } else if (vitaCorrente > vitaMassima) {
            this.vitaCorrente = vitaMassima;
        } else {
            this.vitaCorrente = vitaCorrente;
        }
    }

    public void setAttacco(int attacco) {
        this.attacco = Math.max(0, attacco);
    }

    public void setTipo(TipoNemico tipo) {
        this.tipo = tipo;
    }


}
