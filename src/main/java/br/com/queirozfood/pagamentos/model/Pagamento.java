package br.com.queirozfood.pagamentos.model;

import br.com.queirozfood.pagamentos.enums.PagamentoStatus;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Positive
    private BigDecimal valor;
    @NotBlank
    @Size(max = 100)
    private String nome;
    @NotBlank
    @Size(max = 7)
    private String numero;
    private String expiracao;
    @NotBlank
    @Size(min = 3, max = 3)
    private String codigo;
    @NotNull
    @Enumerated(EnumType.STRING)
    private PagamentoStatus status;
    @NotNull
    private Long pedidoId;
    @NotNull
    private Long formaDePagamentoId;

}
