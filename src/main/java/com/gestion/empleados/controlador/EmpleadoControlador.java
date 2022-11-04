package com.gestion.empleados.controlador;


import com.gestion.empleados.excepciones.ResourceNotFoundException;
import com.gestion.empleados.modelo.Empleado;
import com.gestion.empleados.repositorio.EmpleadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")
public class EmpleadoControlador {

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @GetMapping("/empleados")
    public List<Empleado> listarTodosLosEmpleados(){
        return empleadoRepositorio.findAll();
    }

    @PostMapping("/empleados")
    public Empleado guardarEmpleado(@RequestBody Empleado empleado){
        return empleadoRepositorio.save(empleado);
    }

    @GetMapping("/empleado/{id}")
    public ResponseEntity<Empleado> obtenerEmpleado(@PathVariable Long id){
        Empleado empleado= empleadoRepositorio.findById(id).orElseThrow(()->
                new ResourceNotFoundException("No existe el empleado"));
        return ResponseEntity.ok(empleado);
    }

    @PutMapping("/empleado/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id,@RequestBody Empleado detallesEmpleado){
        Empleado empleado= empleadoRepositorio.findById(id).orElseThrow(()->
                new ResourceNotFoundException("No existe el empleado"));
        empleado.setNombre(detallesEmpleado.getNombre());
        empleado.setApellido(detallesEmpleado.getApellido());
        empleado.setEmail(detallesEmpleado.getEmail());
        Empleado empleadoActualizado= empleadoRepositorio.save(empleado);
        return ResponseEntity.ok(empleadoActualizado);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String,Boolean>> eliminarEmpleado(@PathVariable("id") Long id){
        Empleado empleado = empleadoRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el empleado con el ID : " + id));

        empleadoRepositorio.delete(empleado);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminar",Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

}
