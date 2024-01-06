/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.Dao;

import controlador.TDA.Listas.DynamicList;
import controlador.TDA.Listas.Exceptions.EmptyException;
import controlador.Utiles.Utiles;
import controlador.dao.DaoImplement;
import java.lang.reflect.Field;
import modelos.Pasajeros;
import modelos.VentaBoleto;

/**
 *
 * @author Jhostin Roja
 */
public class DaoPasajeros extends DaoImplement<Pasajeros> {

    private DynamicList<Pasajeros> PasajerosList = new DynamicList<>();
    private Pasajeros pasajeros;

    public DaoPasajeros() {
        super(Pasajeros.class);
    }

    public DynamicList<Pasajeros> getPasajerosList() {
        PasajerosList = all();
        return PasajerosList;
    }

    public void setPasajerosList(DynamicList<Pasajeros> PasajerosList) {
        this.PasajerosList = PasajerosList;
    }

    public Pasajeros getPasajero() {
        if (pasajeros == null) {
            pasajeros = new Pasajeros();
        }
        return pasajeros;
    }

    public void setPasajeros(Pasajeros pasajeros) {
        this.pasajeros = pasajeros;
    }

    public Boolean persist() {
        pasajeros.setIdPasajeros(all().getLength() + 1);
        return persist(pasajeros);
    }

//    // Metodo Ordenar Burbuja
//    public DynamicList<Pasajeros> ordenar(DynamicList<Pasajeros> lista, Integer tipo, String field) throws EmptyException, Exception {
//        Field attribute = Utiles.getField(Pasajeros.class, field);
//        Integer n = lista.getLength();
//        Pasajeros[] pasajeros = lista.toArray();
//        if (attribute != null) {
//            for (int i = 0; i < n - 1; i++) {
//                int k = i;
//                Pasajeros t = pasajeros[i];
//                for (int j = i + 1; j < n; j++) {
//                    if (pasajeros[j].compare(t, field, tipo)) {
//                        t = pasajeros[j];
//                        k = j;
//                    }
//                }
//                pasajeros[k] = pasajeros[i];
//                pasajeros[i] = t;
//            }
//        } else {
//            throw new Exception("No existe el criterio de busqueda");
//        }
//        return lista.toList(pasajeros);
//
//    }
//    public static void main(String[] args) {
//        try {
//            DaoPasajeros pc = new DaoPasajeros();
//            System.out.println(pc.all().toString());
//            System.out.println("-------");
//            System.out.println(pc.ordenar( pc.all(), 0, "Apellidos".toString()));
//        } catch (Exception e) {
//        }
////    }
    public DynamicList<Pasajeros> metodoOrdenarQuicksort(DynamicList<Pasajeros> lista, Integer tipo, String field) throws EmptyException, Exception {
        Pasajeros[] pasajeros = lista.toArray();
        Field attribute = Utiles.getField(Pasajeros.class, field);

        if (attribute != null) {
            quickSort(pasajeros, 0, pasajeros.length - 1, tipo, field);
        }
        else {
            throw new Exception("El criterio de búsqueda no existe");
        }
        return lista.toList(pasajeros);
    }

    private void quickSort(Pasajeros[] pasajeros, int izq, int der, Integer tipo, String field) {
        if (izq < der) {
            int particionIndex = particion(pasajeros, izq, der, tipo, field);
            quickSort(pasajeros, izq, particionIndex - 1, tipo, field);
            quickSort(pasajeros, particionIndex + 1, der, tipo, field);
        }
    }

    private int particion(Pasajeros[] pasajeros, int izq, int der, Integer tipo, String field) {
        Pasajeros pivot = pasajeros[der];
        int i = izq - 1;
        for (int j = izq; j < der; j++) {
            if (pasajeros[j].compare(pivot, field, tipo)) {
                i++;
                Pasajeros temp = pasajeros[i];
                pasajeros[i] = pasajeros[j];
                pasajeros[j] = temp;
            }
        }
        Pasajeros temp = pasajeros[i + 1];
        pasajeros[i + 1] = pasajeros[der];
        pasajeros[der] = temp;
        return i + 1;
    }

