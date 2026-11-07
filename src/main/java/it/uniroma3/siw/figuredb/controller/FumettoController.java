package it.uniroma3.siw.figuredb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.figuredb.model.Fumetto;
import it.uniroma3.siw.figuredb.service.DisegnatoreService;
import it.uniroma3.siw.figuredb.service.EditoreService;
import it.uniroma3.siw.figuredb.service.FumettoService;
import it.uniroma3.siw.figuredb.service.OperazioneNonValidaException;
import it.uniroma3.siw.figuredb.service.RisorsaNonTrovataException;
import it.uniroma3.siw.figuredb.service.ScrittoreService;
import it.uniroma3.siw.figuredb.service.SerieService;
import jakarta.validation.Valid;

@Controller
public class FumettoController {

    @Autowired
    private FumettoService fumettoService;
    @Autowired
    private SerieService serieService;
    @Autowired
    private ScrittoreService scrittoreService;
    @Autowired
    private DisegnatoreService disegnatoreService;
    @Autowired
    private EditoreService editoreService;

    /** Elenco di tutti i fumetti. */
    @GetMapping("/fumetti")
    public String list(Model model) {
        model.addAttribute("fumetti", fumettoService.findAll());
        return "listFumetti";
    }

    /** Dettaglio fumetto con scrittore, disegnatori ed editore. */
    @GetMapping("/fumetti/{id}")
    public String show(@PathVariable Long id, Model model) {
        Fumetto fumetto = fumettoService.findByIdCompleto(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("il fumetto", id));
        model.addAttribute("fumetto", fumetto);
        return "showFumetto";
    }

    @GetMapping("/admin/fumetti/new")
    public String createForm(Model model) {
        model.addAttribute("fumettoForm", new Fumetto());
        aggiungiDatiForm(model);
        return "formFumetto";
    }

    @PostMapping("/admin/fumetti")
    public String newFumetto(@Valid @ModelAttribute("fumettoForm") Fumetto fumettoForm,
            BindingResult bindingResult,
            @RequestParam Long serieId,
            @RequestParam Long scrittoreId,
            @RequestParam Long editoreId,
            @RequestParam(required = false) List<Long> disegnatoriIds,
            Model model) {
        if (bindingResult.hasErrors()) {
            aggiungiDatiForm(model);
            return "formFumetto";
        }
        try {
            Fumetto salvato = fumettoService.inserisci(fumettoForm, serieId, scrittoreId, editoreId, disegnatoriIds);
            return "redirect:/fumetti/" + salvato.getId();
        } catch (OperazioneNonValidaException e) {
            model.addAttribute("errore", e.getMessage());
            aggiungiDatiForm(model);
            return "formFumetto";
        }
    }

    @GetMapping("/admin/fumetti/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Fumetto fumetto = fumettoService.findByIdCompleto(id).orElse(null);
        if (fumetto == null)
            return "redirect:/fumetti";
        model.addAttribute("fumettoForm", fumetto);
        aggiungiDatiForm(model);
        return "formFumetto";
    }

    @PostMapping("/admin/fumetti/{id}/edit")
    public String editFumetto(@PathVariable Long id,
            @Valid @ModelAttribute("fumettoForm") Fumetto fumettoForm,
            BindingResult bindingResult,
            @RequestParam Long serieId,
            @RequestParam Long scrittoreId,
            @RequestParam Long editoreId,
            @RequestParam(required = false) List<Long> disegnatoriIds,
            Model model) {
        if (bindingResult.hasErrors()) {
            aggiungiDatiForm(model);
            return "formFumetto";
        }
        try {
            fumettoService.aggiorna(id, fumettoForm, serieId, scrittoreId, editoreId, disegnatoriIds);
            return "redirect:/fumetti/" + id;
        } catch (OperazioneNonValidaException e) {
            model.addAttribute("errore", e.getMessage());
            aggiungiDatiForm(model);
            return "formFumetto";
        }
    }

    @PostMapping("/admin/fumetti/{id}/delete")
    public String deleteFumetto(@PathVariable Long id) {
        fumettoService.delete(id);
        return "redirect:/fumetti";
    }

    /** Dati per i menu a tendina della form. */
    private void aggiungiDatiForm(Model model) {
        model.addAttribute("serieList", serieService.findAll());
        model.addAttribute("scrittori", scrittoreService.findAll());
        model.addAttribute("disegnatori", disegnatoreService.findAll());
        model.addAttribute("editori", editoreService.findAll());
    }
}
