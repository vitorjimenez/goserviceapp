package com.soulcode.goserviceapp.service;

import com.soulcode.goserviceapp.domain.Servico;
import com.soulcode.goserviceapp.repository.ServicoRepository;
import com.soulcode.goserviceapp.service.exceptions.ServicoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public List<Servico> findAll(){
        return servicoRepository.findAll();
    }

    public Servico findById(Long id){
        Optional<Servico> servico = servicoRepository.findById(id);
        if(servico.isPresent()){
            return servico.get();
        }
        else{
            throw new ServicoNaoEncontradoException();
        }
    }

    public Servico createServico(Servico servico){
        servico.setId(null);
        return servicoRepository.save(servico);
    }

    public void removeServicoById(Long id){
        servicoRepository.deleteById(id);
    }

    public Servico update (Servico servico){
        Servico updateServico = this.findById(servico.getId());
        updateServico.setId(servico.getId());
        updateServico.setDescricao(servico.getDescricao());
        updateServico.setCategoria(servico.getCategoria());
        return servicoRepository.save(updateServico);
    }
}