    public DynamicList<Pasajeros> metodoShellSortOrden(DynamicList<Pasajeros> lista, Integer tipo, String field) throws EmptyException, Exception {
        long starTime = System.nanoTime();
        Field attribute = Utiles.getField(Pasajeros.class, field);
        Pasajeros[] pasajeros = lista.toArray();

        if (attribute == null) {
            throw new Exception("El criterio de búsqueda no existe");
        }

        int N = pasajeros.length;
        int salto = N / 2;

        while (salto >= 1) {
            for (int i = 0; i < salto; i++) {
                for (int j = salto + i; j < N; j += salto) {
                    Pasajeros v = pasajeros[j];
                    int n = j - salto;

                    while (n >= 0 && pasajeros[n].compare(v, field, tipo)) {
                        pasajeros[n + salto] = pasajeros[n];
                        n -= salto;
                    }

                    pasajeros[n + salto] = v;
                }
            }
            salto /= 2;
        }

        lista.reset();
        for (Pasajeros pasajeros1 : pasajeros) {
            lista.add(pasajeros1);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - starTime);
        System.out.println("Tiempo de ejecución:" + duration + "nanosegundos");
        return lista.toList(pasajeros);
        //}
        ////public static void main(String[] args) {
        ////    DaoPasajeros controlPasajero = new DaoPasajeros();
        ////
        ////    try {
        ////        DynamicList<Pasajeros> listadePasajeros = controlPasajero.getPasajerosList();
        ////        DynamicList<Pasajeros> listaOrdenada = controlPasajero.shellSortOrden(listadePasajeros, 0, "nombres");
        ////
        ////        for (int i = 0; i < listaOrdenada.getLength(); i++) {
        ////            Pasajeros pasajeros = listaOrdenada.getInfo(i);
        ////            System.out.println("Nombre: " + pasajeros.getNombres() + ", ID: " + pasajeros.getIdPasajeros());
        ////        }
        ////        } catch (EmptyException e){
        ////            e.printStackTrace();
        ////    } catch (Exception ex) {
        ////        ex.printStackTrace();
        ////    }
        ////}
        //
    }

    public DynamicList<VentaBoleto> ordenarQuicksort(DynamicList<VentaBoleto> lista, Integer tipo, String field) throws EmptyException, Exception {
        VentaBoleto[] ventaBoletos = lista.toArray();
        Field attribute = Utiles.getField(Pasajeros.class, field);
        if (attribute != null) {
            quickSort(ventaBoletos, 0, ventaBoletos.length - 1, tipo, field);
        }
        else {
            throw new Exception("El criterio de búsqueda no existe");
        }
        return lista.toList(ventaBoletos);
    }

    private void quickSort(VentaBoleto[] ventaBoletos, int izq, int der, Integer tipo, String field) {
        if (izq < der) {
            int particionIndex = particion(ventaBoletos, izq, der, tipo, field);
            quickSort(ventaBoletos, izq, particionIndex - 1, tipo, field);
            quickSort(ventaBoletos, particionIndex + 1, der, tipo, field);
        }
    }

    private int particion(VentaBoleto[] ventaBoletos, int izq, int der, Integer tipo, String field) {
        VentaBoleto pivot = ventaBoletos[der];
        int i = izq - 1;
        for (int j = izq; j < der; j++) {
            if (ventaBoletos[j].compare(pivot, field, tipo)) {
                i++;
                VentaBoleto temp = ventaBoletos[i];
                ventaBoletos[i] = ventaBoletos[j];
                ventaBoletos[j] = temp;
            }
        }
        VentaBoleto temp = ventaBoletos[i + 1];
        ventaBoletos[i + 1] = ventaBoletos[der];
        ventaBoletos[der] = temp;
        return i + 1;
    }

