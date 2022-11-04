/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Lector {
    //arreglo de tokens
    static ArrayList<String> tokensName = new ArrayList<String>(); 
    //arreglo de lineas
    static ArrayList<ArrayList<Integer>> lineas = new ArrayList<ArrayList<Integer>>(); 
    //arreglo de cantidad
    static ArrayList<ArrayList<Integer>> cantidad = new ArrayList<ArrayList<Integer>>(); 
    //arreglo de tipo
    static ArrayList<Tokens> tipos = new ArrayList<Tokens>(); 
    
    //arreglo de errores (Token)
    static ArrayList<String> error_tokensName = new ArrayList<String>(); 
    //arreglo de lineas
    static ArrayList<ArrayList<Integer>> error_lineas = new ArrayList<ArrayList<Integer>>(); 
    //arreglo de cantidad
    static ArrayList<ArrayList<Integer>> error_cantidad = new ArrayList<ArrayList<Integer>>(); 
    //arreglo de tipo
    static ArrayList<Tokens> error_tipos = new ArrayList<Tokens>(); 
    
    
    Lector(){}
    
    //INSERTAR TOKEN ERROR------------------------------------------------------------
    public static void insertarTokenError (String tokenName,int linea, Tokens tipo){
        //primer token
        if(error_tokensName.size()==0){
            error_tokensName.add(tokenName);
            ArrayList<Integer> lines = new ArrayList<Integer>(); 
            lines.add(linea); //agregamos la linea
            ArrayList<Integer> cantidades = new ArrayList<Integer>(); 
            cantidades.add(1);
            error_cantidad.add(cantidades);
            error_lineas.add(lines); //agregamos al arreglo de lineas []
            error_tipos.add(tipo); //agregamos tipo
        }
        else{   
            for (int i=0;i<error_tokensName.size();i++) {
                //ya esta ingresado el token
                if (error_tokensName.get(i).equals(tokenName)){
                    //ya estaba en esa linea?
                    for (int x=0; x<error_lineas.size();x++){
                        //ya estaba en esa linea?
                        int indice = error_lineas.get(i).indexOf(linea);
                        if(indice != -1){
                            error_cantidad.get(i).set(indice, error_cantidad.get(i).get(indice)+1);
                            return;
                        }
                        else{
                            //distinta linea?
                            error_lineas.get(i).add(linea);//agregar linea
                            error_cantidad.get(i).add(1);//agregar cantidad
                            return;
                        }
                    }

                }
            }
            error_tokensName.add(tokenName);
            ArrayList<Integer> lines = new ArrayList<Integer>(); 
            lines.add(linea);
            ArrayList<Integer> cantidades = new ArrayList<Integer>(); 
            cantidades.add(1);
            error_cantidad.add(cantidades);
            error_lineas.add(lines);
            error_tipos.add(tipo);
        } 
    }
    
    //INSERTA NUEVO TOKEN EN ARREGLO tokensName---------------------------------------
    public static void insertarToken (String tokenName,int linea, Tokens tipo){
        //primer token
        if(tokensName.size()==0){
            tokensName.add(tokenName);
            ArrayList<Integer> lines = new ArrayList<Integer>(); 
            lines.add(linea); //agregamos la linea
            ArrayList<Integer> cantidades = new ArrayList<Integer>(); 
            cantidades.add(1);
            cantidad.add(cantidades);
            lineas.add(lines); //agregamos al arreglo de lineas []
            tipos.add(tipo); //agregamos tipo
        }
        else{   
            for (int i=0;i<tokensName.size();i++) {
                //ya esta ingresado el token
                if (tokensName.get(i).toString().equals(tokenName.toString())){
                    //ya estaba en esa linea?
                    int indice = lineas.get(i).indexOf(linea);
                    if(indice != -1){
                        cantidad.get(i).set(indice, cantidad.get(i).get(indice)+1);
                        return;
                    }
                    else{
                        //distinta linea?
                        lineas.get(i).add(linea);//agregar linea
                        cantidad.get(i).add(1);//agregar cantidad
                        return;
                    }
                    
                }
            }
            tokensName.add(tokenName);
            ArrayList<Integer> lines = new ArrayList<Integer>(); 
            lines.add(linea);
            ArrayList<Integer> cantidades = new ArrayList<Integer>(); 
            cantidades.add(1);
            cantidad.add(cantidades);
            lineas.add(lines);
            tipos.add(tipo);
        } 
    }
    
    //IMPRIME TOKENS---------------------------------------
    public static void imprimirTokens(ArrayList<String> tokenName, 
                                        ArrayList<Tokens> tipo, ArrayList<ArrayList<Integer>> lineas, 
                                        ArrayList<ArrayList<Integer>> cantidades){
        System.out.printf("%20s %30s %40s ", "TOKEN","TIPO","LINEAS");
        System.out.println();
        for (int i=0;i<tokenName.size();i++) {
            if (tokenName.get(i).toString()!="default"){
                System.out.printf("%20s %30s %40s ", 
                    tokenName.get(i),tipo.get(i),imprimirLineas(i,lineas, cantidades));
                 System.out.println();
            }
            
        }
    }
    
    //IMPRIMIR LINES Y CANTIDAD DE APARICIONES--------------
    public static String imprimirLineas(int index,ArrayList<ArrayList<Integer>> lineas, 
                                        ArrayList<ArrayList<Integer>> cantidades){
        ArrayList<Integer> lineasAux = lineas.get(index);
        ArrayList<Integer> cantidadAux = cantidades.get(index);
        String result = "";
        for (int i=0;i<lineasAux.size();i++) {
            result+=lineasAux.get(i);
            if(cantidadAux.get(i) != 1){
                    result += "("+cantidadAux.get(i)+")";
                }
            if (i!= lineasAux.size()-1){
                result += ", ";
            }
        }
        return result;
    }
    
    
    
    public static void leer() throws FileNotFoundException, IOException{
        JFileChooser fileChooser = new JFileChooser();
        FileFilter filtro = new FileNameExtensionFilter("Archivos txt", "txt"); 
        fileChooser.setFileFilter(filtro);
        int valor = fileChooser.showOpenDialog(fileChooser);
        
        //SE SELECCIONA UN ARCHIVO CORRECTAMENTE
        if (valor == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            File f = new File(ruta);
            Reader lector = new BufferedReader(new FileReader(f));
            
            Lexer lexer = new Lexer(lector);
            
            while(true){
                Tokens tokens = lexer.yylex();
                //int linea = lexer.lineNumber+1;
            
                if(tokens==null){
                    System.out.print("TOKENS VALIDOS:\n");
                    imprimirTokens(tokensName, tipos, lineas, cantidad);
                    System.out.print("\n\nERRORES ENCONTRADOS:\n");
                    imprimirTokens(error_tokensName, error_tipos, error_lineas, error_cantidad);
                    return;
                }
            
                //ES UN ERROR:
                switch(tokens){
                    case ERROR:
                        //insertarTokenError (lexer.lexeme,linea,tokens);
                        break;
                    default:
                        //insertarToken (lexer.lexeme,linea,tokens);
                       break;
                }
            }
        }else {
            System.out.println("No se ha seleccionado ningun archivo");
        }
    }
}
