package it.uniroma3.siw.figuredb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.figuredb.model.Serie;
import it.uniroma3.siw.figuredb.model.TipoSerie;
import it.uniroma3.siw.figuredb.service.FigureService;
import it.uniroma3.siw.figuredb.service.FumettoService;
import it.uniroma3.siw.figuredb.service.OperazioneNonValidaException;
import it.uniroma3.siw.figuredb.service.RisorsaNonTrovataException;
import it.uniroma3.siw.figuredb.service.SerieService;
import jakarta.validation.Valid;

@Controller
public class SerieController {

    @Autowired
    private SerieService serieService;
    @Autowired
    private FumettoService fumettoService;
    @Autowired
    private FigureService figureService;

    /** Elenco di tutte le serie. */
    @GetMapping("/serie")
    public String list(Model model) {
        model.addAttribute("serieList", serieService.findAll());
        return "listSerie";
    }

    /** Dettaglio serie: personaggi, fumetti e figure collegati. */
    @GetMapping("/serie/{id}")
    public String show(@PathVariable Long id, Model model) {
        Serie serie = serieService.findByIdWithPersonaggi(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("la serie", id));
        model.addAttribute("serie", serie);
        model.addAttribute("fumetti", fumettoService.findBySerieId(id));
        model.addAttribute("figure", figureService.findBySerieId(id));
        return "showSerie";
    }

    @GetMapping("/admin/serie/new")
    public String createForm(Model model) {
        model.addAttribute("serie", new Serie());
        model.addAttribute("tipi", TipoSerie.values());
        return "formSerie";
    }

    @PostMapping("/admin/serie")
    public String newSerie(@Valid @ModelAttribute("serie") Serie serie,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tipi", TipoSerie.values());
            return "formSerie";
        }
        try {
            Serie salvata = serieService.save(serie);
            return "redirect:/serie/" + salvata.getId();
        } catch (OperazioneNonValidaException e) {
            model.addAttribute("errore", e.getMessage());
            model.addAttribute("tipi", TipoSerie.values());
            return "formSerie";
        }
    }

    @GetMapping("/admin/serie/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Serie serie = serieService.findById(id).orElse(null);
        if (serie == null)
            return "redirect:/serie";
        model.addAttribute("serie", serie);
        model.addAttribute("tipi", TipoSerie.values());
        return "formSerie";
    }

    @PostMapping("/admin/serie/{id}/edit")
    public String editSerie(@PathVariable Long id,
            @Valid @ModelAttribute("serie") Serie serie,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tipi", TipoSerie.values());
            return "formSerie";
        }
        serieService.aggiorna(id, serie);
        return "redirect:/serie/" + id;
    }

    /** Aggiunta di un personaggio dalla pagina di dettaglio della serie. */
    @PostMapping("/admin/serie/{id}/personaggi")
    public String addPersonaggio(@PathVariable Long id,
            @RequestParam String nome,
            @RequestParam(required = false) String descrizione) {
        serieService.aggiungiPersonaggio(id, nome, descrizione);
        return "redirect:/serie/" + id;
    }

    @PostMapping("/admin/serie/{id}/delete")
    public String deleteSerie(@PathVariable Long id) {
        serieService.delete(id);
        return "redirect:/serie";
    }
}
