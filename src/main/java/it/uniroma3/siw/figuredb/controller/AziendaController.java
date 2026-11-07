package it.uniroma3.siw.figuredb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.figuredb.model.Azienda;
import it.uniroma3.siw.figuredb.service.AziendaService;
import it.uniroma3.siw.figuredb.service.OperazioneNonValidaException;
import it.uniroma3.siw.figuredb.service.RisorsaNonTrovataException;
import jakarta.validation.Valid;

@Controller
public class AziendaController {

    @Autowired
    private AziendaService aziendaService;

    @GetMapping("/aziende")
    public String list(Model model) {
        model.addAttribute("aziende", aziendaService.findAll());
        return "listAziende";
    }

    @GetMapping("/aziende/{id}")
    public String show(@PathVariable Long id, Model model) {
        Azienda azienda = aziendaService.findByIdWithFigure(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("l'azienda", id));
        model.addAttribute("azienda", azienda);
        return "showAzienda";
    }

    @GetMapping("/admin/aziende/new")
    public String createForm(Model model) {
        model.addAttribute("azienda", new Azienda());
        return "formAzienda";
    }

    @PostMapping("/admin/aziende")
    public String newAzienda(@Valid @ModelAttribute("azienda") Azienda azienda,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "formAzienda";
        try {
            Azienda salvata = aziendaService.save(azienda);
            return "redirect:/aziende/" + salvata.getId();
        } catch (OperazioneNonValidaException e) {
            model.addAttribute("errore", e.getMessage());
            return "formAzienda";
        }
    }

    @GetMapping("/admin/aziende/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Azienda azienda = aziendaService.findById(id).orElse(null);
        if (azienda == null)
            return "redirect:/aziende";
        model.addAttribute("azienda", azienda);
        return "formAzienda";
    }

    @PostMapping("/admin/aziende/{id}/edit")
    public String editAzienda(@PathVariable Long id,
            @Valid @ModelAttribute("azienda") Azienda azienda,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "formAzienda";
        aziendaService.aggiorna(id, azienda);
        return "redirect:/aziende/" + id;
    }

    @PostMapping("/admin/aziende/{id}/delete")
    public String deleteAzienda(@PathVariable Long id) {
        aziendaService.delete(id);
        return "redirect:/aziende";
    }
}
