package br.edu.ifsp.prw3.prw3_Avaliacao2.controller;

import br.edu.ifsp.prw3.prw3_Avaliacao2.conserto.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("consertos")
public class ConsertoController {

    @Autowired
    private ConsertoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(
            @RequestBody @Valid DadosCadastroConserto dados,
            UriComponentsBuilder uriBuilder) {

        var conserto = new Conserto(dados);

        repository.save(conserto);

        var uri = uriBuilder
                .path("/consertos/{id}")
                .buildAndExpand(conserto.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(new DadosDetalhamentoConserto(conserto));
    }

    @GetMapping
    public ResponseEntity listar(Pageable paginacao) {

        var pagina = repository
                .findAll(paginacao)
                .map(DadosDetalhamentoConserto::new);

        return ResponseEntity.ok(pagina);
    }

    @GetMapping("algunsdados")
    public ResponseEntity listarAlgunsDados() {

        var lista = repository
                .findAllByAtivoTrue()
                .stream()
                .map(DadosListagemConserto::new)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoConserto> buscarPorId(
            @PathVariable Long id) {

        var opcional = repository.findById(id);

        if (opcional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                new DadosDetalhamentoConserto(opcional.get())
        );
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(
            @RequestBody @Valid DadosAtualizacaoConserto dados) {

        var opcional = repository.findById(dados.id());

        if (opcional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var conserto = opcional.get();

        conserto.atualizarInformacoes(dados);

        return ResponseEntity.ok(
                new DadosDetalhamentoConserto(conserto)
        );
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {

        var opcional = repository.findById(id);

        if (opcional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        opcional.get().excluir();

        return ResponseEntity.noContent().build();
    }
}