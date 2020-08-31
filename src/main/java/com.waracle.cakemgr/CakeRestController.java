package com.waracle.cakemgr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RestController
public class CakeRestController {

    @Autowired
    private CakeRepository cakeRepo;

    @GetMapping("/")
    public String getAllCakesFromRoot() {
        return cakeRepo.findAll().stream().map(cakeEntity -> {
            StringBuffer sb = new StringBuffer();
            sb.append("\t\t\"id\" : " + cakeEntity.getId() + " , ");
            sb.append("\t\t\"title\" : " + cakeEntity.getTitle() + " , ");
            sb.append("\t\t\"description\" : " + cakeEntity.getDesc() + " , ");
            sb.append("\t\t\"image\" : " + cakeEntity.getImage());
            return sb.toString();
        }).collect(Collectors.joining("<br>"));
    }

    @PostMapping(value={"/", "/cakes"})
    public CakeEntity createCake(@RequestBody CakeEntity cake) {
        return cakeRepo.save(cake);
    }

    @GetMapping("/cakes")
    public ResponseEntity<List<CakeEntity>> getAllCakes() {
        return ResponseEntity.ok().body(cakeRepo.findAll());
    }

    @GetMapping("/cakes/{id}")
    public ResponseEntity<CakeEntity> getCake(@PathVariable(value = "id") Integer cakeId) {
        CakeEntity cakeEntity = cakeRepo.findById(cakeId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("cakeId %s Not Found", cakeId)));
        return ResponseEntity.ok().body(cakeEntity);
    }

    @DeleteMapping("/cakes/{id}")
    public void deleteCake(@PathVariable(value = "id") Integer cakeId) {
        CakeEntity cakeEntity = cakeRepo.findById(cakeId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("cakeId %s cannot be deleted as it doesn't exist", cakeId)));
        cakeRepo.deleteById(cakeId);
    }

}