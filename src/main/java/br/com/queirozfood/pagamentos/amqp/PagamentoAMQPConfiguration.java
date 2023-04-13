package br.com.queirozfood.pagamentos.amqp;


import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;

@Configuration
public class PagamentoAMQPConfiguration {

    @Bean
    public Queue criaFila() {

        return new Queue("pagamento.concluido", false);
        // return QueueBuilder.nonDurable("pagamento.concluido").build();
        // faz a mesma coisa que o primeiro return
    }

    @Bean
    public RabbitAdmin criaRabbitAdmin(ConnectionFactory connectionFactory) {

        return new RabbitAdmin(connectionFactory);
    }


    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializaRabbit(RabbitAdmin rabbitAdmin) {

        return event -> rabbitAdmin.initialize();
    }

}
