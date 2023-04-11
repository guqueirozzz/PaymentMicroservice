package br.com.queirozfood.pagamentos.repositories;

import br.com.queirozfood.pagamentos.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

}
