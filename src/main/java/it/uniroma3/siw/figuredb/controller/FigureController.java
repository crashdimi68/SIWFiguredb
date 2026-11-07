package it.uniroma3.siw.figuredb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.figuredb.model.Figure;
import it.uniroma3.siw.figuredb.model.Materiale;
import it.uniroma3.siw.figuredb.service.AziendaService;
import it.uniroma3.siw.figuredb.service.FigureService;
import it.uniroma3.siw.figuredb.service.OperazioneNonValidaException;
import it.uniroma3.siw.figuredb.service.RecensioneService;
import it.uniroma3.siw.figuredb.service.RisorsaNonTrovataException;
import it.uniroma3.siw.figuredb.service.SerieService;
import jakarta.validation.Valid;


@Controller
public class FigureController {

    /** Quante figure vengono mostrate in una pagina dell'elenco. */
    private static final int FIGURE_PER_PAGINA = 9;

    @Autowired
    private FigureService figureService;
    @Autowired
    private AziendaService aziendaService;
    @Autowired
    private SerieService serieService;
    @Autowired
    private RecensioneService recensioneService;

    /**
     * Elenco delle figure, nove per pagina. Il numero di pagina arriva come parametro
     * di richiesta (/figure?page=2); se manca si intende la prima. Una richiesta
     * oltre l'ultima pagina viene riportata sull'ultima esistente con un redirect.
     */
    @GetMapping("/figure")
    public String list(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Figure> pagina = figureService.findPagina(Math.max(page, 0), FIGURE_PER_PAGINA);
        if (pagina.getTotalPages() > 0 && page >= pagina.getTotalPages()) {
            return "redirect:/figure?page=" + (pagina.getTotalPages() - 1);
        }
        model.addAttribute("figure", pagina.getContent());
        model.addAttribute("pagina", pagina);
        return "listFigure";
    }

    /** Pagina che ospita il widget React di ricerca delle figure. */
    @GetMapping("/figure/cerca")
    public String cerca(Model model) {
        return "cercaFigure";
    }

    @GetMapping("/figure/{id}")
    public String show(@PathVariable Long id, Model model) {
        Figure figure = figureService.findByIdWithAziendaESerie(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("la figure", id));
        model.addAttribute("figure", figure);
        model.addAttribute("recensioni", recensioneService.findByFigureId(id));
        model.addAttribute("votoMedio", recensioneService.votoMedio(id));
        return "showFigure";
    }

    @GetMapping("/admin/figure/new")
    public String createForm(Model model) {
        model.addAttribute("figureForm", new Figure());
        aggiungiDatiForm(model);
        return "formFigure";
    }

    /**
     * Inserimento a catalogo: il controller valida il formato dell'input e delega il
     * caso d'uso al Service Layer, che lo esegue in un'unica transazione.
     * Se viola il vincolo di consistenza il messaggio torna nella form.
     */
    @PostMapping("/admin/figure")
    public String newFigure(@Valid @ModelAttribute("figureForm") Figure figureForm,
            BindingResult bindingResult,
            @RequestParam Long aziendaId,
            @RequestParam Long serieId,
            Model model) {
        if (bindingResult.hasErrors()) {
            aggiungiDatiForm(model);
            return "formFigure";
        }
        try {
            Figure salvata = figureService.inserisciACatalogo(figureForm, aziendaId, serieId);
            return "redirect:/figure/" + salvata.getId();
        } catch (OperazioneNonValidaException e) {
            model.addAttribute("errore", e.getMessage());
            aggiungiDatiForm(model);
            return "formFigure";
        }
    }

    @GetMapping("/admin/figure/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Figure figure = figureService.findByIdWithAziendaESerie(id).orElse(null);
        if (figure == null)
            return "redirect:/figure";
        model.addAttribute("figureForm", figure);
        aggiungiDatiForm(model);
        return "formFigure";
    }

    @PostMapping("/admin/figure/{id}/edit")
    public String editFigure(@PathVariable Long id,
            @Valid @ModelAttribute("figureForm") Figure figureForm,
            BindingResult bindingResult,
            @RequestParam Long aziendaId,
            @RequestParam Long serieId,
            Model model) {
        if (bindingResult.hasErrors()) {
            aggiungiDatiForm(model);
            return "formFigure";
        }
        figureService.aggiorna(id, figureForm, aziendaId, serieId);
        return "redirect:/figure/" + id;
    }

    @PostMapping("/admin/figure/{id}/delete")
    public String deleteFigure(@PathVariable Long id) {
        figureService.delete(id);
        return "redirect:/figure";
    }

    /** Dati per i menu a tendina della form. */
    private void aggiungiDatiForm(Model model) {
        model.addAttribute("aziende", aziendaService.findAll());
        model.addAttribute("serieList", serieService.findAll());
        model.addAttribute("materiali", Materiale.values());
    }
}
