package com.example.wizardspotionshop.helper;

public interface IUsuarioDAO {
    public boolean registrar(String usuario, String senha);
    public boolean entrar(String usuario, String senha);
    public boolean verificarLogado();
    public String retornaUsuarioLog();
    public boolean sair();
}
