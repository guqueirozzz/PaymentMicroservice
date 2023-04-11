package br.com.queirozfood.pagamentos.dto;

import br.com.queirozfood.pagamentos.enums.PagamentoStatus;
import br.com.queirozfood.pagamentos.model.Pagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagamentoDTO {

    private  Long id;
    private  BigDecimal valor;
    private  String nome;
    private  String numero;
    private  String expiracao;
    private  String codigo;
    private  PagamentoStatus status;
    private  Long pedidoId;
    private  Long formaDePagamentoId;

    public PagamentoDTO() {
    }


//    public static PagamentoDTO fromDomain(Pagamento pagamento) {
//
//        return new PagamentoDTO(
//                id = pagamento.getId(),
//                valor = pagamento.getValor(),
//                nome = pagamento.getNome(),
//                numero = pagamento.getNumero(),
//                expiracao = pagamento.getExpiracao(),
//                codigo = pagamento.getCodigo(),
//                status = pagamento.getStatus(),
//                pedidoId = pagamento.getPedidoId(),
//                formaDePagamentoId = pagamento.getFormaDePagamentoId()
//        );
//    }

    public Pagamento teste(Pagamento pagamento) {

        return new Pagamento(
                id = pagamento.getId(),
                valor = pagamento.getValor(),
                nome = pagamento.getNome(),
                numero = pagamento.getNumero(),
                expiracao = pagamento.getExpiracao(),
                codigo = pagamento.getCodigo(),
                status = pagamento.getStatus(),
                pedidoId = pagamento.getPedidoId(),
                formaDePagamentoId = pagamento.getFormaDePagamentoId()
        );
    }
}
