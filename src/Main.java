import ec.edu.puce.biblioteca.modelo.Libro;

void main() {
    Libro[] libros = new Libro[5];
    int contador = 0;
    int opcion = 0;

    IO.println("=== Biblioteca PUCE ===");

    while (opcion != 3) {
        IO.println("\n1. Registrar libro");
        IO.println("2. Mostrar libros");
        IO.println("3. Salir");
        opcion = leerEntero("Elige una opción: ");

        if (opcion == 1) {
            if (contador >= libros.length) {
                IO.println("El almacenamiento está lleno (máximo " + libros.length + " libros).");
            } else {
                String titulo = leerTexto("Título: ");
                String autor = leerTexto("Autor: ");
                int anio = leerAnio();
                libros[contador] = new Libro(titulo, autor, anio);
                contador++;
                IO.println("Libro registrado correctamente.");
            }
        } else if (opcion == 2) {
            if (contador == 0) {
                IO.println("Todavía no hay libros registrados.");
            } else {
                IO.println("\n--- Libros registrados ---");
                for (int i = 0; i < contador; i++) {
                    IO.println((i + 1) + ". " + libros[i].titulo()
                            + " | " + libros[i].autor()
                            + " | " + libros[i].anio());
                }
            }
        } else if (opcion == 3) {
            IO.println("Programa finalizado. ¡Hasta luego!");
        } else {
            IO.println("Opción inválida, elige 1, 2 o 3.");
        }
    }
}

String leerTexto(String mensaje) {
    String texto = IO.readln(mensaje).trim();
    while (texto.isEmpty()) {
        IO.println("El campo no puede estar vacío.");
        texto = IO.readln(mensaje).trim();
    }
    return texto;
}

int leerEntero(String mensaje) {
    while (true) {
        try {
            return Integer.parseInt(IO.readln(mensaje).trim());
        } catch (NumberFormatException e) {
            IO.println("Ingresa solo números enteros.");
        }
    }
}

int leerAnio() {
    int maximo = LocalDate.now().getYear();
    int anio = leerEntero("Año de publicación: ");
    while (anio < 1450 || anio > maximo) {
        IO.println("Año inválido. Debe estar entre 1450 y " + maximo + ".");
        anio = leerEntero("Año de publicación: ");
    }
    return anio;
}