package br.edu.ifsp.prw3.prw3_Avaliacao2.mecanico;

import jakarta.validation.constraints.NotBlank;

public record DadosMecanico(

        @NotBlank
        String nome,

        Integer anosExperiencia) {
}