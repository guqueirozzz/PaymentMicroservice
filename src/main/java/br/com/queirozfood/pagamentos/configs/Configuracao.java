package br.com.queirozfood.pagamentos.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configuracao {

    @Bean
    public ModelMapper getModelMapper() {

        return new ModelMapper();
    }
}
