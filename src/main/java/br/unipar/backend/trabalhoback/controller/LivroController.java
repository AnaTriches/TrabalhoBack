package br.unipar.backend.trabalhoback.controller;

import br.unipar.backend.trabalhoback.model.Livro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LivroController {
    private List<Livro> livros = new ArrayList<>();

    @GetMapping("/livros")
    public List<Livro> listarLivros(@RequestParam (required = false) String autor,
                                    @RequestParam (required = false) String genero,
                                    @RequestParam (required = false) Integer ano) {
        List<Livro> resultado = new ArrayList<>();

        for (Livro livro : livros){
            if (autor != null && !livro.getAutor().equalsIgnoreCase(autor)){
                continue;
            }
            if (genero != null && !livro.getGenero().equalsIgnoreCase(genero)){
                continue;
            }
            if (ano != null && livro.getAno() != ano){
                continue;
            }
            resultado.add(livro);
        }
        return resultado;
    }

    @PostMapping("/livros")
    public Livro cadastrarLivro(@RequestBody Livro livro){
        livros.add(livro);
        return livro;
    }

    @GetMapping("/livros/{id}")
    public ResponseEntity<Livro> buscarPorId (@PathVariable int id){
        for (Livro livro : livros){
            if (livro.getId() == id){
                return ResponseEntity.ok(livro);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/livros/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable int id, @RequestBody Livro livroAtualizado){
        for (Livro livro : livros){
            if (livro.getId() == id){
                livro.setTitulo(livroAtualizado.getTitulo());
                livro.setAutor(livroAtualizado.getAutor());
                livro.setGenero(livroAtualizado.getGenero());
                livro.setAno(livroAtualizado.getAno());

                return ResponseEntity.ok(livro);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/livros/{id}")
    public ResponseEntity<Void> excluirLivro(@PathVariable int id){
        for (Livro livro : livros){
            if (livro.getId() == id){
                livros.remove(livro);
                return ResponseEntity.noContent().build();
            }
        }
        return  ResponseEntity.notFound().build();
    }

}

