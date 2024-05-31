package com.example.wizardspotionshop.model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String usuario;
    private Integer pontuacao;
    private Boolean snake;
    private Boolean velha;
    private Boolean clicker;
    private Boolean livre;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public Boolean getSnake() {
        return snake;
    }

    public void setSnake(Boolean snake) {
        this.snake = snake;
    }

    public Boolean getVelha() {
        return velha;
    }

    public void setVelha(Boolean velha) {
        this.velha = velha;
    }

    public Boolean getClicker() {
        return clicker;
    }

    public void setClicker(Boolean clicker) {
        this.clicker = clicker;
    }

    public Boolean getLivre() {
        return livre;
    }

    public void setLivre(Boolean livre) {
        this.livre = livre;
    }
}
