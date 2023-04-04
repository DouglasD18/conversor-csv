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

    if (!pastaDeSaidas.exists()) {
      pastaDeSaidas.mkdir();
    }
    System.out.println(pastaDeSaidas.isDirectory());

    FileReader fileReader = null;
    BufferedReader bufferedReader = null;
    for (File f : listFiles) {
      try {
        File inputFile = new File(f.getAbsolutePath());
        fileReader = new FileReader(inputFile);
        bufferedReader = new BufferedReader(fileReader);

        File outputFile = new File(pastaDeSaidas.getAbsolutePath() + "/" + f.getName());
        if (outputFile.exists()) {
          outputFile.delete();
        }

        String lineContent = bufferedReader.readLine();
        String content = "";
        content += lineContent + "\n";

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

          String newContent = name + "," + birth + "," + contentList[2] + "," + cpf + "\n";

          content += newContent;

          lineContent = bufferedReader.readLine();
        }

        fileReader.close();
        bufferedReader.close();

        System.out.println(content);
        write(content, outputFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Write csv.
   */
  private void write(String content, File file) {
    try {
      if (!file.exists()) {
        file.createNewFile();
      }

      FileWriter fileWriter = null;
      BufferedWriter bufferedWriter = null;

      fileWriter = new FileWriter(file);
      bufferedWriter = new BufferedWriter(fileWriter);

      bufferedWriter.write(content);
      bufferedWriter.flush();

      fileWriter.close();
      bufferedWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
