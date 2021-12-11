
package pruebas;

import vista.Vista;
import controlador.Controlador;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Alumno;
import modelo.Direccion;
import modelo.ManejoDeArchivos;

/**
 *
 * @author brandon
 */
public class Pruebas {
    private final String fileNom = "nombres.csv";
    private final String fileDir = "directorio.csv";
    private final int linDir = 1400;
    private final int linNom = 110;
    
    public Pruebas() {
    }
    
    public void generarDatosDePrueba(int cantidadDeDatos){
        Direccion dir = new Direccion();
        Alumno alu = new Alumno();
        Vista vista = new Vista();
        
        for(int i = 0; i < cantidadDeDatos; i++){
            
            //Datos de Usuario
            alu.setFechaNac(generarFechaDeNacimiento());
            alu.calcularEdad();
            alu.setSexo(generarSexo());
            alu.setPrimerNombre(generarNombre(alu.getSexo()));
            alu.setSegundoNombre(generarNombre(alu.getSexo()));
            alu.setApellidoMaterno(generarApellido());
            alu.setApellidoPaterno(generarApellido());
            alu.setDireccion(generarDir());
            alu.setFechaDeRegistro(generarFechaDeReg());
            
            // Datos del ALumno
            alu.setSemestreDeIngreso(semestreDeIngreso(alu.getEdad()));
            alu.generarNumDeCuenta();
            alu.setPassword(alu.getNumeroDeCuenta());
            alu.setSemestresActivo(semestresActivo(alu.getEdad()));
            alu.setPromedio(generarpromedio());
            int asig = (int)(Math.random()*5+1);
            alu.setAsignaturasInscritasEnOrdinario(asig);
            alu.setAsignaturasAprovadasEnOrdinario((int)(Math.random()*asig + 1));
            alu.setCreditosDelAlumno(generarCreditos(alu.getSemestresActivo()));
            alu.generarIndicadorEscolar();
            
            String datos = alu.generarLineaCSV();
            ManejoDeArchivos file = new ManejoDeArchivos();
            try {
                file.añadirReg(alu.generarLineaCSV(), true);
            } catch (IOException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    private void generarDireccion(Direccion dir){
        int numero = (int)(Math.random()*1);
    }
    
    private String generarApellido(){
        int numero = (int)(Math.random()*linNom);
        String apellido = "";
        try {
            BufferedReader br;
            FileReader fr = new FileReader(fileNom);
            br = new BufferedReader(fr);
            
            int cont = 0;
            String linea = br.readLine();
            
            while(cont <= numero){
                StringTokenizer tokenizer = new StringTokenizer(linea,";");
                if(cont == numero){
                        apellido = tokenizer.nextToken();
                        apellido = tokenizer.nextToken();
                        apellido = tokenizer.nextToken();
                }
                cont++;
                linea = br.readLine();
            }
            br.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return apellido;
    }
    
    private  String generarNombre(String genero){                       //true para F y false para M
        boolean sexo = (genero.equals("F"));
        int numero = (int)(Math.random()*linNom);
        String nombre = "";
        try {
            BufferedReader br;
            FileReader fr = new FileReader(fileNom);
            br = new BufferedReader(fr);
            
            int cont = 0;
            String linea = br.readLine();
            
            while(cont <= numero){
                StringTokenizer tokenizer = new StringTokenizer(linea,";");
                if(cont == numero){
                    if(sexo){
                        nombre = tokenizer.nextToken();
                        nombre = tokenizer.nextToken();
                    }else{
                        nombre = tokenizer.nextToken();
                    }
                }
                cont++;
                linea = br.readLine();
            }
            br.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return nombre;
    }
    
    private static String generarSexo(){
        int numero = (int)(Math.random()*2+1);
        String sexo = "";
        if(numero == 1){
            sexo = "F";
        }else{
            sexo = "M";
        }
        return sexo;
    }
    
    private static String generarFechaDeNacimiento(){
        LocalDate startDate = LocalDate.of(1993, 1, 1); //start date
        long start = startDate.toEpochDay();

        LocalDate endDate = LocalDate.of(2003, 7, 1); //end date
        long end = endDate.toEpochDay();

        long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
        LocalDate date = LocalDate.ofEpochDay(randomEpochDay);
        
        String str = "";
        if(date.getDayOfMonth() < 10){
            str = "0" + date.getDayOfMonth() + "-";
        }else{
            str = date.getDayOfMonth() + "-";
        }if(date.getMonthValue() < 10){
            str = str + "0" + date.getMonthValue() + "-";
        }else{
            str = str + date.getMonthValue() + "-";
        }
        str = str + date.getYear();
        
        return str;
    }
    
    private String generarFechaDeReg(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String datosStr = dtf.format(LocalDateTime.now());
        return datosStr;
    }
    
    private Direccion generarDir(){
        Direccion dir = new Direccion();
        int numero = (int)(Math.random()*linDir + 2);
        System.out.println("num = " + numero);
        dir.setPais("Mexico");
        try {
            BufferedReader br;
            FileReader fr = new FileReader(fileDir);
            br = new BufferedReader(fr);
            
            int cont = 0;
            int cont1 = 0;
            String linea = br.readLine();
            
            while(cont <= numero){
                StringTokenizer tokenizer = new StringTokenizer(linea,",");
                if(cont == numero){
                    while(cont1 <= 6){
                        switch (cont1){
                            case 0:
                                dir.setEstado(tokenizer.nextToken());
                                break;
                            case 1:
                                dir.setMunicipio(tokenizer.nextToken());
                                break;
                            case 2:
                                dir.setCiudad(tokenizer.nextToken());
                                break;
                            case 3:
                                dir.setCalle(tokenizer.nextToken());
                                break;
                            case 4:
                                dir.setColonia(tokenizer.nextToken());
                                break;
                            case 5:
                                dir.setNumeroExt(tokenizer.nextToken());
                                dir.setNumeroInt("S/N");
                                break;
                            case 6:
                                dir.setCodigoPostal(tokenizer.nextToken());
                                break;
                            default:
                                tokenizer.nextToken();
                                break;
                        }
                        cont1++;
                    }
                }
                cont++;
                linea = br.readLine();
            }
            br.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return dir;
    }
    
    private String semestreDeIngreso(int edad){
        String semestre;
        int aniosCursados = edad - 18;
        int semestresCursador = 0;
        Date date = new Date();
        ZoneId timeZone = ZoneId.systemDefault();
        LocalDate getLocalDate = date.toInstant().atZone(timeZone).toLocalDate();
        semestresCursador = (getLocalDate.getYear() - aniosCursados);
        
        semestre = semestresCursador + "-" + (int)(Math.random()*2+1);
        return semestre;
    }
    
    private int semestresActivo(int edad){
        int aniosCursados = edad - 18;
        int semestresCursador = 0;
        Date date = new Date();
        ZoneId timeZone = ZoneId.systemDefault();
        LocalDate getLocalDate = date.toInstant().atZone(timeZone).toLocalDate();
        semestresCursador =(getLocalDate.getYear() - (getLocalDate.getYear() - aniosCursados))*2;
        if(semestresCursador > 10){
            semestresCursador = 10;
        }
        return semestresCursador;
    }
    
    private double generarpromedio(){
        double prom = (double)(Math.random()*10+5);
        if(prom > 10){
            prom = (double)10;
        }
        return prom;
    }
    
    private int generarCreditos(int semestresAct){
        int creditos = 0;
        int[] creditosPorSemestre = {46, 44, 46, 42, 42, 48, 46, 44, 40, 40};
        for(int i = 0; i < semestresAct; i++){
            creditos = creditos + creditosPorSemestre[i];
        }
        return creditos;
    }
}
