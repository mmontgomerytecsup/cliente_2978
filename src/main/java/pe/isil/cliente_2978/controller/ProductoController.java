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
import pe.isil.cliente_2978.model.Producto;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private RestTemplate restTemplate;


    //MAPEAR LAS RUTAS DEL API-PRODUCTO
    private static final String GET_ALL_PRODUCTOS_API = "http://localhost:8090/api_2978/productos/getAll"; //GET
    private static final String GET_BY_ID_PRODUCTO_API = "http://localhost:8090/api_2978/productos/getById/{id}"; //GET
    private static final String STORE_PRODUCTO_API = "http://localhost:8090/api_2978/productos/store"; //POST
    private static final String UPDATE_PRODUCTO_API = "http://localhost:8090/api_2978/productos/update/{id}"; //PUT
    private static final String DELETE_PRODUCTO_API = "http://localhost:8090/api_2978/productos/delete/{id}"; //DELETE
    private static final String GET_BY_CATEGORIA_PRODUCTO_API = "http://localhost:8090/api_2978/productos/getByCategoria/{categoria}"; //GET
    private static final String GET_BY_STOCK_PRODUCTO_API = "http://localhost:8090/api_2978/productos/getByStock"; //GET

    @GetMapping("")
    public String index(Model model){
        //Consumiendo el microservicio: GET_ALL_PRODUCTOS_API
        ResponseEntity<Producto[]> listado = restTemplate.getForEntity(GET_ALL_PRODUCTOS_API, Producto[].class);//obtiene la lista de productos y lo guarda en la variable productos
        //enviar productos a la vista
        model.addAttribute("productos", listado.getBody());
        return "productos/index"; //la vista html
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model){
        model.addAttribute("producto", new Producto());
        return "productos/nuevo"; // la vista nuevo.html
    }

    @PostMapping("/store")
    public String store(Model model, Producto producto, RedirectAttributes ra){
        restTemplate.postForEntity(STORE_PRODUCTO_API, producto, Producto.class);
        ra.addFlashAttribute("msgExito", "Producto registrado exitosamente");
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editar(Model model, @PathVariable("id") Integer id){
        Producto producto = restTemplate.getForObject(GET_BY_ID_PRODUCTO_API, Producto.class, id);
        model.addAttribute("producto", producto);
        return "productos/editar";
    }

    @PostMapping("/editar/{id}")
    public String actualizar(Model model, @PathVariable ("id") Integer id, Producto producto, RedirectAttributes ra){
        restTemplate.put(UPDATE_PRODUCTO_API, producto, id);
        ra.addFlashAttribute("msgExito", "Producto actualizado");
        return "redirect:/productos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable ("id") Integer id, RedirectAttributes ra){
        restTemplate.delete(DELETE_PRODUCTO_API, id);
        ra.addFlashAttribute("msgExito", "Producto eliminado");
        return "redirect:/productos";
    }

    @GetMapping("/getByCategoria/{categoria}")
    public String getByCategoria(Model model, @PathVariable("categoria") String categoria){
        //Consumiendo el microservicio: GET_BY_CATEGORIA_PRODUCTO_API
        ResponseEntity<Producto[]> listado = restTemplate.getForEntity(GET_BY_CATEGORIA_PRODUCTO_API, Producto[].class, categoria);//obtiene la lista de productos y lo guarda en la variable productos
        //enviar productos a la vista
        model.addAttribute("productos", listado.getBody());
        return "productos/index"; //la vista html
    }

    @GetMapping("/getByStock")
    public String getByStock(Model model){
        //Consumiendo el microservicio: GET_BY_STOCK_PRODUCTO_API
        ResponseEntity<Producto[]> listado = restTemplate.getForEntity(GET_BY_STOCK_PRODUCTO_API, Producto[].class);//obtiene la lista de productos y lo guarda en la variable productos
        //enviar productos a la vista
        model.addAttribute("productos", listado.getBody());
        return "productos/index"; //la vista html
    }
}
