package com.waracle.cakemgr;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(StartupApplicationListener.class);

    @Value("classpath:cakes.json")
    private Resource resourceFile;

    @Autowired
    private CakeRepository cakeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override public void onApplicationEvent(ContextRefreshedEvent event) {

       try (InputStream inputStream = resourceFile.getInputStream()) {

           BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
           String lines = reader.lines().collect(Collectors.joining());
           LOG.info("Loading cakes from cakes.json");
           List<CakeEntity> cakes = objectMapper.readValue(lines, new TypeReference<List<CakeEntity>>(){});
           LOG.info("Loaded {} cakes from cakes.json ", cakes.size());
           for (CakeEntity cake : cakes) {
               LOG.info("Cake loaded {} ", objectMapper.writeValueAsString(cake));
               cakeRepository.save(cake);
           }
       } catch (Exception e) {
           throw new RuntimeException(e);
       }

    }
}