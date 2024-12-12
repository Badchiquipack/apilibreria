package com.marriaga.alura.apilibreria.principal;

import com.marriaga.alura.apilibreria.model.Datos;
import com.marriaga.alura.apilibreria.model.DatosLibro;
import com.marriaga.alura.apilibreria.service.ConsumoAPI;
import com.marriaga.alura.apilibreria.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    Scanner entrada = new Scanner(System.in);

    public void muestraInformacion() {
        String json = consumo.obtenerDatos(URL_BASE);
        System.out.println(json);
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);

        //Top 10 libros más descargados
        System.out.println("Top 10 libros más decargados");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibro::descargas).reversed())
                .limit(10)
                .forEach(System.out::println);

        //Búsqueda de libros por nombre
        System.out.println("Ingrese el nombre del libro que desea buscar: ");
        String tituloLibro = entrada.nextLine();
        json = consumo.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        Datos datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            System.out.println("Libro encontrado ");
            System.out.println(libroBuscado.get());
        } else {
            System.out.println("Libro no encontrado");
        }

        //Trabajando con estadísticas
        DoubleSummaryStatistics est = datos.resultados().stream()
                .filter(d -> d.descargas() > 0)
                .collect(Collectors.summarizingDouble(DatosLibro::descargas));
        System.out.println(" Cantidad media de descargas: " + est.getAverage());
        System.out.println(" Libro más descargado: " + est.getMax());
        System.out.println(" Libro menos descargado: " + est.getMin());
        System.out.println(" Cantidad de registros evaluados para calcular las estadísticas: " + est.getCount());
    }
}
