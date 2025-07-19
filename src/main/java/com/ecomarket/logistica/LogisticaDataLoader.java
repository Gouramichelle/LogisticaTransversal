package com.ecomarket.logistica;

import com.ecomarket.logistica.model.LogisticaEntity;
import com.ecomarket.logistica.repository.LogisticaRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.stream.IntStream;

@Configuration
public class LogisticaDataLoader {

    @Bean
    public CommandLineRunner loadLogistica(LogisticaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Faker faker = new Faker(new Locale("es"));

                IntStream.range(0, 20).forEach(i -> {
                    LogisticaEntity logistica = new LogisticaEntity();
                    logistica.setId(faker.number().numberBetween(1, 50));
                    logistica.setIdFactura(faker.number().numberBetween(1, 50));
                    logistica.setEstado(faker.options().option("En camino", "Entregado", "Pendiente", "Devuelto"));

                    repository.save(logistica);
                });

                System.out.println(" Se generaron 20 registros de logística con DataFaker.");
            }
        };
    }
}