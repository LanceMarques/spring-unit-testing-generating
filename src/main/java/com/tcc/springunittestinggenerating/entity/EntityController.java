package com.tcc.springunittestinggenerating.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entity")
public class EntityController {

    @Autowired
    private EntityService service;

    @GetMapping
    public ResponseEntity<List<EntityModel>> listar(){
        final List<EntityModel> entityModels = this.service.listar();
        return ResponseEntity.status(HttpStatus.OK).body(entityModels);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EntityModel> buscarPorId(@PathVariable(name = "id") final Integer id){
        final EntityModel model = this.service.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @PostMapping
    public ResponseEntity<EntityModel> criar (@RequestBody final EntityModel newModel){
        final EntityModel model = this.service.criar(newModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> atualizar(@PathVariable(name = "id") final Integer id, @RequestBody final EntityModel newModel){

        this.service.atualizar(id, newModel);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> excluir(@PathVariable(name = "id") final Integer id){

        this.service.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
