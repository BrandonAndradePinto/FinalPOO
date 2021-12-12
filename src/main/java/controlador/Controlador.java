package controlador;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Admin;
import modelo.Alumno;
import modelo.Direccion;
import modelo.Login;
import modelo.ManejoDeArchivos;
import vista.Vista;

/**
 *
 * @author brandon
 */
public class Controlador {
    
    public ManejoDeArchivos file = new ManejoDeArchivos();

    public Controlador() {
    }
    
    //[----------Metodos para Validar Datos----------]
    
    public int validarFileInicioDePrograma(){                                                           //La funcion regresa 0 si se encuentran los archivos necesarios para ejecutar el programa
        ManejoDeArchivos file = new ManejoDeArchivos();                                                 //1 si faltan los dos archivos necesarios para la ejcucion
        boolean existeFileAdmin = file.verificarExistenciaFileAdmin();                                  //2 si flata solo el archivo de administrador
        boolean existeFileAlum = file.verificarExistenciaFileAlumno();                                  //3 si falta el archivo de alumnos
        if(existeFileAdmin && existeFileAlum){                                                          //-1 si ocurrio un error
            return 0;
        }else if(!existeFileAdmin && !existeFileAlum){
            return 1;
        }else if(!existeFileAdmin && existeFileAlum){
            return 2;
        }else if(existeFileAdmin && !existeFileAlum){
            return 3;
        }
        return -1;
    }
    
    public boolean validarIngresoAdmin(String[] datosDeIngreso){
        Login login = new Login();
        return login.validarAdmin(datosDeIngreso[0], datosDeIngreso[1]);
    }
    
    public boolean validarIngresoAlumno(String[] datosDeIngreso){
        Login login = new Login();
        return login.validarAlumno(datosDeIngreso[0], datosDeIngreso[1]);
    }
    
