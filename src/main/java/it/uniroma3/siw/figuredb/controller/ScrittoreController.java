package it.uniroma3.siw.figuredb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.figuredb.model.Scrittore;
import it.uniroma3.siw.figuredb.service.RisorsaNonTrovataException;
import it.uniroma3.siw.figuredb.service.ScrittoreService;
import jakarta.validation.Valid;

@Controller
public class ScrittoreController {

    @Autowired
    private ScrittoreService scrittoreService;

    /** Scheda pubblica di uno scrittore, con i fumetti che ha scritto. */
    @GetMapping("/scrittori/{id}")
    public String show(@PathVariable Long id, Model model) {
        Scrittore scrittore = scrittoreService.findByIdWithFumetti(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("lo scrittore", id));
        model.addAttribute("scrittore", scrittore);
        return "showScrittore";
    }

    @GetMapping("/admin/scrittori")
    public String list(Model model) {
        model.addAttribute("scrittori", scrittoreService.findAll());
        return "listScrittori";
    }

    @GetMapping("/admin/scrittori/new")
    public String createForm(Model model) {
        model.addAttribute("scrittore", new Scrittore());
        return "formScrittore";
    }

    @PostMapping("/admin/scrittori")
    public String newScrittore(@Valid @ModelAttribute("scrittore") Scrittore scrittore,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "formScrittore";
        scrittoreService.save(scrittore);
        return "redirect:/admin/scrittori";
    }

    @GetMapping("/admin/scrittori/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Scrittore scrittore = scrittoreService.findById(id).orElse(null);
        if (scrittore == null)
            return "redirect:/admin/scrittori";
        model.addAttribute("scrittore", scrittore);
        return "formScrittore";
    }

    @PostMapping("/admin/scrittori/{id}/edit")
    public String editScrittore(@PathVariable Long id,
            @Valid @ModelAttribute("scrittore") Scrittore scrittore,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "formScrittore";
        scrittore.setId(id);
        scrittoreService.save(scrittore);
        return "redirect:/scrittori/" + id;
    }

    @PostMapping("/admin/scrittori/{id}/delete")
    public String deleteScrittore(@PathVariable Long id) {
        scrittoreService.delete(id);
        return "redirect:/admin/scrittori";
    }
}
