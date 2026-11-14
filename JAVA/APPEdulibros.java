import java.util.Scanner;

public class APPEdulibros {
    Scanner sc = new Scanner(System.in);

    // Arreglos temporales: cada fila guarda un registro.
    String[][] usuarios = new String[100][5];
    String[][] materiales = new String[100][7];
    String[][] donaciones = new String[100][6];
    String[][] solicitudes = new String[100][6];
    String[][] prestamos = new String[100][8];
    String[][] novedades = new String[100][5];

    int nU = 0, nM = 0, nD = 0, nS = 0, nP = 0, nN = 0;
    String usuario = "", rol = "";

    public static void main(String[] args) {
        APPEdulibros programa = new APPEdulibros();
        programa.iniciar();
    }
        // Cuenta inicial para ingresar y crear los demas usuarios.
        void iniciar() {
        usuarios[0][0] = "1";
        usuarios[0][1] = "Administrador";
        usuarios[0][2] = "admin@edulibros.com";
        usuarios[0][3] = "admin123";
        usuarios[0][4] = "Administrador";
        nU = 1;

        int op = 0;
        while (op != 2) {
            System.out.println("\n==================================");
            System.out.println("          BIENVENIDO A EDULIBROS");
            System.out.println("==================================");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Salir");
            op = numero("Seleccione una opcion: ");
            if (op == 1) login();
            else if (op == 2) System.out.println("Gracias por utilizar EduLibros.");
            else System.out.println("Opcion incorrecta.");
        }
    }

    int numero(String m) {
        System.out.print(m);
        while (!sc.hasNextInt()) {
            System.out.println("Ingrese un numero valido.");
            sc.nextLine();
            System.out.print(m);
        }
        int x = sc.nextInt();
        sc.nextLine();
        return x;
    }

    String texto(String m) {
        System.out.print(m);
        return sc.nextLine();
    }

    void login() {
        String correo = texto("Correo: ");
        String clave = texto("Contrasena: ");
        boolean ok = false;
        for (int i = 0; i < nU; i++) {
            if (usuarios[i][2].equalsIgnoreCase(correo) && usuarios[i][3].equals(clave)) {
                usuario = usuarios[i][1];
                rol = usuarios[i][4];
                ok = true;
            }
        }
        if (ok) {
            System.out.println("Bienvenido(a), " + usuario + ". Rol: " + rol);
            menu();
        } else System.out.println("Datos incorrectos. No fue posible iniciar sesion.");
    }

    void menu() {
        int op = 0;
        while (op != 7) {
            System.out.println("\n========== MENU EDULIBROS ==========");
            System.out.println("Sesion: " + usuario + " (" + rol + ")");
            System.out.println("1. Usuarios");
            System.out.println("2. Inventario y catalogo");
            System.out.println("3. Donaciones");
            System.out.println("4. Solicitudes y prestamos");
            System.out.println("5. Devoluciones y novedades");
            System.out.println("6. Reportes");
            System.out.println("7. Cerrar sesion");
            op = numero("Opcion: ");
            switch (op) {
                case 1:
                    if (rol.equals("Administrador")) menuUsuarios();
                    else System.out.println("Solo el administrador puede gestionar usuarios.");
                    break;
                case 2: menuInventario(); break;
                case 3:
                    if (rol.equals("Estudiante")) System.out.println("No tiene permiso para gestionar donaciones.");
                    else menuDonaciones();
                    break;
                case 4: menuPrestamos(); break;
                case 5:
                    if (rol.equals("Estudiante")) System.out.println("No tiene permiso para registrar devoluciones.");
                    else menuDevoluciones();
                    break;
                case 6:
                    if (rol.equals("Estudiante")) System.out.println("No tiene permiso para consultar reportes.");
                    else menuReportes();
                    break;
                case 7:
                    usuario = ""; rol = "";
                    System.out.println("Sesion cerrada correctamente.");
                    break;
                default: System.out.println("Opcion incorrecta.");
            }
        }
    }

