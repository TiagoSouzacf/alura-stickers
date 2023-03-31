package com.examplebr.com.alura.linguagensapi;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class LinguagemController {

    @Autowired
    private LinguagemRepository repositorio;

    @GetMapping("/linguagens")
    public List<Linguagem> obterLinguagens() {
        List<Linguagem> linguagens = repositorio.findByOrderByRanking();
        return linguagens;
    }

    @GetMapping("linguagens/{id}")
    public ResponseEntity<Linguagem> buscarId (@PathVariable String id){
        Optional<Linguagem> linguagem = repositorio.findById(id);
        if(!linguagem.isPresent()){
            return ResponseEntity.notFound().build();
        } return ResponseEntity.ok(linguagem.get());
    }

    @PostMapping("/linguagens")

    public ResponseEntity<Linguagem> cadastrarLinguagem(@RequestBody Linguagem linguagem) {
        Linguagem linguagemSalva = repositorio.save(linguagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(linguagemSalva);
    }

    @DeleteMapping("linguagens/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable String id) {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("linguagens/{id}")
    public ResponseEntity<Linguagem> atualizar(@PathVariable String id, @RequestBody Linguagem linguagem) {
        if (!repositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        linguagem.setId(id);
        return ResponseEntity.ok(repositorio.save(linguagem));
    }

}
