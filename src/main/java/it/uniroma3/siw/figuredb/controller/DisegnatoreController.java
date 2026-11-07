package it.uniroma3.siw.figuredb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.figuredb.model.Disegnatore;
import it.uniroma3.siw.figuredb.service.RisorsaNonTrovataException;
import it.uniroma3.siw.figuredb.service.DisegnatoreService;
import jakarta.validation.Valid;

@Controller
public class DisegnatoreController {

    @Autowired
    private DisegnatoreService disegnatoreService;

    /** Scheda pubblica di un disegnatore, con i fumetti su cui ha lavorato. */
    @GetMapping("/disegnatori/{id}")
    public String show(@PathVariable Long id, Model model) {
        Disegnatore disegnatore = disegnatoreService.findByIdWithFumetti(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("il disegnatore", id));
        model.addAttribute("disegnatore", disegnatore);
        return "showDisegnatore";
    }

    @GetMapping("/admin/disegnatori")
    public String list(Model model) {
        model.addAttribute("disegnatori", disegnatoreService.findAll());
        return "listDisegnatori";
    }

    @GetMapping("/admin/disegnatori/new")
    public String createForm(Model model) {
        model.addAttribute("disegnatore", new Disegnatore());
        return "formDisegnatore";
    }

    @PostMapping("/admin/disegnatori")
    public String newDisegnatore(@Valid @ModelAttribute("disegnatore") Disegnatore disegnatore,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "formDisegnatore";
        disegnatoreService.save(disegnatore);
        return "redirect:/admin/disegnatori";
    }

    @GetMapping("/admin/disegnatori/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Disegnatore disegnatore = disegnatoreService.findById(id).orElse(null);
        if (disegnatore == null)
            return "redirect:/admin/disegnatori";
        model.addAttribute("disegnatore", disegnatore);
        return "formDisegnatore";
    }

    @PostMapping("/admin/disegnatori/{id}/edit")
    public String editDisegnatore(@PathVariable Long id,
            @Valid @ModelAttribute("disegnatore") Disegnatore disegnatore,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "formDisegnatore";
        disegnatore.setId(id);
        disegnatoreService.save(disegnatore);
        return "redirect:/disegnatori/" + id;
    }

    @PostMapping("/admin/disegnatori/{id}/delete")
    public String deleteDisegnatore(@PathVariable Long id) {
        disegnatoreService.delete(id);
        return "redirect:/admin/disegnatori";
    }
}
