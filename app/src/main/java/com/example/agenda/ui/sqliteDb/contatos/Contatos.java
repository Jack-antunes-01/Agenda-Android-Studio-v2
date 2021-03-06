package com.example.agenda.ui.sqliteDb.contatos;

public class Contatos {
    private long id;
    private long ra;
    private String nome;
    private String apelido;
    private long celular;
    private long whatsapp;
    private long telegram;
    private String nascimento;
    private String facebook;
    private String twitter;
    private String instagram;
    private String email;
    private byte[] foto;
    private int status;

    public Contatos(long id, long ra, String nome, String apelido, long celular, long whatsapp,
                    long telegram, String nascimento, String facebook, String twitter, String instagram,
                    String email, int status, byte[] foto)
    {
        this.id = id;
        this.ra = ra;
        this.nome = nome;
        this.apelido = apelido;
        this.celular = celular;
        this.whatsapp = whatsapp;
        this.telegram = telegram;
        this.nascimento = nascimento;
        this.facebook = facebook;
        this.twitter = twitter;
        this.instagram = instagram;
        this.email = email;
        this.foto = foto;
        this.status = status;
    }

    public long getId(){
        return this.id;
    }
    public long getRa(){
        return this.ra;
    }
    public String getNome (){
        return this.nome;
    }
    public String getApelido (){
        return this.apelido;
    }
    public long getCelular (){
        return this.celular;
    }
    public long getWhatsapp (){
        return this.whatsapp;
    }
    public long getTelegram (){
        return this.telegram;
    }
    public String getNascimento (){
        return this.nascimento;
    }
    public String getFacebook (){
        return this.facebook;
    }
    public String getTwitter (){
        return this.twitter;
    }
    public String getInstagram (){
        return this.instagram;
    }
    public String getEmail (){
        return this.email;
    }
    public byte[] getFoto (){
        return this.foto;
    }
    public int getStatus (){
        return this.status;
    }

    @Override
    public boolean equals(Object o){
        return this.id == ((Contatos)o).id;
    }

    @Override
    public int hashCode(){
        return Integer.valueOf((int) this.id);
    }
}
