package it.uniroma3.siw.figuredb.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.figuredb.dto.FigureDTO;
import it.uniroma3.siw.figuredb.dto.RecensioneDTO;
import it.uniroma3.siw.figuredb.service.FigureService;
import it.uniroma3.siw.figuredb.service.RecensioneService;

/**
 * API REST sulle figure. E' l'endpoint usato dal widget React di ricerca.
 * I dati escono sempre come DTO: le entita' JPA non vengono serializzate
 * direttamente, per non trascinare associazioni LAZY e cicli.
 */
@RestController
@RequestMapping("/api/figure")
public class FigureRestController {

    @Autowired
    private FigureService figureService;

    @Autowired
    private RecensioneService recensioneService;

    /**
     * GET /api/figure
     * GET /api/figure?nome=... per la ricerca per nome.
     */
    @GetMapping
    public ResponseEntity<List<FigureDTO>> tutte(
            @RequestParam(required = false) String nome) {
        List<FigureDTO> figure = (nome == null || nome.isBlank()
                ? figureService.findAll()
                : figureService.cercaPerNome(nome))
                .stream()
                .map(FigureDTO::from)
                .toList();
        return ResponseEntity.ok(figure);
    }

    /** GET /api/figure/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<FigureDTO> una(@PathVariable Long id) {
        return figureService.findByIdWithAziendaESerie(id)
                .map(FigureDTO::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /** GET /api/figure/{id}/recensioni */
    @GetMapping("/{id}/recensioni")
    public ResponseEntity<List<RecensioneDTO>> recensioniDellaFigure(@PathVariable Long id) {
        if (figureService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<RecensioneDTO> recensioni = recensioneService.findByFigureId(id).stream()
                .map(RecensioneDTO::from)
                .toList();
        return ResponseEntity.ok(recensioni);
    }
}
