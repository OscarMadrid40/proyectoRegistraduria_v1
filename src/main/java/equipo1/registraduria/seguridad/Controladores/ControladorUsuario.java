package equipo1.registraduria.seguridad.Controladores;
import equipo1.registraduria.seguridad.Modelos.Usuario;
import equipo1.registraduria.seguridad.Modelos.Rol;
import equipo1.registraduria.seguridad.Repositorios.RepositorioUsuario;
import equipo1.registraduria.seguridad.Repositorios.RepositorioRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class ControladorUsuario {
    @Autowired
    private RepositorioUsuario miRepositorioUsuario;
    @Autowired
    private RepositorioRol miRepositorioRol;
    @PutMapping("{id}/rol/{id_rol}")
    public Usuario asignarRolAUsuario(@PathVariable String id, @PathVariable String id_rol){
        System.out.println("Asignando Rol a un Usuario");
        Usuario usuarioActual = this.miRepositorioUsuario
                .findById(id)
                .orElse(null);
        Rol rolActual = this.miRepositorioRol
                .findById(id_rol)
                .orElse(null);
        if(usuarioActual != null && rolActual != null){
            usuarioActual.setRol(rolActual);
            return this.miRepositorioUsuario.save(usuarioActual);
        }else{
            return null;
        }
    }
    @GetMapping("")
    public List<Usuario> index(){
         return this.miRepositorioUsuario.findAll();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Usuario create(@RequestBody Usuario infoUsuario){
        System.out.println("Creando un Usuario nuevo");
        infoUsuario.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
        return this.miRepositorioUsuario.save(infoUsuario);
    }
    @PostMapping("/validate")
    public Usuario validate(@RequestBody Usuario infoUsuario, final HttpServletResponse response) throws IOException{
       Usuario usuarioActual = this.miRepositorioUsuario
               .getUserByEmail(infoUsuario.getCorreo());
       if(usuarioActual != null && usuarioActual.getContrasena().equals(convertirSHA256(infoUsuario.getContrasena()))){
           usuarioActual.setContrasena("");
           return usuarioActual;
       }else {
           response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
           return null;
       }
    }

    @GetMapping("{id}")
    public Usuario show(@PathVariable String id){
        System.out.println("Mostrando un Usuario con id "+ id);
        Usuario usuarioActual = this.miRepositorioUsuario
                .findById(id)
                .orElse(null);
        return usuarioActual;
    }

    @PutMapping("{id}")
    public Usuario update(@PathVariable String id, @RequestBody Usuario infoUsuario){
        System.out.println("Actualizando Usuario con id "+ id);
        Usuario usuarioAcutal = this.miRepositorioUsuario
                .findById(id)
                .orElse(null);
        if(usuarioAcutal != null){
            usuarioAcutal.setNombre(infoUsuario.getNombre());
            usuarioAcutal.setApellido(infoUsuario.getApellido());
            usuarioAcutal.setCedula(infoUsuario.getCedula());
            usuarioAcutal.setSeudonimo(infoUsuario.getSeudonimo());
            usuarioAcutal.setCorreo(infoUsuario.getCorreo());
            usuarioAcutal.setContrasena(infoUsuario.getContrasena());
            return this.miRepositorioUsuario.save(usuarioAcutal);
        }else{
            return null;
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        System.out.println("Eliminando el Usuario con id "+ id);
        Usuario usuarioAEliminar = this.miRepositorioUsuario
                .findById(id)
                .orElse(null);
        if (usuarioAEliminar != null){
            this.miRepositorioUsuario.delete(usuarioAEliminar);
        }
    }
    public String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