    void menuUsuarios() {
        int op = 0;
        while (op != 4) {
            System.out.println("\n--- USUARIOS ---");
            System.out.println("1. Registrar  2. Consultar  3. Actualizar  4. Volver");
            op = numero("Opcion: ");
            if (op == 1) {
                if (nU < 100) {
                    String nom = texto("Nombre: ");
                    String id = texto("Identificacion/codigo: ");
                    String correo = texto("Correo: ");
                    String clave = texto("Contrasena de prueba: ");
                    System.out.println("1. Estudiante  2. Bibliotecario  3. Administrador");
                    int r = numero("Rol: ");
                    String tipo = r == 1 ? "Estudiante" : r == 2 ? "Bibliotecario" : r == 3 ? "Administrador" : "";
                    if (!nom.isEmpty() && !id.isEmpty() && !correo.isEmpty() && !clave.isEmpty() && !tipo.isEmpty()) {
                        usuarios[nU][0] = "" + (nU + 1); usuarios[nU][1] = nom;
                        usuarios[nU][2] = correo; usuarios[nU][3] = clave; usuarios[nU][4] = tipo;
                        nU++; System.out.println("Usuario registrado.");
                    } else System.out.println("Datos incompletos o rol incorrecto.");
                } else System.out.println("Limite de usuarios alcanzado.");
            } else if (op == 2) {
                for (int i = 0; i < nU; i++)
                    System.out.println(usuarios[i][0] + " | " + usuarios[i][1] + " | " + usuarios[i][2] + " | " + usuarios[i][4]);
                if (nU == 0) System.out.println("No hay usuarios.");
            } else if (op == 3) {
                listarUsuarios();
                int id = numero("ID a modificar: ");
                if (id > 0 && id <= nU) {
                    int i = id - 1;
                    usuarios[i][1] = texto("Nuevo nombre: ");
                    usuarios[i][2] = texto("Nuevo correo: ");
                    System.out.println("1. Estudiante 2. Bibliotecario 3. Administrador");
                    int r = numero("Nuevo rol: ");
                    if (r == 1) usuarios[i][4] = "Estudiante";
                    else if (r == 2) usuarios[i][4] = "Bibliotecario";
                    else if (r == 3) usuarios[i][4] = "Administrador";
                    System.out.println("Usuario actualizado.");
                } else System.out.println("ID no encontrado.");
            }
        }
    }

    void listarUsuarios() {
        for (int i = 0; i < nU; i++)
            System.out.println(usuarios[i][0] + " | " + usuarios[i][1] + " | " + usuarios[i][4]);
    }

    void menuInventario() {
        int op = 0;
        while (op != 7) {
            System.out.println("\n--- INVENTARIO ---");
            System.out.println("1. Registrar material");
            System.out.println("2. Consultar catalogo");
            System.out.println("3. Buscar por nombre");
            System.out.println("4. Filtrar por categoria");
            System.out.println("5. Actualizar material");
            System.out.println("6. Cambiar estado fisico");
            System.out.println("7. Volver");
            op = numero("Opcion: ");
            if (op == 1) {
                if (nM < 100) {
                    materiales[nM][0] = "" + (nM + 1);
                    materiales[nM][1] = texto("Titulo: ");
                    materiales[nM][2] = texto("Categoria: ");
                    materiales[nM][3] = texto("Descripcion: ");
                    System.out.println("1. Nuevo 2. Con uso 3. Danado");
                    int e = numero("Estado fisico: ");
                    materiales[nM][4] = e == 1 ? "Nuevo" : e == 2 ? "Con uso" : "Danado";
                    materiales[nM][5] = texto("Ubicacion: ");
                    materiales[nM][6] = e == 3 ? "Fuera de servicio" : "Disponible";
                    nM++; System.out.println("Material registrado.");
                }
            } else if (op == 2) listarMateriales();
            else if (op == 3) {
                String q = texto("Nombre o parte del titulo: "); boolean hallado = false;
                for (int i = 0; i < nM; i++) if (materiales[i][1].toLowerCase().contains(q.toLowerCase())) {
                    mostrarMaterial(i); hallado = true;
                }
                if (!hallado) System.out.println("No se encontraron coincidencias.");
            } else if (op == 4) {
                String q = texto("Categoria: "); boolean hallado = false;
                for (int i = 0; i < nM; i++) if (materiales[i][2].equalsIgnoreCase(q)) {
                    mostrarMaterial(i); hallado = true;
                }
                if (!hallado) System.out.println("No hay materiales en esa categoria.");
            } else if (op == 5 || op == 6) {
                listarMateriales();
                int id = numero("ID del material: ");
                if (id > 0 && id <= nM) {
                    int i = id - 1;
                    if (op == 5) {
                        materiales[i][2] = texto("Nueva categoria: ");
                        materiales[i][3] = texto("Nueva descripcion: ");
                        materiales[i][5] = texto("Nueva ubicacion: ");
                    } else {
                        System.out.println("1. Nuevo 2. Con uso 3. Danado");
                        int e = numero("Estado: ");
                        if (e >= 1 && e <= 3) {
                            materiales[i][4] = e == 1 ? "Nuevo" : e == 2 ? "Con uso" : "Danado";
                            if (e == 3) materiales[i][6] = "Fuera de servicio";
                            else if (!materiales[i][6].equals("Prestado")) materiales[i][6] = "Disponible";
                        }
                    }
                    System.out.println("Informacion actualizada.");
                } else System.out.println("ID no encontrado.");
            }
        }
    }

