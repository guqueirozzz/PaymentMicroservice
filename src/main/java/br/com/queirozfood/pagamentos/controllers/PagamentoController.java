package br.com.queirozfood.pagamentos.controllers;

import br.com.queirozfood.pagamentos.dto.PagamentoDTO;
import br.com.queirozfood.pagamentos.services.PagamentoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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

        Message message = new Message(("Criei um pagamento com o id " + pagamentoDTO.getId()).getBytes());
        rabbitTemplate.convertAndSend("pagamentos.exchange", "", pagamentoDTO); // removendo a routkey por conta da exchange

        return ResponseEntity.created(endereco).body(pagamentoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> atualizar(@PathVariable @NotNull Long id, @RequestBody @Valid PagamentoDTO dto) {

        PagamentoDTO atualizado = pagamentoService.atualizarPagamento(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}/confirmar")
    @CircuitBreaker(name = "atualizaPedido", fallbackMethod = "pagamentoAutorizadoComIntegracaoPendente")
    public ResponseEntity<PagamentoDTO> confirmarPagamento(@PathVariable @NotNull Long id) {

        PagamentoDTO dto = pagamentoService.confirmarPagamento(id);
        return ResponseEntity.ok().body(dto);
    }

    public ResponseEntity<PagamentoDTO> pagamentoAutorizadoComIntegracaoPendente(Long id, Exception e) {

        PagamentoDTO dto = pagamentoService.alteraStatus(id);
        return ResponseEntity.ok().body(dto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDTO> remover(@PathVariable @NotNull Long id) {

        pagamentoService.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }

}
