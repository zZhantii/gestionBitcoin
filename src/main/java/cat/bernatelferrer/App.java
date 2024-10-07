package cat.bernatelferrer;

import java.util.Date;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public final class App {

    static Scanner entradaUser = new Scanner(System.in);

    static String nameFile, nombreCartera;

    static String currentUsersHomeDir = System.getProperty("user.home");

    static String[][] archivos;

    static String cartera = "";

    public static void comandos() {
        boolean salida = true;
        do {
            System.out.print(cartera + " > ");
            String comando = entradaUser.nextLine();
            String[] separacionComando = comando.split(" ");
            try {
                if (separacionComando[0].equals("quit")) {
                    salida = false;
                } else if (separacionComando[0].equals("valor")) {
                    String date = separacionComando[1];
                    String camp = separacionComando[2];
                    comandoValor(date, camp);
                } else if (separacionComando[0].equals("avg")) {
                    String data1 = separacionComando[1];
                    String data2 = separacionComando[2];
                    String camp = separacionComando[3];
                    comandoAVG(data1, data2, camp);
                } else if (separacionComando[0].equals("rent")) {
                    String data1 = separacionComando[1];
                    String data2 = separacionComando[2];
                    comandoRent(data1, data2);
                } else if (separacionComando[0].equals("list")) {
                    listCarteras();
                } else if (separacionComando[0].equals("new")) {
                    String nombreCartera = separacionComando[1];
                    comandoNew(nombreCartera);
                } else if (separacionComando[0].equals("select")) {
                    String nombreCartera = separacionComando[1];
                    comandoSelect(nombreCartera);
                } else if (separacionComando[0].equals("buy")) {
                    String data = separacionComando[1];
                    String valor = separacionComando[2];
                    comandobuy(data, valor);
                } else if (separacionComando[0].equals("sell")) {
                    String data = separacionComando[1];
                    String valor = separacionComando[2];
                    comandosell(data, valor);
                }
            } catch (Exception e) {
                System.out.println("Error... " + e.getMessage());
            }
        } while (salida);
    }

    public static void main(String[] args) {
        try {
            // Creo la carpeta princiapl, seguido con las dos subcarpetas
            File carpetaPrincipal = new File(currentUsersHomeDir + File.separator + "BitcoinsManager");
            File carpeta1 = new File(currentUsersHomeDir + File.separator + "BitcoinsManager" + File.separator + "cotitzacions");
            File carpeta2 = new File(currentUsersHomeDir + File.separator + "BitcoinsManager" + File.separator + "carteres");
            // Si la carpeta no existe, crea las tres carpetas, pero si ya existe solo crea
            // las dos carpetas, finalmente inicia el programa
            if (!carpetaPrincipal.exists()) {
                carpetaPrincipal.mkdir();
                carpeta1.mkdir();
                carpeta2.mkdir();
            } else if (carpetaPrincipal.exists()) {
                carpeta1.mkdir();
                carpeta2.mkdir();
                System.out.println("\nBitcoin manager by Nom_i_Cognoms v1.0");
                compCoti();
            }
        } catch (Exception e) {
            System.out.println("Error... " + e.getMessage());
        }
    }

    static public void compCoti() {
        // Con la variable fichero le meto la ruta del fichero
        File fichero = new File(currentUsersHomeDir + File.separator + "BitcoinsManager" + File.separator + "cotitzacions");
        try {
            // Creo la array File en el cual la ruta donde estan los fichero, con la funcion
            // .listFiles() listo todos los que existen
            File[] files = fichero.listFiles();
            // Si no existen ficheros, pedirá que se añada los ficheros en el directorio,
            // sino mostrará todos los ficheros que existan
            if (files == null || files.length == 0) {
                System.out.println("Porfavor, inserte su documento de cotizaciones en la carpeta");
            } else {
                for (int i = 0; i < files.length; i++) {
                    nameFile = files[i].getName();
                    System.out.println((i + 1) + ". " + nameFile);
                }

                System.out.print("Selecione el fichero de cotizaciones a cargar: ");
                int numFichero = Integer.parseInt(entradaUser.nextLine());
                System.out.println("\n");

                File ficheroSelect = files[numFichero - 1];
                // Con la funcion getName() obtengo el nombre del fichero y lo guardo en una
                // variable
                nameFile = ficheroSelect.getName();
                // Con el FileReader leeo todo el archivo que quiero y con el BufferReader nos
                // permite leer caracteres, arrays y lienas
                FileReader fr = new FileReader(ficheroSelect);
                BufferedReader bf = new BufferedReader(fr);
                // La variable Long nos permite trabajar con numeros muy grandes y el .lines()
                // extrae las lineas del archivo y el .count() las cuenta
                long lineas = bf.lines().count();

                bf.close();

                System.out.println("Fitxer " + nameFile + " carregat.");
                System.out.println(lineas + " registres llegits. ");

                Scanner lectura = new Scanner(ficheroSelect);

                archivos = new String[(int) lineas][7];

                int contador = 0;

                while (lectura.hasNextLine()) {
                    String fila = lectura.nextLine();

                    String[] arr = fila.split(",");

                    archivos[contador] = arr;
                    contador++;
                }

                lectura.close();
                comandos();
            }
        } catch (Exception e) {
            System.out.println("Error... " + e.getMessage());
        }
    }

    static public void comandoValor(String date, String camp) {
        try {
            // SimpleDateFormat crea un formato para la fecha que me interesa
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // Date es para guardar fechas y el .parse para buscar la fecha con el formato
            // que me interesa
            Date fechaDate = sdf.parse(date);
            // Con el equal, comparo dos String y el sdf.formato cojo el formato de la fecha
            // y la comparo
            if (date.equals(sdf.format(fechaDate))) {
                for (int i = 0; i < archivos.length; i++) {
                    for (int j = 0; j < archivos[i].length; j++) {
                        if ((archivos[i][j]).equalsIgnoreCase(date)) {
                            switch (camp) {
                                case "open":
                                    System.out.println(archivos[i][1]);
                                    break;
                                case "high":
                                    System.out.println(archivos[i][2]);
                                    break;
                                case "low":
                                    System.out.println(archivos[i][3]);
                                    break;
                                case "close":
                                    System.out.println(archivos[i][4]);
                                    break;
                                case "volume":
                                    System.out.println(archivos[i][6]);
                                    break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error... " + e.getMessage());
        }
    }

    static public void comandoAVG(String data1, String data2, String camp) {
        try {
            int campInt = 0;

            if (camp.equals("open")) {
                campInt = 1;
            } else if (camp.equals("high")) {
                campInt = 2;
            } else if (camp.equals("low")) {
                campInt = 3;
            } else if (camp.equals("close")) {
                campInt = 4;
            } else if (camp.equals("volume")) {
                campInt = 6;
            }

            LocalDate fecha1 = LocalDate.parse(data1);
            LocalDate fecha2 = LocalDate.parse(data2);

            long suma = 0;
            int count = 0;

            for (int i = 1; i < archivos.length; i++) {
                // formateo la fecha desde el primer elemento
                LocalDate fecha = LocalDate.parse(archivos[i][0]);
                // Comprueba si la fecha actual es posterios o igual a la fecha1 o anterior o
                // igual a fecha2
                if ((fecha.isAfter(fecha1) || fecha.equals(fecha1))
                        || (fecha.isBefore(fecha2) || fecha.equals(fecha2))) {
                    suma += Double.parseDouble(archivos[i][campInt]);
                    count++;
                }
            }

            double media = (double) suma / count;

            System.out.println(media);

        } catch (Exception e) {
            System.out.println("Error... " + e.getMessage());
        }
    }

    static public void comandoRent(String data1, String data2) {
        try {
            LocalDate fecha1 = LocalDate.parse(data1);
            LocalDate fecha2 = LocalDate.parse(data2);

            double valor_compra = 0;
            double valor_venta = 0;

            for (int i = 1; i < archivos.length; i++) {
                LocalDate fecha = LocalDate.parse(archivos[i][0]);
                if (fecha.equals(fecha1)) {
                    valor_compra = Double.parseDouble(archivos[i][1]);
                }
                if (fecha.equals(fecha2)) {
                    valor_venta = Double.parseDouble(archivos[i][1]);
                }
            }

            double rent = (valor_venta - valor_compra) / valor_compra * 100;
            double roundRent = Math.round(rent * 100.0) / 100.0;
            System.out.println(roundRent + "%");

        } catch (Exception e) {
            System.out.println("Error... " + e.getMessage());
        }
    }

    static public void comandoMonkey() {

    }

    static void listCarteras() {

        File fichero = new File(currentUsersHomeDir + File.separator + "BitcoinsManager" + File.separator + "carteres");

        File[] carterasList = fichero.listFiles();

        if (carterasList != null) {
            for (int i = 0; i < carterasList.length; i++) {
                nameFile = carterasList[i].getName();
                System.out.println((i + 1) + ". " + nameFile);
            }
        }
    }

    static void comandoNew(String nombreCartera) {
        try {
            File fichero = new File(currentUsersHomeDir + File.separator + "BitcoinsManager" + File.separator
                    + "carteres" + File.separator + nombreCartera + ".txt");
            boolean creado = fichero.createNewFile();

            if (creado) {
                System.out.println(nombreCartera + " created and selected.");
                cartera = nombreCartera;
            } else {
                System.out.println(
                        "Error: " + nombreCartera + "ja existeix.\nFes servir la comanda select per seleccionar-la.");
            }
        } catch (Exception e) {
            System.out.println("Error... " + e.getMessage());
        }
    }

    static void comandoSelect(String nombreCartera) {
        try {
            File fichero = new File(currentUsersHomeDir + File.separator + "BitcoinsManager" + File.separator
                    + "carteres" + File.separator + nombreCartera + ".txt");

            if (fichero.exists()) {
                System.out.println(cartera + " seleccionada.");
                cartera = nombreCartera;
            } else {
                System.out.println(
                        "ERROR: aquesta cartera no existeix." + "\nFes servir new per crear una nova cartera.");
            }
        } catch (Exception e) {
            System.out.println("Error... " + e.getMessage());
        }

    }

    static public String getCarteraPath(String nombreCartera) {
        File fichero = new File(currentUsersHomeDir + File.separator + "BitcoinsManager" + File.separator + "carteres"
                + File.separator + nombreCartera + ".txt");
        // Devuelve la ruta absoluta del archivo como una cadena
        return fichero.getAbsolutePath();
    }

    static public boolean carteraExists(String nombreCartera) {
        // Crea un fichero con un nuevo objeto File con la ruta de la cartera
        // selecionada
        File fichero = new File(getCarteraPath(nombreCartera));
        // Verifica si el fichero existe y devuelve el resultado
        return fichero.exists();
    }

    static public void comandobuy(String data, String valor) {
        LocalDate fecha = LocalDate.parse(data);
        int valorFinal = Integer.parseInt(valor);
        double valorBTC = valorFinal * 57300;

        // Guardo la ruta en un String, la ruta de la cartera
        String carteraPath = getCarteraPath(cartera);
        if (!carteraExists(cartera)) {
            System.out.println("ERROR: la cartera no existe.\n" + "Fes servir list per mostrar un llistat de carteres,\n" + "new per crear una nova cartera o select per seleccionar\n" + "una cartera.");
            return;
        } else {
            System.out.println("Ordre de compra enregistrada " + "( " + valorBTC + " BTC )");
        }

        try {
            FileWriter escritura = new FileWriter(carteraPath, true);

            escritura.write("Compra: " + fecha + " " + valorFinal + " ( " + valorBTC + " )" + "\n");

            escritura.close();

        } catch (Exception e) {
            System.out.println("Error... " + e.getMessage());
        }
    }

    static public void comandosell(String data, String valor) {
        LocalDate fecha = LocalDate.parse(data);
        int valorFinal = Integer.parseInt(valor);
        double valorUSD = valorFinal * 0.92;

        String carteraPath = getCarteraPath(cartera);
        if (!carteraExists(cartera)) {
            System.out.println("ERROR: la cartera no existe.\n" + "Fes servir list per mostrar un llistat de carteres,\n" + "new per crear una nova cartera o select per seleccionar\n" + "una cartera.");
            return;
        } else {
            System.out.println("Ordre de compra enregistrada " + "( " + valorUSD + " )");
        }

        try {
            FileWriter escritura = new FileWriter(carteraPath, true);

            escritura.write("Venta: " + fecha + " " + valorFinal + " ( " + valorUSD + " USD )" + "\n");

            escritura.close();

        } catch (Exception e) {
            System.out.println("Error... " + e.getMessage());
        }
    }
}