    void listarMateriales() {
        if (nM == 0) System.out.println("No hay materiales registrados.");
        for (int i = 0; i < nM; i++) mostrarMaterial(i);
    }

    void mostrarMaterial(int i) {
        System.out.println("ID " + materiales[i][0] + " | " + materiales[i][1] + " | "
                + materiales[i][2] + " | Estado: " + materiales[i][4] + " | Disponibilidad: " + materiales[i][6]);
    }

    void menuDonaciones() {
        int op = 0;
        while (op != 5) {
            System.out.println("\n--- DONACIONES ---");
            System.out.println("1. Registrar 2. Ver pendientes 3. Aceptar 4. Rechazar / historial 5. Volver");
            op = numero("Opcion: ");
            if (op == 1 && nD < 100) {
                donaciones[nD][0] = "" + (nD + 1);
                donaciones[nD][1] = texto("Material: ");
                donaciones[nD][2] = texto("Categoria: ");
                donaciones[nD][3] = texto("Fecha (AAAA-MM-DD): ");
                donaciones[nD][4] = texto("Donante (opcional): ");
                donaciones[nD][5] = "Pendiente";
                nD++; System.out.println("Donacion registrada como pendiente.");
            } else if (op == 2 || op == 4) {
                for (int i = 0; i < nD; i++)
                    if (op == 4 || donaciones[i][5].equals("Pendiente"))
                        System.out.println(donaciones[i][0] + " | " + donaciones[i][1] + " | " + donaciones[i][5]);
            } else if (op == 3 || op == 4) {
                int id = numero("ID de donacion: ");
                if (id > 0 && id <= nD && donaciones[id - 1][5].equals("Pendiente")) {
                    int i = id - 1;
                    if (op == 3) {
                        donaciones[i][5] = "Aceptada";
                        if (nM < 100) {
                            materiales[nM][0] = "" + (nM + 1); materiales[nM][1] = donaciones[i][1];
                            materiales[nM][2] = donaciones[i][2]; materiales[nM][3] = "Donacion";
                            materiales[nM][4] = "Con uso"; materiales[nM][5] = "Por asignar";
                            materiales[nM][6] = "Disponible"; nM++;
                        }
                        System.out.println("Donacion aceptada e incorporada al inventario.");
                    } else {
                        donaciones[i][5] = "Rechazada";
                        System.out.println("Donacion rechazada y conservada en el historial.");
                    }
                } else System.out.println("No existe una donacion pendiente con ese ID.");
            }
        }
    }

