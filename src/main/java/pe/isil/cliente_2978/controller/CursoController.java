package pe.isil.cliente_2978.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.isil.cliente_2978.model.Curso;

@Controller
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    private RestTemplate restTemplate; //Permite consumir las API REST

    //MAPEAR LAS RUTAS DEL API-CURSO
    private static final String GET_ALL_CURSOS_API = "http://localhost:8090/api_2978/cursos/getAll"; //GET
    private static final String GET_BY_ID_CURSO_API = "http://localhost:8090/api_2978/cursos/getById/{id}"; //GET
    private static final String STORE_CURSO_API = "http://localhost:8090/api_2978/cursos/store "; //POST
    private static final String UPDATE_CURSO_API = "http://localhost:8090/api_2978/cursos/update/{id}"; //PUT
    private static final String DELETE_CURSO_API = "http://localhost:8090/api_2978/cursos/delete/{id}"; //DELETE

    @GetMapping("")
    public String index(Model model){
        //Consumiendo el microservicio: GET_ALL_CURSOS_API
        ResponseEntity<Curso[]> listado = restTemplate.getForEntity(GET_ALL_CURSOS_API, Curso[].class);//obtiene la lista de cursos y lo guarda en la variable cursos
        //enviar cursos a la vista
        model.addAttribute("cursos", listado.getBody());
        return "cursos/index"; //la vista html
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model){
        model.addAttribute("curso", new Curso());
        return "cursos/nuevo"; // la vista nuevo.html
    }

    @PostMapping("/store")
    public String store(Model model, Curso curso, RedirectAttributes ra){
        restTemplate.postForEntity(STORE_CURSO_API, curso, Curso.class);
        ra.addFlashAttribute("msgExito", "Curso registrado exitosamente");
        return "redirect:/cursos";
    }

    @GetMapping("/editar/{id}")
    public String editar(Model model, @PathVariable ("id") Integer id){
        Curso curso = restTemplate.getForObject(GET_BY_ID_CURSO_API, Curso.class, id);
        model.addAttribute("curso", curso);
        return "cursos/editar";
    }

    @PostMapping("/editar/{id}")
    public String actualizar(Model model, @PathVariable ("id") Integer id, Curso curso, RedirectAttributes ra){
        restTemplate.put(UPDATE_CURSO_API, curso, id);
        ra.addFlashAttribute("msgExito", "Curso actualizado");
        return "redirect:/cursos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable ("id") Integer id, RedirectAttributes ra){
        restTemplate.delete(DELETE_CURSO_API, id);
        ra.addFlashAttribute("msgExito", "Curso eliminado");
        return "redirect:/cursos";
    }

}
