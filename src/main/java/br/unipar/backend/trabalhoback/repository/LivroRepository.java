package br.unipar.backend.trabalhoback.repository;

import br.unipar.backend.trabalhoback.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Integer> {

    @Query("""
        SELECT l FROM Livro l
        WHERE (:autor IS NULL OR l.autor = :autor)
        AND (:genero IS NULL OR l.genero = :genero)
        AND (:ano IS NULL OR l.ano = :ano)
    """)
    List<Livro> buscarComFiltros(
            @Param("autor") String autor,
            @Param("genero") String genero,
            @Param("ano") Integer ano
    );
}