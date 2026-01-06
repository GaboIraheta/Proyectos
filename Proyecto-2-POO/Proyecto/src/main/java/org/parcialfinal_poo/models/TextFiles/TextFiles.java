package org.parcialfinal_poo.models.TextFiles;

import javafx.scene.control.Alert;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class TextFiles { //00088023 Se define la clase donde se trabajara con los archivos
    public static void createFile(char type, String reporte){ // Método para crear un archivo .txt con la fecha y hora actuales

        LocalDateTime today = LocalDateTime.now();// 0088023 Se define la variable que nos dará le fecha y hora actual

        String nameFile = "Reporte-" + type + " " + today.getDayOfMonth() + "-" + today.getMonthValue() + "-" + today.getYear() + " " + today.getHour() + "-" + today.getMinute() + "-" + today.getSecond();//00088023 Se define el nombre del archivo con el formato: "Reporte-<Letra reporte> DD-MM-YYYY Hr-Min-sec"

        try { //00088023 Se abre un bloque try por el fileWritter
            FileWriter archivo = new FileWriter("ParcialFinal_POO/Reportes/" + nameFile);//00088023 Se crea el archivo con la dirección de la carpeta

            archivo.write(reporte); //00088023Se escribe lo que recibió el reporte
            archivo.close();//00088023Se cierra el archivo
        }catch (IOException e){ //00088023 Bloque catch que recibe el error si el fileWritter no encuentra una dirección
            try{ //TODO: comentar
                FileWriter archivo = new FileWriter("Reportes/" + nameFile);

                archivo.write(reporte);
                archivo.close();
            }
            catch(IOException e1){
                e1.printStackTrace();

                Alert alert = new Alert(Alert.AlertType.ERROR);//00088023 Se crea una alerta tipo error por si existe un error con la creación de archivos
                alert.setTitle("Error");//00088023 Se le pone como titulo Error
                alert.setContentText("Hubo un error con la creación de archivos");//00088023 Se da una breve descripción
                alert.setHeaderText(null);//00088023 Por formato se le pone como null el heather text
                alert.showAndWait();//00088023 Se muestra la alerta y espera a que la cierren
            }
        }
    }
}
