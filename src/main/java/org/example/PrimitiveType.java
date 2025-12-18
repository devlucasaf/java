package org.example;

public class PrimitiveType {
    public static void main(String[] args) {
        var nome = "Marcelo";
        var n2 ="j";
        var idade = 16;
        var peso = 113.15;
        var casado = true;
        nome = n2;

        //declaração antiga
        //String nome = "Marcelo";
        //int idade = 34;
        //double peso = 113.15;
        //boolean casado = true;

        if(idade <= 18){
            System.out.printf("Olá %s você é Novo\n",nome);
            System.out.printf("Olá %s você pesa %s",nome,peso);
        } else {
            System.out.printf(" %s você é velho.",nome);

        }
    }
}