    //[----------Metodos para acciones del Administrador----------] Algunos tambien sirven para Alumno
    public void registrarAlumno(){
        String datosStr = "";
        int datosInt;
        double datosDoubles;
        boolean datosbool;
        Alumno alu = new Alumno();
        KeyboardInput input = new KeyboardInput();
        Direccion dir = new Direccion();
        System.out.println("Por favor, ingrese los siguientes datos");
        
        do{
            System.out.print("\n\tPrimer Nombre: ");
            datosStr = input.readString();
            if(datosStr.equals("")){
                System.out.println("\tERROR: NO se ingreso ningun Nombre");
            }else{
                alu.setPrimerNombre(datosStr);
            }
        }while(datosStr.equals(""));
        
        datosStr = "";
        System.out.print("\n\tSegundo Nombre: ");
        datosStr = input.readString();
        alu.setSegundoNombre(datosStr);
        
        do{
            System.out.print("\n\tApellido Paterno: ");
            datosStr = input.readString();
            if(datosStr.equals("")){
                System.out.println("\tERROR: NO se ingreso ningun Apellido");
            }else{
                alu.setApellidoPaterno(datosStr);
            }
        }while(datosStr.equals(""));
        
        do{
            System.out.print("\n\tApellido Materno: ");
            datosStr = input.readString();
            if(datosStr.equals("")){
                System.out.println("\tERROR: NO se ingreso ningun Apellido");
            }else{
                alu.setApellidoMaterno(datosStr);
            }
        }while(datosStr.equals(""));
        
        do{
            System.out.println("\n\tNota: F para Femenino y M para Masculino");
            System.out.print("\tSexo: ");
            datosStr = input.readString();
            datosStr = datosStr.toUpperCase();
            if(!datosStr.equals("F") && !datosStr.equals("M")){
                System.out.println("\tERROR: Ingreso de datos No valido");
            }else{
                alu.setSexo(datosStr);
            }
        }while(!datosStr.equals("F") && !datosStr.equals("M"));
        
        do{
            System.out.println("\n\tFecha de nacimiento (dd-mm-aaaa)");
            do{
                datosStr = "";
                System.out.print("\n\tDia: ");
                datosInt = input.readInteger();
                if(datosInt > 31 || datosInt < 1){
                    System.out.println("\tERROR: Valor invalido");
                }else{
                    if(datosInt < 10){
                        datosStr = datosStr + "0" + datosInt +"-";
                    }else{
                        datosStr = datosStr + datosInt + "-";
                    }
                }
            }while(datosInt > 31 || datosInt < 1);
            do{
                System.out.print("\n\tmes: ");
                datosInt = input.readInteger();
                if(datosInt > 12 || datosInt < 1){
                    System.out.println("\tERROR: Valor invalido");
                }else{
                    if(datosInt < 10){
                        datosStr = datosStr + "0" + datosInt +"-";
                    }else{
                        datosStr = datosStr + datosInt + "-";
                    }
                }
            }while(datosInt > 12 || datosInt < 1);
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int anioActual = calendar.get(Calendar.YEAR);
            do{
                System.out.print("\n\tAnio: ");
                datosInt = input.readInteger();
                if(datosInt >= anioActual || datosInt < 1950){
                    System.out.println("\tERROR: El año ingresado es mayor o igual al actual o menor a 1950");
                }else{
                    datosStr = datosStr + datosInt;
                }
            }while(datosInt >= anioActual || datosInt < 1950);
            alu.setFechaNac(datosStr);
            alu.calcularEdad();
            if(alu.getEdad() < 18){
                System.out.println("ERROR: Edad invalida, menor a 18 años");
            }
        }while(alu.getEdad() < 18);
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        datosStr = dtf.format(LocalDateTime.now());
        alu.setFechaDeRegistro(datosStr);
        
        System.out.println("\n\tDireccion");
        System.out.print("\t\tPais: ");
        dir.setPais(input.readString());
        
        System.out.print("\t\tEstado: ");
        dir.setEstado(input.readString());
        
        System.out.print("\t\tMunicipio: ");
        dir.setMunicipio(input.readString());
        
        System.out.print("\t\tCiudad: ");
        dir.setCiudad(input.readString());
        
        System.out.print("\t\tCalle: ");
        dir.setCalle(input.readString());
        
        System.out.print("\t\tColonia: ");
        dir.setColonia(input.readString());
        
        System.out.print("\t\tNumero Exterior: ");
        dir.setNumeroExt(input.readString());
        
        System.out.print("\t\tNumero Interior: ");
        dir.setNumeroInt(input.readString());
        
        System.out.print("\t\tCodigo Postal: ");
        dir.setCodigoPostal(input.readString());
        
        alu.setDireccion(dir);
        //USUARIO
        //ALUMNO
        
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        datosStr = "";
        do{
            System.out.print("\n\tSemestre de ingreso (anio): ");
            datosInt = input.readInteger();
            if(datosInt > calendar.get(Calendar.YEAR)){
                System.out.println("\tERROR: Año mayor al actual");
            }else{
                datosStr = datosStr + datosInt + "-";
            }
        }while(datosInt > calendar.get(Calendar.YEAR));
        do{
            System.out.print("\n\tSemestre de ingreso (periodo 1-2): ");
            datosInt = input.readInteger();
            if(datosInt > 2 || datosInt < 1){
                System.out.println("\tERROR: En un año solo hay dos semestres 1-2");
            }else{
                datosStr = datosStr + datosInt;
            }
        }while(datosInt > 2 || datosInt < 1);
        alu.setSemestreDeIngreso(datosStr);
        
        do{
            System.out.println("\n\tNOTA: 0 para los de primer ingreso");
            System.out.print("\tSemestres activo; ");
            datosInt = input.readInteger();
            if(datosInt < 0){
                System.out.println("ERROR: No hay semestres negativos");
            }else{
                alu.setSemestresActivo(datosInt);
            }
        }while(datosInt < 0);
        if(alu.getSemestresActivo() == 0){
            alu.setCreditosDelAlumno(0);
        }else{
            do{
                System.out.print("\n\tCreditos del Alumno: ");
                datosInt = input.readInteger();
                if(datosInt < 0){
                    System.out.println("\tERROR: Los creditos no pueden ser negativvos");
                }
            }while(datosInt < 0);
            alu.setCreditosDelAlumno(datosInt);
        }
        
        if(alu.getSemestresActivo() == 0){
            alu.setAsignaturasInscritasEnOrdinario(5);
        }else{
            do{
                System.out.print("\n\tAsignaturas Inscritas en Ordinario: ");
                datosInt = input.readInteger();
                if(datosInt < 0){
                    System.out.println("\tERROR: No puede haber menos de 0 asignaturas Inscritas");
                }
            }while(datosInt < 0);
            alu.setAsignaturasInscritasEnOrdinario(datosInt);
        }
        
        if(alu.getSemestresActivo() == 0){
            alu.setAsignaturasAprovadasEnOrdinario(0);
        }else{
            do{
                System.out.print("\n\tAsignaturas Aprobadas en Ordinario: ");
                datosInt = input.readInteger();
                if(datosInt < 0 || datosInt > alu.getAsignaturasInscritasEnOrdinario()){
                    System.out.println("\tERROR: NO puede haber menos de 0 asignaturas Aprodas ni mayor a las inscritas");
                }
            }while(datosInt < 0 || datosInt > alu.getAsignaturasInscritasEnOrdinario());
            alu.setAsignaturasAprovadasEnOrdinario(datosInt);
                    
        }
        
        if(alu.getSemestresActivo() == 0){
            alu.setRegular(true);
        }
        
        if(alu.getSemestresActivo() == 0){
            alu.setPromedio(0);
        }else{
            double prom;
            do{
                System.out.print("\n\tPromedio: ");
                prom = input.readDouble();
                if(prom > 10){
                    System.out.println("\tERROR: Promedio Mayor a 10");
                }else if(prom < 5){
                    prom = 5;
                }
            }while(prom > 10);
            alu.setPromedio(prom);
        }
        
        alu.generarNumDeCuenta();
        alu.generarIndicadorEscolar();
        alu.setPassword(alu.getNumeroDeCuenta());
        ManejoDeArchivos file = new ManejoDeArchivos();
        try {
            file.añadirReg(alu.generarLineaCSV(), true);
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void registrarAdmin(){
        String datosStr = "";
        int datosInt;
        double datosDoubles;
        boolean datosbool;
        Admin alu = new Admin();
        KeyboardInput input = new KeyboardInput();
        Direccion dir = new Direccion();
        System.out.println("Por favor, ingrese los siguientes datos");
        
        do{
            System.out.print("\n\tRFC: ");
            datosStr = input.readString();
            if(datosStr.equals("")){
                System.out.println("\tERROR: NO se ingreso ningun dato");
            }else{
                alu.setRfc(datosStr);
            }
        }while(datosStr.equals(""));
        
        do{
            System.out.print("\n\tPrimer Nombre: ");
            datosStr = input.readString();
            if(datosStr.equals("")){
                System.out.println("\tERROR: NO se ingreso ningun Nombre");
            }else{
                alu.setPrimerNombre(datosStr);
            }
        }while(datosStr.equals(""));
        
        datosStr = "";
        System.out.print("\n\tSegundo Nombre: ");
        datosStr = input.readString();
        alu.setSegundoNombre(datosStr);
        
        do{
            System.out.print("\n\tApellido Paterno: ");
            datosStr = input.readString();
            if(datosStr.equals("")){
                System.out.println("\tERROR: NO se ingreso ningun Apellido");
            }else{
                alu.setApellidoPaterno(datosStr);
            }
        }while(datosStr.equals(""));
        
        do{
            System.out.print("\n\tApellido Materno: ");
            datosStr = input.readString();
            if(datosStr.equals("")){
                System.out.println("\tERROR: NO se ingreso ningun Apellido");
            }else{
                alu.setApellidoMaterno(datosStr);
            }
        }while(datosStr.equals(""));
        
        do{
            System.out.println("\n\tNota: F para Femenino y M para Masculino");
            System.out.print("\tSexo: ");
            datosStr = input.readString();
            datosStr = datosStr.toUpperCase();
            if(!datosStr.equals("F") && !datosStr.equals("M")){
                System.out.println("\tERROR: Ingreso de datos No valido");
            }else{
                alu.setSexo(datosStr);
            }
        }while(!datosStr.equals("F") && !datosStr.equals("M"));
        
        do{
            System.out.println("\n\tFecha de nacimiento (dd-mm-aaaa)");
            do{
                datosStr = "";
                System.out.print("\n\tDia: ");
                datosInt = input.readInteger();
                if(datosInt > 31 || datosInt < 1){
                    System.out.println("\tERROR: Valor invalido");
                }else{
                    if(datosInt < 10){
                        datosStr = datosStr + "0" + datosInt +"-";
                    }else{
                        datosStr = datosStr + datosInt + "-";
                    }
                }
            }while(datosInt > 31 || datosInt < 1);
            do{
                System.out.print("\n\tmes: ");
                datosInt = input.readInteger();
                if(datosInt > 12 || datosInt < 1){
                    System.out.println("\tERROR: Valor invalido");
                }else{
                    if(datosInt < 10){
                        datosStr = datosStr + "0" + datosInt +"-";
                    }else{
                        datosStr = datosStr + datosInt + "-";
                    }
                }
            }while(datosInt > 12 || datosInt < 1);
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int anioActual = calendar.get(Calendar.YEAR);
            do{
                System.out.print("\n\tAnio: ");
                datosInt = input.readInteger();
                if(datosInt >= anioActual || datosInt < 1950){
                    System.out.println("\tERROR: El año ingresado es mayor o igual al actual o menor a 1950");
                }else{
                    datosStr = datosStr + datosInt;
                }
            }while(datosInt >= anioActual || datosInt < 1950);
            alu.setFechaNac(datosStr);
            alu.calcularEdad();
            if(alu.getEdad() < 18){
                System.out.println("ERROR: Edad invalida, menor a 18 años");
            }
        }while(alu.getEdad() < 18);
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        datosStr = dtf.format(LocalDateTime.now());
        alu.setFechaDeRegistro(datosStr);
        
        System.out.println("\n\tDireccion");
        System.out.print("\t\tPais: ");
        dir.setPais(input.readString());
        
        System.out.print("\t\tEstado: ");
        dir.setEstado(input.readString());
        
        System.out.print("\t\tMunicipio: ");
        dir.setMunicipio(input.readString());
        
        System.out.print("\t\tCiudad: ");
        dir.setCiudad(input.readString());
        
        System.out.print("\t\tCalle: ");
        dir.setCalle(input.readString());
        
        System.out.print("\t\tColonia: ");
        dir.setColonia(input.readString());
        
        System.out.print("\t\tNumero Exterior: ");
        dir.setNumeroExt(input.readString());
        
        System.out.print("\t\tNumero Interior: ");
        dir.setNumeroInt(input.readString());
        
        System.out.print("\t\tCodigo Postal: ");
        dir.setCodigoPostal(input.readString());
        
        alu.setDireccion(dir);
        alu.setPassword(alu.getRfc());
        ManejoDeArchivos file = new ManejoDeArchivos();
        try {
            file.añadirReg(alu.generarLineaCSV(), false);
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean actualizarDatosAlumno(String Key){
        ManejoDeArchivos file = new ManejoDeArchivos();
        String datos = file.buscarRegistro(Key, true);
        if(datos.equals("")){
            System.out.println("\n*ALUMNO NO ENCONTRADO");
            return false;
        }else{
            int op = 0;
            KeyboardInput input = new KeyboardInput();
            Alumno alu = new Alumno(datos);
            Direccion dir = new Direccion();
            do{
                Vista vista = new Vista();
                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                vista.limpiarPantalla();
                System.out.println(vista.title("Actualizar datos de un alumno"));
                vista.menuActualizarDatos(true);
                op = input.readInteger();
                String datosStr = "";
                String datosStr2 = "";
                double datosDou = 0;
                int datosInt = 0;
                switch (op){
                    case 1:
                        System.out.println(vista.title("Actualizar/Modificar Nombre"));
                        System.out.println("Por favor, ingrese ingrese la siguiente informacion: \n");
                        datosStr = "";
                        do{
                            System.out.print("\n\tPrimer Nombre: ");
                            datosStr = input.readString();
                            if(datosStr.equals("")){
                                System.out.println("\tERROR: NO se ingreso ningun Nombre");
                            }else{
                                alu.setPrimerNombre(datosStr);
                            }
                        }while(datosStr.equals(""));

                        datosStr = "";
                        System.out.print("\n\tSegundo Nombre: ");
                        datosStr = input.readString();
                        alu.setSegundoNombre(datosStr);

                        do{
                            System.out.print("\n\tApellido Paterno: ");
                            datosStr = input.readString();
                            if(datosStr.equals("")){
                                System.out.println("\tERROR: NO se ingreso ningun Apellido");
                            }else{
                                alu.setApellidoPaterno(datosStr);
                            }
                        }while(datosStr.equals(""));

                        do{
                            System.out.print("\n\tApellido Materno: ");
                            datosStr = input.readString();
                            if(datosStr.equals("")){
                                System.out.println("\tERROR: NO se ingreso ningun Apellido");
                            }else{
                                alu.setApellidoMaterno(datosStr);
                            }
                        }while(datosStr.equals(""));
                        break;
                        
                    case 2:
                        System.out.println(vista.title("Actualizar/Modificar Fecha de Nacimiento"));
                        System.out.println("Por favor, ingrese ingrese la siguiente informacion: \n");
                        do{
                            System.out.println("\n\tFecha de nacimiento (dd-mm-aaaa)");
                            do{
                                datosStr = "";
                                System.out.print("\n\tDia: ");
                                datosInt = input.readInteger();
                                if(datosInt > 31 || datosInt < 1){
                                    System.out.println("\tERROR: Valor invalido");
                                }else{
                                    if(datosInt < 10){
                                        datosStr = datosStr + "0" + datosInt +"-";
                                    }else{
                                        datosStr = datosStr + datosInt + "-";
                                    }
                                }
                            }while(datosInt > 31 || datosInt < 1);
                            do{
                                System.out.print("\n\tmes: ");
                                datosInt = input.readInteger();
                                if(datosInt > 12 || datosInt < 1){
                                    System.out.println("\tERROR: Valor invalido");
                                }else{
                                    if(datosInt < 10){
                                        datosStr = datosStr + "0" + datosInt +"-";
                                    }else{
                                        datosStr = datosStr + datosInt + "-";
                                    }
                                }
                            }while(datosInt > 12 || datosInt < 1);
                            int anioActual = calendar.get(Calendar.YEAR);
                            do{
                                System.out.print("\n\tAnio: ");
                                datosInt = input.readInteger();
                                if(datosInt >= anioActual || datosInt < 1950){
                                    System.out.println("\tERROR: El año ingresado es mayor o igual al actual o menor a 1950");
                                }else{
                                    datosStr = datosStr + datosInt;
                                }
                            }while(datosInt >= anioActual || datosInt < 1950);
                            alu.setFechaNac(datosStr);
                            alu.calcularEdad();
                            if(alu.getEdad() < 18){
                                System.out.println("ERROR: Edad invalida, menor a 18 años");
                            }
                        }while(alu.getEdad() < 18);
                                                   
                        break;
                        
                    case 3:
                        System.out.println(vista.title("Actualizar/Modificar Sexo"));
                        System.out.println("Por favor, ingrese ingrese la siguiente informacion: \n");
                        do{
                            System.out.println("\n\tNota: F para Femenino y M para Masculino");
                            System.out.print("\tSexo: ");
                            datosStr = input.readString();
                            datosStr = datosStr.toUpperCase();
                            if(!datosStr.equals("F") && !datosStr.equals("M")){
                                System.out.println("\tERROR: Ingreso de datos No valido");
                            }else{
                                alu.setSexo(datosStr);
                            }
                        }while(!datosStr.equals("F") && !datosStr.equals("M"));
                        break;
                        
                    case 4:
                        System.out.println(vista.title("Actualizar/Modificar Direccion"));
                        System.out.println("Por favor, ingrese ingrese la siguiente informacion: \n");
                        System.out.println("\n\tDireccion");
                        System.out.print("\t\tPais: ");
                        dir.setPais(input.readString());

                        System.out.print("\t\tEstado: ");
                        dir.setEstado(input.readString());

                        System.out.print("\t\tMunicipio: ");
                        dir.setMunicipio(input.readString());

                        System.out.print("\t\tCiudad: ");
                        dir.setCiudad(input.readString());

                        System.out.print("\t\tCalle: ");
                        dir.setCalle(input.readString());

                        System.out.print("\t\tColonia: ");
                        dir.setColonia(input.readString());

                        System.out.print("\t\tNumero Exterior: ");
                        dir.setNumeroExt(input.readString());

                        System.out.print("\t\tNumero Interior: ");
                        dir.setNumeroInt(input.readString());

                        System.out.print("\t\tCodigo Postal: ");
                        dir.setCodigoPostal(input.readString());

                        alu.setDireccion(dir);
                        break;
                    
                            
                    case 5:
                        System.out.println(vista.title("Actualizar/Modificar Contraseña"));
                        System.out.println("Por favor, ingrese ingrese la siguiente informacion: \n");
                        System.out.println("Contraseña Actual: " + alu.getPassword());
                        System.out.print("Nueva contraseña: ");
                        datosStr = input.readString();
                        System.out.print("Repetir contraseña: ");
                        datosStr2 = input.readString();
                        if(datosStr.equals(datosStr2)){
                            alu.setPassword(datosStr2);
                        }else{
                            System.out.println("ERROR: las contraseñas no coinciden\n");
                            vista.enterContinuar();
                        }
                        break;
                        
                        
                    case 6:
                        System.out.println(vista.title("Actualizar/Modificar Semestre de Ingreso"));
                        System.out.println("Por favor, ingrese ingrese la siguiente informacion: \n");
                        datosStr = "";
                        do{
                            System.out.print("\n\tSemestre de ingreso (anio): ");
                            datosInt = input.readInteger();
                            if(datosInt > calendar.get(Calendar.YEAR)){
                                System.out.println("\tERROR: Año mayor al actual");
                            }else{
                                datosStr = datosStr + datosInt + "-";
                            }
                        }while(datosInt > calendar.get(Calendar.YEAR));
                        do{
                            System.out.print("\n\tSemestre de ingreso (periodo 1-2): ");
                            datosInt = input.readInteger();
                            if(datosInt > 2 || datosInt < 1){
                                System.out.println("\tERROR: En un año solo hay dos semestres 1-2");
                            }else{
                                datosStr = datosStr + datosInt;
                            }
                        }while(datosInt > 2 || datosInt < 1);
                        alu.setSemestreDeIngreso(datosStr);
                        break;
                        
                        
                    case 7:
                        System.out.println(vista.title("Actualizar/Modificar Promedio"));
                        System.out.println("Por favor, ingrese ingrese la siguiente informacion: \n");
                        do{
                            System.out.print("\n\tPromedio: ");
                            datosDou = input.readDouble();
                            if(datosDou > 10){
                                System.out.println("\tERROR: Promedio Mayor a 10");
                            }else if(datosDou < 5){
                                datosDou = 5;
                            }
                        }while(datosDou > 10);
                        alu.setPromedio(datosDou);
                        break;
                        
                        
                    case 8:
                        System.out.println(vista.title("Actualizar/Modificar Creditos del Alumno"));
                        System.out.println("Por favor, ingrese ingrese la siguiente informacion: \n");
                        do{
                            System.out.print("\n\tCreditos del Alumno: ");
                            datosInt = input.readInteger();
                            if(datosInt < 0){
                                System.out.println("\tERROR: Los creditos no pueden ser negativvos");
                            }
                        }while(datosInt < 0);
                        alu.setCreditosDelAlumno(datosInt);
                        break;
                        
                        
                    case 9:
                        System.out.println(vista.title("Actualizar/Modificar Asignaturas Inscritas en ordinario"));
                        System.out.println("Por favor, ingrese ingrese la siguiente informacion: \n");
                        do{
                            System.out.print("\n\tAsignaturas Inscritas en Ordinario: ");
                            datosInt = input.readInteger();
                            if(datosInt < 0){
                                System.out.println("\tERROR: No puede haber menos de 0 asignaturas Inscritas");
                            }
                        }while(datosInt < 0);
                        alu.setAsignaturasInscritasEnOrdinario(datosInt);
                        break;
                    
                        
                    case 10:
                        System.out.println(vista.title("Actualizar/Modificar Asignaturas Aprobadas en ordinario"));
                        System.out.println("Por favor, ingrese ingrese la siguiente informacion: \n");
                        do{
                            System.out.print("\n\tAsignaturas Aprobadas en Ordinario: ");
                            datosInt = input.readInteger();
                            if(datosInt < 0 || datosInt > alu.getAsignaturasInscritasEnOrdinario()){
                                System.out.println("\tERROR: NO puede haber menos de 0 asignaturas Aprodas ni mayor a las inscritas");
                            }
                        }while(datosInt < 0 || datosInt > alu.getAsignaturasInscritasEnOrdinario());
                        alu.setAsignaturasAprovadasEnOrdinario(datosInt);
                        break;
                        
                    case 11:
                        vista.limpiarPantalla();
                        System.out.println(vista.title("Actualizar/Modificar Semestres que el alumno estuvo activo"));
                        System.out.println("Por favor, ingrese ingrese la siguiente informacion: \n");
                        do{
                            System.out.println("\n\tNOTA: 0 para los de primer ingreso");
                            System.out.print("\tSemestres activo; ");
                            datosInt = input.readInteger();
                            if(datosInt < 0){
                                System.out.println("ERROR: No hay semestres negativos");
                            }else{
                                alu.setSemestresActivo(datosInt);
                            }
                        }while(datosInt < 0);
                        break;
                        
                    case 12:
                        break;
                        
                    default:
                        vista.limpiarPantalla();
                        System.out.println(vista.title("Opcion No valida"));
                        break;
                }
            }while(op != 12);
            file.actualizarReg(alu.generarLineaCSV(), Key, true);
        }
        return true;
    }
    
    public boolean actualizarMisDatosAdmin(String Key){
        ManejoDeArchivos file = new ManejoDeArchivos();
        String datos = file.buscarRegistro(Key, false);
        if(datos.equals("")){
            System.out.println("\n*Datos no encontrado");
            return false;
        }else{
            int op = 0;
            KeyboardInput input = new KeyboardInput();
            Admin admin = new Admin(datos);
            Direccion dir = new Direccion();
            do{
                Vista vista = new Vista();
                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                
                vista.limpiarPantalla();
                vista.menuActualizarDatos(false);
                op = input.readInteger();
                
                String datosStr = "";
                String datosStr2 = "";
                double datosDou = 0;
                int datosInt = 0;
                
                switch (op){
                    case 1:
                        System.out.println(vista.title("Actualizar/Modificar Nombre"));
                        System.out.println("Por favor, ingrese ingrese la siguiente informacion: \n");
                        datosStr = "";
                        do{
                            System.out.print("\n\tPrimer Nombre: ");
                            datosStr = input.readString();
                            if(datosStr.equals("")){
                                System.out.println("\tERROR: NO se ingreso ningun Nombre");
                            }else{
                                admin.setPrimerNombre(datosStr);
                            }
                        }while(datosStr.equals(""));

                        datosStr = "";
                        System.out.print("\n\tSegundo Nombre: ");
                        datosStr = input.readString();
                        admin.setSegundoNombre(datosStr);

                        do{
                            System.out.print("\n\tApellido Paterno: ");
                            datosStr = input.readString();
                            if(datosStr.equals("")){
                                System.out.println("\tERROR: NO se ingreso ningun Apellido");
                            }else{
                                admin.setApellidoPaterno(datosStr);
                            }
                        }while(datosStr.equals(""));

                        do{
                            System.out.print("\n\tApellido Materno: ");
                            datosStr = input.readString();
                            if(datosStr.equals("")){
                                System.out.println("\tERROR: NO se ingreso ningun Apellido");
                            }else{
                                admin.setApellidoMaterno(datosStr);
                            }
                        }while(datosStr.equals(""));
                        break;
                        
                    case 2:
                        System.out.println(vista.title("Actualizar/Modificar Fecha de Nacimiento"));
                        System.out.println("Por favor, ingrese ingrese la siguiente informacion: \n");
                        do{
                            System.out.println("\n\tFecha de nacimiento (dd-mm-aaaa)");
                            do{
                                datosStr = "";
                                System.out.print("\n\tDia: ");
                                datosInt = input.readInteger();
                                if(datosInt > 31 || datosInt < 1){
                                    System.out.println("\tERROR: Valor invalido");
                                }else{
                                    if(datosInt < 10){
                                        datosStr = datosStr + "0" + datosInt +"-";
                                    }else{
                                        datosStr = datosStr + datosInt + "-";
                                    }
                                }
                            }while(datosInt > 31 || datosInt < 1);
                            do{
                                System.out.print("\n\tmes: ");
                                datosInt = input.readInteger();
                                if(datosInt > 12 || datosInt < 1){
                                    System.out.println("\tERROR: Valor invalido");
                                }else{
                                    if(datosInt < 10){
                                        datosStr = datosStr + "0" + datosInt +"-";
                                    }else{
                                        datosStr = datosStr + datosInt + "-";
                                    }
                                }
                            }while(datosInt > 12 || datosInt < 1);
                            int anioActual = calendar.get(Calendar.YEAR);
                            do{
                                System.out.print("\n\tAnio: ");
                                datosInt = input.readInteger();
                                if(datosInt >= anioActual || datosInt < 1950){
                                    System.out.println("\tERROR: El año ingresado es mayor o igual al actual o menor a 1950");
                                }else{
                                    datosStr = datosStr + datosInt;
                                }
                            }while(datosInt >= anioActual || datosInt < 1950);
                            admin.setFechaNac(datosStr);
                            admin.calcularEdad();
                            if(admin.getEdad() < 18){
                                System.out.println("ERROR: Edad invalida, menor a 18 años");
                            }
                        }while(admin.getEdad() < 18);
                                                   
                        break;
                        
                    case 3:
                        System.out.println(vista.title("Actualizar/Modificar Sexo"));
                        System.out.println("Por favor, ingrese ingrese la siguiente informacion: \n");
                        do{
                            System.out.println("\n\tNota: F para Femenino y M para Masculino");
                            System.out.print("\tSexo: ");
                            datosStr = input.readString();
                            datosStr = datosStr.toUpperCase();
                            if(!datosStr.equals("F") && !datosStr.equals("M")){
                                System.out.println("\tERROR: Ingreso de datos No valido");
                            }else{
                                admin.setSexo(datosStr);
                            }
                        }while(!datosStr.equals("F") && !datosStr.equals("M"));
                        break;
                        
                    case 4:
                        System.out.println(vista.title("Actualizar/Modificar Direccion"));
                        System.out.println("Por favor, ingrese ingrese la siguiente informacion: \n");
                        System.out.println("\n\tDireccion");
                        System.out.print("\t\tPais: ");
                        dir.setPais(input.readString());

                        System.out.print("\t\tEstado: ");
                        dir.setEstado(input.readString());

                        System.out.print("\t\tMunicipio: ");
                        dir.setMunicipio(input.readString());

                        System.out.print("\t\tCiudad: ");
                        dir.setCiudad(input.readString());

                        System.out.print("\t\tCalle: ");
                        dir.setCalle(input.readString());

                        System.out.print("\t\tColonia: ");
                        dir.setColonia(input.readString());

                        System.out.print("\t\tNumero Exterior: ");
                        dir.setNumeroExt(input.readString());

                        System.out.print("\t\tNumero Interior: ");
                        dir.setNumeroInt(input.readString());

                        System.out.print("\t\tCodigo Postal: ");
                        dir.setCodigoPostal(input.readString());

                        admin.setDireccion(dir);
                        break;
                    
                            
                    case 5:
                        System.out.println(vista.title("Actualizar/Modificar Contraseña"));
                        System.out.println("Por favor, ingrese ingrese la siguiente informacion: \n");
                        System.out.println("Contraseña Actual: " + admin.getPassword());
                        System.out.print("Nueva contraseña: ");
                        datosStr = input.readString();
                        System.out.print("Repetir contraseña: ");
                        datosStr2 = input.readString();
                        if(datosStr.equals(datosStr2)){
                            admin.setPassword(datosStr2);
                        }else{
                            System.out.println("ERROR: las contraseñas no coinciden\n");
                            vista.enterContinuar();
                        }
                        break;
   
                    case 6:
                        System.out.println(vista.title("Modificar RFC"));
                        System.out.println("Por favor, ingrese ingrese la siguiente informacion: \n");
                        do{
                            System.out.print("\n\tRFC: ");
                            datosStr = input.readString();
                            if(datosStr.equals("")){
                                System.out.println("\tERROR: NO se ingreso ningun dato");
                            }else{
                                admin.setRfc(datosStr);
                            }
                        }while(datosStr.equals(""));
                        break;
                        
                    case 7:
                        break;
                        
                    default:
                        vista.limpiarPantalla();
                        System.out.println(vista.title("Opcion No valida"));
                        break;
                }
            }while(op != 7);
            file.actualizarReg(admin.generarLineaCSV(), Key, false);
        }
        return true;
    }
    
    public void consultarDatos(boolean usuario, String key){
        if(usuario){
            ManejoDeArchivos archivo = new ManejoDeArchivos();
            String datos = archivo.buscarRegistro(key, true);
            if(datos.equals("")){
                System.out.println("\n*ALUMNO NO ENCONTRADO");
            }else{
                Alumno alu = new Alumno(datos);
                System.out.println(alu.toString());
            }
        }else{
            ManejoDeArchivos archivo = new ManejoDeArchivos();
            String datos = archivo.buscarRegistro(key, false);
            if(datos.equals("")){
                System.out.println("\n*Datos No encontrados");
            }else{
                Admin admin = new Admin(datos);
                System.out.println(admin.toString());
            }
        }
    }
    
    public void eliminarRegistroAdmin(String key){
        ManejoDeArchivos archivo = new ManejoDeArchivos();
        String datos = archivo.buscarRegistro(key, false);
        if(datos.equals("")){
            System.out.println("\n*Datos No encontrados");
        }else{
            archivo.eliminarReg(key);
            System.out.println("Registro Eliminado");
        }
    }
    
    public void generarNumInscripcion(){
        ManejoDeArchivos archivo = new ManejoDeArchivos();
        ArrayList<Alumno> alu = archivo.listAlu();
        if(alu == null || alu.isEmpty()){
        }else{
            Collections.sort(alu);
            int cont = alu.size();
            for(Alumno ele : alu){
                ele.setNumeroDeInscripcion(cont);
                cont--;
                archivo.actualizarReg(ele.generarLineaCSV(), ele.getNumeroDeCuenta(), true);
            }
            
        }
    }
    
    //[----------Metodos para acciones del Alumno----------]
    
    public void consultarNumInscripcion(String key){
    ManejoDeArchivos archivo = new ManejoDeArchivos();
        String datos = archivo.buscarRegistro(key, true);
        if(datos.equals("")){
            System.out.println("\n*ALUMNO NO ENCONTRADO");
        }else{
            Alumno alu = new Alumno(datos);
            System.out.println("Numero de Inscripcion: " + alu.getNumeroDeInscripcion());
        }
    }
    
    //Utilidades para el programa
    
    public int validarOption(int optionMax, int option){
        int op = option;
        if(op < 1 || op > optionMax){
            op = -1;
        }
        return op;
    }
}
