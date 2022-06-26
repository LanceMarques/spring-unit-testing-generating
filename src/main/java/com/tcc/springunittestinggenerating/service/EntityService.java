package com.tcc.springunittestinggenerating.service;

import com.tcc.springunittestinggenerating.repository.EntityRepository;
import com.tcc.springunittestinggenerating.exceptions.EntityNotFoundException;
import com.tcc.springunittestinggenerating.model.EntityModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntityService {

    @Autowired
    EntityRepository repository;

    public List<EntityModel> listar(){
        return this.repository.findAll();
    }

    public EntityModel buscarPorId(final Integer id){
        Optional<EntityModel> modelOptional = this.repository.findById(id);

        if(!modelOptional.isPresent()){
            throw new EntityNotFoundException();
        }

        return modelOptional.get();
    }

    public EntityModel criar(final EntityModel model){
        return this.repository.save(model);
    }

    public void atualizar(final Integer id, final EntityModel newModel){
        final EntityModel model = this.buscarPorId(id);
        BeanUtils.copyProperties(newModel, model, "id");
        this.repository.save(model);
    }

    public void excluir(final Integer id){
        EntityModel model = this.buscarPorId(id);
        this.repository.delete(model);
    }

}