    void menuPrestamos() {
        int op = 0;
        while (op != 7) {
            System.out.println("\n--- SOLICITUDES Y PRESTAMOS ---");
            System.out.println("1. Solicitar material");
            System.out.println("2. Consultar solicitudes");
            System.out.println("3. Aprobar solicitud");
            System.out.println("4. Rechazar solicitud");
            System.out.println("5. Registrar entrega");
            System.out.println("6. Consultar prestamos / vencidos");
            System.out.println("7. Volver");
            op = numero("Opcion: ");
            if (op == 1 && nS < 100) {
                listarMateriales();
                int id = numero("ID del material: ");
                if (id > 0 && id <= nM && materiales[id - 1][6].equals("Disponible")) {
                    solicitudes[nS][0] = "" + (nS + 1); solicitudes[nS][1] = usuario;
                    solicitudes[nS][2] = "" + id; solicitudes[nS][3] = materiales[id - 1][1];
                    solicitudes[nS][4] = texto("Fecha de solicitud (AAAA-MM-DD): ");
                    solicitudes[nS][5] = "Pendiente"; nS++;
                    System.out.println("Solicitud registrada como pendiente.");
                } else System.out.println("Material inexistente o no disponible.");
            } else if (op == 2) {
                for (int i = 0; i < nS; i++)
                    System.out.println(solicitudes[i][0] + " | " + solicitudes[i][1] + " | "
                            + solicitudes[i][3] + " | " + solicitudes[i][5]);
            } else if (op == 3 || op == 4) {
                int id = numero("ID de solicitud: ");
                if (id > 0 && id <= nS && solicitudes[id - 1][5].equals("Pendiente")) {
                    solicitudes[id - 1][5] = op == 3 ? "Aprobada" : "Rechazada";
                    System.out.println("Solicitud " + solicitudes[id - 1][5].toLowerCase() + ".");
                } else System.out.println("Solicitud no encontrada o ya procesada.");
            } else if (op == 5 && nP < 100) {
                for (int i = 0; i < nS; i++) if (solicitudes[i][5].equals("Aprobada"))
                    System.out.println(solicitudes[i][0] + " | " + solicitudes[i][1] + " | " + solicitudes[i][3]);
                int id = numero("ID de solicitud aprobada: ");
                if (id > 0 && id <= nS && solicitudes[id - 1][5].equals("Aprobada")) {
                    int m = Integer.parseInt(solicitudes[id - 1][2]) - 1;
                    prestamos[nP][0] = "" + (nP + 1); prestamos[nP][1] = solicitudes[id - 1][1];
                    prestamos[nP][2] = solicitudes[id - 1][2]; prestamos[nP][3] = solicitudes[id - 1][3];
                    prestamos[nP][4] = texto("Fecha de entrega (AAAA-MM-DD): ");
                    prestamos[nP][5] = texto("Fecha limite (AAAA-MM-DD): ");
                    prestamos[nP][6] = "Activo"; prestamos[nP][7] = "";
                    nP++; solicitudes[id - 1][5] = "Entregada"; materiales[m][6] = "Prestado";
                    System.out.println("Entrega registrada. Prestamo activo.");
                } else System.out.println("No se encontro una solicitud aprobada.");
            } else if (op == 6) {
                String actual = texto("Fecha actual (AAAA-MM-DD) para identificar vencidos, o deje vacio: ");
                for (int i = 0; i < nP; i++) {
                    if (rol.equals("Estudiante") && !prestamos[i][1].equals(usuario)) continue;
                    boolean vencido = prestamos[i][6].equals("Activo") && !actual.isEmpty()
                            && actual.compareTo(prestamos[i][5]) > 0;
                    System.out.println(prestamos[i][0] + " | " + prestamos[i][1] + " | " + prestamos[i][3]
                            + " | Limite: " + prestamos[i][5] + " | " + (vencido ? "VENCIDO" : prestamos[i][6]));
                }
            }
        }
    }

