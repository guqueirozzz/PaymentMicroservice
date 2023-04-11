package br.com.queirozfood.pagamentos.controllers;

import br.com.queirozfood.pagamentos.dto.PagamentoDTO;
import br.com.queirozfood.pagamentos.services.PagamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public Page<PagamentoDTO> obterTodos(@PageableDefault(size = 10) Pageable paginacao) {

        return pagamentoService.obterTodos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> obterPorId(@PathVariable @NotNull Long id) {

        PagamentoDTO pagamentoDTO = pagamentoService.obterPorId(id);
        return ResponseEntity.ok(pagamentoDTO);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> cadastrar(@RequestBody @Valid PagamentoDTO dto, UriComponentsBuilder uriBuilder) {

        PagamentoDTO pagamentoDTO = pagamentoService.criarPagamento(dto);
        URI endereco = uriBuilder.path("/pagamentos/{id}").buildAndExpand(pagamentoDTO.getId()).toUri();

        return ResponseEntity.created(endereco).body(pagamentoDTO);
    }

    @PutMapping
    public ResponseEntity<PagamentoDTO> atualizar(@PathVariable @NotNull Long id, @RequestBody @Valid PagamentoDTO dto) {

        PagamentoDTO atualizado = pagamentoService.atualizarPagamento(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDTO> remover(@PathVariable @NotNull Long id) {

        pagamentoService.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }

}
