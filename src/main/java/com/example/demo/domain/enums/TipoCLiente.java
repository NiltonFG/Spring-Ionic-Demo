package com.example.demo.domain.enums;

public enum TipoCLiente {
  PESSOAFISICA(1,"Pessoa física"),
  PESSOAJURIDICA(2,"Pessoa jurídica");

  private int cod;
  private String descricao;

  private TipoCLiente(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
  }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoCLiente toEnum(Integer cod){
        if(cod == null)
            return null;
        for (TipoCLiente x: TipoCLiente.values()) {
            if(cod.equals(x.getCod()))
                return x;
        }
        throw new IllegalArgumentException("Id inválido: "+ cod);
    }
}
