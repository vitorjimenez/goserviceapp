package com.soulcode.goserviceapp.controller;

import com.soulcode.goserviceapp.domain.Servico;
import com.soulcode.goserviceapp.domain.Usuario;
import com.soulcode.goserviceapp.service.ServicoService;
import com.soulcode.goserviceapp.service.UsuarioService;
import com.soulcode.goserviceapp.service.exceptions.UsuarioNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping(value = "/admin")
public class AdministradorController {

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = "/servicos")
    public String servico() {
        return "servicosAdmin";
    }

    @PostMapping
    public String createServico(Servico servico, RedirectAttributes attributes){
        try {
            servicoService.createServico(servico);
            attributes.addFlashAttribute("sucessMessage", "Sucesso ao adicionar novo serviço.");
        } catch (Exception ex){
            attributes.addFlashAttribute("errorMessage", "Erro ao adicionar novo serviço.");
        }
        return  "redirect:/admin/servicos";
    }
    @GetMapping(value = "/usuarios")
    public ModelAndView usuarios() {
        ModelAndView mv = new ModelAndView("usuariosAdmin");
        try {
            List<Usuario> usuarios = usuarioService.findAll();
            mv.addObject("usuarios", usuarios);
        } catch (Exception ex){
            mv.addObject("errorMessage", "Erro ao buscar dadoss de usuário");
        }
        return mv;
    }

    @PostMapping(value = "/usuarios")
    public String createUser(Usuario usuario, RedirectAttributes attributes){
        try {
            usuarioService.createUser(usuario);
            attributes.addFlashAttribute("sucessMessage", "Novo usuário cadastrado");
        } catch (Exception ex){
            attributes.addFlashAttribute("errorMessage", "Erro ao cadastrar novo usuário");
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping(value = "/usuarios/disabled")
    public String disableUser(@RequestParam(name = "usuarioId")Long id, RedirectAttributes attributes){
        try{
            usuarioService.disableUser(id);
        }catch (UsuarioNaoEncontradoException ex){
            attributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception ex){
            attributes.addFlashAttribute("errorMessage", "Erro ao encontrar usuário");
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping(value = "/usuarios/disabled")
    public String enableUser(@RequestParam(name = "usuarioId")Long id, RedirectAttributes attributes){
        try{
            usuarioService.enableUser(id);
        }catch (UsuarioNaoEncontradoException ex){
            attributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception ex){
            attributes.addFlashAttribute("errorMessage", "Erro ao habilitar usuário");
        }
        return "redirect:/admin/usuarios";
    }

}