    public DynamicList<VentaBoleto> shellSortOrden(DynamicList<VentaBoleto> lista, Integer tipo, String field) throws EmptyException, Exception {
        long starTime = System.nanoTime();
        Field attribute = Utiles.getField(Pasajeros.class, field);
        VentaBoleto[] ventaBoletos = lista.toArray();
        if (attribute == null) {
            throw new Exception("El criterio de búsqueda no existe");
        }
        int N = ventaBoletos.length;
        int salto = N / 2;
        while (salto >= 1) {
            for (int i = 0; i < salto; i++) {
                for (int j = salto + i; j < N; j += salto) {
                    VentaBoleto v = ventaBoletos[j];
                    int n = j - salto;
                    while (n >= 0 && ventaBoletos[n].compare(v, field, tipo)) {
                        ventaBoletos[n + salto] = ventaBoletos[n];
                        n -= salto;
                    }

                    ventaBoletos[n + salto] = v;
                }
            }
            salto /= 2;
        }

        lista.reset();
        for (VentaBoleto ventaBoleto1 : ventaBoletos) {
            lista.add(ventaBoleto1);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - starTime);
        System.out.println("Tiempo de ejecución:" + duration + "nanosegundos");
        return lista.toList(ventaBoletos);
    }

    public int busquedaBinaria(DynamicList<Pasajeros> lista, String valorABuscar, String campo) throws EmptyException, Exception {
        DynamicList<Pasajeros> listaOrdenada = metodoOrdenarQuicksort(lista, 0, campo); // Ordenar la lista
        Pasajeros[] pasajeros = listaOrdenada.toArray(); // Convertir a un arreglo para realizar la búsqueda binaria

        int izquierda = 0;
        int derecha = pasajeros.length - 1;

        while (izquierda <= derecha) {
            int medio = izquierda + (derecha - izquierda) / 2;

            switch (campo) {
                case "Apellidos":
                    int comparacion = pasajeros[medio].getApellidos().compareTo(valorABuscar);
                    if (comparacion == 0) {
                        return medio; // Se encontró el elemento
                    }
                    else if (comparacion < 0) {
                        izquierda = medio + 1;
                    }
                    else {
                        derecha = medio - 1;
                    }
                    break;
                // Puedes añadir más casos para otros campos si es necesario
                default:
                    break;
            }
        }
        return -1; // No se encontró el elemento
    }

    public int busquedaLineal(DynamicList<Pasajeros> lista, String valorABuscar, String campo) throws EmptyException {
        for (int i = 0; i < lista.getLength(); i++) {
            Pasajeros pasajero = lista.getInfo(i);
            switch (campo) {
                case "Apellidos":
                    if (pasajero.getApellidos().equals(valorABuscar)) {
                        return i; // Se encontró el elemento
                    }
                    break;
                // Puedes añadir más casos para otros campos si es necesario
                default:
                    break;
            }
        }
        return -1;

    }

    public DynamicList<Pasajeros> buscarTotalBinario(DynamicList<Pasajeros> lista, Double total) throws Exception {
        DynamicList<Pasajeros> lo = this.metodoShellSortOrden(lista, 0, "total");
        Pasajeros[] p = lo.toArray();
        DynamicList<Pasajeros> result = new DynamicList<Pasajeros>();

        int left = 0;
        int right = lista.getLength() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (p[mid].getDatosboleto().getPrecioTotal().doubleValue() == total.doubleValue()) {
                result.add(p[mid]);

                int temp = mid - 1;
                while (temp >= left && p[temp].getDatosboleto().getPrecioTotal().doubleValue() == total.doubleValue()) {
                    result.add(p[temp]);
                    temp--;
                }

                temp = mid + 1;
                while (temp <= right && p[temp].getDatosboleto().getPrecioTotal().doubleValue() == total.doubleValue()) {
                    result.add(p[temp]);
                    temp++;
                }
                return result;
            }
            if (p[mid].getDatosboleto().getPrecioTotal().doubleValue() < total.doubleValue()) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return result;
    }

    public DynamicList<Pasajeros> buscarNombresBinario(DynamicList<Pasajeros> lista, String nombre) throws Exception {
        DynamicList<Pasajeros> lo = this.metodoOrdenarQuicksort(lista, 0, "nombres");
        Pasajeros[] p = lo.toArray();
        DynamicList<Pasajeros> result = new DynamicList<Pasajeros>();

        int left = 0;
        int right = lista.getLength() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (p[mid].getNombres().equalsIgnoreCase(nombre)) {
                result.add(p[mid]);

                int temp = mid - 1;
                while (temp >= left && p[temp].getNombres().equalsIgnoreCase(nombre)) {
                    result.add(p[temp]);
                    temp--;
                }

                temp = mid + 1;
                while (temp <= right && p[temp].getNombres().equalsIgnoreCase(nombre)) {
                    result.add(p[temp]);
                    temp++;
                }
                return result;
            }
            if (p[mid].getNombres().compareToIgnoreCase(nombre) < 0) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return result;
    }

