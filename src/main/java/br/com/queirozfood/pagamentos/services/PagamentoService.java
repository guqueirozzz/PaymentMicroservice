package br.com.queirozfood.pagamentos.services;

import br.com.queirozfood.pagamentos.dto.PagamentoDTO;
import br.com.queirozfood.pagamentos.enums.PagamentoStatus;
import br.com.queirozfood.pagamentos.model.Pagamento;
import br.com.queirozfood.pagamentos.repositories.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PagamentoDTO> obterTodos(Pageable paginacao) {

        return pagamentoRepository
                .findAll(paginacao)
                .map(p -> modelMapper.map(p, PagamentoDTO.class));
    }

    public PagamentoDTO obterPorId(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public PagamentoDTO criarPagamento(PagamentoDTO pagamentoDTO) {

        // O JPA REPOSITORY SO ENTENDE A CLASSE PAGAMENTO e não PagamentoDTO
        Pagamento pagamento = modelMapper.map(pagamentoDTO, Pagamento.class);
        pagamento.setStatus(PagamentoStatus.CRIADO);
        pagamentoRepository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public PagamentoDTO atualizarPagamento(Long id, PagamentoDTO pagamentoDTO) {

        Pagamento pagamento = modelMapper.map(pagamentoDTO, Pagamento.class);
        pagamento.setId(id);
        pagamento = pagamentoRepository.save(pagamento);

        PagamentoDTO pagamentODTO = modelMapper.map(pagamento, PagamentoDTO.class);
        return pagamentoDTO;
    }

    public void excluirPagamento(Long id) {

        pagamentoRepository.deleteById(id);
    }
}
