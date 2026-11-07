package it.uniroma3.siw.figuredb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.figuredb.model.User;
import it.uniroma3.siw.figuredb.service.CredentialsService;
import it.uniroma3.siw.figuredb.service.RecensioneDuplicataException;
import it.uniroma3.siw.figuredb.service.RecensioneService;

@Controller
public class RecensioneController {

    @Autowired
    private RecensioneService recensioneService;
    @Autowired
    private CredentialsService credentialsService;

    @PostMapping("/figure/{figureId}/recensione")
    public String addRecensione(@PathVariable Long figureId,
            @RequestParam String titolo,
            @RequestParam String testo,
            @RequestParam Integer voto,
            RedirectAttributes redirectAttrs) {
        try {
            recensioneService.inserisci(figureId, utenteCorrente(), titolo, testo, voto);
        } catch (RecensioneDuplicataException e) {
            redirectAttrs.addFlashAttribute("errore", e.getMessage());
        } catch (AccessDeniedException e) {
            redirectAttrs.addFlashAttribute("errore", "Devi essere autenticato per recensire una figure.");
        }
        return "redirect:/figure/" + figureId;
    }

    @PostMapping("/figure/{figureId}/recensione/{recensioneId}/edit")
    public String editRecensione(@PathVariable Long figureId,
            @PathVariable Long recensioneId,
            @RequestParam String titolo,
            @RequestParam String testo,
            @RequestParam(required = false) Integer voto,
            RedirectAttributes redirectAttrs) {
        try {
            recensioneService.modifica(recensioneId, titolo, testo, voto, utenteCorrente());
        } catch (AccessDeniedException e) {
            redirectAttrs.addFlashAttribute("errore", "Puoi modificare solo le tue recensioni.");
        }
        return "redirect:/figure/" + figureId;
    }

    @PostMapping("/figure/{figureId}/recensione/{recensioneId}/delete")
    public String deleteRecensione(@PathVariable Long figureId,
            @PathVariable Long recensioneId,
            RedirectAttributes redirectAttrs) {
        try {
            recensioneService.elimina(recensioneId, utenteCorrente(), isAdmin());
        } catch (AccessDeniedException e) {
            redirectAttrs.addFlashAttribute("errore", "Puoi eliminare solo le tue recensioni.");
        }
        return "redirect:/figure/" + figureId;
    }

    /** Pannello admin: tutte le recensioni presenti nel sito. */
    @GetMapping("/admin/recensioni")
    public String listAll(Model model) {
        model.addAttribute("recensioni", recensioneService.findAll());
        return "listRecensioni";
    }

    /** Moderazione: l'amministratore puo' eliminare qualunque recensione. */
    @PostMapping("/admin/recensioni/{recensioneId}/delete")
    public String deleteDaAdmin(@PathVariable Long recensioneId) {
        recensioneService.elimina(recensioneId, utenteCorrente(), true);
        return "redirect:/admin/recensioni";
    }

    private User utenteCorrente() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return credentialsService.getUserByUsername(username);
    }

    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
