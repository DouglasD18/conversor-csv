package com.trybe.conversorcsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Conversor {

  /**
   * Função utilizada apenas para validação da solução do desafio.
   *
   * @param args Não utilizado.
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou gravar os
   *         arquivos de saída.
   */
  public static void main(String[] args) throws IOException {
    File pastaDeEntradas = new File("./entradas/");
    File pastaDeSaidas = new File("./saidas/");
    new Conversor().converterPasta(pastaDeEntradas, pastaDeSaidas);
  }

  /**
   * Converte todos os arquivos CSV da pasta de entradas. Os resultados são gerados na pasta de
   * saídas, deixando os arquivos originais inalterados.
   *
   * @param pastaDeEntradas Pasta contendo os arquivos CSV gerados pela página web.
   * @param pastaDeSaidas Pasta em que serão colocados os arquivos gerados no formato requerido pelo
   *        subsistema.
   *
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou gravar os
   *         arquivos de saída.
   */
  public void converterPasta(File pastaDeEntradas, File pastaDeSaidas) throws IOException {
    File[] listFiles = pastaDeEntradas.listFiles();
    FileReader fileReader = null;
    BufferedReader bufferedReader = null;
    FileWriter fileWriter = null;
    BufferedWriter bufferedWriter = null;
    for (File f : listFiles) {
      System.out.println(pastaDeSaidas.getAbsolutePath() + "/" + f.getName());
      System.out.println(f.getAbsolutePath());
      try {
        File inputFile = new File(f.getAbsolutePath());
        fileReader = new FileReader(inputFile);
        bufferedReader = new BufferedReader(fileReader);

        File outputFile = new File(pastaDeSaidas.getAbsolutePath() + "/" + f.getName());
        outputFile.createNewFile();
        fileWriter = new FileWriter(outputFile);
        bufferedWriter = new BufferedWriter(fileWriter);

        String lineContent = bufferedReader.readLine();
        bufferedWriter.write(lineContent);
        bufferedWriter.flush();

        lineContent = bufferedReader.readLine();

        while (lineContent != null) {
          String[] contentList = lineContent.split(",");
          String name = contentList[0].toUpperCase();
          String[] data = contentList[1].split("/");
          String birth = data[2] + "-" + data[1] + "-" + data[0];
          String first = contentList[3].substring(0, 3);
          String second = contentList[3].substring(3, 6);
          String third = contentList[3].substring(6, 9);
          String fourth = contentList[3].substring(9);
          String cpf = first + "." + second + "." + third + "-" + fourth;

          String newContent = name + "," + birth + "," + contentList[2] + "," + cpf;

          bufferedWriter.write(newContent);
          bufferedWriter.flush();

          lineContent = bufferedReader.readLine();
        }

        fileReader.close();
        bufferedReader.close();
        fileWriter.close();
        bufferedWriter.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
