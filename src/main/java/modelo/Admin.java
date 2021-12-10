package modelo;

import java.util.StringTokenizer;

/**
 *
 * @author brandon
 */
public class Admin extends Usuario{
    private String rfc;                                                         //Debe tener 13 caracteres exactos
    private String password;

    public Admin() {
    }
    
    public Admin(String datos) {
        StringTokenizer tokenizer = new StringTokenizer(datos,",");
        Direccion dir = new Direccion();
        int cont = 0;
        while(tokenizer.hasMoreTokens()){
            switch (cont){
                case 0:
                    rfc = tokenizer.nextToken();
                    break;
                    
                case 1:
                    password = tokenizer.nextToken();
                    break;
                    
                case 2:
                    super.setPrimerNombre(tokenizer.nextToken());
                    break;
                
                case 3:
                    super.setSegundoNombre(tokenizer.nextToken());
                    break;
                    
                case 4:
                    super.setApellidoPaterno(tokenizer.nextToken());
                    break;
                    
                case 5:
                    super.setApellidoMaterno(tokenizer.nextToken());
                    break;
                    
                case 6:
                    super.setSexo(tokenizer.nextToken());
                    break;
                
                case 7:
                    super.setFechaNac(tokenizer.nextToken());
                    break;  
                    
                case 8:
                    super.setEdad(Integer.parseInt(tokenizer.nextToken()));
                    break;
                    
                case 9:
                    super.setFechaDeRegistro(tokenizer.nextToken());
                    break;
                    
                case 10:
                    dir.setPais(tokenizer.nextToken());
                    break;
                
                case 11:
                    dir.setEstado(tokenizer.nextToken());
                    break;
                    
                case 12:
                    dir.setMunicipio(tokenizer.nextToken());
                    break;
                    
                case 13:
                    dir.setCiudad(tokenizer.nextToken());
                    break;
                    
                case 14:
                    dir.setCalle(tokenizer.nextToken());
                    break;
                
                case 15:
                    dir.setColonia(tokenizer.nextToken());
                    break;  
                    
                case 16:
                    dir.setNumeroExt(tokenizer.nextToken());
                    break;
                    
                case 17:
                    dir.setNumeroInt(tokenizer.nextToken());
                    break;
                    
                case 18:
                    dir.setCodigoPostal(tokenizer.nextToken());
                    break;

                default:
                    break;
            }
            cont++;
        }
        super.setDireccion(dir);
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String generarLineaCSV(){
        Direccion dir = super.getDireccion();
        return rfc+","+password+","+super.getPrimerNombre()+","+
                super.getSegundoNombre()+","+super.getApellidoPaterno()+","+
                super.getApellidoMaterno()+","+super.getSexo()+","+
                super.getFechaNac()+","+super.getEdad()+","+super.getFechaDeRegistro()+","+
                dir.getPais()+","+dir.getEstado()+","+dir.getMunicipio()+","+
                dir.getCiudad()+","+dir.getCalle()+","+dir.getColonia()+","+
                dir.getNumeroExt()+","+dir.getNumeroInt()+","+dir.getCodigoPostal();
    }
    
    
    @Override
    public String toString() {
        Direccion dir = super.getDireccion();
        return "Primer Nombre " + super.getPrimerNombre() + "\n" + 
                "Segundo Nombre: " + super.getSegundoNombre() + "\n" +
                "Apellido Paterno: " + super.getApellidoPaterno() + "\n" + 
                "Apellido Materno: " + super.getApellidoMaterno() + "\n" +
                "Fecha de Nacimiento: " + super.getFechaNac() +"\tEdad: " + super.getEdad() +"\n" +
                "Fecha de Registro: " + super.getFechaDeRegistro() +"\n" +
                "\nDireccion: \n" + 
                "\tPais: " + dir.getPais() +"\n" +
                "\tEstado: " + dir.getEstado() + "\n" +
                "\tMunicipio: " + dir.getMunicipio() +"\n" +
                "\tCiudad: " + dir.getCiudad() +"\n" +
                "\tCalle: " + dir.getCalle() + "\n" +
                "\tColonia: " + dir.getColonia() + "\n" +
                "\tNumero Exterior: " + dir.getNumeroExt() +"\n" +
                "\tNumero Interior: " + dir.getNumeroInt() + "\n" +
                "\tCodigo Postal: " + dir.getCodigoPostal() + "\n" + 
                "\nRFC: " + rfc + "\n";
    }
}
