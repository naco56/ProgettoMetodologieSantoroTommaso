package it.unicam.cs.mpgc.rpg129078.model;

import it.unicam.cs.mpgc.rpg129078.model.abilita.Abilita;
import it.unicam.cs.mpgc.rpg129078.model.arma.Arma;
import it.unicam.cs.mpgc.rpg129078.model.arma.Potenziabile;

public class Giocatore {

    private String nome;

    private int vitaMassima;
    private int vitaCorrente;

    private int energiaMassima;
    private int energiaCorrente;

    private Arma arma;
    private Abilita abilita;

    private Inventario inventario;

    private int livello;
    private int esperienzaCorrente;
    private int esperienzaPerLivello;


    public Giocatore(String nome, int vitaMassima, int energiaMassima, Arma arma, Abilita abilita, Inventario inventario) {
        this.nome = nome;

        this.vitaMassima = vitaMassima;
        this.vitaCorrente = vitaMassima;

        this.energiaMassima = energiaMassima;
        this.energiaCorrente = energiaMassima;

        this.arma = arma;
        this.abilita = abilita;

        this.inventario = inventario;

        this.livello = 1;
        this.esperienzaCorrente = 0;
        this.esperienzaPerLivello = 30;
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

    public int getLivello() {
        return livello;
    }

    public int getEsperienzaCorrente() {
        return esperienzaCorrente;
    }

    public int getEsperienzaPerLivello() {
        return esperienzaPerLivello;
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

    public void setLivello(int livello) {
        this.livello = livello;
    }

    public void setEsperienzaCorrente(int exp) {
        this.esperienzaCorrente = exp;
    }

    public void setEsperienzaPerLivello(int exp) {
        this.esperienzaPerLivello = exp;
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


    public boolean guadagnaEsperienza(int quantita) {
        esperienzaCorrente += quantita;
        if (esperienzaCorrente >= esperienzaPerLivello) {
            aumentoLivello();
            return true;
        }
        return false;
    }


    private void aumentoLivello() {
        livello++;
        esperienzaCorrente = 0;
        esperienzaPerLivello = (int) (esperienzaPerLivello * 1.5);

        vitaMassima += 15;
        vitaCorrente = vitaMassima; // cura completa al level up
        energiaMassima += 10;
        energiaCorrente = energiaMassima;


    }
}



