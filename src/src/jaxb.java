/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import jaxb.Libros;

/**
 * @author Marco Girbau
 */
public class jaxb 
{
    Libros misLibros;
    
    public int abrir_XML_JAXB(File fichero) 
    {
        JAXBContext contexto;
        try 
        {
            //Crea una instancia JAXB
            contexto = JAXBContext.newInstance(Libros.class);
            //Crea un objeto Unmarsheller.
            Unmarshaller u = contexto.createUnmarshaller();
            //Deserializa (unmarshal) el fichero
            misLibros = (Libros) u.unmarshal(fichero);
            return 0;
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            return -1;
        }
    }
}
