/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Principal {
    
    public static void main(String[] args) {
        
        
        String directorio = System.getProperty("user.dir");
        String ruta = directorio + "\\src\\codigo\\Lexer.flex";
        String ruta2 = directorio + "\\src\\codigo\\LexerCup.flex";
        String rutaSintax = directorio + "\\src\\codigo\\Sintax.cup";
        String [] rutaSintactica = {"-parser","Sintax",rutaSintax};
           
        try {
            generar(ruta,ruta2,rutaSintactica);
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        Lector n = new Lector();
        try {
            Lector.leer();
        } catch (IOException ex) {
            System.out.print(ex);
        }*/     
        
    }
    public static void generar(String ruta1,String ruta2,String[] rutaSintax) throws IOException, Exception{
        File archivo;
        archivo=new File(ruta1);
        JFlex.Main.generate(archivo);
        archivo = new File(ruta2);
        
        JFlex.Main.generate(archivo);
        
        java_cup.Main.main(rutaSintax);
        
        String directorio = System.getProperty("user.dir");
        String path1 = directorio + "\\sym.java";
        String destino = directorio + "\\src\\codigo\\sym.java";
        
        
        String pathSintax = directorio + "\\Sintax.java";
        String pathSintax2 = directorio + "\\src\\codigo\\Sintax.java";
        
        Path rutaSym = Paths.get(destino);
        if(Files.exists(rutaSym)){
            Files.delete(rutaSym);
        }
        
        Path rutaSintactica = Paths.get(pathSintax2);
        if(Files.exists(rutaSintactica)){
            Files.delete(rutaSintactica);
        }
        
        Files.move(Paths.get(path1), Paths.get(destino));
        Files.move(Paths.get(pathSintax), Paths.get(pathSintax2));

    }
}
