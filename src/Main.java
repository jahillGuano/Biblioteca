import ec.edu.puce.biblioteca.modelo.Libro;

void main() {
    Libro[] libros = new Libro[5];
    int contador = 0;
    int opcion = 0;

    while (opcion != 6) {
        IO.println("\n=== Biblioteca PUCE ===");
        IO.println("1. Registrar libro");
        IO.println("2. Mostrar libros");
        IO.println("3. Insertar ordenado por año");
        IO.println("4. Buscar por año (binaria)");
        IO.println("5. Ordenar por año (burbuja)");
        IO.println("6. Salir");
        opcion = leerEntero("Elige una opción: ");

        if (opcion == 1) {
            if (contador >= libros.length) {
                IO.println("Array lleno (máximo " + libros.length + ").");
            } else {
                String titulo = leerTexto("Título: ");
                String autor = leerTexto("Autor: ");
                int anio = leerAnio();
                contador = registrar(libros, contador, new Libro(titulo, autor, anio));
                IO.println("Libro registrado.");
            }

        } else if (opcion == 2) {
            mostrar(libros, contador);

        } else if (opcion == 3) {
            if (contador >= libros.length) {
                IO.println("Array lleno (máximo " + libros.length + ").");
            } else {
                String titulo = leerTexto("Título: ");
                String autor = leerTexto("Autor: ");
                int anio = leerAnio();
                contador = insertarOrdenado(libros, contador, new Libro(titulo, autor, anio));
                mostrar(libros, contador);
            }

        } else if (opcion == 4) {
            if (contador == 0) {
                IO.println("No hay libros registrados.");
            } else {
                // La búsqueda binaria necesita el array ordenado
                ordenarBurbuja(libros, contador);
                int anioBuscado = leerEntero("Año a buscar: ");
                int pos = buscarBinario(libros, contador, anioBuscado);
                if (pos == -1) {
                    IO.println("No encontrado.");
                } else {
                    IO.println("Encontrado en la posición " + pos + ": " + libros[pos]);
                }
            }

        } else if (opcion == 5) {
            ordenarBurbuja(libros, contador);
            mostrar(libros, contador);

        } else if (opcion == 6) {
            IO.println("Programa finalizado.");

        } else {
            IO.println("Opción inválida.");
        }
    }
}
int registrar(Libro[] libros, int contador, Libro nuevo) {
    libros[contador] = nuevo;
    return contador + 1;
}

/* Inserta manteniendo el orden por año (el array ya debe estar ordenado) */
int insertarOrdenado(Libro[] libros, int contador, Libro nuevo) {
    int pos = 0;
    while (pos < contador && libros[pos].anio() < nuevo.anio()) {
        pos++;
    }
    for (int i = contador; i > pos; i--) {      // desplaza de derecha a izquierda
        libros[i] = libros[i - 1];
    }
    libros[pos] = nuevo;
    return contador + 1;
}

/* Búsqueda binaria sobre un array ordenado por año */
int buscarBinario(Libro[] libros, int contador, int anioBuscado) {
    int bajo = 0;
    int alto = contador - 1;
    while (bajo <= alto) {
        int medio = bajo + (alto - bajo) / 2;
        if (libros[medio].anio() == anioBuscado) {
            return medio;
        }
        if (libros[medio].anio() < anioBuscado) {
            bajo = medio + 1;
        } else {
            alto = medio - 1;
        }
    }
    return -1;
}

/* Ordenamiento burbuja solo sobre la parte usada del array */
void ordenarBurbuja(Libro[] libros, int contador) {
    for (int i = 0; i < contador - 1; i++) {
        for (int j = 0; j < contador - 1 - i; j++) {
            if (libros[j].anio() > libros[j + 1].anio()) {
                Libro temp = libros[j];
                libros[j] = libros[j + 1];
                libros[j + 1] = temp;
            }
        }
    }
}

void mostrar(Libro[] libros, int contador) {
    if (contador == 0) {
        IO.println("No hay libros registrados.");
        return;
    }
    IO.println("--- Libros registrados ---");
    for (int i = 0; i < contador; i++) {
        IO.println(i + ". " + libros[i].titulo() + " | " + libros[i].autor() + " | " + libros[i].anio());
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