    public DynamicList<Pasajeros> buscarApellidosBinario(DynamicList<Pasajeros> lista, String apellido) throws Exception {
        DynamicList<Pasajeros> lo = this.metodoOrdenarQuicksort(lista, 0, "apellidos");
        Pasajeros[] p = lo.toArray();
        DynamicList<Pasajeros> result = new DynamicList<Pasajeros>();

        int left = 0;
        int right = lista.getLength() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (p[mid].getApellidos().equalsIgnoreCase(apellido)) {
                result.add(p[mid]);

                int temp = mid - 1;
                while (temp >= left && p[temp].getApellidos().equalsIgnoreCase(apellido)) {
                    result.add(p[temp]);
                    temp--;
                }

                temp = mid + 1;
                while (temp <= right && p[temp].getApellidos().equalsIgnoreCase(apellido)) {
                    result.add(p[temp]);
                    temp++;
                }
                return result;
            }
            if (p[mid].getApellidos().compareToIgnoreCase(apellido) < 0) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return result;
    }

    public DynamicList<Pasajeros> buscarDniBinario(DynamicList<Pasajeros> lista, String dni) throws Exception {
        DynamicList<Pasajeros> lo = this.metodoOrdenarQuicksort(lista, 0, "dni");
        Pasajeros[] p = lo.toArray();
        DynamicList<Pasajeros> result = new DynamicList<Pasajeros>();

        int left = 0;
        int right = lista.getLength() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (p[mid].getDNI().equalsIgnoreCase(dni)) {
                result.add(p[mid]);

                int temp = mid - 1;
                while (temp >= left && p[temp].getDNI().equalsIgnoreCase(dni)) {
                    result.add(p[temp]);
                    temp--;
                }

                temp = mid + 1;
                while (temp <= right && p[temp].getDNI().equalsIgnoreCase(dni)) {
                    result.add(p[temp]);
                    temp++;
                }
                return result;
            }
            if (p[mid].getDNI().compareToIgnoreCase(dni) < 0) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return result;
    }

    public DynamicList<Pasajeros> buscarTotalLB(DynamicList<Pasajeros> lista, Double total) throws Exception {
        lista = this.metodoShellSortOrden(lista, 0, "total");

        int n = lista.getLength();
        int segmento = (int) Math.sqrt(n);
        Pasajeros[] pasajeros = lista.toArray();
        DynamicList<Pasajeros> result = new DynamicList<>();

        int i;
        for (i = 0; i < n; i += segmento) {
            if (pasajeros[Math.min(i + segmento, n) - 1].getDatosboleto().getPrecioTotal().doubleValue() >= total.doubleValue()) {
                break;
            }
        }

        if (i >= n) {
            return result;
        }

        int lo = i;
        int hi = Math.min(i + segmento, n);
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (pasajeros[mid].getDatosboleto().getPrecioTotal().doubleValue() == total.doubleValue()) {
                result.add(pasajeros[mid]);

                int aux = mid - 1;
                while (aux >= lo && pasajeros[aux].getDatosboleto().getPrecioTotal().doubleValue() == total.doubleValue()) {
                    result.add(pasajeros[aux]);
                    aux--;
                }

                aux = mid + 1;
                while (aux < hi && pasajeros[aux].getDatosboleto().getPrecioTotal().doubleValue() == total.doubleValue()) {
                    result.add(pasajeros[aux]);
                    aux++;
                }
                return result;
            }
            else if (pasajeros[mid].getDatosboleto().getPrecioTotal().doubleValue() < total.doubleValue()) {
                lo = mid + 1;
            }
            else {
                hi = mid;
            }
        }
        return result;
    }
}
