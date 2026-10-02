package br.edu.ifsp.prw3.prw3_Avaliacao2.conserto;

import br.edu.ifsp.prw3.prw3_Avaliacao2.mecanico.Mecanico;
import br.edu.ifsp.prw3.prw3_Avaliacao2.veiculo.Veiculo;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "consertos")
@Entity(name = "Conserto")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Conserto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataEntrada;
    private String dataSaida;

    @Embedded
    private Mecanico mecanico;

    @Embedded
    private Veiculo veiculo;

    private Boolean ativo;

    public Conserto(DadosCadastroConserto dados) {
        this.dataEntrada = dados.dataEntrada();
        this.dataSaida = dados.dataSaida();
        this.mecanico = new Mecanico(dados.mecanico());
        this.veiculo = new Veiculo(dados.veiculo());
        this.ativo = true;
    }

    public void atualizarInformacoes(DadosAtualizacaoConserto dados) {

        if (dados.dataSaida() != null) {
            this.dataSaida = dados.dataSaida();
        }

        if (dados.nomeMecanico() != null) {
            this.mecanico.atualizarNome(dados.nomeMecanico());
        }

        if (dados.anosExperienciaMecanico() != null) {
            this.mecanico.atualizarAnosExperiencia(
                    dados.anosExperienciaMecanico());
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
