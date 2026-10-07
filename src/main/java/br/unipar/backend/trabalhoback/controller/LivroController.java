package br.unipar.backend.trabalhoback.controller;

import br.unipar.backend.trabalhoback.model.Livro;
import br.unipar.backend.trabalhoback.repository.LivroRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LivroController {

    private final LivroRepository repository;

    public LivroController(LivroRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/livros")
    public List<Livro> listarLivro(
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) Integer ano) {

        return repository.buscarComFiltros(autor, genero, ano);
    }

    @PostMapping("/livros")
    public Livro cadastrarLivros(@RequestBody Livro livro) {
        return repository.save(livro);
    }

    @GetMapping("/livros/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable int id) {

        return repository.findById(id)
                .map(livro -> ResponseEntity.ok(livro))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/livros/{id}")
    public ResponseEntity<Livro> atualizarLivro(
            @PathVariable int id,
            @RequestBody Livro livroAtualizado) {

        return repository.findById(id)
                .map(livro -> {

                    livro.setTitulo(livroAtualizado.getTitulo());
                    livro.setAutor(livroAtualizado.getAutor());
                    livro.setGenero(livroAtualizado.getGenero());
                    livro.setAno(livroAtualizado.getAno());

                    return ResponseEntity.ok(repository.save(livro));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/livros/{id}")
    public ResponseEntity<Void> excluirLivro(@PathVariable int id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}