    void menuDevoluciones() {
        int op = 0;
        while (op != 3) {
            System.out.println("\n--- DEVOLUCIONES ---");
            System.out.println("1. Registrar devolucion 2. Consultar novedades 3. Volver");
            op = numero("Opcion: ");
            if (op == 1) {
                for (int i = 0; i < nP; i++) if (prestamos[i][6].equals("Activo"))
                    System.out.println(prestamos[i][0] + " | " + prestamos[i][3] + " | " + prestamos[i][1]);
                int id = numero("ID del prestamo: ");
                if (id > 0 && id <= nP && prestamos[id - 1][6].equals("Activo")) {
                    int i = id - 1, m = Integer.parseInt(prestamos[i][2]) - 1;
                    String fecha = texto("Fecha de devolucion (AAAA-MM-DD): ");
                    prestamos[i][6] = "Finalizado"; prestamos[i][7] = fecha;
                    System.out.println("1. Buen estado 2. Danado 3. Extraviado");
                    int estado = numero("Condicion: ");
                    if (estado == 1) materiales[m][6] = "Disponible";
                    else if (estado == 2 || estado == 3) {
                        String tipo = estado == 2 ? "Danado" : "Extraviado";
                        materiales[m][4] = tipo;
                        materiales[m][6] = estado == 2 ? "Fuera de servicio" : "Extraviado";
                        novedades[nN][0] = "" + (nN + 1); novedades[nN][1] = prestamos[i][2];
                        novedades[nN][2] = prestamos[i][3]; novedades[nN][3] = tipo;
                        novedades[nN][4] = texto("Observacion: "); nN++;
                    }
                    if (fecha.compareTo(prestamos[i][5]) > 0) System.out.println("Devolucion tardia.");
                    else System.out.println("Devolucion dentro del plazo.");
                    System.out.println("Devolucion registrada.");
                } else System.out.println("Prestamo activo no encontrado.");
            } else if (op == 2) {
                if (nN == 0) System.out.println("No hay novedades.");
                for (int i = 0; i < nN; i++)
                    System.out.println(novedades[i][0] + " | " + novedades[i][2] + " | "
                            + novedades[i][3] + " | " + novedades[i][4]);
            }
        }
    }

    void menuReportes() {
        int op = 0;
        while (op != 6) {
            System.out.println("\n--- REPORTES ---");
            System.out.println("1. Inventario 2. Materiales mas solicitados 3. Prestamos");
            System.out.println("4. Donaciones 5. Novedades 6. Volver");
            op = numero("Opcion: ");
            if (op == 1) {
                int disponibles = 0, prestados = 0, fuera = 0;
                for (int i = 0; i < nM; i++) {
                    if (materiales[i][6].equals("Disponible")) disponibles++;
                    else if (materiales[i][6].equals("Prestado")) prestados++;
                    else fuera++;
                }
                System.out.println("Total: " + nM + " | Disponibles: " + disponibles
                        + " | Prestados: " + prestados + " | Fuera de servicio/extraviados: " + fuera);
            } else if (op == 2) {
                for (int i = 0; i < nM; i++) {
                    int total = 0;
                    for (int j = 0; j < nS; j++)
                        if (solicitudes[j][2].equals(materiales[i][0])) total++;
                    System.out.println(materiales[i][1] + ": " + total + " solicitud(es)");
                }
                System.out.println("Conteo basico; no se ordenan los resultados.");
            } else if (op == 3) {
                int activos = 0, finalizados = 0;
                for (int i = 0; i < nP; i++)
                    if (prestamos[i][6].equals("Activo")) activos++; else finalizados++;
                System.out.println("Activos: " + activos + " | Finalizados: " + finalizados);
            } else if (op == 4) {
                int pendientes = 0, aceptadas = 0, rechazadas = 0;
                for (int i = 0; i < nD; i++) {
                    if (donaciones[i][5].equals("Pendiente")) pendientes++;
                    else if (donaciones[i][5].equals("Aceptada")) aceptadas++;
                    else rechazadas++;
                }
                System.out.println("Pendientes: " + pendientes + " | Aceptadas: " + aceptadas
                        + " | Rechazadas: " + rechazadas);
            } else if (op == 5) {
                for (int i = 0; i < nN; i++)
                    System.out.println(novedades[i][2] + " | " + novedades[i][3] + " | " + novedades[i][4]);
                if (nN == 0) System.out.println("No hay novedades registradas.");
            }
        }
    }
}
